package self.kearse.mathapp;

import androidx.annotation.NonNull;

public class ComplexDoublePolar extends Complex<Double> {
    /** The argument, theta, of the polar representation <i>z=r*e^{i*theta)</i> */
    private Double argument;
    /** The modulus, r, of the polar representation <i>z=r*e^{i*theta)</i> */
    private Double modulus;

    /** Constructs a new complex number in radial notation.
     * @param argument the argument of the number, in radians
     * @param modulus the modulus of the number
     * @throws NullPointerException if argument or modulus are null
     */
    public ComplexDoublePolar (Double argument, Double modulus) throws NullPointerException {
        if ( (argument == null) || (modulus == null) ) {
            throw new NullPointerException("Cannot use null values");
        } else {
            this.argument = argument;
            this.modulus = modulus;
        }
    }

    /**
     * Returns the principal Argument of this.  Calling this method updates the argument field to
     * the principal Argument.
     * @return the principal Argument of this, where -pi < theta <= pi
     */
    @Override
    public Double Argument() {
        if ( (this.argument > Math.PI) || (this.argument <= -Math.PI) ) {
            this.argument = this.argument % (2 * Math.PI);
            if (this.argument > Math.PI) {
                this.argument -= (2 * Math.PI);
            }
            else if (this.argument <= -Math.PI) {
                this.argument += (2 * Math.PI);
            }
        }
        return this.argument;
    }

    /**
     * Returns the modulus of this.
     * @return the modulus of this
     */
    @Override
    public Double modulus() {
        return this.modulus;
    }

    /** Returns the real-value component of the Cartesian expression of this. */
    @Override
    public Double real() {
        return this.modulus * Math.cos(this.argument);
    }

    /** Returns the imaginary-value component of the Cartesian expression of this. */
    @Override
    public Double imaginary() {
        return this.modulus * Math.sin(this.argument);
    }

    /**
     * Generate the additive inverse of the complex number.
     * @return the additive inverse, with simply the negative modulus and same argument
     */
    @Override
    public Complex<Double> addInverse() {
        return new ComplexDoublePolar(argument, -modulus);
    }

    /**
     * Generates the multiplicative inverse of the complex number.
     * @return the multiplicative inverse, with the negative argument and inverse of the modulus
     * @throws ArithmeticException if this is zero
     */
    @Override
    public Complex<Double> multInverse() throws ArithmeticException {
        if (modulus.equals(0d)) {
            throw new ArithmeticException("No multiplicative inverse of zero");
        } else {
            return new ComplexDoublePolar(-argument, (1 / modulus));
        }
    }

    /**
     * Generate the complement of the complex number.
     * @return r*e^(-i*theta), for complex number r*e^(i*theta)
     */
    @Override
    public Complex<Double> complement() {
        return new ComplexDoublePolar(-argument, modulus);
    }

    /**
     * Adds the value of another Complex number to this.
     * @param other another Complex number
     * @return a new Complex number representing the sum of the two numbers, in Cartesian form
     * @throws NullPointerException if other is null
     */
    @Override
    public Complex<Double> add(Complex<? extends Number> other) throws NullPointerException {
        if (other == null) {
            throw new NullPointerException("Cannot add a null reference");
        }
        else {
            Double real = this.real() + other.real().doubleValue();
            Double imaginary = this.imaginary() + other.imaginary().doubleValue();
            return new ComplexDoubleCartesian(real, imaginary);
        }
    }

    /** Multiplies another Complex number with this.
     * @param other another Complex number
     * @return a new Complex number representing the product of the two numbers, in polar form
     * @throws NullPointerException if other is null
     */
    @Override
    public Complex<Double> multiply(Complex<? extends Number> other) throws NullPointerException {
        if (other == null) {
            throw new NullPointerException("Cannot add a null pointer");
        } else {
            Double argument = this.argument + other.Argument().doubleValue();
            Double modulus = this.modulus * other.modulus().doubleValue();
            return new ComplexDoublePolar(argument, modulus);
        }
    }

    @Override
    @NonNull
    public String toString() {
        return String.format("%s*e^(i*%s)", this.modulus.toString(), this.argument.toString());
    }

    @Override
    public String toLaTeX() {
        return String.format("%s\\,e^{%s\\,i}", this.modulus.toString(), this.argument.toString());
    }

    @Override
    public boolean equals(Object other) {
        /* Use Complex parent class equality checks */
        if (!super.equals(other)) return false;
        if (!(other instanceof Complex)) return true;
        @SuppressWarnings("unchecked")
        Complex<Number> o = (Complex<Number>) other;
        /* Component-wise equality check */
        Double tArgument = this.Argument();
        Double tModulus = this.modulus();
        double oArgument = o.Argument().doubleValue();
        double oModulus = o.modulus().doubleValue();
        if (tModulus.equals(0d) && (oModulus == 0d)) {
            return true;
        }
        else if ( (tArgument.equals(oArgument)) && (tModulus.equals(oModulus)) ) {
                return true;
        } else {
            /* Check for coaxial equality with mismatched modulus signs */
            tModulus = -tModulus;
            if (tModulus.equals(oModulus)) {
                if (tArgument > 0d) tArgument -= Math.PI;
                else tArgument += Math.PI;
                return tArgument.equals(oArgument);
            }
            else return false;
        }
    }
}
