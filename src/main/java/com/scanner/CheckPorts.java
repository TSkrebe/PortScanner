package com.scanner;

import java.io.IOException;
import java.lang.Integer;
import java.lang.InterruptedException;
import java.lang.String;
import java.lang.System;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CheckPorts {

    private final String ip;
    private final int port1;
    private final int port2;
    private final int threads;
    private final int timeout;
    
    public CheckPorts(String ip, int port1, int port2, int threads, int timeout) {
        this.ip = ip;
        this.port1 = port1;
        this.port2 = port2;
        this.threads = threads;
        this.timeout = timeout;
    }


    public void run() {
        ExecutorService executorService = Executors.newFixedThreadPool(threads);
        List<Callable<Integer>> list = new ArrayList<>();

        for (int i = port1; i <= port2; i++) {
            final int port = i;
            list.add(() -> {
                try (Socket socket = new Socket()){
                    socket.connect(new InetSocketAddress(ip, port), timeout);
                    return port;
                } catch (IOException e) {
                    return -1;
                }
            });
        }

        int all = 0;
        try {
            List<Future<Integer> >  futures = executorService.invokeAll(list);
            executorService.shutdown();
            for (Future<Integer> f : futures){
                int p = f.get();
                if (p != -1) {
                    all++;
                    System.out.println(p);
                }

            }
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("Ooops!!! An error occurred while ");
        }

        System.out.println(all + " open port(s) in total.");
    }

}
