package com.sky.context;

public class BaseContext implements AutoCloseable{

    public static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static void setCurrentId(Long id) {
        threadLocal.set(id);
    }

    public static Long getCurrentId() {
        return threadLocal.get();
    }

    public static void removeCurrentId() {
        threadLocal.remove();
    }

    @Override
    public void close() throws Exception {
        threadLocal.remove();
    }
}
