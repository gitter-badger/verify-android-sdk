/*
 * Copyright (c) 2015 Nexmo Inc
 * All rights reserved.
 *
 * Licensed only under the Nexmo Verify SDK License Agreement located at
 *
 * https://www.nexmo.com/terms-use/verify-sdk/ (the “License”)
 *
 * You may not use, exercise any rights with respect to or exploit this SDK,
 * or any modifications or derivative works thereof, except in accordance
 * with the License.
 */

package com.nexmo.sdk.core.device;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Locale;

/**
 * Utility class for accessing device properties.
 */
public class DeviceProperties {

    /** Log tag. */
    private static final String TAG = DeviceProperties.class.getSimpleName();

    /**
     * Get the Android API version.
     *
     * @return The Android API version.
     */
    public static String getApiLevel() {
        return Build.VERSION.RELEASE;
    }

    /**
     * Get the Android unique IMEI of the current handset.
     * @param context The context of the sender activity.
     *
     * @return The unique IMEI of the device.
     */
    public static String getIMEI(Context context) {
        if (context != null) {
            // Use the Application context to prevent memory leaks when referencing activities that are being killed.
            Context appContext = context.getApplicationContext();
            TelephonyManager manager  = (TelephonyManager) appContext.getSystemService(Context.TELEPHONY_SERVICE);
            return manager.getDeviceId();
        }
        return null;
    }

    /**
     * Get the IP address of the current device.
     * @param context The context of the sender activity.
     *
     * @return The IP address of the current device.
     */
    public static String getIPAddress(Context context) {
        if (context != null) {
            // Use the Application context to prevent memory leaks when referencing activities that are being killed.
            Context appContext = context.getApplicationContext();

            WifiManager manager = (WifiManager) appContext.getSystemService(Context.WIFI_SERVICE);
            // Get the WiFi or cellular network IP address.
            if (manager.isWifiEnabled()) {
                int ipAddress = manager.getConnectionInfo().getIpAddress();
                // Format the integer IP address to the numeric representation.
                return ((ipAddress>>24) & 0xFF) + "." + ((ipAddress>>16) & 0xFF) + "." + ((ipAddress>>8) & 0xFF) + "." + ((ipAddress & 0xFF));
            } else {
                try {
                    for (Enumeration<NetworkInterface> networks = NetworkInterface.getNetworkInterfaces(); networks.hasMoreElements();) {
                        NetworkInterface networkInterface = networks.nextElement();
                        for (Enumeration<InetAddress> ipAddresses = networkInterface.getInetAddresses(); ipAddresses.hasMoreElements();) {
                            InetAddress inetAddress = ipAddresses.nextElement();
                            // Ignore the loopback address.
                            // // If only the loopback is available, it is not possible to do any requests to the service.
                            if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                                return inetAddress.getHostAddress();
                            }
                        }
                    }
                } catch (SocketException e) {
                    Log.e(TAG, "Error getting the IP address." + e.getStackTrace());
                }
            }
        }
        return  null;
    }

    /**
     * Get the user's preferred locale language. The format is language code and country code, separated by dash.
     * <p> Since the user's locale changes dynamically, avoid caching this value.
     *
     * @return The user's preferred language.
     */
    public static String getLanguage() {
        String language = Locale.getDefault().toString();
        if (!TextUtils.isEmpty(language) && language.indexOf("_") > 1) {
            return language.replace("_", "-");
        }
        return null;
    }

}