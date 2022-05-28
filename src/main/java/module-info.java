module com.cuphead {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;


    opens com.cuphead to javafx.fxml;
    exports com.cuphead;
    exports com.cuphead.View;
    opens com.cuphead.View to javafx.fxml;
    exports com.cuphead.models;
    opens com.cuphead.models to javafx.fxml;
}