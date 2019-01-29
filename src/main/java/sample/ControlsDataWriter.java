package sample;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.time.LocalDate;

public class ControlsDataWriter {
    ControlsColorHandler controlsColorHandler = new ControlsColorHandler();

    void resetValues(Control... controls) {
        for (Control control : controls
        ) {
            if (control instanceof ChoiceBox) {
                ((ChoiceBox) control).setValue(null);
                controlsColorHandler.setColor(ControlsColorHandler.OK_COLOR_LIGHT_GREY, control);
            }
            if (control instanceof TextField) {
                ((TextField) control).setText("");
                controlsColorHandler.setColor(ControlsColorHandler.OK_COLOR_WHITE, control);
            }
            if (control instanceof DatePicker) {
                ((DatePicker) control).setValue(null);
                controlsColorHandler.setColor(ControlsColorHandler.OK_COLOR_WHITE, control);
            }
        }
    }

    void setStartupValues(String taskStatus, String taskPriority, Control... controls) {
        ControlsColorHandler controlsColorHandler=new ControlsColorHandler();
        for (Control control : controls
        ) {
            if (control instanceof ChoiceBox) {
                if (((ChoiceBox) control).getItems().contains(taskStatus)) {
                    ((ChoiceBox) control).setValue(taskStatus);
                } else if (((ChoiceBox) control).getItems().contains(taskPriority)) {
                    ((ChoiceBox) control).setValue(taskPriority);
                }
                controlsColorHandler.setColor(ControlsColorHandler.OK_COLOR_LIGHT_GREY, control);
            }
            if (control instanceof TextField) {
                ((TextField) control).setText("");
                controlsColorHandler.setColor(ControlsColorHandler.OK_COLOR_WHITE, control);
            }
            if (control instanceof DatePicker) {
                LocalDate actualDate = LocalDate.now();
                ((DatePicker) control).setValue(actualDate);
                controlsColorHandler.setColor(ControlsColorHandler.OK_COLOR_WHITE, control);
            }
        }
    }
}
