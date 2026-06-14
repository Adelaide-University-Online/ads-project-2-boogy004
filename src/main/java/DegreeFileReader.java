import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Reads degree structure data from a text file and constructs a CourseGraph.
 *
 * The expected file format is:
 * First line: all courses required for the degree, separated by commas.
 * Remaining lines: course followed by zero or more prerequisites, separated by commas.
 */
public class DegreeFileReader {

    /**
     * Reads a degree file and builds a directed course graph.
     *
     * @param fileName the name or path of the text file to read
     * @return a CourseGraph representing the courses and prerequisite relationships
     * @throws IOException if the file cannot be read
     */
    public CourseGraph readFile(String fileName) throws IOException {
        CourseGraph graph = new CourseGraph();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String firstLine = reader.readLine();

            if (firstLine == null || firstLine.isBlank()) {
                throw new IllegalArgumentException("Degree file cannot be empty.");
            }

            addAllCourses(firstLine, graph);

            String line;
            while ((line = reader.readLine()) != null) {
                processCourseLine(line, graph);
            }
        }

        return graph;
    }

    /**
     * Adds all courses from the first line of the degree file to the graph.
     *
     * @param line the first line containing all required courses
     * @param graph the graph being constructed
     */
    private void addAllCourses(String line, CourseGraph graph) {
        String[] courseCodes = line.split(",");

        for (String courseCode : courseCodes) {
            graph.addCourse(new Course(courseCode.trim()));
        }
    }

    /**
     * Processes one line of the degree file.
     *
     * The first item is the dependent course.
     * Any remaining items are prerequisites for that course.
     *
     * @param line the line to process
     * @param graph the graph being constructed
     */
    private void processCourseLine(String line, CourseGraph graph) {
        if (line == null || line.isBlank()) {
            return;
        }

        String[] parts = line.split(",");

        Course dependentCourse = new Course(parts[0].trim());
        graph.addCourse(dependentCourse);

        for (int i = 1; i < parts.length; i++) {
            Course prerequisite = new Course(parts[i].trim());
            graph.addPrerequisite(prerequisite, dependentCourse);
        }
    }
}
