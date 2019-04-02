package model;



public abstract class EntryLine {
    String enterTime="";
    double amount=0;
    EntryType entryType;

    EntryLine(){
        entryType =new EntryType();
    }


    public boolean getIsExpense(){
        return entryType.getIsExpense();
    }

    public EntryType getEntryType(){
        return this.entryType;
    }

    public double getAmount(){
        return amount;
    }

    //MODIFIES: this
    //EFFECTS: Enter the expense type into ExpenseEntry
    public void setEntryType(String entryType){
        this.entryType.setTypeName(entryType);
    }

    //MODIFIES: this
    //EFFECTS: Enter the value of expense into ExpenseEntry
    public void setAmount(Double amount){
        this.amount=amount;
    }

    //MODIFIES: this, Time
    //EFFECTS: set the current time
    public void setCurrentTime(){
        Time t= new Time();
        enterTime=t.getTime();
    }

    public void setTime(String enterTime){
        this.enterTime=enterTime;
    }

    //EFFECTS: return the object as a string
    public abstract String toString();
}
