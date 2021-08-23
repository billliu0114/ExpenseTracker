package ui;

import Exceptions.AmountNegativeException;
import model.EntryLine;
import model.ExpenseEntry;
import model.IncomeEntry;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class EntryEditor {


    private TypeEditor typeEditor= new TypeEditor();
    private BalanceEditor balanceEditor=new BalanceEditor();
    private ExchangeRateEditor exchangeRateEditor;

    {
        try {
            exchangeRateEditor = new ExchangeRateEditor();
        } catch (IOException e) {
            System.out.println("Cannot get exchange rate from the web.");
        }
    }

    Scanner scanner = new Scanner(System.in);
    private ArrayList<EntryLine> entryList= new ArrayList<>();
    private Map<Integer,Integer> listnumber= new HashMap<>();

    public ArrayList<EntryLine> getEntryList(){
        return entryList;
    }

    void AddEntry(String expenseType) throws AmountNegativeException {
        EntryLine theEntry;
        if(expenseType.equals("expense"))
            theEntry= new ExpenseEntry();
        else
            theEntry= new IncomeEntry();

        System.out.println("Please enter the amount:");
        double amount = scanner.nextDouble();
        if (amount < 0)
            throw new AmountNegativeException();
        scanner.nextLine();
        theEntry.setAmount(amount);
        System.out.println("Please categorize the income:");
        theEntry.setEntryType(typeEditor.getTypeFromUser(expenseType));
        theEntry.setCurrentTime();
        System.out.println("The entry has been recorded as:");
        System.out.println(theEntry + "\n");
        entryList.add(theEntry);
        typeEditor.EnterTypeIntoMap(theEntry);
    }

    public EntryLine GUI_AddEntry(String expenseType,double amount,String type){
        EntryLine theEntry;
        if(expenseType.equals("expense"))
            theEntry= new ExpenseEntry();
        else
            theEntry= new IncomeEntry();

        theEntry.setAmount(amount);
        theEntry.setEntryType(type);
        theEntry.setCurrentTime();
        entryList.add(theEntry);
        typeEditor.EnterTypeIntoMap(theEntry);
        return theEntry;
    }

    void LoadEntry(EntryLine oneEntry, Double amount, String category, String time){
        oneEntry.setAmount(amount);
        oneEntry.setEntryType(category);
        oneEntry.setTime(time);
        entryList.add(oneEntry);
        typeEditor.EnterTypeIntoMap(oneEntry);
    }

    public void printRecord(int choice){
        int i=1;
        int j=0;

        listnumber.clear();
        PrintRecordHeader();
        for (EntryLine item : entryList) {
            j++;
            if(choice==1)
                i = printOneLine(i, j, item);
            else if(choice==2){
                if(item.getIsExpense()) {
                    i = printOneLine(i, j, item);
                }
            }
            else{
                if(!item.getIsExpense()) {
                    i = printOneLine(i, j, item);
                }
            }
        }
        System.out.println("");
    }

    private void PrintRecordHeader() {
        balanceEditor.printTotalBalanceOfOneList(entryList);
        balanceEditor.printTotalIncomeOfOneList(entryList);
        balanceEditor.printTotalExpenseOfOneList(entryList);
        System.out.println("");
        System.out.println("Record:");
    }

    private int printOneLine(int i, int j, EntryLine item) {
        System.out.println("(" + i + ") " + item);
        listnumber.put(i,j);
        i++;
        return i;
    }

    public void deleteRecord(int choice, int numberToDelete){
        int theRealNumber;
        if(choice==1){
            entryList.remove(numberToDelete);
        }
        else {
            theRealNumber=listnumber.get(numberToDelete);
            entryList.remove(theRealNumber-1);
        }

    }

    public void changeListToUSD(){
        exchangeRateEditor.CADtoUSD(entryList);
    }

    public void changeListToCAD(){
        exchangeRateEditor.USDtoCAD(entryList);
    }
    public double getExchangeRate(){
        return exchangeRateEditor.getUSDCAD();
    }
    public TypeEditor getTypeEditor() {
        return typeEditor;
    }

    public BalanceEditor getBalanceEditor() {
        return balanceEditor;
    }
}