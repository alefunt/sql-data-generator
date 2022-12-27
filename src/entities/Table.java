package entities;

import javax.swing.*;
import java.util.HashMap;

public class Table {
    public String name;
    public HashMap<String, Field> fields;
    private JPanel tableTab;

    public Table(String name, JPanel tableTab) {
        this.name = name;
        fields = new HashMap<>();
        this.tableTab = tableTab;
    }

    public Field addField(String name) {
        JComboBox<String> typeBox = createTableFieldTpeComboBox();
        Field field = new Field(typeBox);
        createTableTabField(name, field, typeBox);
        fields.put(name, field);
        tableTab.revalidate();
        return field;
    }

    private void createTableTabField(String label, Table.Field field, JComboBox<String> comboBox) {
        tableTab.add(new JLabel(label));
        comboBox.setSelectedItem(field.getType().name());
        tableTab.add(comboBox);
    }

    private JComboBox<String> createTableFieldTpeComboBox() {
        JComboBox<String> comboBox = new JComboBox<>();
        for (FieldType type : FieldType.values()) {
            comboBox.addItem(type.name());
        }
        return comboBox;
    }

    public static class Field {
        public DataGenerator generator;
        private final JComboBox<String> typeBox;

        Field(JComboBox<String> typeBox) {
            this.typeBox = typeBox;
        }

        public FieldType getType() {
            return FieldType.valueOf(String.valueOf(typeBox.getSelectedItem()));
        }
        public void setType(String val){
            typeBox.setSelectedItem(val);
        }
    }
}
