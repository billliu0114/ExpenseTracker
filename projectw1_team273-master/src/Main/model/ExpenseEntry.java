package model;


import java.text.DecimalFormat;

public class ExpenseEntry extends EntryLine {

    public ExpenseEntry(){
        super();
        entryType.setIsExpense(true);
    }
    //EFFECTS: return the object as a string
    @Override
    public String toString () {
        DecimalFormat df= new DecimalFormat("####0.00");
        return "- $"+df.format(amount) + " |" + entryType.getTypeName() + "| " + enterTime;
    }

}

