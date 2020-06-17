import java.io.IOException;
import java.util.concurrent.TimeUnit;

//Diese Klasse ist einzig und allein dafür da um einen rotieren ladebalken zu erzeugen

public class Loading_animation extends Thread
{

	public void run()
	{

		do
		{

			System.out.print("\\ \r");
			if (isInterrupted()) break;
			warten();
			System.out.print("|\r");
			if (isInterrupted()) break;
			warten();
			System.out.print("/\r");
			if (isInterrupted()) break;
			warten();
			System.out.print("⎯\r");
			if (isInterrupted()) break;
			warten();
		}while(!isInterrupted());
		System.out.println("");
		interrupt();
		//System.out.println("Exit 1");

	}


	public void warten()
	{
		try
		{
			TimeUnit.MILLISECONDS.sleep(200);
		} catch (InterruptedException e)
		{
			interrupt();
			//System.out.println("Exit 2");
			//e.printStackTrace();
		}


	}



}
