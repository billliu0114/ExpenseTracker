package GUI;

import model.EntryLine;
import model.EntryType;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class GUI_TypeEnter extends JFrame implements ListSelectionListener{

    private GUI_MainWindow gui_mainWindow;
    private ArrayList<EntryType> typelist;
    private JList<EntryType> list;
    private DefaultListModel<EntryType>model=new DefaultListModel<>();
    private JButton submit;
    private JButton addButton;
    private JTextField typeField;
    private double amount;
    private JPanel panel;
    private String expenseType;




    public GUI_TypeEnter(GUI_MainWindow gui_mainWindow, String expenseType, double amount){
        this.gui_mainWindow = gui_mainWindow;
        this.amount=amount;
        this.expenseType=expenseType;
        typelist= gui_mainWindow.getMainInterface().getEntryEditor().getTypeEditor().GUI_getTypeList(this.expenseType);
        createView(this.expenseType);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setSize(300,300);
        setLocationRelativeTo(null);

        setResizable(false);
    }

    private void createView(String expenseType) {
        panel=new JPanel(new BorderLayout());
        getContentPane().add(panel);

        panel.add(new JLabel("Please choose one of the following options: "),BorderLayout.NORTH);

        list=new JList<>();
        displayList();
        list.setModel(model);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);




        JScrollPane JSlist=new JScrollPane(list);
        panel.add(JSlist,BorderLayout.CENTER);
        JSlist.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        submit=new JButton("Submit");
        if(list.getSelectedIndex()==-1)
            submit.setEnabled(false);
        SubmitButtonListener submitButtonListener=new SubmitButtonListener(panel);
        submit.addActionListener(submitButtonListener);


        addButton =new JButton("Add a new type");
        AddButtonListener addButtonListener=new AddButtonListener(addButton, expenseType);
        addButton.addActionListener(addButtonListener);
        addButton.setEnabled(false);

        typeField=new JTextField();
        typeField.addActionListener(addButtonListener);
        typeField.getDocument().addDocumentListener(addButtonListener);


        CreateBottomView(panel);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                gui_mainWindow.setEnabled(true);
                gui_mainWindow.setAlwaysOnTop(true);
            }
        });





    }

    private void CreateBottomView(JPanel panel) {
        JPanel bottomPanel=new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel,BoxLayout.Y_AXIS));
        bottomPanel.add(Box.createVerticalStrut(5));
        bottomPanel.add(new JSeparator(SwingConstants.HORIZONTAL));
        bottomPanel.add(Box.createVerticalStrut(5));
        Box hBox00=Box.createHorizontalBox();
        hBox00.add(new JLabel("Enter the new type below, if needed: "));
        bottomPanel.add(hBox00);
        bottomPanel.add(typeField);
        bottomPanel.add(Box.createVerticalStrut(5));
        Box hBox01=Box.createHorizontalBox();
        hBox01.add(Box.createHorizontalGlue());
        hBox01.add(addButton);
        hBox01.add(Box.createHorizontalGlue());
        bottomPanel.add(hBox01);
        bottomPanel.add(Box.createVerticalStrut(5));
        bottomPanel.add(new JSeparator(SwingConstants.HORIZONTAL));
        bottomPanel.add(Box.createVerticalStrut(5));
        Box hBox02=Box.createHorizontalBox();
        hBox02.add(Box.createHorizontalGlue());
        hBox02.add(submit);
        hBox02.add(Box.createHorizontalGlue());
        bottomPanel.add(hBox02);
        bottomPanel.add(Box.createVerticalStrut(5));
        panel.add(bottomPanel, BorderLayout.SOUTH);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
    }

    private void displayList() {

        for(EntryType item: typelist){
            model.addElement(item);
        }
    }

    //Idea from https://docs.oracle.com/javase/tutorial/uiswing/examples/components/index.html ListDemo.java
    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() == false) {

            if (list.getSelectedIndex() == -1) {
                //No selection, disable fire button.
                submit.setEnabled(false);

            } else {
                //Selection, enable the fire button.
                submit.setEnabled(true);
            }
        }
    }

    //Idea from https://docs.oracle.com/javase/tutorial/uiswing/examples/components/index.html ListDemo.java
    class AddButtonListener implements ActionListener, DocumentListener{
        private boolean alreadyEnabled = false;
        private JButton button;
        private String expenseType;
        public AddButtonListener(JButton button, String expenseType){
            this.button=button;
            this.expenseType=expenseType;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            String name=typeField.getText();
            EntryType et=new EntryType();
            et.setIsExpense(expenseType.equals("expense"));
            et.setTypeName(name);

            //User didn't type in a unique name...
            if (name.equals("") || alreadyInList(et)) {
                Toolkit.getDefaultToolkit().beep();
                typeField.requestFocusInWindow();
                typeField.selectAll();
                return;
            }

            int index= list.getSelectedIndex();
            if(index==-1)
                index=0;
            else
                index++;
            model.insertElementAt(et,index);
            typeField.requestFocusInWindow();
            typeField.setText("");

        }

        protected boolean alreadyInList(EntryType et) {
            return model.contains(et);
        }
        @Override
        public void insertUpdate(DocumentEvent e) {
            enableButton();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            handleEmptyTextField(e);
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            if (!handleEmptyTextField(e)) {
                enableButton();
            }
        }

        private void enableButton() {
            if (!alreadyEnabled) {
                button.setEnabled(true);
            }
        }
        private boolean handleEmptyTextField(DocumentEvent e) {
            if (e.getDocument().getLength() <= 0) {
                button.setEnabled(false);
                alreadyEnabled = false;
                return true;
            }
            return false;
        }
    }

    private class SubmitButtonListener implements ActionListener{
        JPanel panel;
        public SubmitButtonListener(JPanel panel){
            this.panel=panel;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            EntryType et=list.getSelectedValue();
            EntryLine theEntry= gui_mainWindow.getMainInterface().getEntryEditor().GUI_AddEntry(expenseType,amount,et.getTypeName());
            JOptionPane.showMessageDialog(panel, "Your record has been saved as:\n"+theEntry,"information", JOptionPane.PLAIN_MESSAGE);
            dispose();
        }
    }



}
