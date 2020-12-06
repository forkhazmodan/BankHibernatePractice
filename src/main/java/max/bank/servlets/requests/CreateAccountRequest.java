package max.bank.servlets.requests;


import max.bank.servlets.enums.Currency;

public class CreateAccountRequest {

    private Currency currency;

    private Long user_id;

    public CreateAccountRequest() {
    }

    public CreateAccountRequest(Currency currency, Long user_id) {
        this.currency = currency;
        this.user_id = user_id;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }
}
