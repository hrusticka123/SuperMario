package supermario;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
/**
 * Common class for all types of blocks in the grid
 * @author Marcel-PC
 */
public class Block {
    /**
     * types of possible blocks
     */
    public enum BlockType {Coin, Flower, Box, Goomba, Obstacle, Bird};
    /**
     * coordinates of the block
     * used for moving blocks, such as enemies
     */
    public int x,y;
    /**
     * size of one block
     * all objects in the game (including Mario) are squares of this size
     */
    public static final int SIZE = 50;
    /**
     * row of the block in the grid
     */
    protected int row;
    /**
     * column of the block in the grid
     */
    protected int col;
    /**
     * image of the block
     */
    protected BufferedImage image;
    /**
     * 
     * @param imgName name of the image as saved in ../img/ directory
     * @param i row in the grid
     * @param j column in the grid
     * @throws IOException 
     */
    public Block(String imgName,int i, int j)throws IOException {
        this.row=i;
        this.col=j;
        image = ImageIO.read(getClass().getResource("../img/"+imgName+".png"));
        x=col*SIZE;
        y=row*SIZE;
    }
     
    /**
     * getter for image
     * @return image
     */
    Image getImage()
    {
        return image;
    }
}
