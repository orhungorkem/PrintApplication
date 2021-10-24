import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ApplicationServer {

    public static void main(String[] args) throws RemoteException {
        Registry registry = LocateRegistry.createRegistry(3099);
        registry.rebind("printer",new PrinterService());
    }


}
