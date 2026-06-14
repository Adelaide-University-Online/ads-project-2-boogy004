import org.junit.jupiter.api.Test;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the CourseGraph class.
 */
class CourseGraphTest {

    /**
     * Tests that a new graph contains no courses.
     */
    @Test
    void constructorShouldCreateEmptyGraph() {
        CourseGraph graph = new CourseGraph();

        assertTrue(graph.getCourses().isEmpty());
    }

    /**
     * Tests that a course can be added to the graph.
     */
    @Test
    void addCourseShouldAddCourseToGraph() {
        CourseGraph graph = new CourseGraph();
        Course course = new Course("A");

        graph.addCourse(course);

        assertTrue(graph.getCourses().contains(course));
    }

    /**
     * Tests that adding the same course more than once does not create duplicates.
     */
    @Test
    void addCourseShouldNotCreateDuplicateCourses() {
        CourseGraph graph = new CourseGraph();
        Course course = new Course("A");

        graph.addCourse(course);
        graph.addCourse(course);

        assertEquals(1, graph.getCourses().size());
    }

    /**
     * Tests that adding a prerequisite creates a directed edge
     * from the prerequisite course to the dependent course.
     */
    @Test
    void addPrerequisiteShouldCreateDirectedEdge() {
        CourseGraph graph = new CourseGraph();
        Course prerequisite = new Course("A");
        Course dependent = new Course("B");

        graph.addPrerequisite(prerequisite, dependent);

        assertTrue(graph.getAdjacentCourses(prerequisite).contains(dependent));
    }

    /**
     * Tests that adding a prerequisite also adds both courses to the graph.
     */
    @Test
    void addPrerequisiteShouldAddBothCoursesToGraph() {
        CourseGraph graph = new CourseGraph();
        Course prerequisite = new Course("A");
        Course dependent = new Course("B");

        graph.addPrerequisite(prerequisite, dependent);

        assertTrue(graph.getCourses().contains(prerequisite));
        assertTrue(graph.getCourses().contains(dependent));
    }

    /**
     * Tests that prerequisite edges are directional.
     */
    @Test
    void addPrerequisiteShouldNotCreateReverseEdge() {
        CourseGraph graph = new CourseGraph();
        Course prerequisite = new Course("A");
        Course dependent = new Course("B");

        graph.addPrerequisite(prerequisite, dependent);

        assertFalse(graph.getAdjacentCourses(dependent).contains(prerequisite));
    }

    /**
     * Tests that duplicate prerequisite relationships are not stored more than once.
     */
    @Test
    void addPrerequisiteShouldNotCreateDuplicateEdges() {
        CourseGraph graph = new CourseGraph();
        Course prerequisite = new Course("A");
        Course dependent = new Course("B");

        graph.addPrerequisite(prerequisite, dependent);
        graph.addPrerequisite(prerequisite, dependent);

        assertEquals(1, graph.getAdjacentCourses(prerequisite).size());
    }

    /**
     * Tests that a course with no dependent courses returns an empty adjacency set.
     */
    @Test
    void getAdjacentCoursesShouldReturnEmptySetForCourseWithNoDependents() {
        CourseGraph graph = new CourseGraph();
        Course course = new Course("A");

        graph.addCourse(course);

        assertTrue(graph.getAdjacentCourses(course).isEmpty());
    }

    /**
     * Tests that multiple dependent courses can be retrieved from one prerequisite course.
     */
    @Test
    void getAdjacentCoursesShouldReturnAllDependentCourses() {
        CourseGraph graph = new CourseGraph();
        Course prerequisite = new Course("A");
        Course dependentOne = new Course("B");
        Course dependentTwo = new Course("C");

        graph.addPrerequisite(prerequisite, dependentOne);
        graph.addPrerequisite(prerequisite, dependentTwo);

        Set<Course> adjacentCourses = graph.getAdjacentCourses(prerequisite);

        assertEquals(2, adjacentCourses.size());
        assertTrue(adjacentCourses.contains(dependentOne));
        assertTrue(adjacentCourses.contains(dependentTwo));
    }
}
