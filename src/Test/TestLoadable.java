import model.ExpenseEntry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.Loadable;
import ui.MainInterface;
import ui.Saveable;
import ui.TypeEditor;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestLoadable {
    private String testFileName = "TestLoad.txt";
    private MainInterface m;
    private TypeEditor typeEditor;

    @BeforeEach
    void setup() {
        m = new MainInterface();
        typeEditor= new TypeEditor();
    }


    private void TLoad(Loadable ll) throws IOException {
        ll.load(testFileName);
    }

    private void TSave(Saveable ss) throws IOException {
        ss.save(testFileName);
    }

    @Test
    void TestLoad() throws IOException {
        TLoad(m);
        assertEquals("$19.99 |Lunch| Mon 01/10/2018 17:31",m.getEntryEditor().getEntryList().get(0).toString());
        assertEquals("$39.99 |dinner| Mon 01/10/2018 17:32", m.getEntryEditor().getEntryList().get(1).toString());
        assertEquals("$5.99 |alchohol drink| Mon 01/10/2018 17:32", m.getEntryEditor().getEntryList().get(2).toString());
    }

    @Test
    void TestSave() throws IOException {
        TLoad(m);
        ExpenseEntry newEntry = new ExpenseEntry();
        newEntry.setEntryType("Apple Music");
        newEntry.setAmount(19.99);
        newEntry.setTime("Mon 01/10/2018 17:31");
        m.getEntryEditor().getEntryList().add(newEntry);
        TSave(m);
        m= new MainInterface();
        TLoad(m);
        assertEquals("$19.99 |Apple Music| Mon 01/10/2018 17:31", m.getEntryEditor().getEntryList().get(m.getEntryEditor().getEntryList().size()-1).toString());

    }

}
