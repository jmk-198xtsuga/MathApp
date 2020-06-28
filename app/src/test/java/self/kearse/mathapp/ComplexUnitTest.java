package self.kearse.mathapp;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit testing for the Complex classes
 *
 * @author Justin Kearse
 */
public class ComplexUnitTest {

    /** The allowed tolerance for computational error (when checking equality). */
    private static double TOLERANCE = 1E-13;

    /**
     * Tests for the constructor methods.
     */
    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    public class constructorTests {
        Complex<Double> onePolar, oneCartesian;
        Complex<Double> fortytwoPolar, fortytwoCartesian;
        Complex<Double> negativeiPolar, negativeiCartesian;
        @BeforeAll
        public void sampleConstructions() {
            onePolar = new ComplexDoublePolar(0d, 1d );
            fortytwoPolar = new ComplexDoublePolar( 0d, 42d );
            negativeiPolar = new ComplexDoublePolar( -(Math.PI/2), 1d );
            oneCartesian = new ComplexDoubleCartesian( 1d, 0d );
            fortytwoCartesian = new ComplexDoubleCartesian( 42d, 0d );
            negativeiCartesian = new ComplexDoubleCartesian( 0d, -1d );
        }
    }

    @Test
    public void comparisonTest() {
        assertEquals(0d, new Double(0));
        assertEquals( new Double(0d), 0);
        assertEquals( 0d, (Number) new Double(0));
    }

    @Nested
    public class ExponentialTests {
        @Test
        public void logarithmTest() {
            assertEquals(new ComplexDoubleCartesian(Math.log(42d), 3d),
                    Complex.Log(new ComplexDoublePolar(3d,42d)));
        }
        @Test
        public void exponentTest() {
            assertEquals(new ComplexDoublePolar(Math.PI/2d, 1d),
                    Complex.Exp(new ComplexDoublePolar(Math.PI/2d,Math.PI/2d)));
        }
        @Test
        public void inverseTest() {
            ComplexDoubleCartesian check = new ComplexDoubleCartesian(76d, -0.78d);
            assertEquals(check, Complex.Log(Complex.Exp(check)));
            Complex<Double> image = Complex.Exp(Complex.Log(check));
            Double realError = Math.abs(check.real() - image.real());
            Double imaginaryError = Math.abs(check.imaginary() - image.imaginary());
            assertTrue( (realError < TOLERANCE) && (imaginaryError < TOLERANCE),
                    String.format("Maximum error: %s, real: %s, imaginary: %s",
                            TOLERANCE, realError, imaginaryError));
        }
    }
}