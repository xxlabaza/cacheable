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

package com.xxlabaza.test.cacheable.d_nightmare;

import static com.xxlabaza.test.cacheable.d_nightmare.NightmareExampleConfiguration.EXPIRATION_TIMEOUT;
import static com.xxlabaza.test.cacheable.d_nightmare.NightmareExampleService.CACHE_NAME;
import static com.xxlabaza.test.cacheable.d_nightmare.NightmareExampleService.TIMEOUT_MILLISECONDS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;
import lombok.val;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StopWatch;

/**
 * @author Artem Labazin <xxlabaza@gmail.com>
 * @since 12.11.2016
 */
@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = NightmareExampleConfiguration.class,
        properties = "spring.cache.type: REDIS"
)
public class NightmareExampleServiceIT {

    private static final StopWatch WATCH = new StopWatch();

    @Autowired
    private RedisCacheManager cacheManager;

    @Autowired
    private NightmareExampleService nightmareExampleService;

    @Before
    public void before () {
        cacheManager.getCache(CACHE_NAME).clear();
    }

    @Test
    public void cacheWorks () {
        WATCH.start();
        val uuid1 = nightmareExampleService.getUUID();
        WATCH.stop();
        assertTrue(WATCH.getLastTaskTimeMillis() >= TIMEOUT_MILLISECONDS);

        WATCH.start();
        val uuid2 = nightmareExampleService.getUUID();
        WATCH.stop();
        assertTrue(WATCH.getLastTaskTimeMillis() < TIMEOUT_MILLISECONDS);

        assertEquals(uuid1, uuid2);
    }

    @Test
    public void cachedObjectExpires () throws InterruptedException {
        val uuid1 = nightmareExampleService.getUUID();

        TimeUnit.SECONDS.sleep(EXPIRATION_TIMEOUT + 1);

        WATCH.start();
        val uuid2 = nightmareExampleService.getUUID();
        WATCH.stop();
        assertTrue(WATCH.getLastTaskTimeMillis() >= TIMEOUT_MILLISECONDS);

        assertNotEquals(uuid1, uuid2);
    }
}
