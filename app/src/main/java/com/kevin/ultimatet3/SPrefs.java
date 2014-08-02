package com.kevin.ultimatet3;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by student on 7/31/2014.
 */
public class SPrefs {
    public static class SKeys {
        //String keys
        final static String name = "name";
        final static String age = "age";

        //int keys
        final static String clicks = "clicks";
    }

    public static boolean setIntArray(Context context, String key, int[] data) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(key + "_length", data.length);
        for(int i = 0; i < data.length; i++) {
            editor.putInt(key + "_" + i, data[i]);
        }
        return editor.commit();
    }

    public static int[] getIntArray(Context context, String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        int length = prefs.getInt(key + "_length", 0);
        int[] data = new int[length];
        for(int i = 0; i < length; i++) {
            data[i] = prefs.getInt(key + "_" + i, -256);
        }
        return data;
    }

    public static boolean set2dimIntArray(Context context, String key, int[][] data) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(key + "_length", data.length);
        for(int i = 0; i < data.length; i++) {
            setIntArray(context, key + "_" + i, data[i]);
        }
        return editor.commit();
    }

    public static int[][] get2dimIntArray(Context context, String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        int length = prefs.getInt(key + "_length", 0);
        int[][] data = new int[length][];
        for(int i = 0; i < length; i++) {
            data[i] = getIntArray(context, key + "_" + i);
        }
        return data;
    }

    public static boolean set4dimIntArray(Context context, String key, int[][][][] data) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(key + "_length", data.length);
        for(int i = 0; i < data.length; i++) {
            editor.putInt(key + "_length_" + i, data[i].length);
            for(int j = 0; j < data[i].length; j++) {
                set2dimIntArray(context, key + "_" + i, data[i][j]);
            }
        }
        return editor.commit();
    }

    public static int[][][][] get4dimIntArray(Context context, String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        int length = prefs.getInt(key + "_length", 0);
        int[][][][] data = new int[length][][][];
        for(int i = 0; i < length; i++) {
            int length2 = prefs.getInt(key + "_length_" + i, 0);
            for(int j = 0; j < length2; j++) {
                data[i][j] = get2dimIntArray(context, key);
            }
        }
        return data;
    }

    public static boolean setInt(Context context, String key, int data) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(key, data);
        return editor.commit();
    }

    public static int getInt(Context context, String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getInt(key, -256);
    }

    public static boolean setBool(Context context, String key, boolean data) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(key, data);
        return editor.commit();
    }

    public static boolean getBool(Context context, String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getBoolean(key, false);
    }
}
