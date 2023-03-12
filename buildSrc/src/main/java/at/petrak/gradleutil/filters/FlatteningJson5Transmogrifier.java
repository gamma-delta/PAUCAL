package at.petrak.gradleutil.filters;

import blue.endless.jankson.api.SyntaxError;

import java.io.FilterReader;
import java.io.IOException;
import java.io.Reader;

public class FlatteningJson5Transmogrifier extends FilterReader {
    public FlatteningJson5Transmogrifier(Reader in) throws SyntaxError, IOException {
        super(JsonUtil.processJson(in, true));
    }
}