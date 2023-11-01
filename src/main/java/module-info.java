module com.teamd.battleship {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.teamd.battleship to javafx.fxml;
    exports com.teamd.battleship;
}