package validators;

public class StringValidator extends ContentValidator {

    public StringValidator(boolean isNullable) {
        super(isNullable);
    }

    public StringValidator() {
        super(false);
    }

    public boolean isContentValid(String value, String fieldName) {
        if (!this.isNullable && (value == null || value.trim().equals(""))) {
            this.frame.showErrorMessage("The " + fieldName + " cannot be empty!");
            return false;
        }
        return true;
    }
}
