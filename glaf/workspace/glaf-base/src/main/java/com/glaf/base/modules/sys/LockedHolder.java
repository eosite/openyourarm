package com.glaf.base.modules.sys;


public class LockedHolder {

    /**
     * ThreadLocal to hold the Assertion for Threads to access.
     */
    private static final ThreadLocal<String> threadLocal = new ThreadLocal<String>();


    public static String getLocked() {
        return threadLocal.get();
    }


    public static void setLocked(final String locked) {
        threadLocal.set(locked);
    }

    /**
     * Clear the ThreadLocal.
     */
    public static void clear() {
        threadLocal.set(null);
    }
}
