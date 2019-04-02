package oberserver;

import java.text.DecimalFormat;
import java.util.Observable;
import java.util.Observer;

public class BalanceObserver implements Observer {
    @Override
    public void update(Observable o, Object arg) {
        DecimalFormat df= new DecimalFormat("####0.00");
        System.out.println("You went over the budget by: $"+df.format(arg));
    }
}
