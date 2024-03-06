package ClientServer;

import java.io.*;
import java.net.*;

public class ClientHandler {
    static Socket socket;
    static BufferedReader br;
    static PrintWriter out;
    public ClientHandler() throws  IOException{
        socket = new Socket("127.0.0.1",7777);
        System.out.println("connection req sent to the server:");
        br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        System.out.println("Br enabled at the client side:");
        out = new PrintWriter(socket.getOutputStream());
        System.out.println("out enabled at the client side:");

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
                        socket.close();
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
        System.out.println("Client is ready to send connection req:");
        try {
            new ClientHandler();
            startReading();
            startWriting();
        } catch (IOException e) {
//            throw new RuntimeException(e);
            e.printStackTrace();
        }
        finally {
            try {
                if (br != null) {
                    br.close();
                }

                if (out != null) {
                    out.close();
                }
                if (socket != null) {
                    socket.close();
                }

//                socket.close();
//                br.close();
//                out.close();
            }catch(Exception e){
                e.printStackTrace();
            }

        }

    }
}
















