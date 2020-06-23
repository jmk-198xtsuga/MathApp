package self.kearse.mathapp;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit testing for the ComplexDoubleCartesian class
 *
 * @author Justin Kearse
 */
public class ComplexDoubleCartesianUnitTest {

    /**
     * Tests for the constructor methods.
     */
    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    public class constructorTests {
        Complex<Double> oneCartesian;
        Complex<Double> fortytwoCartesian;
        Complex<Double> negativeiCartesian;
        @BeforeAll
        public void sampleConstructions() {
            oneCartesian = new ComplexDoubleCartesian( 1d, 0d );
            fortytwoCartesian = new ComplexDoubleCartesian( 42d, 0d );
            negativeiCartesian = new ComplexDoubleCartesian( 0d, -1d );
        }

        /**
         * Tests to make sure that the constructors properly return a new Object of
         * the appropriate class.
         */
        @Nested
        public class InstantiationTests {
            @Test
            void CartesianConstructorNotNull() {
                assertNotNull(oneCartesian, "constructor does not return null with legal parameters");
            }
            @Test
            void CartesianConstructorReturnsComplex() {
                assertTrue(oneCartesian instanceof Complex, "Constructor returns an instance of Complex");
            }
            @Test
            void CDCConstructorReturnsCDC() {
                assertTrue(new ComplexDoubleCartesian(1d, 0d) instanceof ComplexDoubleCartesian,
                        "CDC Constructor returns an instance of ComplexDoubleCartesian");
            }
        }

        /**
         * Tests to ensure that the constructors recognize and use the parameters given.
         */
        @Nested
        public class ParameterTests {
            @Test
            void CDCParametersReal() {
                assertEquals(new Double( 1d), oneCartesian.real());
                assertEquals(new Double(42d), fortytwoCartesian.real());
            }
            @Test
            void CDCParametersImaginary() {
                assertEquals(new Double( 0d), oneCartesian.imaginary());
                assertEquals(new Double(-1d), negativeiCartesian.imaginary());
            }
        }
    }
}