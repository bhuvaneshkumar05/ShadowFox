package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import util.DBConnection;

public class AuthorDAO {

    public void addAuthor(String name) {

        String sql = "INSERT INTO Author(name) VALUES(?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.executeUpdate();

            System.out.println("Author added successfully.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}