import java.math.BigDecimal;
import java.math.MathContext;
import java.util.InputMismatchException;
import java.util.Scanner;

public class EnhancedCalculator {

    private static final Scanner sc = new Scanner(System.in);
    private static final MathContext MC = MathContext.DECIMAL64;

    public static void main(String[] args) {

        boolean running = true;

        while (running) {
            System.out.println("\n===== ENHANCED CONSOLE CALCULATOR =====");
            System.out.println("1. Basic Arithmetic");
            System.out.println("2. Scientific Calculator");
            System.out.println("3. Unit Conversion");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");

            try {
                int choice = sc.nextInt();
                sc.nextLine(); // clear buffer

                switch (choice) {
                    case 1 -> basicArithmetic();
                    case 2 -> scientificCalculator();
                    case 3 -> unitConversion();
                    case 4 -> {
                        running = false;
                        System.out.println("Exiting Calculator...");
                    }
                    default -> System.out.println("Invalid option!");
                }
            } catch (InputMismatchException e) {
                System.out.println("❌ Invalid input! Please enter a number.");
                sc.nextLine();
            }
        }
    }

    // ---------------- BASIC ARITHMETIC ----------------
    private static void basicArithmetic() {
        try {
            BigDecimal a = readBigDecimal("Enter first number: ");
            BigDecimal b = readBigDecimal("Enter second number: ");

            System.out.println("1. Add");
            System.out.println("2. Subtract");
            System.out.println("3. Multiply");
            System.out.println("4. Divide");
            System.out.print("Choose operation: ");

            int op = sc.nextInt();

            switch (op) {
                case 1 -> System.out.println("Result = " + a.add(b));
                case 2 -> System.out.println("Result = " + a.subtract(b));
                case 3 -> System.out.println("Result = " + a.multiply(b));
                case 4 -> {
                    if (b.compareTo(BigDecimal.ZERO) == 0)
                        System.out.println("❌ Division by zero not allowed");
                    else
                        System.out.println("Result = " + a.divide(b, MC));
                }
                default -> System.out.println("Invalid operation!");
            }
        } catch (InputMismatchException e) {
            System.out.println("❌ Invalid numeric input");
            sc.nextLine();
        }
    }

    // ---------------- SCIENTIFIC ----------------
    private static void scientificCalculator() {
        System.out.println("1. Square Root");
        System.out.println("2. Power");
        System.out.print("Choose operation: ");

        int choice = sc.nextInt();

        if (choice == 1) {
            BigDecimal num = readBigDecimal("Enter number: ");
            double result = Math.sqrt(num.doubleValue());
            System.out.println("Square Root = " + result);
        } else if (choice == 2) {
            BigDecimal base = readBigDecimal("Enter base: ");
            BigDecimal exp = readBigDecimal("Enter exponent: ");
            double result = Math.pow(base.doubleValue(), exp.doubleValue());
            System.out.println("Result = " + result);
        } else {
            System.out.println("Invalid choice!");
        }
    }

    // ---------------- UNIT CONVERSION ----------------
    private static void unitConversion() {
        System.out.println("1. Temperature");
        System.out.println("2. Currency");
        System.out.print("Choose option: ");

        int choice = sc.nextInt();

        if (choice == 1)
            temperatureConversion();
        else if (choice == 2)
            currencyConversion();
        else
            System.out.println("Invalid choice!");
    }

    private static void temperatureConversion() {
        System.out.println("1. Celsius to Fahrenheit");
        System.out.println("2. Fahrenheit to Celsius");
        System.out.print("Choose option: ");

        int choice = sc.nextInt();
        BigDecimal temp = readBigDecimal("Enter temperature: ");

        if (choice == 1) {
            BigDecimal f = temp.multiply(BigDecimal.valueOf(9))
                    .divide(BigDecimal.valueOf(5), MC)
                    .add(BigDecimal.valueOf(32));
            System.out.println("Fahrenheit = " + f);
        } else if (choice == 2) {
            BigDecimal c = temp.subtract(BigDecimal.valueOf(32))
                    .multiply(BigDecimal.valueOf(5))
                    .divide(BigDecimal.valueOf(9), MC);
            System.out.println("Celsius = " + c);
        }
    }

    private static void currencyConversion() {
        final BigDecimal USD_TO_INR = new BigDecimal("83.00");

        System.out.println("1. USD → INR");
        System.out.println("2. INR → USD");
        System.out.print("Choose option: ");

        int choice = sc.nextInt();
        BigDecimal amount = readBigDecimal("Enter amount: ");

        if (choice == 1)
            System.out.println("INR = " + amount.multiply(USD_TO_INR));
        else if (choice == 2)
            System.out.println("USD = " + amount.divide(USD_TO_INR, MC));
    }

    // ---------------- HELPER METHOD ----------------
    private static BigDecimal readBigDecimal(String message) {
        System.out.print(message);
        return new BigDecimal(sc.next());
    }
}

