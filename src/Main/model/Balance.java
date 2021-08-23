package model;

import oberserver.BalanceObserver;

import java.text.DecimalFormat;
import java.util.Observable;

public class Balance extends Observable{
    private double balance;
    private double budget;

    public Balance(double budget){
        addObserver(new BalanceObserver());
        this.budget=budget;
    }

    public void setBalance(double balance){
        this.balance=balance;
    }

    public double getBalance(){
        return balance;
    }

    public void addBalance(double balance){
        this.balance+=balance;
    }

    public void deductBalance(double balance){
        this.balance-=balance;
    }

    public void displayBalance(boolean CompareToBudget){

        DecimalFormat df= new DecimalFormat("####0.00");
        System.out.print("$"+df.format(balance)+"\n");

        if(CompareToBudget) {
            if (balance > budget) {
                setChanged();
                notifyObservers(balance - budget);
            }
        }
    }


}
