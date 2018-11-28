package supermario;
import com.sun.javafx.scene.traversal.Direction;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Character class,
 * takes care of the Mario's attributes and movements
 * @author Marcel-PC
 */
public class Mario {
    /**
     *current X position of Mario
     */
    public int X = START_X;
    /**
     *current Y position of Mario
     */
    public int Y = START_Y;  
    
    /**
     * length of one movement tick
     */
    private static final int DISPLACEMENT=5;

    /**
     * maximum jump count
     */
    private static int JUMP_ROOF=30;
    /**
     * start X position
     */
    private static final int START_X=0;
    /**
     * start Y position of Mario
     */
    private static final int START_Y = GameFrame.HEIGHT - Block.SIZE - 2*Block.SIZE;
    
    /**
     * if Mario died
     */
    private boolean dead = false;
    /**
     * if Mario is temporarily invincible
     */
    private boolean invincible = false;
    /**
     * object that Mario currently collides with
     */
    private Block colidedObject = null;
    /**
     * direction of movement
     */
    private Direction dir = Direction.RIGHT;
    /**
     * 
     * @return current column where Mario appears in grid 
     */
    private int col()
    {
        return X/Block.SIZE;
    }
    
    /**
     * 
     * @return current row where Mario appears in grid 
     */
    private int row()
    {
        return Y/Block.SIZE;
    }
    
    /**
     * if Mario is currently jumping
     */
    private boolean jumping = false;
    /**
     * if Mario is currently falling
     */
    private boolean falling = false;
    /**
     * current jump count, maximum 
     * @see #JUMP_ROOF
     */
    private int jumpCount = 0;
    
    /**
     * checks if Mario does not collides with the block of given coordinates
     * @param row row of block
     * @param col column of block
     * @return intersected block
     */
    private Block intersect(int row, int col)
    {
        //check for bounds
        row = (row < 0) ? 0 : (row >= Map.ROWS ? Map.ROWS -1 : row);
        col = (col < 0) ? 0 : (col >= Map.COLS ? Map.COLS -1 : col);
        //create bounding rectangles
        Rectangle marioRec = new Rectangle(X,Y,Block.SIZE,Block.SIZE);
        Rectangle blockRec = new Rectangle(col*Block.SIZE,row*Block.SIZE,Block.SIZE,Block.SIZE);
        //check for intersection
        if (Map.grid[row][col] != null && marioRec.intersects(blockRec))
        {
            return Map.grid[row][col];
        }
        else
        {
            return null;
        }
    }
    
    /**
     * checks for any collision nearby
     * takes all surrounding blocks in the grid, looks for non-empty
     * if there is an object and it intersects, return it
     * also checks for possible enemy collisions
     * @return colliding block
     */
    private Block collision() {
        if (intersect(row(),col()) != null) return intersect(row(),col());
        else if (intersect(row(),col()+1) != null) return intersect(row(),col()+1);
        else if (intersect(row(),col()-1) != null) return intersect(row(),col()-1);
        else if (intersect(row()+1,col()) != null) return intersect(row()+1,col());
        else if (intersect(row()+1,col()+1) != null) return intersect(row()+1,col()+1);
        else if (intersect(row()+1,col()-1) != null) return intersect(row()+1,col()-1);
        else if (intersect(row()-1,col()) != null) return intersect(row()-1,col());
        else if (intersect(row()-1,col()+1) != null) return intersect(row()-1,col()+1);
        else if (intersect(row()-1,col()-1) != null) return intersect(row()-1,col()-1);
        else //check for enemies nearby
        {            
            Rectangle marioRec = new Rectangle(X,Y,Block.SIZE,Block.SIZE);
            for(Enemy enemy : Game.enemies)
            {
                Rectangle enemyRec = new Rectangle(enemy.x,enemy.y,Block.SIZE,Block.SIZE);
                if (marioRec.intersects(enemyRec))
                {
                    return enemy;
                }
            }
            return null;
        }
    }
    
    /**
     *
     * @throws IOException
     */
    public Mario()  throws IOException {
           
    }

    /**
     * jumping getter
     * @return jumping
     */
    public boolean getJumping()
    {
        return jumping;
    }
    /**
     * falling getter
     * @return falling
     */
    public boolean getFalling() {
        return falling;
    }
    /**
     * X position getter
     * @return X
     */
    public int getX()
    {
        return X;
    }
    /**
     * Y position getter
     * @return Y
     */
    public int getY()
    {
        return Y;
    }
    
    /**
     * tells Mario where to move
     * @param direction left or right
     */
    public void move(int direction) {
        switch (direction) {
            //move left
            case KeyEvent.VK_LEFT:
                X-=DISPLACEMENT;
                //bound check
                 if(X<=0){
                      X=0;
                }
                dir = Direction.LEFT;
                break;      
            case KeyEvent.VK_RIGHT:
                X+=DISPLACEMENT;
                dir = Direction.RIGHT;
                break;
        }
        
        //check if collision did not occur
        if ((colidedObject = collision()) != null)
        {
            //collides with enemy
            if (colidedObject instanceof Enemy)
            {
                if (die((Enemy)colidedObject)) return;
            }
            //collides with item
            else if (colidedObject instanceof Collectible)
            {
                takeCollectible((Collectible)colidedObject);
                X += (dir == Direction.LEFT) ? DISPLACEMENT : -DISPLACEMENT;
            }
            //collides with terrain
            else
            {
               X += (dir == Direction.LEFT) ? DISPLACEMENT : -DISPLACEMENT;
            }
        }
    }
    

    /**
     * tells Mario to jump
     */
    public void jump() {
        //initialize
        JUMP_ROOF = 30;
        this.jumping = true;
        this.jumpCount = 0;
    }
    
    /**
     * collect the collectible item
     * retrieves item and does extra stuff if necessary
     * @param item instance of Collectible
     */
    private void takeCollectible(Collectible item)
    {
        item.take();
        if (item.type == Block.BlockType.Flower)
            invincible = true;
    }

    /**
     * evaluates current image - left, right, jumping, invincible etc...
     * @return image to be drawn as Mario
     * @throws IOException 
     */
    public Image getImage() throws IOException {
        switch (dir)
        {
            case LEFT:
                return (jumping || falling) ? 
                        (invincible) ? ImageIO.read(getClass().getResource("../img/mario_jump_left_invincible.png")) : ImageIO.read(getClass().getResource("../img/mario_jump_left.png")) : 
                        (invincible) ? ImageIO.read(getClass().getResource("../img/mario_left_invincible.png")) : ImageIO.read(getClass().getResource("../img/mario_left.png"));
            case RIGHT:
                return (jumping || falling) ? 
                        (invincible) ? ImageIO.read(getClass().getResource("../img/mario_jump_invincible.png")) : ImageIO.read(getClass().getResource("../img/mario_jump.png"))  : 
                        (invincible) ? ImageIO.read(getClass().getResource("../img/mario_invincible.png")) : ImageIO.read(getClass().getResource("../img/mario.png")) ;
        }
        return ImageIO.read(getClass().getResource("../img/mario.png"));
    }
    
    /**
     * checks current state of Mario after commands
     * takes care of jumping, falling and collision handling
     */
    public void checkState() {
        //check if enemy does not collide when idle
        if (!jumping && !falling)
        {         
            if ((colidedObject = collision()) == null)
                falling=true;
            else if (colidedObject instanceof Enemy)
            {
                if (die((Enemy)colidedObject)) return;
            }
        }
        
        //Mario is jumping
        //when jump ends, falling starts
        if(jumping){
            if(jumpCount<JUMP_ROOF)
            {
                //move up
                Y-=DISPLACEMENT;
                jumpCount++;
                //collision found
                if ((colidedObject = collision()) != null)
                {
                    //collision with box, open it
                    if (colidedObject instanceof Box)
                    {
                        if (!((Box)colidedObject).open)
                            ((Box)colidedObject).open();
                    }
                    //collision with enemy, die
                    else if (colidedObject instanceof Enemy)
                    {
                        if (die((Enemy)colidedObject)) return;
                    }
                    //collision with collectible, tak it
                    else if (colidedObject instanceof Collectible)
                    {
                        takeCollectible((Collectible)colidedObject);
                        return;
                    }
                    jumping = false;
                    falling = true;
                }
            }
            else
            {
                jumping = false;
                falling = true;
            }
        }
        
        //Mario is falling
        if (falling)
        {
            //move down
            Y+=DISPLACEMENT;
            if ((colidedObject = collision()) != null)
            {
                //enemy hit from up, kill it and bounce
                if (colidedObject instanceof Enemy)
                {
                    jumpCount= 0;
                    JUMP_ROOF = 15;
                    falling = false;
                    jumping = true;
                    ((Enemy)colidedObject).die();
                }
                //collectible collision, take it
                else if (colidedObject instanceof Collectible)
                {
                    takeCollectible((Collectible)colidedObject);
                }
                //terrain found, stop falling
                else
                {
                    falling = false;
                    Y -= DISPLACEMENT;
                }
            }
        }
                
        //fall to hole, die
        if (Y+Block.SIZE >= GameFrame.HEIGHT) die(null);
}

    /**
     * resets Mario's state
     * @param reset checks if complete reset is necessary
     */
    public void reinitialize(boolean reset) {
        X = START_X;
        if (reset)
        {
            Y = START_Y;
            falling = false;
            jumping = false;
            dead = false;
            invincible = false;
        }
    }

    /**
     * Mario is out of bounds, new level to be started
     * @return true if out of bounds
     */
    public boolean out() {
        return X+Block.SIZE>=GameFrame.WIDTH;
    }

    /**
     * check if Mario is dead
     * @return dead 
     */
    public boolean dead()
    {
        return dead;
    }
    
    /**
     * kill Mario
     * @param enemy enemy that killed him, if Mario is invincible, enemy dies
     * @return boolean if Mario really died
     */
    private boolean die(Enemy enemy)
    {
        if (enemy == null || !invincible)
        {
            dead = true;
        }
        else
        {
            enemy.die();
            invincible = false;
        }
        return dead;
    }
}
