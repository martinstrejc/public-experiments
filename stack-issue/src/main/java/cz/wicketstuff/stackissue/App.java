package cz.wicketstuff.stackissue;

import java.io.Closeable;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
	
	public static final int MEM_ALLOC = 10 * 1024 * 1024;
	public static final int SOCKET_READ_SIZE  = 1024;

	public static class ThreadSocket
	{
	
		public final Thread thread;
		public final SocketProcess socket;

		public ThreadSocket(Thread thread, SocketProcess socket)
		{
			this.thread = thread;
			this.socket = socket;
		}
		
		public void interrupt() throws IOException 
		{
			String name = thread.getName();
			System.out.println(name + " is going to get kill");
			socket.close();
			thread.interrupt();
			System.out.println(name + " killed");
		}
		
	}
	
	private final LinkedList<ThreadSocket> threadSockes = new LinkedList<>();
	
	public void startMemThread(int i)
	{
		Thread t = new Thread(new MemoryAllocator(), "t-memory-allocator-" + i);
		t.start();
	}
	
	public void startProcessThread(int i)
	{
		Thread t = new Thread(new RunProcess(), "t-spawn-process-" + i);
		t.start();
	}
	
	public void killThreads() throws IOException
	{
		for (int i = 0; i < 100; i++)
		{
			threadSockes.pollLast().interrupt();
		}
	}

	public void run() throws IOException
	{
		for (int port = 10000; port < 65535; port++)
		{
			SocketProcess socketProcess = new SocketProcess(port);
            try 
            {
    			Thread t = new Thread(socketProcess, "t-socker-" + port);
    			threadSockes.add(new ThreadSocket(t, socketProcess));
    			t.start();
    			
            }
            catch (OutOfMemoryError e) {
            	logError(e);
	    		Runtime rt = Runtime.getRuntime();
	    		logMessage("Totl memory " + rt.totalMemory());
	    		logMessage("Free memory " + rt.freeMemory());
            	killThreads();
            	socketProcess.close();
				// e.printStackTrace();
				break;
			}
		}
		
	}

	public void run2()
	{
		startMemThread(1);

		try
		{
			Thread.sleep(30000);
		}
		catch (InterruptedException e)
		{
			Thread.currentThread().interrupt();
		}
		
		for (int i = 0; i < 1000; i++) 
		{
			startProcessThread(900000 + i);
		}
	}
	
    public static void main( String[] args ) throws Exception
    {
        logMessage("Hello World!");
        new App().run();
        logMessage("Finished!");
    }
    
    public static class MemoryAllocator implements Runnable
    {
    	private ByteBuffer bb;
    	private final List<ByteBuffer> list = new LinkedList<>();
    	
    	public void allocate() 
    	{
    		bb = ByteBuffer.allocateDirect(MEM_ALLOC);
    		list.add(bb);
    	}

		@Override
		public void run()
		{
			allocate();
			logMessage("allocated ByteBuffer: " + bb.capacity());
    		try
			{
				Thread.sleep(10);
			}
			catch (InterruptedException e)
			{
				logError(e);
				Thread.currentThread().interrupt();
			}
		}
    	
    }

    public static class RunProcess implements Runnable
    {
		@Override
		public void run()
		{
    		try
			{
				Thread.sleep(1000);
			}
			catch (InterruptedException e)
			{
				logError(e);
				Thread.currentThread().interrupt();
			}

    		try
			{
    			logMessage("going to sleep");
	    		Runtime rt = Runtime.getRuntime();
	    		
	    		rt.exec("echo \"Sleeping " + Thread.currentThread().getName() + "\";sleep 1000000");
			}
			catch (IOException ioe)
			{
				logError(ioe);
			}
		}
    	
    }

    public static class SocketProcess implements Runnable, Closeable
    {

		private final DatagramSocket serverSocket;

    	public SocketProcess(int port) throws SocketException
    	{
    		serverSocket = new DatagramSocket(port);
    		logMessage("Server initialized at port " + port);
    	}
    	
		@Override
		public void run()
		{
			try
			{
		        byte[] receiveData = new byte[SOCKET_READ_SIZE]; 
		        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length); 
		        serverSocket.receive(receivePacket);
		        serverSocket.close();
			}
			catch (Exception e)
			{
				logError(e);
			}
		}

		@Override
		public void close() throws IOException
		{
			serverSocket.close();
		}
    	
    }
    
    public static void logError(Throwable t) 
    {
		System.out.println("[" + Thread.currentThread().getName() + "] " + t.getClass() + ": " + t.getMessage());
		// t.printStackTrace();    	
    }

    public static void logMessage(String message) 
    {
		System.out.println("[" + Thread.currentThread().getName() + "] " + message);
    }
    
}
