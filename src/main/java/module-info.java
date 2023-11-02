module ca.bcit.comp2522.termproject.jaguarundi {
    requires javafx.controls;
    requires javafx.fxml;


    opens ca.bcit.comp2522.termproject.jaguarundi to javafx.fxml;
    exports ca.bcit.comp2522.termproject.jaguarundi;
}