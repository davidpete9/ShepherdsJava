package commons;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


/**
 * singleton
 * Az ablak vezerles. Megnyitaskor beolvassa az adatokat, bezaraskor ment.
 */
public class MainFrame extends JFrame {

    private static MainFrame SINGLE_INSTANCE = null;

    public static MainFrame getInstance() {
        if (SINGLE_INSTANCE == null)
            SINGLE_INSTANCE = new MainFrame();

        return SINGLE_INSTANCE;
    }

    private MainFrame() {
        super("Shepherd administration");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(800, 600));

        Storage.getInstance().loadAllFromFile();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Storage.getInstance().saveAllToFile();
            }
        });

    }

    /**
     * Az inditast kovetoen futtatom le, a fomenube navigaltat.
     */
    public void navigateToMainMenu() {
        MenuButtonListeners.mainMenuTarget.actionPerformed(null);
    }

    /**
     * Hibajelzes, JOptionPane segitsegevel
     * @param msg
     */
    public void showErrorMessage(String msg) {
        JOptionPane.showMessageDialog(null,
                msg, "Error!",
                JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Megerosites, igazzal terv vissza, ha a JOptionPane-en az okra lett kattintva.
     * @param msg: String
     * @return
     */
    public boolean confirmAction(String msg) {
        int dialogResult = JOptionPane.showOptionDialog(null,
                msg,
                "Confirmation",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.WARNING_MESSAGE,
                null,
                new String[]{"Ok", "Cancel"},
                "default");

        return dialogResult == JOptionPane.OK_OPTION;
    }


}
