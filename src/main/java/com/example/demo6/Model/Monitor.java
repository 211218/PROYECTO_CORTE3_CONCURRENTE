package com.example.demo6.Model;

import java.util.Observable;

public class Monitor extends Observable {

    public boolean reservacionLibre = true;
    public boolean client;
    public boolean accEntrar;
    public int numClientes;
    public String reservado;
    public int solicitarTicket;
    public int entrada;
    public int peticiones;
    public int contador=0;
    public boolean confirmacion;
    public int maxNumClientes;
    public boolean[] juegos;
    public int auxJuego;


    public int entrar(String nombre){
        // comprueba y verifica la entrada de los clientes
        int numJuego = -1;
        try {
            if(reservado.equals(nombre)){
                confirmacion = false;
                numJuego = 10;
                auxJuego = 10;
            }else{
                synchronized (this) {
                    numClientes++;
                    maxNumClientes++;
                    while (maxNumClientes==10) {
                        wait();
                    }
                    accEntrar=true;
                    client=true;
                    for (int i=0; i<10; i++) {
                        if(!juegos[i]) {
                            numJuego = i;
                            auxJuego = i;
                            juegos[i] = true;
                            i = 100;
                        }
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setChanged();
        notifyObservers("seat " + numJuego);
        return numJuego;
    }
    public void comprarTicket(){
        //Solo un hilo a la vez puede entrar a ordenar
        synchronized (this) {
            solicitarTicket++;
            notifyAll();
        }
    }

    public void darTicket(){// ordenar de manera sincronizada
        String txt = "libreVisitante";
        boolean aux = false;
        synchronized (this) {
            if (solicitarTicket<=0){
                txt = "libre";
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }else{
                aux = true;
                txt = "ocupado";
                peticiones++;
                solicitarTicket--;
            }
            notifyAll();
            setChanged();
            notifyObservers(txt +" "+ auxJuego);
        }
        if (aux){
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }



    public void darEntradas(){//notifica al  mesero  que la comida ya esta disponibles
        //Se cocina solo una vez, y esto va conforme a al pedido
        String txt = "libre";
        synchronized (this) {
            if (peticiones<=0){
                txt = "libre";
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }else{
                txt = "ocupado";
                entrada++;
                peticiones--;
            }
            notifyAll();
            setChanged();
            notifyObservers(txt);
        }
    }
    public void entrada(){// comen la comida de manera sincronizada
        synchronized (this) {
            while (entrada<=0){
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            entrada--;
        }
        try {
            Thread.sleep(11000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void salir(int numJuegoLibre){
        //Solo puede salir un cliente a la vez
        synchronized (this) {
            if(!confirmacion){
                confirmacion=true;
                reservacionLibre =true;
            }else{
                numClientes--;
                maxNumClientes--;
                client=false;
            }
            juegos[numJuegoLibre] = false;
            notifyAll();
            contador++;
            setChanged();
            notifyObservers("" + contador);
        }
    }
    public void recepcion(){
        //Solo deja entrar un cliente a la vez
        synchronized (this) {
            while(numClientes < 1 || client){
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            accEntrar=false;
            notifyAll();
        }
    }


    public Monitor(){
        client=false;
        accEntrar=false;
        numClientes=0;
        reservado ="";
        solicitarTicket=0;
        entrada=0;
        peticiones=0;
        auxJuego = -1;
        confirmacion=false;
        maxNumClientes = 0;
        juegos = new boolean[10];

        for (int i=0; i<10; i++) {
            juegos[i] = false;
        }
    }

}
