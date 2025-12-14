package http;

import http.parsers.HeaderParser;
import http.parsers.RequestLineParser;
import http.reader.HttpLineReader;
import java.io.IOException;
import java.net.Socket;

public class HttpRequestParserProvider {
  public HttpRequestParser provide(Socket socket) throws IOException {
    return HttpRequestParser.newBuilder()
        .reader(new HttpLineReader(socket.getInputStream()))
        .requestLineParser(new RequestLineParser())
        .headerParser(new HeaderParser())
        .build();
  }
}
