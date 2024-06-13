package ttl.mie.domain;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.time.Duration;

 /**
 * A JPA Converter to convert from a String in the Database
 * to a Duration object in Java.
 * @author whynot
 */
@Converter(autoApply = true)
public class JPADurationConverter implements AttributeConverter<Duration, Long> {

    @Override
    public Long convertToDatabaseColumn(Duration duration) {
        if(duration != null) {
            Long durString = duration.toSeconds();
//            System.out.println("DurationConverter.DToS: " + duration + ", to " + durString);
            return durString;
        }
        return null;
    }

    @Override
    public Duration convertToEntityAttribute(Long durSeconds) {
        if(durSeconds != null) {
            Duration dur = Duration.ofSeconds(durSeconds);
//            System.out.println("DurationConverter.SToD: " + durString + ", to " + durString);
            return dur;
        }
        return null;
    }
}