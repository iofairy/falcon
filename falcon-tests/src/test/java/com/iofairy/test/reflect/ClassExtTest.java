package com.iofairy.test.reflect;

import com.iofairy.falcon.reflect.ClassExt;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author GG
 * @version 1.0
 * @date 2022/6/19 12:55
 */
public class ClassExtTest {
    @Test
    public void testClassExt() {
        Class<?> c0 = Outer.class;
        Class<?> c1 = Outer.Inner1.class;
        Class<?> c2 = Outer.StaticInner1.class;
        Class<?> c3 = Outer.Inner1.Inner2.class;
        Class<?> c4 = Outer.StaticInner1.Enum1.class;
        Class<?> c5 = new Outer() {
        }.getClass();
        class A {
        }
        Class<?> c6 = A.class;
        Class<?> c7 = Outer.anonymousClass;
        Class<?> c8 = Outer.StaticInner1.Annotation.class;
        Class<?> c9 = null;
        // ====================================================
        ClassExt of0 = ClassExt.of(c0);
        ClassExt of1 = ClassExt.of(c1);
        ClassExt of2 = ClassExt.of(c2);
        ClassExt of3 = ClassExt.of(c3);
        ClassExt of4 = ClassExt.of(c4);
        ClassExt of5 = ClassExt.of(c5);
        ClassExt of6 = ClassExt.of(c6);
        ClassExt of7 = ClassExt.of(c7);
        ClassExt of8 = ClassExt.of(c8);
        ClassExt of9 = ClassExt.of(c9);

        // ====================================================
        System.out.println("of0: " + of0);
        System.out.println("of1: " + of1);
        System.out.println("of2: " + of2);
        System.out.println("of3: " + of3);
        System.out.println("of4: " + of4);
        System.out.println("of5: " + of5);
        System.out.println("of6: " + of6);
        System.out.println("of7: " + of7);
        System.out.println("of8: " + of8);
        System.out.println("of9: " + of9);

        // ====================================================
        assertEquals(of0.simpleNameWithEnclosing, "Outer");                         // simpleName='Outer'
        assertEquals(of1.simpleNameWithEnclosing, "Outer.Inner1");                  // simpleName='Inner1'
        assertEquals(of2.simpleNameWithEnclosing, "Outer.StaticInner1");            // simpleName='StaticInner1'
        assertEquals(of3.simpleNameWithEnclosing, "Outer.Inner1.Inner2");           // simpleName='Inner2'
        assertEquals(of4.simpleNameWithEnclosing, "Outer.StaticInner1.Enum1");      // simpleName='Enum1'
        assertEquals(of5.simpleNameWithEnclosing, "ClassExtTest.");                 // simpleName=''
        assertEquals(of6.simpleNameWithEnclosing, "ClassExtTest.A");                // simpleName='A'
        assertEquals(of7.simpleNameWithEnclosing, "Outer.");                        // simpleName=''
        assertEquals(of8.simpleNameWithEnclosing, "Outer.StaticInner1.Annotation"); // simpleName='Annotation'
        assertNull(of9);

        assertEquals(of0.importClause, "import com.iofairy.test.reflect.Outer;");
        assertEquals(of1.importClause, "import com.iofairy.test.reflect.Outer.Inner1;");
        assertEquals(of2.importClause, "import com.iofairy.test.reflect.Outer.StaticInner1;");
        assertEquals(of3.importClause, "import com.iofairy.test.reflect.Outer.Inner1.Inner2;");
        assertEquals(of4.importClause, "import com.iofairy.test.reflect.Outer.StaticInner1.Enum1;");
        assertEquals(of5.importClause, "");
        assertEquals(of6.importClause, "");
        assertEquals(of7.importClause, "");
        assertEquals(of8.importClause, "import com.iofairy.test.reflect.Outer.StaticInner1.Annotation;");

        assertEquals(of0.importStaticClause, "import static com.iofairy.test.reflect.Outer.*;");
        assertEquals(of1.importStaticClause, "import static com.iofairy.test.reflect.Outer.Inner1.*;");
        assertEquals(of2.importStaticClause, "import static com.iofairy.test.reflect.Outer.StaticInner1.*;");
        assertEquals(of3.importStaticClause, "import static com.iofairy.test.reflect.Outer.Inner1.Inner2.*;");
        assertEquals(of4.importStaticClause, "import static com.iofairy.test.reflect.Outer.StaticInner1.Enum1.*;");
        assertEquals(of5.importStaticClause, "");
        assertEquals(of6.importStaticClause, "");
        assertEquals(of7.importStaticClause, "");
        assertEquals(of8.importStaticClause, "import static com.iofairy.test.reflect.Outer.StaticInner1.Annotation.*;");

        assertTrue(of0.isClass);
        assertTrue(of1.isClass);
        assertTrue(of2.isClass);
        assertTrue(of3.isClass);
        assertFalse(of4.isClass);
        assertTrue(of5.isClass);
        assertTrue(of6.isClass);
        assertTrue(of7.isClass);
        assertFalse(of8.isClass);
    }
}
