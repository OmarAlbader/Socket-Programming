import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

public class Server {
    private ServerSocket serverSocket;
    private Socket connectionSocket;
    private DataInputStream input;
    private DataOutputStream out;

    public Server(int port) {
        try {
            serverSocket = new java.net.ServerSocket(port);

            System.out.println("Server started");
            System.out.println("Waiting for a client request...");

            connectionSocket = serverSocket.accept();
            System.out.println("Client Accepted");

            input = new DataInputStream(new BufferedInputStream(connectionSocket.getInputStream(), 2048));

            out = new DataOutputStream(connectionSocket.getOutputStream());

            String received_msg = "", modified_msg = "";

            while (!received_msg.equals("exit")) {
                received_msg = input.readUTF();
                System.out.println("Received \"" + received_msg + "\", converting it to UpperCase...");
                TimeUnit.SECONDS.sleep(1);

                modified_msg = received_msg.toUpperCase();
                System.out.println("Successfully converted to UpperCase");

                TimeUnit.SECONDS.sleep(1);
                System.out.println("Data \"" + modified_msg + "\" sent from Server to Client\n");
                out.writeUTF(modified_msg);
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                serverSocket.close();
                input.close();
                out.close();

                System.out.println("Closing connection...");

                System.out.println("Server Closed");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public static void main (String[] args) {
        Server server1 = new Server(5000);} }
