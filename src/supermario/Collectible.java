package supermario;

import java.io.IOException;

/**
 * Collectible blocks, items that help in the game
 * inheritance not implemented, two implemented collectible types are different only in one function
 * derives from Block
 * @author Marcel-PC
 */
public class Collectible extends Block{
    /**
     * type of the collectible --- flower or coin
     */
    Block.BlockType type;
    /**
     * Block parameters
     * @see Block
     * @param i grid row
     * @param j grid column
     * @param type
     * @throws IOException 
     */
    public Collectible(int i, int j, String type) throws IOException {
        super(type,i,j);  
        if (type == "coin")
            this.type = Block.BlockType.Coin;
        else
            this.type = Block.BlockType.Flower;
    }
    /**
     * take item
     * depending on the type, increase score
     */
    void take()
    {
        Map.grid[row][col] = null;
        switch (type)
        {
            case Flower:
                GamePanel.increaseScore(50);
                
                break;
            case Coin:
                GamePanel.increaseScore(100);
                break;
        }
    }
}
