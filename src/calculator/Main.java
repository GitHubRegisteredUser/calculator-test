package calculator;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("Введите данные");
        System.out.print("-> ");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine().replaceAll("\\s", "");
        try {
            System.out.println(calc(input));
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    public static String calc(String input) {
        String[] expression = input.split("(?=[^a-zA-Z\\d])|(?<=[^a-zA-Z\\d])");
        if (expression.length != 3 || !checkAll(expression)) {
            throw new RuntimeException("Данные введены некорректно");
        }
        boolean isRoman = false;
        if (checkRoman(expression)) {
            expression[0] = findArabic(expression[0]);
            expression[2] = findArabic(expression[2]);
            isRoman = true;
        }
        int num1 = Integer.parseInt(expression[0]);
        String operator = expression[1];
        int num2 = Integer.parseInt(expression[2]);
        String result;
        result = switch (operator) {
            case "+" -> Integer.toString(num1 + num2);
            case "-" -> Integer.toString(num1 - num2);
            case "*" -> Integer.toString(num1 * num2);
            case "/" -> Integer.toString(num1 / num2);
            default -> throw new RuntimeException("Данные введены некорректно");
        };
        if (isRoman) {
            result = arabicToRoman(result);
        }
        return result;
    }

    static String findArabic(String s) {
        String result = "check";
        for (RomanNumerals romanNumeral : RomanNumerals.values()) {
            if (romanNumeral.name().equals(s)) {
                result = romanNumeral.getEquivalent();
            }
        }
        return result;
    }

    static String arabicToRoman(String s) {
        int n = Integer.parseInt(s);
        if (n <= 0) {
            throw new RuntimeException("Данные введены некорректно");
        }
        int[] arabic = {100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] roman = {"C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < arabic.length; i++) {
            while (n >= arabic[i]) {
                n = n - arabic[i];
                result.append(roman[i]);
            }
        }
        return result.toString();
    }

    static boolean checkRoman(String[] array) {
        return !findArabic(array[0]).equals("check")
                && !findArabic(array[2]).equals("check");
    }

    static boolean checkAll(String[] array) {
        return (array[0].matches("[1-9]|10") && array[2].matches("[1-9]|10"))
                || (checkRoman(array));
    }

}
