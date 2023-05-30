package mk.ukim.finki.si;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static mk.ukim.finki.si.SILab2.function;
import static org.junit.jupiter.api.Assertions.*;

public class SILab2Test {

    @Test
    public void testEveryBranchCriterium() {
        User user;

        //testNullUser
        assertThrows(RuntimeException.class, () -> function(null, null));

        //testNullPassword
        assertThrows(RuntimeException.class, () -> function(new User(null, null, "user@domain.com"), null));

        //testNullEmail
        assertThrows(RuntimeException.class, () -> function(new User("username", "password", null), null));

        //testNullUsername
        user = new User(null, "password", "user@domain.com");
        function(user, new ArrayList<>());
        assertEquals("user@domain.com", user.getUsername());

        //testEmailWithoutAtOrDot
        assertFalse(function(new User("username", "password", "userdomaincom"), null));

        //testUniqueEmailAndUsername
        user = new User("username", "password", "user@domain.com");
        assertFalse(function(user, Arrays.asList(new User("other", "password", "other@domain.com"))));

        //testDuplicateEmail
        user = new User("username", "password", "user@domain.com");
        assertFalse(function(user, Arrays.asList(new User("other", "password", "user@domain.com"))));

        //testDuplicateUsername
        user = new User("username", "password", "user@domain.com");
        assertFalse(function(user, Arrays.asList(new User("username", "password", "other@domain.com"))));

        //testPasswordContainsUsername
        assertFalse(function(new User("username", "username", "user@domain.com"), new ArrayList<>()));

        // testShortPassword
        assertFalse(function(new User("username", "short", "user@domain.com"), new ArrayList<>()));

        //testPasswordContainsSpace
        assertFalse(function(new User("username", "password with space", "user@domain.com"), new ArrayList<>()));

        //testPasswordWithoutSpecialChar
        assertFalse(function(new User("username", "password", "user@domain.com"), new ArrayList<>()));

        //testPasswordWithSpecialCharButSameNotZero
        user = new User("username", "password#", "user@domain.com");
        assertFalse(function(user, Arrays.asList(new User("username", "password", "user@domain.com"))));

        //testPasswordWithSpecialCharAndSameIsZero
        assertTrue(function(new User("username", "password#", "user@domain.com"), new ArrayList<>()));
    }

    @Test
    public void testMultipleConditionCriterium() {

        //testUserIsNull
        assertThrows(RuntimeException.class, () -> {
            function(null, null);
        });

        //testUserWithNullPassword
        assertThrows(RuntimeException.class, () -> {
            function(new User("username", null, "email@example.com"), null);
        });

        //testUserWithNullEmail
        assertThrows(RuntimeException.class, () -> {
            function(new User("username", "password", null), null);
        });

        //testUserWithNullPasswordAndEmail
        assertThrows(RuntimeException.class, () -> {
            function(new User("username", null, null), null);
        });
    }
}
