import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PassengerTest {
    private Passenger passenger;

    @BeforeEach
    void setUp() {
        // 正确初始化一个Passenger对象用于大部分测试
        passenger = new Passenger("John", "Doe", 30, "Man", "john.doe@example.com", "0412345678", "A1234567", "4485364739527352", 123);
    }

    @Test
    @DisplayName("Valid Passenger Creation")
    void testValidPassengerCreation() {
        assertAll(
                () -> assertEquals("John", passenger.getFirstName()),
                () -> assertEquals("Doe", passenger.getSecondName()),
                () -> assertEquals(30, passenger.getAge()),
                () -> assertEquals("Man", passenger.getGender()),
                () -> assertEquals("john.doe@example.com", passenger.getEmail()),
                () -> assertEquals("0412345678", passenger.getPhoneNumber()),
                () -> assertEquals("A1234567", passenger.getPassport()),
                () -> assertEquals("4485364739527352", passenger.getCardNumber()),
                () -> assertEquals(123, passenger.getSecurityCode())
        );
    }

    @Test
    @DisplayName("Invalid Email")
    void testInvalidEmail() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Passenger("John", "Doe", 30, "Man", "invalid_email", "0412345678", "A1234567", "4485364739527352", 123));
        assertTrue(exception.getMessage().contains("Email address is invalid"));
    }

    @Test
    @DisplayName("Invalid Phone Number")
    void testInvalidPhoneNumber() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Passenger("John", "Doe", 30, "Man", "john.doe@example.com", "0123456789", "A1234567", "4485364739527352", 123));
        assertTrue(exception.getMessage().contains("Phone number is invalid"));
    }

    @Test
    @DisplayName("Invalid Passport Number")
    void testInvalidPassportNumber() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Passenger("John", "Doe", 30, "Man", "john.doe@example.com", "0412345678", "A1234567890", "4485364739527352", 123));
        assertTrue(exception.getMessage().contains("Passport number must be 9 characters or less"));
    }

    @Test
    @DisplayName("Invalid Credit Card Number")
    void testInvalidCreditCardNumber() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Passenger("John", "Doe", 30, "Man", "john.doe@example.com", "0412345678", "A1234567", "invalidcard", 123));
        assertTrue(exception.getMessage().contains("Invalid credit card number"));
    }

    @Test
    @DisplayName("Invalid Security Code")
    void testInvalidSecurityCode() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Passenger("John", "Doe", 30, "Man", "john.doe@example.com", "0412345678", "A1234567", "4485364739527352", 12));
        assertTrue(exception.getMessage().contains("Security code must be 3 or 4 digits"));
    }

    @Test
    @DisplayName("Test toString method for correct format")
    void testToStringMethod() {
        Passenger passenger = new Passenger("Alice", "Wonderland", 28, "Woman", "alice@example.com", "0412345678", "B123456", "4485364739527352", 123);
        String expectedString = "Passenger{ Fullname= Alice Wonderland, email='alice@example.com', phoneNumber='0412345678', passport='B123456'}";
        assertEquals(expectedString, passenger.toString(), "toString method does not return the correct format.");
    }
}
