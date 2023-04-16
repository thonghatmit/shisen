package tud.ai1.shisen.util;

import java.util.List;
import java.util.Random;
import tud.ai1.shisen.model.Token;
import tud.ai1.shisen.model.Grid;
import tud.ai1.shisen.model.IToken;
import tud.ai1.shisen.model.TokenState;
import java.util.*;
import java.util.Random;

/**
 * 
 * Diese Klasse repraesentiert die Cheats.
 * 
 * @author Niklas Vogel
 *
 */
public class Cheats {

  /**
   * Diese Klasse soll nicht initialisierbar sein, da sie nur statische
   * Methoden enthaelt.
   */
  private Cheats() {}

  public static void findPartner(final Grid grid) {
    // TODO Aufgabe 5e
    IToken [] selectedToken = grid.getActiveTokens();
    if (selectedToken[1] != null) 
        return; 
        
    if(isCheatPossible(grid, Consts.CHEAT_FIND_PARTNER)){
        boolean solved = false;
        if(selectedToken[0].getTokenState()==TokenState.CLICKED){
            List<IToken>gleicheTyp = findTokensWithType(selectedToken[0], grid);
            for(int i=0; i < gleicheTyp.size();i++) {
                if(solvable(selectedToken[0], gleicheTyp.get(i) ,grid)){
                    grid.selectToken(gleicheTyp.get(i));
                    solved = true;
                    break;
                }
            }
            if(!solved){
                grid.deselectToken(selectedToken[0]); 
            }
            
        } 
        grid.updateScore(Consts.DECREASE_SCORE); 
        grid.updateScore(Consts.CHEAT_COST_FIND_PARTNER); 
  }
}
  /**
   * Dieser Cheat markiert einen Token, der derzeit loesbar ist.
   *
   * @param grid Grid, auf dem ein loesbarer Token markiert werden soll.
   */
  public static void useHint(final Grid grid) {
    // Wenn bereits zwei Tokens angeklickt sind, breche Cheat ab
    if (grid.bothClicked())
      return;
    if (!isCheatPossible(grid, Consts.CHEAT_HINT))
      return;
    final IToken[] tok = findValidTokens(grid);
    if (tok == null) {
      System.out.println("Cheat nicht mehr moeglich");
      return;

    }
    grid.deselectTokens();

    grid.selectToken(tok[0]);
    System.out.println("Cheat: useHint");
    grid.updateScore(Consts.CHEAT_COST_HINT);
  }

  public static void solvePair(final Grid grid) {
    // TODO Aufgabe 5d
    IToken[] paar=new IToken[2];
    if(grid.getScore()>=20){
        paar=findValidTokens(grid);
        paar[0].setTokenState(TokenState.SOLVED);
        paar[1].setTokenState(TokenState.SOLVED);
        grid.updateScore(-20);
    }else{
        System.out.println("ga, deo du diem");
    
  }
}


  /**
   * Findet ein Paar aus derzeit loesbaren Tokens.
   * 
   * @param grid Spielfeld, auf dem das loesbare Paar gefunden werden soll.
   * @return Loesbares Tokenpaar, null falls kein loesbares Paar mehr vorhanden ist
   */
  private static IToken[] findValidTokens(final Grid grid) {
    final IToken[][] board = grid.getGrid();
    IToken token = null;
    Random r = new Random();
    // Offset fuer die Startposition der Suche im Grid-Array
    int n1 = r.nextInt(board.length);
    int n2 = r.nextInt(board[0].length);
    for (int x = 0; x < board.length; x++) {
      for (int y = 0; y < board[0].length; y++) {
        token = board[(x + n1) % board.length][(y + n2) % board[0].length];
        if (token.getTokenState() != TokenState.DEFAULT)
          continue;
        // Teste alle theoretich moeglichen Partner
        for (IToken partner : findTokensWithType(token, grid)) {
          if (solvable(token, partner, grid)&&token.getID()!=partner.getID()) {
            IToken[] ret = {token, partner};
            return ret;
          }
        }
      }
    }
    return null;
  }

  /**
   * Bestimmt ob zwei Tokens zueinander passen (loesbar sind).
   * 
   * @param token1 Token 1
   * @param token2 Token 2
   * @param grid Spielfeld der Tokens
   * @return Boolean: Passen Tokens zusammen?
   */
  private static boolean solvable(IToken token1, IToken token2, Grid grid) {
    if (token1.getTokenState() == TokenState.SOLVED || token2.getTokenState() == TokenState.SOLVED)
      return false;
    // Set TokenStates to Clicked so Search algorithm can use them
    TokenState pre1 = token1.getTokenState();
    TokenState pre2 = token2.getTokenState();
    token1.setTokenState(TokenState.CLICKED);
    token2.setTokenState(TokenState.CLICKED);
    List<IToken> list = PathFinder.getInstance().findPath(grid, (int) token1.getPos().x,
        (int) token1.getPos().y, (int) token2.getPos().x, (int) token2.getPos().y);
    // After algorithm has finished, reset TokenStates
    token1.setTokenState(pre1);
    token2.setTokenState(pre2);
    if (list != null && list.size() > 0)
      return true;
    return false;
  }


  private static List<IToken> findTokensWithType(final IToken token, final Grid grid) {
    // TODO Aufgabe 5c
    
    ArrayList<IToken>gleicheTyp =new ArrayList<IToken>();
    int a=token.getValue();
    for (int x = 0; x < 20; x++) {
      for (int y = 0; y < 10; y++) {
    IToken token1 ;
    token1=grid.getTokenAt(x, y);
    if(a==token1.getValue()&&token1.getTokenState()==TokenState.DEFAULT){
    gleicheTyp.add(token1);
}



    
    

    
  }
}
Collections.shuffle(gleicheTyp);
return gleicheTyp;
}

  private static List<IToken> shuffle(List<IToken> list) {
    // TODO Aufgabe 5b
    Collections.shuffle(list);
    
    return list;
  }

  private static boolean isCheatPossible(final Grid grid, final int cheatID) {
    // TODO Aufgabe 5a
    if(getCheatCost(cheatID) < grid.getScore()){
        return true;
    }else{
    return false;
}
  }

  /**
   * Liefert die Cheatkosten fuer den uebergebenen Cheat zurueck.
   *
   * @param cheatID ID des Cheats
   * @return Cheatkosten
   */
  private static int getCheatCost(final int cheatID) {
    switch (cheatID) {
      case Consts.CHEAT_HINT:
        return Consts.CHEAT_COST_HINT;
      case Consts.CHEAT_SOLVE_PAIR:
        return Consts.CHEAT_COST_SOLVE_PAIR;
      case Consts.CHEAT_FIND_PARTNER:
        return Consts.CHEAT_COST_FIND_PARTNER;
      default:
        System.out.println("Kein Cheat mit dieser ID vorhanden");
        return -1;
    }
  }
}
