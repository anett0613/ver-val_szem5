package service;

import domain.Grade;
import domain.Homework;
import domain.Pair;
import domain.Student;
import org.junit.jupiter.api.*;
import repository.GradeXMLRepository;
import repository.HomeworkXMLRepository;
import repository.StudentXMLRepository;
import validation.GradeValidator;
import validation.HomeworkValidator;
import validation.StudentValidator;
import validation.Validator;

import static org.junit.jupiter.api.Assertions.*;

class ServiceTest {

    private static Service serviceBefore;

    @BeforeAll
    public static void setup() {
        Validator<Student> studentValidator = new StudentValidator();
        Validator<Homework> homeworkValidator = new HomeworkValidator();
        Validator<Grade> gradeValidator = new GradeValidator();

        StudentXMLRepository studentXMLRepository1 = new StudentXMLRepository(studentValidator, "students.xml");
        HomeworkXMLRepository homeworkXMLRepository = new HomeworkXMLRepository(homeworkValidator, "homework.xml");
        GradeXMLRepository gradeXMLRepository = new GradeXMLRepository(gradeValidator, "grades.xml");

        serviceBefore = new Service(studentXMLRepository1, homeworkXMLRepository, gradeXMLRepository);
    }

    @AfterAll
    public static void cleanUp() {
        System.out.println("AfterAll cleanUp method called");
    }

    @Test
    void findAllStudents() {
    }

    @Test
    void findAllHomework() {
    }

    @Test
    void findAllGrades() {
    }

    @Test
    @DisplayName("Student addition")
    void saveValidStudent() {
        Student s = new Student("13", "Anett", 532);

        int result = serviceBefore.saveStudent(s.getID(), s.getName(), s.getGroup());
        assertEquals(0, result);

        serviceBefore.deleteStudent(s.getID());
    }

    @Test
    @DisplayName("Invalid Student addition")
    void saveInvalidStudent() {
        Student student = new Student("13", "Anett", -200);

        int result = serviceBefore.saveStudent(student.getID(), student.getName(), student.getGroup());
        assertEquals(1, result);

        serviceBefore.deleteStudent(student.getID());
    }

    @Test
    void saveValidHomework() {
        Homework homework = new Homework("25", "description", 2, 4);

        int result = serviceBefore.saveHomework(homework.getID(),homework.getDescription(),homework.getDeadline(),
                homework.getStartline());

        assertTrue(result == 1);

        serviceBefore.deleteHomework(homework.getID());
    }

    @Test
    void saveGrade() {
        Student student = new Student("11", "Anett", 532);
        Homework hw = new Homework("23", "description", 2, 4);
        Grade grade = new Grade(new Pair<>(student.getID(),hw.getID()),8.5, 3, "Good");

        serviceBefore.saveStudent(student.getID(),student.getName(),student.getGroup());
        serviceBefore.saveHomework(hw.getID(),hw.getDescription(),hw.getDeadline(),hw.getStartline());

        int result = serviceBefore.saveGrade(grade.getID().getObject1(),grade.getID().getObject2(),
                grade.getGrade(), grade.getDeliveryWeek(),grade.getFeedback());
        assertEquals(0,result);
        serviceBefore.deleteHomework(hw.getID());
        serviceBefore.deleteStudent(student.getID());
    }

    @Test
    @DisplayName("Student delete")
    void deleteStudent() {
        Student student = new Student("13", "Anett", 532);
        serviceBefore.saveStudent(student.getID(), student.getName(),student.getGroup());
        int result = serviceBefore.deleteStudent(student.getID());
        assertEquals(1,result);
    }

    @Test
    @DisplayName("Delete homework")
    void deleteHomework() {
        Homework hw = new Homework("12", "description", 2, 4);
        serviceBefore.saveHomework(hw.getID(),hw.getDescription(),hw.getDeadline(),hw.getStartline());
        int result = serviceBefore.deleteHomework(hw.getID());

        Assertions.assertTrue(result == 0);
    }

    @Test
    @DisplayName("Update student")
    void updateStudent() {
        Student student = new Student("13", "Anett", 532);
        serviceBefore.saveStudent(student.getID(), student.getName(),student.getGroup());
        int result = serviceBefore.updateStudent(student.getID(),student.getName()+"-updated",
                student.getGroup()-1);
        assertEquals(1,result);
        serviceBefore.deleteStudent(student.getID());
    }

    @Test
    @DisplayName("Update homework")
    void updateHomework() {
        Homework hw = new Homework("12", "description", 2, 4);
        serviceBefore.saveHomework(hw.getID(),hw.getDescription(),hw.getDeadline(),hw.getStartline());
        int result = serviceBefore.updateHomework(hw.getID(), hw.getDescription()+"-new",
                hw.getDeadline(), hw.getStartline()-1);
        assertEquals(0,result);
        serviceBefore.deleteHomework(hw.getID());
    }

    @Test
    @DisplayName("Extending deadline")
    void extendDeadline() {
        Homework hw = new Homework("12", "description", 2, 4);
        serviceBefore.saveHomework(hw.getID(),hw.getDescription(),hw.getDeadline(),hw.getStartline());
        int result = serviceBefore.extendDeadline(hw.getID(), 3);
        assertEquals(0,result);
        serviceBefore.deleteHomework(hw.getID());
    }

    @Test
    void createStudentFile() {
    }
}