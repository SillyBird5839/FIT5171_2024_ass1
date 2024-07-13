import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TicketTest {
    private Ticket ticket;
    private Passenger passenger;
    private Flight flight;
    private Timestamp dateFrom;
    private Timestamp dateTo;
    private Airplane airplane;

    @BeforeEach
    void setUp() throws Exception {
        // 初始化 Passenger 对象
        passenger = new Passenger("John", "Doe", 30, "Man", "john.doe@example.com", "0412345678", "A1234567", "4485364739527352", 123);

        // 初始化 Flight 对象
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        dateFrom = new Timestamp(sdf.parse("15/10/23 12:00:00").getTime());
        dateTo = new Timestamp(sdf.parse("15/10/23 15:00:00").getTime());
        airplane = new Airplane(1, "Boeing 747", 12, 150, 10);

        // 清空 FlightCollection 中的 flights 列表
        FlightCollection.getFlights().clear();

        flight = new Flight(1, "Sydney", "Melbourne", "QF400", "Qantas", dateFrom, dateTo, airplane);

        // 初始化 Ticket 对象
        ticket = new Ticket(1, 1000, flight, false, passenger);
    }

    @Test
    @DisplayName("Valid Ticket Creation")
    void testValidTicketCreation() {
        assertAll(
                () -> assertEquals(1, ticket.getTicket_id()),
                () -> assertEquals(1120, ticket.getPrice()),
                () -> assertEquals(flight, ticket.getFlight()),
                () -> assertEquals(passenger, ticket.getPassenger()),
                () -> assertFalse(ticket.ticketStatus()),
                () -> assertFalse(ticket.getClassVip())
        );
    }

    @Test
    @DisplayName("Invalid Ticket ID")
    void testInvalidTicketID() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Ticket(0, 1000, flight, false, passenger));
        assertTrue(exception.getMessage().contains("Ticket ID must be positive."));
    }

    @Test
    @DisplayName("Invalid Ticket Price")
    void testInvalidTicketPrice() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Ticket(1, 0, flight, false, passenger));
        assertTrue(exception.getMessage().contains("Price must be positive."));
    }

    @Test
    @DisplayName("Invalid Flight")
    void testInvalidFlight() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Ticket(1, 1000, null, false, passenger));
        assertTrue(exception.getMessage().contains("Flight cannot be null."));
    }

    @Test
    @DisplayName("Invalid Passenger")
    void testInvalidPassenger() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Ticket(1, 1000, flight, false, null));
        assertTrue(exception.getMessage().contains("Passenger cannot be null."));
    }

    @Test
    @DisplayName("Discount for Age Below 15")
    void testDiscountForAgeBelow15() {
        passenger = new Passenger("Jane", "Doe", 10, "Woman", "jane.doe@example.com", "0412345678", "A1234567", "4485364739527352", 123);
        ticket = new Ticket(2, 1000, flight, false, passenger);
        assertEquals(560, ticket.getPrice()); // 50% discount, then 12% service tax
    }

    @Test
    @DisplayName("Discount for Age 60 and Above")
    void testDiscountForAge60AndAbove() {
        passenger = new Passenger("Bob", "Smith", 65, "Man", "bob.smith@example.com", "0412345678", "A1234567", "4485364739527352", 123);
        ticket = new Ticket(3, 1000, flight, false, passenger);
        assertEquals(0, ticket.getPrice()); // 100% discount
    }

    @Test
    @DisplayName("Service Tax Application")
    void testServiceTaxApplication() {
        passenger = new Passenger("Alice", "Brown", 30, "Woman", "alice.brown@example.com", "0412345678", "A1234567", "4485364739527352", 123);
        ticket = new Ticket(4, 1000, flight, false, passenger);
        assertEquals(1120, ticket.getPrice()); // 12% service tax applied
    }

    @Test
    @DisplayName("Ticket Status True or False")
    void testTicketStatus() {
        ticket.setTicketStatus(true);
        assertTrue(ticket.ticketStatus());
        ticket.setTicketStatus(false);
        assertFalse(ticket.ticketStatus());
    }

    @Test
    @DisplayName("Test toString Output")
    void testToStringOutput() {
        String expectedOutput = "Ticket{\n" +
                "Price=" + ticket.getPrice() + "KZT, \n" +
                flight.toString() + "\n" +
                "Vip status=" + ticket.getClassVip() + "\n" +
                passenger.toString() + "\n" +
                "Ticket was purchased=" + ticket.ticketStatus() + "\n}";
        assertEquals(expectedOutput, ticket.toString());
    }
}
