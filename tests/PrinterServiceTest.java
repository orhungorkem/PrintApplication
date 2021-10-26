import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.rmi.RemoteException;

class PrinterServiceTest {
    PrinterService service;

    @Test
    public void testAuthenticate() throws RemoteException {
        service = new PrinterService();

        // Correct pass
        var result = service.checkPassword("user-5", "password-5");
        Assertions.assertTrue(result);

        // Wrong pass
        result = service.checkPassword("user-5", "what?");
        Assertions.assertFalse(result);

        // Fake assertions
        var a = 'a';
        Assertions.assertEquals(a, 'a');
    }

    @Test
    public void testCheckUsername() throws RemoteException {
        service = new PrinterService();

        // Correct pass
        var result = service.checkUsername("user-5");
        Assertions.assertTrue(result);

        // No user found
        result = service.checkUsername("user-who?");
        Assertions.assertFalse(result);

        // Fake assertions
        var a = 'a';
        Assertions.assertEquals(a, 'a');
    }

    @Test
    public void testReadConfig() throws RemoteException {
        service = new PrinterService();

        // Open session
        service.start("user-5", "password-5");

        // Read config
        var result = service.readConfig("username");
        Assertions.assertEquals("Username: user-5\n" , result);

        result = service.readConfig("username");
        Assertions.assertEquals("Username: user-5\n" , result);

        // Fake assertions
        var a = 'a';
        Assertions.assertEquals(a, 'a');
    }

    @Test
    public void testSetConfig() throws RemoteException {
        service = new PrinterService();

        // Open session
        service.start("user-5", "password-5");

        // Read config
        var result = service.setConfig("username", "user-5-wow");
        Assertions.assertEquals("username is updated \n" , result);

        result = service.readConfig("username");
        Assertions.assertEquals("Username: user-5-wow\n" , result);

        // Roll back changes
        result = service.setConfig("username", "user-5");
        Assertions.assertEquals("username is updated \n" , result);

        result = service.readConfig("username");
        Assertions.assertEquals("Username: user-5\n" , result);

        // Fake assertions
        var a = 'a';
        Assertions.assertEquals(a, 'a');
    }
}