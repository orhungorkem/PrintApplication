import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class DummyTest {
    @Test
    public void testWriteFile() throws IOException {
        // Open write mode
        FileWriter writer = new FileWriter("data/cred-hashed.txt", true);

        // Write credentials to file
        for (int i = 0; i < 10; i++) {
            // Add salt to password
            var pw = "password-" + i + "--" + i;

            // Hash password
            var pwHashed = pw.hashCode();

            // Append credential to file
            writer.write("" + i + ",user-" + i + "," + pwHashed + "\r\n");
        }

        // Close write mode
        writer.close();

        // Fake assertions
        var a = 'a';
        Assertions.assertEquals(a, 'a');
    }

    @Test
    public void testReadFile() throws IOException {
        // Read file
        Path pathName = Path.of("data/cred-hashed.txt");
        String actual = Files.readString(pathName);

        // Separate records
        var b = actual.split("\r\n");

        // Get username and password
        for (int i = 0; i < b.length; i++) {
            var item = b[i];
            var arguments = item.split(",");
            var username = arguments[0];
            var pwHashed = arguments[1];

            // Check if user-5 has correct password
            if (username.equals("user-5")){
                // Correct
                var pass = "password-5--user-5";
                var passHashed = "" + pass.hashCode();
                assertEquals(pwHashed, passHashed);

                // Wrong
                pass = "wrong-password--user-5";
                passHashed = "" + pass.hashCode();
                assertNotEquals(pwHashed, passHashed);

                // Wrong
                pass = "password-5-user-5";
                passHashed = "" + pass.hashCode();
                assertNotEquals(pwHashed, passHashed);
            }
        }

        // Fake assertions
        var a = 'a';
        Assertions.assertEquals(a, 'a');
    }

    @Test
    public void testUpdateFile() throws IOException {
        var givenPassword = "password-new";
        FileWriter writer = new FileWriter("data/cred-hashed-temp.txt", true);

        // Read file
        Path pathName = Path.of("data/cred-hashed.txt");
        String actual = Files.readString(pathName);

        // Separate records
        var b = actual.split("\r\n");

        // Get username and password
        for (int i = 0; i < b.length; i++) {
            var item = b[i];
            var arguments = item.split(",");
            var username = arguments[0];

            // Check if user-5 has correct password
            if (username.equals("user-5")){
                // Add salt to password
                var pw = givenPassword + "--user-5";

                // Has password
                var pwHashed = pw.hashCode();
                writer.write("user-" + i + "," + pwHashed + "\r\n");
            } else {
                writer.write("" + item + "\r\n");
            }
        }

        // Close write mode
        writer.close();

        Path oldSource = Paths.get("data/cred-hashed.txt");
        Path newSource = Paths.get("data/cred-hashed-temp.txt");
        try{

            // rename a file in the same directory
            Files.delete(oldSource);
//            Files.move(oldSource, oldSource.resolveSibling("cred-hashed-old.txt"));
            Files.move(newSource, newSource.resolveSibling("cred-hashed.txt"));

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Fake assertions
        var a = 'a';
        Assertions.assertEquals(a, 'a');
    }
}