import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class Client {
    public static void printMenu() {
        //the menu with available options
        System.out.println("-------------Menu--------------");
        System.out.println("Press 0 to start server");
        System.out.println("Press 1 to print");
        System.out.println("Press 2 to see queue");
        System.out.println("Press 3 to put a job to top of the queue");
        System.out.println("Press 4 to restart server");
        System.out.println("Press 5 to stop server");
        System.out.println("Press 6 to see printer status");
        System.out.println("Press 7 to see a user configuration parameter");
        System.out.println("Press 8 to edit a user configuration parameter");
        System.out.println("Press 9 to login");
        System.out.println("Press 10 to logout");
        System.out.println("Press 11 to turn off application");
    }

    public static void loginMenu(Scanner in, Printer printServer) throws RemoteException {
        System.out.print("Username: ");
        String username = in.next();
        System.out.print("Password: ");
        String password = in.next();
        System.out.println(printServer.login(username, password));

    }


    public static void main(String[] args) throws IOException, NotBoundException {


        Scanner in = new Scanner(System.in);
        Printer printServer = (Printer) Naming.lookup("rmi://localhost:3099/printer");
        while (true) {
            printMenu();
            String choice = in.next();
            if (choice.equals("0")) {
                String response = printServer.start();
                System.out.println(response);
            } else if (choice.equals("1")) {
                // Check server is up
                String response = "";
                if (printServer.isPrintServerStopped()) {
                    System.out.println("");
                    response = "Printer server is not running.";
                } else {
                    System.out.print("Printer name: ");
                    String printer = in.next();
                    System.out.println("");
                    System.out.print("File name: ");
                    String filename = in.next();
                    System.out.println("");
                    response = printServer.print(filename, printer);
                }
                System.out.println(response);
            } else if (choice.equals("2")) {
                String response = "";
                if (printServer.isPrintServerStopped()) {
                    System.out.println("");
                    response = "Printer server is not running.";
                } else {
                    System.out.print("Printer name: ");
                    String printer = in.next();
                    System.out.println("");
                    response = printServer.queue(printer);
                }
                System.out.println(response);
            } else if (choice.equals("3")) {
                String response = "";
                if (printServer.isPrintServerStopped()) {
                    System.out.println("");
                    response = "Printer server is not running.";
                } else {
                    System.out.print("Printer name: ");
                    String printer = in.next();
                    System.out.println("");
                    System.out.print("Job id: ");
                    int job = in.nextInt();
                    System.out.println("");
                    response = printServer.topQueue(printer, job);
                }
                System.out.println(response);
            } else if (choice.equals("4")) {
                String response = "";
                if (printServer.isPrintServerStopped()) {
                    System.out.println("");
                    response = "Printer server is not running.";
                } else {
                    response = printServer.restart();
                }
                System.out.println(response);
            } else if (choice.equals("5")) {
                String response = "";
                if (printServer.isPrintServerStopped()) {
                    System.out.println("");
                    response = "Printer server is not running.";
                } else {
                    response = printServer.stop();
                }
                System.out.println(response);
            } else if (choice.equals("6")) {
                String response = "";
                if (printServer.isPrintServerStopped()) {
                    System.out.println("");
                    response = "Printer server is not running.";
                } else {
                    System.out.print("Printer name: ");
                    String printer = in.next();
                    System.out.println("");
                    response = printServer.status(printer);
                }
                System.out.println(response);
            } else if (choice.equals("7")) {
                String response = "";
                if (printServer.isPrintServerStopped()) {
                    System.out.println("");
                    response = "Printer server is not running.";
                } else {
                    System.out.print("Type the parameter name: ");
                    String parameter = in.next();
                    System.out.println("");
                    response = printServer.readConfig(parameter);
                }
                System.out.println(response);
            } else if (choice.equals("8")) {
                String response = "";
                if (printServer.isPrintServerStopped()) {
                    System.out.println("");
                    response = "Printer server is not running.";
                } else {
                    System.out.print("Type the parameter name: ");
                    String parameter = in.next();
                    System.out.print("Type the parameter value: ");
                    String value = in.next();
                    System.out.println("");
                    response = printServer.setConfig(parameter, value);
                }
                System.out.println(response);
            } else if (choice.equals("9")) {
                loginMenu(in, printServer);
            } else if (choice.equals("10")) {
                String response = printServer.logout();
                System.out.println(response);
            } else if (choice.equals("11")) {
                System.out.println("Turning off...");
                break;
            } else {
                System.out.println("Printer server is confused O_O ... Please try again by pressing a number from the above list of actions.");
            }
        }


    }




    }

