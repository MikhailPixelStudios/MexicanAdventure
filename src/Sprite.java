import java.awt.*;

/**
 * Created by Ученик on 20.11.2017.
 */
public class Sprite {
    private Image image;
    public Sprite(Image image){
        this.image=image;
    }
    public int getWidtth(){
        return image.getWidth(null);
    }

    public int getHeight(){
        return image.getHeight(null);

    }
    public void draw(Graphics g, int x,int y){
        g.drawImage(image,x,y,null);
    }
}
