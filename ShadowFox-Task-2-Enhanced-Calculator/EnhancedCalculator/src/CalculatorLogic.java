import java.math.BigDecimal;
import java.math.MathContext;

public class CalculatorLogic {

    private static final MathContext MC = MathContext.DECIMAL64;
    private static final BigDecimal USD_TO_INR = new BigDecimal("83.00");

    // -------- BASIC ARITHMETIC --------
    public static BigDecimal add(BigDecimal a, BigDecimal b) {
        return a.add(b);
    }

    public static BigDecimal subtract(BigDecimal a, BigDecimal b) {
        return a.subtract(b);
    }

    public static BigDecimal multiply(BigDecimal a, BigDecimal b) {
        return a.multiply(b);
    }

    public static BigDecimal divide(BigDecimal a, BigDecimal b) {
        if (b.compareTo(BigDecimal.ZERO) == 0)
            throw new ArithmeticException("Division by zero");
        return a.divide(b, MC);
    }

    // -------- SCIENTIFIC --------
    public static double squareRoot(BigDecimal a) {
        if (a.compareTo(BigDecimal.ZERO) < 0)
            throw new ArithmeticException("Negative value");
        return Math.sqrt(a.doubleValue());
    }

    public static double power(BigDecimal base, BigDecimal exp) {
        return Math.pow(base.doubleValue(), exp.doubleValue());
    }

    // -------- TEMPERATURE --------
    public static BigDecimal celsiusToFahrenheit(BigDecimal c) {
        return c.multiply(BigDecimal.valueOf(9))
                .divide(BigDecimal.valueOf(5), MC)
                .add(BigDecimal.valueOf(32));
    }

    public static BigDecimal fahrenheitToCelsius(BigDecimal f) {
        return f.subtract(BigDecimal.valueOf(32))
                .multiply(BigDecimal.valueOf(5))
                .divide(BigDecimal.valueOf(9), MC);
    }

    // -------- CURRENCY --------
    public static BigDecimal usdToInr(BigDecimal usd) {
        return usd.multiply(USD_TO_INR);
    }

    public static BigDecimal inrToUsd(BigDecimal inr) {
        return inr.divide(USD_TO_INR, MC);
    }
}
