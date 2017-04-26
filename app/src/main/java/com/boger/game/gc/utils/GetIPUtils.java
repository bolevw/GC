package com.boger.game.gc.utils;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * Created by liubo on 16/7/27.
 */

public class GetIPUtils {
    public static String getIpAddress(boolean alwaysGetWifi) {

        try {
            Enumeration<NetworkInterface> enmNetI = NetworkInterface.getNetworkInterfaces();
            while (enmNetI.hasMoreElements()) {
                NetworkInterface networkInterface = enmNetI.nextElement();
                boolean b = !alwaysGetWifi || (networkInterface.getDisplayName().equals("wlan0") || networkInterface.getDisplayName().equals("eth0"));
                if (b) {
                    Enumeration<InetAddress> inetAddressEnumeration = networkInterface.getInetAddresses();
                    while (inetAddressEnumeration.hasMoreElements()) {
                        InetAddress inetAddress = inetAddressEnumeration.nextElement();
                        if (inetAddress instanceof Inet4Address && !inetAddress.isLoopbackAddress()) {
                            return "net name is " + networkInterface.getDisplayName() + ",ip is " + inetAddress.getHostAddress();
                        }
                    }
                }
            }
            /*for (Enumeration<NetworkInterface> enNetI = NetworkInterface.getNetworkInterfaces(); enNetI.hasMoreElements(); ) {
                NetworkInterface netI = enNetI.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = netI.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (inetAddress instanceof Inet4Address && !inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress();
                    }
                }
            }*/
        } catch (SocketException e) {
            e.printStackTrace();
        }

        return "";
    }


}
