package API;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import static ui.MainInterface.extractDouble;

public class ExchangeRateAPI {

    private double exchangeRate=1;
    public void setExchangerateFromWeb() throws IOException {
        BufferedReader br = null;
        String target = null;
        double exrate;
        try {
            String apikey = "access_key=51716768df5bf0b928f9be8437d39817"; //fill this in with the API key they email you
            String query = "http://apilayer.net/api/live?";
            String theURL=query+apikey+"&currencies=CAD&source=USD&format=1";
            URL url = new URL(theURL);
            br = new BufferedReader(new InputStreamReader(url.openStream()));

            String line;

            StringBuilder sb = new StringBuilder();

            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                if(line.contains("\"USDCAD\":"))
                    target=line;
            }
            if(target==null)
                target="1";

            exrate=extractDouble(target);
        } finally {
            if (br != null) {
                br.close();
            }
        }
        exchangeRate=exrate;
    }

    public double getExchangeRate(){
        return exchangeRate;
    }
}
