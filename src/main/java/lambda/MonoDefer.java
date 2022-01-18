package lambda;

import reactor.core.publisher.Mono;

public class MonoDefer {
    /**
     * 概念上：reactor创建source有两种方式：懒汉和饿汉
     * Mono.defer()是一种懒汉
     * Mono.just()或Flux.fromIterable()是饿汉
     */
    public static void main(String[] args) throws InterruptedException {
        // 会立刻调用求出当前时间
        Mono<Long> eager = Mono.just(System.currentTimeMillis());
        // 当subscribe时才会求出当前时间
        Mono<Long> lazy = Mono.defer(() -> Mono.just(System.currentTimeMillis()));

        eager.subscribe(System.out::println);
        lazy.subscribe(System.out::println);

        Thread.sleep(5000);

        // 再次调用时不会求时间了
        eager.subscribe(System.out::println);
        // 再次调用会重新再求一次时间
        lazy.subscribe(System.out::println);
    }
}
