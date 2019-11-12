package testes;

import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import util.Ultilitarios;

public class TestViewMatriz extends Application  {
	
	private  int linhaMatrA = 0;
	private  int colunaMatrA = 0;
	private  int linhaMatrB = 0;
	private  int colunaMatrB = 0;
	
	private String opcaoSelecionada = "";
	private Label simboloOperacao = new Label("");
	
	private TextField TabelaMatrizA[][];
	private TextField TabelaMatrizB[][];
	
	private GridPane GPRespMatrA = new GridPane();
	private GridPane GPRespMatrB = new GridPane();
	
	private TabPane painelAbas = new TabPane(); // Trabalhando com abas
	private Tab AbaMatrix ;
	private Tab aba2;
	private Tab aba3;
	
	public static void main(String[] args) {    
        launch(args);
    }
	
	@SuppressWarnings({ "static-access", "rawtypes", "unchecked" })
	@Override
    public void start(Stage primaryStage) {
		/*Ideia: Usar um text Fild para ser uma celula;
		 * Definir: o tamanho da "celula" e o espaco maximo; 
		 */
	
		abaMatrixConfigurar();
		aba2();
		aba3();

		painelAbas.getTabs().addAll(AbaMatrix, aba2, aba3);
		
		Scene scene = new Scene(painelAbas, 900, 600); 
		// Largura X altura
		
        primaryStage.setTitle("Trabalho de Algebra Linear");
        primaryStage.setScene(scene);
        primaryStage.show();
		
	}//close Start	

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void abaMatrixConfigurar() {
		/* Reunir nesse metodo todo o conteudo que sera exibido na 
		aba 'Matrix'*/
		
		AbaMatrix = new Tab("Matriz");
		
		int DimensoesHeight =  20; // altura
		int DimensoesWidth = 50 ; // largura
		
		final TextField TFColunaMatrA = new TextField("");
		final TextField TFLinhaMatrA = new TextField("");
		
		final TextField TFcolunaMatrB = new TextField("");
		final TextField TFLinhaMatrB = new TextField("");
		
	
		GridPane GPMatrizA = new GridPane();
		GridPane GPMatrizB = new GridPane(); 
		GridPane GPMatrizResposta = new GridPane();
		
		
		/* Uma Linha pode ser representada pelo HBox e uma coluna por VBox;
		 * 	A ideia aqui é organizar tudo que representa a matriz A (bottoes, TextField, matrizA) em uma 
		 * coluna e o que representa a matriz B em outra coluna;
		 * Assim é só uni-las em uma Linha (HBox)
		 * */
		VBox raiz = new VBox(10);
		VBox VBoxColunaA = new VBox(10);
		VBox VBoxColunaB = new VBox(10);
	
		raiz.setTranslateX(10);
		raiz.setTranslateY(20);
		
		VBoxColunaB.setTranslateX(100);// TODO: O que acontece quando usa-se esse valor?
	
		HBox HBoxLinha = new HBox(20);
		HBox HBoxMatrizA = new HBox();
		HBox HBoxMatrizB = new HBox();
		HBox HBoxResultado = new HBox(20);
		
		Button BGerarMatrixA = new Button("Gerar");
		Button BGerarMatrixB = new Button("Gerar");
		Button BCalular = new Button("Calcular operação");

		BGerarMatrixA.setMaxSize(80, 100);
		BGerarMatrixB.setMaxSize(80, 100);
		
		//Butões para realizar ações em nas matrizes
		//Open - Organizando Button
		Button BSomaMatrizes = new Button("Soma A e B");
		Button BSubMatrizes = new Button("Subtração A e B");
		Button BMultEscalar = new Button("Multiplicar A por Escalar");
		Button BMultiplicacaoMatrizes = new Button("Multiplica A e B");
		Button BTranposicao = new Button("Tranposicao Matriz");
		Button BPotencia = new Button("Potencia de uma Matriz");
		Button BInversa = new Button("Inversa de uma Matriz");
		Button BMatrCofatora = new Button("Matriz Cofatora");
		Button BMatrAdjunta = new Button("Matriz Adjunta");
		Button BDeterminante = new Button("Determinantes");
		
		HBox HBoxOpcoesA = new HBox(30);
		HBox HBoxOpcoesB = new HBox(30);
		
		HBoxOpcoesA.getChildren().addAll(BSomaMatrizes, BSubMatrizes, BMultEscalar, BMultiplicacaoMatrizes,BTranposicao);
		HBoxOpcoesB.getChildren().addAll(BPotencia,BInversa,BMatrCofatora,BMatrAdjunta,BDeterminante);
		//Close - Organizando Button
		
		
        raiz.getChildren().addAll( HBoxOpcoesA, HBoxOpcoesB, HBoxLinha, BCalular, HBoxResultado);

        AbaMatrix.setContent(raiz); // adicionando toda as opções na Aba Matriz
        AbaMatrix.setClosable(false);
        
		
        //Acoes dos butoes
	    BGerarMatrixA.setOnAction(new EventHandler() {
			
			public void handle(Event arg0) {
				TextField celula;
			
				if(TFLinhaMatrA.getText().equals("") || TFColunaMatrA.getText().equals("")) {
					// TODO: Um erro 
					System.err.println("O campo linha ou coluna estão em branco!!!");
					return;
				}
				
			
				linhaMatrA = Integer.parseInt(TFLinhaMatrA.getText());
				colunaMatrA = Integer.parseInt(TFColunaMatrA.getText());
				
				TabelaMatrizA = new TextField[linhaMatrA][colunaMatrA];
				
				//remove todas as label's anteriores
				GPMatrizA.getChildren().clear();
				
		        for(int y = 0; y < linhaMatrA; y++){
		            for(int x = 0; x < colunaMatrA; x++){

		            	celula = new TextField();
		            	TabelaMatrizA[y][x] = celula;
		            	
		                // Create a new TextField in each Iteration
		                celula.setPrefHeight(50);
		                celula.setPrefWidth(50);
		                //celula.setAlignment(Pos.CENTER);
		                celula.setEditable(true);
		                celula.setText("0");

		                // Iterate the Index using the loops
		                GPMatrizA.setRowIndex(celula,y);
		                GPMatrizA.setColumnIndex(celula, x);    
		                GPMatrizA.getChildren().add(celula);
		            }
		        }
			}//Fim do loop X; 
		  });
	    
		BGerarMatrixB.setOnAction(new EventHandler() {
					
			public void handle(Event arg0) {
				TextField celula;
			
				if(TFLinhaMatrB.getText().equals("") || TFcolunaMatrB.getText().equals("")) {
					System.err.println("O campo linha ou coluna estão em branco!!!");
					return;
				}
				
			
				linhaMatrB = Integer.parseInt(TFLinhaMatrB.getText());
				colunaMatrB = Integer.parseInt(TFcolunaMatrB.getText());
				
				TabelaMatrizB = new TextField[linhaMatrB][colunaMatrB];
				
				//remove todas as label's anteriores
				GPMatrizB.getChildren().clear();
				
		        for(int y = 0; y < linhaMatrB; y++){
		            for(int x = 0; x < colunaMatrB; x++){
	
		            	celula = new TextField();
		            	TabelaMatrizB[y][x] = celula;
		            	
		                // Create a new TextField in each Iteration
		                celula.setPrefHeight(50);
		                celula.setPrefWidth(50);
		                //celula.setAlignment(Pos.CENTER);
		                celula.setEditable(true);
		                celula.setText("0");
	
		                // Iterate the Index using the loops
		                GPMatrizB.setRowIndex(celula,y);
		                GPMatrizB.setColumnIndex(celula, x);    
		                GPMatrizB.getChildren().add(celula);
		            }
		        }
			}//Fim do loop X; 
		  });
	  
		BCalular.setOnAction(new EventHandler() {

			@Override
			public void handle(Event arg0) {
				TextField celula;
				
				if(opcaoSelecionada.equals("")) {
					//TODO: Tratar erro aqui 
					System.err.println("Selelione uma opcao primerio");
					return;
				}
				
				if(opcaoSelecionada.equals(Ultilitarios.SOMA) || 
					opcaoSelecionada.equals(Ultilitarios.SUBTRACAO) ||
					opcaoSelecionada.equals(Ultilitarios.MULTIPLICACAO)) {
					
					// TODO: Isso é so um exemplo
					if(linhaMatrA != linhaMatrB && colunaMatrA != colunaMatrB) {
						System.out.println("Não pode ser realizado soma!!!");
						return;
					}
					
					exibindoMatrizesResultados();

					//TODO: Chamar o objeto que ira realizar a soma das matrizes
					//O resultado da soma deve ser colocado em GPMatrResultado, para ser exibido;
					for(int y = 0; y < linhaMatrB; y++){
			            for(int x = 0; x < colunaMatrB; x++){

			            	celula = new TextField();
			            	
			                
			                celula.setPrefHeight(50);
			                celula.setPrefWidth(50);
			                celula.setEditable(false);
			                celula.setText("1");

			            
			                GPMatrizResposta.setRowIndex(celula,y);
			                GPMatrizResposta.setColumnIndex(celula, x);    
			                GPMatrizResposta.getChildren().add(celula);
			            }
			        }//close -- Y
					HBoxResultado.setAlignment(Pos.CENTER);
					HBoxResultado.getChildren().clear();
					HBoxResultado.getChildren().addAll(GPRespMatrA, simboloOperacao, GPRespMatrB, new Label("="),GPMatrizResposta);
					
				}//close if - Soma
				else if(opcaoSelecionada.equals(Ultilitarios.ESCALAR)){
					
				}//close else if - Escalar
				else {}
				
			}
			
		});
		
		BSomaMatrizes.setOnAction(new EventHandler() {
			
			public void handle(Event arg0) {
				/*Essa info só aparecerá quando for clicado a opcao soma*/
				
				TFLinhaMatrA.setMaxSize(DimensoesWidth, DimensoesHeight);
				TFColunaMatrA.setMaxSize(DimensoesWidth, DimensoesHeight);
				TFLinhaMatrB.setMaxSize(DimensoesWidth, DimensoesHeight);
				TFcolunaMatrB.setMaxSize(DimensoesWidth, DimensoesHeight);
				
				HBoxMatrizA.getChildren().addAll(new Label("Linha:"), TFLinhaMatrA, new Label ("X"),new Label("Coluna:"), TFColunaMatrA, BGerarMatrixA);
				HBoxMatrizB.getChildren().addAll(new Label("Linha:"), TFLinhaMatrB, new Label ("X"),new Label("Coluna:"), TFcolunaMatrB, BGerarMatrixB);
				
				VBoxColunaA.getChildren().addAll(new Label("Matriz A"),HBoxMatrizA,GPMatrizA);
				VBoxColunaB.getChildren().addAll(new Label("Matriz B"),HBoxMatrizB,GPMatrizB);

				//VBoxColunaA.setAlignment(Pos.CENTER);
				//VBoxColunaB.setAlignment(Pos.CENTER);
				
				HBoxLinha.getChildren().addAll(VBoxColunaA,VBoxColunaB);

				opcaoSelecionada = Ultilitarios.SOMA;
				simboloOperacao.setText("+");
				
			}
		  });
		
		BMultEscalar.setOnAction(new EventHandler() {

			@Override
			public void handle(Event arg0) {
				//Receber o valor que deve ser multiplicado e a matriz
				opcaoSelecionada = Ultilitarios.ESCALAR;
				simboloOperacao.setText("x");
				
			}
			
		});
	}
	
	private void aba2() {
		aba2 = new Tab("Aba 2");
		aba2.setContent(new Button("Um botão de conteúdo"));
		aba2.setClosable(false);
		
	}
	
	private void aba3() {
		aba3 = new Tab("Aba 3");
		aba3.setContent(new Rectangle(150, 50));
		aba3.setClosable(false);
	}
	
	@SuppressWarnings("static-access")
	private void exibindoMatrizesResultados() {
		TextField celula;
		//gerar uma grade com os valores de A e outra de B e outra do resultado da operacao
		
		for(int y = 0; y < linhaMatrA; y++){
            for(int x = 0; x < colunaMatrA; x++){

            	celula = TabelaMatrizA[y][x];
            	
                //celula.getText();
                celula.setPrefHeight(50);
                celula.setPrefWidth(50);
                celula.setEditable(false);
                //celula.setText("0");

                // Iterate the Index using the loops
                GPRespMatrA.setRowIndex(celula,y);
                GPRespMatrA.setColumnIndex(celula, x);    
                GPRespMatrA.getChildren().add(celula);
            }
        }
		
		for(int y = 0; y < linhaMatrB; y++){
            for(int x = 0; x < colunaMatrB; x++){

            	celula = TabelaMatrizB[y][x];
            	
                //celula.getText();
                celula.setPrefHeight(50);
                celula.setPrefWidth(50);
                celula.setEditable(false);
                //celula.setText("0");

                // Iterates the Index using the loops
                GPRespMatrB.setRowIndex(celula,y);
                GPRespMatrB.setColumnIndex(celula, x);    
                GPRespMatrB.getChildren().add(celula);
            }
        }
	 

		/*
        for(int y = 0; y < linhaMatrA; y++){
            for(int x = 0; x < colunaMatrA; x++){

            	celula = mat[y][x];
            	
                //str = str + celula.getText() + " ";
        
            }//Fim do loop X; 
         
        }
		 */

	}
}//close class