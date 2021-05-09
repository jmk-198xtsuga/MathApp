//package self.kearse.mathapp;
//
//import android.graphics.Typeface;
//import android.text.SpannableStringBuilder;
//import android.text.Spanned;
//import android.text.SpannedString;
//import android.text.style.StyleSpan;
//import androidx.annotation.NonNull;
//
//public class ComplexDoubleCartesian extends ComplexNumber{
//    /** A prime integer for use in hashing a ComplexNumber number */
//    final static int HASH_PRIME = 71;
//    private final Double real;
//    private final Double imaginary;
//
//    public ComplexDoubleCartesian (@NonNull Double real, @NonNull Double imaginary) {
//        this.real = real;
//        this.imaginary = imaginary;
//    }
//
//    /**
//     * Computes the principal Argument of the complex number.  Computed such that the modulus would
//     * be positive.
//     * @return the principal Argument of this
//     */
//    @NonNull
//    @Override
//    public Double Argument() {
//        double Argument;
//        if (real == 0d) {
//            if (imaginary == 0d) {
//                Argument = 0d;
//            } else {
//                Argument = Math.PI/2d;
//            }
//        } else if (imaginary == 0d) {
//            if (real > 0) Argument = 0d;
//            else Argument = Math.PI;
//        } else {
//            Argument = Math.atan(imaginary / real);
//            if (real < 0d) {
//                if (Argument < 0d) Argument += Math.PI;
//                else Argument -= Math.PI;
//            }
//        }
//        return Argument;
//    }
//
//    /**
//     * Utilized the modulus squared in the multiplicative inverse method, so it is appropriate
//     * to have this intermediate step available and avoid having to compute a square root and
//     * an extra square.
//     * @return the square of the modulus of the ComplexNumber number
//     */
//    @NonNull
//    private Double modulusSquared() {
//        return this.real * this.real + this.imaginary * this.imaginary;
//    }
//
//    @NonNull
//    @Override
//    public Double modulus() {
//        return Math.sqrt(modulusSquared());
//    }
//
//    @NonNull
//    @Override
//    public Double real() {
//        return this.real;
//    }
//
//    @NonNull
//    @Override
//    public Double imaginary() {
//        return this.imaginary;
//    }
//
//    @NonNull
//    @Override
//    public ComplexNumber<Double> addInverse() {
//        return new ComplexDoubleCartesian(-this.real, -this.imaginary);
//    }
//
//    @NonNull
//    @Override
//    public ComplexNumber<Double> multInverse() throws ArithmeticException {
//        if (this.real.equals(0d) && this.imaginary.equals(0d)) {
//            throw new ArithmeticException("No multiplicative inverse of zero");
//        } else {
//            ComplexNumber<Double> complement = this.complement();
//            Double modulusSquare = this.modulusSquared();
//            return new ComplexDoubleCartesian(complement.real() / modulusSquare,
//                    complement.imaginary() / modulusSquare );
//        }
//    }
//
//    @NonNull
//    @Override
//    public ComplexNumber<Double> complement() {
//        return new ComplexDoubleCartesian(this.real, -this.imaginary);
//    }
//
//    @NonNull
//    @Override
//    public ComplexNumber<Double> add(@NonNull ComplexNumber<? extends Number> other) {
//        return new ComplexDoubleCartesian(this.real + other.real().doubleValue(),
//                this.imaginary + other.imaginary().doubleValue());
//    }
//
//    @NonNull
//    @Override
//    public ComplexNumber<Double> multiply(@NonNull ComplexNumber<? extends Number> other) {
//        Double real = (this.real * other.real().doubleValue()) -
//                (this.imaginary * other.imaginary().doubleValue());
//        Double imaginary = (this.real * other.imaginary().doubleValue()) +
//                (this.imaginary * other.real().doubleValue());
//        return new ComplexDoubleCartesian(real, imaginary);
//    }
//
//    @Override
//    @NonNull
//    public String toString() {
//        return String.format("%s+%si", this.real.toString(), this.imaginary.toString());
//    }
//
//    @NonNull
//    @Override
//    public String toLaTeX() {
//        return toString();
//    }
//
//    @NonNull
//    @Override
//    public SpannedString toSpannedString() {
//        int italicIndex;
//        SpannableStringBuilder result = new SpannableStringBuilder();
//        result.append(this.real().toString());
//        result.append("+");
//        result.append(this.imaginary().toString());
//        italicIndex = result.length();
//        result.append("i");
//        result.setSpan(new StyleSpan(Typeface.ITALIC), italicIndex, result.length(),
//                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        return new SpannedString(result);
//    }
//
///*    @Override
//    public boolean equals(Object other) {
//        *//* Use ComplexNumber parent class equality checks *//*
//        if (!super.equals(other)) return false;
//        if (!(other instanceof ComplexNumber)) return true;
//        @SuppressWarnings("unchecked")
//        ComplexNumber<Number> o = (ComplexNumber<Number>) other;
//        *//* Component-wise equality check *//*
//        Double tReal = this.real;
//        Double tImaginary = this.imaginary;
//        double oReal = o.real().doubleValue();
//        double oImaginary = o.imaginary().doubleValue();
//        return (tReal.equals(oReal)) && (tImaginary.equals(oImaginary));
//    }*/
//
//    /**
//     * Hashing algorithm for ComplexDoublePolar
//     * @return an integer based on the real and imaginary components.
//     */
//    @Override
//    public int hashCode() {
//        return real.hashCode() * HASH_PRIME + imaginary.hashCode();
//    }
//}