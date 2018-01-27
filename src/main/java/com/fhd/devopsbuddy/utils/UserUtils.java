package com.fhd.devopsbuddy.utils;

import com.fhd.devopsbuddy.backend.persistence.domain.backend.User;

public class UserUtils {
    /**
     * Non instantiable.
     */
    private UserUtils() {
        throw new AssertionError("Non instantiable");
    }

    /**
     * Creates a user with basic attributes set.
     * @return A User entity
     */
    public static User createBasicUser() {

        User user = new User();
        user.setUsername("basicUser");
        user.setPassword("secret");
        user.setEmail("me@example.com");
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setPhoneNumber("123456789123");
        user.setCountry("GB");
        user.setEnabled(true);
        user.setDescription("A basic user");
        user.setProfileImageUrl("https://blabla.images.com/basicuser");

        return user;
    }
}
