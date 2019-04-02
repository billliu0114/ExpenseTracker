package ui;

import API.ExchangeRateAPI;
import model.EntryLine;

import java.io.IOException;
import java.util.ArrayList;

public class ExchangeRateEditor {
    private double USDCAD;

    ExchangeRateEditor() throws IOException {
        ExchangeRateAPI exAPI = new ExchangeRateAPI();
        exAPI.setExchangerateFromWeb();
        USDCAD= exAPI.getExchangeRate();
    }


    public double getUSDCAD() {
        return USDCAD;
    }

    public void CADtoUSD(ArrayList<EntryLine> alist){
        double cad;
        double usd;
        for(EntryLine item:alist){
            cad=item.getAmount();
            usd=cad/USDCAD;
            item.setAmount(usd);
        }
    }

    public void USDtoCAD(ArrayList<EntryLine> alist){
        double cad;
        double usd;
        for(EntryLine item:alist){
            usd=item.getAmount();
            cad=usd*USDCAD;
            item.setAmount(cad);
        }
    }
}
