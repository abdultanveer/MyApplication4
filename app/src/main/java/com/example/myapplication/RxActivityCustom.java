package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.myapplication.data.TodoNote;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class RxActivityCustom extends AppCompatActivity {

    private String TAG = RxActivityCustom.class.getSimpleName();
    CompositeDisposable disposable = new CompositeDisposable();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_custom);

        Observable<TodoNote> notesObservable = getNotesObservable();
        DisposableObserver<TodoNote> observer = getObserver();



        notesObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(getFunction())
                .subscribe(observer);

        disposable.add(observer);

    }

    private Function<? super TodoNote,TodoNote> getFunction() {
       return new Function<TodoNote, TodoNote>() {
            @Override
            public TodoNote apply(TodoNote todoNote) throws Exception {
                return new TodoNote(todoNote.getTitle().toUpperCase(),
                        todoNote.getSubTitle().toUpperCase());
            }
        };
    }

    private DisposableObserver<TodoNote> getObserver() {
       return new DisposableObserver<TodoNote>(){

            @Override
            public void onNext(TodoNote todoNote) {
                Log.d(TAG, "Note: " + todoNote.getSubTitle());
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                Log.i(TAG,"all notes emitted");

            }
        };
    }

    private Observable<TodoNote> getNotesObservable() {
        final List<TodoNote> notes = prepareNotes();

        return Observable.create(new ObservableOnSubscribe<TodoNote>() {
            @Override
            public void subscribe(ObservableEmitter<TodoNote> emitter) throws Exception {
                for (TodoNote note : notes) {
                    if (!emitter.isDisposed()) {
                        emitter.onNext(note);
                    }
                }

                if (!emitter.isDisposed()) {
                    emitter.onComplete();
                }
            }
        });
    }

    private List<TodoNote> prepareNotes() {
        List<TodoNote> notes = new ArrayList<>();
        notes.add(new TodoNote("title1", "buy tooth paste!"));
        notes.add(new TodoNote("title1", "call brother!"));
        notes.add(new TodoNote("title1", "watch narcos tonight!"));
        notes.add(new TodoNote("title1", "pay power bill!"));

        return notes;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }
}
