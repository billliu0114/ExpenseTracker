package GUI;

import javax.swing.*;
import java.awt.*;

public class PrettyJPanelDark extends JPanel {
    public void paintComponent(Graphics g){
        ImageIcon icon=new ImageIcon("cimg.jpg");
        g.drawImage(icon.getImage(),0,0,this.getWidth(),this.getHeight(),this);
    }
}
