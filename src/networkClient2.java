import java.io.*;
import java.net.*;
import java.util.Scanner;

public class networkClient2
{


    public static void main(String[] args) throws IOException {

        Scanner input = new Scanner(System.in);
        String eingabe;
        int num=0;
        try{
            Socket socket = new Socket("192.168.178.62",6666);
            DataOutputStream dout =new DataOutputStream(socket.getOutputStream());
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            do
            {
                System.out.print("Gebe einen Text ein: ");
                eingabe = input.nextLine();
                out.println(eingabe);
                //dout.writeUTF(eingabe);
                num++;
            }while(!eingabe.equalsIgnoreCase("exit"));


            //dout.writeUTF("Hello Server");
            //dout.flush();
            //dout.close();
            socket.close();
        }catch(Exception e){System.out.println(e);}

    }


    public static void sendMessage(int player, int column)
    {
        String eingabe;

        try{
            Socket socket = new Socket("localhost",6666);
            DataOutputStream dout =new DataOutputStream(socket.getOutputStream());

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String ausgabe;
            //do
            // {
            String playerS;
            String columnS;
            playerS = String.valueOf(player);
            columnS = String.valueOf(column);

            ausgabe = (playerS+","+columnS);
            out.printf(ausgabe);
            //out.flush();
            //System.out.println(ausgabe);
            //out.println("CLR");
            //dout.writeUTF(eingabe);

            // }while(!ausgabe.equalsIgnoreCase("exit"));


            //dout.writeUTF("Hello Server");
            dout.flush();
            dout.close();
            socket.close();
        }catch(Exception e){System.out.println(e);}
    }
}


