package GUI;

import Exceptions.AmountNegativeException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static ui.MainInterface.extractDouble;

public class GUI_AddEntry extends JFrame {
    private GUI_MainWindow gui_mainWindow;
    private JLabel header;
    private JTextField textAmount;
    private JButton submit;
    private String expenseType;
    private boolean nextWindowOpenned;


    public GUI_AddEntry(GUI_MainWindow gui_mainWindow, String expenseType){
        this.gui_mainWindow = gui_mainWindow;
        this.expenseType=expenseType;
        nextWindowOpenned=false;

        createView();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setSize(400,100);
        setLocationRelativeTo(null);

        setResizable(false);

    }

    private void createView() {

        JPanel panel=new JPanel();
        getContentPane().add(panel);

        header=new JLabel("Please enter the amount: ");
        panel.add(header);

        textAmount=new JTextField(12);
        panel.add(textAmount);

        submit=new JButton("Submit");
        panel.add(submit);
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Double dollar = extractDouble(textAmount.getText());
                    if(dollar<0)
                        throw new AmountNegativeException();
                    GUI_TypeEnter gui_typeEnter=new GUI_TypeEnter(gui_mainWindow,expenseType,dollar);
                    gui_typeEnter.setVisible(true);
                    nextWindowOpenned=true;
                    dispose();
                }
                catch (Exception ex){
                    Toolkit.getDefaultToolkit().beep();
                    JOptionPane.showMessageDialog(panel, "You have entered invalid number", "Warning",JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                if(!nextWindowOpenned) {
                    gui_mainWindow.setEnabled(true);
                    gui_mainWindow.setAlwaysOnTop(true);
                }
            }
        });

    }
}
