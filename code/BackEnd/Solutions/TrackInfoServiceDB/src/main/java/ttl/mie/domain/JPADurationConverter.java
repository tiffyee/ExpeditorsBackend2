package ttl.mie.domain;

/**
 * A JPA Converter to convert from a String in the Database
 * to a Duration object in Java.
 * @author whynot
 */
//@Converter(autoApply = true)
//public class JPADurationConverter implements AttributeConverter<Duration, Long> {
//
//    @Override
//    public Long convertToDatabaseColumn(Duration duration) {
//        if(duration != null) {
//            Long durLong = duration.toSeconds();
//            System.out.println("DurationConverter.DToL: " + duration + ", to " + durLong);
//            return durLong;
//        }
//        return null;
//    }
//
//    @Override
//    public Duration convertToEntityAttribute(Long durSeconds) {
//        if(durSeconds != null) {
//            Duration duration = Duration.ofSeconds(durSeconds);
//            System.out.println("DurationConverter.LToD: " + durSeconds + ", to " + duration);
//            return duration;
//        }
//        return null;
//    }
//}