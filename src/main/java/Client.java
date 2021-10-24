import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Client {

    public static void main(String [] args) throws MalformedURLException, NotBoundException, RemoteException {
        Printer printServer = (Printer) Naming.lookup("rmi://localhost:3099/printer");
        printServer.print("aaaa","Printer 1");
        printServer.print("bbbb","Printer 1");
        System.out.println(printServer.getPrinters().get("Printer 1").getJobs().toString());
    }
}
