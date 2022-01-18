package webflux;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class SubscribeAndPublish {
    public static void main(String[] args) {
        Flux.range(0, 1)
                .map(i -> {
                        // myElastic-x
                        System.out.println(String.format("First map - (%s), Thread: %s", i, Thread.currentThread().getName()));
                        return i;
                    }
                )
                .publishOn(Schedulers.newParallel("myParallel"))
                .map(i -> {
                        // myParallel-x
                        System.out.println(String.format("First map - (%s), Thread: %s", i, Thread.currentThread().getName()));
                        return i;
                    }
                )
                .subscribeOn(Schedulers.newElastic("myElastic"))
                .map(i -> {
                        // myParallel-x
                        System.out.println(String.format("First map - (%s), Thread: %s", i, Thread.currentThread().getName()));
                        return i;
                    }
                )
                .blockLast();
    }
}
