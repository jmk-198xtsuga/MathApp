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
}