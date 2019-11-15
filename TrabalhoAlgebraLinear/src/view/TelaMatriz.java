package view;

import javax.swing.JOptionPane;

import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import model.Matriz;
import operation.OperationMatriz;
import util.Ultilitarios;

public class TelaMatriz extends Application  {
	
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
		
		final TextField TFColunaMatrA = new TextField("3");
		final TextField TFLinhaMatrA = new TextField("3");
		TFLinhaMatrA.setMaxSize(DimensoesWidth, DimensoesHeight);
		TFColunaMatrA.setMaxSize(DimensoesWidth, DimensoesHeight);
		
		final TextField TFColunaMatrB = new TextField("3");
		final TextField TFLinhaMatrB = new TextField("3");
		TFLinhaMatrB.setMaxSize(DimensoesWidth, DimensoesHeight);
		TFColunaMatrB.setMaxSize(DimensoesWidth, DimensoesHeight);
		
		GridPane GPMatrizA = new GridPane();
		GridPane GPMatrizB = new GridPane(); 
		GridPane GPMatrizResposta = new GridPane();
		
		geraTabelaMatriz(GPMatrizA, TabelaMatrizA, linhaMatrA, colunaMatrA);
		geraTabelaMatriz(GPMatrizB, TabelaMatrizB, linhaMatrB, colunaMatrB);
		
		ScrollPane SPMatrizA = new ScrollPane(GPMatrizA);
		SPMatrizA.setMaxHeight(200);
		SPMatrizA.setMaxWidth(375);
		
		ScrollPane SPMatrizB = new ScrollPane(GPMatrizB);
		SPMatrizB.setMaxHeight(200);
		SPMatrizB.setMaxWidth(375);
		
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
		Button BCalular = new Button("Calcular operacao");

		BGerarMatrixA.setMaxSize(80, 100);
		BGerarMatrixB.setMaxSize(80, 100);
		
		HBoxMatrizA.getChildren().addAll(new Label("Linha:"), TFLinhaMatrA, new Label ("  X  "),new Label("Coluna:"), TFColunaMatrA, new Label("  "), BGerarMatrixA);
		HBoxMatrizB.getChildren().addAll(new Label("Linha:"), TFLinhaMatrB, new Label ("  X  "),new Label("Coluna:"), TFColunaMatrB, new Label("  "), BGerarMatrixB);
		
		
//		VBoxColunaA.getChildren().addAll(new Label("Matriz A"),HBoxMatrizA,GPMatrizA);
//		VBoxColunaB.getChildren().addAll(new Label("Matriz B"),HBoxMatrizB,GPMatrizB);
		VBoxColunaA.getChildren().addAll(new Label("\t\t\t\tMatriz A"),HBoxMatrizA,SPMatrizA);
		VBoxColunaB.getChildren().addAll(new Label("\t\t\t\tMatriz B"),HBoxMatrizB,SPMatrizB);

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

        
        raiz.getChildren().addAll(HBoxLinha, HBoxOpcoesA, HBoxOpcoesB, BCalular, HBoxResultado);
        ScrollPane SPRaiz = new ScrollPane(raiz);
        //AbaMatrix.setContent(raiz); // adicionando toda as opcoes na Aba Matriz
        AbaMatrix.setContent(SPRaiz); // adicionando toda as opcoes na Aba Matriz
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
					
					geraTabelaMatriz(GPMatrizA, TabelaMatrizA, linhaMatrA, colunaMatrA); //gera a tabela com os TextField's
					
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
					
					geraTabelaMatriz(GPMatrizB, TabelaMatrizB,linhaMatrB,colunaMatrB);
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
				Matriz a = converteTabelaINMatriz(TabelaMatrizA, linhaMatrA, colunaMatrA);
				Matriz b = converteTabelaINMatriz(TabelaMatrizB, linhaMatrB, colunaMatrB);
				
				if(a == null || b == null) //Erro tratado em converteGridPaneINMatriz()
					return;
				
				Matriz resul = OperationMatriz.getInstance().additionMatriz(a, b);
				if(resul == null) //Erro tratato dentro do metodo additionMatriz(a, b)
					return;
				
				geraGridPaneResultado(GPMatrizResposta,resul);
				buildResultadoTwoMatrix(HBoxResultado, a, b, GPMatrizResposta, " + ");
//				opcaoSelecionada = Ultilitarios.SOMA;
//				simboloOperacao.setText("+");
			}
		  });
		
		BSubMatrizes.setOnAction(new EventHandler() {

			@Override
			public void handle(Event arg0) {
				Matriz a = converteTabelaINMatriz(TabelaMatrizA, linhaMatrA, colunaMatrA);
				Matriz b = converteTabelaINMatriz(TabelaMatrizB, linhaMatrB, colunaMatrB);
				
				if(a == null || b == null) //Erro tratado em converteGridPaneINMatriz()
					return;
				
				Matriz resul = OperationMatriz.getInstance().subMatriz(a, b);
				if(resul == null) //Erro tratato dentro do metodo subMatriz(a, b)
					return;
				
				geraGridPaneResultado(GPMatrizResposta,resul);
				buildResultadoTwoMatrix(HBoxResultado, a, b, GPMatrizResposta, "-");
				
			}
		});
		
		BMultiplicacaoMatrizes.setOnAction(new EventHandler() {

			@Override
			public void handle(Event arg0) {
				Matriz a = converteTabelaINMatriz(TabelaMatrizA, linhaMatrA, colunaMatrA);
				Matriz b = converteTabelaINMatriz(TabelaMatrizB, linhaMatrB, colunaMatrB);
				
				if(a == null || b == null) //Erro tratado em converteGridPaneINMatriz()
					return;
				
				Matriz resul = OperationMatriz.getInstance().matrixMultiplication(a, b);
				if(resul == null) //Erro tratato dentro do metodo matrixMultiplication(a, b)
					return;
				
				geraGridPaneResultado(GPMatrizResposta,resul);
				buildResultadoTwoMatrix(HBoxResultado, a, b, GPMatrizResposta, "x");
				
			}
		});
		
		BMultEscalar.setOnAction(new EventHandler() {

			@Override
			public void handle(Event arg0) {
				//Receber o valor que deve ser multiplicado e a matriz
				double scalar = 0;
				Matriz matrix = converteTabelaINMatriz(TabelaMatrizA, linhaMatrA, colunaMatrA);
				
				if(matrix == null) //Erro tratado em converteGridPaneINMatriz()
					return;
				
				Matriz resul = OperationMatriz.getInstance().scalarMultiplication(scalar, matrix);
				if(resul == null) //Erro tratato dentro do metodo scalarMultiplication()
					return;
				
				geraGridPaneResultado(GPMatrizResposta,resul);
				buildResultadoOneMatrixOneDouble(HBoxResultado, matrix,  scalar, GPMatrizResposta, "*");
//				opcaoSelecionada = Ultilitarios.ESCALAR;
//				simboloOperacao.setText("x");
				
			}	
		});//fim do EventHandler
		
		BTranposicao.setOnAction(new EventHandler() {
			@Override
			public void handle(Event arg0) {
				Matriz matrix = converteTabelaINMatriz(TabelaMatrizA, linhaMatrA, colunaMatrA);
				
				if(matrix == null) //Erro tratado em converteGridPaneINMatriz()
					return;
				
				Matriz resul = OperationMatriz.getInstance().transposition(matrix);
				if(resul == null) //Erro tratato dentro do metodo transposition(a, b)
					return;
				
				geraGridPaneResultado(GPMatrizResposta,resul);
				
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
		
		BDeterminante.setOnAction(new EventHandler() {
			@Override
			public void handle(Event arg0) {
				Matriz matrix = converteTabelaINMatriz(TabelaMatrizA, linhaMatrA, colunaMatrA);
				
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
		
	} //fim da abaMatrixConfigurar()
	
	//Recebe uma matriz e retorna um GridPane formado por Label's com os valores da Matriz recebida 
	private GridPane transformaMatrizEmGridPaneLabel(Matriz matrix) {
		Label celula;
		GridPane GPMatrizLabel = new GridPane();
		
		for(int linha = 0; linha < matrix.getLinha(); linha++) 
            for(int coluna = 0; coluna < matrix.getColuna(); coluna++){
            	celula = new Label(" " + matrix.value(linha, coluna));
            	GPMatrizLabel.setRowIndex(celula,linha);
            	GPMatrizLabel.setColumnIndex(celula, coluna);    
            	GPMatrizLabel.getChildren().add(celula);
            }
		return GPMatrizLabel;
	}
	
	//atribui valores ao GPMatrizResposta
	private void geraGridPaneResultado(GridPane GPMatrizResposta, Matriz matrix) {
		Label celula;
		GPMatrizResposta.getChildren().clear();
		for(int linha = 0; linha < matrix.getLinha(); linha++)
            for(int coluna = 0; coluna < matrix.getColuna(); coluna++){
            	celula = new Label(" " + matrix.value(linha, coluna));
            	GPMatrizResposta.setRowIndex(celula,linha);
            	GPMatrizResposta.setColumnIndex(celula, coluna);    
            	GPMatrizResposta.getChildren().add(celula);
            }
	}
	
	//Gera os resultado da operação, onde mostra as matrizes e a operacao com o resultado
	private void buildResultadoTwoMatrix(HBox HBoxResultado, Matriz matrixA, Matriz matrixB, GridPane GPMatrizResposta, String simboloOperacao) {
		GridPane GPAuxA = transformaMatrizEmGridPaneLabel(matrixA);
		GridPane GPAuxB = transformaMatrizEmGridPaneLabel(matrixB);
		
		HBoxResultado.setAlignment(Pos.CENTER);
		HBoxResultado.getChildren().clear();
		HBoxResultado.getChildren().addAll(GPAuxA, new Label(simboloOperacao), GPAuxB, new Label("="),GPMatrizResposta);
		
	}
	
	//Gera os resultado da operação, onde mostra a matriz e o numero da passado junto com a operacao e o resultado 
	private void buildResultadoOneMatrixOneDouble(HBox HBoxResultado, Matriz matrix, Double valor, GridPane GPMatrizResposta, String simboloOperacao) {
		GridPane GPMatrix = transformaMatrizEmGridPaneLabel(matrix);
		HBoxResultado.setAlignment(Pos.CENTER);
		HBoxResultado.getChildren().clear();
		HBoxResultado.getChildren().addAll(GPMatrix, new Label(simboloOperacao), new Label(""+valor), new Label(" = "),GPMatrizResposta);
	}
	
	//Gera os resultado da operação, onde mostra a matriz e o resultado 
	private void buildResultadoOneMatrix(HBox HBoxResultado, Matriz matrix, Double valor, GridPane GPMatrizResposta, String simboloOperacao) {
		GridPane GPMatrix = transformaMatrizEmGridPaneLabel(matrix);
		HBoxResultado.setAlignment(Pos.CENTER);
		HBoxResultado.getChildren().clear();
		HBoxResultado.getChildren().addAll(GPMatrix, new Label(simboloOperacao), new Label(""+valor), new Label(" = "),GPMatrizResposta);
	}
	
	//Controi o GridPane e o preenche com TextField para as Martizes A e B
	
	private void geraTabelaMatriz(GridPane GPMatriz, TextField[][] TabelaMatriz,int linha, int coluna) {
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
	
	//Resgata os valores da tabela dos TextField[][] e o converte em no tipo dado "Matriz"
	private Matriz converteTabelaINMatriz(TextField[][] TabelaMatriz,int linha, int coluna){
		
		try {
			if(linha < 1 || coluna < 1) {
				JOptionPane.showMessageDialog(null, "O numero de linhas e colunas devem ser maiores que 0!!", "Erro ao criar Matriz" , JOptionPane.ERROR_MESSAGE);
				return null;
			}
			double[][] matrix = new double[linha][coluna];
			for(int i = 0; i< linha; i++)
				for(int j = 0; j< linha; j++) 
					matrix[i][j] = Double.parseDouble(TabelaMatriz[i][j].getText());	
			return new Matriz(matrix);
			
		}catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Digite apenas numeros nos campos", "Erro ao criar Matriz" , JOptionPane.ERROR_MESSAGE);
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