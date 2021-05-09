//package self.kearse.mathapp;
//
//import android.graphics.Typeface;
//import android.text.SpannableStringBuilder;
//import android.text.Spanned;
//import android.text.SpannedString;
//import android.text.style.StyleSpan;
//import android.text.style.SuperscriptSpan;
//import androidx.annotation.NonNull;
//
//public class ComplexDoublePolar extends ComplexNumber<Double> {
//    /** A prime integer for use in hashing a ComplexNumber number */
//    final static int HASH_PRIME = 23;
//    /** The argument, theta, of the polar representation <i>z=r*e^{i*theta)</i> */
//    private Double argument;
//    /** The modulus, r, of the polar representation <i>z=r*e^{i*theta)</i> */
//    private Double modulus;
//
//    /** Constructs a new complex number in radial notation.
//     * @param argument the argument of the number, in radians
//     * @param modulus the modulus of the number
//     */
//    public ComplexDoublePolar (@NonNull Double argument, @NonNull Double modulus) {
//        this.argument = argument;
//        this.modulus = modulus;
//    }
//
//    /**
//     * Returns the principal Argument of this.  Calling this method updates the argument field to
//     * the principal Argument.
//     * @return the principal Argument of this, where -pi < theta <= pi
//     */
//    @NonNull
//    @Override
//    public Double Argument() {
//        if(this.modulus < 0) {
//            this.argument = principalArgument(this.argument + Math.PI);
//            this.modulus = -this.modulus;
//        } else {
//            this.argument = principalArgument(this.argument);
//        }
//        return this.argument;
//    }
//
//    /**
//     * Returns the modulus of this.
//     * @return the modulus of this
//     */
//    @NonNull
//    @Override
//    public Double modulus() {
//        return this.modulus;
//    }
//
//    /** Returns the real-value component of the Cartesian expression of this. */
//    @NonNull
//    @Override
//    public Double real() {
//        return this.modulus * Math.cos(this.argument);
//    }
//
//    /** Returns the imaginary-value component of the Cartesian expression of this. */
//    @NonNull
//    @Override
//    public Double imaginary() {
//        return this.modulus * Math.sin(this.argument);
//    }
//
//    /**
//     * Generate the additive inverse of the complex number.
//     * @return the additive inverse, with simply the negative modulus and same argument
//     */
//    @NonNull
//    @Override
//    public ComplexNumber<Double> addInverse() {
//        return new ComplexDoublePolar(argument, -modulus);
//    }
//
//    /**
//     * Generates the multiplicative inverse of the complex number.
//     * @return the multiplicative inverse, with the negative argument and inverse of the modulus
//     * @throws ArithmeticException if this is zero
//     */
//    @NonNull
//    @Override
//    public ComplexNumber<Double> multInverse() throws ArithmeticException {
//        if (modulus.equals(0d)) {
//            throw new ArithmeticException("No multiplicative inverse of zero");
//        } else {
//            return new ComplexDoublePolar(-argument, (1 / modulus));
//        }
//    }
//
//    /**
//     * Generate the complement of the complex number.
//     * @return r*e^(-i*theta), for complex number r*e^(i*theta)
//     */
//    @NonNull
//    @Override
//    public ComplexNumber<Double> complement() {
//        return new ComplexDoublePolar(-argument, modulus);
//    }
//
//    /**
//     * Adds the value of another ComplexNumber number to this.
//     * @param other another ComplexNumber number
//     * @return a new ComplexNumber number representing the sum of the two numbers, in Cartesian form
//     */
//    @NonNull
//    @Override
//    public ComplexNumber<Double> add(@NonNull ComplexNumber<? extends Number> other) {
//        Double real = this.real() + other.real().doubleValue();
//        Double imaginary = this.imaginary() + other.imaginary().doubleValue();
//        return new ComplexDoubleCartesian(real, imaginary);
//    }
//
//    /**
//     * Multiplies another ComplexNumber number with this.
//     * @param other another ComplexNumber number
//     * @return a new ComplexNumber number representing the product of the two numbers, in polar form
//     */
//    @NonNull
//    @Override
//    public ComplexNumber<Double> multiply(@NonNull ComplexNumber<? extends Number> other) {
//        Double argument = this.argument + other.Argument().doubleValue();
//        Double modulus = this.modulus * other.modulus().doubleValue();
//        return new ComplexDoublePolar(argument, modulus);
//    }
//
//    /**
//     * Formats this number for String output.
//     * @return "&lt;m&gt;*e^(i*&lt;a&gt;)", such as "3*e^(i*1.57)"
//     */
//    @Override
//    @NonNull
//    public String toString() {
//        return String.format("%s*e^(i*%s)", this.modulus.toString(), this.argument.toString());
//    }
//
//    /**
//     * Formats this number for LaTeX.
//     * @return "&lt;m&gt;\,e^{&lt;a&gt;i}", such as "3\,e^{1.57i}"
//     */
//    @NonNull
//    @Override
//    public String toLaTeX() {
//        return String.format("%s\\,e^{%s\\,i}", this.modulus.toString(), this.argument.toString());
//    }
//
//    @NonNull
//    @Override
//    public SpannedString toSpannedString() {
//        int superscriptIndex, italicIndex;
//        SpannableStringBuilder result = new SpannableStringBuilder();
//        result.append(this.modulus.toString());
//        result.append("e");
//        superscriptIndex = result.length();
//        result.append(this.argument.toString());
//        italicIndex = result.length();
//        result.append("i");
//        result.setSpan(new StyleSpan(Typeface.ITALIC), italicIndex, result.length(),
//                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        result.setSpan(new SuperscriptSpan(), superscriptIndex, result.length(),
//                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        return new SpannedString(result);
//    }
//
////    /**
////     * Checks for equality with another Object.
////     * @param other the object to compare to
////     * @return true if other is an equivalent numerical value, false otherwise.
////     */
////    @Override
////    public boolean equals(Object other) {
////        /* Use ComplexNumber parent class equality checks */
////        if (!super.equals(other)) return false;
////        if (!(other instanceof ComplexNumber)) return true;
////        @SuppressWarnings("unchecked")
////        ComplexNumber<Number> o = (ComplexNumber<Number>) other;
////
////        /* Component-wise equality check */
////        Double tArgument = this.Argument();
////        Double tModulus = this.modulus();
////        double oArgument = o.Argument().doubleValue();
////        double oModulus = o.modulus().doubleValue();
////        if (tModulus.equals(0d) && (oModulus == 0d)) {
////            return true;
////        }
////        else if ( (tArgument.equals(oArgument)) && (tModulus.equals(oModulus)) ) {
////                return true;
////        } else {
////            /* Check for coaxial equality with mismatched modulus signs */
////            tModulus = -tModulus;
////            if (tModulus.equals(oModulus)) {
////                if (tArgument > 0d) tArgument -= Math.PI;
////                else tArgument += Math.PI;
////                return tArgument.equals(oArgument);
////            }
////            else return false;
////        }
////    }
//
//    /**
//     * Hashing algorithm for ComplexDoublePolar
//     * @return an integer based on the principal argument and modulus.
//     */
//    @Override
//    public int hashCode() {
//        return Argument().hashCode() * HASH_PRIME + modulus.hashCode();
//    }
//
//    /**
//     * Computes the principal Argument of a given argument.
//     * @param argument a value in radians
//     * @return The radian value in the range -&pi;&nbsp;&lt;&nbsp;arg&nbsp;&leq;&nbsp;&pi;
//     */
//    private double principalArgument(@NonNull Number argument) {
//        double arg = argument.doubleValue();
//        if ( (arg > Math.PI) || (arg <= -Math.PI) ) {
//            arg = arg % (2 * Math.PI);
//            if (arg > Math.PI) {
//                arg -= (2 * Math.PI);
//            }
//            else if (arg <= -Math.PI) {
//                arg += (2 * Math.PI);
//            }
//        }
//        return arg;
//    }
//}
