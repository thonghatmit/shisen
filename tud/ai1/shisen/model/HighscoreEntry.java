package tud.ai1.shisen.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Klasse die einen einzelnen Highscore-Eintrag repraesentiert.
 *
 * @author Andrej Felde, Daniel Stein, Nicklas Behler
 *
 */
public class HighscoreEntry implements Comparable<HighscoreEntry> {

  /**
   * Datum des gespielten Spiels
   */
  private LocalDateTime date;

  /**
   * Gebrauchte Zeit in Millisekunden
   */
  private double duration;

  /**
   * Anzahl des erspielten Scores
   */
  private int score;
  

  /**
   * Separator zum parsen und schreiben der Highscore-Datei
   */
  private static final String separator = ";";

  /**
   * Formatter um String in LocalDateTime zu formatieren
   */
  private static final DateTimeFormatter formatter =
      DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

  /**
   * Konstruktor des HighscoreEntrys erzeugt neue Instanz eines
   * Highscore-Eintrags.
   *
   * @param date Datum des gespielten Spiels.
   * @param score Erspielter Score.
   * @param duration Gebrauchte Zeit in Millisekunden.
   */
  public HighscoreEntry(LocalDateTime date, int score, double duration) {
    // TODO Aufgabe 4.1b
    if(date==null){
        throw new IllegalArgumentException ("date ist leer");
}else if(score>1000){
    throw new IllegalArgumentException ("score ist mehr als 1000");
}else if(duration<0){
    throw new IllegalArgumentException ("duration ist negativ");
}else{
    this.date=date;
    this.score=score;
    this.duration=duration;
    
    
}
  }

  public HighscoreEntry(String data) {
    // TODO Aufgabe 4.1c
    String[]datetime=data.split(";");
    if(datetime.length>3){
        throw new IllegalArgumentException("IllegalArgumentException");
        
    }
    LocalDateTime date=LocalDateTime.parse(datetime[0], formatter);
    int score=Integer.parseInt(datetime[1]);
    double duration=Double.parseDouble(datetime[2]);
    validate(date,score,duration);
    
    this.date=LocalDateTime.parse(datetime[0], formatter);
    this.score=Integer.parseInt(datetime[1]);
    this.duration=Double.parseDouble(datetime[2]);
    
    

    
    
}

  public void validate(LocalDateTime date, int score, double duration) {
    // TODO Aufgabe 4.1a
    if(date==null){
        throw new IllegalArgumentException ("date ist leer");
}else if(score>1000){
    throw new IllegalArgumentException ("score ist mehr als 1000");
}else if(duration<0){
    throw new IllegalArgumentException ("duration ist negativ");
}
}

  @Override
  public boolean equals(Object obj) {
    // TODO Aufgabe 4.1d
    if(obj!=null&& obj instanceof HighscoreEntry){
        return true;
    }else{
        
    return false;
  }
}

  /**
   * Getter fuer date als String.
   * 
   * @return String - Spieldatum
   */
  public String getDate() {
    return String.format("%02d.%02d. %02d:%02d", date.getDayOfMonth(), date.getMonthValue(),
        date.getHour(), date.getMinute());
  }

  /**
   * Getter fuer die score.
   * 
   * @return Erreichte Punktzahl
   */
  public int getScore() {
    return score;
  }

  /**
   * Getter fuer die Dauer.
   * 
   * @return Gebrauchte Zeit in Sekunden
   */
  public double getDuration() {
    return duration;
  }

  /**
   * Getter um Format zum Speichern zu kriegen.
   * 
   * @return String dd.MM.yyyy hh:mm
   */
  public String dateToSaveFormat() {
    return String.format("%02d.%02d.%02d %02d:%02d", date.getDayOfMonth(), date.getMonthValue(),
        date.getYear(), date.getHour(), date.getMinute());
  }

  @Override
  public int compareTo(HighscoreEntry other) {
    // TODO Aufgabe 4.1e
    HighscoreEntry highscore= new HighscoreEntry(date,score,duration);
    if(highscore.getScore()<other.getScore()){
        return 2;
        
    }else if(highscore.getScore()>other.getScore()){
        
    return -1;
  }else if(highscore.getScore()==other.getScore()){
      if(highscore.getDuration()<other.getDuration()){
          return 2;
        }else if(highscore.getScore()>other.getScore()){
            return -1;
        }else{
            return 0;
        }
    }
    return compareTo(other);
}

  /**
   * Diese Methode gibt die String-Repraesentation des Objekts zurueck.
   * 
   * @return String-Repraesentation
   */
  @Override
  public String toString() {
    return dateToSaveFormat() + separator + score + separator + duration;
  }
  
  
}
