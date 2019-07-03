/*....................Pour parser le fichier modéle de donnée et de creer un JtreeModel qu'on va appeler dans la classe principale........................ */
//La récursivité :
package javaapplication;

import static java.awt.SystemColor.info;
import java.io.File;
import java.io.IOException;
import javax.swing.JFrame; //
import javax.swing.JScrollPane; //
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler; //
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

/**
 *
 * @author moussa
 */
public class type2{ 
    public static void main(String[] args)
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setIgnoringComments(true);
        factory.setIgnoringElementContentWhitespace(true);
        //Les exceptions...
        try {
            factory.setValidating(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            // ErrorHandler errHandler = new SimpleErrorHandler();
            // builder.setErrorHandler(errHandler);
            File fileXML = new File("OntologieTest.xml");
            Document xml;
            try {
                xml = builder.parse(fileXML);
                Element racine = xml.getDocumentElement();
                DefaultMutableTreeNode root = new DefaultMutableTreeNode(racine.getNodeName()); //examine le parent et les enfants d'un noeud // Le noeud sans parent est la racine de l'arbre et un noeud sans enfant est une feuille
                DefaultMutableTreeNode n = null;
                createJTree(racine, root, n);//noeud sans parent // Il saute et part les fils de balise1 et les balises catégories 

                JFrame fenetre = new JFrame();
                fenetre.setLocationRelativeTo(null);
                fenetre.setSize(300, 400);
                fenetre.add(new JScrollPane(new JTree(root)));
                fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                fenetre.setVisible(true);

            }
            catch (SAXParseException e) { }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /*public void valueChanged(TreeSelectionEvent tsl)
    {
    if (tsl.getNewLeadSelectionPath() != null )
    {
    info.setViewportView(jList1)(tsl.getNewLeadSelectionPath().getLastPathComponent().toString());
    }
    }*/


    /**
     * Méthode qui va parser le contenu d'un noeud
     * @param n
     * @param tab
     * @return
     */ // Methode qui parse et qui remplit l'arbre...
    public static void createJTree(Node n, DefaultMutableTreeNode treeNode, DefaultMutableTreeNode noeuds){

        if(n instanceof Element){
            Element element = (Element)n;

            //nous contrôlons la liste des attributs présents
            if(n.getAttributes() != null && n.getAttributes().getLength() > 0){

                //Pour récupérer la liste des attributs dans une Map
                NamedNodeMap att = n.getAttributes();
                int nbAtt = att.getLength();

                //Pour parcourir tous les noeuds pour les afficher
                for(int j = 0; j < nbAtt; j++){ // Récupération attribut
                    Node noeud = att.item(j);

                    //On récupère le nom de l'attribut et sa valeur grâce à ces deux méthodes
                    DefaultMutableTreeNode attribut = new DefaultMutableTreeNode ( noeud.getNodeValue());
                    noeuds = attribut;//ajoute plante dans attribut // S'il y'avait plusieurs attribut il allez tout prendre // Les balises catégories
                }
            }

            //Pour maintenant traiter les noeuds enfants du noeud en cours de traitement
            int nbChild = n.getChildNodes().getLength();
            //Nous récupérons la liste des noeuds enfants // Les balises type
            NodeList list = n.getChildNodes();

            //Pour  parcourir la liste des noeuds
            for(int i = 0; i < nbChild; i++){ // Parcourir les fils et ajouter à leurs parent
                Node n2 = list.item(i);
                //si le noeud enfant est un Element, nous le traitons
                if (n2 instanceof Element){

                    if((n2.getTextContent() != null) && !n2.getTextContent().trim().equals("") && (n2.getChildNodes().getLength() == 1)) // recupére les valuers entres types
                    {
                        DefaultMutableTreeNode value = new DefaultMutableTreeNode(n2.getTextContent());
                        noeuds.add(value);// ajouté dans noeuds
                    }
                    //appel récursif à la méthode pour le traitement du noeud et de ses enfants
                    createJTree(n2, treeNode, noeuds);// n2 c la balise type, treenode=la racine balise1, je lui passe la varibale noeuds parent de n2 (nom latin ici)

                    if(noeuds != null)
                        treeNode.add(noeuds); // Ajout des noeuds
                }
            }
            if(noeuds != null)
                 treeNode.add(noeuds); // Ajout des noeuds
        }
    }
}




