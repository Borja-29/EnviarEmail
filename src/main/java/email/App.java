package email;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application {
	
	private static Stage primaryStage;
	private Controlador controladorEmail;

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		controladorEmail  = new Controlador();
		
		Scene scene = new Scene(controladorEmail.getView());
		
		primaryStage.setTitle("Enviar email");
		primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/imagenes/email-send-icon-32x32.png")));
		primaryStage.setScene(scene);
		primaryStage.show();

	}
	
	public static Stage getPrimaryStage() {
		return primaryStage;
	}

	public static void main(String[] args) {
		launch(args);

	}

}