import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FlightTest {
    private Flight flight;
    private Timestamp dateFrom;
    private Timestamp dateTo;
    private Airplane airplane;

    @BeforeEach
    void setUp() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        dateFrom = new Timestamp(sdf.parse("15/10/23 12:00:00").getTime());
        dateTo = new Timestamp(sdf.parse("15/10/23 15:00:00").getTime());
        airplane = new Airplane(1, "Boeing 747", 12, 150, 10);
        // 使用反射清空 FlightCollection 中的 flights 列表
//        Field flightsField = FlightCollection.class.getDeclaredField("flights");
//        flightsField.setAccessible(true);
//        flightsField.set(null, new ArrayList<Flight>());

        // clear the collection
        FlightCollection.getFlights().clear();

        flight = new Flight(1, "Sydney", "Melbourne", "QF400", "Qantas", dateFrom, dateTo, airplane);
    }

    @Test
    @DisplayName("Valid Flight Creation")
    void testValidFlightCreation() {
        assertAll(
                () -> assertEquals("Sydney", flight.getDepartTo()),
                () -> assertEquals("Melbourne", flight.getDepartFrom()),
                () -> assertEquals("QF400", flight.getCode()),
                () -> assertEquals("Qantas", flight.getCompany()),
                () -> assertEquals(dateFrom, flight.getDateFrom()),
                () -> assertEquals(dateTo, flight.getDateTo()),
                () -> assertEquals(airplane, flight.getAirplane())
        );
    }

    @Test
    @DisplayName("Invalid Flight ID")
    void testInvalidFlightID() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Flight(0, "Sydney", "Melbourne", "QF400", "Qantas", dateFrom, dateTo, airplane));
        assertTrue(exception.getMessage().contains("Flight ID must be positive"));
    }

    @Test
    @DisplayName("Invalid Departure Location")
    void testInvalidDepartureLocation() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Flight(1, "", "Melbourne", "QF400", "Qantas", dateFrom, dateTo, airplane));
        assertTrue(exception.getMessage().contains("Departure to location cannot be empty"));
    }

    @Test
    @DisplayName("Invalid Arrival Location")
    void testInvalidArrivalLocation() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Flight(1, "Sydney", "", "QF400", "Qantas", dateFrom, dateTo, airplane));
        assertTrue(exception.getMessage().contains("Departure from location cannot be empty"));
    }

    @Test
    @DisplayName("Invalid Date Format")
    void testInvalidDateFormat() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Timestamp wrongDate = new Timestamp(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse("2023/10/15 12:00:00").getTime());
            new Flight(1, "Sydney", "Melbourne", "QF400", "Qantas", wrongDate, dateTo, airplane);
        });
        assertTrue(exception.getMessage().contains("Departure date format must be 'DD/MM/YY HH:MM:SS'"));
    }

    @Test
    @DisplayName("Null Airplane Object")
    void testNullAirplaneObject() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Flight(1, "Sydney", "Melbourne", "QF400", "Qantas", dateFrom, dateTo, null);
        });
        assertTrue(exception.getMessage().contains("Airplane cannot be null"));
    }

    @Test
    @DisplayName("Check Flight Uniqueness")
    void testFlightUniqueness() throws Exception {
        FlightCollection.addFlights(new ArrayList<>(List.of(flight)));
        Timestamp date = new Timestamp(new SimpleDateFormat("dd/MM/yy HH:mm:ss").parse("15/10/23 12:00:00").getTime());
        Airplane airplane = new Airplane(2, "Airbus A380", 12, 200, 10);

        // 尝试添加重复的航班
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            new Flight(1, "Sydney", "Melbourne", "QF400", "Qantas", dateFrom, dateTo, airplane);
        });
        assertTrue(exception.getMessage().contains("A flight with the same details already exists in the system."));
    }

    @Test
    @DisplayName("Test toString Output")
    void testToStringOutput() {
        String expectedOutput = "Flight{airplane=Airplane{model='Boeing 747', business sits=12, economy sits=150, crew sits=10}, date to='" + dateTo + "', date from='" + dateFrom + "', depart from='Melbourne', depart to='Sydney', code='QF400', company='Qantas'}";
        assertEquals(expectedOutput, flight.toString());
    }
}
