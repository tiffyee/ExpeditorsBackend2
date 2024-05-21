package ttl.mie.service;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.sound.midi.Track;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ttl.mie.dao.TrackDAO;
import ttl.mie.dao.inmemory.InMemorySearchSpec;
import ttl.mie.domain.track.Format;
import ttl.mie.domain.track.dto.TrackDTO;
import ttl.mie.pricing.InMemoryPricingProvider;
import ttl.mie.pricing.PricingProvider;

@Service
@Transactional
public class TrackService {

   private TrackDAO trackDAO;
   private DecimalFormat decimalFormat = new DecimalFormat("0.00");

   private BeanWrapper beanWrapper = new BeanWrapperImpl(TrackDTO.builder().build());
   private final PricingProvider pricingProder;

   public TrackService(TrackDAO trackDAO, InMemoryPricingProvider pricingProder) {
      this.trackDAO = trackDAO;
      this.pricingProder = pricingProder;
   }

   public TrackDTO addTrack(TrackDTO track) {
      return trackDAO.insert(track);
   }

   public boolean deleteTrack(int id) {
      return trackDAO.delete(id);
   }

   public boolean updateTrack(TrackDTO track) {
      return trackDAO.update(track);
   }

   public Optional<TrackDTO> getById(int id) {
      var track = trackDAO.findById(id)
            .map(this::addPriceToTrack);

      return track;
   }

   public List<TrackDTO> getAllTracks() {
//      List<TrackDTO> tracks = trackRepo.findAll();
//      tracks.forEach(this::addPriceToTrack);
      List<TrackDTO> tracks =
            trackDAO.findAll().stream()
                  .map(this::addPriceToTrack)
                  .toList();

      return tracks;
   }

   public List<TrackDTO> getTracksBySearchSpec(Map<String, String> rawQueryStrings) {
      var queryStrings = convertToMapOfCorrectTypes(rawQueryStrings);
      List<InMemorySearchSpec<TrackDTO>> searchSpecs = new ArrayList<>();
      queryStrings.forEach((k, value) -> {
         var opSymbol = k.substring(0, 1);
         var searchType = InMemorySearchSpec.SearchType.getBySymbol(opSymbol);
         var key = k.substring(2);
         var ss = switch (key) {
            case String s when s.equals("artist") -> new InMemorySearchSpec<TrackDTO>(
                  searchType,
                  "artist", value,
                  (t) ->
                        t.artists().stream().anyMatch(a -> {
                           var result = a.name().toUpperCase().contains(value.toString().toUpperCase());
                           return result;
                        }));

            default -> new InMemorySearchSpec<TrackDTO>(
                  searchType,
                  key, value);
         };
         if (ss != null) {
            searchSpecs.add(ss);
         }
      });

      List<TrackDTO> result = getTracksBySearchSpec(searchSpecs);
      return result;
   }

   public List<TrackDTO> getTracksBySearchSpec(List<InMemorySearchSpec<TrackDTO>> searchSpecs) {
      List<TrackDTO> result = trackDAO.findBySearchSpec(searchSpecs);
      return result;
   }

   public Map<String, Object> convertToMapOfCorrectTypes(Map<String, String> queryStrings) {
      Map<String, Object> result = new HashMap<>();
      queryStrings.forEach((k, v) -> {
         switch (k) {
            case String s when
                  s.contains("length") -> result.put(k, Duration.parse(v));

            case String s when
                  s.contains("format") -> result.put(k, Format.valueOf(v.toUpperCase()));

            default -> result.put(k, v);
         }
      });

      return result;
   }

   public List<TrackDTO> getTracksBy(Map<String, String> queryStrings) {
      TrackDTO.TrackBuilder builder = new TrackDTO.TrackBuilder();
      queryStrings.forEach((k, v) -> {
         if (k.equals("title")) {
            builder.title(v.toString());
         } else if (k.equals("artist")) {
            builder.artist(v.toString());
         } else if (k.equals("album")) {
            builder.album(v.toString());
         }
      });
      TrackDTO example = builder.build();

      List<TrackDTO> result = getTracksBy(example);
      return result;
   }

   public List<TrackDTO> getTracksBy(TrackDTO example) {
      List<TrackDTO> result = trackDAO.findByExample(example).stream()
            .map(this::addPriceToTrack)
            .toList();
      return result;
   }

   private TrackDTO addPriceToTrack(TrackDTO track) {
      BigDecimal price = pricingProder.getPriceByTrackId(track.trackId());
      return TrackDTO.copyWithPrice(price, track);
   }


   /**
    * Partial update of tracks.  Here we do it "by hand", i.e
    * use the Reflection API directly.  We try to match
    * property names in the input map to corresponding "set"Blah
    * methods in the object.
    *
    * @param id
    * @param props
    */
   public void updateTrackPartial(int id, Map<String, Object> props) {
      trackDAO.findById(id)
            .ifPresent(t -> {
               Class<?> clazz = Track.class;
               Map<String, Method> methods = Arrays
                     .asList(clazz.getMethods()).stream()
                     .filter(m -> m.getName().startsWith("set"))
                     .collect(Collectors.toMap(m -> m.getName(), m -> m));

               props.forEach((name, value) -> {
                  if (!name.equals("id")) {
                     String setMethodName = "set" + name.substring(0, 1).toUpperCase() + name.substring(1);
                     Method m = methods.get(setMethodName);
                     if (m != null) {
                        Class<?>[] paramTypes = m.getParameterTypes();
                        if (paramTypes.length == 1) {
                           Object param = value;
                           try {
                              Class<?> pClass = paramTypes[0];
                              //We only handle String properties for now.
                              if (pClass.equals(String.class)) {
                                 param = String.valueOf(value);
                              }
                              m.invoke(t, param);
                           } catch (Exception e) {
                              e.printStackTrace();
                           }
                        }
                     }
                  }
               });
            });
   }

   /**
    * Here we use the handy Spring BeanWrapper class to make our lives
    * that little bit simpler.
    *
    * @param id    The id of the resource to update.
    * @param props The properties to update.
    */
   public void updateTrackPartialBeanWrapper(int id, Map<String, Object> props) {
      trackDAO.findById(id)
            .ifPresent(track -> {

//      if (track != null) {
               BeanWrapper bw = PropertyAccessorFactory.forBeanPropertyAccess(track);
               props.forEach((name, value) -> {
                  if (!name.equals("id")) {
                     if (bw.isWritableProperty(name)) {
                        Class<?> pClass = bw.getPropertyType(name);
                        bw.setPropertyValue(name, bw.convertIfNecessary(value, pClass));
                     }
                  }
               });
            });
   }
}

