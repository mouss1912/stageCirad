/**
 * La fénétre principale....
 * \\ Pour Indenter cmd+A et cmd+
 * les listeners permettent au programmeur de réagir suite aux actions de l'utilisateur (clic de souris, touche du clavier enfoncée, etc.).
 *
 *
 * si on veut une liste qui peut être mise à jour par programme, il faut utiliser un ListModel.
 *
 *
 */

package javaapplication;

import java.awt.event.ActionEvent;
import javax.swing.JTable;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javaapplication.type2.createJTree; // import du  Modele de creation de Jtree
//import javaapplication.monListeModel.ajoutValeur; // import du  Modele de creation de Jtree
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 *
 * @author
 */
public class Accueil extends javax.swing.JFrame
{
    private FichierDonneeTest f;
    private monListeModele valeurs; // ListeModéleZone de Sélection
    private monListePanier monPanier; // Liste Panier
    // private ListeAffichageResultats maListeDeResultats; // Liste d'affichage des resultats
    // private ArrayList<ArrayList<String>> resultats;// resultats recuperer
    private ArrayList<ArrayList<String>> resultatsTemporaires; // resultats Temporels
    private ArrayList<ArrayList<String>> resultatsDefinitif; // resulats definitifs
    String elem;
    private List<String> ligneSupprimee;
    
     NameFile nf = new NameFile();
    //Fonction qui positionne et modifie les caractéristiques des composants dans la fenetre//
    public Accueil() throws IOException
    {
        f = new FichierDonneeTest();
        valeurs = new monListeModele();
        monPanier = new monListePanier();
        //   maListeDeResultats = new ListeAffichageResultats();
        //resultats = new ArrayList<>();
        resultatsTemporaires = new ArrayList<>();
        resultatsDefinitif = new ArrayList<>();
        ligneSupprimee = new ArrayList<>();
        initComponents();
        recuperenomDufichierxml();
    }
    
    //===================================================================================================================//
    public void recuperenomDufichierxml()//Méthode à faire....
    {
        
    }
    //==================================================================================================================//
    private void monArbre(String nameFile) // Methode de construcution de l'arbre // Qui prend en argument le fichier Ontologie.xml
    {
        //nameFile=" ";
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setIgnoringComments(true);
        factory.setIgnoringElementContentWhitespace(true);
        //Les exceptions...
        try {
            factory.setValidating(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            
            File fileXML = new File(nameFile);
            Document xml;
            try {
                xml = builder.parse(fileXML);
                Element racine = xml.getDocumentElement();
                DefaultMutableTreeNode root = new DefaultMutableTreeNode(racine.getNodeName()); //examine le parent et les enfants d'un noeud // Le noeud sans parent est la racine de l'arbre et un noeud sans enfant est une feuille
                Node n = racine.cloneNode(false);
                createJTree(racine, root, null);//noeud sans parent
                
                DefaultTreeModel arbreTemp = new DefaultTreeModel(root); // Caster le modéle sur l'abre généré
                arbre.setModel(arbreTemp);
                // jScrollPane1.setViewportView(arbre);
                
                arbre.setRootVisible(false); // Méthode pour ne pas afficher la racine
                
            }catch (SAXParseException e) { }
        } catch (ParserConfigurationException | SAXException | IOException e)
        {
            e.printStackTrace();
        }
        
    }
    
    
    String prev_infos = null;
    ///////////////////////////////////////////////////////////////////////////////
    private ArrayList<ArrayList<String>> mesResutats() // Methode pour extraire les resultats dans une liste
    {
        ArrayList<ArrayList<String>> contenuCSV = f.getContenuCSV();
        //  System.out.println("afficheContenuCSV");
        f.afficheContenuCSV(); // Apelle de la fonction afficheContenuCSV
        
        //Attention variable local et non la variable global de la classe
        ArrayList<ArrayList<String>> resultats = new ArrayList<>();
        if(contenuCSV != null && !contenuCSV.isEmpty())
        {
            //   System.out.println("Je suis dans le bon csv");
            //   System.out.println("c'est ok "+elem+" "+contenuCSV.size());
            
            // listeS = new ArrayList<String>();
            
            for (ArrayList<String> listeS : contenuCSV) // listeS = liste des enTétes
            {
                Set<String> listcles = monPanier.getPanier().keySet();
                if(!listeS.isEmpty())
                {
                    int i=0;
                    String enTete = "";
                    int indexET = 0;
                    enTete = listeS.get(indexET); // recupére le contenu de la cellule à là ligne 4 du fichierCSV
                    //  System.out.println("entete : " + enTete + " "+ listeS.size());
                    
                    // Liste clés
                    // int indiceligne = listeS.get(indice);
                    for (String clesAjoute : listcles) //
                    {
                        if(clesAjoute.equals(enTete))
                        {
                            ArrayList<String> listPanier = (ArrayList<String>)monPanier.getPanier().get(enTete);
                            //   System.out.println("èèèèèèèè "+enTete+" "+listPanier+" bool "+enTete.equals("Nom latin plante"));
                            //  System.out.println("Panier bien identitifié");
                            ///////////
                            int e = 0; // numéro de la ligne
                            for(String s : listeS){
                                while( s.equals(elem) ){//hasmap ( valeurs) = la cellule
                                    //  System.out.println("Bonne cellule "+elem);
                                    
                                    List<String> listeRecuperer = new ArrayList<>();// Ajout des cellules récuperer dans une liste
                                    //    System.out.println("---------liste de la cellule recuperee----------");
                                    for(int indiceKey = 0; indiceKey<=contenuCSV.size(); indiceKey++){//Parcours des clés du panier ,c 'est donc des lignes du csv
                                        if (indiceKey<65)
                                        {
                                            //System.out.println("indice "+indiceKey+" "+contenuCSV.size());
                                            // if(!contenuCSV.get(indiceKey).isEmpty()){
                                            //System.out.println("Construction de la liste");
                                            // Ne pas fusionner les deux lignes
                                            
                                            //System.out.println(" Ajout des contenu des autres cellues "+contenuCSV.get(indiceKey).size()+" num colonne "+indiceKey+" num ligne "+e);
                                            
                                            if( e < contenuCSV.get(indiceKey).size()){
                                                //  System.out.println("valeur ajouter à la ligne résultat : " + contenuCSV.get(indiceKey).get(e));
                                                listeRecuperer.add(contenuCSV.get(indiceKey).get(e));
                                                
                                            }
                                            
                                        }
                                        
                                    }
                                    
                                    String ligne = "ligne qui va être ajoutée à la liste résultat : ";
                                    for (String cell: listeRecuperer){
                                        ligne += cell + "  ";
                                    }
                                    
                                    if(prev_infos == null){
                                        prev_infos = infos;
                                    }
                                    //   System.out.println(ligne);
                                    System.out.println(ligne);
                                    //mesSuppression(elem);
                                    System.out.println("info : "+infos+" prev info : "+prev_infos+(!prev_infos.equals(infos)));
                                    if(prev_infos!= null && !prev_infos.equals(infos) ){
                                        ArrayList<ArrayList<String>> listeasupprimer = new ArrayList<>();
                                        System.out.println("elem "+elem);
                                        for(ArrayList<String> lp : resultatsDefinitif){
                                            if( !lp.contains(elem) ){
                                                System.out.println("----------------- contain "+lp);
                                                listeasupprimer.add(lp);
                                            }
                                        }
                                        //System.out.println("listeasupprimer "+listeasupprimer);
                                        resultatsDefinitif.removeAll(listeasupprimer);
                                        prev_infos = infos;
                                    }

                                   

                                    resultats.add((ArrayList<String>) listeRecuperer);

                                   // resultats.add((ArrayList<String>) listeRecuperer);
                                    // resultatsTemporels.add((ArrayList<String>) listeRecuperer); //Ajouter chaque liste dans resultatsTemporels
                                    break;
                                }
                                e++;
                            }
                        }
                    }
                    /*  for (String contenuS : listeS) // C'est des STring
                    {
                    System.out.println("i = "+ i);
                    System.out.println(listeS.get(i));
                    i++; // i = i+1;
                    }*/
                }
            }
            
            System.out.println("Liste résultat\n---------");
            
            for(ArrayList<String> l : resultats)
            {
                System.out.println(l);
            }
            System.out.println("---------\nfin liste résultat");
        }
        
        
        return resultats;
    }
    
    /* Exemple de boucle for
    /* Exemple de boucle for
    for(type variable : tableau | collection){
    .
    .
    }
    */
    
    private void mesResultatsDefinitifs()
    {
        //resultatsTemporels = resultats; // liste Resultats temporaires // Stocker chaque resultats dans resultatsTemporaire
        // liste Resultats definitifs
        for(ArrayList<String> resTemp : resultatsTemporaires)
        {  if (!resultatsDefinitif.contains(resTemp)) // Si ma liste de resulat contient la ligne je l'ajoute pas 
        {
            resultatsDefinitif.add(resTemp);
        }
        
        //System.out.println(resultatsDefinitif);
        // return resultatsDefinitif;
        
        }
    
    }
    
//.............................Methode pour Supprimer : pour supprimer dans le panier et dans les résultats.................................//
    /*Pour chaque double clique sur un element dans panier: Je parcours ma liste de resultatsDefinitif  (ligne par ligne) et je cherche
    la ligne de resultat qui contient l'element cliquer sur panier (info et valeur) (Je supose que ça doit etre la bonne ligne à vérifier
    je la suprime dans mes resultats definitif (et je remets à jour automatiquement ma liste de resultat)*/
    private void mesSuppression(String suppr)
    { // On supprime une lise de String (la ligne resultat)
        
        ///////////////////////////////////////////////////////////////////////////////////////////////////
        // ArrayList<String> listeSuprimee= new ArrayList<>();
        int l = 0; // numéro de la ligne dans resultatDefinitif
        List<ArrayList<String>> ligneSupprimee = new ArrayList<>(); 
        for (ArrayList<String> suprim : resultatsDefinitif) { // Paramétre donner à là fonction de suppression
            // System.out.println("Now : "+suprim);
            // System.out.println("Elem : "+elem);
            if(suprim.contains(suppr)){
                ligneSupprimee.add(suprim);
            }
        } //Pour chaque String suprim dans resultatsDefinitif
        //System.out.println("Avant : "+resultatsDefinitif);
        resultatsDefinitif.removeAll(ligneSupprimee);
        //System.out.println("Apres : "+resultatsDefinitif);
    }
    
    //..................Méthode pour écrire mes résultats dans un fichier CSV................
    // Allows to define custom separator
    public void writeToCsvFile(List<String[]> thingsToWrite, String separator, String fileName){
        try (FileWriter writer = new FileWriter(fileName)){
            for (String[] strings : thingsToWrite) {
                for (int i = 0; i < strings.length; i++) {
                    writer.append(strings[i]);
                    if(i < (strings.length-1))
                        writer.append(separator);
                }
                writer.append(System.lineSeparator());
            }
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
/////////////////////////////////////////////////
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        entete = new javax.swing.JPanel();
        parametrages = new javax.swing.JButton();
        about = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        resultat = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jSplitPane2 = new javax.swing.JSplitPane();
        jSplitPane1 = new javax.swing.JSplitPane();
        typedonnee = new javax.swing.JPanel();
        zoneModele = new javax.swing.JPanel();
        modeleCharger = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        arbre = new javax.swing.JTree();
        parametrage = new javax.swing.JPanel();
        zoneSelection = new javax.swing.JPanel();
        zoneAbout = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        param = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        CheminCSV = new javax.swing.JTextField();
        CheminXML = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        dico = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        listeValeurs = new javax.swing.JList();
        panier = new javax.swing.JPanel();
        zonePanier = new javax.swing.JPanel();
        panierRempli = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        valeurDansPanier = new javax.swing.JList();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        entete.setBackground(new java.awt.Color(255, 255, 255));

        parametrages.setBackground(new java.awt.Color(255, 255, 255));
        parametrages.setFont(new java.awt.Font("Arial", 3, 24)); // NOI18N
        parametrages.setText("Charger");
        parametrages.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                parametragesActionPerformed(evt);
            }
        });

        about.setBackground(new java.awt.Color(255, 255, 255));
        about.setFont(new java.awt.Font("Arial", 3, 24)); // NOI18N
        about.setText("About");
        about.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboutActionPerformed(evt);
            }
        });

        jLabel4.setBackground(new java.awt.Color(204, 204, 204));
        jLabel4.setFont(new java.awt.Font("Lucida Bright", 3, 36)); // NOI18N
        jLabel4.setText("Knomana");

        javax.swing.GroupLayout enteteLayout = new javax.swing.GroupLayout(entete);
        entete.setLayout(enteteLayout);
        enteteLayout.setHorizontalGroup(
            enteteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(enteteLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(about, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(parametrages, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        enteteLayout.setVerticalGroup(
            enteteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(enteteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(enteteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(about, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(parametrages, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, enteteLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        resultat.setBackground(new java.awt.Color(255, 255, 255));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTable1.setAutoResizeMode(jTable1.AUTO_RESIZE_OFF);
        jScrollPane3.setViewportView(jTable1);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setForeground(new java.awt.Color(255, 255, 255));

        jButton1.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        jButton1.setText("Sauvegarde");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout resultatLayout = new javax.swing.GroupLayout(resultat);
        resultat.setLayout(resultatLayout);
        resultatLayout.setHorizontalGroup(
            resultatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3)
            .addGroup(resultatLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        resultatLayout.setVerticalGroup(
            resultatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(resultatLayout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jSplitPane2.setAutoscrolls(true);
        jSplitPane2.setOneTouchExpandable(true);

        jSplitPane1.setAutoscrolls(true);
        jSplitPane1.setOneTouchExpandable(true);

        typedonnee.setBackground(new java.awt.Color(255, 255, 255));
        typedonnee.setMaximumSize(new java.awt.Dimension(190, 385));
        typedonnee.setMinimumSize(new java.awt.Dimension(150, 140));
        typedonnee.setPreferredSize(new java.awt.Dimension(150, 384));
        typedonnee.setLayout(new java.awt.CardLayout());

        zoneModele.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout zoneModeleLayout = new javax.swing.GroupLayout(zoneModele);
        zoneModele.setLayout(zoneModeleLayout);
        zoneModeleLayout.setHorizontalGroup(
            zoneModeleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 150, Short.MAX_VALUE)
        );
        zoneModeleLayout.setVerticalGroup(
            zoneModeleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 252, Short.MAX_VALUE)
        );

        typedonnee.add(zoneModele, "card3");

        arbre.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                arbreMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(arbre);

        javax.swing.GroupLayout modeleChargerLayout = new javax.swing.GroupLayout(modeleCharger);
        modeleCharger.setLayout(modeleChargerLayout);
        modeleChargerLayout.setHorizontalGroup(
            modeleChargerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
        );
        modeleChargerLayout.setVerticalGroup(
            modeleChargerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 252, Short.MAX_VALUE)
        );

        typedonnee.add(modeleCharger, "card4");

        jSplitPane1.setLeftComponent(typedonnee);

        parametrage.setBackground(new java.awt.Color(255, 255, 255));
        parametrage.setLayout(new java.awt.CardLayout());

        zoneSelection.setBackground(new java.awt.Color(255, 255, 255));
        zoneSelection.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout zoneSelectionLayout = new javax.swing.GroupLayout(zoneSelection);
        zoneSelection.setLayout(zoneSelectionLayout);
        zoneSelectionLayout.setHorizontalGroup(
            zoneSelectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 500, Short.MAX_VALUE)
        );
        zoneSelectionLayout.setVerticalGroup(
            zoneSelectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 252, Short.MAX_VALUE)
        );

        parametrage.add(zoneSelection, "card2");

        zoneAbout.setBackground(new java.awt.Color(255, 255, 255));

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jTextArea1.setText("L'interface logicielle Knomana, permet de consulter les données, \nqu’elles soient relatives à un type d'information ou \ndes études transversales entres \nles différents types d'informations, et de constituer des scénarios d'études.\nLa consultation des données nécessite un paramétrage\n préalable des fichiers (modèle de donnée et Jeu de donnée), puis appuyer sur GO dans Paramétrages.");
        jTextArea1.setEditable(false); // pour empécher d'ecrire dans le JtextArea
        jScrollPane5.setViewportView(jTextArea1);

        javax.swing.GroupLayout zoneAboutLayout = new javax.swing.GroupLayout(zoneAbout);
        zoneAbout.setLayout(zoneAboutLayout);
        zoneAboutLayout.setHorizontalGroup(
            zoneAboutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 500, Short.MAX_VALUE)
            .addGroup(zoneAboutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, zoneAboutLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 494, Short.MAX_VALUE)))
        );
        zoneAboutLayout.setVerticalGroup(
            zoneAboutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 252, Short.MAX_VALUE)
            .addGroup(zoneAboutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(zoneAboutLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        parametrage.add(zoneAbout, "card5");

        param.setBackground(new java.awt.Color(255, 255, 255));
        param.setForeground(new java.awt.Color(255, 255, 255));

        jLabel2.setText("Modéle de donnée");

        jLabel3.setText("Jeu de donnée");

        CheminCSV.setText("Chemin du fichier...");
        CheminCSV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CheminCSVActionPerformed(evt);
            }
        });

        CheminXML.setText("Chemin du fichier...");

        jButton2.setText("GO");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout paramLayout = new javax.swing.GroupLayout(param);
        param.setLayout(paramLayout);
        paramLayout.setHorizontalGroup(
            paramLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, paramLayout.createSequentialGroup()
                .addGroup(paramLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(paramLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2))
                    .addGroup(paramLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(paramLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(paramLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(CheminCSV)
                            .addComponent(CheminXML, javax.swing.GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE))))
                .addContainerGap())
        );
        paramLayout.setVerticalGroup(
            paramLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paramLayout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(paramLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(CheminXML, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addGroup(paramLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(CheminCSV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jButton2)
                .addContainerGap(86, Short.MAX_VALUE))
        );

        parametrage.add(param, "card2");

        dico.setBackground(new java.awt.Color(255, 255, 255));
        dico.setForeground(new java.awt.Color(255, 255, 255));

        listeValeurs.setModel(valeurs);
        listeValeurs.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listeValeursMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(listeValeurs);

        javax.swing.GroupLayout dicoLayout = new javax.swing.GroupLayout(dico);
        dico.setLayout(dicoLayout);
        dicoLayout.setHorizontalGroup(
            dicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
        );
        dicoLayout.setVerticalGroup(
            dicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 252, Short.MAX_VALUE)
        );

        parametrage.add(dico, "card2");

        jSplitPane1.setRightComponent(parametrage);

        jSplitPane2.setLeftComponent(jSplitPane1);

        panier.setBackground(new java.awt.Color(255, 255, 255));
        panier.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        panier.setMaximumSize(new java.awt.Dimension(200, 150));
        panier.setMinimumSize(new java.awt.Dimension(130, 80));
        panier.setPreferredSize(new java.awt.Dimension(150, 90));
        panier.setLayout(new java.awt.CardLayout());

        zonePanier.setBackground(new java.awt.Color(255, 255, 255));
        zonePanier.setMinimumSize(new java.awt.Dimension(130, 80));
        zonePanier.setPreferredSize(new java.awt.Dimension(131, 80));

        javax.swing.GroupLayout zonePanierLayout = new javax.swing.GroupLayout(zonePanier);
        zonePanier.setLayout(zonePanierLayout);
        zonePanierLayout.setHorizontalGroup(
            zonePanierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 150, Short.MAX_VALUE)
        );
        zonePanierLayout.setVerticalGroup(
            zonePanierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 256, Short.MAX_VALUE)
        );

        panier.add(zonePanier, "card2");

        panierRempli.setBackground(new java.awt.Color(255, 255, 255));
        panierRempli.setForeground(new java.awt.Color(255, 255, 255));
        panierRempli.setMaximumSize(new java.awt.Dimension(200, 120));
        panierRempli.setMinimumSize(new java.awt.Dimension(130, 80));
        panierRempli.setPreferredSize(new java.awt.Dimension(131, 80));

        jScrollPane4.setMinimumSize(new java.awt.Dimension(130, 80));
        jScrollPane4.setPreferredSize(new java.awt.Dimension(131, 80));

        valeurDansPanier.setModel(monPanier);
        valeurDansPanier.setMinimumSize(new java.awt.Dimension(130, 80));
        valeurDansPanier.setPreferredSize(new java.awt.Dimension(131, 80));
        valeurDansPanier.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                valeurDansPanierMouseClicked(evt);
            }
        });
        //valeurDansPanier.setAutoResizeMode(valeurDansPanier.AUTO_RESIZE_OFF);
        jScrollPane4.setViewportView(valeurDansPanier);

        javax.swing.GroupLayout panierRempliLayout = new javax.swing.GroupLayout(panierRempli);
        panierRempli.setLayout(panierRempliLayout);
        panierRempliLayout.setHorizontalGroup(
            panierRempliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panierRempliLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
                .addContainerGap())
        );
        panierRempliLayout.setVerticalGroup(
            panierRempliLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panierRempliLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE)
                .addContainerGap())
        );

        panier.add(panierRempli, "card3");

        jSplitPane2.setRightComponent(panier);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(entete, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSplitPane2)
                    .addComponent(resultat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(entete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSplitPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(resultat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    /*.......................Méthode pour les interactions dans la zone de séléction......................*/
// Méthodes action listeneer des boutons "paramétrages" et "GO"
    private void parametragesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_parametragesActionPerformed
// Affichage panel de paramétrages des fichiers
//Suprression
        parametrage.removeAll();
        parametrage.repaint();
        parametrage.revalidate();
        
//Ajout mise à jour
        parametrage.add(param);
        parametrage.repaint();
        parametrage.revalidate();
        
    }//GEN-LAST:event_parametragesActionPerformed
    
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
// Affichage du panel de la zone de sélection
//Suprression
        parametrage.removeAll();
        parametrage.repaint();
        parametrage.revalidate();
        
//Ajout mise à jour
        parametrage.add(dico);
        parametrage.repaint();
        parametrage.revalidate();
        
//Affichage du  pannel de Modéle de donnée récupéré
// Suprression
        typedonnee.removeAll();
        typedonnee.repaint();
        typedonnee.revalidate();
        
//Ajout mise à jour
        typedonnee.add(modeleCharger);
        typedonnee.repaint();
        typedonnee.revalidate();
        f = new FichierDonneeTest();
//
        String cheminXML = CheminXML.getText();
        monArbre(cheminXML);
        
//
        String cheminCSV = CheminCSV.getText();
        f.parseList(cheminCSV);
        
        /*try {
        
        f.parseListXlsx();
        } catch (IOException ex) {
        Logger.getLogger(Accueil.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        //   System.out.println("999 "+f.getContenuCSV().size());
        //Affichage du  pannel Panier Rempli
        
        
        
        // Suprression
        panier.removeAll();
        panier.repaint();
        panier.revalidate();
        
        //Ajout mise à jour
        panier.add(valeurDansPanier);
        panier.repaint();
        panier.revalidate();
        
    }//GEN-LAST:event_jButton2ActionPerformed
    
    String infos;//nom du noeud selectionner pour le sauvegarder
// Methode MouseCLicked pour ajouter les éléments chosis du JTree vers le Jlist selection   
    private void arbreMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_arbreMouseClicked
        valeurs.clear();
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) arbre.getSelectionPath().getLastPathComponent();
        infos = selectedNode.toString();
        
        //valeurs.addElement(selectedNode.getUserObject().toString());
        Integer num = selectedNode.getParent().getIndex(selectedNode);//recupere l'index du noeud selectionné
        
        int num_parent_select = selectedNode.getRoot().getIndex(selectedNode.getParent());
        
        int compt = 0;
        for(int i=0; i<num_parent_select; i++){
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) selectedNode.getRoot().getChildAt(i);
            compt += node.getChildCount();
        }
        
        selectedNode.getParent();
        
        TreeSet<String> set = (TreeSet)f.getListe((compt+num));
        
        if(!selectedNode.getParent().equals(selectedNode.getRoot())){
            if(set != null){
                TreeSet<String> setadd = (TreeSet)f.getListe((compt+num));
                //System.out.println("-----------------------------++++++++++++++");
                for(String str : set){
                    if(str.equals("")){
                        //System.out.println("-----------------------------++++++++++++++");
                        setadd.add("Pas d'infos ");
                    }
                    else{
                        setadd.add(str);
                    }  
                }
                //System.out.println("setadd "+setadd);
                valeurs.addCollection(setadd);
            }
            else{
                //System.out.println("********************=====================");
                valeurs.addElement("Pas d'infos");
            }
        }
    }//GEN-LAST:event_arbreMouseClicked
    
// Methode pour ajouter les elements du Jlist selection vers le Jlist panier
    private void listeValeursMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listeValeursMouseClicked
        if (evt.getClickCount() == 2)
        {
            System.out.println("###################################################");
            if (!valeurs.isEmpty()){ //valeurs non vide
                elem = (String) valeurs.getElementAt(listeValeurs.locationToIndex(evt.getPoint()));
                valeurs.getElementAt(listeValeurs.locationToIndex(evt.getPoint()));
                
                int numero_ligne_selectionner = listeValeurs.locationToIndex(evt.getPoint());
                
                if (!monPanier.contains(elem+" "+infos)){ // Si le panier ne contient pas l'element on l'ajoute
                    //on ajoute lement au panier et a la hash map
                    //Pour stocker les élément du panier en clés-valeurs avec le Hash Map
                    String ckey = infos;
                    String value= elem;
                    
                    // monPanier.addElement(value, ckey, infos); // On ajoute l'élement dans le panier
                    
                    monPanier.addElement(infos, ckey, value); // On ajoute l'élement dans le panier
                    
                }
                
                //Attention cela fonctionne que si le choix est effectuer dans l'ordre ou si la hasmap et remplise avec tout les element
                //System.out.println("Parcours de l'objet HashMap : "+monPanier.panier.get(infos).get(numero_ligne_selectionner));
                //1)nom de la colonne
                /*               System.out.println("Nom de colonne = "+infos);
                // 2) Valeur de la cellule
                System.out.println("Nom de la ligne = "+elem);
                if(!monPanier.getPanier().get(infos).isEmpty() && numero_ligne_selectionner < monPanier.getPanier().get(infos).size()){
                //    System.out.println("Valeur de la HashMap = "+monPanier.getPanier().get(infos).get(numero_ligne_selectionner));
                // }
                //System.out.println(monPanier.panier);
                // 3 Resulat du test pour décider si on garde ou pas la ligne
                //3-1: On affiche tout
                //    NewClass.TestAffichage();
                */
                //     System.out.println("appel de la fonction pour récupérer les résultats\n -------------------------");
                resultatsTemporaires = mesResutats();
                //    System.out.println("\n\n"+resultatsTemporaires+"\n-------------");
                mesResultatsDefinitifs();
                
                //System.out.println("Resultat Stocke : taille "+resultatsDefinitif.size());
                
                for (ArrayList<String> res : resultatsDefinitif){
                    System.out.println(res);
                }
                System.out.println("---------");
//3-2: Afficher les lignes contenant la valeurs de la clé
                //  System.out.println("liste des résultats à mettre dans le tableau");
                // System.out.println(resultatsDefinitif);
                // System.out.println(resultatsDefinitif.size());
                
                //     final ArrayList<ArrayList<String>> resultatsDefinitif = mesResultatsDefinitifs();
                //..............Pour la liste resultat.........................................................//
                /*                MesResultats.setModel(new javax.swing.AbstractListModel() {
                ArrayList<ArrayList<String>> res = resultatsDefinitif;
                public int getSize() { return res.get(0).size(); }
                public Object getElementAt(int i) { return res.get(0).get(i); }
                });
                */
                //.............................Pour le tableau resultat.................................................//
                jTable1.setModel(new javax.swing.table.AbstractTableModel() 
                { 
                        //if (!jTable1.contains(res)) // Pour ne pas ajouter le resultat s'il est déja ajouter
                     
                    ArrayList<ArrayList<String>> res = resultatsDefinitif;
                    
                    @Override
                    public String getColumnName(int columnIndex){ //récupère les entetes du csv
                        if(columnIndex < f.getContenuCSV().size()){
                            return f.getContenuCSV().get(columnIndex).get(0);
                        }
                        return "Vide";
                    }
                    
                    @Override
                    public int getRowCount() {
                        return res.size();
                    }
                    
                    @Override
                    public int getColumnCount() {
                        //    return 15;
                        return 65; // Le nombre de colonne
                        //           ContenuCSV.get().size(); recupéré le nbre de colonne du fichier CSV
                        // Attention revoir affichage des resultats pas correcte
                    }
                    
                    @Override
                    public Object getValueAt(int row, int col)
                    {
                      
                        
                        
                            if(row < res.size() && col < res.get(row).size())
                            {
                                //if (row == 1 && res.get(row).get(col)!= null)
                                //    System.out.println(res.get(row).get(col));
                                
                                return res.get(row).get(col);
                                
                            }
                            return "";
                        }
                        
                    
                });
                
                //3-2: Afficher les lignes contenant la valeurs de la clé
                
            }
        }
        
        // if (evt.getClickCount() == 2)
    }//GEN-LAST:event_listeValeursMouseClicked
    
    
    
// Methode pour supprimer un element du panier aprés un clic
    private void valeurDansPanierMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_valeurDansPanierMouseClicked
        if (evt.getClickCount() == 2){ //clique deux fois pour supprimer
            if (!monPanier.isEmpty()){
                String value = (String) monPanier.getElementAt(valeurDansPanier.locationToIndex(evt.getPoint()));
                System.out.println("suppression "+value);
                monPanier.deleteElement(value);
                panier.repaint();
                panier.revalidate();
                
                String supprValue = monPanier.getValue(value);
                mesSuppression(supprValue);
                resultat.repaint();
                resultat.revalidate();
            }
            
           // System.out.println("Resultat Stocke : taille "+resultatsDefinitif.size());
                for (ArrayList<String> res : resultatsDefinitif){
                    System.out.println(res);
                }
            System.out.println("---------");
        }
    }//GEN-LAST:event_valeurDansPanierMouseClicked
    
    private void CheminCSVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CheminCSVActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CheminCSVActionPerformed
//............Bouton pour le About...........
    private void aboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutActionPerformed
        // Affichage panel About
//Suprression
        parametrage.removeAll();
        parametrage.repaint();
        parametrage.revalidate();
        
//Ajout mise à jour
        parametrage.add(zoneAbout);
        parametrage.repaint();
        parametrage.revalidate();
        
    }//GEN-LAST:event_aboutActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       // TODO add your handling code here:
        
        String namefile = nf.getJTextPane().getText();
        nf.setJtable(jTable1);
        nf.setVisible(true);
        /*try { 
            writeCsvJtable(jTable1, namefile);
        } catch (IOException ex) {
            Logger.getLogger(Accueil.class.getName()).log(Level.SEVERE, null, ex);
        }*/ 
    }//GEN-LAST:event_jButton1ActionPerformed

   
    
    /*====================================================================================================================
    les composants insérés dans le Jframe
    ======================================================================================================================*/
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField CheminCSV;
    private javax.swing.JTextField CheminXML;
    private javax.swing.JButton about;
    private javax.swing.JTree arbre;
    private javax.swing.JPanel dico;
    private javax.swing.JPanel entete;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JList listeValeurs;
    private javax.swing.JPanel modeleCharger;
    private javax.swing.JPanel panier;
    private javax.swing.JPanel panierRempli;
    private javax.swing.JPanel param;
    private javax.swing.JPanel parametrage;
    private javax.swing.JButton parametrages;
    private javax.swing.JPanel resultat;
    private javax.swing.JPanel typedonnee;
    private javax.swing.JList valeurDansPanier;
    private javax.swing.JPanel zoneAbout;
    private javax.swing.JPanel zoneModele;
    private javax.swing.JPanel zonePanier;
    private javax.swing.JPanel zoneSelection;
    // End of variables declaration//GEN-END:variables
    
    private void setAutoResizeMode() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
      public static void writeCsvJtable(JTable j, String nameFile) throws IOException{
        //nameFile = "save.csv";
        File file = new File(nameFile);
        FileOutputStream out = new FileOutputStream(file);
        String chaineOut = "";
        for(int i=0; i<j.getModel().getColumnCount(); i++){
            chaineOut += j.getModel().getColumnName(i)+";";
        }
        chaineOut += "\n";
        for(int i=0; i<j.getModel().getRowCount(); i++){
            for(int e=0; e<j.getModel().getColumnCount(); e++){
                chaineOut += j.getModel().getValueAt(i, e)+";";
            }
            chaineOut += "\n";
        }
        out.write(chaineOut.getBytes());      
        out.close();
        
    }
}

//........................................; A ajouter objet pour déplacer le trait verticale de la zone....//