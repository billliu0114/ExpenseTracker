package ui;

import Exceptions.InvalidOperationException;
import model.EntryLine;
import model.EntryType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TypeEditor {
    private Map<EntryType,ArrayList<EntryLine>> typeMap;
    private int operation;
    private Scanner scanner = new Scanner(System.in);
    private ArrayList<EntryType> listOfType = new ArrayList<>();

    public TypeEditor(){
        typeMap=new HashMap<>();
    }

    public void EnterTypeIntoMap(EntryLine el){
        EntryType et;
        et= el.getEntryType();
        ArrayList<EntryLine> alist;
        if(!typeMap.containsKey(et)){
            alist= new ArrayList<>();
            typeMap.put(et,alist);
            alist.add(el);
        }
        else{
            alist=typeMap.get(et);
            alist.add(el);
        }
    }

    public String getTypeFromUser(String expenseType) {
        String result;
        CopyKeySetIntoListOfType(expenseType);
        System.out.println("Please choose a number from below: ");
        if (listOfType.isEmpty())
            System.out.println("1. Add a new type");
        else {
            PrintListOfType();
        }
        getAndCheckInputException();
        if(operation==(listOfType.size()+1)){
            System.out.println("Please enter the new type: ");
            result=scanner.nextLine();
        }
        else{
            result= listOfType.get(operation-1).getTypeName();
        }

        listOfType.clear();
        return result;

    }

    private void PrintListOfType() {
        int i = 0;
        for (EntryType item : listOfType) {
            i++;
            System.out.println(i + ". " + item);
        }
        i++;
        System.out.println(i + ". Add a new type");
    }

    private void CopyKeySetIntoListOfType(String expenseType) {
        if(expenseType.equals("expense")) {
            for (EntryType item : typeMap.keySet()) {
                if(item.getIsExpense())
                    listOfType.add(item);
            }
        }
        else{
            for (EntryType item : typeMap.keySet()) {
                if(!item.getIsExpense())
                    listOfType.add(item);
            }
        }
    }

    private void getAndCheckInputException(){
        while (true) {
            try {
                operation = scanner.nextInt();
                if (listOfType.isEmpty()) {
                    if (operation != 1) {
                        throw new InvalidOperationException();
                    }
                }
                else{
                    if(operation<1||operation>(listOfType.size()+1))
                        throw new InvalidOperationException();
                }
                scanner.nextLine();
                break;
            } catch (Exception e) {
                System.out.println("Your input is not valid. Please try again: ");
                scanner.nextLine();
            }
        }
    }

    public ArrayList<EntryType>GUI_getTypeList(String expenseType){
        CopyKeySetIntoListOfType(expenseType);
        ArrayList<EntryType> tmpList = new ArrayList<>(listOfType);
        listOfType.clear();
        return tmpList;
    }






}
