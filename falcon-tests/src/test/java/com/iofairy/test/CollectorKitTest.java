package com.iofairy.test;

import com.iofairy.falcon.stream.CollectorKit;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author GG
 * @version 1.0
 * @date 2023/5/14 5:46
 */
public class CollectorKitTest {
    @Test
    public void testToMap() {
        List<User> users = Arrays.asList(
                new User("Jack", "swimming"),
                new User("Jack", "reading"),
                new User("Ben", "singing"),
                new User("Emma", "reading"),
                new User("Jack", "singing"),
                new User(null, null),
                new User("Emma", "swimming"),
                new User("Grace", "swimming"),
                new User(null, null),
                new User("Kate", "reading"),
                new User("Grace", null)
        );

        assertThrows(IllegalStateException.class, () -> users.stream().parallel().collect(CollectorKit.toMap(HashMap::new, e -> e.name, e -> e, false)));

        Map<String, User> hashMap1 = users.stream().parallel().collect(CollectorKit.toMap(HashMap::new, e -> e.name, e -> e, true));
        Map<String, User> linkedHashMap1 = users.stream().parallel().collect(CollectorKit.toMap(LinkedHashMap::new, e -> e.name, e -> e, true));
        Map<String, String> linkedHashMapNullValue = users.stream().parallel().collect(CollectorKit.toMap(LinkedHashMap::new, e -> e.name, e -> e.hobby, true));
        System.out.println(hashMap1);
        System.out.println(linkedHashMap1);
        System.out.println(linkedHashMapNullValue);
        assertEquals(hashMap1.toString(), "{null=User{name='null', hobby='null'}, Kate=User{name='Kate', hobby='reading'}, Grace=User{name='Grace', hobby='null'}, Ben=User{name='Ben', hobby='singing'}, Jack=User{name='Jack', hobby='singing'}, Emma=User{name='Emma', hobby='swimming'}}");
        assertEquals(linkedHashMap1.toString(), "{Jack=User{name='Jack', hobby='singing'}, Ben=User{name='Ben', hobby='singing'}, Emma=User{name='Emma', hobby='swimming'}, null=User{name='null', hobby='null'}, Grace=User{name='Grace', hobby='null'}, Kate=User{name='Kate', hobby='reading'}}");
        assertEquals(linkedHashMapNullValue.toString(), "{Jack=singing, Ben=singing, Emma=swimming, null=null, Grace=null, Kate=reading}");
        System.out.println("============================================================");

        List<User> users1 = Arrays.asList(
                new User("Jack", "reading"),
                new User("Ben", "singing"),
                new User(null, null),
                new User("Emma", "swimming"),
                new User("Kate", "reading"),
                new User("Grace", null)
        );
        Map<String, User> hashMap2 = users1.stream().parallel().collect(CollectorKit.toMap(e -> e.name, e -> e));
        Map<String, User> linkedHashMap2 = users1.stream().parallel().collect(CollectorKit.toLinkedMap(e -> e.name, e -> e, false));
        System.out.println(hashMap2);
        System.out.println(linkedHashMap2);
        assertEquals(hashMap2.toString(), "{null=User{name='null', hobby='null'}, Kate=User{name='Kate', hobby='reading'}, Grace=User{name='Grace', hobby='null'}, Ben=User{name='Ben', hobby='singing'}, Jack=User{name='Jack', hobby='reading'}, Emma=User{name='Emma', hobby='swimming'}}");
        assertEquals(linkedHashMap2.toString(), "{Jack=User{name='Jack', hobby='reading'}, Ben=User{name='Ben', hobby='singing'}, null=User{name='null', hobby='null'}, Emma=User{name='Emma', hobby='swimming'}, Kate=User{name='Kate', hobby='reading'}, Grace=User{name='Grace', hobby='null'}}");
        System.out.println("============================================================");

        List<User> users2 = Arrays.asList(
                new User("Jack", "swimming"),
                new User("Jack", "reading"),
                new User("Ben", "singing"),
                new User("Emma", "reading"),
                new User("Jack", "singing"),
                new User("Emma", "swimming"),
                new User("Grace", "swimming"),
                new User("Kate", "reading")
        );
        Map<String, User> concurrentHashMap1 = users2.stream().parallel().collect(CollectorKit.toMap(ConcurrentHashMap::new, e -> e.name, e -> e, true));
        Map<String, User> concurrentSkipListMap1 = users2.stream().parallel().collect(CollectorKit.toMap(ConcurrentSkipListMap::new, e -> e.name, e -> e, true));
        System.out.println(concurrentHashMap1);
        System.out.println(concurrentSkipListMap1);
        /*
         * concurrentHashMap1 与 concurrentSkipListMap1 打印的字符串不固定，所以没有使用 assertEquals
         */

    }

    @Test
    public void testGroupingByAllowNullKey() {
        List<User> users = Arrays.asList(
                new User("Jack", "swimming"),
                new User("Jack", "reading"),
                new User("Ben", "singing"),
                new User("Emma", "reading"),
                new User("Jack", "singing"),
                new User(null, null),
                new User("Emma", "swimming"),
                new User("Grace", "swimming"),
                new User(null, null),
                new User("Kate", "reading"),
                new User("Grace", null)
        );

        Map<String, List<User>> map1 = users.stream().collect(CollectorKit.groupingByAllowNullKey(e -> e.name));
        Map<String, List<User>> map2 = users.stream().parallel().collect(CollectorKit.groupingByAllowNullKey(e -> e.name));

        System.out.println(map1);
        System.out.println(map2);

        assertEquals(map1.toString(), "{null=[User{name='null', hobby='null'}, User{name='null', hobby='null'}], Kate=[User{name='Kate', hobby='reading'}], Grace=[User{name='Grace', hobby='swimming'}, User{name='Grace', hobby='null'}], Ben=[User{name='Ben', hobby='singing'}], Jack=[User{name='Jack', hobby='swimming'}, User{name='Jack', hobby='reading'}, User{name='Jack', hobby='singing'}], Emma=[User{name='Emma', hobby='reading'}, User{name='Emma', hobby='swimming'}]}");
        assertEquals(map2.toString(), "{null=[User{name='null', hobby='null'}, User{name='null', hobby='null'}], Kate=[User{name='Kate', hobby='reading'}], Grace=[User{name='Grace', hobby='swimming'}, User{name='Grace', hobby='null'}], Ben=[User{name='Ben', hobby='singing'}], Jack=[User{name='Jack', hobby='swimming'}, User{name='Jack', hobby='reading'}, User{name='Jack', hobby='singing'}], Emma=[User{name='Emma', hobby='reading'}, User{name='Emma', hobby='swimming'}]}");

    }


    static class User {
        private String name;
        private String hobby;

        public User(String name, String hobby) {
            this.name = name;
            this.hobby = hobby;
        }

        @Override
        public String toString() {
            return "User{" +
                    "name='" + name + '\'' +
                    ", hobby='" + hobby + '\'' +
                    '}';
        }
    }
}
