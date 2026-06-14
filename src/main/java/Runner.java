/**
* File: Runner.java
* Description: Acts as the entry point for the StudyPlanner application. Collects user input, coordinates graph'
 * construction and study planning, and displays the resulting study plan.
* Author: Gabrielle Booth
* Student ID: a3145294
* Email ID: gabrielle.booth@student.adelaide.edu.au
* AI Tool Used: Y
* This is my own work as defined by
*    the University's Academic Integrity Policy.
**/

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * Entry point for the StudyPlanner application.
 *
 * The Runner collects user input, reads the degree file,
 * creates the course graph, generates the study plan,
 * and displays the result.
 */
public class Runner {

    /**
     * Runs the StudyPlanner application.
     *
     * @param args command-line arguments, not currently used
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("Enter degree file name: ");
            String fileName = scanner.nextLine();

            System.out.print("Enter maximum courses per study period: ");
            int maxCoursesPerPeriod = Integer.parseInt(scanner.nextLine());

            DegreeFileReader reader = new DegreeFileReader();
            CourseGraph graph = reader.readFile(fileName);

            StudyPlanner planner = new StudyPlanner();
            List<List<Course>> studyPlan =
                    planner.createStudyPlan(graph, maxCoursesPerPeriod);

            printStudyPlan(studyPlan);

        } catch (NumberFormatException exception) {
            System.out.println("Error: maximum courses must be a whole number.");
        } catch (IllegalArgumentException | IllegalStateException exception) {
            System.out.println("Error: " + exception.getMessage());
        } catch (IOException exception) {
            System.out.println("Error: file could not be read.");
        }
    }

    /**
     * Prints the study plan grouped by study period.
     *
     * @param studyPlan the study plan to print
     */
    private static void printStudyPlan(List<List<Course>> studyPlan) {
        for (int i = 0; i < studyPlan.size(); i++) {
            System.out.println("Study Period " + (i + 1) + ":");

            for (Course course : studyPlan.get(i)) {
                System.out.println("- " + course);
            }

            System.out.println();
        }
    }
}