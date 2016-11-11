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
package com.xxlabaza.test.cacheable;


import com.xxlabaza.test.cacheable.hard.HardExampleService;
import com.xxlabaza.test.cacheable.medium.MediumExampleService;
import com.xxlabaza.test.cacheable.simple.SimpleExampleService;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author Artem Labazin <xxlabaza@gmail.com>
 * @since 11.11.2016
 */
@Slf4j
@EnableCaching
@SpringBootApplication
public class Main implements CommandLineRunner {

  public static void main(String[] args) {
    new SpringApplicationBuilder(Main.class)
        .logStartupInfo(false)
        .run(args)
        .close();
  }

  @Autowired
  private SimpleExampleService simpleExampleService;

  @Autowired
  private MediumExampleService mediumExampleService;

  @Autowired
  private HardExampleService hardExampleService;

  @Override
  public void run (String... args) throws Exception {
    System.out.println();

    log.info("Heavy object example:");
    simpleExample();

    System.out.println();

    log.info("Fixed delay cache flush example:");
    mediumExample();

    System.out.println();

    log.info("Condition and specific key caching example:");
    hardExample();

    System.out.println();
  }

  private void simpleExample () {
    val ho1 = simpleExampleService.getHeavyObject();
    log.info(ho1.toString());
    val ho2 = simpleExampleService.getHeavyObject();
    log.info(ho2.toString());
    val ho3 = simpleExampleService.getHeavyObject();
    log.info(ho3.toString());
    assert ho1 == ho2 && ho1 == ho3;

    log.warn("Flushing cache manually");
    simpleExampleService.evictCache();
    val ho4 = simpleExampleService.getHeavyObject();
    log.info(ho4.toString());
    val ho5 = simpleExampleService.getHeavyObject();
    log.info(ho5.toString());
    assert ho1 != ho4 && ho1 != ho5;
  }

  private void mediumExample () {
    log.info(mediumExampleService.getOne(1).toString());
    log.info(mediumExampleService.getOne(2).toString());
    log.info(mediumExampleService.getOne(1).toString());
    log.info(mediumExampleService.getOne(2).toString());
    log.info(mediumExampleService.getOne(1).toString());
    log.info(mediumExampleService.getOne(2).toString());
  }

  private void hardExample () {
    val user1 = hardExampleService.getUser("Artem", "Labazin", 25);
    log.info(user1.toString());
    val user2 = hardExampleService.getUser("Artem", "Labazin", 25);
    log.info(user2.toString());
    assert user1 == user2;

    val user3 = hardExampleService.getUser("Artem", "", 0);
    log.info(user3.toString());
    assert user1 == user3;

    val user4 = hardExampleService.getUser("Artem", "Labazin", 51);
    log.info(user4.toString());
    val user5 = hardExampleService.getUser("Artem", "Labazin", 51);
    log.info(user5.toString());
    assert user4 != user5;

    val user6 = hardExampleService.getUser("NotArtem", "Labazin", 25);
    log.info(user6.toString());
    val user7 = hardExampleService.getUser("NotArtem", "", 0);
    log.info(user7.toString());
    assert user1 != user6 && user6 == user7;

    log.warn("Flushing cache for NotArtem:");
    hardExampleService.flushCacheFor("NotArtem");

    val user8 = hardExampleService.getUser("Artem", "Labazin", 25);
    log.info(user8.toString());
    val user9 = hardExampleService.getUser("NotArtem", "Labazin", 25);
    log.info(user9.toString());
    assert user1 == user8 && user6 != user9;
  }
}
