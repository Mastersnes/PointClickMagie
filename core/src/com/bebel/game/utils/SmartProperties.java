package com.bebel.game.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import static com.bebel.game.utils.SecurityUtils.decrypt;
import static com.bebel.game.utils.SecurityUtils.encrypt;

/**
 * Properties intelligent
 */
public class SmartProperties extends Properties {
    //String
    public String get(final String key, final String defVal) {
        return decrypt(getProperty(key), defVal);
    }
    public void set(final String key, final String val) {
        setProperty(key, encrypt(val));
    }
    public List<String> get(final String key) {
        final String val = get(key, "");
        if (val == null || val.isEmpty()) return new ArrayList<>();
        else return Arrays.asList(val.split(","));
    }
    public void set(final String key, final List<String> list) {
        String val;
        if (list == null || list.isEmpty()) val = "";
        else val = String.join(",", list);
        setProperty(key, encrypt(val));
    }

    //Boolean
    public boolean get(final String key, final boolean defVal) {
        final String val = get(key, Boolean.toString(defVal));
        return Boolean.valueOf(val);
    }
    public void set(final String key, final boolean val) {
        set(key, Boolean.toString(val));
    }

    // Int
    public int get(final String key, final int defVal) {
        final String val = get(key, Integer.toString(defVal));
        return Integer.valueOf(val);
    }
    public void set(final String key, final int val) {
        set(key, Integer.toString(val));
    }

    // List Int
    public List<Integer> getListInt(final String key) {
        final List<String> list = get(key);
        if (list == null || list.isEmpty()) return new ArrayList<>();
        else return list
            .stream()
            .map(Integer::valueOf)
            .collect(Collectors.toList());
    }
    public void setListInt(final String key, final List<Integer> list) {
        List<String> listStr;
        if (list == null || list.isEmpty()) listStr = null;
        else listStr = list
            .stream()
            .map(String::valueOf)
            .collect(Collectors.toList());
        set(key, listStr);
    }
}
