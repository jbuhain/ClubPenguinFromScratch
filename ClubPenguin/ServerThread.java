
import java.net.*;
import java.io.*;

public class ServerThread implements Runnable{
    private Socket clientSocket;
    private Manager m1;
    private PrintWriter out; //not using this
  
	private ObjectOutputStream outObj;
	private PushbackInputStream inObj;
	private ObjectInputStream inObjREAL;
	private int threadCode;
	private int time_loop;
	
    public ServerThread(Socket clientSocket, Manager m1, int referenceNum){
        this.clientSocket = clientSocket;
		this.m1 = m1;
        this.threadCode = referenceNum;
		time_loop = 0;
		try{
              out = new PrintWriter(clientSocket.getOutputStream(), true);
			  outObj = new ObjectOutputStream(clientSocket.getOutputStream());
        } catch (IOException ex){
            m1.removeThread(this);
            System.out.println("Error listening for a connection");
            System.out.println(ex.getMessage());
        }
    }
    public void run(){
        System.out.println(Thread.currentThread().getName() + ": connection opened");
        try{
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); //not using rn
            inObj = new PushbackInputStream(clientSocket.getInputStream());		
			inObjREAL = new ObjectInputStream(clientSocket.getInputStream());

            while(true){
				try {
					Thread.sleep(10);
				} catch(InterruptedException ex) {
					Thread.currentThread().interrupt();
				}
				time_loop++;
                while(inObj.available()>0){
					try{
						Object obj = inObjREAL.readObject();
						if(obj.toString().equals("Next Sensei Dialogue")){
							m1.broadcast("Next Sensei Dialogue");
						}
						if(obj.toString().equals("Ice Drop")){
							m1.broadcast("Ice Drop");
						}
						else if(obj instanceof Player){
							Player temp = (Player) obj;
							temp.setIDCode(threadCode);
							//System.out.println("code:" + temp);
							//System.out.println("RUN AT SERVERTHREAD");
							//System.out.println(temp.toString());
							m1.broadcast(temp);
							//System.out.println("WAT");
						}
					}catch (ClassNotFoundException e) {
						System.err.println("Class does not exist" + e);
						System.exit(1);
					}catch (IOException ex){
						m1.removeThread(this);
						System.out.println("Error listening for a connection");
						System.out.println(ex.getMessage());
					}
                    
                }
				if(time_loop==500){
					m1.broadcast("attack");
				}
				if(time_loop==750){
					m1.broadcast("disengage");
				}
				if(time_loop==1000){
					time_loop = 0;
				}
            }            
            
            
            //out.close();
            //System.out.println(Thread.currentThread().getName() + ": connection closed.");
        } catch (IOException ex){
            m1.removeThread(this);
            System.out.println("Error listening for a connection");
            System.out.println(ex.getMessage());
        }
    }
  
    
    public void send(Object temp){
		//System.out.println("Sending " + msg);
		//outObj.println(temp);
		try{
			outObj.reset();
			outObj.writeObject(temp);
		}catch (IOException ex){
			m1.removeThread(this);
			System.out.println("Error listening for a connection");
			System.out.println(ex.getMessage());
		}
    }
    
    
    
    
}