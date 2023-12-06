module ca.bcit.comp2522.termproject.jaguarundi {
    requires javafx.controls;
    requires javafx.fxml;


    opens ca.bcit.comp2522.termproject.jaguarundi to javafx.fxml;
    exports ca.bcit.comp2522.termproject.jaguarundi;
    exports ca.bcit.comp2522.termproject.jaguarundi.holdables;
    opens ca.bcit.comp2522.termproject.jaguarundi.holdables to javafx.fxml;
    exports ca.bcit.comp2522.termproject.jaguarundi.boxes;
    opens ca.bcit.comp2522.termproject.jaguarundi.boxes to javafx.fxml;
    exports ca.bcit.comp2522.termproject.jaguarundi.interactables;
    opens ca.bcit.comp2522.termproject.jaguarundi.interactables to javafx.fxml;
    exports ca.bcit.comp2522.termproject.jaguarundi.systems;
    opens ca.bcit.comp2522.termproject.jaguarundi.systems to javafx.fxml;
//    exports ca.bcit.comp2522.termproject.jaguarundi;
//    opens ca.bcit.comp2522.termproject.jaguarundi to javafx.fxml;
}