package org.itmo.bcs;

import picocli.CommandLine;
import picocli.CommandLine.ParseResult;

import java.io.IOException;
import java.lang.management.ManagementFactory;

import static picocli.CommandLine.printHelpIfRequested;

public class GCTesterApp {
    public static void main(String[] args) {
        try {
            final CliOptions cliOptions = new CliOptions();
            final ParseResult parseResult = new CommandLine(cliOptions).parseArgs(args);
            if (!printHelpIfRequested(parseResult)) {
                System.out.println("Running with these garbage collectors:");
                ManagementFactory.getGarbageCollectorMXBeans().forEach(b -> {
                    System.out.println(b.getName());
                    System.out.println(b.getObjectName());
                });
                System.out.println("Will allocate with the following options: " + cliOptions);
                final ObjectSupplier supplier = new ObjectSupplier(cliOptions.getSize());
                final ObjectStore store = new ObjectStore(cliOptions.getTtl());
                final Allocator allocator = new Allocator(supplier, store, cliOptions.getInterval());
                final Thread thread = new Thread(allocator);
                thread.start();
                //noinspection ResultOfMethodCallIgnored
                System.in.read();
                allocator.stop();
                thread.join();
            }
        } catch (CommandLine.ParameterException e) {
            System.err.println(e.getMessage());
            e.getCommandLine().usage(System.err);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
