package ttl.mie.extractor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.springframework.stereotype.Component;
import ttl.mie.domain.track.dto.TrackDTO;

@Component
public class AudioInfoExtractor {

   private final Set<String> validExtensions;

   public AudioInfoExtractor() {
      //exService = Executors.newFixedThreadPool(100);
      validExtensions = new HashSet<>();
      validExtensions.addAll(Arrays.asList(".mp3", ".flac", ".wav", ".ogg"));
   }

   public <U> List<TrackDTO> searchTrackStream(File folder, Predicate<AudioFile> pred) throws IOException {
      return searchTrackStream(folder, Integer.MAX_VALUE, pred);
   }

   public <U> List<TrackDTO> searchTrackStream(File folder, int limit, Predicate<AudioFile> pred) throws IOException {
      List<TrackDTO> result = Files.walk(folder.toPath())
            .filter(this::isMusicFile)
            .map(p -> checkForMatchingTrack(p, pred))
            .filter(l -> !l.isEmpty())
            .limit(limit)
            .collect(ArrayList::new, List::addAll, List::addAll);

      return result;
   }

   public <U> List<TrackDTO> searchTrackStreamPar(File folder, int limit, Predicate<AudioFile> pred) throws IOException {
      List<TrackDTO> result = Files.walk(folder.toPath())
            .parallel()
            .filter(this::isMusicFile)
            .limit(limit)
            .map(p -> checkForMatchingTrack(p, pred))
            .collect(ArrayList::new, List::addAll, List::addAll);

      return result;
   }

   public <U> List<TrackDTO> searchTrackST(File folder, Predicate<AudioFile> pred) {
      List<TrackDTO> result = new ArrayList<>();
      if (folder.isDirectory()) {
         File[] files = folder.listFiles();
         for (File file : files) {
            result.addAll(searchTrackST(file, pred));
         }
      } else {
         result.addAll(checkForMatchingTrack(folder.toPath(), pred));
         // count = count + countOccurrences2(folder, searchWord);
      }
      return result;
   }

   public Predicate<AudioFile> allPass(Object o) {
      return (f) -> true;
   }

   public Predicate<AudioFile> checkLength(int length) {
      return (af) -> {
         AudioHeader ah = af.getAudioHeader();
         return ah.getTrackLength() > length;
      };

   }

   public Predicate<AudioFile> checkTitleMushy(String searchTitle) {
      return (af) -> {
         Tag tag = af.getTag();
         String tagTitle = fieldValueOrElse(tag, FieldKey.TITLE, " no title").toLowerCase();
         return tagTitle.contains(searchTitle.toLowerCase());
      };
   }

   public Predicate<AudioFile> checkTitleExact(String searchTitle) {
      return (af) -> {
         Tag tag = af.getTag();
         String tagTitle = fieldValueOrElse(tag, FieldKey.TITLE, " no title");
         return tagTitle.equals(searchTitle);
      };
   }

   public boolean isMusicFile(Path path) {
      String str = path.toString();
      int dotIndex = str.lastIndexOf(".");
      if (dotIndex != -1) {
         str = str.substring(dotIndex);
         return validExtensions.contains(str);
      }
      return false;
   }

   public <U> List<TrackDTO> checkForMatchingTrack(Path myDocument,
                                                   Predicate<AudioFile> bpred) {
      List<TrackDTO> result = new ArrayList<>();

      if (isMusicFile(myDocument)) {
         try {
            AudioFile f = AudioFileIO.read(myDocument.toFile());
            Tag tag = f.getTag();
            AudioHeader ah = f.getAudioHeader();

            if (bpred.test(f)) {
               //AudioTrackInfo fileInfo = new AudioTrackInfo();
               var year = fieldValueOrElse(tag, FieldKey.YEAR, "");
               if (year.isBlank()) {
                  year = fieldValueOrElse(tag, FieldKey.ALBUM_YEAR, "");
               }
               if (year.isBlank()) {
                  year = fieldValueOrElse(tag, FieldKey.ORIGINAL_YEAR, "");
               }
               TrackDTO fileInfo = TrackDTO.builder()
                     .length(ah.getTrackLength())
                     .format(ah.getFormat())
                     .title(fieldValueOrElse(tag, FieldKey.TITLE, ""))
                     .album(fieldValueOrElse(tag, FieldKey.ALBUM, ""))
                     .artist(fieldValueOrElse(tag, FieldKey.ARTIST, ""))
                     .group(fieldValueOrElse(tag, FieldKey.ARTIST, ""))
                     .genre(fieldValueOrElse(tag, FieldKey.GENRE, ""))
                     .year(year)
                     .build();
               result.add(fileInfo);
            }

         } catch (Exception e) {
            // e.printStackTrace();
         }
      }
      return result;
   }

   public String fieldValueOrElse(Tag tag, FieldKey key, String orElse) {
      String value = tag.getFirst(key);
      if (value == null) {
         value = orElse;
      }
      return value;
   }
}
