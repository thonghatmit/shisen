package tud.ai1.shisen.model;

import java.util.ArrayList;
import java.util.List;
import java.util.*;
import tud.ai1.shisen.util.IOOperations;
import java.lang.Exception;
import tud.ai1.shisen.util.Consts; 

/**
 * Klasse, welche für die Verwaltung der Highscores verantworlicht ist. Laedt,
 * speichert und legt neue Highscore-Eintraege in einer ArrayList an und
 * speichert die ArrayListen zusammen mit dem Key der passenden Feldgröße in
 * eine Hashmap.
 *
 * @author Andrej Felde, Daniel Stein, Nicklas Behler
 */
public class Highscore {

  /**
   * Maximale Anzahl an Highscore-Eintraegen in einer Highscore-Liste pro
   * Feldgroeße.
   */
  public static final int MAX_ENTRIES = 10;
  

  /**
   * Alle Highscore-Listen mit zugehöriger Levelgröße
   */
  private List<HighscoreEntry> highscores;

  /**
   * Konstruktor der Highscore-Klasse.
   */
  public Highscore() {
    highscores = new ArrayList<HighscoreEntry>();
  }

  /**
   * Methode zum Erzeugen der Highscores aus dem gespeicherten Stringformat. Teilt
   * den uebergebenen String in einzelne Eintraege und speichert diese in einem
   * String-Array. Danach werden die einzelnen Eintraege in der Hashmap, in die
   * passenden Arraylists ihrer Feldgröße einsortiert.
   *
   * @param str String, der die vorherigen Highscores enthaelt.
   */
  public void initHighscore(String str) {
    if (str.isEmpty())
      return;

    for (String line : str.split("\\r?\\n")) {
      addEntry(new HighscoreEntry(line));
    }
  }
  

  public void saveToFile(String fileName) {
    // TODO Aufgabe 4.2b
    
    try{
        String a="";
    for(HighscoreEntry highscore : highscores) {
        
  
a=a+(highscore.dateToSaveFormat()+";"+highscore.getScore()+";"+highscore.getDuration() +System.lineSeparator());

}
IOOperations IO=new IOOperations();
IO.writeFile(fileName, a);
}
catch(Exception e){
    System.out.println(e.getMessage());
    
    System.out.println("loi");
    
}


  }
 

  /**
   * Getter Methode für Highscores einzelner Level.
   *
   * @return Gibt die Highscore-Liste zurueck
   */
  public List<HighscoreEntry> getHighscore() {
    return highscores;
  }

  public void addEntry(HighscoreEntry entry) {
    // TODO Aufgabe 4.2a
    if(highscores.size()<10){
        highscores.add(entry);
        
    }else if(highscores.size()==10){
    int a=highscores.get(highscores.size()-1).compareTo(entry);
        if(a>0){
            highscores.remove(highscores.size()-1);
            highscores.add(entry);
        }else if(a<=0){
            System.out.println("Chicken");
            
            
        }
        
        
    
  }
   Collections.sort(highscores);
}
}
