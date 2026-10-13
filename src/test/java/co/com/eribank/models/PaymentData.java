package co.com.eribank.models;

/** Datos de un pago. Inmutable y construido con el patrón Builder. */
public final class PaymentData {

    private final String phone;
    private final String name;
    private final double amount;
    private final String country;

    private PaymentData(Builder builder) {
        this.phone = builder.phone;
        this.name = builder.name;
        this.amount = builder.amount;
        this.country = builder.country;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String phone() {
        return phone;
    }

    public String name() {
        return name;
    }

    public double amount() {
        return amount;
    }

    public String amountAsText() {
        return amount % 1 == 0 ? String.valueOf((long) amount) : String.valueOf(amount);
    }

    public String country() {
        return country;
    }

    public static final class Builder {
        private String phone;
        private String name;
        private double amount;
        private String country;

        private Builder() {
        }

        public Builder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder amount(double amount) {
            this.amount = amount;
            return this;
        }

        public Builder country(String country) {
            this.country = country;
            return this;
        }

        public PaymentData build() {
            if (amount <= 0) {
                throw new IllegalArgumentException("El monto del pago debe ser mayor a cero");
            }
            return new PaymentData(this);
        }
    }
}
