import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Printer extends Remote {
    String printFromQueue(String clientInput) throws RemoteException;
}



