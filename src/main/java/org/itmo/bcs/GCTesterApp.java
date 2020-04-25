package org.itmo.bcs;

import picocli.CommandLine;
import picocli.CommandLine.ParseResult;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static picocli.CommandLine.printHelpIfRequested;

public class GCTesterApp {
    public static void main(String[] args) {
        ManagementFactory.getGarbageCollectorMXBeans().forEach(b -> {
            System.out.println(b.getName());
            System.out.println(b.getObjectName());
        });
        try {
            final CliOptions cliOptions = new CliOptions();
            final ParseResult parseResult = new CommandLine(cliOptions).parseArgs(args);
            if (!printHelpIfRequested(parseResult)) {
                System.out.println("Running with these garbage collectors:");
                System.out.println("Will allocate with the following options: " + cliOptions);
                Supplier<Allocator> allocatorSupplier = () -> new Allocator(
                        new ObjectSupplier(cliOptions.getSize()),
                        new ObjectStore(cliOptions.getTtl())
                );
                final List<Allocator> allocators = Stream.generate(allocatorSupplier)
                        .limit(cliOptions.getNumThreads()).collect(toList());
                final List<Thread> threads = allocators.stream().map(Thread::new).peek(Thread::start).collect(toList());
                //noinspection ResultOfMethodCallIgnored
                System.in.read();
                System.out.println("Starting allocation");
                allocators.forEach(Allocator::start);
                for (Thread thread : threads) {
                    thread.join();
                }
                final double average = allocators.stream()
                        .map(Allocator::getCounter)
                        .mapToLong(Long::longValue)
                        .average().orElse(0.0);
                System.out.println(String.format("Allocated: %f objects", average));
            }
        } catch (CommandLine.ParameterException e) {
            System.err.println(e.getMessage());
            e.getCommandLine().usage(System.err);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
