package org.npopa.samples;

import java.lang.instrument.Instrumentation;

public class JavaAgent {



   public static void agentmain(String agentArgument, Instrumentation instrumentation) throws Exception {
     premain(agentArgument, instrumentation);
   }

   public static void premain(String agentArgument, Instrumentation instrumentation) throws Exception {
     
     JettyServer server;
     int port;

     String[] args = agentArgument.split(":");
     if (args.length < 1 || args.length > 2) {
       System.err.println("Usage: -javaagent:/path/to/JavaAgent.jar=[host:]<port>");
       System.exit(1);
     }


     if (args.length == 2) {
       port = Integer.parseInt(args[1]);
       server = new JettyServer(args[0], port);
     } else {
       port = Integer.parseInt(args[0]);
       server = new JettyServer(port);
     }

     server.start();
     
   }
}
