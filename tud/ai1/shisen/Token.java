package tud.ai1.shisen;
import tud.ai1.shisen.util.TokenDisplayValueProvider;
import tud.ai1.shisen.model.IToken;
/**
 * Write a description of class Token here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Token implements IToken
{
    private static int counter =0;
    private final int id;
    private tud.ai1.shisen.model.TokenState state;
    private final int value;
    private org.newdawn.slick.geom.Vector2f pos;
    public Token (int value, tud.ai1.shisen.model.TokenState state, org.newdawn.slick.geom.Vector2f pos){
        this.value=value;
        this.state=state;
        counter=counter+1;
        this.pos=pos;
        id=counter;
        
        }
        public Token(int value){
            this.value=value;
            state=tud.ai1.shisen.model.TokenState.DEFAULT;
            pos= new org.newdawn.slick.geom.Vector2f (0,0);
            counter=counter+1;
            id=counter; 
    }
    
    public int getValue(){
        return value;
    }
    public tud.ai1.shisen.model.TokenState getTokenState(){
        return state;
    }
    public int getID(){
        return id;
    }
    public org.newdawn.slick.geom.Vector2f getPos(){
        return pos;
    }
    public String getDisplayValue(){
        TokenDisplayValueProvider provide= TokenDisplayValueProvider.getInstance();
        return provide.getDisplayValue(value);
        
        
        
    }
    public void setTokenState(tud.ai1.shisen.model.TokenState newState){
        state=newState;
        
    }
    public void setPos(org.newdawn.slick.geom.Vector2f newPos){
        pos=newPos;
        
    }
    
        
        
        
        
    
}
        
        
        
    
    


    
