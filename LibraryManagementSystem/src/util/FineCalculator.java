package util;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class FineCalculator {

    public static long calculateFine(LocalDate dueDate, LocalDate returnDate) {

        long daysLate = ChronoUnit.DAYS.between(dueDate, returnDate);

        if (daysLate > 0) {
            return daysLate * 5;  // 5 currency per day
        }
        return 0;
    }
}