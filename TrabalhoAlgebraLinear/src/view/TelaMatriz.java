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
		 * BorderPane: Divide os elementos em regiões e coloca um componente em cada uma 
	* desses regiões
		 * FlowPane:  Ajeita os componentes de acordo com uma orientação e com o fluxo da aplicação;
		 * GridPane: Cria uma grade com os componentes. É possível informar qual a posição do 
	* componente na grade;
		 * AnchorPane: Os componentes filhos são "ancorados"  em uma parte do painel;
		 * TilePane: Um painel com pedaços (Tile vem do inglês e significa azulejo) para os 
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
		
		
		//Botoes de funções
		Button btnSoma = new Button();
		btnSoma.setTooltip(new Tooltip("Soma das matrizes A e B"));
		
		Button btnSubtracao = new Button();
		btnSubtracao.setTooltip(new Tooltip("Subtração da matriz A por B"));
		
		Button btnMultiplicaEscalar = new Button();
		btnMultiplicaEscalar.setTooltip(new Tooltip("Multiplica a matriz A por um Escalar"));
		
		Button btnMultiplicaMatrizes = new Button();
		btnMultiplicaMatrizes.setTooltip(new Tooltip("Multiplica a matriz A por B"));
		
		Button btnTransposta = new Button();
		btnTransposta.setTooltip(new Tooltip("Transposta da matriz A"));
		
		Button btnPotencia = new Button();
		btnPotencia.setTooltip(new Tooltip("Potência da matrizes A"));
		
		Button btnMatrizCofatora = new Button();
		btnMatrizCofatora.setTooltip(new Tooltip("Matriz Cofatora de A"));
		
		Button btnMatrizAdjunta = new Button();
		btnMatrizAdjunta.setTooltip(new Tooltip("Matriz Adjunta de A"));
		
		Button btnDeterminante = new Button();
		btnDeterminante.setTooltip(new Tooltip("Determinante da matrizes A"));
		
		
		//Matriz Resultado
			//Alguma coisa para exibir
		
		Scene cena = new Scene(dadosMatrizA, 300, 400);
		palco.setTitle("Operações com Matrizes");
		palco.setScene(cena);
		palco.show();
		
	}


}
