# Overview

This repository contains a few basic examples of work with Spring Cache.

Output looks like (take a look at the time):

```bash
00:47:55.983  INFO : Heavy object example:
00:47:59.002  INFO : HeavyObject(value=Hello world)
00:47:59.003  INFO : HeavyObject(value=Hello world)
00:47:59.004  INFO : HeavyObject(value=Hello world)
00:47:59.004  WARN : Flushing cache manually
00:48:02.009  INFO : HeavyObject(value=Hello world)
00:48:02.010  INFO : HeavyObject(value=Hello world)

00:48:02.010  INFO : Fixed delay cache flush example:
00:48:03.039  INFO : Record(id=1, value=996bc37b-dd3b-47fb-87ed-eda2ae6e6d4c)
00:48:04.043  INFO : Record(id=2, value=fe448f20-4652-4da1-8e81-17e01882fb68)
00:48:05.047  INFO : Record(id=1, value=97413ad3-f680-4d23-ac76-95663623a00b)
00:48:05.047  INFO : Record(id=2, value=fe448f20-4652-4da1-8e81-17e01882fb68)
00:48:05.047  INFO : Record(id=1, value=97413ad3-f680-4d23-ac76-95663623a00b)
00:48:05.047  INFO : Record(id=2, value=fe448f20-4652-4da1-8e81-17e01882fb68)

00:48:05.048  INFO : Condition and specific key caching example:
00:48:08.104  INFO : User(firstName=Artem, lastName=Labazin, age=25)
00:48:08.104  INFO : User(firstName=Artem, lastName=Labazin, age=25)
00:48:08.104  INFO : User(firstName=Artem, lastName=Labazin, age=25)
00:48:11.110  INFO : User(firstName=Artem, lastName=Labazin, age=51)
00:48:14.111  INFO : User(firstName=Artem, lastName=Labazin, age=51)
00:48:17.115  INFO : User(firstName=NotArtem, lastName=Labazin, age=25)
00:48:17.116  INFO : User(firstName=NotArtem, lastName=Labazin, age=25)
00:48:17.116  WARN : Flushing cache for NotArtem:
00:48:17.116  INFO : User(firstName=Artem, lastName=Labazin, age=25)
00:48:20.120  INFO : User(firstName=NotArtem, lastName=Labazin, age=25)
```

Compare it with the [code](https://github.com/xxlabaza/cacheable/blob/master/src/main/java/com/xxlabaza/test/cacheable.java).

## Useful links

* [Caching Data with Spring](https://spring.io/guides/gs/caching/);

* [@Caching and @CacheEvict](https://dzone.com/articles/spring-31-caching-and-0).
