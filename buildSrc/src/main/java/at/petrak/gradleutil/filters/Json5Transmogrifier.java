package at.petrak.gradleutil.filters;

import blue.endless.jankson.api.SyntaxError;

import java.io.FilterReader;
import java.io.IOException;
import java.io.Reader;

/**
 * We do this as a FilterReader to make sure it can read every line at once
 * because gradle passes things line-by-line otherwise for Some Reason
 * <p>
 * See https://github.com/Cypher121/hexbound/tree/master/buildSrc/src/main/kotlin/coffee/cypher/gradleutil/filters
 * thank you cypher
 */
public class Json5Transmogrifier extends FilterReader {
    public Json5Transmogrifier(Reader in) throws SyntaxError, IOException {
        super(JsonUtil.processJson(in, false));
    }
}