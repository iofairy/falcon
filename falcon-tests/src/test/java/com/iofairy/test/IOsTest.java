package com.iofairy.test;

import com.iofairy.falcon.io.IOs;
import com.iofairy.falcon.io.MultiByteArrayInputStream;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author GG
 * @version 1.0
 */
public class IOsTest {
    @Test
    public void testToMultiBAIS() {
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(new byte[0]);
            MultiByteArrayInputStream multiBAIS = IOs.toMultiBAIS(bais);
            ByteArrayInputStream bais1 = new ByteArrayInputStream(new byte[1]);
            MultiByteArrayInputStream multiBAIS1 = IOs.toMultiBAIS(bais1);

            assertEquals(-1, multiBAIS.read());
            assertEquals(0, multiBAIS1.read());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
