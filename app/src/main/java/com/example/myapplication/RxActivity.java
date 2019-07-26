package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;


import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class RxActivity extends AppCompatActivity {
public  static String TAG = RxActivity.class.getSimpleName();

Disposable disposable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx);

        Observable<String> animalsObservable = getAnimalsObservable();
                //Observable.just("Ant", "Bee", "Cat", "Dog", "Fox");
        Observer<String> animalsObserver = getAnimalsObserver();

        animalsObservable
                .subscribeOn(Schedulers.io())//Schedulers.io() – This is used to perform non CPU-intensive operations like making network calls, reading disc / files, database operations etc., This maintains pool of threads.
                .observeOn(AndroidSchedulers.mainThread())
                .filter(getPredicate())
                .subscribe(animalsObserver);//register

    }

    private Predicate<? super String> getPredicate() {
        return new Predicate<String>() {
            @Override
            public boolean test(String s) throws Exception {
                return s.toLowerCase().startsWith("b");
            }
        };
    }

    private Observer<String> getAnimalsObserver(){

        return new Observer<String>() {

            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe");
                disposable = d;

            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, "Name: " + s);

            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: " + e.getMessage());

            }

            @Override
            public void onComplete() {
                Log.d(TAG, "All items are emitted!");
            }
        };
    }

    private Observable<String> getAnimalsObservable() {
        return Observable.just("Ant", "Bee", "Cat", "Dog", "Fox",           "Bat", "Bee", "Bear", "Butterfly");  //get data from db
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();//unregister
    }
}
