import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        CurrencyCollector collector=new CurrencyCollector();

        String[] table=new String[]{"USD", "EUR", "GBP", "CHF"};

        for (String currency : table) {
            collector.showValueInPLN(currency, "A", 100);

        }
    }
}
