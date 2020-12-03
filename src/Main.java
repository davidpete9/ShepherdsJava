import commons.MainFrame;

public class Main {

    public static void main(String[] args) {

    MainFrame frame = MainFrame.getInstance();
    frame.navigateToMainMenu();
    frame.setVisible(true);
    }
}

