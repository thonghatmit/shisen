package tud.ai1.shisen.model;


import tud.ai1.shisen.model.Token;
import tud.ai1.shisen.model.IToken;
import org.newdawn.slick.geom.Vector2f;
import tud.ai1.shisen.util.IOOperations;
import java.util.List;

import tud.ai1.shisen.util.Consts;
import tud.ai1.shisen.util.PathFinder;

/**
 * Diese Klasse repraesentiert das Spielfeld.
 * 
 * @author Nicklas Behler, Sebastian C, Lennart Fedler, Niklas Grimm, Robert Jakobi, Max Kratz,
 *         Niklas Vogel
 *
 */
public class Grid implements IGrid {

  private int waitTime = 1000;
  private TokenState destiny;
  private long currTime;
  private boolean timerActive = false;
  private List<IToken> list;
  private static int score;
  private static IToken[][] grid;
  private IToken selectedTokenOne = null;
  private IToken selectedTokenTwo = null;
  
  /*
   * TODO: Aufgabe 3a
   */

  /**
   * Konstruktor, der ein zufaelliges Grid zum Testen erzeugt.
   */
  public Grid() {
    final IToken[][] demoGrid = new Token[10][10];
    for (int x = 0; x < demoGrid.length; x++) {
      for (int y = 0; y < demoGrid[x].length; y++) {
        demoGrid[x][y] = new Token(1);
      }
    }
    grid = demoGrid;
    this.score = 0; 
  }

  /*
   * TODO: Aufgabe 3b
   */
  public Grid(String path){
      grid = parseMap(path);
      this.score = Consts.START_POINTS;
      this.fillTokenPositions();
    }
  

  /*
   * TODO: Aufgabe 3c
   */
  @Override
  public IToken getTokenAt(int x, int y){
      if(0 > x || x >= grid.length || 0 > y || y >= grid[0].length){
          return null;
        }else{
            return grid[x][y];
        }
    }

  /*
   * TODO: Aufgabe 3d
   */
  @Override
  public IToken[][] getGrid(){
     return grid ;
    }
    @Override
    public IToken[] getActiveTokens(){
       IToken[] selectedToken = new IToken[2];
       selectedToken[0]=selectedTokenOne;
       selectedToken[1]=selectedTokenTwo;
       return selectedToken;
    }

  /*
   * TODO: Aufgabe 3e
   */
  @Override
  public boolean bothClicked(){
    if(this.selectedTokenOne != null && this.selectedTokenTwo != null
    && selectedTokenOne.getTokenState() == TokenState.CLICKED
    && selectedTokenTwo.getTokenState() == TokenState.CLICKED){
        return true;
    }else{
        return false;
    }
}
  /*
   * TODO: Aufgabe 3f
   */
  
public void deselectToken(IToken token){
    if(token == null) return;
    token.setTokenState(TokenState.DEFAULT);
    if (this.selectedTokenTwo != null) {
        this.selectedTokenTwo = null;
    }
    
    if (this.selectedTokenOne != null) {
        this.selectedTokenOne = null; 
    }
    
}

@Override
public void deselectTokens(){
    this.deselectToken(this.selectedTokenOne);
    this.deselectToken(this.selectedTokenTwo);
}
  /*
   * TODO: Aufgabe 3g
   */
public static boolean isSolved(){
    for(int i=0;i<20;i++){
        for(int y=0;y<10;y++){
            if(grid[i][y].getTokenState()==TokenState.SOLVED){
                continue;
    }else{   
        return false;
    }
    
}
}
return true;
}

  /*
   * TODO: Aufgabe 3h
   */
  public Token[][] parseMap(String path){
      Token[][] field = new Token[20][10];
      IOOperations IO = new IOOperations();
      String mapString = IO.readFile(path);
      mapString = mapString.replaceAll(System.lineSeparator(),",");
      String[] worte = mapString.split(",");
     
      for(int i=0 ; i < 10 ; i++){
          field[0][i] = new Token(-1,TokenState.SOLVED,new Vector2f(0,i));
          System.out.println(field[0][i]);
          field[19][i] = new Token(-1,TokenState.SOLVED,new Vector2f(19,i));
    }
    
      for(int i=0 ; i < 20 ; i++){
          field[i][0] = new Token(-1,TokenState.SOLVED,new Vector2f(i,0));
          field[i][9] = new Token(-1,TokenState.SOLVED,new Vector2f(i,9));
    } 
    
    int count=0;
     for(int x=1;x<19;x++){
         for(int y=1;y<9;y++){
             field[x][y] = new Token(Integer.parseInt(worte[count]),TokenState.DEFAULT,new Vector2f(x,y));
             count++;
            }
        }
      return field;
    }

  /**
   * Updated den Score um incr. Sollte der Score anschliessend negativ sein, so wird er bis auf 0
   * dekrementiert.
   * 
   * @param incr Zahl um die Score erhoeht / erniedrigt werden soll.
   */
  public void updateScore(final int incr) {
    if (score + incr >= 0) {
      score += incr;
    } else {
      score = 0;
    }
  }

  /**
   * Getter fuer score.
   *
   * @return Aktueller score.
   */
  public int getScore() {
    return score;
  }

  /**
   * Teile jedem Token seine Position im Array mit.
   */
  private void fillTokenPositions() {
    for (int x = 0; x < grid.length; x++) {
      for (int y = 0; y < grid[0].length; y++) {
        grid[x][y].setPos(new Vector2f(x, y));
      }
    }
  }

  /**
   * Waehle einen Token auf dem Spielfeld aus und loese diesen falls moeglich.
   * 
   * @param Token Angeklickter Token.
   */
  @Override
  public void selectToken(final IToken token) {
    if (this.selectedTokenOne == null) {
      this.selectedTokenOne = token;
      selectedTokenOne.setTokenState(TokenState.CLICKED);
    } else if (this.selectedTokenTwo == null) {
      this.selectedTokenTwo = token;
      selectedTokenTwo.setTokenState(TokenState.CLICKED);
      this.list = PathFinder.getInstance().findPath(this, (int) this.selectedTokenOne.getPos().x,
          (int) this.selectedTokenOne.getPos().y, (int) this.selectedTokenTwo.getPos().x,
          (int) this.selectedTokenTwo.getPos().y);
      if (this.list == null || this.list.size() == 0 || !this.selectedTokenOne.getDisplayValue()
          .equals(this.selectedTokenTwo.getDisplayValue())) {
        this.selectedTokenOne.setTokenState(TokenState.WRONG);
        this.selectedTokenTwo.setTokenState(TokenState.WRONG);
        this.updateScore(Consts.DECREASE_SCORE);
        this.startTimer(Consts.DISPLAY_WRONG_TIME, TokenState.DEFAULT);
        System.out.println("b"); 
      } else {
        for (final IToken tok : this.list) {
          tok.setTokenState(TokenState.CLICKED);
        }
        this.updateScore(Consts.GAIN_SCORE);
        this.startTimer(Consts.DISPLAY_WRONG_TIME, TokenState.SOLVED);
      }
    }
  }

  /**
   * Startet einen Timer (Genutzt fuer Anzeigedauer bei falscher / richtiger Auswahl von zwei
   * Tokens).
   * 
   * @param waitTime Zeit in Sekunden, die gewartet werden soll.
   * @param dest Ziel Tokenstate.
   */
  private void startTimer(final double waitTime, final TokenState dest) {
    this.timerActive = true;
    this.currTime = System.currentTimeMillis();
    this.waitTime = (int) waitTime * 1000;
    this.destiny = dest;
  }

  /**
   * Prueft ob Anzeigezeit bei falscher/richtiger Auswahl bereits ueberschritten ist.
   * Falls ja wird der entsprechende Code ausgefuehrt.
   */
  @Override
  public void getTimeOver() {
    if (this.timerActive) {
      if (System.currentTimeMillis() - this.currTime > this.waitTime) {
        try {
          if (this.list != null) {
            for (final IToken tok : this.list) {
              tok.setTokenState(TokenState.SOLVED);
            }
          }
          this.selectedTokenOne.setTokenState(this.destiny);
          this.selectedTokenTwo.setTokenState(this.destiny);
          this.selectedTokenOne = null;
          this.selectedTokenTwo = null;
          this.timerActive = false;
        } catch (final Exception e) {
          e.printStackTrace();
        }
      }
    }
  }
}
