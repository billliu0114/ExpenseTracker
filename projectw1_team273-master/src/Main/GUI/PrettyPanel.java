package GUI;

import javax.swing.*;
import java.awt.*;

public class PrettyPanel extends JPanel{


    public void paintComponent(Graphics g){
        ImageIcon icon=new ImageIcon("bimg.jpg");
        g.drawImage(icon.getImage(),0,0,this.getWidth(),this.getHeight(),this);
    }
}
