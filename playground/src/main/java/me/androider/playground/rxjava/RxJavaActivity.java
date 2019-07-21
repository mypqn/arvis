package me.androider.playground.rxjava;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import androidx.annotation.Nullable;

import me.androider.playground.global.app.SimpleDemoActivity;
import me.androider.playground.global.Constant;
import me.androider.playground.global.model.SimpleModel;
import rx.Notification;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
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

        btnStart.setOnClickListener((v) -> useToMap());
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
        Action1<String> onNextAction = (s) -> printInfo("onNext: " + s);
        Action1<Throwable> onErrorAction = (t) -> printInfo("onError: " + t.getMessage());
        Action0 onCompletedAction = () -> printInfo("onCompleted: ");

        Observable.just("RxJava", "RxAndroid").subscribe(onNextAction);
        Observable.just("RxJava", "RxAndroid").subscribe(onNextAction, onErrorAction);
        Observable.just("RxJava", "RxAndroid").subscribe(onNextAction, onErrorAction, onCompletedAction);
    }

    private void controlThread() {
        Action1<String> action = (s) -> printInfo("onNext: " + s);
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
        String[] s = {"RxJava", "RxAndroid"};
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
                .subscribe((l) -> printInfo(l));
    }

    /**
     * 使用range, static方法
     * 从0到9, 参数是0, 10, 不包括10
     */
    private void useRange() {
        createObserver();
        Observable.range(0, 10)
                .subscribe((i) -> printInfo(i));
    }

    /**
     * 使用repeat,非static方法
     * 打印0到9, 打印3次
     */
    private void useRepeat() {
        createObserver();
        Observable.range(0, 10)
                .repeat(3)
                .subscribe((i) -> printInfo(i));
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
                    }
                }))
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        printInfo(s);
                    }
                });
    }

    private void useFilter() {
        Observable.just(1, 2, 3, 4).filter(new Func1<Integer, Boolean>() {
            @Override
            public Boolean call(Integer integer) {
                return integer > 2;
            }
        })
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        printInfo("useFilter: " + integer);
                    }
                });
    }

    private void useElementAt() {
        Observable.just(1, 2, 3, 4).elementAt(3)
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        printInfo("useElementAt: " + integer);
                    }
                });
    }

    private void useDistinct() {
        Observable.just(1, 2, 2, 3, 1, 4).distinct()
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        printInfo("useDistinct: " + integer);
                    }
                });
    }

    private void useSkip() {
        Observable.just(1, 2, 3, 4).skip(2)
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        printInfo("useSkip: " + integer);
                    }
                });
    }

    private void useTake() {
        Observable.just(1, 2, 3, 4).take(2)
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        printInfo("useTake: " + integer);
                    }
                });
    }

    private void useIgnoreElements() {
        Observable.just(1, 2, 3, 4).ignoreElements()
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        printInfo("useIgnoreElements: " + integer);
                    }
                });
    }

    private void useThrottleFirst() {
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                for (int i = 0; i < 10; i++) {
                    subscriber.onNext(i);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                subscriber.onCompleted();
            }
        }).throttleFirst(200, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        printInfo("useThrottleFirst: " + integer);
                    }
                });
    }

    private void useThrottleWithTimeout() {
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                for (int i = 0; i < 10; i++) {
                    subscriber.onNext(i);
                    int sleep = 100;
                    if (i % 3 == 0) {
                        sleep = 300;
                    }
                    try {
                        Thread.sleep(sleep);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                subscriber.onCompleted();
            }
        }).throttleWithTimeout(200, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        printInfo("useThrottleWithTimeout: " + integer);
                    }
                });
    }

    private void useStartWith() {
        Observable.just(3, 4).startWith(1, 2)
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        printInfo("useStartWith: " + integer);
                    }
                });
    }

    /**
     * 使用merge
     * merge不保证顺序， 如果要保证顺序，要使用concat
     */
    private void useMerge() {
        Observable<Integer> o1 = Observable.just(1, 2).subscribeOn(Schedulers.io());
        Observable<Integer> o2 = Observable.just(3, 4).subscribeOn(Schedulers.io());
        Observable.merge(o1, o2)
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        printInfo("useMerge: " + integer);
                    }
                });
    }

    /**
     * 使用concat
     * concat可以确保顺序
     */
    private void useConcat() {
        Observable<Integer> o1 = Observable.just(1, 2).subscribeOn(Schedulers.io());
        Observable<Integer> o2 = Observable.just(3, 4).subscribeOn(Schedulers.io());
        Observable.concat(o1, o2)
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        printInfo("useConcat: " + integer);
                    }
                });
    }

    private void useZip() {
        Observable<String> o1 = Observable.just("a", "b").subscribeOn(Schedulers.io());
        Observable<Integer> o2 = Observable.just(1, 2).subscribeOn(Schedulers.io());
        Observable.zip(o1, o2, new Func2<String, Integer, String>() {
            @Override
            public String call(String s, Integer integer) {
                return s + integer;
            }
        })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        printInfo("useZip: " + s);
                    }
                });
    }

    /**
     * 前一个Observable只输出最后一个
     */
    private void useCombineLatest() {
        Observable<String> o1 = Observable.just("a", "b").subscribeOn(Schedulers.io());
        Observable<Integer> o2 = Observable.just(1, 2).subscribeOn(Schedulers.io());
        Observable.combineLatest(o1, o2, new Func2<String, Integer, String>() {
            @Override
            public String call(String s, Integer integer) {
                return s + integer;
            }
        })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        printInfo("combineLatest: " + s);
                    }
                });
    }

    private void useDelay() {
        Observable.create(new Observable.OnSubscribe<Long>() {
            @Override
            public void call(Subscriber<? super Long> subscriber) {
                long current = System.currentTimeMillis() / 1000;
                subscriber.onNext(current);
            }
        })
                .delay(2, TimeUnit.SECONDS)
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        printInfo("useDelay: " + (System.currentTimeMillis() / 1000 - aLong));
                    }
                });
    }

    private void useDoOnEach() {
        Observable.just(1, 2, 3)
                .doOnEach(new Action1<Notification<? super Integer>>() {
                    @Override
                    public void call(Notification<? super Integer> notification) {
                        printInfo("doOnEach: " + notification.getValue());
                    }
                })
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onCompleted() {
                        printInfo("useDoOnEach onCompleted: ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        printInfo("useDoOnEach onError: " + e.getMessage());
                    }

                    @Override
                    public void onNext(Integer integer) {
                        printInfo("useDoOnEach onNext: " + integer);
                    }
                });
    }

    private void useDoOnNext() {
        Observable.just(1, 2, 3)
                .doOnNext(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        printInfo("doOnNext: " + integer);
                    }
                })
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onCompleted() {
                        printInfo("useDoOnNext onCompleted: ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        printInfo("useDoOnNext onError: " + e.getMessage());
                    }

                    @Override
                    public void onNext(Integer integer) {
                        printInfo("useDoOnNext onNext: " + integer);
                    }
                });
    }

    private void useDoOnSubscribe() {
        Observable.just(1, 2, 3)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        printInfo("doOnSubscribe: ");
                    }
                })
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        printInfo("userDoOnSubscribe onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        printInfo("userDoOnSubscribe onError: " + e.getMessage());
                    }

                    @Override
                    public void onNext(Integer integer) {
                        printInfo("userDoOnSubscribe onNext: " + integer);
                    }
                });
    }

    /**
     * doOnTerminate 比 doOnCompleted 先执行
     */
    private void useDoOnTerminateAndDoOnCompleted() {
        Observable.just(1, 2, 3)
                .doOnCompleted(new Action0() {
                    @Override
                    public void call() {
                        printInfo("useDoOnTerminate doOnCompleted: ");
                    }
                })
                .doOnTerminate(new Action0() {
                    @Override
                    public void call() {
                        printInfo("useDoOnTerminate doOnTerminate: ");
                    }
                })
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        printInfo("useDoOnTerminate onCompleted: ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        printInfo("useDoOnTerminate onError: " + e.getMessage());
                    }

                    @Override
                    public void onNext(Integer integer) {
                        printInfo("useDoOnTerminate onNext: " + integer);
                    }
                });
    }

    private void useSubscribeOnAndObserveOn() {
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                printInfo("useSubscribeOnAndObserveOn " + Thread.currentThread().getName());
                subscriber.onNext(1);
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        printInfo("useSubscribeOnAndObserveOn onCompleted: ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        printInfo("useSubscribeOnAndObserveOn onError: " + e.getMessage());
                    }

                    @Override
                    public void onNext(Integer integer) {
                        printInfo("useSubscribeOnAndObserveOn onNext: " + integer + "\t" +
                                Thread.currentThread().getName());

                    }
                });
    }

    private void useTimeout() {
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                for (int i = 0; i < 6; i++) {
                    try {
                        Thread.sleep(i * 100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    subscriber.onNext(i);
                }
                subscriber.onCompleted();
            }
        })
                .timeout(400, TimeUnit.MILLISECONDS, Observable.just(10, 20))
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        printInfo("useTimeout onCompleted: ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        printInfo("useTimeout onError: " + e.getMessage());
                    }

                    @Override
                    public void onNext(Integer integer) {
                        printInfo("useTimeout onNext: " + integer);
                    }
                });
    }

    private void useError() {
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                for (int i = 0; i < 6; i++) {
                    if (i > 3) {
                        subscriber.onError(new Throwable("useError"));
                    }
                    subscriber.onNext(i);
                }
                subscriber.onCompleted();
            }
        })
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends Integer>>() {
                    @Override
                    public Observable<? extends Integer> call(Throwable throwable) {
                        printInfo("useError onErrorResumeNext: " + throwable.getMessage());
                        return Observable.just(10, 20, 30);
                    }
                })
                .onErrorReturn(new Func1<Throwable, Integer>() {
                    @Override
                    public Integer call(Throwable throwable) {
                        printInfo("useError onErrorReturn: " + throwable.getMessage());
                        return -1;
                    }
                })
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onCompleted() {
                        printInfo("useError onCompleted: ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        printInfo("useError onError: " + e.getMessage());
                    }

                    @Override
                    public void onNext(Integer integer) {
                        printInfo("useError onNext: " + integer);
                    }
                });
    }

    private void useRetry() {
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                for (int i = 1; i < 6; i++) {
                    if (i % 3 == 0) {
                        subscriber.onError(new Throwable("useRetry"));
                    }
                    subscriber.onNext(i);
                }
                subscriber.onCompleted();
            }
        })
                .retry(2)
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        printInfo("useRetry onCompleted: ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        printInfo("useRetry onError: " + e.getMessage());
                    }

                    @Override
                    public void onNext(Integer integer) {
                        printInfo("useRetry onNext: " + integer);
                    }
                });
    }

    private void useAll() {
        Observable.just(1, 2, 3, 4)
                .all(new Func1<Integer, Boolean>() {
                    @Override
                    public Boolean call(Integer integer) {
                        printInfo("useAll all: " + integer);
                        return integer < 2;
                    }
                })
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onCompleted() {
                        printInfo("useAll onCompleted: ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        printInfo("useAll onError: " + e.getMessage());
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        printInfo("useAll onNext: " + aBoolean);
                    }
                });
    }

    private void useContains() {
        Observable.just(1, 2, 3, 4)
                .contains(3)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        printInfo("useContains: " + aBoolean);
                    }
                });
    }

    /**
     * "" 和 null 在 isEmpty 都为false, empty是没有信息传输
     */
    private void useIsEmpty() {
        Observable.just("a", "", null, "c")
                .isEmpty()
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        printInfo("useIsEmpty: " + aBoolean);
                    }
                });
    }

    /**
     * 只会接受到最早的一个Observable
     */
    private void useAmb() {
        Observable.amb(Observable.just(1, 2, 3).delay(200, TimeUnit.MILLISECONDS),
                Observable.just(10, 20, 30),
                Observable.just(100, 200, 300).delay(100, TimeUnit.MILLISECONDS))
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        printInfo("useAmb: " + integer);
                    }
                });
    }

    private void useDefaultIfEmpty() {
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
//                subscriber.onNext(1);
                subscriber.onCompleted();
            }
        })
                .defaultIfEmpty(6)
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        printInfo("useDefaultIfEmpty: " + integer);
                    }
                });
    }

    private void useToList() {
        Observable.just(1, 3, 2)
                .toList()
                .subscribe(new Action1<List<Integer>>() {
                    @Override
                    public void call(List<Integer> integers) {
                        printInfo("useToList: " + integers);
                    }
                });
    }

    private void useToSortedList() {
        Observable.just(1, 3, 2)
                .toSortedList()
                .subscribe(new Action1<List<Integer>>() {
                    @Override
                    public void call(List<Integer> integers) {
                        printInfo("useToSortedList: " + integers);
                    }
                });
    }

    private void useToMap() {
        SimpleModel sm1 = new SimpleModel(1, "1");
        SimpleModel sm2 = new SimpleModel(2, "2");
        SimpleModel sm3 = new SimpleModel(3, "3");
        Observable.just(sm1, sm3, sm2)
                .toMap(new Func1<SimpleModel, Integer>() {
                    @Override
                    public Integer call(SimpleModel simpleModel) {
                        return simpleModel.getNumber();
                    }
                })
                .subscribe(new Action1<Map<Integer, SimpleModel>>() {
                    @Override
                    public void call(Map<Integer, SimpleModel> integerSimpleModelMap) {
                        printInfo("useToMap: " + integerSimpleModelMap.get(2).getNumber());
                    }
                });
    }
}
