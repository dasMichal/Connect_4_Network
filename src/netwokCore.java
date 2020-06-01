import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class netwokCore
{


	//socket server port on which it will listen
	private static int port = 6666;
	private static String ip_adresse;

	private static String[] data;


	public static void reciverNet() throws IOException
	{
		System.out.println("In ReciveNet");


		//create the socket server object
		ServerSocket server = new ServerSocket(port);



			System.out.println("Waiting for the client request");
			//creating socket and waiting for client connection
			Socket socket = server.accept();
			System.out.println("Server accept true");

			//PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			DataInputStream din = new DataInputStream(socket.getInputStream());
			//BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			//String message = in.readLine();

			byte messageType = din.readByte();
			String message = din.readUTF();

			System.out.println(">>> Recived " + message);
			try
			{
				System.out.println("In Recive Try-catch");
				data = message.split(",");
				//System.out.println(">>> " + message);
				//System.out.println(">>> " + data[0] + " Column " + data[1]);
			} catch (Exception e)
			{
				//System.out.println("oups no splitting");
			}
			System.out.println("Out of Recive Try-Catch");
			//in.close();




		System.out.println("Shutting down Socket server!!");
		//close the ServerSocket object
		server.close();

	}

	public static void transmittNet(String column,String playerID,byte type) throws IOException
	{
		String eingabe;
		System.out.println("In TransmitteNet");


		try{
			System.out.println("In Transmitt Try-catch");

			//Socket socket = new Socket("localhost",port);
			Socket socket = new Socket("192.168.178.55",6666);
			//Socket socket = new Socket("192.168.178.62",6665);
			//Socket socket = new Socket(ip_adress,6666);
			System.out.println("Set IP adress");

			DataOutputStream dout =new DataOutputStream(socket.getOutputStream());
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			//BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));



			String ausgabe;
			String playerS;
			String columnS;
			playerS = String.valueOf(playerID);
			columnS = String.valueOf(column);

			ausgabe = (playerS+","+columnS);
			System.out.println("Transmitting "+ausgabe);
			//out.printf(ausgabe);
			dout.writeByte(type);
			dout.writeUTF(ausgabe);

			System.out.println("flush");
			dout.flush();
			//dout.close();


		}catch(Exception e)
		{
			System.out.println(e);
		}
		System.out.println("Out of Transmitt try-catch");

	}

	private static final String IPV4_REGEX =
			"^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
					"(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
					"(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
					"(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";

	private static final Pattern IPv4_PATTERN = Pattern.compile(IPV4_REGEX);

	public static boolean isValidInet4Address(String ip)
	{
		if (ip == null)
		{
			return false;
		}

		Matcher matcher = IPv4_PATTERN.matcher(ip);
		return matcher.matches();
	}



	public static String[] getData()
	{
		return data;
	}

	public static void setIp_adress(String ip_adress)
	{
		netwokCore.ip_adresse = ip_adress;
	}

	public static void setPort(int port)
	{
		netwokCore.port = port;
	}
}








