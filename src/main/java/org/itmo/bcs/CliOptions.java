package org.itmo.bcs;

import lombok.Data;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Data
@Command(mixinStandardHelpOptions = true, header = "Allocate objects according to strategy")
public class CliOptions {
    @Option(names = {"-i", "--interval"}, defaultValue = "0", description = "Interval between objects creation, millis")
    private Integer interval;
    @Option(names = {"-s", "--size"}, defaultValue = "1", description = "underlying long[] array size to allocate")
    private Integer size;
    @Option(names = {"-t", "--ttl"}, defaultValue = "1", description = "Retention policy - number of allocation cycles objects survive")
    private Integer ttl;
}
