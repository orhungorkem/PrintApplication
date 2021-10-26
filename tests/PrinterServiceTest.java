import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.rmi.RemoteException;

class PrinterServiceTest {
    PrinterService service;

    @Test
    public void testAuthenticate() throws RemoteException {
        service = new PrinterService();

        // Correct pass
        var result = service.authenticate("user-5", "password-5");
        Assertions.assertTrue(result);

        // Wrong pass
        result = service.authenticate("user-5", "what?");
        Assertions.assertFalse(result);

        // No user found
        result = service.authenticate("user-who?", "password-5");
        Assertions.assertFalse(result);

        // Fake assertions
        var a = 'a';
        Assertions.assertEquals(a, 'a');
    }
}