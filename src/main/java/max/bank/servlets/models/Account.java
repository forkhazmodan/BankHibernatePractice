package max.bank.servlets.models;

import com.google.gson.annotations.Expose;
import max.bank.servlets.enums.Currency;

import javax.persistence.*;
import java.util.ArrayList;

import java.util.List;

@Entity
@Table(name = "Accounts")
@NamedQuery(name = "getById", query = "SELECT a FROM max.bank.servlets.models.Account a WHERE a.id = :id")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Expose
    private long id;

    @Enumerated(EnumType.STRING)
    @Expose
    private Currency currency;

    @Expose
    private long balance;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "fromAccount", cascade = CascadeType.ALL)
    private List<Transaction> outgoingTransactions = new ArrayList<>();

    @OneToMany(mappedBy = "toAccount", cascade = CascadeType.ALL)
    private List<Transaction> incomingTransactions = new ArrayList<>();

    public Account() {
    }

    public Account(Currency currency, User user) {
        this.currency = currency;
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Transaction> getOutgoingTransactions() {
        return outgoingTransactions;
    }

    public void setOutgoingTransactions(List<Transaction> outgoingTransactions) {
        this.outgoingTransactions = outgoingTransactions;
    }

    public List<Transaction> getIncomingTransactions() {
        return incomingTransactions;
    }

    public void setIncomingTransactions(List<Transaction> incomingTransactions) {
        this.incomingTransactions = incomingTransactions;
    }

    public void addIncomingTransaction(Transaction incomingTransaction) {
        if ( ! incomingTransactions.contains(incomingTransaction)) {
            incomingTransactions.add(incomingTransaction);
        }
    }

    public void addOutgoingTransaction(Transaction outgoingTransaction) {
        if ( ! outgoingTransactions.contains(outgoingTransaction)) {
            outgoingTransactions.add(outgoingTransaction);
        }
    }
}
