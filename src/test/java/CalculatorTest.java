import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: Администратор
 * Date: 14.11.13
 * Time: 12:27
 * To change this template use File | Settings | File Templates.
 */
public class CalculatorTest {

    private static final double DELTA = 1e-15;

    @Test
    public void testCalculateChaptThre() {
        String expressionToTest = "(6+8)-11";
        double expectedResult = 3;
        double result = Calculator.calculate(expressionToTest);
        assertEquals(expectedResult, result, DELTA);
    }

    @Test
    public void testCalculateOrdinaryExpr() {
        String expressionToTest = "(1 + 1 * 2) / 10^2";
        double expectedResult = 0.03;
        double result = Calculator.calculate(expressionToTest);
        assertEquals(expectedResult, result, DELTA);
    }

    @Test(expected = Throwable.class)
    public void testCalculateOperationNotSupported() {
        String expressionToTest = "1 _)(*&^$%# (1 + 1 * 2) / 10";
        Calculator.calculate(expressionToTest);
    }
}

