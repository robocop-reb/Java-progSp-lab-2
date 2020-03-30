package sample;

import java.net.*;
import java.io.*;


public class Server {
    private ServerSocket socket = null;
    private Socket clientSocket = null;
    private DataInputStream consoleInput = null;

    public static int getNum(Socket socket) throws IOException {
        int num=0;
        DataInputStream inFromClient = new DataInputStream(socket.getInputStream());
        num = inFromClient.readInt();
        return num;

    }
    public static void sendRes(Socket socket,String res) throws IOException {
        DataOutputStream toClient = new DataOutputStream(socket.getOutputStream());
        toClient.writeUTF(res);

    }
    public Socket start(int port) throws IOException {
        try {
            socket = new ServerSocket(port);
            clientSocket = socket.accept();
            consoleInput = new DataInputStream(new BufferedInputStream(System.in));
            if (clientSocket != null) {
                System.out.println("connected");
            }
        }catch (UnknownHostException i){
            System.out.println(i);
        }
        return clientSocket;
    }

    public void stop(boolean des) throws IOException {
        if(des == false) {
            socket.close();
        }
    }

    public static void main(String[] args) throws IOException {
        Server server = new Server();
       Socket socket = server.start(5000);
        System.out.println("Awaiting a number");
       int num = 0;
       while(num == 0) {
           num = getNum(socket);
       }
       if(num % 3 == 0) {
           sendRes(socket, "Can be divided by three");
       }else{
           sendRes(socket, "Can not be divided by three");
       }
       System.out.println(num);
       System.out.println("enter zero to kill server");
        boolean des = server.consoleInput.readBoolean();
        server.stop(des);



    }
}