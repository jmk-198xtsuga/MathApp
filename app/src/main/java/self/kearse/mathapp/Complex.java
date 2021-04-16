package self.kearse.mathapp;

import android.text.SpannedString;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import java.util.List;

/**
 * A representation of complex numbers and set of operations for interacting with them.
 * @param <T> The type for representing component values
 * @author Justin Kearse
 */
public abstract class Complex<T> {
    /** Returns the principal Argument of this. */
    @NonNull public abstract T Argument ();
    /** Returns the current little-a argument of this. */
    @NonNull public abstract T argument();
    /** Returns the modulus of this. */
    @NonNull public abstract T modulus ();
    /** Returns the real-value component of the Cartesian expression of this. */
    @NonNull public abstract T real ();
    /** Returns the imaginary-value component of the Cartesian expression of this. */
    @NonNull public abstract T imaginary();

    /**
     * Generate the additive inverse of the complex number.
     * @return the additive inverse
     */
    @NonNull public abstract Complex<T> addInverse ();

    /**
     * Generates the multiplicative inverse of the complex number.
     * Note: this may be poorly defined if T is a non-decimal type.
     * @return the multiplicative inverse
     * @throws ArithmeticException if this is zero
     */
    @NonNull public abstract Complex<T> multInverse () throws ArithmeticException;

    /**
     * Generate the complement of the complex number.
     * @return a-bi, for complex number a+bi
     */
    @NonNull public abstract Complex<T> complement ();

    /**
     * Adds the value of another Complex number to this.
     * @param other another Complex number
     * @return a new Complex number representing the sum of the two numbers
     */
    @NonNull public abstract Complex<T> add (@NonNull Complex<T> other);

    /**
     * Subtracts another Complex number from this.
     * @param other another Complex number
     * @return a new Complex number representing the difference of the two numbers
     */
    @NonNull public Complex<T> subtract (@NonNull Complex<T> other) {
        return add(other.addInverse());
    }

    /** Multiplies another Complex number with this.
     * @param other another Complex number
     * @return a new Complex number representing the product of the two numbers
     */
    @NonNull public abstract Complex<T> multiply (@NonNull Complex<T> other);

    /**
     * Divides this by another complex number.  Should utilize the complement of the denominator
     * to ensure division of a complex number by a real number, then divide appropriately.
     * @param denominator another Complex number
     * @return a new Complex number representing the quotient of the two numbers
     * @throws ArithmeticException if denominator is zero
     */
    @NonNull public Complex<T> divide (@NonNull Complex<T> denominator)
            throws ArithmeticException {
        if (denominator.modulus().equals(0)) {
            throw new ArithmeticException("Attempted to divide by zero");
        }
        else return this.multiply(denominator.multInverse());
    }

    /**
     * Checks for equality with another Object.  Compares types and values up to a specific
     * implementation of complex numbers.
     * @param other the object to compare to
     * @return true if the same Object, or the comparison of Real-axis component to other, or false
     *             if there is an unmatched complex component.
     */
    @Override
    public boolean equals (Object other) {
        /* Never equal to null */
        if (other == null) return false;
            /* Reflexive equality check */
        else if (this == other) return true;
            /* Begin external type checking */
        else if (other instanceof Complex<?>) {
            Complex<?> o = (Complex<?>) other;
            return (this.real().equals(o.real()) && this.imaginary().equals(o.imaginary()));
        } else {
            return false;
        }
    }

    /**
     * Read (and agree) that it is good practice to overload hashCode() and equals() together.
     * @return a hash code value for the Complex number.
     */
    @Override
    public abstract int hashCode();

    /**
     * Raises this to the given exponent.
     * @param exponent the desired exponent
     * @return a new Complex number representing the exponentiation result
     */
    @NonNull public final Complex<T> pow (@NonNull Complex<T> exponent) {
        return Exp(Log(this).multiply(exponent));
    }

    /**
     * Determines the principal complex Logarithm of a supplied value.
     * @param value the value of which to take the Logarithm.
     * @return a new Complex number representing the Logarithm.
     * @throws ArithmeticException if the modulus of value is zero.
     */
    @NonNull public static <S> Complex<S>.ComplexCartesian<?,?> Log (@NonNull Complex<S> value)
            throws ArithmeticException {
        return value.Log();
    }

    /**
     * Determines the principal complex Logarithm of this.
     * @return a new Complex number representing the Logarithm
     * @throws ArithmeticException if the modulus of value is zero
     */
    @NonNull abstract protected ComplexCartesian<?,?> Log ()
            throws ArithmeticException;

    /**
     * Determines the principal exponentiation of the natural logarithm <i>e</i> to a given exponent.
     * @param exponent the desired exponent
     * @return a new Complex number representing the exponentiation result
     */
    @NonNull public static <S> Complex<S>.ComplexPolar<?,?> Exp (@NonNull Complex<S> exponent) {
        return exponent.Exp();
    }

    /**
     * Determines the principal exponentiation of the natural logarithm <i>e</i> to this as an exponent.
     * @return a new Complex number representing the exponentiation result
     */
    @NonNull abstract protected ComplexPolar<?,?> Exp ();

    /**
     * Determines the principal <i>n</i>-th root of the given value
     * @param value the number to find the root of
     * @param degree the exponent <i>n</i> such that (root)^n=value
     * @return a new Complex number with the principal n-th root
     * @throws IllegalArgumentException if degree is zero
     */
    @NonNull
    public static <S> Complex<S>.ComplexPolar<?,?> root (@NonNull Complex<S> value, int degree)
            throws IllegalArgumentException {
        return value.root(degree);
    }

    /**
     * Determines the principal <i>n</i>-th root of this.
     * @param degree the exponent <i>n</i> such that (root)^n=value
     * @return a new Complex number with the principal n-th root
     * @throws IllegalArgumentException if degree is zero
     */
    @NonNull
    protected abstract ComplexPolar<?,?> root (int degree)
            throws IllegalArgumentException;

    /**
     * Determines the principal <i>n</i>-th roots of the given value
     * @param value the number to find the roots of
     * @param degree the exponent <i>n</i> such that (root)^n=value
     * @return a new List of Complex numbers representing the <i>n</i> unique roots of value
     * @throws IllegalArgumentException if degree is zero
     */
    @NonNull
    public static <S> List<? extends Complex<S>.ComplexPolar<?,?>>
        roots(@NonNull Complex<S> value, int degree)
        throws IllegalArgumentException {
        return value.roots(degree);
    }

    /**
     * Determines the principal <i>n</i>-th roots of the given value
     * @param degree the exponent <i>n</i> such that (root)^n=value
     * @return a new List of Complex numbers representing the <i>n</i> unique roots of value
     * @throws IllegalArgumentException if degree is zero
     */
    @NonNull
    public abstract List<? extends ComplexPolar<?,?>> roots (int degree)
            throws IllegalArgumentException;

    /**
     * Formats the Complex number as a String.
     * @return a string representation of the Complex number
     */
    @Override
    @NonNull
    public abstract String toString();

    /**
     * Formats the Complex number in LaTeX-style math code
     * @return a string with the LaTeX-style math code for the Complex number, without delimiters
     */
    @NonNull
    public abstract String toLaTeX();

    /**
     * Formats the Complex number in a SpannableString (for Android TextView use)
     * @return a SpannableString for the Complex number
     */
    @NonNull public abstract SpannedString toSpannedString();

    /**
     * Provides a differ for Complex numbers.
     * @param <Diff_T> The type of Complex numbers.
     * @return a differ with a speficied item and content diff check.
     */
    @NonNull
    protected static <Diff_T extends Complex<?>> DiffUtil.ItemCallback<Diff_T> getDiffCallback() {
        return new Differ<>();
    }

    /**
     * A differ to handle DiffUtil.ItemCallBack requests for Complex numbers.
     * @param <Diff_T> The type of Complex numbers.
     */
    private static class Differ<Diff_T extends Complex<?>> extends DiffUtil.ItemCallback<Diff_T> {
        @Override
        public boolean areItemsTheSame(@NonNull Diff_T oldItem, @NonNull Diff_T newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Diff_T oldItem, @NonNull Diff_T newItem) {
            return oldItem.equals(newItem);
        }
    }

    /**
     * A representation of complex numbers using Cartesian coordinate values, and a set of operations
     * for interacting with them.
     * @param <Real_T> The type for representing real component values.
     * @param <Imag_T> The type for representing imaginary component values.
     * @author Justin Kearse
     */
    public abstract class ComplexCartesian<Real_T extends T, Imag_T extends T> extends Complex<T> {
        /** The real-axis component of the Complex number. */
        @NonNull
        private final Real_T real;
        /** The imaginary-axis component of the Complex number. */
        @NonNull
        private final Imag_T imaginary;

        /**
         * Constructs a new Complex number using supplied Cartesian coordinate values.
         * @param real the real-axis component of the Complex number.
         * @param imaginary the imaginary-axis component of the Complex number.
         */
        public ComplexCartesian(@NonNull Real_T real, @NonNull Imag_T imaginary) {
            this.real = real;
            this.imaginary = imaginary;
        }

        @NonNull
        @Override
        public final Real_T real() {
            return this.real;
        }

        @NonNull
        @Override
        public final Imag_T imaginary() {
            return this.imaginary;
        }

        @NonNull
        @Override
        public T argument() {
            return Argument();
        }
    }

    /**
     * A representation of complex numbers using polar coordinate values, and a set of operations
     * for interacting with them.
     * @param <Arg_T> The type for representing argument values.
     * @param <Mod_T> The type for representing modulus values.
     * @author Justin Kearse
     */
    public abstract class ComplexPolar<Arg_T extends T, Mod_T extends T> extends Complex<T> {
        /** The radian angular component of the Complex number. */
        @NonNull
        private final Arg_T argument;
        /** The magnitude length component of the Complex number. */
        @NonNull
        private final Mod_T modulus;

        /**
         * Constructs a new Complex number using supplied polar coordinate values.
         * @param argument the radian angular component of the Complex number.
         * @param modulus the magnitude length component of the Complex number.
         */
        public ComplexPolar(@NonNull Arg_T argument, @NonNull Mod_T modulus) {
            this.argument = argument;
            this.modulus = modulus;
        }

        @NonNull
        @Override
        public final Mod_T modulus() {
            return this.modulus;
        }

        @NonNull
        @Override
        public final Arg_T argument() {
            return this.argument;
        }
    }
}