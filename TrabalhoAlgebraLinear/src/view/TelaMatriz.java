package view;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TelaMatriz extends Application{
	
	public static void main(String[] args) {
		System.out.println("ge");
	
		launch();
		
	}

	@Override
	public void start(Stage palco) throws Exception {
		
		/*
		 * VBox: Alinha os componentes verticalmente;
		 * HBox: Alinha os componentes horizontamente;
		 * StackPane: Empilha um componente sobre o outro;
		 * BorderPane: Divide os elementos em regi�es e coloca um componente em cada uma 
	* desses regi�es
		 * FlowPane:  Ajeita os componentes de acordo com uma orienta��o e com o fluxo da aplica��o;
		 * GridPane: Cria uma grade com os componentes. � poss�vel informar qual a posi��o do 
	* componente na grade;
		 * AnchorPane: Os componentes filhos s�o "ancorados"  em uma parte do painel;
		 * TilePane: Um painel com peda�os (Tile vem do ingl�s e significa azulejo) para os 
	* componentes semelhante ao GridPane, mas permite tratamento dos "Tiles";*/
		
		//Matriz A
		VBox dadosMatrizA = new VBox();
		dadosMatrizA.setSpacing(5);
		dadosMatrizA.setAlignment(Pos.TOP_LEFT);
		dadosMatrizA.setLayoutX(10);
		dadosMatrizA.setLayoutY(20);
		
		TextField txfLinhasA = new TextField();
		TextField txtfColunasA = new TextField();
		Button btnGerarMatrizA = new Button();
		
		dadosMatrizA.getChildren().addAll(txfLinhasA, txtfColunasA, btnGerarMatrizA);
		
		//Matriz B
		TextField txfLinhasB = new TextField();
		TextField txtfColunasB = new TextField();
		Button btnGerarMatrizB = new Button();
		
		
		//Botoes de fun��es
		Button btnSoma = new Button();
		btnSoma.setTooltip(new Tooltip("Soma das matrizes A e B"));
		
		Button btnSubtracao = new Button();
		btnSubtracao.setTooltip(new Tooltip("Subtra��o da matriz A por B"));
		
		Button btnMultiplicaEscalar = new Button();
		btnMultiplicaEscalar.setTooltip(new Tooltip("Multiplica a matriz A por um Escalar"));
		
		Button btnMultiplicaMatrizes = new Button();
		btnMultiplicaMatrizes.setTooltip(new Tooltip("Multiplica a matriz A por B"));
		
		Button btnTransposta = new Button();
		btnTransposta.setTooltip(new Tooltip("Transposta da matriz A"));
		
		Button btnPotencia = new Button();
		btnPotencia.setTooltip(new Tooltip("Pot�ncia da matrizes A"));
		
		Button btnMatrizCofatora = new Button();
		btnMatrizCofatora.setTooltip(new Tooltip("Matriz Cofatora de A"));
		
		Button btnMatrizAdjunta = new Button();
		btnMatrizAdjunta.setTooltip(new Tooltip("Matriz Adjunta de A"));
		
		Button btnDeterminante = new Button();
		btnDeterminante.setTooltip(new Tooltip("Determinante da matrizes A"));
		
		
		//Matriz Resultado
			//Alguma coisa para exibir
		
		Scene cena = new Scene(dadosMatrizA, 300, 400);
		palco.setTitle("Opera��es com Matrizes");
		palco.setScene(cena);
		palco.show();
		
	}


}
