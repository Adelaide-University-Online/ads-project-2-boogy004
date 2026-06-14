/**
 * File: StudyPlannerTest.java
 * Description: Contains unit tests for the StudyPlanner class, verifying study plan generation, prerequisite
 * ordering, study load constraints and cycle detection.
 * Author: Gabrielle Booth
 * Student ID: a3145294
 * Email ID: gabrielle.booth@student.adelaide.edu.au
 * AI Tool Used: Y
 * This is my own work as defined by
 *    the University's Academic Integrity Policy.
 **/


import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the StudyPlanner class.
 */
class StudyPlannerTest {

    /**
     * Tests that a single course with no prerequisites
     * is scheduled in the first study period.
     */
    @Test
    void createStudyPlanShouldScheduleSingleCourse() {
        CourseGraph graph = new CourseGraph();
        Course courseA = new Course("A");
        graph.addCourse(courseA);

        StudyPlanner planner = new StudyPlanner();
        List<List<Course>> studyPlan = planner.createStudyPlan(graph, 2);

        assertEquals(1, studyPlan.size());
        assertEquals(List.of(courseA), studyPlan.get(0));
    }

    /**
     * Tests that a dependent course is scheduled after
     * its prerequisite course.
     */
    @Test
    void createStudyPlanShouldRespectSimplePrerequisite() {
        CourseGraph graph = new CourseGraph();
        Course courseA = new Course("A");
        Course courseB = new Course("B");

        graph.addPrerequisite(courseA, courseB);

        StudyPlanner planner = new StudyPlanner();
        List<List<Course>> studyPlan = planner.createStudyPlan(graph, 2);

        assertEquals(2, studyPlan.size());
        assertEquals(List.of(courseA), studyPlan.get(0));
        assertEquals(List.of(courseB), studyPlan.get(1));
    }

    /**
     * Tests that independent courses can be scheduled
     * in the same study period when the course load allows it.
     */
    @Test
    void createStudyPlanShouldScheduleIndependentCoursesTogether() {
        CourseGraph graph = new CourseGraph();
        Course courseA = new Course("A");
        Course courseB = new Course("B");

        graph.addCourse(courseA);
        graph.addCourse(courseB);

        StudyPlanner planner = new StudyPlanner();
        List<List<Course>> studyPlan = planner.createStudyPlan(graph, 2);

        assertEquals(1, studyPlan.size());
        assertTrue(studyPlan.get(0).contains(courseA));
        assertTrue(studyPlan.get(0).contains(courseB));
    }

    /**
     * Tests that the maximum number of courses per study period
     * is not exceeded.
     */
    @Test
    void createStudyPlanShouldRespectMaximumCourseLoad() {
        CourseGraph graph = new CourseGraph();
        Course courseA = new Course("A");
        Course courseB = new Course("B");
        Course courseC = new Course("C");

        graph.addCourse(courseA);
        graph.addCourse(courseB);
        graph.addCourse(courseC);

        StudyPlanner planner = new StudyPlanner();
        List<List<Course>> studyPlan = planner.createStudyPlan(graph, 2);

        assertEquals(2, studyPlan.size());
        assertEquals(2, studyPlan.get(0).size());
        assertEquals(1, studyPlan.get(1).size());
    }

    /**
     * Tests that a course with multiple prerequisites
     * is not scheduled until all prerequisites are complete.
     */
    @Test
    void createStudyPlanShouldWaitForAllPrerequisites() {
        CourseGraph graph = new CourseGraph();

        Course courseA = new Course("A");
        Course courseB = new Course("B");
        Course courseC = new Course("C");
        Course courseD = new Course("D");

        graph.addPrerequisite(courseA, courseD);
        graph.addPrerequisite(courseB, courseD);
        graph.addPrerequisite(courseC, courseD);

        StudyPlanner planner = new StudyPlanner();
        List<List<Course>> studyPlan = planner.createStudyPlan(graph, 3);

        assertEquals(2, studyPlan.size());

        assertTrue(studyPlan.get(0).contains(courseA));
        assertTrue(studyPlan.get(0).contains(courseB));
        assertTrue(studyPlan.get(0).contains(courseC));

        assertEquals(List.of(courseD), studyPlan.get(1));
    }

    /**
     * Tests that an invalid maximum course load is rejected.
     */
    @Test
    void createStudyPlanShouldRejectInvalidCourseLoad() {
        CourseGraph graph = new CourseGraph();
        graph.addCourse(new Course("A"));

        StudyPlanner planner = new StudyPlanner();

        assertThrows(
                IllegalArgumentException.class,
                () -> planner.createStudyPlan(graph, 0)
        );
    }

    /**
     * Tests that a cycle in the course graph is detected
     * because it would make a valid study plan impossible.
     */
    @Test
    void createStudyPlanShouldDetectCycle() {
        CourseGraph graph = new CourseGraph();

        Course courseA = new Course("A");
        Course courseB = new Course("B");

        graph.addPrerequisite(courseA, courseB);
        graph.addPrerequisite(courseB, courseA);

        StudyPlanner planner = new StudyPlanner();

        assertThrows(
                IllegalStateException.class,
                () -> planner.createStudyPlan(graph, 2)
        );
    }
}
