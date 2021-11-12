import org.apache.commons.lang3.tuple.Pair;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.io.FileWriter;

public class PrinterService extends UnicastRemoteObject implements Printer {
    // Scope operations
    static final int PRINT = 1;
    static final int SEE_QUEUE = 2;
    static final int TOP_QUEUE = 4;
    static final int RESTART = 8;
    static final int START = 16;
    static final int STOP = 32;
    static final int STATUS = 64;
    static final int READ_CONFIG = 128;
    static final int SET_CONFIG = 256;

    // Server config
    static boolean STOPPED = true;
    static String COLOR = "black";
    static String SCALE = "fit";

    public PrinterService() throws RemoteException {
        super();
        this.printers= new HashMap<>();
        this.sessionIdCounter = 0;   //this needs to be incremented in each session creation!!!
        this.currentSession = null;
        this.printers.put("Printer1", new PrinterObject("Printer1"));
        this.printers.put("Printer2", new PrinterObject("Printer2"));
        this.printers.put("Printer3", new PrinterObject("Printer3"));
    }

    private Map<String, PrinterObject> printers;  //service should access to the printers

    private int sessionIdCounter;

    private SessionObject currentSession;  //keeps the current session

    @Override
    public Map<String, PrinterObject> getPrinters(){
        return this.printers;
    }

    @Override
    public String print(String filename, String printer) throws RemoteException {
        // Check user authentication
        if (currentSession==null || !currentSession.isAuthenticated()) {
            return "You do not have access to print server.";
        }

        // Check role permissions
        if (!checkRolePermission(currentSession.getUsername(), PRINT)){
            return "Your role does not have permission to print.";
        }

        // Log action
        try {
            this.log(currentSession.getUsername(), "print");
        } catch (IOException e) {
            e.printStackTrace();
        }

        PrinterObject current = this.printers.get(printer);
        if (current == null){
            return "Printer not found. Try Printer1 or Printer2 or Printer3";
        }
        JobObject job = new JobObject(filename, current.getJobCounter(), this.currentSession.getUsername());
        current.incrementJobCounter();
        current.getJobs().add(job);
        return "Print job is added to queue.";
    }

    @Override
    public String queue(String printer) throws RemoteException {
        // Check user authentication
        if (currentSession==null || !currentSession.isAuthenticated()) {
            return "You do not have access to print server.";
        }

        // Check role permissions
        if (!checkRolePermission(currentSession.getUsername(), SEE_QUEUE)){
            return "Your role does not have permission to see queue.";
        }

        // Log action
        try {
            this.log(currentSession.getUsername(), "queue");
        } catch (IOException e) {
            e.printStackTrace();
        }
        PrinterObject current = this.printers.get(printer);
        if (current == null){
            return "Printer not found. Try Printer1 or Printer2 or Printer3";
        }
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

    @Override
    public String topQueue(String printer, int job) throws RemoteException {
        // Check user authentication
        if (currentSession==null || !currentSession.isAuthenticated()) {
            return "You do not have access to print server.";
        }

        if (!checkRolePermission(currentSession.getUsername(), TOP_QUEUE)){
            return "Your role does not have permission to top queue.";
        }

        // Log action
        try {
            this.log(currentSession.getUsername(), "topQueue");
        } catch (IOException e) {
            e.printStackTrace();
        }
        PrinterObject p = this.printers.get(printer);
        if (p == null){
            return "Printer not found. Try Printer1 or Printer2 or Printer3";
        }

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
    public String login(String username, String password) throws RemoteException{
        //gets user info and assigns session object to current session
        if(checkUsername(username)){  //if username is recorded, create session
            SessionObject session = new SessionObject(this.sessionIdCounter,username,false);  //not authenticated yet
            this.sessionIdCounter++;
            this.currentSession = session;
        }
        else{
            return "Username does not exist.";
        }

        // Log action
        try {
            this.log(currentSession.getUsername(), "start");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(checkPassword(username, password)){
            this.currentSession.setAuthenticated(true);  //now authenticated
            return "Authentication completed.";
        }
        return "Wrong password.";
    }

    @Override
    public String logout() throws RemoteException {
        if (currentSession==null || !currentSession.isAuthenticated()){
            return "You do not have access to print server.";
        }

        // Log action
        try {
            this.log(currentSession.getUsername(), "stop");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //make session object null
        this.currentSession = null;
        return "Session ended.";
    }

    @Override
    public String start() throws RemoteException {
        // Check user authentication
        if (currentSession==null || !currentSession.isAuthenticated()){
            return "You do not have access to print server.";
        }

        // Check role permissions
        if (!checkRolePermission(currentSession.getUsername(), START)){
            return "Your role does not have permission to start server.";
        }

        // Log action
        try {
            this.log(currentSession.getUsername(), "start");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Start server
        STOPPED = false;

        return "Print server started.";
    }

    @Override
    public String stop() throws RemoteException {
        // Check user authentication
        if (currentSession==null || !currentSession.isAuthenticated()) {
            return "You do not have access to print server.";
        }

        // Check role permissions
        if (!checkRolePermission(currentSession.getUsername(), STOP)){
            return "Your role does not have permission to stop server.";
        }

        // Log action
        try {
            this.log(currentSession.getUsername(), "stop");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Stop server
        STOPPED = true;

        return "Print server stopped.";
    }

    @Override
    public String restart() throws RemoteException{
        // Check user authentication
        if (currentSession==null || !currentSession.isAuthenticated()) {
            return "You do not have access to print server.";
        }

        // Check role permissions
        if (!checkRolePermission(currentSession.getUsername(), RESTART)){
            return "Your role does not have permission to restart server.";
        }

        // Log action
        try {
            this.log(currentSession.getUsername(), "restart");
        } catch (IOException e) {
            e.printStackTrace();
        }
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

        // Set config
        COLOR = "black";
        SCALE = "fit";
        STOPPED = false;

        return "Print server restarted.";
    }

    @Override
    public String status(String printer) throws RemoteException {
        // Check user authentication
        if (currentSession==null || !currentSession.isAuthenticated()) {
            return "You do not have access to print server.";
        }

        // Check role permissions
        if (!checkRolePermission(currentSession.getUsername(), STATUS)){
            return "Your role does not have permission to see server status.";
        }

        // Log action
        try {
            this.log(currentSession.getUsername(), "status");
        } catch (IOException e) {
            e.printStackTrace();
        }

        PrinterObject p = this.printers.get(printer);
        if (p == null){
            return "Printer not found. Try Printer1 or Printer2 or Printer3";
        }

        return "Name: "+printer+"\n"+"Number of jobs in queue: "+p.getJobs().size()+"\n";
    }

    @Override
    public boolean checkPassword(String username, String password) throws RemoteException {
        Path pathName = Path.of("data/cred-hashed.txt");
        String actual = "";
        try {
            actual = Files.readString(pathName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Separate records
        var b = actual.split("\r\n");

        // Get username and password
        for (int i = 0; i < b.length; i++) {
            var item = b[i];
            var arguments = item.split(",");
            if (username.equals(arguments[1])) {
                // Hash password
                var saltedPass = this.saltPassword(password, arguments[0]);
                var passHashed = "" + saltedPass.hashCode();

                // Authenticate user password
                if (passHashed.equals(arguments[2])) {
                    // Password is authenticated
                    return true;
                }
            }
        }

        // Log action
        try {
            this.log(username, "Wrong Password");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Password is not authenticated
        return false;
    }

    @Override
    public boolean checkUsername(String username) throws RemoteException {
        Path pathName = Path.of("data/cred-hashed.txt");
        String actual = "";
        try {
            actual = Files.readString(pathName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Separate records
        var b = actual.split("\r\n");

        // Get username and password
        for (int i = 0; i < b.length; i++) {
            var item = b[i];
            var arguments = item.split(",");

            // Check user exists
            if (username.equals(arguments[1])) {
                return true;
            }
        }

        return false;
    }

    @Override
    public String readConfig(String parameter) throws RemoteException {
        // Check user authentication
        if (currentSession==null || !currentSession.isAuthenticated()) {
            return "You do not have access to print server.";
        }

        // Check role permissions
        if (!checkRolePermission(currentSession.getUsername(), READ_CONFIG)){
            return "Your role does not have permission to read configuration.";
        }

        // Log action
        try {
            this.log(currentSession.getUsername(), "readConfig");
        } catch (IOException e) {
            e.printStackTrace();
        }

        String response = "";
        if (parameter.equals("color")){
            response = "Parameter: " + parameter + " has value: " + COLOR;
        } else if (parameter.equals("scale")){
            response = "Parameter: " + parameter + " has value: " + SCALE;
        } else {
            response = "No parameter found.";
        }

        return response;
    }

    @Override
    public String setConfig(String parameter, String value) throws RemoteException {
        // Check user authentication
        if (currentSession==null || !currentSession.isAuthenticated()) {
            return "You do not have access to print server.";
        }

        // Check role permissions
        if (!checkRolePermission(currentSession.getUsername(), SET_CONFIG)){
            return "Your role does not have permission to set configuration.";
        }

        String response = "";
        if (parameter.equals("color")){
            COLOR = value;
            response = "Parameter: " + parameter + " is updated.";
        } else if (parameter.equals("scale")){
            SCALE = value;
            response = "Parameter: " + parameter + " is updated.";
        } else {
            response = "No parameter found.";
        }

        // Log action
        try {
            this.log(currentSession.getUsername(), "setConfig");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }

    public String saltPassword(String password, String salt) {
        return password + "--" + salt;
    }

    public void log(String username, String functionName) throws IOException {
        // Enable write mode for log file
        var writer = new FileWriter("data/log.txt", true);
        var item = "User: " + username + ", Action: " + functionName + ", Timestamp: "+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        writer.write("" + item + "\r\n");
        writer.close();
    }

    public boolean checkRolePermission(String username, Integer scope) {
        try {
            // Step 1. Get assigned role to the username
            Path pathUserRoles = Path.of("data/user-roles.txt");
            String roleFile = Files.readString(pathUserRoles);

            // Separate records
            var roles = roleFile.split("\r\n");

            // Get username role
            var role = "";
            for (int i = 0; i < roles.length; i++) {
                var item = roles[i];
                var arguments = item.split(",");
                if (username.equals(arguments[0])) {
                    role = arguments[1];
                }
            }

            // Step 2. Get assigned permissions to the role
            Path pathRolePermissions = Path.of("data/role-permissions.txt");
            String permissionFile = Files.readString(pathRolePermissions);

            // Separate records
            var permissions = permissionFile.split("\r\n");

            // Get username role
            var permission = 0;
            for (int i = 0; i < permissions.length; i++) {
                var item = permissions[i];
                var arguments = item.split(",");
                if (role.equals(arguments[0])) {
                    try {
                        permission = Integer.parseInt(arguments[1]);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
            }

            // Step 3. Check that role_permissions AND operation_scope should be equal to operation_scope
            return (permission & scope) == scope;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean isPrintServerStopped() throws RemoteException{
        return STOPPED;
    }
}
