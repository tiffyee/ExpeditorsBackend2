package ttl.mie.domain.track;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Format
{
   CD,
   OGG,
   MP3,
   FLAC;

   @JsonCreator
   public static Format forValue(String value) {
      return Format.valueOf(value.toUpperCase());
   }
}
