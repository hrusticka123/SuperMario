package supermario;
import java.util.*;
import java.io.IOException;
/**
 *
 * @author Marcel-PC
 */
public class SuperMario {

    /**
     * main of the game
     * creates new GamePanel for the game along with the GameFrame and starts it
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        GamePanel gamePanel= new GamePanel();

        Game game = new Game(gamePanel);
        game.start();

        GameFrame gameFrame = new GameFrame(gamePanel);
    }
}
