package com.bigao.backend.common.config;

/**
 * Created by wait on 2015/12/15.
 */
public abstract class BaseBean {
    public abstract int read(String[] array);

    public String getString(String[] array, int index) {
        if (index >= array.length) {
            return null;
        }
        if (array[index] == null || array[index].isEmpty()) {
            return null;
        }
        return clearDoubleQuotes(array[index]);
    }

    public int getInt(String[] array, int index) {
        if (index >= array.length) {
            return 0;
        }
        if (array[index] == null || array[index].isEmpty()) {
            return 0;
        }
        return Integer.parseInt(clearDoubleQuotes(array[index]));
    }

    private String clearDoubleQuotes(String str) {
        if (str.charAt(0) == '"') {
            return str.substring(1, str.length() - 1);
        }
        return str;
    }
}
