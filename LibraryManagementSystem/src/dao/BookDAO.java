package dao;

import java.sql.*;
import java.time.LocalDate;
import util.DBConnection;
import util.FineCalculator;

public class BookDAO {

    public void addBook(String title, int authorId) {

        String sql = "INSERT INTO Book(title, author_id, available) VALUES(?,?,1)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, title);
            pstmt.setInt(2, authorId);
            pstmt.executeUpdate();

            System.out.println("Book added successfully.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void viewBooks() {

        String sql = "SELECT * FROM Book";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                System.out.println(
                    rs.getInt("id") + " | " +
                    rs.getString("title") + " | Available: " +
                    rs.getInt("available")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void issueBook(int bookId) {

        String sql = "UPDATE Book SET available=0, due_date=? WHERE id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            LocalDate dueDate = LocalDate.now().plusDays(7);
            pstmt.setString(1, dueDate.toString());
            pstmt.setInt(2, bookId);
            pstmt.executeUpdate();

            System.out.println("Book issued. Due date: " + dueDate);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void returnBook(int bookId) {

        String selectSql = "SELECT due_date FROM Book WHERE id=?";
        String updateSql = "UPDATE Book SET available=1, due_date=NULL WHERE id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement selectStmt = conn.prepareStatement(selectSql);
             PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {

            selectStmt.setInt(1, bookId);
            ResultSet rs = selectStmt.executeQuery();

            if (rs.next()) {

                String dueDateStr = rs.getString("due_date");
                LocalDate dueDate = LocalDate.parse(dueDateStr);
                LocalDate returnDate = LocalDate.now();

                long fine = FineCalculator.calculateFine(dueDate, returnDate);

                updateStmt.setInt(1, bookId);
                updateStmt.executeUpdate();

                System.out.println("Book returned.");
                System.out.println("Fine: " + fine);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}