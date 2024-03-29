package view;

import javax.swing.JOptionPane;

import controller.ControllerBase;
import controller.ControllerMatrix;
import controller.ControllerSystem;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import model.*;
import operation.*;

public class Tela extends Application  {
	//Matriz
	private  int linhaMatrA = 3;
	private  int colunaMatrA = 3;
	private  int linhaMatrB = 3;
	private  int colunaMatrB = 3;
	
	private TextField TabelaMatrizA[][] = new TextField[linhaMatrA][colunaMatrA];
	private TextField TabelaMatrizB[][] = new TextField[linhaMatrB][colunaMatrB];

	//Sistema
	private int numEq = 3;
	private int numIncog = 3;
	private TextField TabelaMatrizAmpliada[][] = new TextField[numEq][numIncog+1];
	
	//Gram_Schmidt
	private int numVetores = 3;
	private TextField TabelaBase[][] = new TextField[numVetores][numVetores];
	
	//Abas
	private TabPane painelAbas = new TabPane(); // Trabalhando com abas
	private Tab AbaMatrix ;
	private Tab abaSystem;
	private Tab abaVector;
	
	public static void main(String[] args) {    
        launch(args);
    }
	
	@SuppressWarnings({ "static-access", "rawtypes", "unchecked" })
	@Override
    public void start(Stage primaryStage) {
	
		abaMatrixConfigurar();
		abaSystemConfigurar();
		abaVectorConfigurar();

		painelAbas.getTabs().addAll(AbaMatrix, abaSystem, abaVector);
		
		Scene scene = new Scene(painelAbas, 900, 600);// Largura X altura
		
        primaryStage.setTitle("Trabalho de Algebra Linear");
        primaryStage.setScene(scene);
        primaryStage.show();
		
	}//close Start	

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void abaMatrixConfigurar() {
		
		AbaMatrix = new Tab("Matriz");
		
		int DimensoesHeight =  20; 
		int DimensoesWidth = 50 ; 
		
		final TextField TFColunaMatrA = new TextField("3");
		final TextField TFLinhaMatrA = new TextField("3");
		final TextField TFColunaMatrB = new TextField("3");
		final TextField TFLinhaMatrB = new TextField("3");
		
		TFLinhaMatrA.setMaxSize(DimensoesWidth, DimensoesHeight);
		TFColunaMatrA.setMaxSize(DimensoesWidth, DimensoesHeight);
		TFLinhaMatrB.setMaxSize(DimensoesWidth, DimensoesHeight);
		TFColunaMatrB.setMaxSize(DimensoesWidth, DimensoesHeight);
		
		GridPane GPMatrizA = new GridPane();
		GridPane GPMatrizB = new GridPane(); 
		GridPane GPMatrizResposta = new GridPane();
		
		ControllerMatrix.getInstance().geraTabelaMatriz(GPMatrizA, TabelaMatrizA, linhaMatrA, colunaMatrA);
		ControllerMatrix.getInstance().geraTabelaMatriz(GPMatrizB, TabelaMatrizB, linhaMatrB, colunaMatrB);
		
		ScrollPane SPMatrizA = new ScrollPane(GPMatrizA);
		SPMatrizA.setMaxSize(300, 150);
		SPMatrizA.setMinSize(100, 80);
		
		ScrollPane SPMatrizB = new ScrollPane(GPMatrizB);
		SPMatrizB.setMaxSize(300, 150);
		SPMatrizB.setMinSize(100, 80);
		
		VBox VBoxColunaA = new VBox(10);
		VBox VBoxColunaB = new VBox(10);
		VBox VBoxColunaAB = new VBox(10);
		
		VBoxColunaB.setTranslateX(100);
		
		HBox HBoxLinha = new HBox(15);
		HBox HBoxMatrizA = new HBox();
		HBox HBoxMatrizB = new HBox();
		HBox HBoxResultado = new HBox(20);
		ScrollPane SPResultado = new ScrollPane(HBoxResultado);
		
		Button BGerarMatrixA = new Button("Gerar");
		Button BGerarMatrixB = new Button("Gerar");
		BGerarMatrixA.setMaxSize(80, 100);
		BGerarMatrixB.setMaxSize(80, 100);
		
		HBoxMatrizA.getChildren().addAll(new Label("Linha:  "), TFLinhaMatrA, new Label ("  X  "),new Label("Coluna:"), TFColunaMatrA, new Label("  "), BGerarMatrixA);
		HBoxMatrizB.getChildren().addAll(new Label("Linha:  "), TFLinhaMatrB, new Label ("  X  "),new Label("Coluna:"), TFColunaMatrB, new Label("  "), BGerarMatrixB);
		
		//Butões para realizar ações nas matrizes
		//Open - Organizando Button
		//Buttons de A
		Button BMultEscalarA = new Button("Multiplica A por Escalar");
		Button BTranposicaoA = new Button("Tranposição de A");
		Button BPotenciaA = new Button("Potência de A");
		Button BInversaA = new Button("Inversa de A");
		Button BMatrCofatoraA = new Button("Cofatora deA");
		Button BMatrAdjuntaA = new Button("Adjunta de A");
		Button BDeterminanteA = new Button("det(A)");
		//Buttons de A com B
		Button BSomaMatrizes = new Button("A + B");
		Button BSubMatrizes = new Button("A - B");
		Button BMultiplicacaoMatrizes = new Button("A * B");
		//Buttons de B
		Button BMultEscalarB = new Button("Multiplica B por Escalar");
		Button BTranposicaoB = new Button("Tranposição de B");
		Button BPotenciaB = new Button("Potência de B");
		Button BInversaB = new Button("Inversa de B");
		Button BMatrCofatoraB = new Button("Cofatora de B");
		Button BMatrAdjuntaB = new Button("Adjunta de B");
		Button BDeterminanteB = new Button("det(B)");
		
		HBox HBBotoesA1 = new HBox(10);
		HBox HBBotoesA2 = new HBox(10);
		HBox HBBotoesB1 = new HBox(10);
		HBox HBBotoesB2 = new HBox(10);
		
		HBBotoesA1.getChildren().addAll(BMultEscalarA, BTranposicaoA, BPotenciaA);
		HBBotoesA2.getChildren().addAll(BInversaA,BMatrCofatoraA, BMatrAdjuntaA, BDeterminanteA);
		
		HBBotoesB1.getChildren().addAll(BMultEscalarB, BTranposicaoB, BPotenciaB);
		HBBotoesB2.getChildren().addAll(BInversaB,BMatrCofatoraB, BMatrAdjuntaB, BDeterminanteB);
		
		//Adionando Campos as colunas
		VBoxColunaA.getChildren().addAll(new Label("\t\t\t\tMatriz A"),HBoxMatrizA,SPMatrizA,HBBotoesA1,HBBotoesA2);
		VBoxColunaAB.getChildren().addAll(new Label(""),new Label(""),BSomaMatrizes,BSubMatrizes,BMultiplicacaoMatrizes);
		VBoxColunaB.getChildren().addAll(new Label("\t\t\t\tMatriz B"),HBoxMatrizB,SPMatrizB,HBBotoesB1,HBBotoesB2);
		
		HBoxLinha.getChildren().addAll(VBoxColunaA, VBoxColunaAB, VBoxColunaB);
		
		VBox raiz = new VBox(10);
		raiz.setTranslateX(10);
		raiz.setTranslateY(20);
		
        raiz.getChildren().addAll(HBoxLinha, SPResultado);
        
        ScrollPane SPRaiz = new ScrollPane(raiz);
        AbaMatrix.setContent(SPRaiz); 
        AbaMatrix.setClosable(false);
		
	    BGerarMatrixA.setOnAction(new EventHandler() {
		
			public void handle(Event arg0) {
				if(TFLinhaMatrA.getText().equals("") || TFColunaMatrA.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Digite algum valor númerico!!!", "Erro ao gerar matriz A" , JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				try {
					linhaMatrA = Integer.parseInt(TFLinhaMatrA.getText());
					colunaMatrA = Integer.parseInt(TFColunaMatrA.getText());
					
					if(linhaMatrA < 1 || colunaMatrA < 1) {
						JOptionPane.showMessageDialog(null, "Digite apenas valores númericos positivos", "Erro ao gerar matriz A" , JOptionPane.ERROR_MESSAGE);
						return;
					}
					TabelaMatrizA = new TextField[linhaMatrA][colunaMatrA];
					GPMatrizA.getChildren().clear();//remove todas as label's anteriores
					
					ControllerMatrix.getInstance().geraTabelaMatriz(GPMatrizA, TabelaMatrizA, linhaMatrA, colunaMatrA);
					
				}catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Digite apenas valores númericos!!!", "Erro ao gerar matriz A" , JOptionPane.ERROR_MESSAGE);
				}
				
			}//Fim do loop handle(); 
		  });
	    
	    BGerarMatrixB.setOnAction(new EventHandler() {
					
			public void handle(Event arg0) {
				if(TFLinhaMatrB.getText().equals("") || TFColunaMatrB.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Digite algum valor númerico!!!", "Erro ao gerar matriz B" , JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				try {
					linhaMatrB = Integer.parseInt(TFLinhaMatrB.getText());
					colunaMatrB = Integer.parseInt(TFColunaMatrB.getText());
					
					if(linhaMatrB < 0 || colunaMatrB < 0) {
						JOptionPane.showMessageDialog(null, "Digite apenas valores númericos positivos", "Erro ao gerar matriz B" , JOptionPane.ERROR_MESSAGE);
						return;
					}
					TabelaMatrizB = new TextField[linhaMatrB][colunaMatrB];
					GPMatrizB.getChildren().clear();
					ControllerMatrix.getInstance().geraTabelaMatriz(GPMatrizB, TabelaMatrizB, linhaMatrB, colunaMatrB);
					
				}catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Digite apenas valores númericos!!!", "Erro ao gerar matriz B" , JOptionPane.ERROR_MESSAGE);
				}
				
			} 
		  });// Fim do Button Gerar MAtrizB
		
		BSomaMatrizes.setOnAction(new EventHandler() {
			
			public void handle(Event arg0) {
				
				Matriz a = ControllerMatrix.getInstance().converteTabelaINMatriz(TabelaMatrizA, linhaMatrA, colunaMatrA);
				Matriz b = ControllerMatrix.getInstance().converteTabelaINMatriz(TabelaMatrizB, linhaMatrB, colunaMatrB);
				
				if(a == null || b == null) //Erro tratado em converteGridPaneINMatriz()
					return;
				
				Matriz resul = OperationMatriz.getInstance().additionMatriz(a, b);
				if(resul == null) //Erro tratato dentro do metodo additionMatriz(a, b)
					return;
				
				ControllerMatrix.getInstance().geraGridPaneResultado(GPMatrizResposta, resul);
				
				ControllerMatrix.getInstance().buildResultadoTwoMatrix(HBoxResultado, a, b, GPMatrizResposta, " + ");
//				opcaoSelecionada = Ultilitarios.SOMA;
//				simboloOperacao.setText("+");
			}
		  });
		
		BSubMatrizes.setOnAction(new EventHandler() {

			@Override
			public void handle(Event arg0) {
				Matriz a = ControllerMatrix.getInstance().converteTabelaINMatriz(TabelaMatrizA, linhaMatrA, colunaMatrA);
				Matriz b = ControllerMatrix.getInstance().converteTabelaINMatriz(TabelaMatrizB, linhaMatrB, colunaMatrB);
				
				if(a == null || b == null) //Erro tratado em converteGridPaneINMatriz()
					return;
				
				Matriz resul = OperationMatriz.getInstance().subMatriz(a, b);
				if(resul == null) //Erro tratato dentro do metodo subMatriz(a, b)
					return;
				
				ControllerMatrix.getInstance().geraGridPaneResultado(GPMatrizResposta, resul);
				ControllerMatrix.getInstance().buildResultadoTwoMatrix(HBoxResultado, a, b, GPMatrizResposta, "-");
				
			}
		});
		
		BMultiplicacaoMatrizes.setOnAction(new EventHandler() {

			@Override
			public void handle(Event arg0) {
				Matriz a = ControllerMatrix.getInstance().converteTabelaINMatriz(TabelaMatrizA, linhaMatrA, colunaMatrA);
				Matriz b = ControllerMatrix.getInstance().converteTabelaINMatriz(TabelaMatrizB, linhaMatrB, colunaMatrB);
				
				if(a == null || b == null) //Erro tratado em converteGridPaneINMatriz()
					return;
				
				Matriz resul = OperationMatriz.getInstance().matrixMultiplication(a, b);
				if(resul == null) //Erro tratato dentro do metodo matrixMultiplication(a, b)
					return;
				
				ControllerMatrix.getInstance().geraGridPaneResultado(GPMatrizResposta, resul);
				ControllerMatrix.getInstance().buildResultadoTwoMatrix(HBoxResultado, a, b, GPMatrizResposta, "x");
				
			}
		});
		
		BMultEscalarA.setOnAction(new EventHandler() {

			@Override
			public void handle(Event arg0) {
				Matriz matrix = ControllerMatrix.getInstance().converteTabelaINMatriz(TabelaMatrizA, linhaMatrA, colunaMatrA);
				
				if(matrix == null) //Erro tratado em converteGridPaneINMatriz()
					return;
				
				double scalar;
				try{scalar = Double.parseDouble(JOptionPane.showInputDialog(null, "Digite o valor do Escalar:"));}
				catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Digite apenas números","Erro no Escalar", JOptionPane.ERROR_MESSAGE);
					scalar = 0;
					return;
				}
				
				Matriz resul = OperationMatriz.getInstance().scalarMultiplication(scalar, matrix);
				if(resul == null) //Erro tratato dentro do metodo scalarMultiplication()
					return;
				
				ControllerMatrix.getInstance().geraGridPaneResultado(GPMatrizResposta, resul);
				ControllerMatrix.getInstance().buildResultadoOneMatrixWithScalar(HBoxResultado, matrix, scalar, GPMatrizResposta);
				
			}	
		});//fim do EventHandler
		
		BTranposicaoA.setOnAction(new EventHandler() {
			@Override
			public void handle(Event arg0) {
				Matriz matrix = ControllerMatrix.getInstance().converteTabelaINMatriz(TabelaMatrizA, linhaMatrA, colunaMatrA);
				
				if(matrix == null) //Erro tratado em converteGridPaneINMatriz()
					return;
				
				Matriz resul = OperationMatriz.getInstance().transposition(matrix);
				if(resul == null) //Erro tratato dentro do metodo transposition(a, b)
					return;
				
				ControllerMatrix.getInstance().geraGridPaneResultado(GPMatrizResposta, resul);
				
				Label celula;
				GridPane GPMatrix = new GridPane();
				for(int linha = 0; linha < matrix.getLinha(); linha++)
		            for(int coluna = 0; coluna < matrix.getColuna(); coluna++){
		            	celula = new Label(" " + matrix.value(linha, coluna));
		            	GPMatrix.setRowIndex(celula,linha);
		            	GPMatrix.setColumnIndex(celula, coluna);    
		            	GPMatrix.getChildren().add(celula);
		            }
				
				celula = new Label("  T");
            	GPMatrix.setRowIndex(celula,0);
            	GPMatrix.setColumnIndex(celula, matrix.getColuna()+1);    
            	GPMatrix.getChildren().add(celula);
            	
				HBoxResultado.setAlignment(Pos.CENTER);
				HBoxResultado.getChildren().clear();
				HBoxResultado.getChildren().addAll(GPMatrix, new Label(" = "), GPMatrizResposta);
			}	
		});//fim do EventHandler
		
		BDeterminanteA.setOnAction(new EventHandler() {
			@Override
			public void handle(Event arg0) {
				Matriz matrix = ControllerMatrix.getInstance().converteTabelaINMatriz(TabelaMatrizA, linhaMatrA, colunaMatrA);
				
				if(matrix == null) //Erro tratado em converteGridPaneINMatriz()
					return;
				
				double resul = OperationMatriz.getInstance().determinante(matrix);
				
				Label celula;
				GridPane GPMatrix = new GridPane();
				for(int linha = 0; linha < matrix.getLinha(); linha++){
					celula = new Label("| ");
	            	GPMatrix.setRowIndex(celula,linha);
	            	GPMatrix.setColumnIndex(celula, 0);    
	            	GPMatrix.getChildren().add(celula);
		            for(int coluna = 0; coluna < matrix.getColuna(); coluna++){
		            	celula = new Label(" " + matrix.value(linha, coluna));
		            	GPMatrix.setRowIndex(celula,linha);
		            	GPMatrix.setColumnIndex(celula, coluna+1);    
		            	GPMatrix.getChildren().add(celula);
		            }
		            celula = new Label(" |");
	            	GPMatrix.setRowIndex(celula,linha);
	            	GPMatrix.setColumnIndex(celula, matrix.getColuna()+1);    
	            	GPMatrix.getChildren().add(celula);
				}
            	
				HBoxResultado.setAlignment(Pos.CENTER);
				HBoxResultado.getChildren().clear();
				HBoxResultado.getChildren().addAll(GPMatrix, new Label(" = "), new Label("" + resul));
			
			}	
		});//fim do EventHandler

		BPotenciaA.setOnAction(new EventHandler() {
			@Override
			public void handle(Event arg0) {
				Matriz matrix = ControllerMatrix.getInstance().converteTabelaINMatriz(TabelaMatrizA, linhaMatrA, colunaMatrA);
				if(matrix == null) //Erro tratado em converteGridPaneINMatriz()
					return;
				
				int expoente;
				try{expoente = Integer.parseInt(JOptionPane.showInputDialog(null, "Digite o valor do Expoente"));}
				catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Digite apenas números","Erro na Potencia", JOptionPane.ERROR_MESSAGE);
					expoente = 0;
					return;
				}
				
				Matriz resul = OperationMatriz.getInstance().potency(matrix, expoente);
				if(resul == null) //Erro tratato dentro do metodo potency()
					return;
				
				ControllerMatrix.getInstance().geraGridPaneResultado(GPMatrizResposta, resul);
				ControllerMatrix.getInstance().buildResultadoOneMatrixPotency(HBoxResultado, matrix, expoente, GPMatrizResposta);
				
			}
		});//fim do EventHandler

		BMatrAdjuntaA.setOnAction(new EventHandler() {
			@Override
			public void handle(Event arg0) {
				Matriz matrix = ControllerMatrix.getInstance().converteTabelaINMatriz(TabelaMatrizA, linhaMatrA, colunaMatrA);
				
				if(matrix == null) //Erro tratado em converteGridPaneINMatriz()
					return;
				
				Matriz resul = OperationMatriz.getInstance().adjunta(matrix);
				if(resul == null) //Erro tratato dentro do metodo adjunta()
					return;
				
				ControllerMatrix.getInstance().geraGridPaneResultado(GPMatrizResposta, resul);
				
				GridPane GPAdjunta = ControllerMatrix.getInstance().transformaMatrizEmGridPaneLabel(matrix);
				
				HBoxResultado.setAlignment(Pos.CENTER);
				HBoxResultado.getChildren().clear();
				HBoxResultado.getChildren().addAll(new Label("Adjunta:"), GPAdjunta, new Label("="),GPMatrizResposta);
				
			}
		});//fim do EventHandler
		
		BMatrCofatoraA.setOnAction(new EventHandler() {
			@Override
			public void handle(Event arg0) {
				Matriz matrix = ControllerMatrix.getInstance().converteTabelaINMatriz(TabelaMatrizA, linhaMatrA, colunaMatrA);
				
				if(matrix == null) //Erro tratado em converteGridPaneINMatriz()
					return;
				
				Matriz resul = OperationMatriz.getInstance().matrizCofatora(matrix);
				if(resul == null) //Erro tratato dentro do metodo adjunta()
					return;
				
				ControllerMatrix.getInstance().geraGridPaneResultado(GPMatrizResposta, resul);
				GridPane GPCofatora = ControllerMatrix.getInstance().transformaMatrizEmGridPaneLabel(matrix);
				
				HBoxResultado.setAlignment(Pos.CENTER);
				HBoxResultado.getChildren().clear();
				HBoxResultado.getChildren().addAll(new Label("Cofatora:"), GPCofatora, new Label("="),GPMatrizResposta);
				
			}
		});//fim do EventHandler
		
		BInversaA.setOnAction(new EventHandler() {
			@Override
			public void handle(Event arg0) {
				Matriz matrix = ControllerMatrix.getInstance().converteTabelaINMatriz(TabelaMatrizA, linhaMatrA, colunaMatrA);
				
				if(matrix == null) //Erro tratado em converteGridPaneINMatriz()
					return;
				
				Matriz resul = OperationMatriz.getInstance().inverse(matrix);
				if(resul == null) //Erro tratato dentro do metodo inverse(a, b)
					return;
				
				ControllerMatrix.getInstance().geraGridPaneResultado(GPMatrizResposta, resul);
				
				Label celula;
				GridPane GPMatrix = new GridPane();
				for(int linha = 0; linha < matrix.getLinha(); linha++)
		            for(int coluna = 0; coluna < matrix.getColuna(); coluna++){
		            	celula = new Label(" " + matrix.value(linha, coluna));
		            	GPMatrix.setRowIndex(celula,linha);
		            	GPMatrix.setColumnIndex(celula, coluna);    
		            	GPMatrix.getChildren().add(celula);
		            }
				
				celula = new Label("  -1");
            	GPMatrix.setRowIndex(celula,0);
            	GPMatrix.setColumnIndex(celula, matrix.getColuna()+1);    
            	GPMatrix.getChildren().add(celula);
            	
				HBoxResultado.setAlignment(Pos.CENTER);
				HBoxResultado.getChildren().clear();
				HBoxResultado.getChildren().addAll(GPMatrix, new Label("="), GPMatrizResposta);
				
			}
		});//fim do EventHandler

		BMultEscalarB.setOnAction(new EventHandler() {

			@Override
			public void handle(Event arg0) {
				//Receber o valor que deve ser multiplicado e a matriz
				
				
				Matriz matrix = ControllerMatrix.getInstance().converteTabelaINMatriz(TabelaMatrizB, linhaMatrB, colunaMatrB);
				
				if(matrix == null) //Erro tratado em converteGridPaneINMatriz()
					return;
				
				double scalar;
				try{scalar = Double.parseDouble(JOptionPane.showInputDialog(null, "Digite o valor do Escalar:"));}
				catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Digite apenas números","Erro no Escalar", JOptionPane.ERROR_MESSAGE);
					scalar = 0;
					return;
				}
				
				Matriz resul = OperationMatriz.getInstance().scalarMultiplication(scalar, matrix);
				if(resul == null) //Erro tratato dentro do metodo scalarMultiplication()
					return;
				
				ControllerMatrix.getInstance().geraGridPaneResultado(GPMatrizResposta, resul);
				ControllerMatrix.getInstance().buildResultadoOneMatrixWithScalar(HBoxResultado, matrix, scalar, GPMatrizResposta);
				
			}	
		});//fim do EventHandler
		
		BTranposicaoB.setOnAction(new EventHandler() {
			@Override
			public void handle(Event arg0) {
				Matriz matrix = ControllerMatrix.getInstance().converteTabelaINMatriz(TabelaMatrizB, linhaMatrB, colunaMatrB);
				
				if(matrix == null) //Erro tratado em converteGridPaneINMatriz()
					return;
				
				Matriz resul = OperationMatriz.getInstance().transposition(matrix);
				if(resul == null) //Erro tratato dentro do metodo transposition(a, b)
					return;
				
				ControllerMatrix.getInstance().geraGridPaneResultado(GPMatrizResposta, resul);
				
				Label celula;
				GridPane GPMatrix = new GridPane();
				for(int linha = 0; linha < matrix.getLinha(); linha++)
		            for(int coluna = 0; coluna < matrix.getColuna(); coluna++){
		            	celula = new Label(" " + matrix.value(linha, coluna));
		            	GPMatrix.setRowIndex(celula,linha);
		            	GPMatrix.setColumnIndex(celula, coluna);    
		            	GPMatrix.getChildren().add(celula);
		            }
				
				celula = new Label("  T");
            	GPMatrix.setRowIndex(celula,0);
            	GPMatrix.setColumnIndex(celula, matrix.getColuna()+1);    
            	GPMatrix.getChildren().add(celula);
            	
				HBoxResultado.setAlignment(Pos.CENTER);
				HBoxResultado.getChildren().clear();
				HBoxResultado.getChildren().addAll(GPMatrix, new Label(" = "), GPMatrizResposta);
			}	
		});//fim do EventHandler
		
		BDeterminanteB.setOnAction(new EventHandler() {
			@Override
			public void handle(Event arg0) {
				Matriz matrix = ControllerMatrix.getInstance().converteTabelaINMatriz(TabelaMatrizB, linhaMatrB, colunaMatrB);
				
				if(matrix == null) //Erro tratado em converteGridPaneINMatriz()
					return;
				
				double resul = OperationMatriz.getInstance().determinante(matrix);
				
				Label celula;
				GridPane GPMatrix = new GridPane();
				for(int linha = 0; linha < matrix.getLinha(); linha++){
					celula = new Label("| ");
	            	GPMatrix.setRowIndex(celula,linha);
	            	GPMatrix.setColumnIndex(celula, 0);    
	            	GPMatrix.getChildren().add(celula);
		            for(int coluna = 0; coluna < matrix.getColuna(); coluna++){
		            	celula = new Label(" " + matrix.value(linha, coluna));
		            	GPMatrix.setRowIndex(celula,linha);
		            	GPMatrix.setColumnIndex(celula, coluna+1);    
		            	GPMatrix.getChildren().add(celula);
		            }
		            celula = new Label(" |");
	            	GPMatrix.setRowIndex(celula,linha);
	            	GPMatrix.setColumnIndex(celula, matrix.getColuna()+1);    
	            	GPMatrix.getChildren().add(celula);
				}
            	
				HBoxResultado.setAlignment(Pos.CENTER);
				HBoxResultado.getChildren().clear();
				HBoxResultado.getChildren().addAll(GPMatrix, new Label(" = "), new Label("" + resul));
			
			}	
		});//fim do EventHandler

		BPotenciaB.setOnAction(new EventHandler() {
			@Override
			public void handle(Event arg0) {
				
				Matriz matrix = ControllerMatrix.getInstance().converteTabelaINMatriz(TabelaMatrizB, linhaMatrB, colunaMatrB);
				if(matrix == null) //Erro tratado em converteGridPaneINMatriz()
					return;
				
				int expoente;
				try{expoente = Integer.parseInt(JOptionPane.showInputDialog(null, "Digite o valor do Expoente:"));}
				catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Digite apenas números","Erro na Potencia", JOptionPane.ERROR_MESSAGE);
					expoente = 0;
					return;
				}
				
				
				Matriz resul = OperationMatriz.getInstance().potency(matrix, expoente);
				if(resul == null) //Erro tratato dentro do metodo potency()
					return;
				
				ControllerMatrix.getInstance().geraGridPaneResultado(GPMatrizResposta, resul);
				ControllerMatrix.getInstance().buildResultadoOneMatrixPotency(HBoxResultado, matrix, expoente, GPMatrizResposta);
				
			}
		});//fim do EventHandler

		BMatrAdjuntaB.setOnAction(new EventHandler() {
			@Override
			public void handle(Event arg0) {
				Matriz matrix = ControllerMatrix.getInstance().converteTabelaINMatriz(TabelaMatrizB, linhaMatrB, colunaMatrB);
				
				if(matrix == null) //Erro tratado em converteGridPaneINMatriz()
					return;
				
				Matriz resul = OperationMatriz.getInstance().adjunta(matrix);
				if(resul == null) //Erro tratato dentro do metodo adjunta()
					return;
				
				ControllerMatrix.getInstance().geraGridPaneResultado(GPMatrizResposta, resul);
				
				GridPane GPAdjunta = ControllerMatrix.getInstance().transformaMatrizEmGridPaneLabel(matrix);
				
				HBoxResultado.setAlignment(Pos.CENTER);
				HBoxResultado.getChildren().clear();
				HBoxResultado.getChildren().addAll(new Label("Adjunta:"), GPAdjunta, new Label("="),GPMatrizResposta);
				
			}
		});//fim do EventHandler
		
		BMatrCofatoraB.setOnAction(new EventHandler() {
			@Override
			public void handle(Event arg0) {
				Matriz matrix = ControllerMatrix.getInstance().converteTabelaINMatriz(TabelaMatrizB, linhaMatrB, colunaMatrB);
				
				if(matrix == null) //Erro tratado em converteGridPaneINMatriz()
					return;
				
				Matriz resul = OperationMatriz.getInstance().matrizCofatora(matrix);
				if(resul == null) //Erro tratato dentro do metodo adjunta()
					return;
				
				ControllerMatrix.getInstance().geraGridPaneResultado(GPMatrizResposta, resul);
				GridPane GPCofatora = ControllerMatrix.getInstance().transformaMatrizEmGridPaneLabel(matrix);
				
				HBoxResultado.setAlignment(Pos.CENTER);
				HBoxResultado.getChildren().clear();
				HBoxResultado.getChildren().addAll(new Label("Cofatora:"), GPCofatora, new Label("="),GPMatrizResposta);
				
			}
		});//fim do EventHandler
		
		BInversaB.setOnAction(new EventHandler() {
			@Override
			public void handle(Event arg0) {
				Matriz matrix = ControllerMatrix.getInstance().converteTabelaINMatriz(TabelaMatrizB, linhaMatrB, colunaMatrB);
				
				if(matrix == null) //Erro tratado em converteGridPaneINMatriz()
					return;
				
				Matriz resul = OperationMatriz.getInstance().inverse(matrix);
				if(resul == null) //Erro tratato dentro do metodo inverse(a, b)
					return;
				
				ControllerMatrix.getInstance().geraGridPaneResultado(GPMatrizResposta, resul);
				
				Label celula;
				GridPane GPMatrix = new GridPane();
				for(int linha = 0; linha < matrix.getLinha(); linha++)
		            for(int coluna = 0; coluna < matrix.getColuna(); coluna++){
		            	celula = new Label(" " + matrix.value(linha, coluna));
		            	GPMatrix.setRowIndex(celula,linha);
		            	GPMatrix.setColumnIndex(celula, coluna);    
		            	GPMatrix.getChildren().add(celula);
		            }
				
				celula = new Label("  -1");
            	GPMatrix.setRowIndex(celula,0);
            	GPMatrix.setColumnIndex(celula, matrix.getColuna()+1);    
            	GPMatrix.getChildren().add(celula);
            	
				HBoxResultado.setAlignment(Pos.CENTER);
				HBoxResultado.getChildren().clear();
				HBoxResultado.getChildren().addAll(GPMatrix, new Label("="), GPMatrizResposta);
				
			}
		});//fim do EventHandler
		
	} //fim da abaMatrixConfigurar()
	
	//Matrizes
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void abaSystemConfigurar() {
		abaSystem = new Tab("Sistemas");
		
		int DimensoesHeight =  10; // altura
		int DimensoesWidth = 40 ; // largura
		
		final TextField TFIncogSystem = new TextField("3");
		final TextField TFEquaSystem = new TextField("3");
		TFIncogSystem.setMaxSize(DimensoesWidth, DimensoesHeight);
		TFEquaSystem.setMaxSize(DimensoesWidth, DimensoesHeight);
		
		GridPane GPSystem = new GridPane();
		GridPane GPSystemResposta = new GridPane();
		
		ControllerSystem.getInstance().geraTabelaSystem(GPSystem, TabelaMatrizAmpliada, numEq, numIncog);
		//TODO - geraTabelaSystem(GPSystem);
		
		ScrollPane SPSystem = new ScrollPane(GPSystem);
		SPSystem.setMaxSize(300,300);//TODO - alterar tamnaho maximo
		
		HBox HBoxDefinicoesSystem = new HBox(); //Linha e Coluna recebidas do Sistema
		HBox HBoxSystem = new HBox();
		HBox HBoxResultado = new HBox(20);
		VBox VBoxSystem = new VBox(10);
		
		VBoxSystem.setTranslateX(100);
		
		Button BTGerar = new Button("Gerar Sistema");
		BTGerar.setMaxSize(100, 100);
		
		HBoxDefinicoesSystem.getChildren().addAll(new Label("Equações: "), TFEquaSystem, new Label (" X "),new Label("Coeficientes: "), TFIncogSystem, new Label("  "), BTGerar);		
		VBoxSystem.getChildren().addAll(HBoxDefinicoesSystem,SPSystem);
		HBoxSystem.getChildren().addAll(VBoxSystem);
		
		Button BTGauss = new Button("Gauss");
		Button BTGauss_Jordan = new Button("Gauss-Jordan");
		Button BTPosto = new Button("Posto da Matriz");
		Button BTApresentarSolucao = new Button("Apresentar Soluções");
		Button BTFatoracao_LU = new Button("Fatoração LU");
		Button BTAnalizarSolucao = new Button("Analisar Solução");

		HBox HBoxBotoes = new HBox(10); 
		HBoxBotoes.getChildren().addAll(BTGauss, BTGauss_Jordan, BTPosto, BTApresentarSolucao, BTFatoracao_LU, BTAnalizarSolucao);
        
		VBox raiz = new VBox(20);
		raiz.setTranslateX(10);
		raiz.setTranslateY(20);
		
        raiz.getChildren().addAll(HBoxSystem, HBoxBotoes, HBoxResultado);
        ScrollPane SPRaiz = new ScrollPane(raiz);
        abaSystem.setContent(SPRaiz); // adicionando toda as opcoes na Aba System
        abaSystem.setClosable(false);
        
        BTGerar.setOnAction(new EventHandler() {
			@Override
			public void handle(Event arg0) {
				try {
					if(TFEquaSystem.getText().equals("") || TFIncogSystem.getText().equals(""))
						throw new Exception("Digite algum valor númerico!!!");
					
					numEq = Integer.parseInt(TFEquaSystem.getText());
					numIncog = Integer.parseInt(TFIncogSystem.getText());
					
					if(numEq < 0 || numIncog < 0) 
						throw new Exception("Digite apenas valores númericos positivos");
					
					TabelaMatrizAmpliada = new TextField[numEq][numIncog+1];
					GPSystem.getChildren().clear();
					ControllerSystem.getInstance().geraTabelaSystem(GPSystem, TabelaMatrizAmpliada, numEq, numIncog);
					
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Preencha os campos com numeros positivos", "Erro ao gerar Sistema" , JOptionPane.ERROR_MESSAGE);
				}
			}
        }); //Fim do Button Gerar Sistema
        
        BTGauss.setOnAction(new EventHandler() {
        	@Override
        	public void handle(Event arg0) {
        		Sistema system = ControllerSystem.getInstance().converteTabelaINSistema(TabelaMatrizAmpliada, numEq, numIncog);
				if(system == null)
					return; //Erro tratado em converteGridPaneINSistema()
				ControllerSystem.getInstance().gaussAdaptadoHBoxResultado(HBoxResultado, system);
				
        	}
        }); //Fim do BTGauss 
        
        BTGauss_Jordan.setOnAction(new EventHandler() {
        	@Override
        	public void handle(Event arg0) {
        		Sistema system = ControllerSystem.getInstance().converteTabelaINSistema(TabelaMatrizAmpliada, numEq, numIncog);
        		if(system == null)
					return; //Erro tratado em converteGridPaneINSistema()
				ControllerSystem.getInstance().gaussJordanAlterado(HBoxResultado, system);
        		
        	}
        }); //Fim do BTGauss_Jordan 
        
		BTPosto.setOnAction(new EventHandler() {
        	@Override
        	public void handle(Event arg0) {
        		Sistema system = ControllerSystem.getInstance().converteTabelaINSistema(TabelaMatrizAmpliada, numEq, numIncog);
        		
				if(system == null) 
					return; //Erro tratado em converteGridPaneINSistema()
				ControllerSystem.getInstance().analyzePostAdaptado(HBoxResultado, system);
        	}
        }); //Fim do BTPosto 
        
		BTApresentarSolucao.setOnAction(new EventHandler() {
        	@Override
        	public void handle(Event arg0) {
        		Sistema system = ControllerSystem.getInstance().converteTabelaINSistema(TabelaMatrizAmpliada, numEq, numIncog);
        		if(system == null) return; //Erro tratado em converteGridPaneINSistema()
				
        		String solucoes = OperationSystem.getInstance().solucions(system);
				HBoxResultado.getChildren().clear();
				
				if(solucoes.equals("Não existe solução!")) 
					HBoxResultado.getChildren().add(new Label(solucoes));
				
				
        		VBox VBSolucoes = new VBox();
        		String valores[] = solucoes.split("\n");
        		Label celula;
        		for(String valor : valores) {
        			celula = new Label(valor);
        			VBSolucoes.getChildren().add(celula);
        		}
        		
				HBoxResultado.getChildren().add(VBSolucoes);
//				geraGridPaneResultado(GPSystemResposta, resul);
//        		buildResultadoSystem(HBoxResultado, system, GPSystemResposta);
        	}
        }); //Fim do Button 
        
		BTFatoracao_LU.setOnAction(new EventHandler() {
        	@Override
        	public void handle(Event arg0) {
        		// TODO Auto-generated method stub
        		HBoxResultado.getChildren().clear();
        		Sistema system = ControllerSystem.getInstance().converteTabelaINSistema(TabelaMatrizAmpliada, numEq, numIncog);
        		
				if(system == null) return; //Erro tratado em converteGridPaneINSistema()
				
        		Matriz matrixLU[] = OperationSystem.getInstance().fatoracaoLU(system);
        		if(matrixLU == null)
        			return;
        		
        		GridPane GPMatrixU = ControllerMatrix.getInstance().transformaMatrizEmGridPaneLabel(matrixLU[0]);
        		GridPane GPMatrixL = ControllerMatrix.getInstance().transformaMatrizEmGridPaneLabel(matrixLU[1]);
        		GridPane GPSystemOriginal = ControllerMatrix.getInstance().transformaMatrizEmGridPaneLabel(new Matriz(system.getMatrizCoef()));
    			
    			HBox HBA = new HBox(10);
    			HBox HBL = new HBox(10);
    			HBox HBU = new HBox(10);
    			VBox VBResultado = new VBox();
    			
    			HBA.getChildren().addAll(new Label("Matriz A"), new Label(" = "), GPSystemOriginal);
    			HBL.getChildren().addAll(new Label("Matriz L"), new Label(" = "), GPMatrixL);
    			HBU.getChildren().addAll(new Label("Matriz U"), new Label(" = "), GPMatrixU);
    			VBResultado.getChildren().addAll(HBA,new Label(""), HBL,new Label(""), HBU);
    			
    			HBoxResultado.setAlignment(Pos.CENTER);
    			HBoxResultado.getChildren().clear();
    			HBoxResultado.getChildren().add(VBResultado);
    			
        	}
        }); //Fim do Button 
        
		BTAnalizarSolucao.setOnAction(new EventHandler() {
        	@Override
        	public void handle(Event arg0) {
        		Sistema system = ControllerSystem.getInstance().converteTabelaINSistema(TabelaMatrizAmpliada, numEq, numIncog);
        		if(system == null) return; //Erro tratado em converteGridPaneINSistema()
				
        		String analise = OperationSystem.getInstance().analyzeSolucion(system);
				
				HBoxResultado.getChildren().clear();
				HBoxResultado.getChildren().add(new Label(analise));
        	}
        }); //Fim do Button 
        
	}// Fim do metodo abaSystemConfigurar()

	/*Gram-Schmidt*/
	private void abaVectorConfigurar() {
		abaVector = new Tab("Gram-Schmidt");
		
		int DimensoesHeight =  10; // altura
		int DimensoesWidth = 40 ; // largura
		
		final TextField TFNumVetores = new TextField("3");
		TFNumVetores.setMaxSize(DimensoesWidth, DimensoesHeight);
		
		GridPane GPBase = new GridPane();
		ControllerBase.getInstance().geraTabelaBase(GPBase, TabelaBase, numVetores);
		
		ScrollPane SPBase = new ScrollPane(GPBase);
		SPBase.setMaxSize(300,300);
		
		HBox HBoxDefinicoesBase = new HBox(); //Linha e Coluna recebidas do Sistema
		Button BTGerar = new Button("Gerar Base");
		BTGerar.setMaxSize(100, 100);	
		HBoxDefinicoesBase.getChildren().addAll(new Label("Número de Vetores: "), TFNumVetores, new Label("  "), BTGerar);		
		
		VBox VBoxBase = new VBox(10);
		VBoxBase.setTranslateX(100);
		VBoxBase.getChildren().addAll(HBoxDefinicoesBase,SPBase);
		
		HBox HBoxBase = new HBox();
		HBoxBase.getChildren().addAll(VBoxBase);
		HBox HBoxResultado = new HBox(20);

		HBox HBoxBotoes = new HBox(30); 
		Button BTOrtogonalizar= new Button("Ortogonalizar Base");
		Button BTOrtonormalizar = new Button("Ortonormalizar Base");
		HBoxBotoes.getChildren().addAll(new Label(""),BTOrtogonalizar,BTOrtonormalizar);
        
		VBox raiz = new VBox(20);
		raiz.setTranslateX(10);
		raiz.setTranslateY(20);
		
        raiz.getChildren().addAll(HBoxBase, HBoxBotoes, HBoxResultado);
        ScrollPane SPRaiz = new ScrollPane(raiz);
        abaVector.setContent(SPRaiz); // adicionando toda as opcoes na Aba System
        abaVector.setClosable(false);
		
        BTGerar.setOnAction(new EventHandler() {
        	@Override
        	public void handle(Event arg0) {
        		try {
					if(TFNumVetores.getText().equals(""))
						throw new Exception("Digite algum valor númerico!!!");
					
					numVetores = Integer.parseInt(TFNumVetores.getText());
					
					if(numVetores < 1) {
						JOptionPane.showMessageDialog(null, "O número de Vetores deve ser maiores que 0!!", "Erro ao criar a Base" , JOptionPane.ERROR_MESSAGE);
						return;
					}
					TabelaBase = new TextField[numVetores][numVetores];
					GPBase.getChildren().clear();
					ControllerBase.getInstance().geraTabelaBase(GPBase, TabelaBase, numVetores);
				
        		} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Preencha os campos com numeros positivos", "Erro ao gerar Base" , JOptionPane.ERROR_MESSAGE);
				}
        	}
		}); //Fim do Button BTGerar 
        
        BTOrtogonalizar.setOnAction(new EventHandler() {
        	@Override
        	public void handle(Event arg0) {
        		ControllerBase control = ControllerBase.getInstance();
        		Vetor base[] = control.converteTabelaINBase(TabelaBase, numVetores);
				
				if(base == null) //Erro tratado em converteTabelaINBase()
					return;
				
				Vetor[] baseOrtoganalizada = OperationBase.getInstance().orthogonalization(base);
				if(baseOrtoganalizada == null) //Erro tratato dentro do metodo orthogonalization(a, b)
					return;
				
				VBox VBBaseOrto = control.geraGridPaneResultado(baseOrtoganalizada);
				control.buildResultadoBase(HBoxResultado, base, VBBaseOrto);
			
        	}
		}); //Fim do Button BTOrtogonalizar
        
        BTOrtonormalizar.setOnAction(new EventHandler() {
        	@Override
        	public void handle(Event arg0) {
        		
        		Vetor[] base = ControllerBase.getInstance().converteTabelaINBase(TabelaBase, numVetores); 
				
				if(base == null) //Erro tratado em converteTabelaINBase()
					return;
				
				Vetor[] baseOrtonormalizada = OperationBase.getInstance().orthonormalization(base);
				if(baseOrtonormalizada == null) //Erro tratato dentro do metodo orthonormalization()
					return;
					
				VBox VBBaseOrtonor = ControllerBase.getInstance().geraGridPaneResultado(baseOrtonormalizada);
				ControllerBase.getInstance().buildResultadoBase(HBoxResultado, base, VBBaseOrtonor);
			
        	}
		}); //Fim do Button BTOrtonormalizar
	} //Fim da abaVectorConfigurar()
	
}//close class	