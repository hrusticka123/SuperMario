package supermario;
import com.sun.javafx.scene.traversal.Direction;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Bird is a flying enemy
 * turns around at terrain
 * derives from Enemy class
 * @author Marcel-PC
 */
public class Bird extends Enemy {
    /**
     * maximum movement ticks
     */
    private final int MAX_MOVE = 150;
    /**
     * current move count
     * @see #MAX_MOVE
     */
    private int moveCount = 0;
    /**
     * image of bird when going left
     */
    private BufferedImage imgLeft;
    /**
     * Enemy's parameters
     * @see Enemy
     * @param i grid row
     * @param j grid column
     * @throws IOException 
     */
    public Bird(int i, int j) throws IOException {
        super(i, j, "bird");
        imgLeft = ImageIO.read(getClass().getResource("../img/bird_left.png"));
    }
    
    /**
     * Bird's specific movement
     */
    @Override
    public void move()
    {
        if (dir == Direction.RIGHT)
        {
            x += DISPLACEMENT;
            moveCount++;
            //stops at max or collision
            if (collision() || moveCount == MAX_MOVE)
            {
                dir = Direction.LEFT;
                x -= DISPLACEMENT;
            }
        }
        else
        {
            x -= DISPLACEMENT;
            moveCount--;
            if (collision() || moveCount == 0)
            {
                dir = Direction.RIGHT;
                x += DISPLACEMENT;
            }
        }
    }
    
    /**
     * Bird's specific collision
     * @return true if collides
     */
    @Override
    protected boolean collision()
    {
        int row = y/Block.SIZE;
        int col = x/Block.SIZE;
        int dirVal = (dir == Direction.RIGHT) ? 1 : 0;
        if (col == Map.COLS - 1)
            return true;
        return isBlocked(Map.grid[row][col+dirVal]);
    }
    
    /**
     * get image based on dir
     * @return bird image
     */
    @Override
    public BufferedImage getImage()
    {
        return (dir == Direction.LEFT) ? imgLeft : image;
    }
}
