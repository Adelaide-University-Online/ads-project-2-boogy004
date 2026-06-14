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

            while (!availableCourses.isEmpty()
                    && studyPeriod.size() < maxCoursesPerPeriod) {

                Course currentCourse = availableCourses.remove();
                studyPeriod.add(currentCourse);
                scheduledCourseCount++;

                for (Course dependentCourse : graph.getAdjacentCourses(currentCourse)) {
                    int updatedInDegree = inDegrees.get(dependentCourse) - 1;
                    inDegrees.put(dependentCourse, updatedInDegree);

                    if (updatedInDegree == 0) {
                        availableCourses.add(dependentCourse);
                    }
                }
            }

            studyPlan.add(studyPeriod);
        }

        if (scheduledCourseCount != graph.getCourses().size()) {
            throw new IllegalStateException("Course graph contains a cycle.");
        }

        return studyPlan;
    }
}
