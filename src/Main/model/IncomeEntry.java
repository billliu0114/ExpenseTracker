package model;


import java.text.DecimalFormat;

public class IncomeEntry extends EntryLine{

    public IncomeEntry(){
        super();
        entryType.setIsExpense(false);
    }
    @Override
    public String toString() {
        DecimalFormat df= new DecimalFormat("####0.00");
        return "+ $"+df.format(amount) + " |" + entryType.getTypeName() + "| " + enterTime;
    }
}
