package com.iofairy.test;

import com.iofairy.falcon.util.Nets;
import org.junit.jupiter.api.Test;

import java.net.*;
import java.util.List;

/**
 * @author GG
 * @version 1.0
 */
public class NetsTest {
    @Test
    public void testTelnet() {
        boolean telnet0 = Nets.telnet("127.0.0.1", 80);
        boolean telnet1 = Nets.telnet("240e:467:e80:b284:9461:1370:bbd8:383c", 80);
        boolean telnet2 = Nets.telnet("192.168.0.10", 80);
        boolean telnet3 = Nets.telnet("240e:467:e80:b284:9461:1370:456:789", 80);
        boolean telnet4 = Nets.telnet("localhost", 80);

        System.out.println("telnet0: " + telnet0);
        System.out.println("telnet1: " + telnet1);
        System.out.println("telnet2: " + telnet2);
        System.out.println("telnet3: " + telnet3);
        System.out.println("telnet4: " + telnet4);
    }

    @Test
    public void testGetIP() {
        String localIPv4 = Nets.getLocalIPv4();
        System.out.println("localIPv4: " + localIPv4);

        List<InterfaceAddress> localIPv6s = Nets.getLocalIPv6s();
        System.out.println("localIPv6s: " + localIPv6s);

        String linkLocalIPv6 = Nets.getLinkLocalIPv6();
        System.out.println("linkLocalIPv6: " + linkLocalIPv6);

        String publicIPv6 = Nets.getPublicIPv6();
        System.out.println("publicIPv6: " + publicIPv6);
    }

}
