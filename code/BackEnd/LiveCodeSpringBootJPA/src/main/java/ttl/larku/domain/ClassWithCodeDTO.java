package ttl.larku.domain;

import java.time.LocalDate;

public record ClassWithCodeDTO(LocalDate startDate, LocalDate endDate, String courseCode) {
}
