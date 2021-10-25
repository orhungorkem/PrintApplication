import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

public interface Printer extends Remote {
    void print(String filename, String printer, String username) throws RemoteException;
    void queue(String printer, String username) throws RemoteException;
    void topQueue(String printer, int job) throws RemoteException;
    Map<String, PrinterObject> getPrinters() throws RemoteException;
    void start(String username, String password) throws RemoteException;
    boolean stop() throws RemoteException;
    void restart() throws RemoteException;
    void status(String printer) throws RemoteException;
}



