package http.parsers;

import http.HttpHeaders;
import http.reader.LineReader;
import java.io.IOException;

public class HeaderParser {

  public HttpHeaders parse(LineReader reader) throws IOException {
    String headerLine;
    HttpHeaders headers = new HttpHeaders();
    while (!(headerLine = reader.readLine().trim()).isBlank()) {
      String[] splitLine = headerLine.split(": ", 2);
      if (splitLine.length != 2) {
        throw new IllegalArgumentException("splitting header line on ':' does not yield length of 2");
      }
      headers.add(splitLine[0], splitLine[1]);
    }
    return headers;
  }
}
