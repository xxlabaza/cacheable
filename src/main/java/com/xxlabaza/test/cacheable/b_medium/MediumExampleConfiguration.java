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

import static com.xxlabaza.test.cacheable.b_medium.MediumExampleService.CACHE_NAME;
import static com.xxlabaza.test.cacheable.b_medium.MediumExampleService.TIMEOUT_MILLISECONDS;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @author Artem Labazin <xxlabaza@gmail.com>
 * @since 11.11.2016
 */
@EnableCaching
@EnableScheduling
@SpringBootApplication
class MediumExampleConfiguration {

    static final long EVICT_DELAY = TIMEOUT_MILLISECONDS * 3;

    @CacheEvict(cacheNames = CACHE_NAME, allEntries = true)
    @Scheduled(fixedDelay = EVICT_DELAY)
    public void reportCacheEvict () {
    }
}
