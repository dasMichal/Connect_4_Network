import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.TreeMap;

public class backgroundNetwork extends Thread
{




	public static void sendNetwork(int player, int column)
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
			out.flush();
			System.out.println(ausgabe);
			//out.println("CLR");
			//dout.writeUTF(eingabe);

			// }while(!ausgabe.equalsIgnoreCase("exit"));



			//dout.writeUTF("Hello Server");
			dout.flush();
			dout.close();
			socket.close();
		}catch(Exception e){System.out.println(e);}
	}

	@Override
	public void run()
	{
		System.out.println("MyThread running");
	}
}
