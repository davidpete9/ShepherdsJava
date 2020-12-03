package commons;

import abstracts.ViewBuilder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A munu-navigalo gombok actionListenerje. A gombra kattintaskor meghivja akkor a kapott ViewBuilder altal felepitett
 * nezetre cserelteti az ablak tartalmat.
 */
class MenuButtonListener implements ActionListener {

    private ViewBuilder nextView;

    public MenuButtonListener(ViewBuilder nextView) {
        this.nextView = nextView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.nextView.switchToTargetView();
    }
}
