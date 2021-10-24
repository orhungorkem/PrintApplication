import org.apache.commons.lang3.tuple.Pair;

import java.nio.charset.StandardCharsets;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class PrinterService extends UnicastRemoteObject implements Printer {

    public PrinterService() throws RemoteException {
        super();
        this.printers= new HashMap<>();
        this.printers.put("Printer 1",new PrinterObject("Printer 1"));
        this.printers.put("Printer 2",new PrinterObject("Printer 2"));
        this.printers.put("Printer 3",new PrinterObject("Printer 3"));
    }

    private Map<String, PrinterObject> printers;  //service should access to the printers

    @Override
    public Map<String, PrinterObject> getPrinters(){
        return this.printers;
    }

    @Override
    public void print(String filename, String printer){
        PrinterObject current = this.printers.get(printer);
        current.getJobs().add(filename);
        current.getJobIds().add(current.getJobCounter());
        current.incrementJobCounter();
    }

    @Override
    public void queue(String printer) throws RemoteException {

        PrinterObject current = this.printers.get(printer);
        List<String> jobs = current.getJobs();
        List<Integer> ids = current.getJobIds();
        for(int i = 0;i<current.getJobs().size();i++){
            System.out.println(ids.get(i)+ " , "+jobs.get(i));
        }
    }

    @Override
    public void topQueue(String printer, int job) throws RemoteException {

        PrinterObject current = this.printers.get(printer);
        List<String> jobs = current.getJobs();
        List<Integer> ids = current.getJobIds();
        int index = ids.indexOf(job);
        String jobFile = jobs.get(index);
        for(int i = index;i>0;i--){
            jobs.set(i,jobs.get(i-1));
            ids.set(i,ids.get(i-1));
        }
        jobs.set(0,jobFile);
        ids.set(0,job);
        System.out.println("Top of the queue is updated");
    }

}