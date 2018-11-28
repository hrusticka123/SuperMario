package supermario;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.JLabel;
/**
 * GamePanel is place where the graphics of the game are drawn
 * derives from JPanel
 * @author Marcel-PC
 */
public class GamePanel extends JPanel {
    
    /**
     * 
     */
    private BufferedImage marioImg = null;
    /**
     * keyboard control of the panel
     */
    private Control control;
    /**
     * Mario character
     */
    private Mario mario;
    /**
     * stats labels and messages
     */
    private static JLabel worldLabel, scoreLabel, livesLabel, message;
    /**
     * stats values
     */
    private static int score = 0, lives = 3, currentLevelScore = 0;

    /**
     * constructor creates new panel
     * sets size, background, layout
     * adds labels, keyboard controls and Mario
     * @throws IOException
     */
    public GamePanel()throws IOException{
        //initialize panel
        this.setSize(GameFrame.WIDTH, GameFrame.HEIGHT);
        this.setBackground(Color.CYAN);
        this.setLayout(null);
        this.setDoubleBuffered(true);

        //add panel items
        control = new Control();
        this.addKeyListener(control);
        worldLabel = new JLabel("World: 1");
        scoreLabel = new JLabel("Score: " + score);
        livesLabel = new JLabel("Lives: "+ lives);
        message = new JLabel("GAME OVER");
        this.add(worldLabel);
        this.add(scoreLabel);
        this.add(livesLabel);
        this.add(message);
        message.setBounds(GameFrame.WIDTH/2 - 200, 50, 600, 40);
        message.setFont(new Font("Arial", Font.PLAIN, 50));
        message.setVisible(false);
        worldLabel.setBounds(100, 10, 200, 50);
        worldLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        scoreLabel.setBounds(300, 10, 200, 50);
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        livesLabel.setBounds(500, 10, 200, 50);
        livesLabel.setFont(new Font("Arial", Font.PLAIN, 20));
    }

    /**
     * resets stat values and labels to default
     */
    private static void resetStats()
    {
        currentLevelScore = 0;
        score = 0;
        lives = 3;
        worldLabel.setText("World: 1");
        scoreLabel.setText("Score: " + score);
        livesLabel.setText("Lives: "+ lives);
    }

    /**
     * paints all the components at each tick
     * @param g graphics
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2=(Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        //draw grid
        for(int i=0; i<Map.ROWS; i++){
            for(int j=0; j<Map.COLS; j++){
                if(Map.grid[i][j]!=null) {
                    g2.drawImage(Map.grid[i][j].getImage(), j*Block.SIZE, i*Block.SIZE, null);
                }
            }
        }

        //draw enemies
        for(Enemy enemy : Game.enemies)
        {
            g2.drawImage(enemy.getImage(), enemy.x, enemy.y, null);
        }
        
        try
        {
            //draw Mario
            g2.drawImage(mario.getImage(),mario.getX(),mario.getY(),null);
        }
        catch(IOException e)
        {}

    }

    /**
     * Add Mario the the panel
     * @param mario
     */
    public void addMario(Mario mario) {
        this.mario = mario;
    }

    /**
     * increase score by given value
     * @param add the value of score
     */
    static void increaseScore(int add) {
        score += add;
        scoreLabel.setText("Score: " + score);
    }

    /**
     * new world entered
     * @param levelNumber number of the world
     * @return true if game was completed, else write new level to label
     */
    static boolean newWorld(int levelNumber) {
        if (levelNumber > Map.MAX)
        {
            showMessage("CONGRATULATIONS");
            resetStats();
            return true;
        }
        else
        {
            worldLabel.setText("World: " + levelNumber);
            currentLevelScore = score;
            return false;
        }
    }

    /**
     * loose one life
     * if all lives are lost, display GAME OVER and reset
     * @see #resetStats()
     * @return true if game continues, false if game is over
     */
    static boolean looseLife() {
        lives--;
        livesLabel.setText("Lives: " + lives);
        if (lives == 0)
        {
            showMessage("GAME OVER");
            resetStats();
            return false;
        }
        return true;
    }

    /**
     * if player died, loose all score he got in the current level
     */
    static void RollBackScore() {
        score = currentLevelScore;
        scoreLabel.setText("Score: " + score);
    }

    /**
     * display message in message label
     * @param msg message to be displayed
     */
    static void showMessage(String msg)
    {
        message.setVisible(true);
        message.setText(msg);
        try {
        Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        message.setVisible(false);
    }
}
