package recordSearch.Controller;

import loginScreen.SqliteHelper;
import recordSearch.Model.CustomersTable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import homeScreen.Model.ValidInputException;


import java.io.IOException;
import java.sql.*;


public class ModifyCustomerController{
    private int customerId;
    @FXML
    private TextField customerNameField;
    @FXML
    private TextField customerAddressField;
    @FXML
    private TextField customerAddress2Field;
    @FXML
    private TextField customerPhoneField;
    @FXML
    private TextField customerCityField;
    @FXML
    private TextField customerPostalField;
    @FXML
    private TextField customerCountryField;
    @FXML
    private CheckBox customerIsActive;
    Alert a = new Alert(Alert.AlertType.WARNING);

    public void handleUpdateCustomer(ActionEvent event) throws Exception {
        if(!validateInput()){
            //System.out.println("Input was WRONG");
        }
        else {
            //Continue with adding input
            //System.out.println("Input was right");
            modifySelectedRecord();
            Parent root = FXMLLoader.load(getClass().getResource("/recordSearch/View/searchCustomer.fxml"));
            Scene scene = new Scene(root);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        }
    }
    public void modifySelectedRecord() throws Exception {

        try {
            PreparedStatement preparedStatement;
            PreparedStatement preparedStatementAddress;
            PreparedStatement preparedStatementCity;
            PreparedStatement preparedStatementCountry;
            ResultSet resultSetAddressId;
            ResultSet resultSetCityId;
            ResultSet resultSetCountryId;
            int addressId;
            int cityId;
            int countryId;
            String query = "select addressId from customer where customerId=?";
            String query_2 = "select cityId from address where addressId=?";
            String query_3 = "select countryId from city where cityId=?";

            preparedStatement = SqliteHelper.getConn().prepareStatement(query);
            preparedStatement.setInt(1, customerId);
            resultSetAddressId = preparedStatement.executeQuery();
            resultSetAddressId.next();
            addressId = Integer.parseInt(resultSetAddressId.getString("addressId"));

            preparedStatement = SqliteHelper.getConn().prepareStatement(query_2);
            preparedStatement.setInt(1, addressId);
            resultSetCityId = preparedStatement.executeQuery();
            resultSetCityId.next();
            cityId = Integer.parseInt(resultSetCityId.getString("cityId"));


            preparedStatement = SqliteHelper.getConn().prepareStatement(query_3);
            preparedStatement.setInt(1, cityId);
            resultSetCountryId = preparedStatement.executeQuery();
            resultSetCountryId.next();
            countryId = Integer.parseInt(resultSetCountryId.getString("countryId"));


            String updateCustomer = "update customer set customerName=?, active=? where customerId=?";
            String updateAddress = "update address set address=?, address2=?, phone=?, postalCode=? where addressId=?";
            String updateCity = "update city set city=? where cityId=?";
            String updateCountry = "update country set country=? where countryId=?";

            preparedStatement = SqliteHelper.getConn().prepareStatement(updateCustomer);
            preparedStatement.setString(1, customerNameField.getText());
            if(customerIsActive.isSelected()){
                preparedStatement.setInt(2, 1);
            }
            else{
                preparedStatement.setInt(2, 0);
            }
            preparedStatement.setInt(3, customerId);
            preparedStatement.executeUpdate();

            preparedStatementAddress = SqliteHelper.getConn().prepareStatement(updateAddress);
            preparedStatementAddress.setString(1, customerAddressField.getText());
            preparedStatementAddress.setString(2, customerAddress2Field.getText());
            preparedStatementAddress.setString(3, customerPhoneField.getText());
            preparedStatementAddress.setString(4, customerPostalField.getText());
            preparedStatementAddress.setInt(5, addressId);
            preparedStatementAddress.executeUpdate();

            preparedStatementCity = SqliteHelper.getConn().prepareStatement(updateCity);
            preparedStatementCity.setString(1, customerCityField.getText());
            preparedStatementCity.setInt(2, cityId);
            preparedStatementCity.executeUpdate();

            preparedStatementCountry = SqliteHelper.getConn().prepareStatement(updateCountry);
            preparedStatementCountry.setString(1, customerCountryField.getText());
            preparedStatementCountry.setInt(2, countryId);
            preparedStatementCountry.executeUpdate();
            //System.out.println("Made it to here");
        }
        catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }


    }



    public boolean validateInput(){
        boolean nameFieldWasWrong = false;
        boolean addressFieldWasWrong = false;
        boolean address2FieldWasWrong = false;
        boolean postFieldWasWrong = false;
        boolean phoneFieldWasWrong = false;
        boolean cityFieldWasWrong = false;
        boolean countryFieldWasWrong = false;

        ValidInputException o = new ValidInputException();
        //If the exception does not return true clear the field
        if(!o.verifyVarChar45(customerNameField.getText())){
            customerNameField.clear();
            nameFieldWasWrong = true;
        }
        if(!o.verifyVarChar50(customerAddressField.getText())){
            customerAddressField.clear();
            addressFieldWasWrong = true;
        }
        if(!o.verifyVarChar50withNull(customerAddress2Field.getText())){
            customerAddress2Field.clear();
            address2FieldWasWrong = true;
        }
        if(!o.verifyVarChar10(customerPostalField.getText())){
            customerPostalField.clear();
            postFieldWasWrong = true;
        }
        if(!o.verifyVarChar20(customerPhoneField.getText())){
            customerPhoneField.clear();
            phoneFieldWasWrong = true;
        }
        if(!o.verifyVarChar50(customerCityField.getText())){
            customerCityField.clear();
            cityFieldWasWrong = true;
        }
        if(!o.verifyVarChar50(customerCountryField.getText())){
            customerCountryField.clear();
            countryFieldWasWrong = true;
        }

        if(nameFieldWasWrong || addressFieldWasWrong || address2FieldWasWrong || postFieldWasWrong
                || phoneFieldWasWrong || cityFieldWasWrong || countryFieldWasWrong){
            a.setContentText("The fields with invalid input have been cleared");
            a.showAndWait();

            return false;



        }


        return true;

    }

    public void handleCancel(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/recordSearch/View/searchCustomer.fxml"));
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();


    }

    public void initOldRecord(CustomersTable selectedRecord){
        customerId = Integer.parseInt(selectedRecord.getCustomerId());
        System.out.println(selectedRecord.getCustomerId());

        try {
            PreparedStatement preparedStatement;
            ResultSet resultSet;
            String query = "select customerName, active,  address, address2, postalCode, phone, city, country\n" +
                    "from customer join address a on customer.addressId = a.addressId\n" +
                    "join city c on a.cityId = c.cityId join country c2 on c.countryId = c2.countryId\n" +
                    "where customerId = ?";

            preparedStatement = SqliteHelper.getConn().prepareStatement(query);
            preparedStatement.setInt(1, customerId);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();


            //System.out.println("Made it to here");
            customerNameField.setText(resultSet.getString("customerName"));
            customerAddressField.setText(resultSet.getString("address"));
            customerAddress2Field.setText(resultSet.getString("address2"));
            customerPostalField.setText(resultSet.getString("postalCode"));
            customerPhoneField.setText(resultSet.getString("Phone"));
            customerCityField.setText(resultSet.getString("city"));
            customerCountryField.setText(resultSet.getString("country"));
            if(resultSet.getInt("active") == 1){
                customerIsActive.setSelected(true);
            }
            else{
                customerIsActive.setSelected(false);
            }

        }
        catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
