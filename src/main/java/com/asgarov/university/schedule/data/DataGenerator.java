package com.asgarov.university.schedule.data;

import com.asgarov.university.schedule.domain.*;
import com.asgarov.university.schedule.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//@Component
public class DataGenerator implements CommandLineRunner {

    private final ProfessorService professorService;
    private final LectureService lectureService;
    private final StudentService studentService;
    private final RoomService roomService;
    private final CourseService courseService;

    public DataGenerator(ProfessorService professorService, LectureService lectureService, StudentService studentService, RoomService roomService, CourseService courseService) {
        this.professorService = professorService;
        this.lectureService = lectureService;
        this.studentService = studentService;
        this.roomService = roomService;
        this.courseService = courseService;
    }

    @Override
    public void run(final String... args) {
        generateUniversityTestData();
    }

    public void generateUniversityTestData() {
        roomService.createAll(generateRooms("Room A1", "Room A2", "Room A3", "Room A4", "Room B1", "Room B2",
                "Room B3", "Room B4"));
//        studentService.createAll(generateStudents("Mark Zukerberg", "Joshua Bloch", "Bill Gates",
//                "Elon Musk", "Nikola Tesla"));
//
//        professorService.createAll(generateLecturers("Vasya Pupkin", "Petya Pushkin", "Sema Sirkin",
//                "Charles Xavier", "Linus Torvalds", "Albus Dumbledore"));
//
//        courseService.createAll(generatesCourses("Informatics 101", "Algorithmic Thinking", "JavaEE",
//                "C++ in Robotics", "Data Science", "Hacking with Python", "Architecture of Networks",
//                "Game Development with C#", "Ethical Hacking", "Non-ethical Hacking", "Programming Architectural Solutions",
//                "Agile Methodologies"));
    }

    private List<Course> generatesCourses(String... courseNames) {
        List<Course> courses = new ArrayList<>();
        List<Student> students = studentService.findAll();

        for (final String courseName : courseNames) {
            Course course = new Course(courseName);

            for (Student student : students) {
                double everyThirdBound = 0.333;
                if (Math.random() < everyThirdBound) {
                    course.addStudent(student);
                }
            }

            int numberOfLecturesPerCourse = 12;
            course.setLectures(generateLectures(numberOfLecturesPerCourse));
            course.setProfessor(getRandomProfessor());
            courses.add(course);
        }
        return courses;
    }

    private Professor getRandomProfessor() {
        List<Professor> professors = professorService.findAll();
        return professors.get(new Random().nextInt(professors.size() - 1));
    }

    private List<Lecture> generateLectures(int amount) {
        List<Lecture> lectures = new ArrayList<>(amount);
        for (int i = 0; i < amount; i++) {
            lectures.add(new Lecture(getRandomDateTime(), getRandomRoom()));
        }
        lectureService.createAll(lectures);
        return lectures;
    }

    private LocalDateTime getRandomDateTime() {
        int averageMonthBound = 30;
        int firstLessonHour = 9;
        int lastLessonHour = 17;
        return LocalDateTime.now()
                .plusDays(new Random().nextInt(averageMonthBound))
                .withHour(firstLessonHour)
                .plusHours(new Random().nextInt(lastLessonHour - firstLessonHour));
    }

    private Room getRandomRoom() {
        List<Room> rooms = roomService.findAll();
        return rooms.get(randomNumberListSizeBound(rooms));
    }

    private int randomNumberListSizeBound(final List<Room> rooms) {
        return new Random().nextInt(rooms.size() - 1);
    }

    private List<Professor> generateLecturers(String... lecturerNames) {
        List<Professor> professors = new ArrayList<>();
        for (final String lecturerName : lecturerNames) {
            String firstName = lecturerName.split(" ")[0];
            String lastName = lecturerName.split(" ")[1];
            Professor professor = new Professor(firstName, lastName, lastName.toLowerCase() + "@mail.ru", "pass");
            professors.add(professor);
        }
        return professors;
    }

    private List<Student> generateStudents(String... studentNames) {
        List<Student> students = new ArrayList<>();
        for (final String studentName : studentNames) {
            String firstName = studentName.split(" ")[0];
            String lastName = studentName.split(" ")[1];
            students.add(new Student(firstName, lastName, lastName.toLowerCase() + "@mail.ru", "pass", randomDegree()));
        }
        return students;
    }

    private Student.Degree randomDegree() {
        Student.Degree[] degrees = Student.Degree.values();
        int bound = new Random().nextInt(degrees.length);
        return degrees[bound];
    }

    private List<Room> generateRooms(String... roomNames) {
        List<Room> rooms = new ArrayList<>();
        for (final String roomName : roomNames) {
            rooms.add(new Room(roomName));
        }
        return rooms;
    }
}
