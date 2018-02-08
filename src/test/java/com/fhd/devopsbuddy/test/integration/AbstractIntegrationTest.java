package com.fhd.devopsbuddy.test.integration;

import com.fhd.devopsbuddy.backend.persistence.domain.backend.Plan;
import com.fhd.devopsbuddy.backend.persistence.domain.backend.Role;
import com.fhd.devopsbuddy.backend.persistence.domain.backend.User;
import com.fhd.devopsbuddy.backend.persistence.domain.backend.UserRole;
import com.fhd.devopsbuddy.backend.persistence.repositories.PlanRepository;
import com.fhd.devopsbuddy.backend.persistence.repositories.RoleRepository;
import com.fhd.devopsbuddy.backend.persistence.repositories.UserRepository;
import com.fhd.devopsbuddy.enums.PlansEnum;
import com.fhd.devopsbuddy.enums.RolesEnum;
import com.fhd.devopsbuddy.utils.UserUtils;
import org.junit.rules.TestName;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractIntegrationTest {
    @Autowired
    protected PlanRepository planRepository;

    @Autowired
    protected RoleRepository roleRepository ;

    @Autowired
    protected UserRepository userRepository;

    protected Plan createPlan(PlansEnum plansEnum) {
        return new Plan(plansEnum);
    }

    protected Role createRole(RolesEnum rolesEnum) {

        return new Role(rolesEnum);
    }

    protected User createUser(String username, String email) {
        Plan basicPlan = createPlan(PlansEnum.BASIC);
        planRepository.save(basicPlan);

        User basicUser = UserUtils.createBasicUser(username, email);
        basicUser.setPlan(basicPlan);

        Role basicRole = createRole(RolesEnum.BASIC);
        roleRepository.save(basicRole);

        Set<UserRole> userRoles = new HashSet<>();
        UserRole userRole = new UserRole(basicUser, basicRole);
        userRoles.add(userRole);

        basicUser.getUserRoles().addAll(userRoles);
        basicUser = userRepository.save(basicUser);
        return basicUser;
    }

    protected User createUser(TestName testName) {
        return createUser(testName.getMethodName(), testName.getMethodName() + "@devopsbuddy.com");
    }
}
