package max.bank.servlets.resources;

import max.bank.servlets.enums.Currency;

public class BalanceResource {

    public Long balance;
    public Double majorBalance;
    public Currency currency = Currency.UAH;

    public BalanceResource(Long balance) {
        this.balance = balance;
        this.majorBalance = (double)balance / 100;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public Double getMajorBalance() {
        return majorBalance;
    }

    public void setMajorBalance(Double majorBalance) {
        this.majorBalance = majorBalance;
    }
}
