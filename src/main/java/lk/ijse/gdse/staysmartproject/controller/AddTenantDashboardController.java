package lk.ijse.gdse.staysmartproject.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse.staysmartproject.dto.TenantDTO;
import lk.ijse.gdse.staysmartproject.dto.tm.TenantTM;
import lk.ijse.gdse.staysmartproject.model.HouseModel;
import lk.ijse.gdse.staysmartproject.model.TenantModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddTenantDashboardController implements Initializable {

    @FXML
    private AnchorPane addTenantDashboard;

    @FXML
    private Button btnAddTenantContractReport;

    @FXML
    private Button btnAddTenantDelete;

    @FXML
    private Button btnAddTenantReset;

    @FXML
    private Button btnAddTenantSave;

    @FXML
    private Button btnAddTenantUpdate;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colHouseId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colTenantId;

    @FXML
    private TableColumn<?, ?> colEndOfDate;

    @FXML
    private Label lblTenantId;

    @FXML
    private TableView<TenantTM> tableAddTenant;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtHouseId;

    @FXML
    private TextField txtName;

    @FXML
    private DatePicker dpEndOfDate;

    TenantModel tenantModel = new TenantModel();
    HouseModel houseModel = new HouseModel();

    @FXML
    void btnAddTenantContractReportAction(ActionEvent event) {

    }

    @FXML
    void btnAddTenantDeleteAction(ActionEvent event) {
        String tenantId = lblTenantId.getText();

        Alert alart = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to delete this tenant?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alart.showAndWait();

        if (buttonType.get() == ButtonType.YES) {
            try {
                boolean isDeleted = tenantModel.deleteTenant(tenantId);
                if (isDeleted) {
                    new Alert(Alert.AlertType.INFORMATION, "Tenant deleted successfully").show();
                    refreshPage();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete tenant").show();
                }
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    void btnAddTenantResetAction(ActionEvent event) {
        try {
            refreshPage();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void dpEndOfDate(ActionEvent event) {

    }

    @FXML
    void btnAddTenantUpdateAction(ActionEvent event) {
        String Tenant_ID = lblTenantId.getText();
        String House_ID = txtHouseId.getText();
        String Name = txtName.getText();
        String Email = txtEmail.getText();
        //Date End_Of_Date = new Date();
        java.sql.Date End_Of_Date = java.sql.Date.valueOf(dpEndOfDate.getValue());

        String namePattern = "^[A-Za-z\\s]{3,}$";
        String emailPattern = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        boolean validName = Name.matches(namePattern);
        boolean validEmail = Email.matches(emailPattern);

        // Reset input field styles
        txtName.setStyle("-fx-border-color: black; -fx-border-width: 0 0 1px 0; -fx-background-color: white");
        txtEmail.setStyle("-fx-border-color: black; -fx-border-width: 0 0 1px 0; -fx-background-color: white");

        if (!validName) {
            txtName.setStyle("-fx-border-color: red");
            return;
        }

        if (!validEmail) {
            txtEmail.setStyle("-fx-border-color: red");
            return;
        }

        // Update tenant if all fields are valid
        if (validName && validEmail) {
            TenantDTO tenantDTO = new TenantDTO(Tenant_ID, House_ID, Name, Email, new Date());

            try {
                boolean isUpdated = tenantModel.updateTenant(tenantDTO);
                if (isUpdated) {
                    new Alert(Alert.AlertType.INFORMATION, "Tenant updated successfully").show();
                    refreshPage();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to update tenant").show();
                }
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        colTenantId.setCellValueFactory(new PropertyValueFactory<>("Tenant_ID"));
        colHouseId.setCellValueFactory(new PropertyValueFactory<>("House_ID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("Email"));
        colEndOfDate.setCellValueFactory(new PropertyValueFactory<>("End_Of_Date"));

        try {
            refreshPage();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnAddTenantSaveAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String Tenant_ID = lblTenantId.getText();
        String House_ID = txtHouseId.getText();
        String Name = txtName.getText();
        String Email = txtEmail.getText();
        Date End_Of_Date = java.sql.Date.valueOf(dpEndOfDate.getValue());

        String namePattern = "^[A-Za-z\\s]{3,}$";
        String emailPattern = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        boolean validName = Name.matches(namePattern);
        boolean validEmail = Email.matches(emailPattern);

        // Reset input field styles
        txtName.setStyle("-fx-border-color: black; -fx-border-width: 0 0 1px 0; -fx-background-color: white");
        txtEmail.setStyle("-fx-border-color: black; -fx-border-width: 0 0 1px 0; -fx-background-color: white");

        if (!validName) {
            txtName.setStyle("-fx-border-color: red");
            return;
        }

        if (!validEmail) {
            txtEmail.setStyle("-fx-border-color: red");
            return;
        }


        // Save tenant if all fields are valid
        if (validName && validEmail) {
            TenantDTO tenantDTO = new TenantDTO(Tenant_ID, House_ID, Name, Email, End_Of_Date);

            boolean isSaved = tenantModel.saveTenant(tenantDTO); // Call the method on the instance

            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Tenant saved successfully").show();
                refreshPage(); // Refresh the page to update the table
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save tenant").show();
            }
        }
    }

    private void refreshPage() throws SQLException, ClassNotFoundException {
        refreshTable();

        String nextTenantId = tenantModel.getNextTenantId();
        lblTenantId.setText(nextTenantId);

        txtName.setText("");
        txtHouseId.setText("");
        txtEmail.setText("");
        dpEndOfDate.setValue(null);

        btnAddTenantSave.setDisable(false);
        btnAddTenantDelete.setDisable(true);
        btnAddTenantUpdate.setDisable(true);
        btnAddTenantReset.setDisable(true);
        btnAddTenantContractReport.setDisable(true);
    }

    private void refreshTable() throws SQLException, ClassNotFoundException {
        ArrayList<TenantDTO> allTenants = tenantModel.getAllTenants();
        ObservableList<TenantTM> tenantTMS = FXCollections.observableArrayList();

        for (TenantDTO tenantDTO : allTenants) {
            TenantTM tenantTM = new TenantTM(
                    tenantDTO.getTenant_ID(),
                    tenantDTO.getHouse_ID(),
                    tenantDTO.getName(),
                    tenantDTO.getEmail(),
                    tenantDTO.getEnd_Of_Date()
            );
            tenantTMS.add(tenantTM);
        }
        tableAddTenant.setItems(tenantTMS);
    }

    @FXML
    void onClickTable(MouseEvent event) {
        TenantTM selectedItem = tableAddTenant.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            return;
        }

        lblTenantId.setText(selectedItem.getTenant_ID());
        txtHouseId.setText(selectedItem.getHouse_ID());
        txtName.setText(selectedItem.getName());
        txtEmail.setText(selectedItem.getEmail());
        dpEndOfDate.setValue(new java.sql.Date(selectedItem.getEnd_Of_Date().getTime()).toLocalDate());

        btnAddTenantSave.setDisable(true);
        btnAddTenantDelete.setDisable(false);
        btnAddTenantUpdate.setDisable(false);
        btnAddTenantReset.setDisable(false);
        btnAddTenantContractReport.setDisable(false);
    }
}