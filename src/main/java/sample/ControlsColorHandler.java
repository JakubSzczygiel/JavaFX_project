package sample;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class ControlsColorHandler {
    static final String FAILURE_COLOR = "DarkRed";
    static final String OK_COLOR_LIGHT_GREY = "LightGrey";
    static final String OK_COLOR_WHITE = "white";

    void adjustControlsColor(Control control) {
        if (control instanceof ChoiceBox) {
            if (((ChoiceBox) control).getValue() != null) {
                setColor(OK_COLOR_LIGHT_GREY, control);
            } else {
                setColor(FAILURE_COLOR, control);
            }
        }
        if (control instanceof TextField) {
            if (!(((TextField) control).getText().isEmpty())) {
                setColor(OK_COLOR_WHITE, control);
            } else {
                setColor(FAILURE_COLOR, control);
            }
        }
        if (control instanceof DatePicker) {
            if (((DatePicker) control).getValue() != null) {
                setColor(OK_COLOR_WHITE, control);
            } else {
                setColor(FAILURE_COLOR, control);
            }
        }
    }

    void adjustControlsColor(Control... controls) {
        for (Control control : controls
        ) {
            adjustControlsColor(control);
        }
    }

    void setColor(String color, Control... controls) {
        for (Control control : controls
        ) {
            control.setStyle(String.format("-fx-background-color: %s", color));
        }
    }
}
