package me.androider.playground.rxjava;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.annotation.Nullable;
import me.androider.playground.app.SimpleDemoActivity;
import me.androider.playground.global.Constant;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.observables.GroupedObservable;
import rx.schedulers.Schedulers;

/**
 * created by Androider on 2019/7/19 13:12
 */
public class RxJavaActivity extends SimpleDemoActivity {

    private Subscriber<String> mSubscriber;
    private Observable<String> mObservable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        btnStart.setOnClickListener((v)-> useGroupBy());
    }

    /**
     * 基本用法
     */
    private void basicUsage() {
        createObserver();
        createObservable();
        mObservable.subscribe(mSubscriber);
    }

    private void createObserver() {
        mSubscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Log.i(TAG, "onCompleted: ");
                textView.append("onCompleted: \n");
            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "onError: " + e.getMessage());
                textView.append("onError: " + e.getMessage() + "\n");
            }

            @Override
            public void onNext(String s) {
                printInfo("onNext: " + s);
            }
        };
    }

    private void createObservable() {
        mObservable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                mSubscriber.onNext("RxJava");
                mSubscriber.onNext("RxAndroid");
                mSubscriber.onCompleted();
            }
        });
    }

    /**
     * 使用Action替代Subscriber
     */
    private void useAction() {
        Action1<String> onNextAction = (s)-> printInfo("onNext: " + s);
        Action1<Throwable> onErrorAction = (t)-> printInfo("onError: " + t.getMessage());
        Action0 onCompletedAction = ()-> printInfo("onCompleted: ");

        Observable.just("RxJava", "RxAndroid").subscribe(onNextAction);
        Observable.just("RxJava", "RxAndroid").subscribe(onNextAction, onErrorAction);
        Observable.just("RxJava", "RxAndroid").subscribe(onNextAction, onErrorAction, onCompletedAction);
    }

    private void controlThread() {
        Action1<String> action = (s)-> printInfo("onNext: " + s);
        Observable.just("RxJava", "RxAndroid")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(action);
    }

    /**
     * 使用just, static方法
     */
    private void useJust() {
        createObserver();
        Observable.just("RxJava", "RxAndroid")
                .subscribe(mSubscriber);
    }

    /**
     * 使用from, static方法
     */
    private void useFrom() {
        createObserver();
        String [] s = {"RxJava", "RxAndroid"};
        Observable.from(s)
                .subscribe(mSubscriber);
    }

    /**
     * 使用interval, static方法
     * 每个1s打印一次
     */
    private void useInterval() {
        createObserver();
        Observable.interval(1, TimeUnit.SECONDS)
                .subscribe((l)-> printInfo(l));
    }

    /**
     * 使用range, static方法
     * 从0到9, 参数是0, 10, 不包括10
     */
    private void useRange() {
        createObserver();
        Observable.range(0, 10)
                .subscribe((i)-> printInfo(i));
    }

    /**
     * 使用repeat,非static方法
     * 打印0到9, 打印3次
     */
    private void useRepeat() {
        createObserver();
        Observable.range(0, 10)
                .repeat(3)
                .subscribe((i)-> printInfo(i));
    }

    private void useMap() {
        Observable.just("World")
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        return "Hello " + s;
                    }
                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        printInfo(s);
                    }
                });
    }

    /**
     * 使用 FlatMap 和 Cast
     * 注意: FlatMap 输出顺序并不一定和输入顺序一致, 如果对顺序有要求要使用concatMap
     */
    private void useFlatMapAndCast() {
        Observable.from(Constant.getSomeTextList())
                .flatMap(new Func1<String, Observable<?>>() {
                    @Override
                    public Observable<?> call(String s) {
                        return Observable.just("Hello " + s);
                    }
                })
                .cast(String.class)
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        printInfo(s);
                    }
                });
    }

    /**
     * 使用concatMap
     * concatMap 可以保证输出和输入的顺序一致
     */
    private void useConcatMap() {
        Observable.from(Constant.getSomeTextList())
                .concatMap(new Func1<String, Observable<String>>() {
                    @Override
                    public Observable<String> call(String s) {
                        return Observable.just("Hello " + s);
                    }
                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        printInfo(s);
                    }
                });
    }

    private void useFlatMapIterable() {
        Observable.just(1, 2, 3)
                .flatMapIterable(new Func1<Integer, Iterable<Integer>>() {
                    @Override
                    public Iterable<Integer> call(Integer integer) {
                        List<Integer> list = new ArrayList<>();
                        list.add(integer * 10);
                        return list;
                    }
                })
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        printInfo(integer);
                    }
                });
    }

    private void useBuffer() {
        Observable.just(1, 2, 3, 4)
                .buffer(2)
                .subscribe(new Action1<List<Integer>>() {
                    @Override
                    public void call(List<Integer> integers) {
                        printInfo(integers);
                    }
                });
    }

    /**
     * 使用
     */
    private void useGroupBy() {
        Observable.concat(Observable.just("a", "b", "c", "a", "b")
                .groupBy(new Func1<String, String>() {
                    @Override
                        public String call(String s) {
                            return s;
                        }}))
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        printInfo(s);
                    }
                });
    }
}
