import org.apache.commons.lang3.tuple.Pair;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class PrinterObject implements Serializable {

    private String name;
    private List<JobObject> jobs;  //queue for the jobs
    private int jobCounter;  //determines the id to give to the new job



    public PrinterObject(String name) {
        this.name = name;
        this.jobs = new LinkedList<>();
        this.jobCounter = 1;
    }

    public String getName() {
        return name;
    }

    public int getJobCounter() {
        return jobCounter;
    }

    public void incrementJobCounter() { //increments coutner after each print
        this.jobCounter++;
    }

    public List<JobObject> getJobs() {
        return jobs;
    }

    @Override
    public String toString() {
        return name;
    }
}
