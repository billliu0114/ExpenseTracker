package ui;

import Exceptions.AmountNegativeException;
import Exceptions.InvalidOperationException;
import model.Balance;
import model.EntryLine;
import model.ExpenseEntry;
import model.IncomeEntry;


import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.*;
import java.util.Scanner;


public class MainInterface implements Saveable, Loadable{

    private EntryEditor entryEditor = new EntryEditor();
    private Scanner scanner = new Scanner(System.in);
    private int operation;
    private boolean changeCurrency=false;



    //REQUIRES: OutputFile.txt in folder
    //MODIFIES: this, ExpenseEntry
    //EFFECTS: the user intereaction interface
    public void run() {
        run_load();


        while(true){
            displayMainMenu();
            run_getOperation();

            if(operation ==1){
                run_opAddEntry("expense");
            }
            else if(operation ==2){
                run_opAddEntry("income");
            }
            else if(operation ==3){
                run_opView();
            }
            else if(operation ==4){
                run_opDelete();
            }
            else {
                run_opTerminate();
                break;
            }
        }
    }


    public EntryEditor getEntryEditor() {
        return entryEditor;
    }

    private boolean displayCurrencyInCAD(){
        int number;
        System.out.println("Do you want to proceed with [1]CAD or [2]USD ?");
        System.out.println("Please select one number: ");
        while(true) {
            try {
                number = scanner.nextInt();
                if (number != 1 && number != 2)
                    throw new InvalidOperationException();
                scanner.nextLine();
                break;
            } catch (Exception ex) {
                System.out.println("Your input is not valid. Please try again: ");
                scanner.nextLine();
            }
        }
        if(number==1)
            System.out.println("The system will display in CAD.");
        else {
            System.out.println("The system will display in USD.");
            System.out.println("The real time exchange rate is: 1 USD= "+entryEditor.getExchangeRate()+" CAD");
        }
        return number == 1;
    }

    private void displayMainMenu() {
        System.out.println("Please select one of the options:");
        System.out.println("[1] for adding an expense");
        System.out.println("[2] for adding an income");
        System.out.println("[3] for viewing record");
        System.out.println("[4] for deleting a record");
        System.out.println("[5] for quitting the program");
    }

    //EFFECTS: Print out the entryList which contains all the records of expense
    private void PrintStatement(int choice){
        entryEditor.printRecord(choice);
    }


    public void run_load(){
        try {
            load("OutputFile.txt");
        }
        catch (IOException ioexception){
            System.out.println("Cannot locate OutputFile.txt");
        }
        if(changeCurrency)
            entryEditor.changeListToUSD();
    }

    private void run_getOperation(){
        while(true) {
            try {
                operation = scanner.nextInt();
                if(operation !=1 && operation !=2 && operation !=3 && operation !=4 && operation !=5)
                    throw new InvalidOperationException();
                scanner.nextLine();
                break;
            }
            catch (Exception ex) {
                System.out.println("Your input is not valid. Please try again: ");
                scanner.nextLine();
            }
        }
    }


    private void run_opAddEntry(String expenseType){

        while(true) {
            try {
                entryEditor.AddEntry(expenseType);
                break;
            } catch (AmountNegativeException e) {
                entryEditor.scanner.nextLine();
                System.out.println("You entered an negative value. Please try again: ");
            } catch (InputMismatchException e){
                entryEditor.scanner.nextLine();
                System.out.println("You did not entered a numerical value. Please try again: ");
            }
        }
    }

    private int run_opView(){
        System.out.println("[1] The complete record. [2] The expense Only. [3]The income Only.");
        while(true) {
            try {
                operation = scanner.nextInt();
                if(operation !=1 && operation !=2 && operation !=3)
                    throw new InvalidOperationException();
                scanner.nextLine();
                break;
            }
            catch (Exception ex) {
                System.out.println("Your input is not valid. Please try again: ");
                scanner.nextLine();
            }
        }
        PrintStatement(operation);
        return operation;
    }
    private void run_opDelete(){
        int selection_delete;
        int view_choice;
        int size_entrylist;
        ArrayList<EntryLine> alist= entryEditor.getEntryList();
        view_choice= run_opView();
        System.out.println("Please select which line to delete by entering the line number: ");
        while(true) {
            try {
                size_entrylist = alist.size();
                selection_delete = scanner.nextInt();
                if (selection_delete <= 1 || selection_delete > size_entrylist) {
                    throw new InvalidOperationException();
                }
                scanner.nextLine();
                break;
            } catch (Exception e) {
                System.out.println("Your input is invalid. Please try again:");
                scanner.nextLine();
            }
        }
        entryEditor.deleteRecord(view_choice,selection_delete);
        System.out.println("Your record has been updated as below: ");
        PrintStatement(view_choice);
    }

    public void run_opTerminate(){
        System.out.println("The program will be terminated.\n");

        try {
            save("OutputFile.txt");
        }
        catch (IOException ioexception){
            System.out.println("Cannot locate OutputFile.txt");
        }
    }

    public void setChangeCurrency(boolean changeCurrency) {
        this.changeCurrency = changeCurrency;
    }

    //REQUIRES: filename to be valid
    //MODIFIES: this, ExpenseEntry, entryList
    //EFFECTS: load the txt file into systems
    @Override
    public void load(String filename) throws IOException{
        EntryLine oneEntry;
        List<String> lines= Files.readAllLines(Paths.get(filename));
        if (lines.size()==0){
            return;
        }
        for(String line: lines){
            ArrayList<String> partsOfLine= splitOnBar(line);
            if(partsOfLine.get(0).charAt(0)=='-') {
               oneEntry = new ExpenseEntry();
            }
            else {
                oneEntry= new IncomeEntry();
            }

            double amount= extractDouble(partsOfLine.get(0));
            String category= partsOfLine.get(1);
            String time= partsOfLine.get(2);
            time= time.trim();
            entryEditor.LoadEntry(oneEntry, amount, category, time);
        }

    }

    //REQUIRES: filename
    //MODIFIES: this, entryList
    //EFFECTS: save the entryList into txt file
    @Override
    public void save(String filename) throws IOException{
        if(changeCurrency)
            entryEditor.changeListToCAD();
        PrintWriter writer= new PrintWriter(filename, "UTF-8");
        ArrayList<EntryLine> alist= entryEditor.getEntryList();
        for (EntryLine line: alist) {
            writer.println(line.toString());
        }
        writer.close();
    }

    //From FileReaderWriter Project
    public static ArrayList<String> splitOnBar(String line){
        String[] splits= line.split("\\|");
        return new ArrayList<>(Arrays.asList(splits));
    }

    //EFFECTS: return the number inside of a string
    public static double extractDouble(String line){
        return Double.parseDouble(line.replaceAll("[^0-9\\.]+",""));
    }
}
