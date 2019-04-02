import model.ExpenseEntry;
import model.Time;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.TypeEditor;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExpenseEntryTest {
    private ExpenseEntry line;
    private String T_str= "AHAHA";
    private double T_amount=500.99;

    @BeforeEach
    void runbefore(){
        TypeEditor typeEditor = new TypeEditor();
        line= new ExpenseEntry();}

    @Test
    void testSetExpenseType(){
        line.setEntryType(T_str);
        assertEquals("$0.0 "+T_str+" ", line.toString());
    }

    @Test
    void testSetAmount(){
        line.setAmount(T_amount);
        assertEquals("$"+T_amount+" "+" ",line.toString());
    }

    @Test
    void testSetEnterTime(){
        line.setCurrentTime();
        Time t=new Time();
        assertEquals("$0.0"+" "+" "+t.getTime(),line.toString());
    }

    @Test
    void testToString(){
        Time t=new Time();
        line.setCurrentTime();
        line.setAmount(T_amount);
        line.setEntryType(T_str);
        assertEquals("$"+T_amount+" "+T_str+" "+t.getTime(),line.toString());
    }
}
