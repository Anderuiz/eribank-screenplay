package co.com.eribank.models;

/** Datos de una solicitud de crédito. Inmutable y construido con el patrón Builder. */
public final class MortgageData {

    private final String firstName;
    private final String lastName;
    private final String age;
    private final String addressOne;
    private final String addressTwo;
    private final String country;
    private final String loanType;
    private final String years;
    private final String occupation;
    private final String yearlyIncome;

    private MortgageData(Builder b) {
        this.firstName = b.firstName;
        this.lastName = b.lastName;
        this.age = b.age;
        this.addressOne = b.addressOne;
        this.addressTwo = b.addressTwo;
        this.country = b.country;
        this.loanType = b.loanType;
        this.years = b.years;
        this.occupation = b.occupation;
        this.yearlyIncome = b.yearlyIncome;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String firstName() { return firstName; }
    public String lastName() { return lastName; }
    public String age() { return age; }
    public String addressOne() { return addressOne; }
    public String addressTwo() { return addressTwo; }
    public String country() { return country; }
    public String loanType() { return loanType; }
    public String years() { return years; }
    public String occupation() { return occupation; }
    public String yearlyIncome() { return yearlyIncome; }

    public static final class Builder {
        private String firstName;
        private String lastName;
        private String age;
        private String addressOne;
        private String addressTwo;
        private String country;
        private String loanType;
        private String years;
        private String occupation;
        private String yearlyIncome;

        private Builder() {
        }

        public Builder firstName(String v) { this.firstName = v; return this; }
        public Builder lastName(String v) { this.lastName = v; return this; }
        public Builder age(String v) { this.age = v; return this; }
        public Builder addressOne(String v) { this.addressOne = v; return this; }
        public Builder addressTwo(String v) { this.addressTwo = v; return this; }
        public Builder country(String v) { this.country = v; return this; }
        public Builder loanType(String v) { this.loanType = v; return this; }
        public Builder years(String v) { this.years = v; return this; }
        public Builder occupation(String v) { this.occupation = v; return this; }
        public Builder yearlyIncome(String v) { this.yearlyIncome = v; return this; }

        public MortgageData build() {
            return new MortgageData(this);
        }
    }
}
