package javaapplication;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class JavaApplication {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            // TODO code application logic here
            Accueil acc = new Accueil();
            acc.setVisible(true);
            //
            //accueil A = new accueil();
            // A.remplirArbre();
        } catch (IOException ex) {
            Logger.getLogger(JavaApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
        }

        
}