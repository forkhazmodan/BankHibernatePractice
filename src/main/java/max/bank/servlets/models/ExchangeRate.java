package max.bank.servlets.models;

import javax.persistence.*;
import max.bank.servlets.enums.*;


@Entity
@Table(name="ExchangeRates")
@NamedQuery(name = "selectByCombination", query = "SELECT er FROM max.bank.servlets.models.ExchangeRate er WHERE er.from = :from AND er.to = :to")
public class ExchangeRate {

    public ExchangeRate() {}

    public ExchangeRate(Currency from, Currency to, Double rate) {
        this.from = from;
        this.to = to;
        this.rate = rate;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Currency from;

    @Enumerated(EnumType.STRING)
    private Currency to;

    private Double rate;

    public Currency getFrom() {
        return from;
    }

    public void setFrom(Currency from) {
        this.from = from;
    }

    public Currency getTo() {
        return to;
    }

    public void setTo(Currency to) {
        this.to = to;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "ExchangeRate{" +
                "id=" + id +
                ", from=" + from +
                ", to=" + to +
                ", rate=" + rate +
                '}';
    }
}
