/*
** Pour liste Modéle de la zone de séléction.
====================================Partie de traitement des fichier CSV============================================
Le format des fichiers CSV (Comma Separated Values) est l'un des plus simples qu'on puisse imaginer. 
Les données sont organisées par ligne. Une ligne peut contenir plusieurs champs/colonnes séparés par des points virgules(;).
La première ligne donne (souvent) les titres des colonnes. 
Bien entendu, l'ordre des colonnes est le même dans toutes les lignes.
*/
package javaapplication;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.NavigableSet;
import java.util.TreeSet;
import javax.swing.AbstractListModel;

/**
 *
 * @author moussa
 */
class monListeModele extends AbstractListModel{
    private TreeSet<String> info;
    
      
    public monListeModele () {
        info = new TreeSet<>(); //Un ensemble pour ne pas ajouter des doublons
        
    }
    @Override
    public int getSize() {
        return info.size ();
    }
    
    @Override
    public Object getElementAt(int index) {
        return info.toArray()[index];
    }
    
    public void addElement(String x){
        info.add(x);
        fireContentsChanged(this, 0, info.size());
    }
    
    public void addCollection(Collection<String> x){ //Pour ajouter un ensemble (liste,ensemble...)
        info.addAll(x);
        fireContentsChanged(this, 0, info.size());
    }
    
    public void clear(){
        info.clear();
    }
    public boolean isEmpty(){
        return info.isEmpty();
    }
    
    public boolean contains(String elem){
        return info.contains(elem);
    }
}
