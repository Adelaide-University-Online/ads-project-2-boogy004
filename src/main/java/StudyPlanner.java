/**
 * File: StudyPlanner.java
 * Description: Generates an optimised study plan from a CourseGraph. Uses prerequisite information and the
 * student's maximum concurrent study load to schedule courses into study periods.
 * Author: Gabrielle Booth
 * Student ID: a3145294
 * Email ID: gabrielle.booth@student.adelaide.edu.au
 * AI Tool Used: Y
 * This is my own work as defined by
 *    the University's Academic Integrity Policy.
 **/


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * Creates a study plan from a course dependency graph.
 *
 * The planner uses a topological sorting approach to ensure
 * prerequisite courses are scheduled before dependent courses.
 */
public class StudyPlanner {

    /**
     * Creates a study plan grouped into study periods.
     *
     * Each study period contains up to the maximum number of courses
     * the student can study concurrently.
     *
     * @param graph the course dependency graph
     * @param maxCoursesPerPeriod the maximum number of courses per study period
     * @return a list of study periods, where each study period is a list of courses
     * @throws IllegalArgumentException if the maximum course load is less than one
     * @throws IllegalStateException if the graph contains a cycle
     */
    public List<List<Course>> createStudyPlan(CourseGraph graph, int maxCoursesPerPeriod) {
        if (maxCoursesPerPeriod < 1) {
            throw new IllegalArgumentException("Maximum courses per period must be at least 1.");
        }

        List<List<Course>> studyPlan = new ArrayList<>();
        Map<Course, Integer> inDegrees = graph.getInDegrees();
        Queue<Course> availableCourses = new LinkedList<>();

        for (Course course : graph.getCourses()) {
            if (inDegrees.get(course) == 0) {
                availableCourses.add(course);
            }
        }

        int scheduledCourseCount = 0;

        while (!availableCourses.isEmpty()) {
            List<Course> studyPeriod = new ArrayList<>();
            List<Course> completedThisPeriod = new ArrayList<>();

            while (!availableCourses.isEmpty()
                    && studyPeriod.size() < maxCoursesPerPeriod) {

                Course currentCourse = availableCourses.remove();
                studyPeriod.add(currentCourse);
                completedThisPeriod.add(currentCourse);
                scheduledCourseCount++;
            }

            studyPlan.add(studyPeriod);

            for (Course completedCourse : completedThisPeriod) {
                for (Course dependentCourse : graph.getAdjacentCourses(completedCourse)) {
                    int updatedInDegree = inDegrees.get(dependentCourse) - 1;
                    inDegrees.put(dependentCourse, updatedInDegree);

                    if (updatedInDegree == 0) {
                        availableCourses.add(dependentCourse);
                    }
                }
            }
        }

        if (scheduledCourseCount != graph.getCourses().size()) {
            throw new IllegalStateException("Course graph contains a cycle.");
        }

        return studyPlan;
    }
}
