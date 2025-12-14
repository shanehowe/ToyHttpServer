package http;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

public class HttpServer {
  private final ConnectionHandler connectionHandler;
  private final ExecutorService connectionPool;

  public HttpServer(ConnectionHandler connectionHandler, ExecutorService connectionPool) {
    this.connectionHandler = connectionHandler;
    this.connectionPool = connectionPool;
  }

  public void start(int port) throws IOException {
    try (ServerSocket server = new ServerSocket(port, 5, InetAddress.getByName("localhost"))) {
      while (true) {
        Socket clientSocket = server.accept();
        connectionPool.submit(() -> connectionHandler.handleConnection(clientSocket));
      }
    }
  }
}
