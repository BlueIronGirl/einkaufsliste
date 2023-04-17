import java.util.StringJoiner;

public class ZahlAlsText {
    public static void main(String[] args) {
        System.out.println(numberAsText(2));
        System.out.println(numberAsText(234));
    }

    static String numberAsText(int number) {
        StringJoiner valueAsText = new StringJoiner(" ");
        final int remainder = number % 10;

        if (number / 10 > 0) {
            valueAsText.add(numberAsText(number / 10));
        }

        String value = switch (remainder) {
            case 1 -> "ONE";
            case 2 -> "TWO";
            case 3 -> "THREE";
            case 4 -> "FOUR";
            case 5 -> "FIVE";
            case 6 -> "SIX";
            case 7 -> "SEVEN";
            case 8 -> "EIGHT";
            case 9 -> "NINE";
            default -> "ZERO";
        };
        valueAsText.add(value);

        return valueAsText.toString();
    }
}
