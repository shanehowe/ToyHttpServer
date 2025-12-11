package http.reader;

import java.io.IOException;

public interface LineReader {
  String readLine() throws IOException;
}
