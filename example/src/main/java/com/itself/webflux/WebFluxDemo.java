package com.itself.webflux;


import lombok.SneakyThrows;
import reactor.core.publisher.Flux;

import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;

/**
 * <p>台式电脑视频和ppt文件位置:E:\docs\webFlux</p>
 * @Author xxw
 * @Date 2023/07/06
 */
public class WebFluxDemo {


}

/**
 * 基于内存级别的发布订阅模式
 * Flow。process：中间处理器，启承上启下作用，既可以当做发布者，也可以做订阅者，
 */
class FlowDemo {
    public static void main(String[] args) throws InterruptedException {
        // 1.创建一个发布者
        SubmissionPublisher publisher = new SubmissionPublisher();

        // 2.创建一个订阅者
        Flow.Subscriber<String> subscriber = new Flow.Subscriber() {
            Flow.Subscription subscription; // 订阅契约，即订阅关系

            /**
             * 订阅发布者
             * @param subscription a new subscription
             * method: subscription.request(n)  上游会根据下游的请求量发送元素。如果上游没有足够的元素可以发送，它会根据自己的策略来处理，比如等待或者缓存元素等。
             * 需要注意的是，如果下游不调用subscription.request(n)方法，上游将不会发送任何元素。因此，下游应该在onSubscribe方法中调用subscription.request(n)来请求元素。
             */
            @Override
            public void onSubscribe(Flow.Subscription subscription) {
                System.out.println("订阅者感知到发布者并和发布者绑定：订阅关系：" + subscription);
                this.subscription = subscription;
                subscription.request(1);// 找发布者要一个数据
            }

            /**
             * 订阅者接收数据 处理从上游Publisher发送的每个元素
             * @param item the item
             *
             *     //队列 queue：未接收的消息会放在队列中，消息积压多了也会压爆队列
             */
            @SneakyThrows // 此注解需要更高版本的lombok
            @Override
            public void onNext(Object item) {
                System.out.println("订阅者收到数据：" + item);
                // 业务处理。。。
                // 模拟业务处理睡眠3秒
                TimeUnit.SECONDS.sleep(3);
                // 业务处理完了再要下一个消息
                subscription.request(1);// 请求上游的一个数据，背压
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("订阅者发生异常：" + throwable);
                subscription.cancel();// 取消订阅
            }

            @Override
            public void onComplete() {
                System.out.println("订阅者数据接收完成");
            }
        };
        // 3.发布者和订阅者绑定关系
        publisher.subscribe(subscriber);

        // 4.发送测试数据
        new Thread(() -> {
            int count = 0;
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                publisher.submit("value【" + count++ + "】");
            }
        }).start();

        /*
         * 如果多个订阅者会导致其他订阅者消息阻塞吗？ 不会
         * 消息可以重复发吗？可以
         */
        // 又复制一个订阅者下来
        Flow.Subscriber<String> otherSubscriber = new Flow.Subscriber() {
            Flow.Subscription subscription; // 订阅契约

            @Override
            public void onSubscribe(Flow.Subscription subscription) {
                System.out.println("订阅者2感知到发布者并和发布者绑定：订阅关系：" + subscription);
                this.subscription = subscription;
                subscription.request(1);// 找发布者要一个数据
            }

            @SneakyThrows // 此注解需要更高版本的lombok
            @Override
            public void onNext(Object item) {
                System.out.println("订阅者2收到数据：" + item);
                // 业务处理。。。
                // 模拟业务处理睡眠3秒
                // TimeUnit.SECONDS.sleep(3);
                // 业务处理完了再要下一个消息
                subscription.request(1);// 请求上游的一个数据，背压
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("订阅者2发生异常：" + throwable);
                subscription.cancel();// 取消订阅
            }

            @Override
            public void onComplete() {
                System.out.println("订阅者2数据接收完成");
            }
        };
        // 与发布者进行绑定
        publisher.subscribe(otherSubscriber);
        Thread.currentThread().join();// 阻塞当前线程

    }
}

/**
 * Mono<T> || Flux<T>
 */
class FluxDemo {
    public static void main(String[] args) {
        LongAdder longAdder = new LongAdder();

        /*
         * 1.准备一个发布者
         *  sink:水槽
         * @return flux 异步的发布者  返回的Flux对象可以被订阅者订阅，以接收Flux序列中的元素。
         */
        Flux<Long> flux = Flux.generate(sink -> {
            System.out.println("生产数据：" + longAdder);
            sink.next(longAdder.longValue());//向水槽里添加数据
            longAdder.increment();
        });
        /*
         * 2.订阅者接收消息
         * 订阅Flux序列，并开始接收序列中的元素。
         */
        flux.subscribe(System.out::println);
    }

}
