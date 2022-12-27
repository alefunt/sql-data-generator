package actions;

import entities.ApplicationState;
import entities.Table;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileReader;

public class LoadConfigFileAction extends AbstractAction {
    public ApplicationState state;

    public LoadConfigFileAction(ApplicationState state) {
        this.state = state;
        putValue(AbstractAction.SHORT_DESCRIPTION, "open configuration file");
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


        int i = fileChooser.showOpenDialog(null);
        if (i != 0)
            return;

        File file = fileChooser.getSelectedFile();
        loadConfig(file);
    }

    private void loadConfig(File file) {
        JSONParser parser = new JSONParser();
        try (FileReader reader = new FileReader(file)) {
            JSONObject config = (JSONObject) parser.parse(reader);
            System.out.println(config.toJSONString());
            state.clearTables();
            state.setOutputType((String) config.get("type"));
            JSONArray tables = (JSONArray) config.get("tables");
            for (Object tableO : tables) {
                Table table = state.addTable((String) ((JSONObject) tableO).get("name"));
                JSONArray fields = (JSONArray) ((JSONObject) tableO).get("fields");
                for (Object fieldO : fields) {
                    JSONObject fieldJO = (JSONObject) fieldO;
                    Table.Field field = table.addField((String) fieldJO.get("name"));
                    field.setType((String) fieldJO.get("type"));
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error", "Could not read configuration file", JOptionPane.ERROR_MESSAGE);
        }
    }
}
