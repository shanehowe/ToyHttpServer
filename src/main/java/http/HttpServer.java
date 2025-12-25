package http;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HttpServer {
  private final ConnectionHandler connectionHandler;
  private final ExecutorService connectionPool;
  private static final Logger logger = LogManager.getLogger(HttpServer.class);

  public HttpServer(ConnectionHandler connectionHandler, ExecutorService connectionPool) {
    this.connectionHandler = connectionHandler;
    this.connectionPool = connectionPool;
  }

  public void start(int port) throws IOException {
    try (ServerSocket server = new ServerSocket(port, 5, InetAddress.getByName("localhost"))) {
      logger.info("JavaHttpServer started on http://localhost:{}", port);
      while (true) {
        Socket clientSocket = server.accept();
        connectionPool.submit(() -> connectionHandler.handleConnection(clientSocket));
      }
    }
  }
}
