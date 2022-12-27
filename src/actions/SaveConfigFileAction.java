package actions;

import entities.ApplicationState;
import entities.Table;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileWriter;
import java.util.Map;

public class SaveConfigFileAction extends AbstractAction {
    public ApplicationState state;

    public SaveConfigFileAction(ApplicationState state) {
        this.state = state;
        putValue(AbstractAction.SHORT_DESCRIPTION, "save configuration file");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                return f.isFile() && f.getName().endsWith(".sdgc");
            }

            @Override
            public String getDescription() {
                return "sql data generator configuration files";
            }
        });
        fileChooser.setSelectedFile(new File("config.sdgc"));

        int i = fileChooser.showSaveDialog(null);
        if (i != 0)
            return;

        File file = fileChooser.getSelectedFile();
        saveConfig(file);
    }


    private void saveConfig(File file) {
        JSONObject config = new JSONObject();
        config.put("type", state.getOutputType().name());
        JSONArray tables = new JSONArray();
        for(Table table : state.getTables()){
            JSONObject tableO = new JSONObject();
            tableO.put("name", table.name);
            JSONArray fields = new JSONArray();
            for(Map.Entry<String, Table.Field> field : table.fields.entrySet()){
                JSONObject fieldO = new JSONObject();
                fieldO.put("name", field.getKey());
                fieldO.put("type", field.getValue().getType().name());
                fields.add(fieldO);
            }
            tableO.put("fields", fields);
            tables.add(tableO);
        }
        config.put("tables", tables);

        try (FileWriter writer = new FileWriter(file)) {
            writer.write(config.toJSONString());
            writer.flush();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error", "Could not save configuration file", JOptionPane.ERROR_MESSAGE);
        }
    }
}
