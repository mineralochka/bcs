package org.itmo.bcs;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@RequiredArgsConstructor
public class Allocator implements Runnable {
    private final ObjectSupplier objectSupplier;
    private final ObjectStore objectStore;

    private final Random random = ThreadLocalRandom.current();
    private boolean heatUp = true;
    @Getter
    private long counter = 0;

    @SneakyThrows
    @Override
    public void run() {
        while (heatUp) {
            doAllocate();
        }
        final long start = System.currentTimeMillis();
        while (System.currentTimeMillis() - start < 60 * 1000) {
            doAllocate();
            counter++;
            if (counter < 0) {
                System.err.println("Negative counter!!");
                return;
            }
        }
    }

    private void doAllocate() {
        objectStore.put(random.nextInt(), objectSupplier.get());
    }

    public void start() {
        heatUp = false;
    }
}
