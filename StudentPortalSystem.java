import java.sql.*;
import java.util.Scanner;

public class StudentPortalSystem {

    public static void main(String[] args) {
        try {
            // Database connection parameters
            String url = "jdbc:mysql://localhost:3306/student_portal1";
            String username = "root";
            String password = "0011";

            // Establish the database connection
            Connection connection = DriverManager.getConnection(url, username, password);

            // Create a Scanner for user input
            Scanner scanner = new Scanner(System.in);

            while (true) {
                displayMenu();
                System.out.print("Enter your choice: ");

                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                switch (choice) {
                    case 1:
                        // Add Records
                        addStudentRecord(connection, scanner);
                        break;
                    case 2:
                        // Display Records
                        displayStudentRecords(connection);
                        break;
                    case 3:
                        // Update Records
                        updateStudentRecord(connection, scanner);
                        break;
                    case 4:
                        // Delete Records
                        deleteStudentRecord(connection, scanner);
                        break;
                    case 5:
                        // Search Records
                        searchStudentRecords(connection, scanner);
                        break;
                    case 6:
                        // Exit
                        connection.close();
                        scanner.close();
                        System.out.println("Exiting Student Portal System.");
                        System.exit(0);
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



   private static void displayMenu() {
        System.out.println("++***********************************++");
        System.out.println("||   Student Portal System (Admin)   ||" );
        System.out.println("++***********************************++");
        System.out.println("|| 1. Add Records                    ||");
        System.out.println("|| 2. Display Records                ||");
        System.out.println("|| 3. Update Records                 ||");
        System.out.println("|| 4. Delete Records                 ||");
        System.out.println("|| 5. Search Records                 ||");
        System.out.println("|| 6. Exit                           ||");
        System.out.println("++***********************************++");
}

    private static void addStudentRecord(Connection connection, Scanner scanner) throws SQLException {
        System.out.println("\nEnter Student Details:");

        System.out.print("Student Name: ");
        String studentName = scanner.nextLine();

        System.out.print("Branch: ");
        String branch = scanner.nextLine();

        System.out.print("Father's Name: ");
        String fatherName = scanner.nextLine();

        System.out.print("Mother's Name: ");
        String motherName = scanner.nextLine();

        System.out.print("Address: ");
        String address = scanner.nextLine();

        System.out.print("Date Of Birth (YYYY-MM-DD): ");
        String dateOfBirth = scanner.nextLine();

        System.out.print("Age: ");
        int age = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        System.out.print("Telephone No: ");
        String telephoneNo = scanner.nextLine();

        // Insert the student record into the database
        String insertQuery = "INSERT INTO students " +
                "(student_name, branch, father_name, mother_name, address, date_of_birth, age, telephone_no) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
        preparedStatement.setString(1, studentName);
        preparedStatement.setString(2, branch);
        preparedStatement.setString(3, fatherName);
        preparedStatement.setString(4, motherName);
        preparedStatement.setString(5, address);
        preparedStatement.setString(6, dateOfBirth);
        preparedStatement.setInt(7, age);
        preparedStatement.setString(8, telephoneNo);

        int rowsAffected = preparedStatement.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Student record added successfully.");
        } else {
            System.out.println("Failed to add student record.");
        }
    }

    private static void displayStudentRecords(Connection connection) throws SQLException {
        // Retrieve and display all student records
        String selectQuery = "SELECT * FROM students";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(selectQuery);

        System.out.println("+-------------------------------------------------------------------------------------------------------------------------------------------------------------------+");
        System.out.printf("| %-5s | %-20s | %-10s | %-20s | %-20s | %-30s | %-12s | %-4s | %-15s |\n", "ID", "Student Name", "Branch", "Father's Name", "Mother's Name", "Address", "Date of Birth", "Age", "Telephone No");
        System.out.println("+-------------------------------------------------------------------------------------------------------------------------------------------------------------------+");

        while (resultSet.next()) {
            int studentId = resultSet.getInt("student_id");
            String studentName = resultSet.getString("student_name");
            String branch = resultSet.getString("branch");
            String fatherName = resultSet.getString("father_name");
            String motherName = resultSet.getString("mother_name");
            String address = resultSet.getString("address");
            String dateOfBirth = resultSet.getString("date_of_birth");
            int age = resultSet.getInt("age");
            String telephoneNo = resultSet.getString("telephone_no");

            System.out.printf("| %-5d | %-20s | %-10s | %-20s | %-20s | %-30s | %-12s  | %-4s | %-15s |\n", studentId, studentName, branch, fatherName, motherName, address, dateOfBirth, age, telephoneNo);
        }

        System.out.println("+-------------------------------------------------------------------------------------------------------------------------------------------------------------------+");
        resultSet.close();
    }

    private static void updateStudentRecord(Connection connection, Scanner scanner) throws SQLException {
    System.out.print("Enter Student ID to update: ");
    int studentId = scanner.nextInt();
    scanner.nextLine(); // Consume the newline character

    // Check if the student record exists
    String checkQuery = "SELECT * FROM students WHERE student_id = ?";
    PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
    checkStatement.setInt(1, studentId);
    ResultSet checkResultSet = checkStatement.executeQuery();

    if (checkResultSet.next()) {
        System.out.println("Enter updated Student Details:");

        System.out.print("Student Name: ");
        String studentName = scanner.nextLine();

        System.out.print("Branch: ");
        String branch = scanner.nextLine();

        System.out.print("Father's Name: ");
        String fatherName = scanner.nextLine();

        System.out.print("Mother's Name: ");
        String motherName = scanner.nextLine();

        System.out.print("Address: ");
        String address = scanner.nextLine();

        System.out.print("Date Of Birth (YYYY-MM-DD): ");
        String dateOfBirth = scanner.nextLine();

        System.out.print("Age: ");
        int age = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        System.out.print("Telephone No: ");
        String telephoneNo = scanner.nextLine();

        // Update the student record in the database
        String updateQuery = "UPDATE students " +
                "SET student_name = ?, branch = ?, father_name = ?, mother_name = ?, address = ?, date_of_birth = ?, age = ?, telephone_no = ? " +
                "WHERE student_id = ?";
        PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
        updateStatement.setString(1, studentName);
        updateStatement.setString(2, branch);
        updateStatement.setString(3, fatherName);
        updateStatement.setString(4, motherName);
        updateStatement.setString(5, address);
        updateStatement.setString(6, dateOfBirth);
        updateStatement.setInt(7, age);
        updateStatement.setString(8, telephoneNo);
        updateStatement.setInt(9, studentId);

        int rowsAffected = updateStatement.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Student record updated successfully.");
        } else {
            System.out.println("Failed to update student record.");
        }
    } else {
        System.out.println("Student with ID " + studentId + " not found.");
    }
    checkResultSet.close();
}


    private static void deleteStudentRecord(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter Student ID to delete: ");
        int studentId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        // Check if the student record exists
        String checkQuery = "SELECT * FROM students WHERE student_id = ?";
        PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
        checkStatement.setInt(1, studentId);
        ResultSet checkResultSet = checkStatement.executeQuery();

        if (checkResultSet.next()) {
            // Delete the student record from the database
            String deleteQuery = "DELETE FROM students WHERE student_id = ?";
            PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery);
            deleteStatement.setInt(1, studentId);

            int rowsAffected = deleteStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Student record deleted successfully.");
            } else {
                System.out.println("Failed to delete student record.");
            }
        } else {
            System.out.println("Student with ID " + studentId + " not found.");
        }
        checkResultSet.close();
    }

 private static void searchStudentRecords(Connection connection, Scanner scanner) throws SQLException {
    System.out.print("Enter Student Name to search: ");
    String studentName = scanner.nextLine();

    // Search for student records by name
    String searchQuery = "SELECT * FROM students WHERE student_name LIKE ?";
    PreparedStatement searchStatement = connection.prepareStatement(searchQuery);
    searchStatement.setString(1, "%" + studentName + "%");
    ResultSet searchResultSet = searchStatement.executeQuery();

    System.out.println("+-------------------------------------------------------------------------------------------------------------------------------------------------------------------+");
    System.out.printf("| %-5s | %-20s | %-10s | %-20s | %-20s | %-30s | %-12s | %-4s | %-15s |\n", "ID", "Student Name", "Branch", "Father's Name", "Mother's Name", "Address", "Date of Birth", "Age", "Telephone No");
    System.out.println("+-------------------------------------------------------------------------------------------------------------------------------------------------------------------+");

    boolean foundRecords = false; // Flag to track if records were found

    while (searchResultSet.next()) {
        foundRecords = true; // Set the flag to true when records are found

        int studentId = searchResultSet.getInt("student_id");
        String branch = searchResultSet.getString("branch");
        String fatherName = searchResultSet.getString("father_name");
        String motherName = searchResultSet.getString("mother_name");
        String address = searchResultSet.getString("address");
        String dateOfBirth = searchResultSet.getString("date_of_birth");
        int age = searchResultSet.getInt("age");
        String telephoneNo = searchResultSet.getString("telephone_no");

        System.out.printf("| %-5d | %-20s | %-10s | %-20s | %-20s | %-30s | %-12s  | %-4s | %-15s |\n", studentId, studentName, branch, fatherName, motherName, address, dateOfBirth, age, telephoneNo);
    }

    System.out.println("+-------------------------------------------------------------------------------------------------------------------------------------------------------------------+");

    if (!foundRecords) { // Check the flag to determine if any records were found
        System.out.println("No matching records found for Student Name: " + studentName);
    }

    searchResultSet.close();
}
}

