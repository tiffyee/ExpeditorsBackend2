package ttl.mie.app;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.springframework.stereotype.Component;
import ttl.mie.domain.track.dto.TrackDTO;

@Component
public class JsonTrackFileReader {

   private ObjectMapper mapper;

   public JsonTrackFileReader(ObjectMapper mapper) {
      this.mapper = mapper;
   }


}
