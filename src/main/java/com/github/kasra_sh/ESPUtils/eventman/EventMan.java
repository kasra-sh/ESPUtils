package com.github.kasra_sh.ESPUtils.eventman;

import com.github.kasra_sh.ESPUtils.ULog;
import com.github.kasra_sh.ESPUtils.estore.EStoreDB;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class EventMan {

    private static final String TAG = "EventMain";
    private static ArrayList<ReceiverMethod> subs = new ArrayList<>();
    private static EStoreDB msgDB = new EStoreDB();
    private static Executor executor = Executors.newFixedThreadPool(2);

    public static void unsafeRegister(Object object) {

        if (object == null) {
            try {
                throw new NullPointerException("Cannot register null object");
            } catch (NullPointerException e){
                StackTraceElement[] ste = e.getStackTrace();
                ULog.w(TAG, e.getMessage()+" : "+ste[1].getClassName());
                return;
            }
        }

        Method[] methods = object.getClass().getDeclaredMethods();
        for (int i = 0; i < methods.length; i++) {
            if (methods[i].isAnnotationPresent(EventReceiver.class)) {
                if (methods[i].getParameterCount() != 1) {
                    try {
                        throw new MultiParameterException(methods[i].getName());
                    } catch (MultiParameterException e) {
                        StackTraceElement[] ste = e.getStackTrace();
                        ULog.w(TAG, e.getMessage()+" : "+ste[1].getClassName());
                    }
                } else {

                    if (methods[i].getParameters()[0].getType().isPrimitive()) {
                        try {
                            throw new PrimitiveParameterException(methods[i].getName());
                        } catch (PrimitiveParameterException e) {
                            StackTraceElement[] ste = e.getStackTrace();
                            ULog.w(TAG, e.getMessage()+" : "+ste[1].getClassName());
                        }
                    }
                    subs.add(
                            new ReceiverMethod(
                                    methods[i],
                                    methods[i].getParameters()[0],
                                    object
                            )
                    );
                    ULog.d(TAG, "Registered "
                            + object.getClass().getName()
                            + "."
                            + methods[i].getName()
                            + "(" + methods[i].getParameters()[0].getType().getSimpleName() + ") ."
                    );
                }
            }
        }
        try {
            register(object);
        } catch (MultiParameterException | PrimitiveParameterException e) {
            StackTraceElement[] ste = e.getStackTrace();
            ULog.w(TAG, e.getMessage()+" : "+ste[1].getClassName());
        }
    }

    public static void register(Object object) throws MultiParameterException, PrimitiveParameterException {

        if (object == null) {
            throw new NullPointerException("Cannot register null object");
        }

        Method[] methods = object.getClass().getDeclaredMethods();
        for (int i = 0; i < methods.length; i++) {
            if (methods[i].isAnnotationPresent(EventReceiver.class)) {
                if (methods[i].getParameterCount() != 1) {
                    throw new MultiParameterException(methods[i].getName());
                } else {
                    if (methods[i].getParameters()[0].getType().isPrimitive()) {
                        throw new PrimitiveParameterException(methods[i].getName());
                    }
                    subs.add(
                            new ReceiverMethod(
                                    methods[i],
                                    methods[i].getParameters()[0],
                                    object
                            )
                    );
                    ULog.d(TAG, "Registered "
                            + object.getClass().getName()
                            + "."
                            + methods[i].getName()
                            + "(" + methods[i].getParameters()[0].getType().getSimpleName() + ") ."
                    );
                }
            }
        }
    }

    private static synchronized boolean receiverExists(Object object) {
        for (ReceiverMethod sub : subs) {
            if (sub.getReceiverObject().getClass().equals(object.getClass())) {
                return true;
            }
        }
        return false;
    }

    public static synchronized void unregister(Object object) {

        ArrayList<ReceiverMethod> newSubs = new ArrayList<>();

        for (int i = 0; i < subs.size(); i++) {
            ReceiverMethod sub = subs.get(i);
            if (sub.getReceiverObject().equals(object)) {
                continue;
            } else {
                newSubs.add(subs.get(i));
            }
        }

        subs.clear();
        subs.addAll(newSubs);
    }

    private static class PostRunnable implements Runnable {
        private Object object;

        private PostRunnable(Object object) {
            this.object = object;
        }

        @Override
        public void run() {
            Object obj = object;
            if (object == null) {
                obj = new Object();
            }
            Class<?> objClass = obj.getClass();
            for (int i = 0; i < subs.size(); i++) {
                Class<?> subClass = subs.get(i).getParameter().getType();
//            System.out.println(objClass.getTypeName() + " : " + subClass.getName());
                if (objClass.getName().equals(subClass.getName())) {
//                ULog.d(TAG, "obj name equals sub");
                    subs.get(i).getMethod().setAccessible(true);
//                    try {
//                        System.out.println(subClass.isInstance(obj));
//                    } catch (Exception e){
//                        e.printStackTrace();
//                    }
                    try {
                        subs.get(i).getMethod().invoke(subs.get(i).getReceiverObject(), obj);
                    } catch (IllegalAccessException | InvocationTargetException e) {
//                        unregister(subs.get(i).getReceiverObject());
//                        ULog.wtf(TAG, e);
                    }
                } else if (objClass.isInstance(subClass)
                        || subClass.isInstance(objClass)
                        || subClass.isAssignableFrom(objClass)
                        || objClass.isAssignableFrom(subClass)) {
//                    ULog.d(TAG,objClass.toGenericString());
                    subs.get(i).getMethod().setAccessible(true);
                    try {
                        subs.get(i).getMethod().invoke(subs.get(i).getReceiverObject(), obj);
                    } catch (IllegalAccessException | InvocationTargetException e) {
//                        unregister(subs.get(i).getReceiverObject());
                        ULog.wtf(TAG, e);
                    }
                }
            }
        }
    }

    public static synchronized void post(Object object) {
        executor.execute(new PostRunnable(object));
    }

}
