import org.apache.commons.lang3.tuple.Pair;

import java.nio.charset.StandardCharsets;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class PrinterService extends UnicastRemoteObject implements Printer {

    public PrinterService() throws RemoteException {
        super();
        this.printers= new HashMap<>();
        this.sessionIdCounter = 1;   //this needs to be incremented in each session creation!!!
        this.currentSession = null;
        this.printers.put("Printer 1",new PrinterObject("Printer 1"));
        this.printers.put("Printer 2",new PrinterObject("Printer 2"));
        this.printers.put("Printer 3",new PrinterObject("Printer 3"));
    }

    private Map<String, PrinterObject> printers;  //service should access to the printers

    private int sessionIdCounter;

    private SessionObject currentSession;  //keeps the current session

    @Override
    public Map<String, PrinterObject> getPrinters(){
        return this.printers;
    }

    @Override
    public void print(String filename, String printer, String username){
        PrinterObject current = this.printers.get(printer);
        JobObject job = new JobObject(filename, current.getJobCounter(), username);
        current.incrementJobCounter();
        current.getJobs().add(job);
    }

    @Override
    public void queue(String printer, String username) throws RemoteException {
        PrinterObject current = this.printers.get(printer);
        List<JobObject> jobs = current.getJobs();
        for(int i = 0;i<jobs.size();i++){
            JobObject job = jobs.get(i);
            if(job.getUsername().equals(username)) {
                System.out.println(job.getId() + " , " + job.getFilename());
            }
        }
    }

    @Override
    public void topQueue(String printer, int job) throws RemoteException {

        //put the job to the top of "user's queue"
    }

    public void start(String username, String password){

        //gets user info and assigns session object to current session


        return;
    }


    public boolean stop(){
        //make session object null
        return true;
    }

    public void restart(){
        //empty the queue for the relevant user
        //make current session null and assign it to a neww session
        return;
    }



    public void status(String printer){
        //show printer metadata (name and number of jobs in the queue)
    }






}