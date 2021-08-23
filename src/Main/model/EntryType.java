package model;

import java.util.Objects;

public class EntryType {
    private boolean isExpense;//true for expense, false for income
    private String typeName;

    public boolean getIsExpense(){
        return isExpense;
    }

    public void setIsExpense(boolean isExpense){
        this.isExpense=isExpense;
    }

    public String getTypeName(){
        return typeName;
    }

    public void setTypeName(String typeName){
        this.typeName=typeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EntryType)) return false;
        EntryType entryType = (EntryType) o;
        return isExpense == entryType.isExpense &&
                Objects.equals(typeName, entryType.typeName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(isExpense, typeName);
    }

    public String toString(){
        return typeName;
    }
}
