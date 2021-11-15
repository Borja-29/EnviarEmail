package email;

import javafx.beans.property.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controlador implements Initializable {

    //Model
    private StringProperty smtp = new SimpleStringProperty();
    private StringProperty puerto = new SimpleStringProperty();
    private BooleanProperty ssl = new SimpleBooleanProperty();
    private StringProperty remitente = new SimpleStringProperty();
    private StringProperty contraseña = new SimpleStringProperty();
    private StringProperty destinatario = new SimpleStringProperty();
    private StringProperty asunto = new SimpleStringProperty();
    private StringProperty mensaje = new SimpleStringProperty();

    //View
    
    @FXML
    private Button enviarButton;

    @FXML
    private Button vaciarButton;
    
    @FXML
    private Button cerrarButton;
    
    @FXML
    private TextField smtpText;

    @FXML
    private TextField puertoText;

    @FXML
    private CheckBox sslCheckbox;
    
    @FXML
    private TextField remitenteText;

    @FXML
    private TextField contraseñaText;

    @FXML
    private TextField destinatarioText;

    @FXML
    private TextField asuntoText;

    @FXML
    private TextArea mensajeText;

    @FXML
    private GridPane view;

    public GridPane getView() {
        return view;
    }

    public Controlador() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Vista.fxml"));
        loader.setController(this);
        loader.load();

    }

    public void initialize(URL url, ResourceBundle resources) {

        smtp.bind(smtpText.textProperty());
        puerto.bind(puertoText.textProperty());
        ssl.bind(sslCheckbox.selectedProperty());
        remitente.bind(remitenteText.textProperty());
        contraseña.bind(contraseñaText.textProperty());
        destinatario.bind(destinatarioText.textProperty());
        asunto.bind(asuntoText.textProperty());
        mensaje.bind(mensajeText.textProperty());

    }

    @FXML
    void onEnviarAction(ActionEvent event) {
        try {
            Email email = new SimpleEmail();
            email.setHostName(smtp.get());
            email.setSmtpPort(Integer.parseInt(puerto.get()));
            email.setAuthenticator(new DefaultAuthenticator(remitente.get(), contraseña.get()));
            email.setSSLOnConnect(ssl.get());
            email.setFrom(remitente.get());
            email.setSubject(asunto.get());
            email.setMsg(mensaje.get());
            email.addTo(destinatario.get());
            email.send();

            Alert alertInf = new Alert(AlertType.INFORMATION);
        	Stage stage = (Stage) alertInf.getDialogPane().getScene().getWindow();
    		stage.getIcons().add(new Image(getClass().getResourceAsStream("/imagenes/email-send-icon-32x32.png")));
    		alertInf.setTitle("Mensaje enviado");
    		alertInf.setHeaderText("Mensaje enviado con éxito a: "+destinatario.get());
    		alertInf.showAndWait();
    		
    		destinatarioText.clear();
    		asuntoText.clear();
    		mensajeText.clear();

        } catch (EmailException e) {
        	
        	Alert alertError = new Alert(AlertType.ERROR);
    		Stage stage = (Stage) alertError.getDialogPane().getScene().getWindow();
    		stage.getIcons().add(new Image(getClass().getResourceAsStream("/imagenes/email-send-icon-32x32.png")));
			alertError.setTitle("Error");
			alertError.setHeaderText("No se pudo enviar el email.");
			alertError.setContentText("Error producido: " + e.getMessage());
			alertError.showAndWait();

        }

    }

    @FXML
    void onVaciarAction(ActionEvent event) {
        smtpText.clear();
        puertoText.clear();
        sslCheckbox.setSelected(false);
        remitenteText.clear();
        contraseñaText.clear();
        destinatarioText.clear();
        asuntoText.clear();
        mensajeText.clear();
    }

    @FXML
    void onCerrarAction(ActionEvent event) {
    	Node source = (Node) event.getSource();
		Stage stage = (Stage) source.getScene().getWindow();
		stage.close();
    }
}