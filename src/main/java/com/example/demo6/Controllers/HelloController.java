package com.example.demo6.Controllers;

import com.example.demo6.Model.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;


import java.util.Objects;
import java.util.Observable;
import java.util.Observer;


public class HelloController implements Observer {

    @FXML
    private AnchorPane anchor;

    @FXML
    private Button btnIniciar;

    @FXML
    private ImageView meseroImageView; // Asegúrate de que este ImageView está definido en tu archivo FXML




    @FXML
    void Finalizar(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    void IniciarAnimacion(ActionEvent event) {
        // Carga la imagen inicial del visitante
        Image initialImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/demo6/cliente.png")));
        meseroImageView.setImage(initialImage);
        btnIniciar.setDisable(true);
        //sse agrega al observador los cambios los hace en los juegos
        Monitor monitor = new Monitor();
        monitor.addObserver(this::update);

        Juegos juegos = new Juegos(anchor, monitor);
        Thread hiloJuegos = new Thread(juegos);
        hiloJuegos.start();


        Recepcionista recepcionista=new Recepcionista(monitor);
        Thread hiloRecepcionista = new Thread(recepcionista);
        hiloRecepcionista.start();

        Tickets cliente = new Tickets(monitor);
        Thread tCliente = new Thread(cliente);
        tCliente.start();

        HilosCreados hilosCreados = new HilosCreados(anchor, monitor, this);
        Thread hCreadorClientes = new Thread(hilosCreados);
        hCreadorClientes.start();
    }

    @Override
    public void update(Observable o, Object arg) {
        synchronized (this) {
            if (((String) arg).contains("ocupado")){
                Image busyImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/demo6/cliente.png")));
                meseroImageView.setImage(busyImage);
            } else if (((String) arg).contains("libre")){
                Image freeImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/demo6/cliente.png")));
                meseroImageView.setImage(freeImage);
            }
        }
    }
}
