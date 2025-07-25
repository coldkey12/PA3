/*
 * COP 3330 Summer 2025
 * Programming Assignment 3
 * Student Name: Yevgeniy An
 *
 */

import java.util.ArrayList;
import java.io.PrintWriter;
import java.io.FileNotFoundException;

public class JSONUtility {

    // TODO: Complete the JSONUtility class

    /**
     * Exports a list of Student objects into a JSON file with the given file name.
     * @param students  the list of Student objects to export
     * @param fileName  the name of the file to write the JSON data to
     */
    public static void exportJSON(GenericList<Student> students, String fileName) {
        try (PrintWriter out = new PrintWriter(fileName)) {  // Try-with-resources for auto-closing
            out.println("[");  // Start JSON array

            // Process each student in the list
            for (int i = 0; i < students.size(); i++) {
                Student s = students.get(i);  // Get current student
                String studentJson = buildStudentJson(s);  // Convert to JSON
                out.print(studentJson);  // Write JSON to file

                // Handle comma separators between objects
                if (i < students.size() - 1) {  // Not last student
                    out.println(",");  // Add comma separator
                } else {  // Last student in list
                    out.println();  // No comma, just newline
                }
            }
            out.println("]");  // Close JSON array
        } catch (FileNotFoundException e) {  // Handle file errors
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    /**
     * Builds a JSON string for a single student.
     * @param student  the Student object to convert to JSON
     * @return         a formatted JSON string
     */
    private static String buildStudentJson(Student student) {
        ArrayList<String> pairs = new ArrayList<>();  // Store key-value pairs

        // Add core student properties
        pairs.add("\"id\": \"" + student.getID() + "\"");  // Student ID
        pairs.add("\"name\": \"" + student.getName() + "\"");  // Full name

        // Process course enrollments
        if (student.getCourseList().size() > 0) {  // Has enrolled courses
            ArrayList<String> courseJsons = new ArrayList<>();  // Store course objects

            // Build JSON for each course
            for (int j = 0; j < student.getCourseList().size(); j++) {
                Course c = student.getCourseList().get(j);  // Get course
                // Create course JSON object
                String courseJson = "            { \"code\": \"" + c.getCode() + "\", \"title\": \"" + c.getTitle() + "\" }";
                courseJsons.add(courseJson);  // Add to list
            }
            // Combine courses into JSON array
            String coursesString = "[\n" + String.join(",\n", courseJsons) + "\n        ]";
            pairs.add("\"enrolledCourses\": " + coursesString);  // Add course array property
        } else {  // No courses enrolled
            pairs.add("\"enrolledCourses\": null");  // Null enrollment indicator
        }

        // Combine all properties into final JSON object
        return "    {\n        " + String.join(",\n        ", pairs) + "\n    }";
    }
}