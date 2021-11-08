import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

public interface Printer extends Remote {
    String print(String filename, String printer) throws RemoteException;
    String queue(String printer) throws RemoteException;
    String topQueue(String printer, int job) throws RemoteException;
    Map<String, PrinterObject> getPrinters() throws RemoteException;
    String start() throws RemoteException;
    String stop() throws RemoteException;
    String restart() throws RemoteException;
    String status(String printer) throws RemoteException;
    String readConfig(String parameter) throws RemoteException;
    String setConfig(String parameter, String value) throws RemoteException;
    boolean checkPassword(String username, String password) throws RemoteException;
    boolean checkUsername(String username) throws RemoteException;
    String login(String username, String password) throws RemoteException;
    String logout() throws RemoteException;
    boolean isPrintServerStopped() throws RemoteException;
}



