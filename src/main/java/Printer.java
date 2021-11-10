import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

public interface Printer extends Remote {
    String print(String filename, String printer) throws IOException;
    String queue(String printer) throws IOException;
    String topQueue(String printer, int job) throws IOException;
    Map<String, PrinterObject> getPrinters() throws RemoteException;
    String login(String username, String password) throws RemoteException;
    String logout() throws RemoteException;
    String start() throws IOException;
    String stop() throws IOException;
    String restart() throws IOException;
    String status(String printer) throws IOException;
    String readConfig(String parameter) throws IOException;
    String setConfig(String parameter, String value) throws IOException;
    boolean checkPassword(String username, String password) throws RemoteException;
    boolean checkUsername(String username) throws RemoteException;
    boolean authorize(String username, int job) throws IOException;
    boolean isPrintServerStopped() throws RemoteException;
}



