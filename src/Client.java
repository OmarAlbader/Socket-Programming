import java.io.*;
import java.net.*;

public class Client {
    private Socket clientSocket;
    private DataInputStream input;
    private DataInputStream inputSocket;
    private DataOutputStream out;
    String host;
    int serverPort;
    public Client (String address, int port) {
        host = address;
        this.serverPort = port;
        try {
            clientSocket = new Socket(host, port);
            System.out.println("Client Connected.");
            // takes input from terminal
            input = new DataInputStream(System.in);

            // sends output to the socket
            out = new DataOutputStream(clientSocket.getOutputStream());

            // read reply from server socket
            inputSocket = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream(), 2048));
        } catch (IOException e) {
            System.out.println(e);
            return;
        }

        String msg = "", modified = "";
        while (!msg.equals("exit")) {
            try {
                System.out.print("Enter your message: ");
                msg = input.readLine();
                out.writeUTF(msg);

                modified = inputSocket.readUTF();
                System.out.println("modified message is: " + modified);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            input.close();
            out.close();
            clientSocket.close();

            System.out.println("Closing connection...");

            System.out.println("Client Closed");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args) {
        Client client = new Client("127.0.0.1", 5000);} }
