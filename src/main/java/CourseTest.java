import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Course class.
 */

public class CourseTest {

    /**
     * Tests that a valid course code is stored correctly.
     */
    @Test
    void constructorShouldStoreValidCourseCode() {
        Course course = new Course("COMP1001");

        assertEquals("COMP1001", course.getCode());
    }

    /**
     * Tests that leading and trailing spaces are removed from the course code.
     */
    @Test
    void constructorShouldTrimCourseCode() {
        Course course = new Course("COMP1001    ");

        assertEquals("COMP1001", course.getCode());
    }

    /**
     * Tests that a null course code is rejected.
     */
    @Test
    void constructorShouldRejectNullCourseCode() {
        assertThrows(IllegalArgumentException.class, () -> new Course(null));
    }

    /**
     * Tests that a blank course code is rejected.
     */
    @Test
    void constructorShouldRejectBlankCourseCode() {
        assertThrows(IllegalArgumentException.class, () -> new Course("   "));
    }

    /**
     * Tests that two Course objects with the same code are considered equal.
     */
    @Test
    void equalsShouldReturnTrueForCoursesWithSameCode() {
        Course courseOne = new Course("COMP1001");
        Course courseTwo = new Course("COMP1001");

        assertEquals(courseOne, courseTwo);
    }

    /**
     * Tests that two Course objects with different codes are not considered equal.
     */
    @Test
    void equalsShouldReturnFalseForCoursesWithDifferentCodes() {
        Course courseOne = new Course("COMP1001");
        Course courseTwo = new Course("COMP2001");

        assertNotEquals(courseOne, courseTwo);
    }

    /**
     * Tests that a Course is not equal to null.
     */
    @Test
    void equalsShouldReturnFalseWhenComparedWithNull() {
        Course course = new Course("COMP1001");

        assertNotEquals(null, course);
    }

    /**
     * Tests that a Course is not equal to an object of another type.
     */
    @Test
    void equalsShouldReturnFalseWhenComparedWithDifferentObjectType() {
        Course course = new Course("COMP1001");

        assertNotEquals("COMP1001", course);
    }

    /**
     * Tests that equal Course objects have the same hash code.
     */
    @Test
    void hashCodeShouldBeSameForEqualCourses() {
        Course courseOne = new Course("COMP1001");
        Course courseTwo = new Course("COMP1001");

        assertEquals(courseOne.hashCode(), courseTwo.hashCode());
    }

    /**
     * Tests that toString returns the course code.
     */
    @Test
    void toStringShouldReturnCourseCode() {
        Course course = new Course("COMP1001");

        assertEquals("COMP1001", course.toString());
    }
}
