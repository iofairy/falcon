package com.iofairy.test;

import com.iofairy.falcon.misc.ErrorMsgType;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static com.iofairy.falcon.misc.Preconditions.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author GG
 * @version 1.0
 * @date 2024/2/9 11:23
 */
public class PreconditionsTest {
    @Test
    public void testGetErrorMsg() {
        String[] s1 = {};
        String[] s2 = null;
        String s3 = null;
        String s4 = "  ";
        String[] s5 = {null, "p2", "p3"};
        String s6 = "p1";

        ErrorMsgType type = ErrorMsgType.NULL;
        String errorMsg1 = getErrorMsg(type, s1);
        String errorMsg2 = getErrorMsg(type, s2);
        String errorMsg3 = getErrorMsg(type, s3);
        String errorMsg4 = getErrorMsg(type, s4);
        String errorMsg5 = getErrorMsg(type, s5);
        String errorMsg6 = getErrorMsg(type, s6);
        System.out.println(type + ": " + errorMsg1);
        System.out.println(type + ": " + errorMsg2);
        System.out.println(type + ": " + errorMsg3);
        System.out.println(type + ": " + errorMsg4);
        System.out.println(type + ": " + errorMsg5);
        System.out.println(type + ": " + errorMsg6);

        assertEquals(errorMsg1, "This parameter must be non-null! ");
        assertEquals(errorMsg2, "This parameter must be non-null! ");
        assertEquals(errorMsg3, "This parameter must be non-null! ");
        assertEquals(errorMsg4, "This parameter must be non-null! ");
        assertEquals(errorMsg5, "All parameters [null, p2, p3] must be non-null! ");
        assertEquals(errorMsg6, "The parameter `p1`  must be non-null! ");

        System.out.println("============================================================");
        type = ErrorMsgType.NOT_NULL;
        errorMsg1 = getErrorMsg(type, s1);
        errorMsg2 = getErrorMsg(type, s2);
        errorMsg3 = getErrorMsg(type, s3);
        errorMsg4 = getErrorMsg(type, s4);
        errorMsg5 = getErrorMsg(type, s5);
        errorMsg6 = getErrorMsg(type, s6);
        System.out.println(type + ": " + errorMsg1);
        System.out.println(type + ": " + errorMsg2);
        System.out.println(type + ": " + errorMsg3);
        System.out.println(type + ": " + errorMsg4);
        System.out.println(type + ": " + errorMsg5);
        System.out.println(type + ": " + errorMsg6);

        assertEquals(errorMsg1, "This parameter must be null! ");
        assertEquals(errorMsg2, "This parameter must be null! ");
        assertEquals(errorMsg3, "This parameter must be null! ");
        assertEquals(errorMsg4, "This parameter must be null! ");
        assertEquals(errorMsg5, "All parameters [null, p2, p3] must be null! ");
        assertEquals(errorMsg6, "The parameter `p1`  must be null! ");
        System.out.println("============================================================");
        type = ErrorMsgType.EMPTY;
        errorMsg1 = getErrorMsg(type, s1);
        errorMsg2 = getErrorMsg(type, s2);
        errorMsg3 = getErrorMsg(type, s3);
        errorMsg4 = getErrorMsg(type, s4);
        errorMsg5 = getErrorMsg(type, s5);
        errorMsg6 = getErrorMsg(type, s6);
        System.out.println(type + ": " + errorMsg1);
        System.out.println(type + ": " + errorMsg2);
        System.out.println(type + ": " + errorMsg3);
        System.out.println(type + ": " + errorMsg4);
        System.out.println(type + ": " + errorMsg5);
        System.out.println(type + ": " + errorMsg6);

        assertEquals(errorMsg1, "This parameter must be non-empty! ");
        assertEquals(errorMsg2, "This parameter must be non-empty! ");
        assertEquals(errorMsg3, "This parameter must be non-empty! ");
        assertEquals(errorMsg4, "This parameter must be non-empty! ");
        assertEquals(errorMsg5, "All parameters [null, p2, p3] must be non-empty! ");
        assertEquals(errorMsg6, "The parameter `p1`  must be non-empty! ");
        System.out.println("============================================================");
        type = ErrorMsgType.NOT_EMPTY;
        errorMsg1 = getErrorMsg(type, s1);
        errorMsg2 = getErrorMsg(type, s2);
        errorMsg3 = getErrorMsg(type, s3);
        errorMsg4 = getErrorMsg(type, s4);
        errorMsg5 = getErrorMsg(type, s5);
        errorMsg6 = getErrorMsg(type, s6);
        System.out.println(type + ": " + errorMsg1);
        System.out.println(type + ": " + errorMsg2);
        System.out.println(type + ": " + errorMsg3);
        System.out.println(type + ": " + errorMsg4);
        System.out.println(type + ": " + errorMsg5);
        System.out.println(type + ": " + errorMsg6);

        assertEquals(errorMsg1, "This parameter must be empty! ");
        assertEquals(errorMsg2, "This parameter must be empty! ");
        assertEquals(errorMsg3, "This parameter must be empty! ");
        assertEquals(errorMsg4, "This parameter must be empty! ");
        assertEquals(errorMsg5, "All parameters [null, p2, p3] must be empty! ");
        assertEquals(errorMsg6, "The parameter `p1`  must be empty! ");
        System.out.println("============================================================");
        type = ErrorMsgType.BLANK;
        errorMsg1 = getErrorMsg(type, s1);
        errorMsg2 = getErrorMsg(type, s2);
        errorMsg3 = getErrorMsg(type, s3);
        errorMsg4 = getErrorMsg(type, s4);
        errorMsg5 = getErrorMsg(type, s5);
        errorMsg6 = getErrorMsg(type, s6);
        System.out.println(type + ": " + errorMsg1);
        System.out.println(type + ": " + errorMsg2);
        System.out.println(type + ": " + errorMsg3);
        System.out.println(type + ": " + errorMsg4);
        System.out.println(type + ": " + errorMsg5);
        System.out.println(type + ": " + errorMsg6);

        assertEquals(errorMsg1, "This parameter must be non-blank! ");
        assertEquals(errorMsg2, "This parameter must be non-blank! ");
        assertEquals(errorMsg3, "This parameter must be non-blank! ");
        assertEquals(errorMsg4, "This parameter must be non-blank! ");
        assertEquals(errorMsg5, "All parameters [null, p2, p3] must be non-blank! ");
        assertEquals(errorMsg6, "The parameter `p1`  must be non-blank! ");
        System.out.println("============================================================");
        type = ErrorMsgType.NOT_BLANK;
        errorMsg1 = getErrorMsg(type, s1);
        errorMsg2 = getErrorMsg(type, s2);
        errorMsg3 = getErrorMsg(type, s3);
        errorMsg4 = getErrorMsg(type, s4);
        errorMsg5 = getErrorMsg(type, s5);
        errorMsg6 = getErrorMsg(type, s6);
        System.out.println(type + ": " + errorMsg1);
        System.out.println(type + ": " + errorMsg2);
        System.out.println(type + ": " + errorMsg3);
        System.out.println(type + ": " + errorMsg4);
        System.out.println(type + ": " + errorMsg5);
        System.out.println(type + ": " + errorMsg6);

        assertEquals(errorMsg1, "This parameter must be blank! ");
        assertEquals(errorMsg2, "This parameter must be blank! ");
        assertEquals(errorMsg3, "This parameter must be blank! ");
        assertEquals(errorMsg4, "This parameter must be blank! ");
        assertEquals(errorMsg5, "All parameters [null, p2, p3] must be blank! ");
        assertEquals(errorMsg6, "The parameter `p1`  must be blank! ");
        System.out.println("============================================================");
        type = ErrorMsgType.HAS_BLANK;
        errorMsg1 = getErrorMsg(type, s1);
        errorMsg2 = getErrorMsg(type, s2);
        errorMsg3 = getErrorMsg(type, s3);
        errorMsg4 = getErrorMsg(type, s4);
        errorMsg5 = getErrorMsg(type, s5);
        errorMsg6 = getErrorMsg(type, s6);
        System.out.println(type + ": " + errorMsg1);
        System.out.println(type + ": " + errorMsg2);
        System.out.println(type + ": " + errorMsg3);
        System.out.println(type + ": " + errorMsg4);
        System.out.println(type + ": " + errorMsg5);
        System.out.println(type + ": " + errorMsg6);

        assertEquals(errorMsg1, "None of the parameters can be blank! ");
        assertEquals(errorMsg2, "None of the parameters can be blank! ");
        assertEquals(errorMsg3, "None of the parameters can be blank! ");
        assertEquals(errorMsg4, "None of the parameters can be blank! ");
        assertEquals(errorMsg5, "None of these parameters [null, p2, p3] can be blank! ");
        assertEquals(errorMsg6, "The parameter `p1`  must be non-blank! ");
        System.out.println("============================================================");
        type = ErrorMsgType.ALL_BLANK;
        errorMsg1 = getErrorMsg(type, s1);
        errorMsg2 = getErrorMsg(type, s2);
        errorMsg3 = getErrorMsg(type, s3);
        errorMsg4 = getErrorMsg(type, s4);
        errorMsg5 = getErrorMsg(type, s5);
        errorMsg6 = getErrorMsg(type, s6);
        System.out.println(type + ": " + errorMsg1);
        System.out.println(type + ": " + errorMsg2);
        System.out.println(type + ": " + errorMsg3);
        System.out.println(type + ": " + errorMsg4);
        System.out.println(type + ": " + errorMsg5);
        System.out.println(type + ": " + errorMsg6);

        assertEquals(errorMsg1, "At least one parameter can't be blank! ");
        assertEquals(errorMsg2, "At least one parameter can't be blank! ");
        assertEquals(errorMsg3, "At least one parameter can't be blank! ");
        assertEquals(errorMsg4, "At least one parameter can't be blank! ");
        assertEquals(errorMsg5, "At least one of these parameters [null, p2, p3] can't be blank! ");
        assertEquals(errorMsg6, "The parameter `p1`  must be non-blank! ");
        System.out.println("============================================================");
        type = ErrorMsgType.HAS_EMPTY;
        errorMsg1 = getErrorMsg(type, s1);
        errorMsg2 = getErrorMsg(type, s2);
        errorMsg3 = getErrorMsg(type, s3);
        errorMsg4 = getErrorMsg(type, s4);
        errorMsg5 = getErrorMsg(type, s5);
        errorMsg6 = getErrorMsg(type, s6);
        System.out.println(type + ": " + errorMsg1);
        System.out.println(type + ": " + errorMsg2);
        System.out.println(type + ": " + errorMsg3);
        System.out.println(type + ": " + errorMsg4);
        System.out.println(type + ": " + errorMsg5);
        System.out.println(type + ": " + errorMsg6);

        assertEquals(errorMsg1, "None of the parameters can be empty! ");
        assertEquals(errorMsg2, "None of the parameters can be empty! ");
        assertEquals(errorMsg3, "None of the parameters can be empty! ");
        assertEquals(errorMsg4, "None of the parameters can be empty! ");
        assertEquals(errorMsg5, "None of these parameters [null, p2, p3] can be empty! ");
        assertEquals(errorMsg6, "The parameter `p1`  must be non-empty! ");
        System.out.println("============================================================");
        type = ErrorMsgType.ALL_EMPTY;
        errorMsg1 = getErrorMsg(type, s1);
        errorMsg2 = getErrorMsg(type, s2);
        errorMsg3 = getErrorMsg(type, s3);
        errorMsg4 = getErrorMsg(type, s4);
        errorMsg5 = getErrorMsg(type, s5);
        errorMsg6 = getErrorMsg(type, s6);
        System.out.println(type + ": " + errorMsg1);
        System.out.println(type + ": " + errorMsg2);
        System.out.println(type + ": " + errorMsg3);
        System.out.println(type + ": " + errorMsg4);
        System.out.println(type + ": " + errorMsg5);
        System.out.println(type + ": " + errorMsg6);

        assertEquals(errorMsg1, "At least one parameter can't be empty! ");
        assertEquals(errorMsg2, "At least one parameter can't be empty! ");
        assertEquals(errorMsg3, "At least one parameter can't be empty! ");
        assertEquals(errorMsg4, "At least one parameter can't be empty! ");
        assertEquals(errorMsg5, "At least one of these parameters [null, p2, p3] can't be empty! ");
        assertEquals(errorMsg6, "The parameter `p1`  must be non-empty! ");
        System.out.println("============================================================");
        type = ErrorMsgType.HAS_NULL;
        errorMsg1 = getErrorMsg(type, s1);
        errorMsg2 = getErrorMsg(type, s2);
        errorMsg3 = getErrorMsg(type, s3);
        errorMsg4 = getErrorMsg(type, s4);
        errorMsg5 = getErrorMsg(type, s5);
        errorMsg6 = getErrorMsg(type, s6);
        System.out.println(type + ": " + errorMsg1);
        System.out.println(type + ": " + errorMsg2);
        System.out.println(type + ": " + errorMsg3);
        System.out.println(type + ": " + errorMsg4);
        System.out.println(type + ": " + errorMsg5);
        System.out.println(type + ": " + errorMsg6);

        assertEquals(errorMsg1, "None of the parameters can be null! ");
        assertEquals(errorMsg2, "None of the parameters can be null! ");
        assertEquals(errorMsg3, "None of the parameters can be null! ");
        assertEquals(errorMsg4, "None of the parameters can be null! ");
        assertEquals(errorMsg5, "None of these parameters [null, p2, p3] can be null! ");
        assertEquals(errorMsg6, "The parameter `p1`  must be non-null! ");
        System.out.println("============================================================");
        type = ErrorMsgType.ALL_NULL;
        errorMsg1 = getErrorMsg(type, s1);
        errorMsg2 = getErrorMsg(type, s2);
        errorMsg3 = getErrorMsg(type, s3);
        errorMsg4 = getErrorMsg(type, s4);
        errorMsg5 = getErrorMsg(type, s5);
        errorMsg6 = getErrorMsg(type, s6);
        System.out.println(type + ": " + errorMsg1);
        System.out.println(type + ": " + errorMsg2);
        System.out.println(type + ": " + errorMsg3);
        System.out.println(type + ": " + errorMsg4);
        System.out.println(type + ": " + errorMsg5);
        System.out.println(type + ": " + errorMsg6);

        assertEquals(errorMsg1, "At least one parameter can't be null! ");
        assertEquals(errorMsg2, "At least one parameter can't be null! ");
        assertEquals(errorMsg3, "At least one parameter can't be null! ");
        assertEquals(errorMsg4, "At least one parameter can't be null! ");
        assertEquals(errorMsg5, "At least one of these parameters [null, p2, p3] can't be null! ");
        assertEquals(errorMsg6, "The parameter `p1`  must be non-null! ");
    }

    @Test
    public void testPreconditions() {
        System.out.println("===============================1=============================");
        try {
            checkBlank(" ");
        } catch (Exception e) {
            System.out.println(e.getClass());
            System.out.println(e.getMessage());
            assertEquals(e.getClass().toString(), "class com.iofairy.except.ConditionsNotMetException");
            assertEquals(e.getMessage(), "This parameter must be non-blank! ");
        }
        System.out.println("===============================2=============================");
        try {
            checkNotBlank("1", null);
        } catch (Exception e) {
            System.out.println(e.getClass());
            System.out.println(e.getMessage());
            assertEquals(e.getClass().toString(), "class com.iofairy.except.ConditionsNotMetException");
            assertEquals(e.getMessage(), "This parameter must be blank! ");
        }
        System.out.println("===============================3=============================");
        try {
            checkHasBlank(args("1", "a", "  "), args("p1", "p2", "p3"));
        } catch (Exception e) {
            System.out.println(e.getClass());
            System.out.println(e.getMessage());
            assertEquals(e.getClass().toString(), "class com.iofairy.except.ConditionsNotMetException");
            assertEquals(e.getMessage(), "None of these parameters [p1, p2, p3] can be blank! ");
        }
        System.out.println("===============================4=============================");
        try {
            checkHasBlank(Arrays.asList("1", "a", "b"), args("p1", "p2", "p3"));
        } catch (Exception e) {
            System.out.println(e.getClass());
            System.out.println(e.getMessage());
            assertEquals(e.getClass().toString(), "class com.iofairy.except.ConditionsNotMetException");
            assertEquals(e.getMessage(), "");
        }
        System.out.println("===============================5=============================");
        try {
            checkHasNullNPE(args(" ", null, new StringBuilder("abc")));
        } catch (Exception e) {
            System.out.println(e.getClass());
            System.out.println(e.getMessage());
            assertEquals(e.getClass().toString(), "class java.lang.NullPointerException");
            assertEquals(e.getMessage(), "None of the parameters can be null! ");
        }
        System.out.println("===============================6=============================");
        try {
            StringBuilder s = null;
            checkAllNullNPE(args(s, " ", null), null);
        } catch (Exception e) {
            System.out.println(e.getClass());
            System.out.println(e.getMessage());
            assertEquals(e.getClass().toString(), "class java.lang.NullPointerException");
            assertEquals(e.getMessage(), "");
        }
        System.out.println("===============================7=============================");
        try {
            StringBuilder s = null;
            checkAllNullNPE(args(s, null), args("name", "id"));
        } catch (Exception e) {
            System.out.println(e.getClass());
            System.out.println(e.getMessage());
            assertEquals(e.getClass().toString(), "class java.lang.NullPointerException");
            assertEquals(e.getMessage(), "At least one of these parameters [name, id] can't be null! ");
        }
    }
}
