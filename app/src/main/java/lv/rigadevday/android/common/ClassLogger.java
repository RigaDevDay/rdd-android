package lv.rigadevday.android.common;

import android.util.Log;

public class ClassLogger {

    public  final int VERBOSE = 2;
    public  final int DEBUG = 3;
    public  final int INFO = 4;
    public  final int WARN = 5;
    public  final int ERROR = 6;
    public  final int ASSERT = 7;

    private Class clazz;

    public ClassLogger(Class clazz) {
        this.clazz = clazz;
    }

    public int v(String msg) {
        return Log.v(clazz.getName(), msg);
    }
    
    public int v(String msg, Throwable tr) {
        return Log.v(clazz.getName(), msg, tr);
    }
    
    public int d(String msg) { 
        return Log.d(clazz.getName(), msg);
    }
    
    public int d(String msg, Throwable tr) {
        return Log.d(clazz.getName(), msg, tr);
    }

    public int i(String msg) {
        return Log.i(clazz.getName(), msg);
    }

    public int i(String msg, Throwable tr) {
        return Log.i(clazz.getName(), msg, tr);
    }

    public int w(String msg) {
        return Log.w(clazz.getName(), msg);
    }

    public int w(String msg, Throwable tr) {
        return Log.w(clazz.getName(), msg, tr);
    }

    public boolean isLoggable(int level) {
        return Log.isLoggable(clazz.getName(), level);
    }

    public int w(Throwable tr) {
        return Log.w(clazz.getName(), tr);
    }

    public int e(String msg) {
        return Log.e(clazz.getName(), msg);
    }

    public int e(String msg, Throwable tr) {
        return Log.e(clazz.getName(), msg, tr);
    }

    public int wtf(String msg) {
        return Log.wtf(clazz.getName(), msg);
    }

    public int wtf(Throwable tr) {
        return Log.wtf(clazz.getName(), tr);
    }

    public int wtf(String msg, Throwable tr) {
        return Log.wtf(clazz.getName(), msg, tr);
    }

    public String getStackTraceString(Throwable tr) {
        return Log.getStackTraceString(tr);
    }
    public int println(int priority, String msg) {
        return Log.println(priority, clazz.getName(), msg);
    }
}
