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
    public class constructorTests {
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

        @BeforeAll
        public void sampleConstructions() {
            fortytwoPolar = new ComplexDoublePolar(0d, 42d);
            negativeiPolar = new ComplexDoublePolar(-Math.PI / 2, 1d);
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
            //TODO: equality check inspection
            assertEquals(new Double(0d), fortytwoPolar.add(fortytwoPolar.addInverse()));
            assertEquals(new Double(0d), negativeiPolar.add(negativeiPolar.addInverse()));
        }

        @Test
        void MultiplyInverseTest() {
            //TODO: equality check inspection
            assertEquals(new Double(1d), fortytwoPolar.multiply(fortytwoPolar.multInverse()));
            assertEquals(new Double(1d), negativeiPolar.multiply(negativeiPolar.multInverse()));
        }

        @Test
        void ComplementTest() {
            //TODO: equality check inspection
            Double fortytwoModulus = fortytwoPolar.modulus();
            Double negativeiModulus = negativeiPolar.modulus();
            assertEquals(fortytwoModulus * fortytwoModulus,
                    fortytwoPolar.multiply(fortytwoPolar.complement()));
            assertEquals(negativeiModulus * negativeiModulus,
                    negativeiPolar.multiply(negativeiPolar.complement()));
        }
    }

    @Test
    void EqualityTest() {
        Complex<Double> fortytwoPolar = new ComplexDoublePolar(0d, 42d);
        Complex<Double> anotherFortyTwoPolar = new ComplexDoublePolar(0d, 42d);
        assertEquals(fortytwoPolar, fortytwoPolar);
        assertEquals(anotherFortyTwoPolar, fortytwoPolar);
        assertEquals(fortytwoPolar, anotherFortyTwoPolar);
        Complex<Double> fortytwoCartesian = new ComplexDoubleCartesian(42d, 0d);
        assertEquals(fortytwoPolar, fortytwoCartesian);
        assertEquals(fortytwoPolar, new Double(42d));
        assertEquals(fortytwoPolar, 42d);
    }
}