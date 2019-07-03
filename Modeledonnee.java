
package javaapplication;

import java.io.*;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import java.util.List;
import java.util.Iterator;

public class Modeledonnee {
     public static void main(String[] args) {
        File xmlFile = new File("OntologieTest.xml");
        Document document = null;
        Element rootNode = null;

        //On crée une instance de SAXBuilder // le parseur XML
        SAXBuilder builder = new SAXBuilder();
        try {
            //On crée un nouveau document JDOM avec en argument le fichier XML  
            document = builder.build(xmlFile);
            //On initialise un nouvel élément racine avec l'élément racine du document.
            rootNode = document.getRootElement();
        }
        catch (Exception e) {
            // do something
            e.printStackTrace();
        }
          System.out.println(rootNode);
     
        //On crée une List contenant tous les noeuds "categorie" de l'Element racine
        List listCategorie = rootNode.getChildren("categorie");
        System.out.println(listCategorie.size());
        //On crée un Iterator sur notre liste
        Iterator i = listCategorie.iterator();
        while (i.hasNext()){
            //On recrée l'Element courant à chaque tour de boucle afin de pouvoir utiliser les méthodes propres aux Element comme //selectionner un noeud fils, modifier du texte, etc.
            Element courant = (Element) i.next();
            //System.out.println(courant.getText());
            System.out.println("\n"+courant.getAttribute("info").getValue());

            List<Element> listType = courant.getChildren("type");
            for(Element e : listType){
                System.out.println(e.getText());
            }
            
            //System.out.println(courant.getChild("type").getText());
        }
        // {

        //On affiche le type d'information de l'élément courant
        // System.out.println(courant.getChild("type d'information").getText());
        // afficheALL(rootNode);
    }


    static void afficheALL(Element rootNode) {

    }
}
    

