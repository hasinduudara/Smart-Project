package lk.ijse.gdse.staysmartproject.model;

import lk.ijse.gdse.staysmartproject.dto.TenantDTO;
import lk.ijse.gdse.staysmartproject.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TenantModel {

    public String getNextTenantId() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT Tenant_ID FROM Tenant ORDER BY Tenant_ID DESC LIMIT 1");

        if (rst.next()) {
            String lastId = rst.getString(1); // Last tenant ID
            String substring = lastId.substring(1); // Extract the numeric part
            int i = Integer.parseInt(substring); // Convert the numeric part to integer
            int newIdIndex = i + 1; // Increment the number by 1
            return String.format("T%03d", newIdIndex); // Return the new tenant ID in format Tnnn
        }
        return "T001";
    }

    public static boolean saveTenant(TenantDTO tenant) throws SQLException, ClassNotFoundException {
        boolean isSaved = CrudUtil.execute(
                "INSERT INTO Tenant VALUES(?,?,?,?,?)",
                tenant.getTenant_ID(),
                tenant.getHouse_ID(),
                tenant.getName(),
                tenant.getEmail(),
                tenant.getEnd_Of_Date());

        if (isSaved) {
            HouseModel houseModel = new HouseModel();
            return houseModel.updateHouseStatus(tenant.getHouse_ID(), "Rented Out");
        }
        return false;
    }

    public boolean updateTenant(TenantDTO tenant) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE Tenant SET House_ID=?, Name=?, Email=? WHERE Tenant_ID=?", tenant.getHouse_ID(), tenant.getName(), tenant.getEmail(), tenant.getTenant_ID());
    }

    public boolean deleteTenant(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM Tenant WHERE Tenant_ID=?", id);
    }

    public ArrayList<TenantDTO> getAllTenants() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Tenant");
        ArrayList<TenantDTO> allTenants = new ArrayList<>();
        while (rst.next()) {
            allTenants.add(new TenantDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getDate(5)
            ));
        }
        return allTenants;
    }

    public TenantDTO getTenantById(String tenantId) {
        try {
            ResultSet rst = CrudUtil.execute("SELECT * FROM Tenant WHERE Tenant_ID=?", tenantId);
            if (rst.next()) {
                return new TenantDTO(
                        rst.getString("Tenant_ID"),
                        rst.getString("House_ID"),
                        rst.getString("Name"),
                        rst.getString("Email"),
                        rst.getDate("End_Of_Date")
                );
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}