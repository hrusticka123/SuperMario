package supermario;
import java.awt.Toolkit;
import javax.swing.JFrame;
/**
 * the frame of the game
 * derives from JFrame
 * encapsulates given GamePanel, setting size or title
 * @see GamePanel
 * @author Marcel-PC
 */
//the main game frame of the game 
public class GameFrame extends JFrame {
    /**
     * sizes of the frame to be set
     */
    public static final int WIDTH=1000, HEIGHT = 500;
    public GameFrame(GamePanel gamePanel){    
        //initialize
        this.setTitle("SuperMario");
        this.setLocation((int)((Toolkit.getDefaultToolkit().getScreenSize().getWidth()-WIDTH)/2),
                        ((int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight()-HEIGHT)/2));		
        this.setSize(WIDTH,HEIGHT);
        this.setVisible(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //add panel to the frame
        this.add(gamePanel);
        gamePanel.grabFocus();
        gamePanel.requestFocusInWindow();
    }
}
