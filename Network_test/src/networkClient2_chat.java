import java.io.*;
import java.net.*;
import java.util.Scanner;

public class networkClient2_chat
{


    public static void main(String[] args) throws IOException, InterruptedException
    {

        backgroundNetwork myThread1 = new backgroundNetwork();
        backgroundNetwork myThread2 = new backgroundNetwork();

        //myThread2.start();

        myThread1.setName("Thread 1");
        myThread2.setName("Thread 2");

        int port = 6666;
        //int port = 49152;
        Scanner input = new Scanner(System.in);
        String eingabe;
        int num=0;
        boolean transmitted = false;
        int trys=0;

        while ((!transmitted) & (trys < 5))
        {

        try{

            myThread1.start();
            //Socket socket = new Socket("192.168.178.62",port);
            //Socket socket = new Socket("192.168.178.55",port);
            Socket socket = new Socket("87.134.0.9",port);
            //Socket socket = new Socket("localhost",port);


            System.out.println("Connected");

            DataOutputStream dout =new DataOutputStream(socket.getOutputStream());
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            do
            {
                System.out.print(">>> \r");
                eingabe = input.nextLine();
                dout.writeByte(1);
                dout.writeUTF(eingabe);
                dout.flush();


                num++;
                //out.println(eingabe);

            }while(!eingabe.equalsIgnoreCase("exit"));


            //dout.writeUTF("Hello Server");
            //dout.flush();
            //dout.close();
            //socket.close();
            transmitted= true;

            break;
        }catch(Exception e)
        {
            trys++;
            //System.out.println(e);
            System.out.print("Connection refused. Try:"+trys+"/5\r");

            Thread.sleep(2000);

        }

        }
        //System.out.println("Joined "+myThread1.getName());
        //myThread1.join();

        System.out.println("No Connection.");
        System.out.println("Exiting");
        System.out.print("\033[H\033[2J");

    }

}


