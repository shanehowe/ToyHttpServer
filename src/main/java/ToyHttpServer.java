import http.HttpRequestParser;
import http.parsers.HeaderParser;
import http.parsers.RequestLineParser;
import http.reader.HttpLineReader;
import http.reader.LineReader;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ToyHttpServer {

  public static void run() throws IOException {
    try (ServerSocket server = new ServerSocket(8080, 5, InetAddress.getByName("localhost"))) {
      Socket accept = server.accept();
      HttpRequestParser httpRequestReader = HttpRequestParser.newBuilder()
          .reader(new HttpLineReader(accept.getInputStream()))
          .requestLineParser(new RequestLineParser())
          .headerParser(new HeaderParser())
          .build();
      OutputStream outputStream = accept.getOutputStream();
      outputStream.write(
          "HTTP/1.1 200 OK\r\nConnection: Close\r\n\r\n".getBytes(StandardCharsets.UTF_8));
    }
  }
}
