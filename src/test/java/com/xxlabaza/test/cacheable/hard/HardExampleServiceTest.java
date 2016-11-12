/*
 * Copyright 2016 Artem Labazin <xxlabaza@gmail.com>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.xxlabaza.test.cacheable.hard;

import static com.xxlabaza.test.cacheable.hard.HardExampleService.CACHE_NAME;
import static com.xxlabaza.test.cacheable.hard.HardExampleService.TIMEOUT_MILLISECONDS;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import lombok.val;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StopWatch;

/**
 * @author Artem Labazin <xxlabaza@gmail.com>
 * @since 12.11.2016
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HardExampleConfiguration.class)
public class HardExampleServiceTest {

    private static final StopWatch WATCH = new StopWatch();

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private HardExampleService hardExampleService;

    @Before
    public void before () {
        cacheManager.getCache(CACHE_NAME).clear();
    }

    @Test
    public void cacheWorks () {
        WATCH.start();
        val user1 = hardExampleService.getUser("Artem", "Labazin", 25);
        WATCH.stop();
        assertTrue(WATCH.getLastTaskTimeMillis() >= TIMEOUT_MILLISECONDS);

        WATCH.start();
        val user2 = hardExampleService.getUser("Artem", "Labazin", 25);
        WATCH.stop();
        assertTrue(WATCH.getLastTaskTimeMillis() < TIMEOUT_MILLISECONDS);

        assertSame(user1, user2);
    }

    @Test
    public void differentFirstNameAndLastNamePairsAreNotSame () {
        WATCH.start();
        val user1 = hardExampleService.getUser("Artem", "Labazin", 25);
        WATCH.stop();
        assertTrue(WATCH.getLastTaskTimeMillis() >= TIMEOUT_MILLISECONDS);

        WATCH.start();
        val user2 = hardExampleService.getUser("Charles", "Kane", 25);
        WATCH.stop();
        assertTrue(WATCH.getLastTaskTimeMillis() >= TIMEOUT_MILLISECONDS);

        assertNotSame(user1, user2);
    }

    @Test
    public void cacheKeyIsFirstNameAndLastNameOnly () {
        val user1 = hardExampleService.getUser("Artem", "Labazin", 25);

        WATCH.start();
        val user2 = hardExampleService.getUser("Artem", "Labazin", 1);
        WATCH.stop();
        assertTrue(WATCH.getLastTaskTimeMillis() < TIMEOUT_MILLISECONDS);

        assertSame(user1, user2);
    }

    @Test
    public void ageBelowZeroNotCache () {
        val user1 = hardExampleService.getUser("Artem", "Labazin", 25);

        WATCH.start();
        val user2 = hardExampleService.getUser("Artem", "Labazin", 0);
        WATCH.stop();
        assertTrue(WATCH.getLastTaskTimeMillis() >= TIMEOUT_MILLISECONDS);

        assertNotSame(user1, user2);
    }

    @Test
    public void ageAboveFiftyNotCache () {
        val user1 = hardExampleService.getUser("Artem", "Labazin", 25);

        WATCH.start();
        val user2 = hardExampleService.getUser("Artem", "Labazin", 54);
        WATCH.stop();
        assertTrue(WATCH.getLastTaskTimeMillis() >= TIMEOUT_MILLISECONDS);

        assertNotSame(user1, user2);
    }

    @Test
    public void notSuitableByAgeConditionAreNotSame () {
        val user1 = hardExampleService.getUser("Artem", "Labazin", 54);

        WATCH.start();
        val user2 = hardExampleService.getUser("Artem", "Labazin", 70);
        WATCH.stop();
        assertTrue(WATCH.getLastTaskTimeMillis() >= TIMEOUT_MILLISECONDS);

        assertNotSame(user1, user2);
    }

    @Test
    public void flushCacheForConcreteUser () {
        hardExampleService.getUser("Artem", "Labazin", 25);
        val citizenKane = hardExampleService.getUser("Charles", "Kane", 25);

        hardExampleService.flushCacheFor(citizenKane);

        WATCH.start();
        hardExampleService.getUser("Artem", "Labazin", 25);
        WATCH.stop();
        assertTrue(WATCH.getLastTaskTimeMillis() < TIMEOUT_MILLISECONDS);

        WATCH.start();
        hardExampleService.getUser("Charles", "Kane", 25);
        WATCH.stop();
        assertTrue(WATCH.getLastTaskTimeMillis() >= TIMEOUT_MILLISECONDS);
    }
}
