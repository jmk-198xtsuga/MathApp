package self.kearse.mathapp;

import androidx.annotation.NonNull;

public class ComplexDoubleCartesian extends Complex<Double> {
    private final Double real;
    private final Double imaginary;

    public ComplexDoubleCartesian (Double real, Double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    /**
     * Computes the principal Argument of the complex number.  Computed such that the modulus would
     * be positive.
     * @return the principal Argument of this
     */
    @Override
    public Double Argument() {
        double Argument;
        if (real == 0d) {
            if (imaginary == 0d) {
                Argument = 0d;
            } else {
                Argument = Math.PI/2d;
            }
        } else if (imaginary == 0d) {
            if (real > 0) Argument = 0d;
            else Argument = Math.PI;
        } else {
            Argument = Math.atan(imaginary / real);
            if (real < 0d) {
                if (Argument < 0d) Argument += Math.PI;
                else Argument -= Math.PI;
            }
        }
        return Argument;
    }

    @Override
    public Double modulus() {
        return Math.sqrt( (this.real * this.real) + (this.imaginary * this.imaginary) );
    }

    @Override
    public Double real() {
        return this.real;
    }

    @Override
    public Double imaginary() {
        return this.imaginary;
    }

    @Override
    public Complex<Double> addInverse() {
        return new ComplexDoubleCartesian(-this.real, -this.imaginary);
    }

    @Override
    public Complex<Double> multInverse() throws ArithmeticException {
        if (this.real.equals(0d) && this.imaginary.equals(0d)) {
            throw new ArithmeticException("No multiplicative inverse of zero");
        } else {
            Complex<Double> complement = this.complement();
            Double modulus = this.modulus();
            return new ComplexDoubleCartesian(complement.real() / modulus,
                    complement.imaginary() / modulus);
        }
    }

    @Override
    public Complex<Double> complement() {
        return new ComplexDoubleCartesian(this.real, -this.imaginary);
    }

    @Override
    public Complex<Double> add(Complex<? extends Number> other) throws NullPointerException {
        return new ComplexDoubleCartesian(this.real + other.real().doubleValue(),
                this.imaginary + other.imaginary().doubleValue());
    }

    @Override
    public Complex<Double> multiply(Complex<? extends Number> other) throws NullPointerException {
        Double real = (this.real * other.real().doubleValue()) -
                (this.imaginary * other.imaginary().doubleValue());
        Double imaginary = (this.real * other.imaginary().doubleValue()) +
                (this.imaginary * other.real().doubleValue());
        return new ComplexDoubleCartesian(real, imaginary);
    }

    @Override
    @NonNull
    public String toString() {
        return String.format("%s+%si", this.real.toString(), this.imaginary.toString());
    }

    @Override
    public String toLaTeX() {
        return toString();
    }

    @Override
    public boolean equals(Object other) {
        /* Use Complex parent class equality checks */
        if (!super.equals(other)) return false;
        if (!(other instanceof Complex)) return true;
        @SuppressWarnings("unchecked")
        Complex<Number> o = (Complex<Number>) other;
        /* Component-wise equality check */
        Double tReal = this.real;
        Double tImaginary = this.imaginary;
        double oReal = o.real().doubleValue();
        double oImaginary = o.imaginary().doubleValue();
        return (tReal.equals(oReal)) && (tImaginary.equals(oImaginary));
    }
}
