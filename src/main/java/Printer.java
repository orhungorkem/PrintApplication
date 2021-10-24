import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

public interface Printer extends Remote {
    void print(String filename, String printer) throws RemoteException;
    void queue(String printer) throws RemoteException;
    void topQueue(String printer, int job) throws RemoteException;
    Map<String, PrinterObject> getPrinters() throws RemoteException;
}



