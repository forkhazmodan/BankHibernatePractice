package max.bank.servlets.enums;

public enum Currency {
    USD("USD"),EUR("EUR"),UAH("UAH");

    private String value;

    Currency(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
