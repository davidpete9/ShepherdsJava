package validators;

public class PositiveIntValidator extends ContentValidator {

    public PositiveIntValidator(boolean isNullable) {
        super(isNullable);
    }

    public PositiveIntValidator() {
        super(false);
    }

    public boolean isContentValid(String value, String fieldName) {
        if (this.isNullable && (value == null || value.trim().equals(""))) {
            return true;
        }
        try {
            int val = Integer.parseInt(value);
            if (val < 0) {
                this.frame.showErrorMessage("The " + fieldName + " cannot be negative!");
                return false;
            }
            return true;
        } catch (NumberFormatException ex) {
            this.frame.showErrorMessage("The " + fieldName + " is not a valid integer!");
            return false;
        }
    }
}
