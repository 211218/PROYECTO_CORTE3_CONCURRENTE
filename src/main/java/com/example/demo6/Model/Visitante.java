package com.example.demo6.Model;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class Visitante implements Runnable{
    private AnchorPane anchor;
    private Monitor monitor;
    private static String[] positions;

    @FXML
    private ImageView table3; // Asegúrate de que este ImageView está definido en tu archivo FXML

    public Visitante(AnchorPane anchor, Monitor monitor){
        this.anchor = anchor;
        this.monitor = monitor;
        positions = new String[11];
        positions[0] = "152 38";  // ruedas
        positions[1] = "216 159"; // casa
        positions[2] = "373 159"; // girar
        positions[3] = "462 182"; // big
        positions[4] = "853 398"; // barco
        positions[5] = "119 280"; // caballos_girar
        positions[6] = "7 410";   // fast
        positions[7] = "293 393"; // caballos
        positions[8] = "470 429"; // caballos2
        positions[9] = "680 393"; // caballos3
        positions[10] = "617 586";


    }
    @Override
    public void run() {
        Image image = new Image(getClass().getResourceAsStream("/com/example/demo6/cliente.png"));
        ImageView cliente = new ImageView(image);
        cliente.setFitWidth(150); // Ajusta el tamaño de la imagen según sea necesario
        cliente.setFitHeight(120); // Ajusta el tamaño de la imagen según sea necesario
        Platform.runLater(() -> {
            cliente.setLayoutX(519);
            cliente.setLayoutY(1133);
            anchor.getChildren().add(cliente);
        });
        //Movimiento de los clientes
        for(int i=0;i<10;i++){
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Platform.runLater(()-> cliente.setLayoutY(cliente.getLayoutY()-50));
        }

        //Posicionar a los clientes en los juegos con forme el id
        int numJuego = monitor.entrar(Thread.currentThread().getName());
        String[] layout = positions[numJuego].split(" ");
        Platform.runLater(()-> {
            cliente.setLayoutX(Integer.parseInt(layout[0]));
            cliente.setLayoutY(Integer.parseInt(layout[1])+50);
        });
        System.out.println("Numero de Juego " + numJuego + " En uso");

        try {
            Thread.sleep(800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Ordenar
        monitor.comprarTicket();

        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //entrar
        monitor.entrada();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();}

        //Salir
        Platform.runLater(() -> {
            cliente.setLayoutX(519);
            cliente.setLayoutY(1133);
        });
        monitor.salir(numJuego);
    }
}
