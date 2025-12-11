import http.HttpRequestReader;
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
      LineReader reader = new HttpLineReader(accept.getInputStream());
      HttpRequestReader httpRequestReader = new HttpRequestReader(reader);
      OutputStream outputStream = accept.getOutputStream();
      outputStream.write("HTTP/1.1 200 OK\r\nConnection: Close\r\n\r\n".getBytes(StandardCharsets.UTF_8));
    }
  }
}
