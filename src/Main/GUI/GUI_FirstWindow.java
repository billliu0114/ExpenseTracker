package GUI;

import ui.MainInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI_FirstWindow extends JFrame {

    private JButton CAD_button;
    private JButton USD_button;
    private MainInterface mm;

    public GUI_FirstWindow(){
        mm=new MainInterface();
        createView();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setSize(400,250);
        setLocationRelativeTo(null);

        setResizable(true);

    }

    private void createView() {
//        JPanel panel=new PrettyPanel();

//        getContentPane().add(panel);

        JPanel panelForm=new PrettyPanel();
        panelForm.setLayout(new GridBagLayout());
        getContentPane().add(panelForm);

        GridBagConstraints c=new GridBagConstraints();
        c.gridx=0;
        c.gridy=0;

        panelForm.add(new JLabel(" "),c);
        c.gridy++;

        panelForm.add(new JLabel("Please choose the currency: "),c);
        c.gridy++;
        panelForm.add(new JLabel(" "),c);
        c.gridy++;

        CAD_button=new JButton("CAD");
        CAD_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mm.setChangeCurrency(false);
                dispose();
                new GUI_MainWindow(mm).setVisible(true);
            }
        });
        panelForm.add(CAD_button,c);
        c.gridy++;

        panelForm.add(new JLabel(" "),c);
        c.gridy++;

        USD_button=new JButton("USD");
        USD_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mm.setChangeCurrency(true);
                dispose();
                new GUI_MainWindow(mm).setVisible(true);
            }
        });
        panelForm.add(USD_button,c);
        c.gridy++;

        panelForm.add(new JLabel(" "),c);
        c.gridy++;

        panelForm.add(new JLabel("The real time exchange rate is: 1 USD= "+ mm.getEntryEditor().getExchangeRate() +" CAD"),c);
        //panelForm.add(new JLabel("The real time exchange rate is: 1 USD= 1.5 CAD"),c);
    }

    private void run(){
        mm.run();
    }

    public static void main(String[] args) {
        new GUI_FirstWindow().setVisible(true);
    }
}
