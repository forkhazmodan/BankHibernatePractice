package max.bank.servlets.models;

import com.google.gson.annotations.Expose;
import max.bank.servlets.enums.Currency;

import javax.persistence.*;

@Entity
@Table(name="Transactions")
public class Transaction {

    public Transaction(){}

    public Transaction(Account fromAccount, Account toAccount, Long amount) {
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Expose
    private Long id;

    @ManyToOne
    @JoinColumn(name = "fromAccountId")
    @Expose
    private Account fromAccount;

    @ManyToOne
    @JoinColumn(name = "toAccountId")
    @Expose
    private Account toAccount;

    @Expose
    private Long amount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Account getToAccount() {
        return toAccount;
    }

    public void setToAccount(Account toAccount) {
        this.toAccount = toAccount;
        fromAccount.addIncomingTransaction(this);
    }

    public Account getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(Account fromAccount) {
        this.fromAccount = fromAccount;
        fromAccount.addOutgoingTransaction(this);
    }

}
