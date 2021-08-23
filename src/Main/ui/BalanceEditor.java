package ui;

import model.Balance;
import model.EntryLine;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class BalanceEditor {

    private double budget=1000;

    public void printTotalBalanceOfOneList(ArrayList<EntryLine> alist){
        Balance totalfunds= new Balance(budget);
        for (EntryLine item: alist) {
            if(!item.getIsExpense())
                totalfunds.addBalance(item.getAmount());
            else
                totalfunds.deductBalance(item.getAmount());
        }
        System.out.print("Your total available funds: ");
        totalfunds.displayBalance(false);
    }

    public String GUIprintTotalBalanceOfOneList(ArrayList<EntryLine> alist){
        Balance totalfunds= new Balance(budget);
        for (EntryLine item: alist) {
            if(!item.getIsExpense())
                totalfunds.addBalance(item.getAmount());
            else
                totalfunds.deductBalance(item.getAmount());
        }
        DecimalFormat df= new DecimalFormat("####0.00");
        String result="$"+df.format(totalfunds.getBalance());
        return result;
    }

    public void printTotalExpenseOfOneList(ArrayList<EntryLine> alist){
        Balance totalExpense= new Balance(budget);
        for (EntryLine item: alist) {
            if (item.getIsExpense())
                totalExpense.addBalance(item.getAmount());
        }
        System.out.print("Your total expense: -");
        totalExpense.displayBalance(true);
    }



    public void printTotalIncomeOfOneList(ArrayList<EntryLine> alist){
        Balance totalExpense= new Balance(budget);
        for (EntryLine item: alist) {
            if (!item.getIsExpense())
                totalExpense.addBalance(item.getAmount());
        }
        System.out.print("Your total income: +");
        totalExpense.displayBalance(false);
    }

    public void setBudget(double budget){
        this.budget=budget;
    }


}
