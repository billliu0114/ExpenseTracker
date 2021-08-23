package GUI;

import model.EntryLine;
import model.ExpenseEntry;
import model.IncomeEntry;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import static ui.MainInterface.splitOnBar;

public class GUI_ViewRecord_New extends JFrame{
    private DefaultTableModel tableModel;
    private JTable table;
    private JButton delButton;
    private GUI_MainWindow gui_mainWindow;
    private JLabel balance;

    public  GUI_ViewRecord_New(GUI_MainWindow gui_mainWindow){
        this.gui_mainWindow=gui_mainWindow;
        setTitle("View Records");
        setBounds(100,100,500,400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        createView();


    }

    private void createView() {
        JPanel panel=new PrettyJPanelDark();
        panel.setLayout(new BorderLayout());
        getContentPane().add(panel);
        JPanel botPanel=new JPanel();
        botPanel.setOpaque(false);
        panel.add(botPanel,BorderLayout.SOUTH);

        String[] columnNames={"Amount","Type","Time"};
        tableModel= new DefaultTableModel(null,columnNames);
        table=new JTable(tableModel);
        RowSorter<TableModel>sorter=new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);
        table.setOpaque(false);
        DefaultTableCellRenderer render=new DefaultTableCellRenderer();
        render.setOpaque(false);
        table.setDefaultRenderer(Object.class,render);
        table.setShowGrid(false);

        JScrollPane scrollPane=new JScrollPane();
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setOpaque(false);
        scrollPane.setViewportView(table);
        scrollPane.setColumnHeaderView(table.getTableHeader());
        scrollPane.getColumnHeader().setOpaque(false);
        panel.add(scrollPane,BorderLayout.CENTER);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        balance=new JLabel("Your available balance: "+balanceUpdate());
        panel.add(balance,BorderLayout.NORTH);
        balance.setBorder(BorderFactory.createEmptyBorder(10,5,0,5));


        loadData();




        delButton=new JButton("Delete");
        botPanel.add(delButton);
        delButton.setEnabled(false);
        delButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(table.getSelectedRow()!=-1){
                    int selectedRow=table.convertRowIndexToModel(table.getSelectedRow());

                    deleteInEntryList(table.getValueAt(table.getSelectedRow(),0),table.getValueAt(table.getSelectedRow(),1),table.getValueAt(table.getSelectedRow(),2));
                    tableModel.removeRow(selectedRow);
                }
                if(table.getSelectedRow()==-1)
                    delButton.setEnabled(false);

                balance.setText("Your available balance: "+balanceUpdate());
            }
        });

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(table.getSelectedRow()!=-1){
                    delButton.setEnabled(true);
                }
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                gui_mainWindow.setEnabled(true);
                gui_mainWindow.setAlwaysOnTop(true);
            }
        });

    }

    private String balanceUpdate() {
        String bal=gui_mainWindow.getMainInterface().getEntryEditor().getBalanceEditor().GUIprintTotalBalanceOfOneList(gui_mainWindow.getMainInterface().getEntryEditor().getEntryList());
        return bal;
    }

    private void deleteInEntryList(Object valueAt, Object valueAt1, Object valueAt2) {
        String amount=(String) valueAt;
        String type=(String) valueAt1;
        String time=(String) valueAt2;

        String line= amount + "|" + type + "| " + time;

        int i=0, index=0;

        for(EntryLine item: gui_mainWindow.getMainInterface().getEntryEditor().getEntryList()){
            if(item.toString().equals(line))
                index=i;
            i++;
        }
        gui_mainWindow.getMainInterface().getEntryEditor().getEntryList().remove(index);
    }

    private void loadData() {
        for(EntryLine item:gui_mainWindow.getMainInterface().getEntryEditor().getEntryList()){
            String line=item.toString();
            ArrayList<String> partsOfLine= splitOnBar(line);

            String amount= partsOfLine.get(0);
            String category= partsOfLine.get(1);
            String time= partsOfLine.get(2).trim();
            String[] rowValues={amount,category,time};
            tableModel.addRow(rowValues);
        }
    }

}
