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

import static com.xxlabaza.test.cacheable.d_nightmare.NightmareExampleService.CACHE_NAME;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import lombok.SneakyThrows;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author Artem Labazin <xxlabaza@gmail.com>
 * @since 12.11.2016
 */
@Service
@CacheConfig(cacheNames = CACHE_NAME)
class NightmareExampleService {

    static final String CACHE_NAME = "uuid";

    static final long TIMEOUT_MILLISECONDS = 1000;

    private static final Map<Integer, Pojo> REPOSITORY = new ConcurrentHashMap<>();

    @Cacheable
    public Pojo getWithCache (Integer id) {
        return getWithoutCache(id);
    }

    @SneakyThrows
    public Pojo getWithoutCache (Integer id) {
        TimeUnit.MILLISECONDS.sleep(TIMEOUT_MILLISECONDS);
        return REPOSITORY.get(id);
    }

    @CachePut(key = "#pojo.id")
    public Pojo putWithCache (Pojo pojo) {
        return putWithoutCache(pojo);
    }

    @CachePut(key = "#pojo.id")
    public Pojo putInCacheOnly (Pojo pojo) {
        return pojo;
    }

    public Pojo putWithoutCache (Pojo pojo) {
        REPOSITORY.put(pojo.getId(), pojo);
        return pojo;
    }

    public void clear () {
        REPOSITORY.clear();
    }
}
