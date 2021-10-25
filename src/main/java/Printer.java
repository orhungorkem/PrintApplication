import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

public interface Printer extends Remote {
    String print(String filename, String printer, String username) throws RemoteException;
    String queue(String printer, String username) throws RemoteException;
    void topQueue(String printer, int job) throws RemoteException;
    Map<String, PrinterObject> getPrinters() throws RemoteException;
    void start(String username, String password) throws RemoteException;
    void stop() throws RemoteException;
    String restart() throws RemoteException;
    String status(String printer) throws RemoteException;
    boolean authenticate(String username, String password) throws RemoteException;
    boolean checkUsername(String username) throws RemoteException;
}



