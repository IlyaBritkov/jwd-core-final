package com.epam.jwd.core_final.domain;

import com.epam.jwd.core_final.exception.UnknownEntityException;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RankTest {
    private final Logger logger = LoggerFactory.getLogger(RankTest.class);

    @Test
    public void getNameMethodShouldReturnAppropriateResult() {
        Rank rank = Rank.TRAINEE;
        Assert.assertEquals("TRAINEE", rank.getName());

        rank = Rank.SECOND_OFFICER;
        Assert.assertEquals("SECOND_OFFICER", rank.getName());

        rank = Rank.FIRST_OFFICER;
        Assert.assertEquals("FIRST_OFFICER", rank.getName());

        rank = Rank.CAPTAIN;
        Assert.assertEquals("CAPTAIN", rank.getName());
    }

    @Test
    public void resolveRankByIdMethodShouldReturnAppropriateResult() {
        Rank rank = Rank.resolveRankById(1);
        Assert.assertEquals("TRAINEE", rank.getName());

        rank = Rank.resolveRankById(2);
        Assert.assertEquals("SECOND_OFFICER", rank.getName());

        rank = Rank.resolveRankById(3);
        Assert.assertEquals("FIRST_OFFICER", rank.getName());

        rank = Rank.resolveRankById(4);
        Assert.assertEquals("CAPTAIN", rank.getName());
    }

    @Test
    public void resolveRankByIdMethodShouldThrowUnknownEntityException() {
        int expected = 0;
        int actual = 0;
        Rank rank;

        try {
            expected++;
            rank = Rank.resolveRankById(0);
        } catch (UnknownEntityException e) {
            actual++;
            logger.error("{}", e.getMessage());
        }

        try {
            expected++;
            rank = Rank.resolveRankById(-1);
        } catch (UnknownEntityException e) {
            actual++;
            logger.error("{}", e.getMessage());
        }

        try {
            expected++;
            rank = Rank.resolveRankById(-1000);
        } catch (UnknownEntityException e) {
            actual++;
            logger.error("{}", e.getMessage());
        }

        try {
            expected++;
            rank = Rank.resolveRankById(5);
        } catch (UnknownEntityException e) {
            actual++;
            logger.error("{}", e.getMessage());
        }

        try {
            expected++;
            rank = Rank.resolveRankById(1000);
        } catch (UnknownEntityException e) {
            actual++;
            logger.error("{}", e.getMessage());
        }

        Assert.assertEquals(expected, actual);
    }
}
