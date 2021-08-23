package ui;

import java.io.IOException;

public interface Saveable {
    void save(String filename) throws IOException;
}
