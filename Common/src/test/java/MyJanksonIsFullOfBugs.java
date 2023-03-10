import blue.endless.jankson.Jankson;
import blue.endless.jankson.JsonObject;
import blue.endless.jankson.api.SyntaxError;
import org.testng.annotations.Test;

public class MyJanksonIsFullOfBugs {
    @Test
    public static void testCapsHex() throws SyntaxError {
        Jankson jankson = Jankson.builder().build();
        String src = " { \"foo\": 0xDEADBEEF } ";
        try {
            JsonObject obj = jankson.load(src);
        } catch (SyntaxError e) {
            System.out.println(e.getCompleteMessage());
            throw e;
        }
    }

    @Test
    public static void testLeadingZeroes() throws SyntaxError {
        Jankson jankson = Jankson.builder().build();
        String src = " { \"foo\": 0x00FF00 } ";
        try {
            JsonObject obj = jankson.load(src);
        } catch (SyntaxError e) {
            System.out.println(e.getCompleteMessage());
            throw e;
        }
    }
}
