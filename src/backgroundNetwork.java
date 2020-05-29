import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.TreeMap;

public class backgroundNetwork extends Thread
{
	private static ServerSocket server;
	//socket server port on which it will listen
	private static int port = 6666;
	@Override
	public void run()
	{
		try
		{
			serverStart();
		} catch (IOException e)
		{
			e.printStackTrace();
		}

	}

	public static void serverStart() throws IOException
	{

		System.out.println("Network Tread Started on Port 6666");
		//create the socket server object
		server = new ServerSocket(port);
		//keep listens indefinitely until receives 'exit' call or program terminates
		while(true){
			//System.out.println("Waiting for the client request");
			//creating socket and waiting for client connection
			Socket socket = server.accept();

			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String message = in.readLine();
			String[] data;


			//System.out.println(">>> " + message);
			try
			{
				data = message.split(",");
				//System.out.println(">>> " + message);
				//System.out.println(">>> Player " + data[0] + " Column " + data[1]);
			} catch (Exception e)
			{
				//System.out.println("oups no splitting");
			}
			//out.close();
			//in.close();
			socket.close();
			//terminate the server if client sends exit request
			if(message.equalsIgnoreCase("exit")) break;
		}
		System.out.println("Shutting down Socket server!!");
		//close the ServerSocket object
		server.close();

	}



}
