package ttl.mie.controller;

import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ttl.mie.domain.track.dto.ArtistDTO;
import ttl.mie.domain.track.dto.SearchResults;
import ttl.mie.domain.track.dto.TrackDTO;
import ttl.mie.extractor.DiscogRestHandler;
import ttl.mie.util.KeyCodec;

@RestController
@RequestMapping("/musicinfo")
public class MusicInfoController {

   @Autowired
   private DiscogRestHandler discogRestHandler;

   @GetMapping
   public ResponseEntity<?> getArtistsForQueryKey(@RequestParam Map<String, String> params) {
      var key = params.get("artist") + "#" + params.get("album");
      var decoded = KeyCodec.decode(key);

      SearchResults searchResults = discogRestHandler.findArtistsForKey(decoded);
      List<ArtistDTO> artists = searchResults.artists();

      return ResponseEntity.ok(artists);
   }
}
