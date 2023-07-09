package guru.springframework.brewery.monolith.web.mappers;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class DateMapperTest {

    @InjectMocks
    private DateMapper classUnderTest;

    @Test
    void test_asOffsetDateTime_null() {
        OffsetDateTime actual = classUnderTest.asOffsetDateTime(null);

        assertNull(actual);
    }

    @Test
    void test_asTimestamp_null() {
        Timestamp actual = classUnderTest.asTimestamp(null);

        assertNull(actual);
    }
}