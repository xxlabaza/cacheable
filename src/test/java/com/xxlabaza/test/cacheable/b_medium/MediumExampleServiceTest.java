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

package com.xxlabaza.test.cacheable.b_medium;

import com.xxlabaza.test.cacheable.b_medium.MediumExampleConfiguration;
import com.xxlabaza.test.cacheable.b_medium.MediumExampleService;

import static com.xxlabaza.test.cacheable.b_medium.MediumExampleConfiguration.EVICT_DELAY;
import static com.xxlabaza.test.cacheable.b_medium.MediumExampleService.TIMEOUT_MILLISECONDS;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;
import lombok.val;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StopWatch;

/**
 * @author Artem Labazin <xxlabaza@gmail.com>
 * @since 12.11.2016
 */
@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = MediumExampleConfiguration.class,
        properties = "spring.cache.type: SIMPLE"
)
public class MediumExampleServiceTest {

    private static final StopWatch WATCH = new StopWatch();

    @Autowired
    private MediumExampleService mediumExampleService;

    @Test
    public void cacheWorks () {
        WATCH.start();
        val record1 = mediumExampleService.getOne(1);
        WATCH.stop();
        assertTrue(WATCH.getLastTaskTimeMillis() >= TIMEOUT_MILLISECONDS);

        WATCH.start();
        val record2 = mediumExampleService.getOne(1);
        WATCH.stop();
        assertTrue(WATCH.getLastTaskTimeMillis() < TIMEOUT_MILLISECONDS);

        assertSame(record1, record2);
    }

    @Test
    public void differentIdsHasDifferentRecords () {
        WATCH.start();
        val record1 = mediumExampleService.getOne(10);
        WATCH.stop();
        assertTrue(WATCH.getLastTaskTimeMillis() >= TIMEOUT_MILLISECONDS);

        WATCH.start();
        val record2 = mediumExampleService.getOne(20);
        WATCH.stop();
        assertTrue(WATCH.getLastTaskTimeMillis() >= TIMEOUT_MILLISECONDS);

        assertNotSame(record1, record2);
    }

    @Test
    public void cacheFlushsFromTimeToTime () throws InterruptedException {
        WATCH.start();
        val record1 = mediumExampleService.getOne(100);
        WATCH.stop();
        assertTrue(WATCH.getLastTaskTimeMillis() >= TIMEOUT_MILLISECONDS);

        TimeUnit.MILLISECONDS.sleep(EVICT_DELAY);

        WATCH.start();
        val record2 = mediumExampleService.getOne(100);
        WATCH.stop();
        assertTrue(WATCH.getLastTaskTimeMillis() >= TIMEOUT_MILLISECONDS);

        assertNotSame(record1, record2);
    }
}
