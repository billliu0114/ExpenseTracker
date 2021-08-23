import model.ExpenseEntry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.MainInterface;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.fail;

public class LoadFileTest {
    public String testFileName;
    public MainInterface m;

    @BeforeEach
    public void setup() {
        m = new MainInterface();
    }

    @Test
    public void TestLoadExceptionShouldBeThrown(){
        testFileName = "TestLoad1.txt";
        try {
            m.load(testFileName);
            fail("Exception not thrown");
        } catch (IOException e) {
            return;
        }
    }
    @Test
    public void TestLoadExceptionShouldNotBeThrown(){
        testFileName = "TestLoad.txt";
        try {
            m.load(testFileName);
        } catch (IOException e) {
            fail("Exception not thrown");
        }
    }
}
