import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class App_test {

    private String str;

    @Before
    public void setUp() throws Exception {
        str = "hello world!";
    }

    @Test
    public void simple() {
        assertEquals(str, "hello world!");
    }
}
