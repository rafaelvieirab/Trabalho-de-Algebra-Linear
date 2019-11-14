package view;

import javax.swing.JOptionPane;

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
	
	private  int linhaMatrA = 3;
	private  int colunaMatrA = 3;
	private  int linhaMatrB = 3;
	private  int colunaMatrB = 3;
	
	private String opcaoSelecionada = "";
	private Label simboloOperacao = new Label("");
	
	private TextField TabelaMatrizA[][] = new TextField[linhaMatrA][colunaMatrA];
	private TextField TabelaMatrizB[][] = new TextField[linhaMatrB][colunaMatrB];

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
		
		Scene scene = new Scene(painelAbas, 900, 600);// Largura X altura
		
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
		TFLinhaMatrA.setMaxSize(DimensoesWidth, DimensoesHeight);
		TFColunaMatrA.setMaxSize(DimensoesWidth, DimensoesHeight);
		
		final TextField TFColunaMatrB = new TextField("");
		final TextField TFLinhaMatrB = new TextField("");
		TFLinhaMatrB.setMaxSize(DimensoesWidth, DimensoesHeight);
		TFColunaMatrB.setMaxSize(DimensoesWidth, DimensoesHeight);
		
		GridPane GPMatrizA = new GridPane();
		GridPane GPMatrizB = new GridPane(); 
		GridPane GPMatrizResposta = new GridPane();
		
		buildMatriX(GPMatrizA, TabelaMatrizA, linhaMatrA, colunaMatrA);
		buildMatriX(GPMatrizB, TabelaMatrizB, linhaMatrB, colunaMatrB);
		
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
		Button BCalular = new Button("Calcular opera磯");

		BGerarMatrixA.setMaxSize(80, 100);
		BGerarMatrixB.setMaxSize(80, 100);
		
		HBoxMatrizA.getChildren().addAll(new Label("Linha:"), TFLinhaMatrA, new Label ("  X"),new Label("Coluna:"), TFColunaMatrA, BGerarMatrixA);
		HBoxMatrizB.getChildren().addAll(new Label("Linha:"), TFLinhaMatrB, new Label ("  X"),new Label("Coluna:"), TFColunaMatrB, BGerarMatrixB);
		
		VBoxColunaA.getChildren().addAll(new Label("Matriz A"),HBoxMatrizA,GPMatrizA);
		VBoxColunaB.getChildren().addAll(new Label("Matriz B"),HBoxMatrizB,GPMatrizB);

		//VBoxColunaA.setAlignment(Pos.CENTER);
		//VBoxColunaB.setAlignment(Pos.CENTER);
		
		HBoxLinha.getChildren().addAll(VBoxColunaA,VBoxColunaB);
		
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

        
        raiz.getChildren().addAll(HBoxOpcoesA, HBoxOpcoesB, HBoxLinha, BCalular, HBoxResultado);
        
        AbaMatrix.setContent(raiz); // adicionando toda as opcoes na Aba Matriz
        AbaMatrix.setClosable(false);
		
        //Acoes dos butoes
	    BGerarMatrixA.setOnAction(new EventHandler() {
			
			public void handle(Event arg0) {
				try {
					if(TFLinhaMatrA.getText().equals("") || TFColunaMatrA.getText().equals(""))
						throw new Exception("Digite algum valor númerico!!!");
					
					linhaMatrA = Integer.parseInt(TFLinhaMatrA.getText());
					colunaMatrA = Integer.parseInt(TFColunaMatrA.getText());
					
					if(linhaMatrA < 0 || colunaMatrA < 0) 
						throw new Exception("Digite apenas valores númericos positivos");
					
					TabelaMatrizA = new TextField[linhaMatrA][colunaMatrA];
					GPMatrizA.getChildren().clear();//remove todas as label's anteriores
					
					buildMatriX(GPMatrizA, TabelaMatrizA, linhaMatrA, colunaMatrA); //gera a tabela com os TextField's
					
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e.getMessage(), "Erro ao gerar matriz A" , JOptionPane.ERROR_MESSAGE);
				}
			}//Fim do loop handle(); 
		  });
	    
	    BGerarMatrixB.setOnAction(new EventHandler() {
					
			public void handle(Event arg0) {
				try {
					if(TFLinhaMatrA.getText().equals("") || TFColunaMatrA.getText().equals(""))
						throw new Exception("Digite algum valor númerico!!!");
					
					linhaMatrB = Integer.parseInt(TFLinhaMatrB.getText());
					colunaMatrB = Integer.parseInt(TFColunaMatrB.getText());

					if(linhaMatrB < 0 || colunaMatrB < 0) 
						throw new Exception("Digite apenas valores númericos positivos");
					
					TabelaMatrizB = new TextField[linhaMatrB][colunaMatrB];
					GPMatrizB.getChildren().clear();//remove todas as label's anteriores
					
					buildMatriX(GPMatrizB, TabelaMatrizB,linhaMatrB,colunaMatrB);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e.getMessage(), "Erro ao gerar matriz B" , JOptionPane.ERROR_MESSAGE);
				}
			} // Fim do handle
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
	
	private void buildMatriX(GridPane GPMatriz, TextField[][] TabelaMatriz,int linha, int coluna) {
    	TextField celula;
    	for(int y = 0; y < linha; y++)
    		for(int x = 0; x < coluna; x++){

    			celula = new TextField();
    			TabelaMatriz[y][x] = celula;

    			// Create a new TextField in each Iteration
    			celula.setPrefHeight(50);
    			celula.setPrefWidth(50);
    			//celula.setAlignment(Pos.CENTER);
    			celula.setEditable(true);
    			celula.setText("0");

    			// Iterate the Index using the loops
    			GPMatriz.setRowIndex(celula,y);
    			GPMatriz.setColumnIndex(celula, x);    
    			GPMatriz.getChildren().add(celula);
    		}
    	
    }
	
	private double[][] converteGridPaneINMatriz(TextField[][] TabelaMatriz,int linha, int coluna){
		
		try {
			if(linha < 1 && coluna < 1) {
				throw new Exception("Gere uma matriz com linhas e colunas maiores que 0");
			}
			double[][] matrix = new double[linha][coluna];
			for(int i = 0; i< linha; i++) {
				for(int j = 0; j< linha; j++) {
					matrix[i][j] = Double.parseDouble(TabelaMatriz[i][j].getText());
				}
			}
			return matrix;
		}catch (Exception e) {
			if(e.getMessage().equals(""))
				JOptionPane.showMessageDialog(null, "Digite apenas numeros na tabela", "Erro ao gerar matriz A" , JOptionPane.ERROR_MESSAGE);
			else
				JOptionPane.showMessageDialog(null, e.getMessage(), "Erro ao gerar matriz A" , JOptionPane.ERROR_MESSAGE);
		}
		
		return null;
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