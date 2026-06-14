/**
 * File: Course.java
 * Description: Represents an individual course within a degree plan. Stores the course code and provides object
 * identity through equals(), hashCode() and toString().
 * Author: Gabrielle Booth
 * Student ID: a3145294
 * Email ID: gabrielle.booth@student.adelaide.edu.au
 * AI Tool Used: Y
 * This is my own work as defined by
 *    the University's Academic Integrity Policy.
 **/

import java.util.Objects;

/**
 * Represents a single course in a degree plan.
 * Each course has a unique course code or name.
 */
public class Course {

    private final String code;

    /**
     * Creates a Course with the given course code.
     *
     * @param code the unique course code or name.
     * @throws IllegalArgumentException if the course code is null or blank.
     */
    public Course(String code) {
        if (code == null || code.isBlank()) {
            throw new IllegalArgumentException("Course code cannot be null or blank.");
        }

        this.code = code.trim();
    }

    /**
     * Compares this course with another object.
     * Two courses are considered equal if they have the same course code.
     *
     * @param obj the object to compare with this course.
     * @return true if both courses have the same code, otherwise fail.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Course other)) {
            return false;
        }
        return code.equals(other.code);
    }

    /**
     * Returns a hash code based on the course code.
     *
     * @return the hash code for this course.
     */
    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

    /**
     * Returns a string representation of the course
     *
     * @return the course code
     */
    @Override
    public String toString() {
        return code;
    }

    public String getCode() {
        return code;
    }
}
