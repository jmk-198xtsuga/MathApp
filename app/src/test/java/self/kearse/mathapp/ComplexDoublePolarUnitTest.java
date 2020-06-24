package self.kearse.mathapp;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.ParameterizedTest.*;
import static org.junit.jupiter.params.provider.ValueSource.*;

/**
 * Unit testing for the ComplexDoublePolar class
 *
 * @author Justin Kearse
 */
public class ComplexDoublePolarUnitTest {
    /** The allowed tolerance for computational error (when checking equality). */
    private static double TOLERANCE = 1E-16;

    /**
     * Tests for the constructor methods.
     */
    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    public class ConstructorTests {
        Complex<Double> onePolar;
        Complex<Double> fortytwoPolar;
        Complex<Double> negativeiPolar;

        @BeforeAll
        public void sampleConstructions() {
            onePolar = new ComplexDoublePolar(0d, 1d);
            fortytwoPolar = new ComplexDoublePolar(0d, 42d);
            negativeiPolar = new ComplexDoublePolar(-(Math.PI / 2), 1d);
        }

        /**
         * Tests to make sure that the constructors properly return a new Object of
         * the appropriate class.
         */
        @Nested
        public class InstantiationTests {
            @Test
            void PolarConstructorNotNull() {
                assertNotNull(onePolar, "constructor does not return null with legal parameters");
            }

            @Test
            void PolarConstructorReturnsComplex() {
                assertTrue(onePolar instanceof Complex, "Constructor returns an instance of Complex");
            }

            @Test
            void CDPConstructorReturnsCDP() {
                assertTrue(onePolar instanceof ComplexDoublePolar,
                        "CDP Constructor returns an instance of ComplexDoublePolar");
            }
        }

        /**
         * Tests to ensure that the constructors recognize and use the parameters given.
         */
        @Nested
        public class ParameterTests {
            @Test
            void CDPParametersArgument() {
                assertEquals(new Double(0d), onePolar.Argument());
                assertEquals(new Double(-Math.PI / 2), negativeiPolar.Argument());
            }

            @Test
            void CDPParametersModulus() {
                assertEquals(new Double(1d), onePolar.modulus());
                assertEquals(new Double(42d), fortytwoPolar.modulus());
            }
        }
    }
    /**
     * Tests to ensure that the Argument method behaves appropriately.
     */
    @Nested
    public class ArgumentTests {
        @Test
        void LegalArgumentRetained() {
            Complex<Double> negativeiPolar = new ComplexDoublePolar(-(Math.PI / 2), 1d);
            assertEquals(new Double(-Math.PI / 2), negativeiPolar.Argument());
        }
        @Test
        void NegativePiWraps() {
            ComplexDoublePolar negativePi = new ComplexDoublePolar(-Math.PI, 1d);
            double Argument = negativePi.Argument();
            assertNotEquals(-Math.PI, Argument);
            assertEquals(Math.PI, Argument);
        }
        @Test
        void BelowBoundsCheck() {
            ComplexDoublePolar negative = new ComplexDoublePolar(-1000d, 1d);
            double Argument = negative.Argument();
            assertTrue(-Math.PI < Argument,
                    "Minimum bound of -Pi (exclusive)");
            assertFalse(Math.PI < Argument,
                    "Maximum bound of +Pi (exclusive)");
        }
        @Test
        void AboveBoundsCheck() {
            ComplexDoublePolar positive = new ComplexDoublePolar(1000d, 1d);
            double Argument = positive.Argument();
            assertTrue(-Math.PI < Argument,
                    "Minimum bound of -Pi (exclusive)");
            assertFalse(Math.PI < Argument,
                    "Maximum bound of +Pi (exclusive)");
        }
    }

    @Test
    void ModulusTest() {
        Complex<Double> fortytwoPolar = new ComplexDoublePolar(0d, 42d);
        assertEquals(new Double(42d), fortytwoPolar.modulus(),
                "Modulus of a real number is itself");
        assertEquals(new Double(2d),
                (new ComplexDoublePolar(Math.sqrt(5d), 2d)).modulus(),
                "Modulus of radial notation is simply the modulus");
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    public class DerivativesTests {
        Complex<Double> fortytwoPolar;
        Complex<Double> negativeiPolar;
        Complex<Double> zero;
        Complex<Double> one;

        @BeforeAll
        public void sampleConstructions() {
            fortytwoPolar = new ComplexDoublePolar(0d, 42d);
            negativeiPolar = new ComplexDoublePolar(-Math.PI / 2, 1d);
            zero = new ComplexDoublePolar(0d, 0d);
            one = new ComplexDoublePolar(0d, 1d);
        }

        @Test
        void RealTest() {
            assertEquals(new Double(42d), fortytwoPolar.real());
            assertTrue(Math.abs(negativeiPolar.real() - 0d) < TOLERANCE);
        }

        @Test
        void ImaginaryTest() {
            assertEquals(new Double(0d), fortytwoPolar.imaginary());
            assertEquals(new Double(-1d), negativeiPolar.imaginary());
        }

        @Test
        void AddInverseTest() {
            //TODO: revert to primitive zero if possible
            assertEquals(zero, fortytwoPolar.add(fortytwoPolar.addInverse()));
            assertEquals(zero, negativeiPolar.add(negativeiPolar.addInverse()));
        }

        @Test
        void MultiplyInverseTest() {
            //TODO: revert to primitive one if possible
            assertEquals(one, fortytwoPolar.multiply(fortytwoPolar.multInverse()));
            assertEquals(one, negativeiPolar.multiply(negativeiPolar.multInverse()));
        }

        @Test
        void ComplementTest() {
            //TODO: revert to primitive squares of modulus if possible
            Double fortytwoModulus = fortytwoPolar.modulus();
            Double negativeiModulus = negativeiPolar.modulus();
            assertEquals(new ComplexDoublePolar(0d, fortytwoModulus * fortytwoModulus),
                    fortytwoPolar.multiply(fortytwoPolar.complement()));
            assertEquals(new ComplexDoublePolar(0d, negativeiModulus * negativeiModulus),
                    negativeiPolar.multiply(negativeiPolar.complement()));
        }
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    public class EqualityTests {
        Complex<Double> fortytwoPolar;
        Complex<Double> anotherFortyTwoPolar;
        Double fortytwoDouble;

        @BeforeAll
        public void sampleConstructions() {
            fortytwoPolar = new ComplexDoublePolar(0d, 42d);
            anotherFortyTwoPolar = new ComplexDoublePolar(0d, 42d);
            fortytwoDouble = new Double(42d);
        }

        @Test
        public void identityCheck() {
            assertEquals(fortytwoPolar, fortytwoPolar);
        }

        @Test
        public void equalFields() {
            assertEquals(anotherFortyTwoPolar, fortytwoPolar);
            assertEquals(fortytwoPolar, anotherFortyTwoPolar);
        }

        @Test
        public void cartesianForm() {
            Complex<Double> fortytwoCartesian = new ComplexDoubleCartesian(42d, 0d);
            assertEquals(fortytwoPolar, fortytwoCartesian);
        }

        @Test
        public void lesserTypes() {
            assertEquals(fortytwoPolar, new Double(42d));
            assertEquals(fortytwoPolar, 42d);
        }
    }

    @Nested
    public class MathTests {
        @Test
        public void addTests() {
            assertEquals(new ComplexDoubleCartesian(2d, 2d),
                    (new ComplexDoublePolar(0d,2d)).add(
                            new ComplexDoublePolar(Math.PI/2d, 2d)
                    ));
        }
        @Test
        public void multiplyTests() {
            assertEquals(new ComplexDoublePolar(4d,6d),
                    (new ComplexDoublePolar(1d, 3d)).multiply(
                            new ComplexDoublePolar(3d, 2d)
                    ));
        }
        @Test
        public void LogarithmTests() {
            assertEquals(new ComplexDoubleCartesian(Math.log(42d), 3d),
                    new ComplexDoublePolar(3d,42d).Log());
        }
    }
}