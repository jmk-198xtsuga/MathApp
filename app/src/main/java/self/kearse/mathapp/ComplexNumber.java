package self.kearse.mathapp;

import android.graphics.Typeface;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.style.StyleSpan;
import android.text.style.SuperscriptSpan;

import androidx.annotation.NonNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A representation of complex numbers and set of operations for interacting with them.
 * @author Justin Kearse
 */
public abstract class ComplexNumber extends Complex<Number> {
    /** The allowed error for certain methods and algorithms */
    private static final double TARGET_PRECISION = 1E-13;

    @NonNull @Override public ComplexCartesian<Double,Double> add(@NonNull Complex<Number> other) {
        return new ComplexCartesian<>(real().doubleValue() + other.real().doubleValue(),
                imaginary().doubleValue() + other.imaginary().doubleValue());
    }

    /**
     * Checks for equality with another Object.  Compares types and values up to a specific
     * implementation of complex numbers, currently based on whether the distance between them
     * is less than a threshold value (TARGET_PRECISION).
     * @param other the object to compare to
     * @return true if the same Object, or the comparison of Real-axis component to other, or false
     *             if there is an unmatched complex component, or a presumptive true
     */
    @Override
    public boolean equals (Object other) {
        // TODO: Rename this and re-implement equals with strict equality for Java specs
        /* Never equal to null */
        if (other == null) return false;
        /* Reflexive equality check */
        else if (this == other) return true;
        /* Begin external type checking */
        else if (!(other instanceof ComplexNumber)) {
            if (other instanceof Number) {
                /* We are positively oriented on the Real number line,
                 * and comparable to other Number types */
                if (this.Argument().equals(0)) {
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
        else {
            Complex<Number> diff = this.subtract((ComplexNumber) other);
            return diff.modulus().doubleValue() < TARGET_PRECISION;
        }
    }

    @Override
    public int hashCode() {
        if (imaginary().doubleValue() == 0d) {
            return real().hashCode();
        } else {
            return Arrays.hashCode(new Number[] {real(), imaginary()});
        }
    }

    /**
     * Determines the principal complex Logarithm of a supplied value.
     * @return a new ComplexNumber representing the Logarithm.
     * @throws ArithmeticException if the modulus of value is zero.
     */
    @NonNull
    @Override
    protected ComplexCartesian<Double,?> Log() throws ArithmeticException {
        if (modulus().equals(0d)) {
            throw new ArithmeticException("Cannot take the logarithm of 0");
        } else {
            return new ComplexCartesian<> (Math.log(modulus().doubleValue()),
                    Argument());
        }
    }

    @NonNull
    @Override
    protected ComplexPolar<?,Double> Exp() {
        return new ComplexPolar<>(imaginary(),Math.exp(real().doubleValue()));
    }

    /**
     * Determines the principal <i>n</i>-th root of the given value
     * @param degree the exponent <i>n</i> such that (root)^n=value
     * @return a new ComplexPolar number with the principal n-th root
     * @throws IllegalArgumentException if degree is zero
     */
    @NonNull
    @Override
    protected ComplexPolar<Double,Double> root (int degree)
            throws IllegalArgumentException {
        double modulus = rootNewton(modulus(), degree);
        double Argument = Argument().doubleValue();
        if (Argument == 0d) {
            Argument = (Math.PI / degree) * 2d;
        } else {
            Argument = Argument / degree;
        }
        return new ComplexPolar<>(Argument, modulus);
    }



    /**
     * Determines the principal <i>n</i>-th roots of the given value
     * @param degree the exponent <i>n</i> such that (root)^n=value
     * @return a new List of ComplexPolar numbers representing the <i>n</i> unique roots of value
     * @throws IllegalArgumentException if degree is zero
     */
    @NonNull
    @Override
    public List<ComplexPolar<?,?>> roots (int degree) throws IllegalArgumentException {
        List<ComplexPolar<?,?>> list = new ArrayList<>();
        ComplexPolar<?,?> principal = root(degree);
        double modulus = principal.modulus().doubleValue();
        double argument = principal.Argument().doubleValue();
        list.add(principal);
        double argIncrement = (Math.PI / degree) * 2d;
        for (int i = 1; i < degree; i++) {
            argument += argIncrement;
            list.add(new ComplexPolar<>(argument, modulus));
        }
        return list;
    }

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

    public static class ComplexCartesian <Real_T extends Number, Imag_T extends Number> extends ComplexNumber {
        private final Real_T real;
        private final Imag_T imaginary;

        public ComplexCartesian(@NonNull Real_T real, @NonNull Imag_T imaginary) {
            this.real = real;
            this.imaginary = imaginary;
        }

        @NonNull @Override public Real_T real() {
            return real;
        }

        @NonNull @Override public Imag_T imaginary() {
            return imaginary;
        }

        @NonNull @Override public Double argument() {
            return Argument();
        }

        @NonNull @Override public Double Argument() {
            return Math.atan2(imaginary().doubleValue(),real().doubleValue());
        }

        /**
         * Utilized the modulus squared in the multiplicative inverse method, so it is appropriate
         * to have this intermediate step available and avoid having to compute a square root and
         * an extra square.
         * @return the square of the modulus of the ComplexNumber number
         */
        @NonNull
        private Double modulusSquared() {
            Double real = this.real.doubleValue();
            Double imaginary = this.imaginary.doubleValue();
            return real * real + imaginary * imaginary;
        }

        @NonNull @Override public Double modulus() {
            return Math.sqrt(modulusSquared());
        }

        @NonNull
        @Override
        public ComplexCartesian<Real_T,Double> complement() {
            return new ComplexCartesian<>(this.real, -this.imaginary.doubleValue());
        }

        @NonNull @Override public ComplexCartesian<Double,Double> addInverse () {
            return new ComplexCartesian<>(-real.doubleValue(), -imaginary.doubleValue());
        }

        @NonNull
        @Override
        public ComplexCartesian<Double,Double> multInverse() throws ArithmeticException {
            if (this.real.doubleValue() == 0d && this.imaginary.doubleValue() == 0d) {
                throw new ArithmeticException("No multiplicative inverse of zero");
            } else {
                ComplexCartesian<?,Double> complement = this.complement();
                Double modulusSquare = this.modulusSquared();
                return new ComplexCartesian<>(complement.real().doubleValue() / modulusSquare,
                        complement.imaginary() / modulusSquare );
            }
        }

        @NonNull
        @Override
        public ComplexCartesian<Double,Double> multiply(@NonNull Complex<Number> other) {
            Double thisReal = this.real.doubleValue();
            Double thisImaginary = this.imaginary.doubleValue();
            Double otherReal = other.real().doubleValue();
            Double otherImaginary = other.imaginary().doubleValue();
            Double resultReal = (thisReal * otherReal) - (thisImaginary * otherImaginary);
            Double resultImaginary = (thisReal * otherImaginary) + (thisImaginary * otherReal);
            return new ComplexCartesian<>(resultReal, resultImaginary);
        }

        @NonNull
        @Override
        public String toString() {
            return String.format("%s+%si", this.real.toString(), this.imaginary.toString());
        }

        @NonNull
        @Override
        public String toLaTeX() {
            return toString();
        }

        @NonNull
        @Override
        public SpannedString toSpannedString() {
            int italicIndex;
            SpannableStringBuilder result = new SpannableStringBuilder();
            result.append(this.real().toString());
            result.append("+");
            result.append(this.imaginary().toString());
            italicIndex = result.length();
            result.append("i");
            result.setSpan(new StyleSpan(Typeface.ITALIC), italicIndex, result.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            return new SpannedString(result);
        }
    }

    protected static class ComplexPolar <Arg_T extends Number, Mod_T extends Number> extends ComplexNumber {
        private final Arg_T argument;
        private final Mod_T modulus;

        public ComplexPolar(@NonNull Arg_T argument, @NonNull Mod_T modulus) {
            this.argument = argument;
            this.modulus = modulus;
        }

        @NonNull @Override public Double real() {
            return this.modulus().doubleValue() * Math.cos(this.argument().doubleValue());
        }

        @NonNull @Override public Double imaginary() {
            return this.modulus().doubleValue() * Math.sin(this.argument().doubleValue());
        }

        @NonNull @Override public Arg_T argument() {
            return argument;
        }

        @NonNull @Override public Number Argument() {
            if (-Math.PI < argument.doubleValue() && argument.doubleValue() <= Math.PI) {
                return argument;
            } else {
                double value = argument.doubleValue() % (2 * Math.PI);
                if (value > Math.PI) value -= (2 * Math.PI);
                return value;
            }
        }

        @NonNull @Override public Mod_T modulus() {
            return modulus;
        }

        @NonNull public ComplexNumber add(ComplexPolar<?,?> other) {
            if (Argument().doubleValue() == other.Argument().doubleValue()) {
                return new ComplexPolar<>(argument,
                        modulus.doubleValue() + other.modulus.doubleValue());
            } else {
                return super.add(other);
            }
        }

        /**
         * Generate the complement of the complex number.
         * @return r*e^(-i*theta), for complex number r*e^(i*theta)
         */
        @NonNull
        @Override
        public ComplexPolar<Double, Mod_T> complement() {
            return new ComplexPolar<>(-argument.doubleValue(), modulus);
        }

        @NonNull @Override public ComplexPolar<Arg_T,Double> addInverse () {
            return new ComplexPolar<>(argument, -modulus.doubleValue());
        }

        /**
         * Generates the multiplicative inverse of the complex number.
         * @return the multiplicative inverse, with the negative argument and inverse of the modulus
         * @throws ArithmeticException if this is zero
         */
        @NonNull
        @Override
        public ComplexPolar<Double,Double> multInverse() throws ArithmeticException {
            if (modulus.doubleValue() == 0d) {
                throw new ArithmeticException("No multiplicative inverse of zero");
            } else {
                return new ComplexPolar<>(-argument.doubleValue(), (1d / modulus.doubleValue()));
            }
        }

        /**
         * Multiplies another ComplexNumber number with this.
         * @param other another ComplexNumber number
         * @return a new ComplexNumber number representing the product of the two numbers, in polar form
         */
        @NonNull
        @Override
        public ComplexPolar<Double,Double> multiply(@NonNull Complex<Number> other) {
            Double argument = this.argument.doubleValue() + other.Argument().doubleValue();
            Double modulus = this.modulus.doubleValue() * other.modulus().doubleValue();
            return new ComplexPolar<>(argument, modulus);
        }

        /**
         * Formats this number for String output.
         * @return "&lt;m&gt;*e^(i*&lt;a&gt;)", such as "3*e^(i*1.57)"
         */
        @Override
        @NonNull
        public String toString() {
            return String.format("%s*e^(i*%s)", this.modulus.toString(), this.argument.toString());
        }

        /**
         * Formats this number for LaTeX.
         * @return "&lt;m&gt;\,e^{&lt;a&gt;i}", such as "3\,e^{1.57i}"
         */
        @NonNull
        @Override
        public String toLaTeX() {
            return String.format("%s\\,e^{%s\\,i}", this.modulus.toString(), this.argument.toString());
        }

        @NonNull
        @Override
        public SpannedString toSpannedString() {
            int superscriptIndex, italicIndex;
            SpannableStringBuilder result = new SpannableStringBuilder();
            result.append(this.modulus.toString());
            result.append("e");
            superscriptIndex = result.length();
            result.append(this.argument.toString());
            italicIndex = result.length();
            result.append("i");
            result.setSpan(new StyleSpan(Typeface.ITALIC), italicIndex, result.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            result.setSpan(new SuperscriptSpan(), superscriptIndex, result.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            return new SpannedString(result);
        }
    }
}