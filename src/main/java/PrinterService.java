import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class PrinterService extends UnicastRemoteObject implements Printer {

    public PrinterService() throws RemoteException {
        super();
    }

    @Override
    public String printFromQueue(String clientMessage) {
        return clientMessage;
    }

}