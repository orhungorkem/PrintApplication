import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class Client {

    public void printMenu(){
        //the menu with available options
    }

    public static void main(String [] args) throws MalformedURLException, NotBoundException, RemoteException {

        Scanner in = new Scanner(System.in);

        Printer printServer = (Printer) Naming.lookup("rmi://localhost:3099/printer");

        boolean on = true;

        while(on){
            break;

            //we need to fill this part


        }




        /*
        printServer.start("user 1","mock_password");
        System.out.println(printServer.print("aaaa","Printer 1","user 1"));
        printServer.print("bbbb","Printer 1","user 1");
        printServer.print("aaaa","Printer 1","user 2");
        printServer.print("bbbb","Printer 1","user 1");
        System.out.println(printServer.topQueue("Printer 1",12));
        System.out.println(printServer.queue("Printer 1"));

        System.out.println(printServer.queue("Printer 1","user 1"));
        System.out.println(printServer.status("Printer 1"));
        System.out.println(printServer.restart());
        System.out.println(printServer.queue("Printer 1","user 1"));
        */
    }
}
