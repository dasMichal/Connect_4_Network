import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class networkserver {

    public static void main(String[] args) throws IOException {

        try{
            ServerSocket serverSocket =new ServerSocket(6666);
            Socket socket = serverSocket.accept();//establishes connection
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //String username = in.readLine();


            String message;
            String[] data;
            //DataInputStream dis=new DataInputStream(socket.getInputStream());


                message = in.readLine();
                System.out.println(">>> "+message);
               try
               {
                   data = message.split(",");
                   System.out.println(">>> "+message);
                   System.out.println(">>> Player "+data[0]+" Column "+data[1]);
               }catch(Exception e)
               {
                   System.out.println("oups no splitting");
               }
               //data = message.split(",");
                //System.out.println(in.readLine());
                //String  str=(String)dis.readUTF();




            //serverSocket.close();
        }catch(Exception e)
        {
            System.out.println(e);
            e.printStackTrace();
        }


    }

    }
