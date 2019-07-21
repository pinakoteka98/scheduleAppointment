import java.util.List;

public class Response {

    public String table;
    public String currency;
    public String code;
    public Rates[] rates;

    public String getTable() {
        return table;
    }

    public void setTable(final String table) {
        this.table=table;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(final String currency) {
        this.currency=currency;
    }

    public String getCode() {
        return code;
    }

    public void setCode(final String code) {
        this.code=code;
    }

    public Rates[] getRates() {
        return rates;
    }

    public void setRates(final Rates[] rates) {
        this.rates=rates;
    }
}







