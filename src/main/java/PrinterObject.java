import org.apache.commons.lang3.tuple.Pair;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class PrinterObject implements Serializable {

    private String name;
    private List<String> jobs;  //keeps the list of filenames to print
    private List<Integer> jobIds; // keeps the ids of jobs
    private int jobCounter;  //determines the id to give to the new job



    public PrinterObject(String name) {
        this.name = name;
        this.jobs = new LinkedList<>();
        this.jobIds = new LinkedList<>();
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

    public List<String> getJobs() {
        return jobs;
    }

    public List<Integer> getJobIds() {
        return jobIds;
    }

    @Override
    public String toString() {
        return name;
    }
}
