import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

class netwokCore
{


	private static ServerSocket server;
	//socket server port on which it will listen
	private static int port = 6666;

	private static String[] data;


	public static void reciverNet() throws IOException
	{

		//create the socket server object
		server = new ServerSocket(port);
		//keep listens indefinitely until receives 'exit' call or program terminates

		while (true)
		{
			System.out.println("Waiting for the client request");
			//creating socket and waiting for client connection
			Socket socket = server.accept();

			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String message = in.readLine();



			//System.out.println(">>> " + message);
			try
			{
				data = message.split(",");
				System.out.println(">>> " + message);
				System.out.println(">>> Player " + data[0] + " Column " + data[1]);
			} catch (Exception e)
			{
				//System.out.println("oups no splitting");
			}
			//out.close();
			//in.close();
			socket.close();
			//terminate the server if client sends exit request
			if (message.equalsIgnoreCase("exit")) break;
		}
		System.out.println("Shutting down Socket server!!");
		//close the ServerSocket object
		server.close();

	}

	public static void transmittNet(String column,String playerID) throws IOException
	{
		String eingabe;

		try{
			Socket socket = new Socket("localhost",port);
			DataOutputStream dout =new DataOutputStream(socket.getOutputStream());

			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

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

	public static String[] getData()
	{
		return data;
	}
}







