package validators;

import commons.MainFrame;

/**
 * Leellenorzi, hogy helyes a bemeneti adat, az adott mezohoz, ha nem figyelmeztet.
 */
public abstract class ContentValidator {

    protected boolean isNullable;

    public ContentValidator(boolean isNullable) {
        this.isNullable = isNullable;
    }

    protected MainFrame frame = MainFrame.getInstance();

    /**
     * Ha nem nullAble, akkor ellenorzi, hogy a content helyes-e. Ha nem jellemzoen JOptionPane-en figyelmeztet,
     * es false-sel ter vissza.
     * @param content: String
     * @param fieldName: String
     * @return boolean
     */
    public abstract boolean isContentValid(String content, String fieldName);
}
