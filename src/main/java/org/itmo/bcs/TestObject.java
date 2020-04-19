package org.itmo.bcs;

public class TestObject {
    private final long[] a;

    public TestObject(int n) {
        this.a = new long[n];
    }

    @Override
    public String toString() {
        return "TestObject{" +
                "array size=" + a.length +
                '}';
    }
}
