import com.google.gson.Gson;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class CurrencyCollector {

    public Response collectRate(String currencyID, String table) throws IOException {

        URL url=new URL("http://api.nbp.pl/api/exchangerates/rates/" + table + "/" + currencyID + "/?format=json");

        URLConnection connection=url.openConnection();
        connection.setRequestProperty("User-Agent", "Chrome");
        Scanner scanner=new Scanner(connection.getInputStream());

        String jsonText=scanner.nextLine();

        Gson gson=new Gson();
        Response response=gson.fromJson(jsonText, Response.class);

        return response;
    }

    public void showValueInPLN(String currencyID, String table, int valuePLN) throws IOException {
        System.out.println("Sredni kurs " + collectRate(currencyID, table).getCode() + " wynosi: " + collectRate(currencyID, table).getRates()[0].getMid());
        System.out.println("Rownowartosc 100 zl to: " + Math.round((valuePLN) / collectRate(currencyID, table).getRates()[0].getMid() / 100 * 100.0) + " " + collectRate(currencyID, table).getCode());
    }
}
