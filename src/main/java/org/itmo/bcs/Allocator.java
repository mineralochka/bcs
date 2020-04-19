package org.itmo.bcs;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@RequiredArgsConstructor
public class Allocator implements Runnable {
    private final ObjectSupplier objectSupplier;
    private final ObjectStore objectStore;
    private final Integer interval;
    private final Random random = ThreadLocalRandom.current();
    private boolean work = true;

    @SneakyThrows
    @Override
    public void run() {
        while (work) {
            objectStore.put(random.nextInt(), objectSupplier.get());
            Thread.sleep(interval);
        }
    }

    public void stop() {
        work = false;
    }
}
