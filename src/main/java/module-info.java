module Java {
    requires javafx.fxml;
    requires javafx.controls;
    exports com.example.demo6.principal;

    opens com.example.demo6.principal;
    opens com.example.demo6.Controllers;
}