package org.itmo.bcs;

import lombok.RequiredArgsConstructor;

import java.util.function.Supplier;

@RequiredArgsConstructor
public class ObjectSupplier implements Supplier<TestObject> {
    private final int size;

    @Override
    public TestObject get() {
        return new TestObject(size);
    }
}
