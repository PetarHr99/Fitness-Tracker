package bg.softuni.finalproject;

import bg.softuni.finalproject.config.UserSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserSessionTest {

    private UserSession userSession;

    @BeforeEach
    void setUp() {
        userSession = new UserSession();
    }

    //testLogin: This test ensures that the login method sets the
    // id and username correctly and marks the session as logged in.
    @Test
    void testLogin() {
        userSession.login(1L, "testuser");
        assertEquals(1L, userSession.id());
        assertEquals("testuser", userSession.getUsername());
        assertTrue(userSession.isLoggedIn());
    }

    //testGetUsername: This test ensures that the getUsername method returns the correct username.
    @Test
    void testGetUsername() {
        userSession.setUsername("testuser");
        assertEquals("testuser", userSession.getUsername());
    }

    //testId: This test ensures that the id method returns the correct id after login.
    @Test
    void testId() {
        userSession.login(1L, "testuser");
        assertEquals(1L, userSession.id());
    }

    //testIsLoggedIn: This test ensures that the isLoggedIn method correctly identifies if the user is logged in or not.
    @Test
    void testIsLoggedIn() {
        assertFalse(userSession.isLoggedIn());
        userSession.login(1L, "testuser");
        assertTrue(userSession.isLoggedIn());
    }

    //testLogout: This test ensures that the logout method resets the id and username
    // and marks the session as not logged in.
    @Test
    void testLogout() {
        userSession.login(1L, "testuser");
        userSession.logout();
        assertEquals(0, userSession.id());
        assertNull(userSession.getUsername());
        assertFalse(userSession.isLoggedIn());
    }
}
