import java.awt.*;
import java.math.BigDecimal;
import javax.swing.*;

public class CalculatorGUI {

    private static JTextArea history = new JTextArea();

    public static void main(String[] args) {

        JFrame frame = new JFrame("Enhanced Calculator");
        frame.setSize(700, 450);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTabbedPane tabs = new JTabbedPane();
        tabs.add("Basic", basicPanel());
        tabs.add("Scientific", scientificPanel());
        tabs.add("Unit Conversion", unitPanel());

        history.setEditable(false);
        JScrollPane historyPane = new JScrollPane(history);
        historyPane.setPreferredSize(new Dimension(250, 0));

        frame.setLayout(new BorderLayout());
        frame.add(tabs, BorderLayout.CENTER);
        frame.add(historyPane, BorderLayout.EAST);

        frame.setVisible(true);
    }

    // ================= BASIC PANEL =================
    private static JPanel basicPanel() {
        JPanel p = new JPanel(new GridLayout(6, 2, 10, 10));

        JTextField n1 = new JTextField();
        JTextField n2 = new JTextField();
        JTextField res = new JTextField();
        res.setEditable(false);

        JComboBox<String> ops = new JComboBox<>(new String[]{"+", "-", "*", "/"});
        JButton calc = new JButton("Calculate");

        calc.addActionListener(e -> {
            try {
                BigDecimal a = new BigDecimal(n1.getText());
                BigDecimal b = new BigDecimal(n2.getText());
                String op = ops.getSelectedItem().toString();

                BigDecimal r = switch (op) {
                    case "+" -> CalculatorLogic.add(a, b);
                    case "-" -> CalculatorLogic.subtract(a, b);
                    case "*" -> CalculatorLogic.multiply(a, b);
                    case "/" -> CalculatorLogic.divide(a, b);
                    default -> BigDecimal.ZERO;
                };

                res.setText(r.toString());
                history.append(a + " " + op + " " + b + " = " + r + "\n");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Invalid Input!");
            }
        });

        p.add(new JLabel("Number 1"));
        p.add(n1);
        p.add(new JLabel("Number 2"));
        p.add(n2);
        p.add(new JLabel("Operation"));
        p.add(ops);
        p.add(new JLabel("Result"));
        p.add(res);
        p.add(calc);

        return p;
    }

    // ================= SCIENTIFIC PANEL =================
    private static JPanel scientificPanel() {
        JPanel p = new JPanel(new GridLayout(6, 2, 10, 10));

        JTextField number = new JTextField();
        JTextField exponent = new JTextField();
        JTextField result = new JTextField();
        result.setEditable(false);

        JButton sqrtBtn = new JButton("√");
        JButton powerBtn = new JButton("xʸ");

        sqrtBtn.addActionListener(e -> {
            try {
                BigDecimal n = new BigDecimal(number.getText());
                double r = CalculatorLogic.squareRoot(n);
                result.setText(String.valueOf(r));
                history.append("√" + n + " = " + r + "\n");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Invalid input for Square Root");
            }
        });

        powerBtn.addActionListener(e -> {
            try {
                BigDecimal base = new BigDecimal(number.getText());
                BigDecimal exp = new BigDecimal(exponent.getText());
                double r = CalculatorLogic.power(base, exp);
                result.setText(String.valueOf(r));
                history.append(base + "^" + exp + " = " + r + "\n");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Invalid input for Power");
            }
        });

        p.add(new JLabel("Number"));
        p.add(number);
        p.add(new JLabel("Exponent"));
        p.add(exponent);
        p.add(sqrtBtn);
        p.add(powerBtn);
        p.add(new JLabel("Result"));
        p.add(result);

        return p;
    }

    // ================= UNIT CONVERSION PANEL =================
    private static JPanel unitPanel() {
        JPanel p = new JPanel(new GridLayout(6, 2, 10, 10));

        JTextField input = new JTextField();
        JTextField result = new JTextField();
        result.setEditable(false);

        JComboBox<String> options = new JComboBox<>(new String[]{
                "Celsius → Fahrenheit",
                "Fahrenheit → Celsius",
                "USD → INR",
                "INR → USD"
        });

        JButton convert = new JButton("Convert");

        convert.addActionListener(e -> {
            try {
                BigDecimal val = new BigDecimal(input.getText());
                String opt = options.getSelectedItem().toString();
                BigDecimal r;

                switch (opt) {
                    case "Celsius → Fahrenheit" ->
                            r = CalculatorLogic.celsiusToFahrenheit(val);
                    case "Fahrenheit → Celsius" ->
                            r = CalculatorLogic.fahrenheitToCelsius(val);
                    case "USD → INR" ->
                            r = CalculatorLogic.usdToInr(val);
                    case "INR → USD" ->
                            r = CalculatorLogic.inrToUsd(val);
                    default -> r = BigDecimal.ZERO;
                }

                result.setText(r.toString());
                history.append(opt + " : " + val + " = " + r + "\n");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Invalid Input!");
            }
        });

        p.add(new JLabel("Value"));
        p.add(input);
        p.add(new JLabel("Conversion Type"));
        p.add(options);
        p.add(new JLabel("Result"));
        p.add(result);
        p.add(convert);

        return p;
    }
}
