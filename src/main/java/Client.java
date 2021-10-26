import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class Client {

    public void printMenu(){
        //the menu with available options
        System.out.println("-------------Menu--------------");
        System.out.println("Press 1 to print");
        System.out.println("Press 2 to see queue");
        System.out.println("Press 3 to put a job to top of the queue");
        System.out.println("Press 4 to restart server");
        System.out.println("Press 5 to stop server");
        System.out.println("Press 6 to see printer status");
        System.out.println("Press 0 to Authenticate");
        //may add read config set config

    }


    public void loginMenu(Scanner in, Printer printServer) throws RemoteException {
        System.out.print("Username: ");
        String username = in.next();
        System.out.print("Password: ");
        String password = in.next();

        System.out.println(printServer.start(username,password));

    }

    public static void main(String [] args) throws MalformedURLException, NotBoundException, RemoteException {

        Scanner in = new Scanner(System.in);

        Printer printServer = (Printer) Naming.lookup("rmi://localhost:3099/printer");




        boolean on = true;

        System.out.print("Enter a string: ");
        String str= in.nextLine();

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
