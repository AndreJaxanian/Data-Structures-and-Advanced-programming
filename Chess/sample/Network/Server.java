package sample.Network;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Vector;
import java.util.function.Consumer;

public class Server extends ConnectionSide {
    private int port ;
    static int id;
    ArrayList<User> users = new ArrayList<>();

    public Server(int port , Consumer<Serializable> WhenRecievecallBack) {
        // call the constructor of the its father --> ConnectionSide class ... with super()
        super(WhenRecievecallBack);
        this.port = port;
    }

    @Override
    // it is server so should return true
    protected boolean isServer() {
        return true ;
    }

    @Override
    protected String getIP() {
        return null;
    }

    @Override
    protected int getPort() {
        return port;
    }

}
