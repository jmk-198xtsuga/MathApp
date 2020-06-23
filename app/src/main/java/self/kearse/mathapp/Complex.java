package self.kearse.mathapp;

import androidx.annotation.NonNull;

/**
 * A representation of complex numbers and set of operations for interacting with them.
 * @param <T> The numerical type for representing component values, logically should be either
 *           Float or Double
 * @author Justin Kearse
 */
public abstract class Complex <T extends Number> {
    /** Returns the principal Argument of this. */
    public abstract T Argument ();
    /** Returns the modulus of this. */
    public abstract T modulus ();
    /** Returns the real-value component of the Cartesian expression of this. */
    public abstract T real ();
    /** Returns the imaginary-value component of the Cartesian expression of this. */
    public abstract T imaginary();

    /**
     * Generate the additive inverse of the complex number.
     * @return the additive inverse
     */
    public abstract Complex<T> addInverse ();

    /**
     * Generates the multiplicative inverse of the complex number.
     * @return the multiplicative inverse
     * @throws ArithmeticException if this is zero
     */
    public abstract Complex<T> multInverse () throws ArithmeticException;

    /**
     * Generate the complement of the complex number.
     * @return a-bi, for complex number a+bi
     */
    public abstract Complex<T> complement ();

    /**
     * Adds the value of another Complex number to this.
     * @param other another Complex number
     * @return a new Complex number representing the sum of the two numbers
     * @throws NullPointerException if other is null
     */
    public abstract Complex<T> add (Complex<? extends T> other) throws NullPointerException;

    /**
     * Subtracts another Complex number from this.
     * @param other another Complex number
     * @return a new Complex number representing the difference of the two numbers
     * @throws NullPointerException if other is null
     */
    public Complex<T> subtract (Complex<? extends T> other) throws NullPointerException {
        if (other == null) {
            throw new NullPointerException("Cannot subtract a null reference");
        }
        else return add(other.addInverse());
    }

    /** Multiplies another Complex number with this.
     * @param other another Complex number
     * @return a new Complex number representing the product of the two numbers
     * @throws NullPointerException if other is null
     */
    public abstract Complex<T> multiply (Complex<? extends T> other) throws NullPointerException;

    /**
     * Divides this by another complex number.  Should utilize the complement of the denominator
     * to ensure division of a complex number by a real number, then divide appropriately.
     * @param denominator another Complex number
     * @return a new Complex number representing the quotient of the two numbers
     * @throws ArithmeticException if denominator is zero
     * @throws NullPointerException if denominator is null
     */
    public Complex<T> divide (Complex<? extends T> denominator)
            throws ArithmeticException, NullPointerException {
        if (denominator == null) {
            throw new NullPointerException("Cannot divide by a null reference");
        }
        else if (denominator.modulus().equals(0)) {
            throw new ArithmeticException("Attempted to divide by zero");
        }
        else return this.multiply(denominator.multInverse());
    }

    /**
     * Checks for equality with another Object.  Compares types and values up to a specific
     * implementation of complex numbers, so any implementation should provide the final
     * check of component-wise value equality.
     * @param other the object to compare to
     * @return true if the same Object, or the comparison of Real-axis component to other, or false
     *             if there is an unmatched complex component, or a presumptive true
     */
    public boolean equals (Object other) {
        /* Never equal to null */
        if (other == null) return false;
        /* Reflexive equality check */
        else if (this == other) return true;
        /* Begin external type checking */
        else if ( (this.Argument().equals(0)) && ! (other instanceof Complex) ) {
            /* We are positively oriented on the Real number line,
             * and comparable to other Number types */
            return this.modulus().equals(other);
        }
        else if ( this.imaginary().equals(0) ) {
            /* We are negatively oriented on the Real axis,
             * and comparable to other Number types */
            return this.addInverse().modulus().equals(other);
        }
        else //noinspection RedundantIfStatement
            if ( !(other instanceof Complex) ) {
            /* We certainly have a complex component and are comparing to something without one */
            return false;
        }
        /* End external type checking */
        else {
            /* From here rely on implementing classes to complete verification, so return
            * a presumptive true */
            return true;
        }
    }

    /**
     * Raises this to the given exponent.
     * @param exponent the desired exponent
     * @return a new Complex number representing the exponentiation result
     * @throws NullPointerException if exponent is null
     */
    public Complex<T> pow (Complex<? extends T> exponent) throws NullPointerException {
        if (exponent == null) {
            throw new NullPointerException("cannot exponentiate by a null reference");
        }
        else return Exp(this.Log().multiply(exponent));
    }

    /**
     * Determines the principal complex Logarithm of this
     * @return a new Complex number representing the Logarithm
     * @throws ArithmeticException if this is zero
     */
    public abstract Complex<T> Log () throws NullPointerException;

    /**
     * Determines the principal exponentiation of the natural logarithm <i>e</i> to a given exponent.
     * @param exponent the desired exponent
     * @return a new Complex number representing the exponentiation result
     * @throws NullPointerException if exponent is null
     */
    public abstract Complex<T> Exp (Complex<? extends T> exponent) throws NullPointerException;

    /**
     * Formats the Complex number as a String.
     * @return a string representation of the Complex number
     */
    @Override
    @NonNull
    public abstract String toString();

    /**
     * Formats the Complex number in LaTeX-style math code
     * @return a string with the LaTeX-style math code for the Complex Number, without delimiters
     */
    public abstract String toLaTeX();
}