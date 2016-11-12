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

package com.xxlabaza.test.cacheable.a_easy;

import com.xxlabaza.test.cacheable.a_easy.SimpleExampleService;
import com.xxlabaza.test.cacheable.a_easy.SimpleExampleConfiguration;

import static com.xxlabaza.test.cacheable.a_easy.SimpleExampleService.CACHE_NAME;
import static com.xxlabaza.test.cacheable.a_easy.SimpleExampleService.TIMEOUT_MILLISECONDS;
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
@SpringBootTest(
        classes = SimpleExampleConfiguration.class,
        properties = "spring.cache.type: SIMPLE"
)
public class SimpleExampleServiceTest {

    private static final StopWatch WATCH = new StopWatch();

    @Autowired
    private SimpleExampleService simpleExampleService;

    @Autowired
    private CacheManager cacheManager;

    @Before
    public void before () {
        cacheManager.getCache(CACHE_NAME).clear();
    }

    @Test
    public void cacheWorks () {
        WATCH.start();
        val heavy1 = simpleExampleService.getHeavyObject();
        WATCH.stop();
        assertTrue(WATCH.getLastTaskTimeMillis() >= TIMEOUT_MILLISECONDS);

        WATCH.start();
        val heavy2 = simpleExampleService.getHeavyObject();
        WATCH.stop();
        assertTrue(WATCH.getLastTaskTimeMillis() < TIMEOUT_MILLISECONDS);

        assertSame(heavy1, heavy2);
    }

    @Test
    public void evictCache () {
        WATCH.start();
        val heavy1 = simpleExampleService.getHeavyObject();
        WATCH.stop();
        assertTrue(WATCH.getLastTaskTimeMillis() >= TIMEOUT_MILLISECONDS);

        simpleExampleService.evictCache();

        WATCH.start();
        val heavy2 = simpleExampleService.getHeavyObject();
        WATCH.stop();
        assertTrue(WATCH.getLastTaskTimeMillis() >= TIMEOUT_MILLISECONDS);

        assertNotSame(heavy1, heavy2);
    }
}
