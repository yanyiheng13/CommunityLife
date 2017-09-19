package com.sai.framework;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;


public class RxBus {

    private static RxBus instance;

    public static RxBus get() {
        if (instance == null) {
            synchronized (RxBus.class) {
                if (instance == null) {
                    instance = new RxBus();
                }
            }
        }
        return instance;
    }

    private RxBus() {
    }

    private ConcurrentHashMap<Object, List<Subject>> subjectMapper = new ConcurrentHashMap<>();


    public Observable register(@NonNull Object tag) {
        List<Subject> subjectList = subjectMapper.get(tag);
        if (subjectList == null) {
            subjectList = new ArrayList<>();
            subjectMapper.put(tag, subjectList);
        }

        Subject subject = PublishSubject.create();
        subjectList.add(subject);
        return subject;
    }

    public boolean isRegistered(@NonNull Object tag){
        return subjectMapper.containsKey(tag);
    }

    public void unregister(@NonNull Object tag, @NonNull Observable observable) {
        List<Subject> subjectList = subjectMapper.get(tag);
        if (subjectList != null && subjectList.size() > 0) {
            subjectList.remove(observable);

            if (subjectList != null && subjectList.size() > 0) {
                subjectMapper.remove(tag);
            }
        }
    }
    public void unregister(@NonNull Object tag) {
        List<Subject> subjectList = subjectMapper.get(tag);
        if (subjectList != null && subjectList.size() > 0) {
            subjectMapper.remove(tag);
        }
    }

    public void post(@NonNull Object tag, Object content) {
        List<Subject> subjectList = subjectMapper.get(tag);

        if (subjectList != null && subjectList.size() > 0) {
            for (Subject subject : subjectList) {
                subject.onNext(content);
            }
        }
    }
}

