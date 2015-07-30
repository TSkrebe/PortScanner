package com.scanner;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;import java.lang.String;import java.lang.System;
import java.lang.reflect.Constructor;
import java.net.Socket;


/** IPv4
 * -i for defining ip address
 * -p for defining port
 * -p2 for defining range default same as p
 * -t number of threads to use for connections   default 10
 * -o timeOut limit in millis. default 1000ms range 0 - 10000
 */  
 //dsklfjlskdfj
public class Main {

    @Option(name = "-i", usage = "host", required = true)
    private String ARG_IP;
    @Option(name = "-p", usage = "port1", required = true)
    private int ARG_PORT;
    @Option(name = "-p2", usage = "port2")
    private int ARG_PORT2 = -1;
    @Option(name = "-t", usage = "threads")
    private int ARG_THREADS = 50;
    @Option(name = "-o", usage = "timeout")
    private int ARG_TIMEOUT = 1000;
    //dsfsdf


    private static final int MIN_PORT = 0;
    private static final int MAX_PORT = 65535;

    private static final int MIN_THREADS = 1;

    private static final int MIN_TIMEOUT = 0;


    public static void main(String[] args) {
        new Main().doMain(args);
    }

    private void doMain(String[] args) {

        CmdLineParser parser = new CmdLineParser(this);
        try {
            parser.parseArgument(args);
            if (ARG_PORT < MIN_PORT || ARG_PORT > MAX_PORT)
                throw new CmdLineException(parser, "Port has to be between " + MIN_PORT + " and " + MAX_PORT);
            if (ARG_PORT2 != -1 && (ARG_PORT2 < MIN_PORT || ARG_PORT2 > MAX_PORT || ARG_PORT2 < ARG_PORT))
                throw new CmdLineException("Port has to be between " + ARG_PORT + " and " + MAX_PORT);
            if (ARG_THREADS < MIN_THREADS)
                throw new CmdLineException("Number of threads has to be bigger than 0");
            if (ARG_TIMEOUT < MIN_TIMEOUT)
                throw new CmdLineException("Time out has to be bigger than -1");

        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            System.err.println("java SampleMain [options...] arguments...");
            // print the list of available options
            parser.printUsage(System.err);
            System.err.println();
            return;
        }

        if (ARG_PORT2 == -1)
            ARG_PORT2 = ARG_PORT;

        System.out.println("Estimated time left: " + ((ARG_PORT2-ARG_PORT+1)/ARG_THREADS)*(ARG_TIMEOUT/1000) + " sec.");

        //if everything is ok run through ports
        CheckPorts checkPorts = new CheckPorts(ARG_IP, ARG_PORT, ARG_PORT2, ARG_THREADS, ARG_TIMEOUT);
        checkPorts.run();
    }


}
