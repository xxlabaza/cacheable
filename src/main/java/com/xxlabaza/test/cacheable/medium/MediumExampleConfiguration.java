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
package com.xxlabaza.test.cacheable.medium;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @author Artem Labazin <xxlabaza@gmail.com>
 * @since 11.11.2016
 */
@Slf4j
@Configuration
@EnableScheduling
class MediumExampleConfiguration {

  final static String CACHE_NAME = "records";

  @CacheEvict(allEntries = true, cacheNames = CACHE_NAME)
  @Scheduled(fixedDelay = 2500)
  public void reportCacheEvict() {
  }
}
