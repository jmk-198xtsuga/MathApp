package self.kearse.mathapp;

import android.text.SpannedString;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import java.util.ArrayList;
import java.util.List;

/**
 * A representation of complex numbers and set of operations for interacting with them.
 * @param <T> The numerical type for representing component values, logically should be either
 *           Float or Double
 * @author Justin Kearse
 */
public abstract class Complex <T extends Number> {
    /** The allowed error for certain methods and algorithms */
    private static final double TARGET_PRECISION = 1E-13;
    /** Returns the principal Argument of this. */
    @NonNull public abstract T Argument ();
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

    //TODO: Explore static methods for mathematical operations.
    // Currently the mathematical operations are controlled via object-oriented methods,
    // which is functional yet there might be a better sytnax implementation of these.

    /**
     * Adds the value of another Complex number to this.
     * @param other another Complex number
     * @return a new Complex number representing the sum of the two numbers
     */
    @NonNull public abstract Complex<T> add (@NonNull Complex<? extends Number> other);

    /**
     * Subtracts another Complex number from this.
     * @param other another Complex number
     * @return a new Complex number representing the difference of the two numbers
     */
    @NonNull public Complex<T> subtract (@NonNull Complex<? extends Number> other) {
        return add(other.addInverse());
    }

    /** Multiplies another Complex number with this.
     * @param other another Complex number
     * @return a new Complex number representing the product of the two numbers
     */
    @NonNull public abstract Complex<T> multiply (Complex<? extends Number> other);

    /**
     * Divides this by another complex number.  Should utilize the complement of the denominator
     * to ensure division of a complex number by a real number, then divide appropriately.
     * @param denominator another Complex number
     * @return a new Complex number representing the quotient of the two numbers
     * @throws ArithmeticException if denominator is zero
     */
    @NonNull public Complex<T> divide (@NonNull Complex<? extends Number> denominator)
            throws ArithmeticException {
        if (denominator.modulus().equals(0)) {
            throw new ArithmeticException("Attempted to divide by zero");
        }
        else return this.multiply(denominator.multInverse());
    }

    /**
     * Checks for equality with another Object.  Compares types and values up to a specific
     * implementation of complex numbers, currently based on whether the distance between them
     * is less than a threshold value (TARGET_PRECISION).
     * @param other the object to compare to
     * @return true if the same Object, or the comparison of Real-axis component to other, or false
     *             if there is an unmatched complex component, or a presumptive true
     */
    public boolean equals (Object other) {
        // TODO: Rename this and re-impement equals with strict equality for Java specs
        /* Never equal to null */
        if (other == null) return false;
        /* Reflexive equality check */
        else if (this == other) return true;
        /* Begin external type checking */
        else if (!(other instanceof Complex)) {
            if (other instanceof Number) {
                /* We are positively oriented on the Real number line,
                 * and comparable to other Number types */
                if (this.Argument().equals(0d)) {
                    return this.modulus().doubleValue() == ((Number) other).doubleValue();
                } else if (this.imaginary().doubleValue() < TARGET_PRECISION) {
                    /* We are negatively oriented on the Real axis,
                     * and comparable to other Number types */
                    return this.addInverse().modulus().doubleValue() == ((Number) other).doubleValue();
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
        /* End external type checking */
        //TODO: I think rearranging else-if sequence would nix raw class warning, or careful
        // generic workings...
        else {
            Complex<T> diff = this.subtract((Complex<? extends Number>) other);
            return diff.modulus().doubleValue() < TARGET_PRECISION;
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
    @NonNull public Complex<? extends Number> pow (@NonNull Complex<? extends Number> exponent) {
        return Exp(Log(this).multiply(exponent));
    }

    /**
     * Determines the principal complex Logarithm of this
     * @return a new Complex number representing the Logarithm
     * @throws ArithmeticException if the modulus of value is zero
     */
    @NonNull public static Complex<? extends Number> Log (@NonNull Complex<? extends Number> value)
            throws ArithmeticException {
        if (value.modulus().equals(0d)) {
            throw new ArithmeticException("Cannot take the logarithm of 0");
        } else {
            return new ComplexDoubleCartesian(Math.log(value.modulus().doubleValue()),
                    value.Argument().doubleValue());
        }
    }

    /**
     * Determines the principal exponentiation of the natural logarithm <i>e</i> to a given exponent.
     * @param exponent the desired exponent
     * @return a new Complex number representing the exponentiation result
     */
    @NonNull public static Complex<? extends Number> Exp (@NonNull Complex<? extends Number> exponent) {
        return new ComplexDoublePolar(exponent.imaginary().doubleValue(),
                Math.exp(exponent.real().doubleValue()));
    }

    /**
     * Determines the principal <i>n</i>-th root of the given value
     * @param value the number to find the root of
     * @param degree the exponent <i>n</i> such that (root)^n=value
     * @return a new Complex number with the principal n-th root
     * @throws IllegalArgumentException if degree is zero
     */
    @NonNull
    public static Complex<? extends Number> root (@NonNull Complex<? extends Number> value, int degree)
            throws IllegalArgumentException {
        double modulus = rootNewton(value.modulus(), degree);
        double Argument = value.Argument().doubleValue();
        if (Argument == 0d) {
            Argument = (Math.PI / degree) * 2d;
        } else {
            Argument = Argument / degree;
        }
        return new ComplexDoublePolar(Argument, modulus);
    }

    /**
     * Determines the principal <i>n</i>-th roots of the given value
     * @param value the number to find the roots of
     * @param degree the exponent <i>n</i> such that (root)^n=value
     * @return a new List of Complex numbers representing the <i>n</i> unique roots of value
     * @throws IllegalArgumentException if degree is zero
     */
    @NonNull
    public static List<Complex<? extends Number>> roots (@NonNull Complex<? extends Number> value,
                                                         int degree) throws IllegalArgumentException {
        List<Complex<? extends Number>> list = new ArrayList<>();
        Complex<? extends Number> principal = root(value, degree);
        double modulus = principal.modulus().doubleValue();
        double argument = principal.Argument().doubleValue();
        list.add(principal);
        double argIncrement = (Math.PI / degree) * 2d;
        for (int i = 1; i < degree; i++) {
            argument += argIncrement;
            list.add(new ComplexDoublePolar(argument, modulus));
        }
        return list;
    }

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
     * Computes the n-th root of a Real number.  Utilizes a exponent/logarithm computation to seed
     * the guess and follows with a Newton algorithm (see
     * <a href=https://en.wikipedia.org/wiki/Nth_root_algorithm>https://en.wikipedia.org/wiki/Nth_root_algorithm</a>)
     * @param value a real number
     * @param degree the degree of the root
     * @return the real value such that raising it to degree yields value (within approximation)
     * @throws IllegalArgumentException if value is negative
     */
    private static double rootNewton (@NonNull Number value, int degree)
            throws IllegalArgumentException {
        if (value.doubleValue() < 0) {
            throw new IllegalArgumentException("Value should be a positive real number");
        }
        /* Short-circuit for roots of zero and one*/
        if (0d == value.doubleValue()) return 0d;
        else if (1d == value.doubleValue()) return 1d;

        double decValue = 1d/degree;
        int oneLess = degree - 1;
        double delta = 1d;
        double root = Math.exp(decValue*Math.log(value.doubleValue()));
        while (delta > TARGET_PRECISION) {
            double next = decValue * ((oneLess * root) + (value.doubleValue()/Math.pow(root, oneLess)));
            delta = Math.abs(next - root);
            root = next;
        }
        /* Coerce root to integer when appropriate */
        int intValue = (int) root;
        if (Math.pow(intValue, degree) == value.doubleValue()) root = intValue;
        /* End integer coercion */
        return root;
    }

    /**
     * Provides a differ for Complex numbers.
     * @param <Diff_T> The type of Complex numbers.
     * @return a differ with a speficied item and content diff check.
     */
    @NonNull
    protected static <Diff_T extends Complex<? extends Number>> DiffUtil.ItemCallback<Diff_T> getDiffCallback() {
        return new Differ<>();
    }

    /**
     * A differ to handle DiffUtil.ItemCallBack requests for Complex numbers.
     * @param <Diff_T> The type of Complex numbers.
     */
    private static class Differ<Diff_T extends Complex<? extends Number>> extends DiffUtil.ItemCallback<Diff_T> {
        @Override
        public boolean areItemsTheSame(@NonNull Diff_T oldItem, @NonNull Diff_T newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Diff_T oldItem, @NonNull Diff_T newItem) {
            return oldItem.equals(newItem);
        }
    }
}