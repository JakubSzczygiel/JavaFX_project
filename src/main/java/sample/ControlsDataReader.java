package sample;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class ControlsDataReader {

    String getControlValue(Control control) {
        if (control instanceof ChoiceBox) {
            if (((ChoiceBox) control).getValue() != null) {
                return ((ChoiceBox) control).getValue().toString();
            }
        }

        if (control instanceof TextField) {
            if (!(((TextField) control).getText().isEmpty())) {
                return ((TextField) control).getText();
            }

        }
        if (control instanceof DatePicker) {
            if (((DatePicker) control).getValue() != null) {
                return ((DatePicker) control).getValue().toString();
            }
        }
        return null;
    }

    boolean areAllControlsFilledWithData(String... strings) {
        for (String string : strings
        ) {
            if (string == null) {
                return false;
            }
        }
        return true;
    }
}
