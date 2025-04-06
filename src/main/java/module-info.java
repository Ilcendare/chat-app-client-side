module com.chatroom.app {
    requires javafx.controls;
    requires java.rmi;
    requires transitive javafx.fxml;
    requires transitive javafx.graphics;
    requires transitive serverlib;
    requires transitive memberlib;
    requires transitive java.sql;

    opens com.chatroom.app to javafx.fxml;
    opens com.chatroom.app.member to javafx.fxml;
    opens com.chatroom.app.viewcontrollers to javafx.fxml;

    opens com.chatroom.app.controllers to javafx.fxml;
    opens com.chatroom.app.utils to javafx.fxml;
    opens com.chatroom.app.animations to javafx.fxml;
    opens com.chatroom.app.server to javafx.fxml;

    exports com.chatroom.app;
    exports com.chatroom.app.member;
    exports com.chatroom.app.controllers;
    exports com.chatroom.app.server;
    exports com.chatroom.app.viewcontrollers;
    exports com.chatroom.app.utils;
    exports com.chatroom.app.animations;
}
