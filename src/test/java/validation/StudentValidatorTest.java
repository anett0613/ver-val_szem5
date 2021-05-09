package validation;

import domain.Student;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentValidatorTest {
    public static Validator<Student> studentValidator;

    @BeforeAll
    public static void setUp() {
        studentValidator = new StudentValidator();
    }

    @Test
    @DisplayName("Checking student validator")
    void validate() {
        Student student = new Student("13", "Anett", -100);
        assertThrows(ValidationException.class, () -> studentValidator.validate(student));

    }
}