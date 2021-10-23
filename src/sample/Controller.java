package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

/**
 * La classe controller es on trobem tota la logica de l'aplicació
 */

public class Controller implements Observer {
    //Variables globals enre elles l'array Threads que es on enmagatzenarem els fils d'execució
    //Al guardarlos d'aquesta forma el que podem fer es pararlos de cop al moment que un arribi al final de la cursa
    private Thread[] motos;
    public static boolean encontrado;
    public static int discircuit =1;
    public static int porce1;
    public static int porce2;
    public static int porce3;
    public static int porce4;

    //Importació dels elements de l'arxiu de disseny fmxl
    @FXML
    private ProgressBar pg1;
    @FXML
    private ProgressBar pg2;
    @FXML
    private ProgressBar pg3;
    @FXML
    private ProgressBar pg4;
    @FXML
    private Button buttonStart;
    @FXML
    private TextField velocitatm1;
    @FXML
    private TextField velocitatm2;
    @FXML
    private TextField velocitatm3;
    @FXML
    private TextField velocitatm4;
    @FXML
    private TextField distanciacircuit;
    @FXML
    private TextField proge1,progre2,proge3,progre4;
    @FXML
    private TextField dis1,dis2,dis3,dis4,guanyador;



    //Creem les 4 posicions en l'array de Threads per els 4 fils
    public Controller(){
        motos = new Thread[4];
    }

    //Funció que executa el programa, quan l'usuari dona al boto "Start"
    public void startAction() throws Exception {
        try{
            //Boloqueixem els camps i el boto "Start"
            visivilidad(1);
            //En cas de que el camp "guanyador" estigui omplert el borrem
            guanyador.clear();
            //variable per saber si ningun fil ha acabat
            encontrado = true;
            //Neteixem l'array per si estigues omplert
            for (int i = 0; i<motos.length;i++){
                motos[i] = null;
            }
            //Obtenim la distancia del circuit
            discircuit = Integer.parseInt(distanciacircuit.getText());

            //Asignacio de velocitats per a cada objecte
            int vel1 = 0;
            int vel2 = 0;
            int vel3 = 0;
            int vel4 = 0;

            //Mirem si el camp de velocitat de cada moto esta omplert
            //Si esta omplet agafem el valor i el transformem en int
            //Si el camp esta buit a velocitat li asignem 0
            if (velocitatm1.getText().trim().isEmpty()){
                System.out.println("moto 1 esta buit");
                vel1 = 0;
            }else {
                vel1 = Integer.parseInt(velocitatm1.getText());
            }
            if (velocitatm2.getText().trim().isEmpty()){
                System.out.println("moto 2 esta buit");
                vel2 = 0;
            }else {
                vel2 = Integer.parseInt(velocitatm2.getText());
            }
            if (velocitatm3.getText().trim().isEmpty()){
                System.out.println("moto 3 esta buit");
                vel3 = 0;
            }else {
                vel3 = Integer.parseInt(velocitatm3.getText());
            }
            if (velocitatm4.getText().trim().isEmpty()){
                System.out.println("moto 4 esta buit");
                vel4 = 0;
            }else {
                vel4 = Integer.parseInt(velocitatm4.getText());
            }
            //metode amb el cual creem els objectes moto i els iniciem
            for (int i = 0; i<motos.length;i++){
                Moto m ;
                switch (i){
                    case 0:
                        m = new Moto(vel1,(i+1)+"");
                        break;
                    case 1:
                        m = new Moto(vel2,(i+1)+"");
                        break;
                    case 2:
                        m = new Moto(vel3,(i+1)+"");
                        break;
                    case 3:
                        m = new Moto(vel4,(i+1)+"");
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + i);
                }
                //li posem un observer per poder notoficar canvis
                m.addObserver(this);
                //Creem el fil
                motos[i] = new Thread(m);
                //Iniciem el fil
                motos[i].start();

            }

        //En cas que algun camp no compleixi les condicions, informem mitjançant un alert
        }catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Alguna cosa ha fallat...");
            alert.setHeaderText(null);
            alert.setContentText("Sempre s'ha d'informar la distància del circuit.\n Les velocitats han de ser nombres enters positius");
            alert.showAndWait();
            //Desbloquejem els camps i el boto
            visivilidad(2);
        }

    }
    //Funció amb la que bloquejem o desboloquejem els camps i el boto
    public void visivilidad(int opc){
        boolean mostrar = true;

        switch (opc){
            case 1:
                mostrar = true;

                break;
            case 2:
                mostrar = false;
                break;

        }
        velocitatm1.setDisable(mostrar);
        velocitatm2.setDisable(mostrar);
        velocitatm3.setDisable(mostrar);
        velocitatm4.setDisable(mostrar);
        buttonStart.setDisable(mostrar);
        distanciacircuit.setDisable(mostrar);

    }

    //Funció amb la que parem els fils
    public void meta(){
        encontrado = false;
        System.out.println("entra funcion parar");
        visivilidad(2);
        //Parem cada fil
        for (int i = 0; i<motos.length;i++){
            System.out.println(i);
            motos[i].interrupt();
            motos[i].stop();
            System.out.println("paro: "+(i+1)+"");
        }
        System.out.println("salgo");
        buttonStart.setDisable(false);

    }


    //Funcio amb la que controlarem les progress bar
    //D'aquesta forma cada cop que un objecte canvi la distancia i ens ho notifiqui ho sambrem
    @Override
    public void update(Observable o, Object arg) {
        //Obtenim objecte i percentatge
        Moto m = (Moto) o;
        int porcentaje = (int) arg;
        //Calculem percentatges i distancies recorregudes
        double proce2 = (double) porcentaje/100;
        double reco = (double) (discircuit/100)*porcentaje;
        String recoreegut = String.valueOf(reco);
        String porcenta = String.valueOf(porcentaje);
        //Mostrem per consola es percentatges
        System.out.println(porcentaje);
        System.out.println(proce2);
        //Protecció perque ningun fil es pugui colar
        if(encontrado){
            //A partir del fil actualitzem progress bar
            switch (m.getNombre()){
                case "1":
                    this.pg1.setProgress(proce2);
                    proge1.setText(porcenta);
                    porce1 = porcentaje;
                    dis1.setText(recoreegut);
                    System.out.println("entra pb 1");
                    break;
                case "2":
                    this.pg2.setProgress(proce2);
                    progre2.setText(porcenta);
                    porce2 = porcentaje;
                    dis2.setText(recoreegut);
                    System.out.println("entra pb 2");
                    break;
                case "3":
                    this.pg3.setProgress(proce2);
                    proge3.setText(porcenta);
                    porce3 = porcentaje;
                    dis3.setText(recoreegut);
                    System.out.println("entra pb 3");
                    break;
                case "4":
                    this.pg4.setProgress(proce2);
                    progre4.setText(porcenta);
                    porce4 = porcentaje;
                    dis4.setText(recoreegut);
                    System.out.println("entra pb 4");
                    break;
            }
            //Si alguna moto obto un percentatge de 100% significa que ha arribat a la meta
            if (porcentaje >= 100){
                //Notifiquem el guanyador
                guanyador.setText("Moto "+((Moto) o).getNombre());
                //cridem a la funció per parar el fils
                meta();
                //La cursa s'ha acabat
                encontrado = false;
            }

        }else {
            //cridem a la funció per parar el fils
            meta();
        }

    }


}
