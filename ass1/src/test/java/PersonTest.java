import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PersonTest {

    @Test
    void testValidPersonCreation() {
        Person person = new Person("John", "Doe", 30, "Man");
        assertAll("Valid person creation",
                () -> assertEquals("John", person.getFirstName()),
                () -> assertEquals("Doe", person.getSecondName()),
                () -> assertEquals(30, person.getAge()),
                () -> assertEquals("Man", person.getGender())
        );
    }

    @Test
    void testInvalidAge() {
        Exception exception1 = assertThrows(IllegalArgumentException.class, () -> new Person("John", "Doe", -1, "Man"));
        assertTrue(exception1.getMessage().contains("Age must be between 0 and 150"));
        Exception exception2 = assertThrows(IllegalArgumentException.class, () -> new Person("John", "Doe", 151, "Man"));
        assertTrue(exception2.getMessage().contains("Age must be between 0 and 150"));
    }

    @Test
    void testInvalidGender() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Person("John", "Doe", 30, "Alien"));
        assertTrue(exception.getMessage().contains("Invalid gender provided"));
    }

    @Test
    void testInvalidFirstName() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Person("1John", "Doe", 30, "Man"));
        assertTrue(exception.getMessage().contains("First name must start with a letter"));
    }

    @Test
    void testInvalidSecondName() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Person("John", "2Doe", 30, "Man"));
        assertTrue(exception.getMessage().contains("Second name must start with a letter"));
    }

    @Test
    void testToStringOutput() {
        Person person = new Person("John", "Doe", 30, "Man");
        String expectedOutput = "Person{firstName='John', secondName='Doe', age=30, gender='Man'}";
        assertEquals(expectedOutput, person.toString());
    }
}
