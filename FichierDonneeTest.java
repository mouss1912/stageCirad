/*Le format des fichiers CSV (Comma Separated Values).
*Les données sont organisées par ligne. Une ligne peut contenir plusieurs champs/colonnes séparés par des points virgules.
*La première ligne donne (souvent) les titres des colonnes. Bien entendu, l'ordre des colonnes est le même dans toutes les lignes
* Import de la lib opencsv
*La fonction StringTokenizer() permet de parser  le fichier et d'extraire le fichier csv.
//////////////////////////Pour récupérer les données en fonction
* Dans cette classe je fait le parse pour correspondre chaque colonne du ficher csv 
*/
package javaapplication;
//import android.util.ArraySet;

import com.opencsv.CSVParser;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author moussa
 */
public class FichierDonneeTest {
    
    private List<TreeSet<String>> list;
    private ArrayList<ArrayList<String>> contenuCSV;
    public List<String> listColonne;
    
    public FichierDonneeTest(){
        
    }
    
    public ArrayList<ArrayList<String>> getContenuCSV(){
        return contenuCSV;
    }
 //...........................La Méthode Parse qui se lance dés que je clique sur le bouton GO...........................//   
    public void parseList(String nameFile){
        TreeSet<String> ens = new TreeSet<>();
        list = new ArrayList<>();
        contenuCSV = new ArrayList<ArrayList<String>>();
        listColonne = new ArrayList<String>();
        
        try {
            String csvFile = (nameFile);
            
            String delimiter = ";"; // Les colonnes sont séparés par ; //Probleme si une colonne contient un ;? (Ici j'ai changer les ; par ,)        
            String line = null;
            StringTokenizer strToken = null;
            BufferedReader bufferReader;
            int lineID = 0;
            int fieldID = 0;
//............................Ouvrir le fichier CSV...................................................//
            bufferReader = new BufferedReader(new FileReader(csvFile));
            
            
//.........................parcourir les lignes du fichier CSV........................................//
            while ((line = bufferReader.readLine()) != null) {
                lineID++;
//......................Parcourir les champs séparés par delimiter....................................//
                strToken = new StringTokenizer(line, delimiter);
                String[] split = line.split(delimiter);
             //   System.out.println("len ------------- "+split.length+" "+split);
                
              //  System.out.println("------------------------------------------");
                while (/*strToken.hasMoreTokens()*/ fieldID < split.length ) {
                    
                    fieldID++;
                    //String resulst = (String)strToken.nextElement();
                    String resulst = split[fieldID-1];
                //    System.out.println("valeur parser depuis csv : " + resulst);
                    
                    //remplit la liste de listes contenuCSV (avec doublons et cellules vides)
                    if (lineID >= 4)
                    {
                        if(contenuCSV.size() < fieldID)
                        {
                            contenuCSV.add(new ArrayList<String>());
                           // if(contenuCSV.get(fieldID-1).isEmpty()){
                              //  contenuCSV.get(fieldID-1).add(resulst);
                            //}
                           // else{
                              //  contenuCSV.get(fieldID-1).add(resulst);
                            //}
                        }
                    //    else{ 
                           // if(!resulst.isEmpty()){ //&& !resulst.equals("")
                              //  contenuCSV.get(fieldID-1).add(resulst);
                           // }
                      //  }
                        contenuCSV.get(fieldID-1).add(resulst);
                    }
                    
                    
                    //rempli la liste du milieu (sans doublon)
                    if(lineID == 4)
                        listColonne.add(resulst);
                    if(lineID > 4)
                    {
                        
                        if(list.isEmpty() || list.size() < fieldID){
                            list.add(new TreeSet<String>());
                          //  contenuCSV.add(new ArrayList<String>());
                            if(list.get(fieldID-1).isEmpty()){
                                list.get(fieldID-1).add(resulst);
                            //    contenuCSV.get(fieldID-1).add(resulst);
                            }
                            else{
                                list.get(fieldID-1).add(resulst);
                             //   contenuCSV.get(fieldID-1).add(resulst);
                            }
                        }
                        else{
                            if(!resulst.isEmpty() && !resulst.equals("")){
                                list.get(fieldID-1).add(resulst);
                               // contenuCSV.get(fieldID-1).add(resulst);
                                //System.out.println("javaapplication.FichierDonneeTest.parseList() +++ "+resulst.length()+" "+resulst.);
                            }
                            
                        }
                    }
                    
                }
                fieldID = 0;
              //  System.out.println("------------------------------------------");
            }
            
        } catch (IOException ex) {
            Logger.getLogger(CSVParser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
/*..........................Méthode pour récupérer les éléments de la colonne comme un ensemble (Pour éviter les doublons)..........*/    
    //Elle prend le numéro de la colonne , fait le parse et récupére que les élements de la colone
    public Collection<String> getListe(Integer col) {
        if(!list.get(col).isEmpty()){
            //System.out.println("i'm here ! "+list.get(col));
            return list.get(col);
        }
        else{
            System.out.println("javaapplication.FichierDonneeTest.getListe()");
            return null;
        }
    }
    
/*..................Méthode pour afficher le contenu du fichierCSV. :Pour tester..............................................*/    
    public void afficheContenuCSV(){
        String affiche="";
        if (contenuCSV.isEmpty()){
            //System.out.println("afficherPart1");
            affiche="list is empty";
        }
        else{
             //System.out.println("afficherPart2");
            for (ArrayList<String> lis : contenuCSV){
                if (!lis.isEmpty()){
                   // System.out.println("afficherPart3");
                    for (String val : lis){
                        if(val.equals(""))
                            val="NoR";
                        affiche+=val+"   ";
                           //System.out.println("afficherPart4");
                    }
                    //affiche+="\n";
                   // System.out.println(affiche);
                    affiche = ""; // reset on la remet à zero
                }
            }
        }
        
       // System.out.println("afficherPart5");
   
    }
    //   public boolean contains(String elem){
    //      return line.contains(elem);
    //  }
    
//////// //////////////////////////////////////////////////////// ///////////////////////////////////////////
    
   /*Méthode de Parse du csv premiére tentative*/ 
    
    /*  public void parse() {
    try {
    String csvFile = ("TestsExcel");
    
    
    String delimiter = ";"; // Les colonnes sont séparés par ; //Probleme si une colonne contient un ;?
    String line = null;
    StringTokenizer strToken = null;
    BufferedReader bufferReader;
    int lineID = 0;
    int fieldID = 0;
    //Ouvrir le fichier CSV
    bufferReader = new BufferedReader(new FileReader(csvFile));
    
    TreeSet<String> ens = new TreeSet<>();
    
    
    //parcourir les lignes du fichier CSV
    while ((line = bufferReader.readLine()) != null) {
    lineID++;
    //Parcourir les champs séparés par delimiter
    if (lineID > 3 ){ // Commencé à partir de la ligne 4
    
    strToken = new StringTokenizer(line, delimiter);
    
    while (strToken.hasMoreTokens() ) {
    fieldID++;
    String resulst = strToken.nextToken();
    if(!ens.contains(resulst)) {
    System.out.println("Ligne " + lineID
    + " / colonne " + fieldID
    + " : " + resulst);
    }
    ens.add(resulst);
    }
    fieldID = 0;
    }
    
    }
    
    } catch (IOException ex) {
    Logger.getLogger(CSVParser.class.getName()).log(Level.SEVERE, null, ex);
    }
    }*/
    
    
    /*  public TreeSet<String> getElementByColonne(Integer col) {
    TreeSet<String> ens = new TreeSet<>();
    
    try {
    String csvFile = ("TestsExcel");
    
    
    String delimiter = ";"; // Les colonnes sont séparés par ; //Probleme si une colonne contient un ;?
    String line = null;
    StringTokenizer strToken = null;
    BufferedReader bufferReader;
    int lineID = 0;
    int fieldID = 0;
    //Ouvrir le fichier CSV
    bufferReader = new BufferedReader(new FileReader(csvFile));
    
    
    //parcourir les lignes du fichier CSV
    while ((line = bufferReader.readLine()) != null) {
    lineID++;
    //Parcourir les champs séparés par delimiter
    strToken = new StringTokenizer(line, delimiter);
    
    while (strToken.hasMoreTokens() ) {
    fieldID++;
    String resulst = strToken.nextToken();
    
    if(fieldID == col){ // Si c'est le num de colonne il l'ajoute
    ens.add(resulst);
    }
    }
    fieldID = 0;
    
    }
    
    } catch (IOException ex) {
    Logger.getLogger(CSVParser.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    return ens; // elle retourne l'ensemble
    }*/
    
    
  ////////////////////////// ///////////////////////////////////////////////////////////////////////////////////////
    /* Méthode de parse du fichier excel fonctionel mais non utilisé dans le programme*/
 /*   public void parseListXlsx() throws FileNotFoundException, IOException{
        FileInputStream fichier = new FileInputStream(new File("TestsExcel.xlsx"));
        XSSFWorkbook wb = new XSSFWorkbook(fichier);
        XSSFSheet feuille = wb.getSheetAt(0);
        
        int lineID = 0;
        int fieldID = 0;
        list = new ArrayList<>();
        contenuCSV = new ArrayList<ArrayList<String>>();
        
        for(Row rowe : feuille)
        { //parcours les lignes de la premiere feuille
            if(lineID > 3){
                while( fieldID < rowe.getLastCellNum() ){
                    Cell col = rowe.getCell(fieldID);
                    //System.out.println("--"+cell.toString()); //affiche le contenu de ces cellules
                    String resulst = "null";
                    if(col != null){
                        resulst = col.toString();
                    }
                    
                    if(contenuCSV.isEmpty() || contenuCSV.size() < fieldID)
                    {
                        contenuCSV.add(new ArrayList<String>());
                        if(contenuCSV.get(fieldID-1).isEmpty()){
                            contenuCSV.get(fieldID-1).add(resulst);
                        }
                        else{
                            contenuCSV.get(fieldID-1).add(resulst);
                        }
                    }
                    else{
                        if(!resulst.isEmpty() && !resulst.equals("")){
                            contenuCSV.get(fieldID-1).add(resulst);
                        }
                    }
                    
                    if(lineID > 4)
                    {
                        if(list.isEmpty() || list.size() < fieldID){
                            list.add(new TreeSet<String>());
                            contenuCSV.add(new ArrayList<String>());
                            if(list.get(fieldID-1).isEmpty()){
                                list.get(fieldID-1).add(resulst);
                                contenuCSV.get(fieldID-1).add(resulst);
                            }
                            else{
                                list.get(fieldID-1).add(resulst);
                                contenuCSV.get(fieldID-1).add(resulst);
                            }
                        }
                        else{
                            if(!resulst.isEmpty() && !resulst.equals("")){
                                list.get(fieldID-1).add(resulst);
                                contenuCSV.get(fieldID-1).add(resulst);
                                //System.out.println("javaapplication.FichierDonneeTest.parseList() +++ "+resulst.length()+" "+resulst.);
                            }
                            
                        }
                    }
                    
                    
                    if(list.isEmpty() || list.size() <= fieldID){
                        list.add(new TreeSet<String>());
                        if(col != null){
                            if(list.get(fieldID).isEmpty()){
                                list.get(fieldID).add(col.toString());
                            }
                            else{
                                list.get(fieldID).add(col.toString());
                                
                            }
                            //System.out.println("d'infos "+col.toString());
                        }
                        else {
                            //System.out.println("Pas d'infos");
                        }
                    }
                    else{
                        if(col != null){
                            list.get(fieldID).add(col.toString());
                            //System.out.println("d'infos "+col.toString());
                        }
                        else {
                            //System.out.println("Pas d'infos");
                        }
                    }
                    //System.out.println("d'infos "+col.toString());
                    fieldID++;
                }
                fieldID = 0;
            }
            lineID++;
        }
        
    }/**/
    
}