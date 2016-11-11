# Overview

This repository contains a few basic examples of work with Spring Cache.

Output looks like (take a look at the time):

```bash
Heavy object example:
00:36:56.514  INFO : HeavyObject(value=Hello world)
00:36:56.515  INFO : HeavyObject(value=Hello world)
00:36:56.515  INFO : HeavyObject(value=Hello world)
00:36:56.515  WARN : Flushing cache manually
00:36:59.518  INFO : HeavyObject(value=Hello world)
00:36:59.518  INFO : HeavyObject(value=Hello world)

Fixed delay cache flush example:
00:37:00.548  INFO : Record(id=1, value=5bb07718-2e91-40c2-bdee-2e564694433b)
00:37:01.553  INFO : Record(id=2, value=b329fbb5-701a-4b9b-bf1c-9f4d58172e59)
00:37:02.558  INFO : Record(id=1, value=bb886556-b4e4-4449-a850-4b86d0efad4e)
00:37:02.559  INFO : Record(id=2, value=b329fbb5-701a-4b9b-bf1c-9f4d58172e59)
00:37:02.559  INFO : Record(id=1, value=bb886556-b4e4-4449-a850-4b86d0efad4e)
00:37:02.559  INFO : Record(id=2, value=b329fbb5-701a-4b9b-bf1c-9f4d58172e59)

Condition and specific key caching example:
00:37:05.602  INFO : User(firstName=Artem, lastName=Labazin, age=25)
00:37:05.602  INFO : User(firstName=Artem, lastName=Labazin, age=25)
00:37:05.603  INFO : User(firstName=Artem, lastName=Labazin, age=25)
00:37:08.607  INFO : User(firstName=Artem, lastName=Labazin, age=51)
00:37:11.612  INFO : User(firstName=Artem, lastName=Labazin, age=51)
00:37:14.618  INFO : User(firstName=NotArtem, lastName=Labazin, age=25)
00:37:14.619  INFO : User(firstName=NotArtem, lastName=Labazin, age=25)
00:37:14.619  WARN : Flushing cache for NotArtem:
00:37:14.619  INFO : User(firstName=Artem, lastName=Labazin, age=25)
00:37:17.622  INFO : User(firstName=NotArtem, lastName=Labazin, age=25
```

Compare it with the [code](https://github.com/xxlabaza/cacheable/blob/master/src/main/java/com/xxlabaza/test/cacheable.java).

## Useful links

* [Caching Data with Spring](https://spring.io/guides/gs/caching/);

* [@Caching and @CacheEvict](https://dzone.com/articles/spring-31-caching-and-0).
