/*
* Liste modéle pour la zone panier: Cette liste contiendra les valeurs sélectionnés dans la zone de sélections
// Il devrait permettre aussi de supprimer des valeurs déja ajoutées
//D'afficher automatiquement les resultats correspondant aux elements ajoutés dans le panier
+ Les éléments mis dans le panier sont un hashmap (clés, valeurs) clés= type info (eg. nom latin plante) et
valeurs= un ArrayList(liste d'info ajouteé eg. Abies...)
+ Chaque élément du panier est un hashmap (String, ArrayList).
+ NB : monPanier= mon liste modéle pour la zone d'affichage de panier
*/

package javaapplication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.swing.AbstractListModel;

/**
 *
 * @author moussa
 */

class monListePanier extends AbstractListModel{
    //.................................Déclaration des attributs..............................................//
    private List<String> listePanier ;
    private Map<String, ArrayList> panier;
    private Map<String, String> correspondancePanier; //entre la valeur dans l'affichage et la liste du milieu
    
    
    //.................................Méthode Constructeurs...................................................//
    public monListePanier () {
        panier = new HashMap<String, ArrayList>();
        listePanier = new LinkedList<>();
        correspondancePanier = new HashMap<String, String>();
        
        // for (i in List);
        // panier.getelm(i).get(O);
        //     Map<String, String> hm = new HashMap<>();
        //    int hash = Arrays.hashCode(List.toArray());
    }
    
    //.......................Méthode accesseurs..........................................................//
    @Override
    public int getSize() {
        return listePanier.size ();
    }
    
    public Map<String, ArrayList> getPanier()
    {
        return panier;
    }
    
    @Override
    public Object getElementAt(int index) {//pour supprimer du panier
        return listePanier.get(index);
    }
    
    public String getValue(String valuePanier){
        return correspondancePanier.get(valuePanier);
    }
    
    //..........................Méthode pour ajouter dans panier..........................................//
    public void addElement(String value, String key, String infos){//remanier
        if (!listePanier.contains(value+" : "+infos)){
            listePanier.add(value+" : "+infos);
            fireContentsChanged(this, 0, listePanier.size());
            correspondancePanier.put(value+" : "+infos, infos);
            //remplissage hash map
            if(panier.containsKey(key)){
                //ajouter lelement dans la liste de la key
                panier.get(key).add(value);
                System.out.println("ajout d'un nouvel objet à la liste de la key : "+panier.get(key));
            }
            else {
                //creer la clef et la liste
                ArrayList<String> values = new ArrayList<String>();
                values.add(value);
                //Mettre clés et values dans panier
                panier.put(key, values);
                System.out.println("objet ajouté à la hashmap : "+panier.get(key));
            }
            
        }
        
    }
    public boolean isEmpty(){
        return listePanier.isEmpty();
    }
    
    public boolean contains(String elem){//todo
        return listePanier.contains(elem);
    }
    
    
    // Methode pour supprimer dans panier
    public void deleteElement(String o){//remanier
        listePanier.remove(o);
        //correspondancePanier.remove(o);
        
        
    }
    
    Object get(String infos) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}









