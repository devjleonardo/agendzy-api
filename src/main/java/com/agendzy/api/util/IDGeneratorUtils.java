package com.agendzy.api.util;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.SecureRandom;
import java.util.Enumeration;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public final class IDGeneratorUtils {

    private static final AtomicLong counter = new AtomicLong(0);
    private static final String MAC_ADDRESS;
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    static {
        MAC_ADDRESS = getMacValue();
    }

    private IDGeneratorUtils() {}

    public static String id() {
        var timestamp = System.currentTimeMillis();
        var localId = timestamp * 10_000 + counter.incrementAndGet();
        return Long.toHexString(localId).toUpperCase() +
                String.format("%06X", SECURE_RANDOM.nextInt(0x1000000)) +
                MAC_ADDRESS;
    }

    public static String tenant() {
        return UUID.randomUUID().toString().replace("-", "").toLowerCase();
    }

    private static String getMacValue() {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface network = networkInterfaces.nextElement();
                byte[] mac = network.getHardwareAddress();
                if (mac != null) {
                    return encodeHexString(mac);
                }
            }
        } catch (SocketException ignored) {
            // ignored
        }
        return "6D756E696666";
    }

    private static String encodeHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }

    public static String eightHexId() {
        return hexByLength(8);
    }

    public static String hexByLength(int length) {
        StringBuilder sb = new StringBuilder();
        while (sb.length() < length) {
            sb.append(Integer.toHexString(SECURE_RANDOM.nextInt()));
        }
        sb.setLength(length);
        return sb.toString();
    }

}
