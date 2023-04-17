import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class ZahlAlsTextTest {
    private static Stream<Arguments> numberAsTextTestProvider() {
        return Stream.of(
                Arguments.of(1, "ONE"),
                Arguments.of(2, "TWO"),
                Arguments.of(21, "TWO ONE"),
                Arguments.of(123, "ONE TWO THREE")
        );
    }

    @ParameterizedTest
    @MethodSource("numberAsTextTestProvider")
    public void numberAsTextTest(int number, String expectedValue) {
        String result = ZahlAlsText.numberAsText(number);
        Assertions.assertEquals(expectedValue, result);
    }
}
