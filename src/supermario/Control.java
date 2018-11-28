package supermario;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
/**
 * control of the keyboard commands
 * saves all pressed keys and sends them for evaluation
 * @author Marcel-PC
 */
public class Control implements KeyListener{ 	
    private static HashSet<Integer> pressedKeys;

    public Control(){
        pressedKeys = new HashSet<Integer>();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        pressedKeys.add(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        pressedKeys.remove(e.getKeyCode());
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    public static HashSet<Integer> getPressedKeys(){
        return pressedKeys;
    }
}
