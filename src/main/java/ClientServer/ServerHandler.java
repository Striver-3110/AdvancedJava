package ClientServer;

import java.net.*;
import java.io.*;
import java.nio.Buffer;
import java.security.spec.ECField;

public class ServerHandler {
    static ServerSocket server ;
    static Socket client;
    static BufferedReader br;
    static PrintWriter out;


    public ServerHandler() throws  IOException{
        server = new ServerSocket(7777);
        System.out.println("Server is ready to accept the connection: ");
        client = server.accept();
        System.out.println("New Client Connected:");
        out = new PrintWriter(client.getOutputStream(),true);
        System.out.println("Print Writer is ready: ");
        br = new BufferedReader(new InputStreamReader(client.getInputStream()));
        System.out.println("buffer Reader is ready: ");
    }

    public static void startWriting() {
//        this.br = br;
        Runnable r2 = () ->{
            try{
                while(true){
                    BufferedReader brIn = new BufferedReader(new InputStreamReader(System.in));
                    String msg = brIn.readLine();
                    out.println("Server: "+msg);
                    out.flush();
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        };
        new Thread(r2).start();

    }
    public static void startReading() {
        Runnable r1 = () -> {
            System.out.println("Reader started:");
            try {
                while (true) {
                    String msg = br.readLine();
                    if (msg == null) {
                        // Handle end of stream or socket closure
                        System.out.println("Connection closed by the server.");
                        break;
                    }

                    if ("exit".equals(msg)) {
                        server.close();
                        break;
                    }

                    System.out.println("Client: " + msg);
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        };
        new Thread(r1).start();
    }

    public static void main(String[] args) {
        System.out.println("Server is Starting...");
        try{
            new ServerHandler();
            startWriting();
            startReading();

        }catch(Exception e){
            System.out.println(e.getMessage());
            e.getCause();
            e.printStackTrace();
        }
        finally {
            try{
                if (br != null) {
                    br.close();
                }

                if (out != null) {
                    out.close();
                }
                if (server != null) {
                    server.close();
                }
                if(client != null){
                    client.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            finally {
                System.exit(0);
            }
        }

    }
}
