import actions.LoadConfigFileAction;
import actions.SaveConfigFileAction;
import entities.ApplicationState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;


public class Application {
    private static final int WIDTH = 500;
    private static final int HEIGHT = 300;
    private static final JFrame jFrame = createFrame();
    private static final JTabbedPane tablesView = new JTabbedPane();
    public static JComboBox<String> outputTypeBox;

    public static ApplicationState state;

    public static void main(String[] args) {
        JPanel jPanel = new JPanel();
        JButton button = new JButton("Generate");


        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem newTable = popupMenu.add(new JMenuItem());
        newTable.setText("new table");
        newTable.addActionListener(new CreateTableAction());
        jPanel.setComponentPopupMenu(popupMenu);

        jPanel.add(button);
        jPanel.add(new JLabel("output type:"));
        outputTypeBox = new JComboBox<>();
        jPanel.add(outputTypeBox);

        tablesView.addTab("generator",jPanel);
        jFrame.add(tablesView);

        state = new ApplicationState(outputTypeBox, tablesView);

        jFrame.setJMenuBar(createMenu());
        jFrame.revalidate();
    }


    private static JMenuBar createMenu(){
        JMenuBar menuBar = new JMenuBar();

        JMenu file = new JMenu("file");
        JMenuItem open = file.add(new JMenuItem());
        open.setAction(new LoadConfigFileAction(state));
        open.setText("open");
        JMenuItem save = file.add(new JMenuItem());
        save.setAction(new SaveConfigFileAction(state));
        save.setText("save");

        JMenu edit = new JMenu("edit");
        JMenuItem newTable = edit.add(new JMenuItem());
        newTable.setAction(new CreateTableAction());
        newTable.setText("new Table");


        menuBar.add(file);
        menuBar.add(edit);

        return menuBar;
    }
    private static JFrame createFrame() {
        JFrame jFrame = new JFrame();
        jFrame.setVisible(true);

        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
        }catch (Exception e){
            e.printStackTrace();
        }

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        jFrame.setTitle("SQL data generator");

        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        jFrame.setBounds((dimension.width - WIDTH) / 2, (dimension.height - HEIGHT) / 2, WIDTH, HEIGHT);

        return jFrame;
    }






    private static class CreateTableAction extends AbstractAction{

        @Override
        public void actionPerformed(ActionEvent e) {
            String tableName = JOptionPane.showInputDialog("Please type the name of your table");

            if(tableName == null)
                return;
            if(tableName.equals(""))
                return;

            state.addTable(tableName);
        }
    }
}
