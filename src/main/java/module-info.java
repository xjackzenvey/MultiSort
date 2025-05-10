module me.chisato.multisort {
    requires javafx.controls;
    requires javafx.fxml;


    opens me.chisato.multisort to javafx.fxml;
    exports me.chisato.multisort;
}