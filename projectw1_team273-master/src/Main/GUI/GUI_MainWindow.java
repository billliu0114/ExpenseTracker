package GUI;

import ui.MainInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GUI_MainWindow extends JFrame {

    private JButton AddExpenseButton;
    private JButton AddIncomeButton;
    private JButton ViewRecordButton;
    private MainInterface mm;

    public GUI_MainWindow(MainInterface mm){
        this.mm=mm;
        mm.run_load();
        createView();

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setSize(400,250);
        setLocationRelativeTo(null);

        setResizable(false);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                mm.run_opTerminate();
                System.exit(0);
            }
        });

    }

    private void createView() {
//        JPanel panel=new JPanel();
//        getContentPane().add(panel);

        JPanel panelForm=new PrettyPanel();
        panelForm.setLayout(new GridBagLayout());
        getContentPane().add(panelForm);

        GridBagConstraints c=new GridBagConstraints();
        c.gridx=0;
        c.gridy=0;

        AddEmptyLine(panelForm, c);

        panelForm.add(new JLabel("Please choose one of the following options: "),c);
        c.gridy++;

        AddEmptyLine(panelForm, c);

        AddExpenseButton=new JButton("Add  Expense");
        panelForm.add(AddExpenseButton,c);
        AddExpenseButton.addActionListener(e -> {
            setEnabled(false);
            setModalExclusionType(Dialog.ModalExclusionType.NO_EXCLUDE);
            setAlwaysOnTop(false);
            GUI_AddEntry add_Expense= new GUI_AddEntry(this,"expense");
            add_Expense.setVisible(true);
        });
        c.gridy++;

        panelForm.add(new JLabel("  "),c);
        c.gridy++;

        AddIncomeButton=new JButton("Add Income ");
        panelForm.add(AddIncomeButton,c);
        AddIncomeButton.addActionListener(e -> {
            setEnabled(false);
            setModalExclusionType(Dialog.ModalExclusionType.NO_EXCLUDE);
            setAlwaysOnTop(false);
            GUI_AddEntry add_Expense= new GUI_AddEntry(this,"income");
            add_Expense.setVisible(true);
        });
        c.gridy++;

        AddEmptyLine(panelForm, c);

        ViewRecordButton=new JButton("View/Delete Record");
        panelForm.add(ViewRecordButton,c);
        ViewRecordButton.addActionListener(e -> {
            setEnabled(false);
            setModalExclusionType(Dialog.ModalExclusionType.NO_EXCLUDE);
            setAlwaysOnTop(false);
            GUI_ViewRecord_New gui_viewRecord=new GUI_ViewRecord_New(this);
            gui_viewRecord.setVisible(true);
        });
        c.gridy++;


    }

    private void AddEmptyLine(JPanel panelForm, GridBagConstraints c) {
        panelForm.add(new JLabel(" "),c);
        c.gridy++;
    }

    public MainInterface getMainInterface(){
        return mm;
    }
}
