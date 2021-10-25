public class JobObject {


    private String filename;
    private int id;
    private String username;


    public JobObject(String filename, int id, String username) {

        this.filename = filename;
        this.id = id;
        this.username = username;

    }

    public String getFilename() {
        return filename;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
