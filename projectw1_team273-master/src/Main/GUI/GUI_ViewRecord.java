package GUI;

import model.EntryLine;
import ui.MainInterface;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GUI_ViewRecord extends JFrame {
    private MainInterface mm;
    private JList<EntryLine> list;
    private DefaultListModel<EntryLine> listModel;
    private JButton deleteButton;
    private GUI_MainWindow gui_mainWindow;

    public GUI_ViewRecord(GUI_MainWindow gui_mainWindow){
        this.gui_mainWindow=gui_mainWindow;
        this.mm=gui_mainWindow.getMainInterface();
        createView();

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setSize(500,500);
        setLocationRelativeTo(null);

        setResizable(false);
    }

    private void createView() {
        JPanel panel=new JPanel(new BorderLayout());
        getContentPane().add(panel);

        JLabel label= new JLabel("Record:");
        panel.add(label,BorderLayout.NORTH);
        label.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        listModel=new DefaultListModel<>();
        loadList();

        list=new JList<>(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        JScrollPane listScrollPane=new JScrollPane(list);
        panel.add(listScrollPane,BorderLayout.CENTER);
        listScrollPane.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));


        deleteButton=new JButton("Delete");

        JPanel buttonPane=new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
        buttonPane.add(Box.createHorizontalGlue());
        buttonPane.add(deleteButton);
        buttonPane.add(Box.createHorizontalGlue());
        panel.add(buttonPane,BorderLayout.SOUTH);
        buttonPane.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        list.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {

                if (list.getSelectedIndex() == -1) {
                    //No selection, disable fire button.
                    deleteButton.setEnabled(false);

                } else {
                    //Selection, enable the fire button.
                    deleteButton.setEnabled(true);
                }
            }
        });

        deleteButton.addActionListener(new DeleteListener());


        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                gui_mainWindow.setEnabled(true);
                gui_mainWindow.setAlwaysOnTop(true);
            }
        });
    }

    private void loadList() {
        for(EntryLine item: mm.getEntryEditor().getEntryList()){
            listModel.addElement(item);
        }
    }

    //Idea from https://docs.oracle.com/javase/tutorial/uiswing/examples/components/index.html ListDemo.java
    private class DeleteListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            //This method can be called only if
            //there's a valid selection
            //so go ahead and remove whatever's selected.
            int index=list.getSelectedIndex();
            mm.getEntryEditor().deleteRecord(1,index);
            listModel.remove(index);

            int size=listModel.getSize();

            if(size==0){//Nobody's left, disable firing.
                deleteButton.setEnabled(false);
            }
            else{
                if(index==listModel.getSize()){
                    //removed item in last position
                    index--;
                }
                list.setSelectedIndex(index);
                list.ensureIndexIsVisible(index);
            }
        }
    }


}
