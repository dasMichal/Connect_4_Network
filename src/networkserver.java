import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class networkserver {

    public static void main(String[] args) throws IOException {

        try{
            String message = "a";
            while(true)
            {
                ServerSocket serverSocket = new ServerSocket(666);
                Socket socket = serverSocket.accept();//establishes connection
                //PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));


                String[] data;
                while((message = in.readLine()) != null)
                {
                    try
                    {
                        data = message.split(",");
                        System.out.println(">>> " + message);
                        System.out.println(">>> Player " + data[0] + " Column " + data[1]);
                    } catch (Exception e)
                    {
                        //System.out.println("oups no splitting");
                    }

                }

                //DataInputStream dis=new DataInputStream(socket.getInputStream());
            }


           // serverSocket.close();
        }catch(Exception e)
        {
            //System.out.println(e);
            e.printStackTrace();
        }


    }

    }
