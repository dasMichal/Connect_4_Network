import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class netwokCore
{


	//socket server port on which it will listen
	private static int port = 6666;
	private static String ip_adress;

	private static String[] data;


	public static void reciverNet() throws IOException
	{

		//create the socket server object
		ServerSocket server = new ServerSocket(port);
		//keep listens indefinitely until receives 'exit' call or program terminates

		//while (true)
		//{
			System.out.println("Waiting for the client request");
			//creating socket and waiting for client connection
			Socket socket = server.accept();

			//PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String message = in.readLine();



			//System.out.println(">>> " + message);
			try
			{
				data = message.split(",");
				System.out.println(">>> " + message);
				System.out.println(">>> " + data[0] + " Column " + data[1]);
			} catch (Exception e)
			{
				//System.out.println("oups no splitting");
			}
			//out.close();
			//in.close();
			socket.close();
			//terminate the server if client sends exit request
			if (message.equalsIgnoreCase("exit"))
				//break;
		//}
		System.out.println("Shutting down Socket server!!");
		//close the ServerSocket object
		server.close();

	}

	public static void transmittNet(String column,String playerID) throws IOException
	{
		String eingabe;

		try{
			//Socket socket = new Socket("localhost",port);
			//Socket socket = new Socket("192.168.178.55",6666);
			Socket socket = new Socket(ip_adress,port);
			DataOutputStream dout =new DataOutputStream(socket.getOutputStream());

			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			//BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			String ausgabe;

			String playerS;
			String columnS;
			playerS = String.valueOf(playerID);
			columnS = String.valueOf(column);

			ausgabe = (playerS+","+columnS);
			out.printf(ausgabe);


			dout.flush();
			dout.close();
			socket.close();

		}catch(Exception e)
		{
			System.out.println(e);
		}



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
		netwokCore.ip_adress = ip_adress;
	}

	public static void setPort(int port)
	{
		netwokCore.port = port;
	}
}








