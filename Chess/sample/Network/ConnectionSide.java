package sample.Network;

import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ConnectException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.function.Consumer;

public abstract class ConnectionSide {

    //TODO connection thread
    /**
     *consumer deffenitiion:
     * functional interface
     * Consumer can be used in all contexts where an object needs to be consumed,
     * i.e. taken as input, and some operation is to be performed on the object without returning any result.
     * Common example of such an operation is printing where an object is taken as input to the printing
     * function and the value of the object is printed( we will expand upon the printing example in more
     * detail below when understanding how to use Consumer interface).
     */

    private Consumer<Serializable> WhenRecievecallBack;
    // called when recieves the object ...
    private ConnectionThread ConThread = new ConnectionThread();

    public ConnectionSide(Consumer<Serializable> whenRecievedCallBack) {
        this.WhenRecievecallBack = whenRecievedCallBack;

        //always program to exit even connection thread is still running
        ConThread.setDaemon(true);
    }
    //Initialize Connection
    public void StartConnection() throws Exception{
        ConThread.start();
    }
    //send data
    public void  send(Serializable data) throws Exception{
        ConThread.outputStream.writeObject(data);
    }

    //close connection
    public void closeConnection()throws Exception{
        ConThread.socket.close();
    }

    protected abstract boolean isServer();
    protected abstract String getIP();
    protected abstract int getPort();

    //Creating the thread
    private class ConnectionThread extends Thread{
        private Socket socket;
        private ObjectOutputStream outputStream;

        @Override
        public void run() {
            try (
                    ServerSocket serverSocket = isServer() ? new ServerSocket(getPort()) : null ;
                    Socket socket = isServer() ? serverSocket.accept() : new Socket(getIP(),getPort());
                    //craet outputStream
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                    //creat inputStream
                    ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                    ){
                WhenRecievecallBack.accept("connection Accepted");
                this.socket=socket;
                this.outputStream=objectOutputStream;

                while (true){
                    Serializable data = (Serializable) objectInputStream.readObject();
                    WhenRecievecallBack.accept(data);
                }
            }catch (ConnectException e){
                WhenRecievecallBack.accept("Could not connect to server");
            }catch (Exception e){
                WhenRecievecallBack.accept("connection Closed");
            }
        }
    }
}
