package sample;

import java.util.Observable;

/**
 * Classe moto que representa els corredor del grand prix
 * Amb aquesta classe es crearan els objectes dels fils
 */
public class Moto extends Observable implements Runnable {

    private String nombre;
    private int velocitat;

    public Moto(int velocitat ,String nombre) {
        this.velocitat = velocitat;
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    //Funció per calcular la distancia recorreguda , el percentatge i notificar
    @Override
    public synchronized void  run(){
        int distanciarecoreguda = 0;
        int procentatge = 1;
        int temps = 0;
        //Mirem que no hagi completat el circuit (100% circuit completat)
        while(procentatge < 100){
            try {
                //Calculem la distancia recorreguda
                distanciarecoreguda = distanciarecoreguda + velocitat*temps;
                temps = temps +1;
                //notifiquem distancia
                System.out.println("La moto "+nombre+" ha recorrecgut " +distanciarecoreguda+" KM");
                //calculem percentatge d'avanç
                procentatge = (distanciarecoreguda*100)/Controller.discircuit;
                //Notifiquem del canvi a l'update
                this.setChanged();
                this.notifyObservers(procentatge);
                this.clearChanged();
                //Posem un sleep perque el proces sigui mes lent i es pugui visualitzar amb calma
                Thread.sleep(2500);
            } catch (InterruptedException e) {
                //Si el fil es para notifiquem
                System.out.println("Fil parat");

            }
        }


    }

}
