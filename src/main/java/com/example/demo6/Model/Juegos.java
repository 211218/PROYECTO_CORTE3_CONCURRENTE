package com.example.demo6.Model;

import javafx.scene.layout.AnchorPane;

public class Juegos implements Runnable{
    private Monitor monitor;
    private AnchorPane anchorPane;

    public Juegos(AnchorPane anchorPane, Monitor monitor){
        this.monitor = monitor;
        this.anchorPane=anchorPane;
    }
    @Override
    public void run() {

        while (true){
            monitor.darTicket();
        }
    }
}
