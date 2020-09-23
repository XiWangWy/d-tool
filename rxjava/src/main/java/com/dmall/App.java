package com.dmall;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Observable observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter emitter) {
                emitter.onNext("测试消息1");
                emitter.onNext("测试消息2");
                emitter.onNext("测试消息3");
            }
        });

        Observer<String> reader = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("onSubscribe:  " + d.toString());
            }

            @Override
            public void onNext(String o) {
                System.out.println("onNext: " + o);
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("onError");
            }

            @Override
            public void onComplete() {
                System.out.println("onComplete");
            }
        };

        observable.subscribe(reader);
    }
}
