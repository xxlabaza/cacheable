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

import java.util.concurrent.TimeUnit;
import lombok.SneakyThrows;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author Artem Labazin <xxlabaza@gmail.com>
 * @since 11.11.2016
 */
@Service
public class HardExampleService {

  private static final String CACHE_NAME = "users";

  @Cacheable(cacheNames = CACHE_NAME, key = "#firstName", condition = "#age < 50")
  @SneakyThrows
  public User getUser (String firstName, String lastName, int age) {
    TimeUnit.SECONDS.sleep(3);
    return new User(firstName, lastName, age);
  }

  @CacheEvict(cacheNames = CACHE_NAME)
  public void flushCacheFor (String firstName) {
  }
}
