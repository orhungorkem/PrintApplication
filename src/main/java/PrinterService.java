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
    public String print(String filename, String printer, String username){
        if(currentSession!=null && currentSession.isAuthenticated()) {
            PrinterObject current = this.printers.get(printer);
            JobObject job = new JobObject(filename, current.getJobCounter(), username);
            current.incrementJobCounter();
            current.getJobs().add(job);
            return "Print job is added to queue.";
        }
        else{
            return "You do not have access to print server.";
        }
    }

    @Override
    public String queue(String printer) throws RemoteException {

        if(currentSession!=null && currentSession.isAuthenticated()) {
            PrinterObject current = this.printers.get(printer);
            List<JobObject> jobs = current.getJobs();
            String response = "Print Queue for "+printer+"\n";
            for (int i = 0; i < jobs.size(); i++) {
                JobObject job = jobs.get(i);
                if (job.getUsername().equals(this.currentSession.getUsername())) {
                    response += job.getId() + " , " + job.getFilename()+"\n";
                }
            }
            return response;
        }
        return "You do not have access to print server.";
    }

    @Override
    public String topQueue(String printer, int job) throws RemoteException {

        //put the job to the top of "user's queue"
        if(currentSession==null || !currentSession.isAuthenticated()){
            return "You do not have access to print server.";
        }

        PrinterObject p = this.printers.get(printer);
        List<JobObject> jobs = p.getJobs();
        List<JobObject> userJobs = new LinkedList<>();
        List<Integer> indices = new LinkedList<>();
        int index = -1;
        for(int i = 0;i<jobs.size();i++){
            if(jobs.get(i).getUsername().equals(this.currentSession.getUsername())){
                userJobs.add(jobs.get(i));
                indices.add(i);
                if(jobs.get(i).getId()==job){  //the job to top
                    index = i;
                    break;
                }
            }
        }
        if(index==-1){
            return "Job not found.";
        }
        JobObject topJob = jobs.get(index);
        for(int i = userJobs.size()-2 ; i>=0 ; i--) {
            userJobs.set(i+1, userJobs.get(i));
        }
        userJobs.set(0,topJob);
        for (int i=0; i<indices.size();i++){
            jobs.set(indices.get(i), userJobs.get(i));
        }
        return "Job "+job+" moved to top of the queue.";


    }

    @Override
    public String start(String username, String password) throws RemoteException{

        //gets user info and assigns session object to current session
        if(checkUsername(username)){  //if username is recorded, create session
            SessionObject session = new SessionObject(this.sessionIdCounter,username,false);  //not authenticated yet
            this.sessionIdCounter++;
            this.currentSession = session;
        }
        else{
            return "Username does not exist.";
        }
        if(authenticate(username, password)){
            this.currentSession.setAuthenticated(true);  //now authenticated
            return "Authentication completed.";
        }
        return "Wrong password.";
    }

    @Override
    public String stop(){
        //make session object null
        this.currentSession = null;
        return "Session ended.";
    }

    @Override
    public String restart(){
        //empty the queue for the relevant user
        //make current session null and assign it to a neww session
        if(currentSession!=null && currentSession.isAuthenticated()) {
            for (String name : printers.keySet()) {
                PrinterObject printer = printers.get(name);
                List<JobObject> jobs = printer.getJobs();
                Iterator<JobObject> i = jobs.iterator();
                while (i.hasNext()) {
                    JobObject job = i.next();
                    if (job.getUsername().equals(this.currentSession.getUsername())) {  //job belongs to the current user
                        i.remove();
                    }
                }
            }
            this.currentSession = new SessionObject(this.sessionIdCounter, this.currentSession.getUsername(), true);
            return "Restarting session.";
        }
        return "You do not have access to print server.";


    }


    @Override
    public String status(String printer){
        //show printer metadata (name and number of jobs in the queue)
        PrinterObject p = this.printers.get(printer);
        return "Name: "+printer+"\n"+"Number of jobs in queue: "+p.getJobs().size()+"\n";
    }

    @Override
    public boolean authenticate(String username, String password) throws RemoteException {

        //fill with authentication details
        return true;
    }

    @Override
    public boolean checkUsername(String username) throws RemoteException {

        //check that username is in database
        return true;
    }


}