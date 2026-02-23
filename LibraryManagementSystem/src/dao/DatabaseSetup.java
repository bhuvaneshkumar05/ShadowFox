package dao;

import java.sql.Connection;
import java.sql.Statement;
import util.DBConnection;

public class DatabaseSetup {

    public static void createTables() {

        String authorTable = """
            CREATE TABLE IF NOT EXISTS Author (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT NOT NULL
            );
        """;

        String bookTable = """
            CREATE TABLE IF NOT EXISTS Book (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                title TEXT NOT NULL,
                author_id INTEGER,
                available INTEGER DEFAULT 1,
                due_date TEXT,
                FOREIGN KEY (author_id) REFERENCES Author(id)
            );
        """;

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute(authorTable);
            stmt.execute(bookTable);

            System.out.println("Tables created successfully.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}