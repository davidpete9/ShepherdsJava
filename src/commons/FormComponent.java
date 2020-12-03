package commons;

import validators.ContentValidator;

import javax.swing.*;


/**
 *  Ilyenekkel azonositja a Controller a kulonbozo JCompnenteket az egyes urlapokon.
 */
public class FormComponent {
    private JComponent element;
    private ContentValidator validator;
    private String fieldName;

    /**
     * A fieldNameForAlertMsg a JOptionPane-eken fog megjelenni, ha  a validalas valahol nem sikerul.
     * @param element
     * @param validator
     * @param fieldNameForAlertMsg
     */
    public FormComponent(JComponent element, ContentValidator validator, String fieldNameForAlertMsg) {
        this.element = element;
        this.validator = validator;
        this.fieldName = fieldNameForAlertMsg;
    }

    public JComponent getElement() {
        return element;
    }

    public ContentValidator getValidator() {
        return validator;
    }

    public String getFieldName() {
        return fieldName;
    }
}
