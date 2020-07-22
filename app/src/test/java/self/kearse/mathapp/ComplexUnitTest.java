package self.kearse.mathapp;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import java.util.LinkedList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit testing for the Complex classes
 *
 * @author Justin Kearse
 */
public class ComplexUnitTest {

    /** The allowed tolerance for computational error (when checking equality). */
    private static double TOLERANCE = 1E-13;
    static Complex<Double> onePolar, oneCartesian;
    static Complex<Double> fortytwoPolar, fortytwoCartesian;
    static Complex<Double> negativeiPolar, negativeiCartesian;
    static Complex<Double> negativeThree;

    @BeforeAll
    public static void sampleConstructions() {
        onePolar = new ComplexDoublePolar(0d, 1d );
        fortytwoPolar = new ComplexDoublePolar( 0d, 42d );
        negativeiPolar = new ComplexDoublePolar( -(Math.PI/2), 1d );
        oneCartesian = new ComplexDoubleCartesian( 1d, 0d );
        fortytwoCartesian = new ComplexDoubleCartesian( 42d, 0d );
        negativeiCartesian = new ComplexDoubleCartesian( 0d, -1d );
        negativeThree = new ComplexDoublePolar(Math.PI, 3d);
    }

    @Nested
    public class comparisonTest {
        @Test
        public void equalLesserType() {
            assert(fortytwoCartesian.equals(42d));
            assertTrue(negativeThree.equals(-3d),
                String.format("Expected %s to equal -3", negativeThree.toString()));
        }
        @Test
        public void notEqualCloseValues() {
            assertNotEquals(new ComplexDoubleCartesian(0d, 2.5d),
                    new ComplexDoubleCartesian(0d, 2.4d));
        }
        @Test
        public void notEqualLesserTypeDistinctValues() {
            assertFalse(fortytwoCartesian.equals(4d));
        }
        @Test
        public void notEqualIncomparibleTypes() {
            assertFalse(fortytwoCartesian.equals(
                    new LinkedList<Integer>()) );
        }
        @Test
        public void notEqualToLesserWhenNotReal() {
            ComplexDoubleCartesian notReal = new ComplexDoubleCartesian(3d, 4d);
            assertFalse(notReal.equals(3d), "Equality not based on real value alone");
            assertFalse(notReal.equals(4d), "Equality not based on imaginary value alone");
            assertFalse(notReal.equals(5d), "Equality not based on magnitude alone");
        }
    }

    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @Nested
    public class ArithmeticTests {
        Complex<Double> i, one, mix, zero;
        @BeforeAll
        public void sampleConstructions() {
            i = new ComplexDoubleCartesian(0d, 1d);
            one = new ComplexDoubleCartesian(1d, 0d);
            mix = new ComplexDoubleCartesian(1d, 1d);
            zero = new ComplexDoubleCartesian(0d, 0d);
        }
        @Nested
        public class SubtractTests {
            @Test
            public void subtractSelfReturnsZero() {
                assertEquals(zero, i.subtract(i),
                        String.format("%s - %s = %s", i, i, zero));
                assertEquals(zero, one.subtract(one),
                        String.format("%s - %s = %s", one, one, zero));
                assertEquals(zero, mix.subtract(mix),
                        String.format("%s - %s = %s", mix, mix, zero));
            }
            @Test
            public void subtractingComponents() {
                assertEquals(i, mix.subtract(one));
                assertEquals(one, mix.subtract(i));
                assertEquals(new ComplexDoubleCartesian(-1d, 0d),
                        i.subtract(mix));
            }
            @Test
            public void nullNotAllowed() {
                try {
                    mix.divide(null);
                    fail();
                } catch (NullPointerException e) {
                    assertTrue(e.getMessage().length() > 0);
                }
            }
        }
        @Nested
        public class DivisionTests {
            @Test
            public void divideBySelfReturnsOne() {
                assertEquals(one, i.divide(i),
                        String.format("%s / %s = %s", i, i, one));
                assertEquals(one, one.divide(one),
                        String.format("%s / %s = %s", one, one, one));
                assertEquals(one, mix.divide(mix),
                        String.format("%s / %s = %s", mix, mix, one));
            }
            @Test
            public void divideByZeroIsIllegal() {
                try {
                    mix.divide(new ComplexDoublePolar(2d, 0d));
                    fail();
                } catch (ArithmeticException e) {
                    assertTrue(e.getMessage().length() > 0);
                }
            }
            @Test
            public void nullNotAllowed() {
                try {
                    mix.divide(null);
                    fail();
                } catch (NullPointerException e) {
                    assertTrue(e.getMessage().length() > 0);
                }
            }
        }
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
        @Nested
        @TestInstance(TestInstance.Lifecycle.PER_CLASS)
        public class powTest {
            Complex<Double> i;
            @BeforeAll
            public void setup() {
                i = new ComplexDoubleCartesian(0d, 1d);
            }
            @Test
            public void iSquared() {
                assertEquals(new ComplexDoubleCartesian(-1d, 0d),
                        i.pow(new ComplexDoubleCartesian(2d,0d)),
                        "i squared is -1");
            }
            @Test
            public void iFourth() {
                assertEquals(new ComplexDoubleCartesian(1d, 0d),
                        i.pow(new ComplexDoubleCartesian(4d,0d)),
                        "i^4 is 1");
            }
        }
        @Nested
        public class rootTest {
            @Test
            public void squareRootNegativeOne() {
                assertEquals(new ComplexDoubleCartesian(0d, 1d),
                        Complex.root(new ComplexDoubleCartesian(-1d, 0d), 2),
                        "The principal square root of -1 is i");
            }
            @Test
            public void cubeRootNegativei() {
                /* I know, this isn't the greatest test... */
                assertNotEquals(new ComplexDoubleCartesian(0d, 1d),
                        Complex.root(new ComplexDoubleCartesian(0d, -1d), 3),
                        "The principal cube root of -i is not i");
            }
            @Test
            public void fourthRootNegativeSixteen() {
                assertEquals(new ComplexDoublePolar(Math.PI / 4d, 2d),
                        Complex.root(new ComplexDoubleCartesian(-16d, 0d), 4),
                        "The fourth root of -16 is 2e^(i*pi/4)");
            }
        }
        @Nested
        @TestInstance(TestInstance.Lifecycle.PER_CLASS)
        public class rootsTest {
            int quarterPiCount = 0;
            Complex<Double> negativeOne;
            List<Complex<Double>> list;
            @BeforeAll
            public void generateRoots() {
                Complex<Double> quarterPi = new ComplexDoublePolar(Math.PI / 4d, 1d);
                negativeOne = new ComplexDoubleCartesian(-1d, 0d);
                list = Complex.roots(negativeOne, 4);
                for (Complex<Double> each : list) {
                    if (quarterPi.equals(each)) {
                        quarterPiCount += 1;
                    }
                }
            }
            @Test
            public void hasCorrectPrincipal() {
                assertTrue(quarterPiCount > 0, "roots List contains pi/4");
            }
            @Test
            public void hasUniqueRoots() {
                assertEquals(1, quarterPiCount, "roots List contains pi/4 once");
            }
            @Test
            public void hasCorrectCount() {
                assertEquals(4, list.size(), "There should be 4 fourth roots");
            }
            @Test
            public void correctRoots() {
                boolean badRoot = false;
                for (Complex<Double> each : list) {
                    assertEquals(negativeOne, each.pow(new ComplexDoubleCartesian(4d, 0d)),
                            String.format("%s not a root of %s", each, negativeOne));
                }
            }
        }
    }
}