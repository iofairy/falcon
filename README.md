# 📚falcon
Falcon是一个强大的Java工具类库，提供了多种便捷的工具类，帮助开发者更方便的处理时间，UUID，集合等。
+ 🔥<u>**不可变且线程安全**</u>的**时间处理类**`DateTime<T>`，提供**统一**的时间API
  - 🔺强大的时间处理API
    - **自动识别**解析多种时间格式串
    - 指定时间单位的**取整操作**（四舍五入、向上/下取整）
    - 时间偏移（指定时间单位的增减）
    - 计算两个时间间隔
    - 时间格式化
    - 获取并格式化**周信息**
    - 各种时间类的互相转换
  - 🔺支持多种时间类型：`Date`, `Calendar`, `LocalDateTime`, `ZonedDateTime`, `OffsetDateTime`, `Instant`
+ 💡便捷的**秒表**`Stopwatch`工具类，方便测试各个代码片段或任意多个连续代码片段的执行时间
+ 💡**带时间的UUID**工具类，生成的**UUID**以时间为前缀，更易于阅读，且**具有一定的顺序**
+ 💡系统变量及Java环境变量工具类`OS`
+ 💡**兼容Java 8及Java 9+模块化系统**
+ ……


## 🛠️Environment（开发环境）
+ JDK 9.0.4 **（兼容Java 8及Java 9+模块化系统）**
+ Apache maven 3.6.1


## 💿集成方式（兼容Java 8及Java 9+）
### Maven
```xml
<dependency>
  <groupId>com.iofairy</groupId>
  <artifactId>falcon</artifactId>
  <version>0.6.1</version>
</dependency>
```

### Gradle
```
implementation 'com.iofairy:falcon:0.6.1'
```

## 🗺️使用指南（User Guide）
- [🔥强大的时间处理API](#强大的时间处理API)
    - [自动识别时间字符串](#自动识别时间字符串)
    - [时间取整操作](#时间取整操作)
    - [时间偏移(时间加减)](#时间偏移时间加减)
    - [计算两个时间间隔](#计算两个时间间隔)
- [🔥秒表`Stopwatch`](#秒表Stopwatch)
- [🔥带时间的UUID`TimedID`](#带时间的UUIDTimedID)



## 🔥强大的时间处理API
### 自动识别时间字符串
```java
DateTime<Date> dt00 = DateTime.parseDate("2022-8-01 10:5:15", TZ.UTC);
DateTime<Date> dt01 = DateTime.parseDate("2022/8/01T10:5:15.987");
DateTime<Date> dt02 = DateTime.parseDate("2022");
DateTime<Date> dt03 = DateTime.parseDate("999");
DateTime<Date> dt04 = DateTime.parseDate("2点5分", TZ.NEW_YORK);
DateTime<Date> dt05 = DateTime.parseDate("20220810T170650666");
DateTime<LocalDateTime> dt06 = DateTime.parse("2022-8-01 10:5:15");
DateTime<LocalDateTime> dt07= DateTime.parse("2022-8-01T10:5:15.987");
DateTime<LocalDateTime> dt08 = DateTime.parse("2022");
DateTime<LocalDateTime> dt09 = DateTime.parse("999");
DateTime<LocalDateTime> dt10 = DateTime.parse("10点5分");
DateTime<LocalDateTime> dt11 = DateTime.parse("2022-8-01T10:5:15.98");
DateTime<LocalDateTime> dt12 = DateTime.parse("202208");
DateTime<LocalDateTime> dt13 = DateTime.parse("20220810");
DateTime<LocalDateTime> dt14 = DateTime.parse("20220810170650");
DateTime<LocalDateTime> dt15 = DateTime.parse("20220810170650666");

System.out.println(dt00.dtDetail());    // 2022-08-01 18:05:15.000000000 [Asia/Shanghai +08:00 GMT+8 星期一]
System.out.println(dt01.dtDetail());    // 2022-08-01 10:05:15.987000000 [Asia/Shanghai +08:00 GMT+8 星期一]
System.out.println(dt02.dtDetail());    // 2022-01-01 00:00:00.000000000 [Asia/Shanghai +08:00 GMT+8 星期六]
System.out.println(dt03.dtDetail());    // 0999-01-01 00:00:00.000000000 [Asia/Shanghai +08:05 GMT+8:05:43 星期二]
System.out.println(dt04.dtDetail());    // 1970-01-01 15:05:00.000000000 [Asia/Shanghai +08:00 GMT+8 星期四]
System.out.println(dt05.dtDetail());    // 2022-08-10 17:06:50.666000000 [Asia/Shanghai +08:00 GMT+8 星期三]
System.out.println(dt06.dtDetail());    // 2022-08-01 10:05:15.000000000 [星期一]
System.out.println(dt07.dtDetail());    // 2022-08-01 10:05:15.987000000 [星期一]
System.out.println(dt08.dtDetail());    // 2022-01-01 00:00:00.000000000 [星期六]
System.out.println(dt09.dtDetail());    // 0999-01-01 00:00:00.000000000 [星期二]
System.out.println(dt10.dtDetail());    // 1970-01-01 10:05:00.000000000 [星期四]
System.out.println(dt11.dtDetail());    // 2022-08-01 10:05:15.980000000 [星期一]
System.out.println(dt12.dtDetail());    // 2022-08-01 00:00:00.000000000 [星期一]
System.out.println(dt13.dtDetail());    // 2022-08-10 00:00:00.000000000 [星期三]
System.out.println(dt14.dtDetail());    // 2022-08-10 17:06:50.000000000 [星期三]
System.out.println(dt15.dtDetail());    // 2022-08-10 17:06:50.666000000 [星期三]
```

### 时间取整操作
```java
/*
 各种原始时间类型
 */
LocalDateTime ldt = LocalDateTime.of(2022, 2, 27, 8, 0, 10, 100);
ZonedDateTime zdt = ldt.atZone(TZ.DEFAULT_ZONE);
Instant instant = zdt.toInstant();
OffsetDateTime odt = zdt.toOffsetDateTime();
Calendar calendar = Calendar.getInstance();
calendar.setTime(Date.from(zdt.toInstant()));
Date date = calendar.getTime();
/*
 将各种原始时间转换为 DateTime 类型
 */
DateTime<LocalDateTime> dt1 = DateTime.of(ldt);     // 2022-02-27 08:00:10.000
DateTime<ZonedDateTime> dt2 = DateTime.of(zdt);     // 2022-02-27 08:00:10.000
DateTime<Instant> dt3 = DateTime.of(instant);       // 2022-02-27 08:00:10.000
DateTime<OffsetDateTime> dt4 = DateTime.of(odt);    // 2022-02-27 08:00:10.000
DateTime<Calendar> dt5 = DateTime.of(calendar);     // 2022-02-27 08:00:10.000
DateTime<Date> dt6 = DateTime.of(date);             // 2022-02-27 08:00:10.000
/*
 各种时间类型取整操作
 */
System.out.println(dt1.round(ChronoUnit.DAYS, RoundingDT.FLOOR));       // 2022-02-27 00:00:00.000
System.out.println(dt2.round(ChronoUnit.DAYS, RoundingDT.CEILING));     // 2022-02-28 00:00:00.000
System.out.println(dt3.round(ChronoUnit.MONTHS, RoundingDT.HALF_UP));   // 2022-03-01 00:00:00.000
System.out.println(dt4.round(ChronoUnit.MINUTES, RoundingDT.CEILING));  // 2022-02-27 08:01:00.000
System.out.println(dt5.round(ChronoUnit.HOURS, RoundingDT.CEILING));    // 2022-02-27 09:00:00.000
System.out.println(dt6.round(ChronoUnit.HOURS, RoundingDT.HALF_UP));    // 2022-02-27 08:00:00.000
```

### 时间偏移(时间加减)
```java
/*
 各种原始时间类型
 */
LocalDateTime ldt = LocalDateTime.of(2022, 2, 27, 8, 0, 10, 100);
ZonedDateTime zdt = ldt.atZone(TZ.DEFAULT_ZONE);
Instant instant = zdt.toInstant();
OffsetDateTime odt = zdt.toOffsetDateTime();
Calendar calendar = Calendar.getInstance();
calendar.setTime(Date.from(zdt.toInstant()));
Date date = calendar.getTime();
/*
 将各种时间转换为 DateTime 类型
 */
DateTime<LocalDateTime> dt1 = DateTime.of(ldt);     // 2022-02-27 08:00:10.000
DateTime<ZonedDateTime> dt2 = DateTime.of(zdt);     // 2022-02-27 08:00:10.000
DateTime<Instant> dt3 = DateTime.of(instant);       // 2022-02-27 08:00:10.000
DateTime<OffsetDateTime> dt4 = DateTime.of(odt);    // 2022-02-27 08:00:10.000
DateTime<Calendar> dt5 = DateTime.of(calendar);     // 2022-02-27 08:00:10.000
DateTime<Date> dt6 = DateTime.of(date);             // 2022-02-27 08:00:10.000
/*
 各种时间类型偏移（加减）操作
 */
System.out.println(dt1.minusHours(5).plusDays(2));  // 2022-03-01 03:00:10.000
System.out.println(dt2.minusMillis(-10000));    // 2022-02-27 08:00:20.000
System.out.println(dt3.plusDays(2));            // 2022-03-01 08:00:10.000
System.out.println(dt4.plusMonths(1));          // 2022-03-27 08:00:10.000
System.out.println(dt5.plusDays(-5));           // 2022-02-22 08:00:10.000
System.out.println(dt6.minusMinutes(100));      // 2022-02-27 06:20:10.000
```
### 计算两个时间间隔
```java
LocalDateTime fromLDT = LocalDateTime.of(2020, 8, 15, 10, 56, 43, 10000000);
DateTime<Date> toDT = DateTime.parseDate("2022/06/20 20:10:56.200");
SignedInterval interval1 = SignedInterval.between(toDT, fromLDT);
Interval interval2 = Interval.between(fromLDT, toDT);
System.out.println(interval1);      // -1年-10月-5天-9时-14分-13秒-190毫秒
System.out.println(interval2);      // 1年10月5天9时14分13秒190毫秒
```


## 🔥秒表`Stopwatch`
**秒表**`Stopwatch`：方便<u>测试各个代码片段或任意多个连续代码片段的执行时间</u>。一个独立的秒表从运行开始到结束之前，便具有**分段记录**、**持续记录**与**实时记录**的功能  
秒表中的**标记**`Stopwatch.mark()`：可在每个代码片段（业务）开始或结束时打个标记。 一方面是<u>代码片段（业务）间的分界点</u>；同时，也类似于savepoint（保存点），保存当时的时间
```java
/*
 elapsedLastStringAndMark 方法：返回秒表距离上次标记处耗时，并打上新的标记
 等同于：
 stopwatch.elapsedLastString();
 stopwatch.mark();
 */
Stopwatch stopwatch = Stopwatch.run();

// >> 开始业务1
Try.sleep(1000);  // ... 执行一些操作
// << 结束业务1
 System.out.println("sleep(1000)。秒表总耗时：" + stopwatch + "---" + "秒表距离上次标记处耗时1：" + stopwatch.elapsedLastStringAndMark()); // 输出：sleep(1000)。秒表总耗时：1.003(秒)---秒表距离上次标记处耗时1：1.01(秒)

// >> 开始业务2
Try.sleep(500);  // ... 执行一些操作
// << 结束业务2
System.out.println("sleep(500)。秒表总耗时：" + stopwatch + "---" + "秒表距离上次标记处耗时2：" + stopwatch.elapsedLastString()); // 输出：sleep(500)。秒表总耗时：1.518(秒)---秒表距离上次标记处耗时2：507.527(毫秒)

stopwatch.mark();  // 业务开始时打个标记
// >> 开始业务3
Try.sleep(1500);  // ... 执行一些操作
// << 结束业务3
System.out.println("sleep(1500)。秒表总耗时：" + stopwatch + "---" + "秒表距离上次标记处耗时3：" + stopwatch.elapsedLastStringAndMark()); // 输出：sleep(1500)。秒表总耗时：3.034(秒)---秒表距离上次标记处耗时3：1.516(秒)

// 业务1开始到业务3完成所耗时间
Stopwatch.Elapsed elapsed = stopwatch.elapsed(0, 3);
System.out.println("业务1开始到业务3完成所耗时间：" + elapsed + "---" + elapsed.toFullString()); // 输出：业务1开始到业务3完成所耗时间：3.034(秒)---3.034(秒) (index: 0 -> 3, mark: START -> MARK3)

```

## 🔥带时间的UUID`TimedID`
Java自带的UUID虽然保证了唯一性，但是随机生成的，不具有顺序性，导致索引效率较低。`TimedID`**自带时间前缀**，记录着UUID创建时间，具有一定的顺序。且可以根据业务需要，**自定义生成的UUID的长度**。
```java
System.out.println(TimedID.getId());        // 240409064014820AADJWNSV35
System.out.println(TimedID.newId());        // 240409064015067AAAZSRRRGE
System.out.println(TimedID.shortId());      // 0240409064015067AA9ULRWUBJ
System.out.println(TimedID.mediumId());     // 0240409064015067AA55DBDMO7Z3K4
System.out.println(TimedID.longId());       // 20240409064015068AAEI0SMVHDP274A
System.out.println(TimedID.linedId());      // 20240409064015068_A7VT0LSYBW
System.out.println(TimedID.nid());          // 1712616015068AA54VB5KZBUX
System.out.println(TimedID.sid());          // 1712616015068AA2BE42OPHJM8
System.out.println(TimedID.mid());          // 1712616015068AA8JEK4Q4FQAILX2H
System.out.println(TimedID.lid());          // 1712616015068AA55F78HAFKRP0S0ZS1
System.out.println(TimedID._id());          // 1712616015069_AAYY7KL2ZN8XEQ
```
设置自定义的默认的 TimedID，<u>默认的 TimedID 一旦设置后，无法变更</u>。
```java
TimedID timedID = TimedID.Builder.newBuilder().withTimestamp(true).withStartInstant("2024-4-9 6:0:0.0").withIdLength(25).build();
TimedID.setDefaultId(timedID);
TimedID.setDefaultId(TimedID.TIMED_ID); // 无效设置
for (int i = 0; i < 10; i++) {
/*
 * 000003554589AABX6J4CSZBWJ
 * 000003554856AA71Q4F2MN9J7
 * 000003554856AA7OKJMZ1HZCE
 * 000003554856AA8X1081ZY4UJ
 * 000003554856AA73NY6R93QQS
 * 000003554856AA7NKRK5HDOMA
 * 000003554856AA9XUAO2FT6YJ
 * 000003554857AAH0UH5M2ULG8
 * 000003554857AA88EFGLRXXME
 * 000003554857AAOTZOXXNQSJB
 */
System.out.println(TimedID.getId());
}

System.out.println(timedID.randomId()); // 000003554857AACYV7HG33PYG
```


## ⭐点个赞哟
如果你喜欢 Falcon，感觉 Falcon 帮助到了你，可以点右上角 **Star** 支持一下哦，感谢感谢！

## Copyright

**Copyright (C) 2021 iofairy**, <https://github.com/iofairy/falcon>

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.


