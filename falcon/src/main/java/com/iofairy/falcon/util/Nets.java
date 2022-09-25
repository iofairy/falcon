/*
 * Copyright (C) 2021 iofairy, <https://github.com/iofairy/falcon>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.iofairy.falcon.util;

import com.iofairy.tcf.Close;
import com.iofairy.top.S;

import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;


/**
 * 网络相关工具类
 *
 * @since 0.3.9
 */
public class Nets {
    /**
     * 判断ip与端口是否可连接
     *
     * @param ip   ip
     * @param port 端口
     * @return 指定IP与端口是否可连接
     */
    public static boolean telnet(String ip, int port) {
        return telnet(ip, port, 2000);
    }

    /**
     * 判断ip与端口是否可连接
     *
     * @param ip      ip
     * @param port    端口
     * @param timeout 超时时间
     * @return 指定IP与端口是否可连接
     */
    public static boolean telnet(String ip, int port, int timeout) {
        Socket socket = null;
        try {
            socket = new Socket();
            socket.connect(new InetSocketAddress(ip, port), timeout);
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            Close.close(socket, false);
        }
    }


    /**
     * 获取本机正在使用的IPv4地址
     *
     * @return 本机正在使用的IPv4地址
     */
    public static String getLocalIPv4() {
        try {
            return Inet4Address.getLocalHost().getHostAddress();
        } catch (Exception e) {
            // 忽略异常
        }
        return "";
    }

    /**
     * 获取本机正在使用的IPv6地址
     *
     * @return 本地正在使用的IPv6地址
     * @see #getLocalIPv4()
     */
    public static List<InterfaceAddress> getLocalIPv6s() {
        String localIPv4 = getLocalIPv4();
        if (S.isEmpty(localIPv4)) {
            return new ArrayList<>();
        }

        try {
            for (Enumeration<NetworkInterface> niEnum = NetworkInterface.getNetworkInterfaces(); niEnum.hasMoreElements(); ) {
                final NetworkInterface ni = niEnum.nextElement();
                if (ni.isUp()) {
                    List<InterfaceAddress> interfaceAddresses = ni.getInterfaceAddresses();
                    String ipv4 = null;
                    List<InterfaceAddress> ipv6s = new ArrayList<>();
                    for (InterfaceAddress ia : interfaceAddresses) {
                        if (ia == null) continue;
                        InetAddress address = ia.getAddress();
                        if (address == null) continue;

                        if (address instanceof Inet4Address) {
                            ipv4 = address.getHostAddress();
                        } else {
                            ipv6s.add(ia);
                        }
                    }

                    if (localIPv4.equals(ipv4)) {
                        return ipv6s;
                    }
                }
            }
        } catch (Exception e) {
            // 忽略异常
        }
        return new ArrayList<>();
    }

    /**
     * 获取 本地链接 IPv6 地址<br>
     * 参考：<a href="https://en.wikipedia.org/wiki/IPv6#Link-local_address">IPv6: Link-local_address</a>
     *
     * @return 本地链接 IPv6 地址
     */
    public static String getLinkLocalIPv6() {
        String ipv6 = "";
        List<InterfaceAddress> interfaceAddresses = getLocalIPv6s();
        for (InterfaceAddress interfaceAddress : interfaceAddresses) {
            InetAddress address = interfaceAddress.getAddress();
            if (address.isLinkLocalAddress()) {
                ipv6 = address.getHostAddress().split("%")[0];
                break;
            }
        }
        return ipv6;
    }

    /**
     * 获取公共IPv6地址
     *
     * @return 公共IPv6地址
     */
    public static String getPublicIPv6() {
        String ipv6 = "";
        List<InterfaceAddress> interfaceAddresses = getLocalIPv6s();
        for (InterfaceAddress interfaceAddress : interfaceAddresses) {
            InetAddress address = interfaceAddress.getAddress();
            if (!address.isLinkLocalAddress()) {
                ipv6 = address.getHostAddress();
                break;
            }
        }
        return ipv6;
    }

}
