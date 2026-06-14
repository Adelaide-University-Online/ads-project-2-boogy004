/**
 * File: DegreeFileReaderTest.java
 * Description: Contains unit tests for the DegreeFileReader class, verifying correct file parsing, graph
 * construction and handling of valid input.
 * Author: Gabrielle Booth
 * Student ID: a3145294
 * Email ID: gabrielle.booth@student.adelaide.edu.au
 * AI Tool Used: Y
 * This is my own work as defined by
 *    the University's Academic Integrity Policy.
 **/

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for DegreeFileReader.
 */
class DegreeFileReaderTest {

    /**
     * Creates a temporary file containing the supplied content.
     *
     * @param content the content to write to the file
     * @return the temporary file
     * @throws IOException if the file cannot be created
     */
    private File createTempDegreeFile(String content) throws IOException {
        File tempFile = File.createTempFile("degree", ".txt");

        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write(content);
        }

        return tempFile;
    }

    /**
     * Tests that all courses listed on the first line
     * are added to the graph.
     */
    @Test
    void readFileShouldAddAllCourses() throws IOException {
        String fileContent =
                "A, B, C\n" +
                        "A\n" +
                        "B, A\n" +
                        "C, A\n";

        File file = createTempDegreeFile(fileContent);

        DegreeFileReader reader = new DegreeFileReader();
        CourseGraph graph = reader.readFile(file.getAbsolutePath());

        assertEquals(3, graph.getCourses().size());
        assertTrue(graph.getCourses().contains(new Course("A")));
        assertTrue(graph.getCourses().contains(new Course("B")));
        assertTrue(graph.getCourses().contains(new Course("C")));
    }

    /**
     * Tests that prerequisite relationships
     * are correctly converted into graph edges.
     */
    @Test
    void readFileShouldCreatePrerequisiteEdges() throws IOException {
        String fileContent =
                "A, B\n" +
                        "A\n" +
                        "B, A\n";

        File file = createTempDegreeFile(fileContent);

        DegreeFileReader reader = new DegreeFileReader();
        CourseGraph graph = reader.readFile(file.getAbsolutePath());

        Course courseA = new Course("A");
        Course courseB = new Course("B");

        assertTrue(graph.getAdjacentCourses(courseA).contains(courseB));
    }

    /**
     * Tests that courses with no prerequisites
     * are still added to the graph.
     */
    @Test
    void readFileShouldHandleCoursesWithoutPrerequisites() throws IOException {
        String fileContent =
                "A\n" +
                        "A\n";

        File file = createTempDegreeFile(fileContent);

        DegreeFileReader reader = new DegreeFileReader();
        CourseGraph graph = reader.readFile(file.getAbsolutePath());

        assertEquals(1, graph.getCourses().size());
        assertTrue(graph.getCourses().contains(new Course("A")));
    }

    /**
     * Tests that an empty file is rejected.
     */
    @Test
    void readFileShouldRejectEmptyFile() throws IOException {
        File file = createTempDegreeFile("");

        DegreeFileReader reader = new DegreeFileReader();

        assertThrows(
                IllegalArgumentException.class,
                () -> reader.readFile(file.getAbsolutePath())
        );
    }

    /**
     * Tests that multiple prerequisites
     * are correctly represented in the graph.
     */
    @Test
    void readFileShouldHandleMultiplePrerequisites() throws IOException {
        String fileContent =
                "A, B, C\n" +
                        "A\n" +
                        "B\n" +
                        "C, A, B\n";

        File file = createTempDegreeFile(fileContent);

        DegreeFileReader reader = new DegreeFileReader();
        CourseGraph graph = reader.readFile(file.getAbsolutePath());

        Course courseA = new Course("A");
        Course courseB = new Course("B");
        Course courseC = new Course("C");

        assertTrue(graph.getAdjacentCourses(courseA).contains(courseC));
        assertTrue(graph.getAdjacentCourses(courseB).contains(courseC));
    }
}
