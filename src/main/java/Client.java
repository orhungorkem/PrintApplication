import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Client {

    public static void main(String [] args) throws MalformedURLException, NotBoundException, RemoteException {
        Printer printer = (Printer) Naming.lookup("rmi://localhost:3099/printer");
        System.out.println(printer.printFromQueue("Custom input which will be changed"));
    }
}
