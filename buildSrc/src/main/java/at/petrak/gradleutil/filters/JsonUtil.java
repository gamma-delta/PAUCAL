package at.petrak.gradleutil.filters;

import blue.endless.jankson.Jankson;
import blue.endless.jankson.JsonGrammar;
import blue.endless.jankson.JsonObject;
import blue.endless.jankson.JsonPrimitive;
import blue.endless.jankson.api.SyntaxError;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

public class JsonUtil {
    private static final Jankson JANKSON = Jankson.builder().build();

    public static StringReader processJson(Reader in, boolean flatten) throws IOException, SyntaxError {
        var jsonSrc = JsonUtil.jesusChristJustGetTheGodDamnReaderIntoAFuckingStream(in);
        JsonObject asJson = JANKSON.load(jsonSrc);

        JsonObject out = flatten
            ? flattenObject(asJson)
            : asJson;
        var unTfedSrc = out.toJson(JsonGrammar.STRICT);
        return new StringReader(unTfedSrc);
    }

    private static JsonObject flattenObject(JsonObject obj) {
        var out = new JsonObject();
        flattenInner(null, obj, out);
        return out;
    }

    private static void flattenInner(String prefix, JsonObject obj, JsonObject out) {
        obj.forEach((keyStub, val) -> {
            String key;
            if (prefix == null) {
                key = keyStub;
            } else if (keyStub.isEmpty()
                || ":_-/".indexOf(keyStub.charAt(keyStub.length() - 1)) != -1) {
                // Allow cases like: { foo: { "": 123, bar: 456 } }
                // Flattens to { "foo": 123, "foo.bar": 456 }
                // Or, cases like { "paucal:": { item1: "Item 1" } }
                key = prefix;
            } else {
                key = prefix + "." + keyStub;
            }

            if (val instanceof JsonObject subobj) {
                flattenInner(key, subobj, out);
            } else if (val instanceof JsonPrimitive prim
                && prim.getValue() instanceof String text
                && text.startsWith(">>>")) {
                // In case i want to actually use the pipe char in the lang somewhere, require a start sigil
                // also, I'm not sure *what* cypher's doing but I am NOT getting newlines in my input at all.
                // so, we insert them ourselves
                String validArea = text.substring(text.indexOf('|'));
                String processed = validArea.replaceAll("\\|\\s*", "\n");
                // This puts a newline out the front so remove that
                out.put(key, new JsonPrimitive(processed.substring(1)));
            } else {
                out.put(key, val);
            }
        });
    }

    public static String jesusChristJustGetTheGodDamnReaderIntoAFuckingStream(Reader in) throws IOException {
        // https://frontbackend.com/java/how-to-convert-reader-to-string-in-java
        char[] buffer = new char[4096];
        StringBuilder bob = new StringBuilder();

        // i love cursed for loops
        for (int numChars; (numChars = in.read(buffer)) >= 0; ) {
            bob.append(buffer, 0, numChars);
        }

        return bob.toString();
    }
}
