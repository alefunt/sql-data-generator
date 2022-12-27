package entities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;


public class ApplicationState {
    private final JComboBox<String> outputTypeComboBox;
    private final JTabbedPane tablesView;
    private ArrayList<Table> tables = new ArrayList<>();

    public ApplicationState(JComboBox<String> outputTypeComboBox, JTabbedPane tablesView){
        this.outputTypeComboBox = outputTypeComboBox;
        for(OutputType e : OutputType.values()){
            outputTypeComboBox.addItem(e.name());
        }
        this.tablesView = tablesView;
    }

    public OutputType getOutputType(){
        return OutputType.valueOf(String.valueOf(outputTypeComboBox.getSelectedItem()));
    }
    public void setOutputType(String val){
        outputTypeComboBox.setSelectedItem(val);
    }
    public ArrayList<Table> getTables(){
        return tables;
    }

    public void clearTables(){
        tables = new ArrayList<>();
        for(int i = tablesView.getTabCount()-1; i > 0; i--){
            tablesView.remove(i);
        }
    }
    public Table addTable(String tableName){
        JPanel tableTab = new JPanel();

        Table table = new Table(tableName, tableTab);
        tables.add(table);
        createTableTab(tableTab, table);

        JPopupMenu tablePopup = new JPopupMenu();
        JMenuItem addFieldI = tablePopup.add(new JMenuItem());
        addFieldI.setText("add field");
        addFieldI.addActionListener(new CreateNewFieldAction(table));

        tableTab.setComponentPopupMenu(tablePopup);

        return table;
    }

    private void createTableTab(JPanel tableTab, Table table){
        tablesView.addTab(table.name,tableTab);

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        titlePanel.setOpaque(false);
        JLabel titleLbl = new JLabel(table.name);
        titleLbl.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
        titlePanel.add(titleLbl);
        JButton closeButton = new JButton("x");
        closeButton.setBorder(BorderFactory.createEmptyBorder(0,4,0,3));
        closeButton.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                tables.remove(table);
                tablesView.remove(tableTab);
            }
        });
        titlePanel.add(closeButton);


        tablesView.setTabComponentAt(tablesView.indexOfComponent(tableTab), titlePanel);
    }

    private static class CreateNewFieldAction extends AbstractAction {
        Table table;
        CreateNewFieldAction(Table tableTab){
            this.table = tableTab;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String fieldName = JOptionPane.showInputDialog("Please type the name of new field");

            if(fieldName == null)
                return;
            if(fieldName.equals(""))
                return;

            table.addField(fieldName);
        }
    }
}
