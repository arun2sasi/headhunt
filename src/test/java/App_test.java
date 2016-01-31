import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class App_test {

    String message = "Hello World";

    @Test
    public void testPrintMessage() {
        assertEquals(message, "Hello World");
    }
}
