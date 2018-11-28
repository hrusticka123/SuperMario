package supermario;

import com.sun.javafx.scene.traversal.Direction;
import java.io.IOException;

/**
 * Goomba is ground enemy
 * turns around at falls and terrain
 * derives from Enemy class
 * @author Marcel-PC
 */
public class Goomba extends Enemy{
    /**
     * Enemy's parameters
     * @see Enemy
     * @param i grid row
     * @param j grid column
     * @throws IOException 
     */
    public Goomba(int i, int j) throws IOException {
        super(i, j, "goomba");
    }
    
    /**
     * Goomba's movement
     */
    @Override
    public void move()
    {
        if (dir == Direction.RIGHT)
        {
            x += DISPLACEMENT;
            if (collision())
            {
                dir = Direction.LEFT;
                x -= DISPLACEMENT;
            }
        }
        else
        {
            x -= DISPLACEMENT;
            if (collision())
            {
                dir = Direction.RIGHT;
                x += DISPLACEMENT;
            }
        }
    }
    
    /**
     * specific collision conditions
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
        //front block is terrain or has hole
        return isBlocked(Map.grid[row][col+dirVal]) || !isBlocked(Map.grid[row+1][col+dirVal]);
    }
}
