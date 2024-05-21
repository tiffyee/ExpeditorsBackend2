package expeditors.backend.rating;


import expeditors.backend.domain.Course;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * A straight ahead Unit test.  Only Mockito, no Spring
 */
@ExtendWith(MockitoExtension.class)
//Can use @MockitoSettings to turn on LENIENT mode.
//Then Mockito won't get upset with unused Mocks.
//Probably better to leave it off and get rid of
//unused mocks.
//@MockitoSettings(strictness = Strictness.LENIENT)
@Tag("unit")
public class NetworkProvideWithHolderMockTest {

    @Mock
    private RestClientHolder restClientHolder;

    @InjectMocks
    private TestableNetworkRatingProvider networkRatingProvider;

    @BeforeEach
    public void setup() {
    }

    @Test
    public void testGetGoodRating() {
        Mockito.when(restClientHolder.getRating(1)).thenReturn(4);
        Course course = new Course("Math-101", "Intro to Math");
        course.setId(1);

        networkRatingProvider.addRatingToCourse(course);

        assertTrue(course.getRating() > 0);

        Mockito.verify(restClientHolder).getRating(1);
    }

}
