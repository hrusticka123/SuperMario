package supermario;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
/**
 * Map to be generated
 * grid of Blocks ROWS * COLS created form .txt file
 * @author Marcel-PC
 */
public class Map {
    public Map(){
        grid = new Block[ROWS][COLS];
    }
    
    /**
     * generate the map of given worldNumber
     * @param worldNumber to be created
     * @throws IOException 
     */
    public void generate(int worldNumber) throws IOException {
        //clear enemies
        Game.enemies.clear();
        //find file according to the worldNumber
        InputStream is = this.getClass().getResourceAsStream("../levels/level"+worldNumber+".txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String line = "";
        String[] blocks=new String[COLS];
        int i=0;
        //read file line by line
        while((line=reader.readLine())!=null){
            //split by space
            blocks=line.split(" ");
            //create block for file values
            for(int j=0; j<COLS; j++){
                if(!blocks[j].equalsIgnoreCase("empty")){
                    switch(blocks[j])
                    {
                        case "floor":
                        case "wall":
                        case "tunnel":
                            grid[i][j]= new Block(blocks[j],i,j);
                            break;
                        case "goomba":
                            Game.enemies.add(new Goomba(i,j));
                            grid[i][j]=null;
                            break;
                        case "bird":
                            Game.enemies.add(new Bird(i,j));
                            grid[i][j]=null;
                            break;
                        case "coin":
                            grid[i][j]= new Collectible(i,j,"coin");
                            break;
                        case "flower":
                            grid[i][j]= new Collectible(i,j,"flower");
                            break;
                        case "box":
                            grid[i][j]= new Box(i,j);
                            break;
                    }
                } else {
                        grid[i][j]=null;
                }
            }
            i++;
            }
        }

    /**
     * grid of blocks to be drawn
     */
    public static Block[][] grid;
    /**
     * number of rows in grid
     */
    public static final int ROWS= 10;
    /**
     * number of columns in grid
     */
    public static final int COLS= 20;
    /**
     * maximum number of levels provided
     */
    public static final int MAX = 4;
}
