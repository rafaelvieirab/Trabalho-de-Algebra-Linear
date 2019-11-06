package view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class TelaOpcoes extends Application {

	public static void main(String[] args) {
		launch();

	}

	@Override
	public void start(Stage palco) throws Exception {
		
		StackPane raiz = new StackPane(); // 4
		Label lblMensagem = new Label(); // 5

		lblMensagem.setText("Estou aprendendo JavaFX!"); // 6
		raiz.getChildren().add(lblMensagem); // 7

		Scene cena = new Scene(raiz, 250, 100); // 8
		palco.setTitle("Aprendendo JavaFX"); // 9
		palco.setScene(cena); // 10
		palco.show(); // 11

	}

}
