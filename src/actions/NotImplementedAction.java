package actions;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class NotImplementedAction extends AbstractAction {
    public NotImplementedAction() {
        putValue(AbstractAction.SHORT_DESCRIPTION, "not implemented");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(null, "Not implemented yet", "This action is not implemented", JOptionPane.ERROR_MESSAGE);
    }
}
