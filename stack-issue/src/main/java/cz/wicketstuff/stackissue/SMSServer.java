package cz.wicketstuff.stackissue;

import java.io.*; 
import java.net.*; 
import java.util.*;

public class SMSServer { 

   public static String message;
   public static InetAddress SenAdd;
   public InetAddress RectAdd;
   public static int portnum;
   private static int clientId = 0;


    public SMSServer(){
    message=" ";
    SenAdd=null;
    RectAdd=null;
    portnum=0;
    }

    public SMSServer(String msg, InetAddress SenAdd, int port, InetAddress RectAdd){
    this.message=msg;
    this.SenAdd=SenAdd;
    this.RectAdd=RectAdd;
    this.portnum=port;
    }

    public String getMsg(){return this.message;}
    public InetAddress getsAdd(){return this.SenAdd;}
    public InetAddress getrAdd(){return this.RectAdd;}
    public int getPort(){return this.portnum;}


public static void main(String args[]) throws IOException 
{ 


    DatagramSocket serverSocket = null;
    boolean listening = true;
int port = 9776;

// Setup socket 
try {
        serverSocket = new DatagramSocket(port); //it has to be from type serverSocket
        System.out.println("Server started at port# "+port);

        int i = 0;
            while (listening) {
    MultiServerThread1 server = new MultiServerThread1(serverSocket, clientId++); // thread for each client
    		System.out.println("starting server-" + i);
            new Thread(server, "server-"+i).start();
            i++;
        } //end while    
    //serverSocket.close();
    } 

    catch (Exception e) {
        System.err.println("Could not start on port: "+port);
        System.exit(1);
    }



 /*  finally {
    if(serverSocket != null) 
        serverSocket.close();
    }*/

 //serverSocket.close(); 
}// end main 








} //end SMSserver

class MultiServerThread1  implements Runnable {

    private DatagramSocket serverSocket=null;
    private int clientId=0;
    public List<SMSServer> myList = new ArrayList<SMSServer>(); //list of object server 


public MultiServerThread1(DatagramSocket serverSocket,int clientId) {
    this.serverSocket = serverSocket;
            this.clientId = clientId;
}


    @Override
public void run() {


    try {

        byte[] receiveData = new byte[1024]; 
        byte[] sendData  = new byte[1024]; 



  while(true) 
    { 

      DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length); 
      serverSocket.receive(receivePacket); // receive msgs from clients

      String segment = new String(receivePacket.getData());  // UDP packet
      InetAddress SendIP = receivePacket.getAddress(); // IP of client sender
      int port = receivePacket.getPort(); //port of client sender

      String[] parts = segment.split("-"); //to split the packet into
      String msg = parts[0]; //the message
      String msg2 = parts[1]; //the ip of the receiver of the msg

      InetAddress RecIP=InetAddress.getByName(msg2); //convert string to ip

      SMSServer client = new SMSServer(msg, SendIP, port, RecIP); // creat object server
      myList.add(client); // add the object to the list


    for(int i=0; i<myList.size(); i++){ // check all the objects


        if ( myList.get(i).getrAdd().equals(RecIP)) // check if object receiver IP is the same as the current client IP?
    {
       sendData=myList.get(i).getMsg().getBytes(); //get the msg from the object
       DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, RecIP, port);//send the msg to this dedicated client
       serverSocket.send(sendPacket); 
    }

    }//end for

    } //end while   

    } 
            catch (Exception e) {
    System.out.println("Error: " + e.getMessage());

            }

     System.out.println(Thread.currentThread().getName() + " just run.");       

} // run


} // MultiServerThread