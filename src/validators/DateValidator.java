package validators;

import commons.Storage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateValidator extends ContentValidator {


    public DateValidator(boolean isNullable) {
        super(isNullable);
    }

    public DateValidator() {
        super(false);
    }


    @Override
    public boolean isContentValid(String content, String fieldName) {
        if (this.isNullable && (content == null || content.trim().equals(""))) {
            return true;
        }
        try {
            Date asDate = new SimpleDateFormat(Storage.DATE_FORMAT).parse(content);
            //nincs exception szoval jo
            return true;
        } catch (ParseException e) {
            this.frame.showErrorMessage("The format of " + fieldName + " is: "+Storage.DATE_FORMAT);
            return false;
        }
    }
}
