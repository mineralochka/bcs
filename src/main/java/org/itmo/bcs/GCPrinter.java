package org.itmo.bcs;

import java.lang.management.ManagementFactory;

public class GCPrinter {
    static void printGC() {
        System.out.println("Running with these garbage collectors:");
        ManagementFactory.getGarbageCollectorMXBeans().forEach(b -> {
            System.out.println(b.getName());
            System.out.println(b.getObjectName());
        });
    }
}
