package tud.ai1.shisen.model;
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
        this.pos=pos;
        id=counter;
        counter=counter+1;
    }
    
    public Token(int value){
            this.value=value;
            state=tud.ai1.shisen.model.TokenState.DEFAULT;
            pos= new org.newdawn.slick.geom.Vector2f (0,0);
            id=counter; 
            counter=counter+1;
    }
    
    @Override
    public int getValue(){
        return value;
    }
    @Override
    public tud.ai1.shisen.model.TokenState getTokenState(){
        return state;
    }
    @Override
    public int getID(){
        return id;
    }
    @Override
    public org.newdawn.slick.geom.Vector2f getPos(){
        return pos;
    }
    @Override
    public String getDisplayValue(){
        return TokenDisplayValueProvider.getInstance().getDisplayValue(value);
    }
    @Override
    public void setTokenState(tud.ai1.shisen.model.TokenState newState){
        state=newState;
        
    }
    @Override
    public void setPos(org.newdawn.slick.geom.Vector2f newPos){
        pos=newPos;
        
    }
    
        
        
        
        
    
}
        
        
        
    
    


    
