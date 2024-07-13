import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AirplaneTest {

    @Test
    void testValidAirplaneCreation() {
        Airplane airplane = new Airplane(1, "Boeing 747", 12, 150, 10);
        assertAll("Verify all properties",
                () -> assertEquals(1, airplane.getAirplaneID()),
                () -> assertEquals("Boeing 747", airplane.getAirplaneModel()),
                () -> assertEquals(12, airplane.getBusinessSitsNumber()),
                () -> assertEquals(150, airplane.getEconomySitsNumber()),
                () -> assertEquals(10, airplane.getCrewSitsNumber())
        );
    }

    @Test
    void testInvalidAirplaneID() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Airplane(-1, "Boeing 747", 50, 150, 10);
        });
        assertTrue(exception.getMessage().contains("Airplane ID must be positive"));
    }

    @Test
    void testInvalidAirplaneModel() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Airplane(1, null, 50, 150, 10);
        });
        assertTrue(exception.getMessage().contains("Airplane model cannot be null or empty"));
    }

    @Test
    void testInvalidSeatsNumber() {
        Exception exception1 = assertThrows(IllegalArgumentException.class, () -> {
            new Airplane(1, "Boeing 747", 10, 150, 10);
        });
        assertTrue(exception1.getMessage().contains("must be between 11 and 30"));
        Exception exception2 = assertThrows(IllegalArgumentException.class, () -> {
            new Airplane(1, "Boeing 747", 15, 310, 10);
        });
        assertTrue(exception2.getMessage().contains("must be between 31 and 300"));
        Exception exception3 = assertThrows(IllegalArgumentException.class, () -> {
            new Airplane(1, "Boeing 747", 15, 150, 0);
        });
        assertTrue(exception3.getMessage().contains("must be between 1 and 10"));
    }

    @Test
    void testToStringMethod() {
        Airplane airplane = new Airplane(1, "Boeing 747", 12, 150, 10);
        String expectedString = "Airplane{model='Boeing 747', business sits=12, economy sits=150, crew sits=10}";
        assertEquals(expectedString, airplane.toString());
    }
}
