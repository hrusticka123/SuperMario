package supermario;

import com.sun.javafx.scene.traversal.Direction;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * common abstract class for all kinds of implemented enemies
 * evaluates their movement and state
 * derives from Block
 * @author Marcel-PC
 */
public abstract class Enemy extends Block {
    /**
     * movement speed
     */
    protected final int DISPLACEMENT = 2;
    /**
     * direction of movement
     */
    protected Direction dir = Direction.RIGHT;

    /**
     * derives from Block, needs row, column and image name
     * @param i row of the block in the grid
     * @param j column of the block in the grid
     * @param imgName image of the block
     * @throws IOException 
     */
    public Enemy(int i, int j, String imgName) throws IOException {
        super(imgName,i,j);
        x = j*Block.SIZE;
        y = i*Block.SIZE;
    }
    
    /**
     * kill the enemy, remove it and increase score for it
     */
    public void die()
    {
        Game.enemies.remove(this);
        GamePanel.increaseScore(100);
    }
    
    /**
     * to be implemented by children for enemy movement
     */
    public abstract void move();
    /**
     * to be implemented by children for enemy collision detection
     * @return true if collision detected
     */
    protected abstract boolean collision();
    /**
     * front block blocks enemy
     * @param block to be checked
     * @return true if it really blocks
     */
    protected boolean isBlocked(Block block) {
        return block != null && !(block instanceof Collectible);
    }
    /**
     * 
     * @return image of the block
     */
    public BufferedImage getImage()
    {
        return image;
    }
}
