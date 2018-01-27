package com.fhd.devopsbuddy.test.integration;

import com.fhd.devopsbuddy.DevopsbuddyApplication;
import com.fhd.devopsbuddy.backend.persistence.domain.backend.Role;
import com.fhd.devopsbuddy.backend.persistence.domain.backend.User;
import com.fhd.devopsbuddy.backend.persistence.domain.backend.UserRole;
import com.fhd.devopsbuddy.backend.service.UserService;
import com.fhd.devopsbuddy.enums.PlansEnum;
import com.fhd.devopsbuddy.enums.RolesEnum;
import com.fhd.devopsbuddy.utils.UserUtils;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = DevopsbuddyApplication.class)
public class UserServiceIntegrationTest {

    @Autowired
    private UserService userService;

    @Rule public TestName testName = new TestName();

    @Test
    public void testCreateNewUser() throws Exception {
        String username = testName.getMethodName();
        String email = testName.getMethodName() + "@devopsbuddy.com";

        Set<UserRole> userRoles = new HashSet<>();
        User basicUser = UserUtils.createBasicUser(username, email);
        userRoles.add(new UserRole(basicUser, new Role(RolesEnum.BASIC)));

        User user = userService.createUser(basicUser, PlansEnum.BASIC, userRoles);
        Assert.assertNotNull(user);
        Assert.assertNotNull(user.getId());

    }
}

