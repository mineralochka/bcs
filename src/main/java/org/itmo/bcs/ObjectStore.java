package org.itmo.bcs;

import lombok.RequiredArgsConstructor;

import java.util.LinkedHashMap;
import java.util.Map;

@RequiredArgsConstructor
public class ObjectStore extends LinkedHashMap<Integer, TestObject> {
    private final int allowedSize;

    @Override
    protected boolean removeEldestEntry(Map.Entry<Integer, TestObject> eldest) {
        return size() > allowedSize;
    }
}
