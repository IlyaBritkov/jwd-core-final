package com.epam.jwd.core_final.domain;

import com.epam.jwd.core_final.exception.UnknownEntityException;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class RoleTest {
    private final Logger logger = LoggerFactory.getLogger(RoleTest.class);

    @Test
    public void getNameMethodShouldReturnAppropriateResult() {
        Role role = Role.MISSION_SPECIALIST;
        Assert.assertEquals("MISSION_SPECIALIST", role.getName());

        role = Role.FLIGHT_ENGINEER;
        Assert.assertEquals("FLIGHT_ENGINEER", role.getName());

        role = Role.PILOT;
        Assert.assertEquals("PILOT", role.getName());

        role = Role.COMMANDER;
        Assert.assertEquals("COMMANDER", role.getName());
    }

    @Test
    public void resolveRoleByIdMethodShouldReturnAppropriateResult() {
        Role role = Role.resolveRoleById(1);
        Assert.assertEquals("MISSION_SPECIALIST", role.getName());

        role = Role.resolveRoleById(2);
        Assert.assertEquals("FLIGHT_ENGINEER", role.getName());

        role = Role.resolveRoleById(3);
        Assert.assertEquals("PILOT", role.getName());

        role = Role.resolveRoleById(4);
        Assert.assertEquals("COMMANDER", role.getName());
    }

    @Test
    public void resolveRoleByIdMethodShouldThrowUnknownEntityException() {
        int expected = 0;
        int actual = 0;
        Role role;

        try {
            expected++;
            role = Role.resolveRoleById(0);
        } catch (UnknownEntityException e) {
            actual++;
            logger.error("{}", e.getMessage());
        }

        try {
            expected++;
            role = Role.resolveRoleById(-1);
        } catch (UnknownEntityException e) {
            actual++;
            logger.error("{}", e.getMessage());
        }

        try {
            expected++;
            role = Role.resolveRoleById(-1000);
        } catch (UnknownEntityException e) {
            actual++;
            logger.error("{}", e.getMessage());
        }

        try {
            expected++;
            role = Role.resolveRoleById(5);
        } catch (UnknownEntityException e) {
            actual++;
            logger.error("{}", e.getMessage());
        }

        try {
            expected++;
            role = Role.resolveRoleById(1000);
        } catch (UnknownEntityException e) {
            actual++;
            logger.error("{}", e.getMessage());
        }

        Assert.assertEquals(expected, actual);
    }

}
