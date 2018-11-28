package supermario;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Box is a terrain like Block with special feature
 * can be opened by hitting it from the bottom
 * derives from Block
 * @author Marcel-PC
 */
public class Box extends Block {
    /**
     * saved open box image
     */
    private BufferedImage openBox;
    /**
     * content of the box as collectible
     * @see Collectible
     */
    private Collectible boxContent;
    /**
     * true if box was already opened
     */
    public boolean open = false;
    /**
     * Block parameters
     * @see Block
     * @param i grid row
     * @param j grid column
     * @throws IOException 
     */
    public Box(int i, int j) throws IOException {
        super("box", i, j);
        openBox = ImageIO.read(getClass().getResource("../img/open_box.png"));
        //generates random content, 75% chance for coin and 25% chance for flower
        int random = (int)(Math.random() * 4 + 1);
        if (random > 1)
            boxContent = new Collectible(i-1,j,"coin");
        else
            boxContent = new Collectible(i-1,j,"flower");
    }
    
    /**
     * open the box
     * add collectible of the boxContent to the grid in the block up from the box
     */
    public void open()
    {
        image = openBox;
        Map.grid[row-1][col] = boxContent;
        open = true;
    }
}
