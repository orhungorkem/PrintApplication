import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Client {

    public static void main(String [] args) throws MalformedURLException, NotBoundException, RemoteException {
        Printer printServer = (Printer) Naming.lookup("rmi://localhost:3099/printer");
        printServer.start("user 1","mock_password");
        System.out.println(printServer.print("aaaa","Printer 1","user 1"));
        printServer.print("bbbb","Printer 1","user 1");
        printServer.print("aaaa","Printer 1","user 2");
        printServer.print("bbbb","Printer 1","user 1");
        System.out.println(printServer.queue("Printer 1","user 1"));
        System.out.println(printServer.status("Printer 1"));
        System.out.println(printServer.restart());
        System.out.println(printServer.queue("Printer 1","user 1"));
        //printServer.topQueue("Printer 1",2);
        //printServer.queue("Printer 1");
    }
}
