package supermario;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
/**
 * Game control class
 * runs all the actions of the game
 * @author Marcel-PC
 */
public class Game extends Thread {
    	
   /**
    * true if game is running
    */
    private boolean running;
    /**
     * Current GamePanel
     */
    private GamePanel gamePanel;
    /**
     * Current Mario
     */
    private Mario mario;
    /**
     * Current map
     */
    private Map map;
    /**
     * current world number
     */
    private int currentWorld = 1;
    /**
     * list of enemies
     */
    public static List<Enemy> enemies = new ArrayList<Enemy>();
    /**
     * set the components of the game --- Mario, Map and GamePanel
     * @param gamePanel panel to be set
     * @throws IOException 
     */
    public Game(GamePanel gamePanel) throws IOException 
    {	
        //set map
        this.map = new Map();
        this.map.generate(currentWorld);
        //set Mario
        this.mario=new Mario();
        //add Game's Mario to the panel
        this.gamePanel=gamePanel;
        this.gamePanel.addMario(mario);
        //run the game
        this.running=true;
    }

    /**
     * when the thread is ran, run the game as well
     * while running, repeat each tick of the game, then sleep the thread for a short time
     * checks for Mario's bounds, Mario's state, keyboard commands and movements
     */
    @Override
    public void run() {
        while(running){
            //mario is out of bounds, go to new world
            if (mario.out())
            {
                //game ends?
                boolean theEnd = GamePanel.newWorld(++currentWorld);
                mario.reinitialize(theEnd);
                //if game ends, go to first world
                currentWorld = (theEnd) ? 1 : currentWorld;
                try
                {
                    //generate world's map
                    map.generate(currentWorld);
                }
                catch (IOException e)
                {}
            }
            //Mario died
            if (mario.dead())
            {
                //some lives are left?
                boolean stillAlive = GamePanel.looseLife();
                mario.reinitialize(true);
                GamePanel.RollBackScore();
                try
                {
                    //if completely dead, reset to the beggining
                    currentWorld = (stillAlive)? currentWorld : 1;
                    map.generate(currentWorld);
                }
                catch (IOException e)
                {}
            }
            
            //move the enemies
            for (Enemy enemy : enemies)
            {
                enemy.move();
            }
            //check the keyboard commands
            commands();
            //check Mario's state
            mario.checkState();
            //repaint the panel
            gamePanel.repaint();
             
            try {
                Thread.sleep(15);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
     
    /**
     * keyboard commands
     * Possible: Left arrow, right arrow and space to jump
     */
    private void commands() {
        //set of currently pressed keys
        HashSet<Integer> keys = Control.getPressedKeys();
        //according movements
        if(keys.contains(KeyEvent.VK_RIGHT)){
            mario.move(KeyEvent.VK_RIGHT);
        } else if (keys.contains(KeyEvent.VK_LEFT)){
            mario.move(KeyEvent.VK_LEFT);
        }
        //Mario cannot be falling or jumping to jump
        if(keys.contains(KeyEvent.VK_SPACE)) {
            if(!mario.getJumping() && !mario.getFalling()){
                mario.jump();
            }
        }
    }
}
