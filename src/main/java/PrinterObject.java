import java.io.Serializable;
import java.util.LinkedList;
import java.util.Queue;

public class PrinterObject implements Serializable {

    private String name;
    private Queue<String> jobs;  //keeps the queue of filenames to print


    public PrinterObject(String name) {
        this.name = name;
        this.jobs = new LinkedList<>();
    }

    public String getName() {
        return name;
    }

    public Queue<String> getJobs() {
        return jobs;
    }

    @Override
    public String toString() {
        return name;
    }
}
