/**
 * File: CourseGraph.java
 * Description: Represents the degree structure as a directed graph using an adjacency list. Stores courses
 * as vertices and prerequisites relationships as directed edges.
 * Author: Gabrielle Booth
 * Student ID: a3145294
 * Email ID: gabrielle.booth@student.adelaide.edu.au
 * AI Tool Used: Y
 * This is my own work as defined by
 *    the University's Academic Integrity Policy.
 **/

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Represents a directed graph of courses and prerequisite relationships.
 *
 * Vertices are Course objects.
 *
 * Directed edges represent prerequisite relationships:
 * A -> B means Course A must be completed before Course B.
 */
public class CourseGraph {

    private final Map<Course, Set<Course>> adjacencyList;

    /**
     * Constructs an empty course graph.
     */
    public CourseGraph() {
        adjacencyList = new HashMap<>();
    }

    /**
     * Adds a course to the graph if it does not already exist.
     *
     * @param course the course to add
     */
    public void addCourse(Course course) {
        adjacencyList.putIfAbsent(course, new HashSet<>());
    }

    /**
     * Adds a prerequisite relationship to the graph.
     *
     * A directed edge is created from the prerequisite course
     * to the dependent course.
     *
     * Example:
     * A -> B
     *
     * means A must be completed before B.
     *
     * @param prerequisite the prerequisite course
     * @param dependent the course that depends on the prerequisite
     */
    public void addPrerequisite(Course prerequisite, Course dependent) {
        addCourse(prerequisite);
        addCourse(dependent);

        adjacencyList.get(prerequisite).add(dependent);
    }

    /**
     * Returns all courses in the graph.
     *
     * @return a set containing all courses
     */
    public Set<Course> getCourses() {
        return Collections.unmodifiableSet(adjacencyList.keySet());
    }

    /**
     * Returns all courses directly reachable from the specified course.
     *
     * @param course the course whose adjacent courses are required
     * @return a set of adjacent courses
     */
    public Set<Course> getAdjacentCourses(Course course) {
        return Collections.unmodifiableSet(
                adjacencyList.getOrDefault(course, Collections.emptySet())
        );
    }

    /**
     * Calculates the in-degree of every course in the graph.
     *
     * The in-degree of a course is the number of prerequisite
     * relationships pointing to that course.
     *
     * @return a map containing each course and its in-degree
     */
    public Map<Course, Integer> getInDegrees() {
        Map<Course, Integer> inDegrees = new HashMap<>();

        // Initialise all courses with an in-degree of zero.
        for (Course course : adjacencyList.keySet()) {
            inDegrees.put(course, 0);
        }

        // Count incoming edges.
        for (Set<Course> adjacentCourses : adjacencyList.values()) {
            for (Course dependentCourse : adjacentCourses) {
                inDegrees.put(
                        dependentCourse,
                        inDegrees.get(dependentCourse) + 1
                );
            }
        }

        return inDegrees;
    }

    /**
     * Returns a string representation of the graph.
     *
     * @return a string representation of the adjacency list
     */
    @Override
    public String toString() {
        return adjacencyList.toString();
    }
}
