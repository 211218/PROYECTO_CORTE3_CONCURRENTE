package com.example.demo6.Model;

public class Tickets implements Runnable{
    private Monitor monitor;
    public Tickets(Monitor monitor){
        this.monitor = monitor;
    }
    @Override
    public void run() {
        while(true){
            monitor.darEntradas();
        }
    }
}
