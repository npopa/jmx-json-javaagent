package org.npopa.samples;

import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.util.thread.QueuedThreadPool;

/*
 
# Use the java agent to monitor JettyServer on port 8185
java -cp jmx-json-javaagent.jar \
     -javaagent:jmx-json-javaagent.jar=8185  \
     org.npopa.samples.JettyServer 8184

*/

public class JettyServer {

    private Server server;
    
    String host = "0.0.0.0";
    int port;

    JettyServer(int port){
      this.port=port;
    }

    JettyServer(String host, int port){
      this.host=host;
      this.port=port;
    }
    
    void start() throws Exception {

        int maxThreads = 5;
        int minThreads = 1;
        int idleTimeout = 120;

        QueuedThreadPool threadPool = new QueuedThreadPool(maxThreads, minThreads, idleTimeout);

        server = new Server(threadPool);
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(port);
        connector.setHost(host);
        server.setConnectors(new Connector[] { connector });

        ServletHandler servletHandler = new ServletHandler();
        server.setHandler(servletHandler);

        servletHandler.addServletWithMapping(JMXJsonServlet.class, "/jmx");

        server.start();

    }

    void stop() throws Exception {
        server.stop();
    }
    
    public static boolean isInstrumentationAccessAllowed(
        ServletContext servletContext, HttpServletRequest request,
        HttpServletResponse response) throws IOException {
        
        return true;
      }
    
    public static void main(String[] args) throws Exception {
      
      JettyServer server;
      
      if (args.length < 1) {
        System.err.println("Usage: JettyServer <[hostname:]port>");
        System.exit(1);
      }

      String[] hostnamePort = args[0].split(":");
      int port;
      
      if (hostnamePort.length == 2) {
        port = Integer.parseInt(hostnamePort[1]);
        server = new JettyServer(hostnamePort[0], port);
      } else {
        port = Integer.parseInt(hostnamePort[0]);
        server = new JettyServer(port);
      }

      server.start();
    }
    
}

