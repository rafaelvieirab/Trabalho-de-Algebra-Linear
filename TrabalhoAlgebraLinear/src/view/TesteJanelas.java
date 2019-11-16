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
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Matriz;
import operation.OperationMatriz;
import util.Ultilitarios;

public class TesteJanelas extends Application{
	
	public  int linhaMatrA = 3;
	public  int colunaMatrA = 3;
	public  int linhaMatrB = 3;
	public  int colunaMatrB = 3;
	
	private String opcaoSelecionada = "";
	private Label simboloOperacao = new Label("");
	
	public TextField TabelaMatrizA[][] = new TextField[linhaMatrA][colunaMatrA];
	public TextField TabelaMatrizB[][] = new TextField[linhaMatrB][colunaMatrB];

	private GridPane GPRespMatrA = new GridPane();
	private GridPane GPRespMatrB = new GridPane();
	
	//TODO teste 
	public Button BTScreenSystem = new Button("Alternar para a tela de Sistemas");  
	public Button BTScreenVector = new Button("Alternar para a tela de Gran-Schmidt");
	
	public Button BGerarMatrixA = new Button("Gerar");
	public Button  BGerarMatrixB = new Button("Gerar");
	public Button  BCalular = new Button("Calcular operacao");

	public Button  BSomaMatrizes = new Button("Soma A e B");
	public Button BSubMatrizes = new Button("Subtra��o A e B");
	public Button BMultEscalar = new Button("Multiplicar A por Escalar");
	public Button BMultiplicacaoMatrizes = new Button("Multiplica A e B");
	public Button BTranposicao = new Button("Tranposicao Matriz");
	public Button BPotencia = new Button("Potencia de uma Matriz");
	public Button BInversa = new Button("Inversa de uma Matriz");
	public Button BMatrCofatora = new Button("Matriz Cofatora");
	public Button BMatrAdjunta = new Button("Matriz Adjunta");
	public Button BDeterminante = new Button("Determinantes");
	
	public GridPane GPMatrizA = new GridPane();
	public GridPane GPMatrizB = new GridPane(); 
	public GridPane GPMatrizResposta = new GridPane();
	
	private Stage stage;
	public Stage getStage() {return stage;}
	public void setStage(Stage stage) {this.stage = stage;}
	
	public TextField TFColunaMatrA = new TextField("3");
	public TextField TFLinhaMatrA = new TextField("3");
	
	
	public static void main(String[] args) {
		launch(args);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void start(Stage stagePrincipal) throws Exception {
		HBox HBButtonScreen = new HBox(50);
		HBButtonScreen.getChildren().addAll(BTScreenSystem, BTScreenVector);
		
		int DimensoesHeight =  20; // altura
		int DimensoesWidth = 50 ; // largura
		
		TFLinhaMatrA.setMaxSize(DimensoesWidth, DimensoesHeight);
		TFColunaMatrA.setMaxSize(DimensoesWidth, DimensoesHeight);
		
		final TextField TFColunaMatrB = new TextField("3");
		final TextField TFLinhaMatrB = new TextField("3");
		TFLinhaMatrB.setMaxSize(DimensoesWidth, DimensoesHeight);
		TFColunaMatrB.setMaxSize(DimensoesWidth, DimensoesHeight);
		
		
		
		geraTabelaMatriz(GPMatrizA, TabelaMatrizA, linhaMatrA, colunaMatrA);
		geraTabelaMatriz(GPMatrizB, TabelaMatrizB, linhaMatrB, colunaMatrB);
		
		ScrollPane SPMatrizA = new ScrollPane(GPMatrizA);
		SPMatrizA.setMaxHeight(200);
		SPMatrizA.setMaxWidth(375);
		
		ScrollPane SPMatrizB = new ScrollPane(GPMatrizB);
		SPMatrizB.setMaxHeight(200);
		SPMatrizB.setMaxWidth(375);
		
		VBox VBoxColunaA = new VBox(10);
		VBox VBoxColunaB = new VBox(10);
		
		VBoxColunaB.setTranslateX(100);// TODO: O que acontece quando usa-se esse valor?
	
		HBox HBoxLinha = new HBox(10);
		HBox HBoxMatrizA = new HBox();
		HBox HBoxMatrizB = new HBox();
		HBox HBoxResultado = new HBox(20);
		
		
		BGerarMatrixA.setMaxSize(80, 100);
		BGerarMatrixB.setMaxSize(80, 100);
		
		HBoxMatrizA.getChildren().addAll(new Label("Linha:  "), TFLinhaMatrA, new Label ("  X  "),new Label("Coluna:"), TFColunaMatrA, new Label("  "), BGerarMatrixA);
		HBoxMatrizB.getChildren().addAll(new Label("Linha:  "), TFLinhaMatrB, new Label ("  X  "),new Label("Coluna:"), TFColunaMatrB, new Label("  "), BGerarMatrixB);
		
		
//		VBoxColunaA.getChildren().addAll(new Label("\t\t\t\tMatriz A"),HBoxMatrizA,SPMatrizA);
		VBoxColunaB.getChildren().addAll(new Label("\t\t\t\tMatriz B"),HBoxMatrizB,SPMatrizB);

		//VBoxColunaA.setAlignment(Pos.CENTER);
		//VBoxColunaB.setAlignment(Pos.CENTER);
		
		HBoxLinha.getChildren().addAll(VBoxColunaA,VBoxColunaB);
		
		//But�es para realizar a��es em nas matrizes
		//Open - Organizando Button
		HBox HBoxOpcoesA = new HBox(30);
		HBox HBoxOpcoesB = new HBox(30);
		
		HBoxOpcoesA.getChildren().addAll(BSomaMatrizes, BSubMatrizes, BMultEscalar, BMultiplicacaoMatrizes,BTranposicao);
		HBoxOpcoesB.getChildren().addAll(BPotencia,BInversa,BMatrCofatora,BMatrAdjunta,BDeterminante);
		//Close - Organizando Button

		VBox raiz = new VBox(10);
		raiz.setTranslateX(10);
		raiz.setTranslateY(20);
		
        raiz.getChildren().addAll(HBButtonScreen, HBoxLinha, HBoxOpcoesA, HBoxOpcoesB, BCalular, HBoxResultado);
        ScrollPane SPRaiz = new ScrollPane(raiz);
        
        Scene scene = new Scene(SPRaiz, 900, 600);// Largura X altura
        stagePrincipal.setTitle("Trabalho de Algebra Linear");
		stagePrincipal.setScene(scene);
		stagePrincipal.show();
		setStage(stagePrincipal);

        //Acoes dos butoes
	    BGerarMatrixA.setOnAction(new EventHandler() {
		
			public void handle(Event arg0) {
				try {
					if(TFLinhaMatrA.getText().equals("") || TFColunaMatrA.getText().equals(""))
						throw new Exception("Digite algum valor n�merico!!!");
					
					linhaMatrA = Integer.parseInt(TFLinhaMatrA.getText());
					colunaMatrA = Integer.parseInt(TFColunaMatrA.getText());
					
					if(linhaMatrA < 0 || colunaMatrA < 0) 
						throw new Exception("Digite apenas valores n�mericos positivos");
					
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
						throw new Exception("Digite algum valor n�merico!!!");
					
					linhaMatrB = Integer.parseInt(TFLinhaMatrB.getText());
					colunaMatrB = Integer.parseInt(TFColunaMatrB.getText());

					if(linhaMatrB < 0 || colunaMatrB < 0) 
						throw new Exception("Digite apenas valores n�mericos positivos");
					
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
					
					// TODO: Isso � so um exemplo
					if(linhaMatrA != linhaMatrB && colunaMatrA != colunaMatrB) {
						System.out.println("N�o pode ser realizado soma!!!");
						return;
					}
					
					//exibindoMatrizesResultados();

					//TODO: Chamar o objeto que ira realizar a soma das matrizes
					//O resultado da soma deve ser colocado em GPMatrResultado, para ser exibido;
					for(int y = 0; y < linhaMatrB; y++){
			            for(int x = 0; x < colunaMatrB; x++){

			            	celula = new TextField();		            	
			                
			                celula.setPrefHeight(30);
			                celula.setPrefWidth(30);
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
				double scalar = Double.parseDouble(JOptionPane.showInputDialog(null, "Digite o valor do Escalars"));
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

		BPotencia.setOnAction(new EventHandler() {
			@Override
			public void handle(Event arg0) {
				// //Receber o valor que deve ser potenciado e a matriz
				//TODO - COnseguir uma forma de receber esse valor
				int expoente = Integer.parseInt(JOptionPane.showInputDialog(null, "Digite o valor do Expoente:"));
				Matriz matrix = converteTabelaINMatriz(TabelaMatrizA, linhaMatrA, colunaMatrA);
				
				if(matrix == null) //Erro tratado em converteGridPaneINMatriz()
					return;
				
				Matriz resul = OperationMatriz.getInstance().potency(matrix, expoente);
				if(resul == null) //Erro tratato dentro do metodo potency()
					return;
				
				geraGridPaneResultado(GPMatrizResposta,resul);
				buildResultadoOneMatrixOneInt(HBoxResultado, matrix, expoente, GPMatrizResposta, "^");
				
			}
		});//fim do EventHandler

		BMatrAdjunta.setOnAction(new EventHandler() {
			@Override
			public void handle(Event arg0) {
				Matriz matrix = converteTabelaINMatriz(TabelaMatrizA, linhaMatrA, colunaMatrA);
				
				if(matrix == null) //Erro tratado em converteGridPaneINMatriz()
					return;
				
				Matriz resul = OperationMatriz.getInstance().adjunta(matrix);
				if(resul == null) //Erro tratato dentro do metodo adjunta()
					return;
				
				geraGridPaneResultado(GPMatrizResposta,resul);
				GridPane GPAdjunta = transformaMatrizEmGridPaneLabel(matrix);
				
				HBoxResultado.setAlignment(Pos.CENTER);
				HBoxResultado.getChildren().clear();
				HBoxResultado.getChildren().addAll(new Label("Adjunta:"), GPAdjunta, new Label("="),GPMatrizResposta);
				
			}
		});//fim do EventHandler
		
		BMatrCofatora.setOnAction(new EventHandler() {
			@Override
			public void handle(Event arg0) {
				Matriz matrix = converteTabelaINMatriz(TabelaMatrizA, linhaMatrA, colunaMatrA);
				
				if(matrix == null) //Erro tratado em converteGridPaneINMatriz()
					return;
				
				Matriz resul = OperationMatriz.getInstance().matrizCofatora(matrix);
				if(resul == null) //Erro tratato dentro do metodo adjunta()
					return;
				
				geraGridPaneResultado(GPMatrizResposta,resul);
				GridPane GPCofatora = transformaMatrizEmGridPaneLabel(matrix);
				
				HBoxResultado.setAlignment(Pos.CENTER);
				HBoxResultado.getChildren().clear();
				HBoxResultado.getChildren().addAll(new Label("Cofatora:"), GPCofatora, new Label("="),GPMatrizResposta);
				
			}
		});//fim do EventHandler
		
		BInversa.setOnAction(new EventHandler() {
			@Override
			public void handle(Event arg0) {
				Matriz matrix = converteTabelaINMatriz(TabelaMatrizA, linhaMatrA, colunaMatrA);
				
				if(matrix == null) //Erro tratado em converteGridPaneINMatriz()
					return;
				
				Matriz resul = OperationMatriz.getInstance().inverse(matrix);
				if(resul == null) //Erro tratato dentro do metodo inverse(a, b)
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
				
				celula = new Label("  -1");
            	GPMatrix.setRowIndex(celula,0);
            	GPMatrix.setColumnIndex(celula, matrix.getColuna()+1);    
            	GPMatrix.getChildren().add(celula);
            	
				HBoxResultado.setAlignment(Pos.CENTER);
				HBoxResultado.getChildren().clear();
				HBoxResultado.getChildren().addAll(GPMatrix, new Label("="), GPMatrizResposta);
				
			}
		});//fim do EventHandler
		
	}

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
	
	//Gera os resultado da opera��o, onde mostra as matrizes e a operacao com o resultado
	private void buildResultadoTwoMatrix(HBox HBoxResultado, Matriz matrixA, Matriz matrixB, GridPane GPMatrizResposta, String simboloOperacao) {
		GridPane GPAuxA = transformaMatrizEmGridPaneLabel(matrixA);
		GridPane GPAuxB = transformaMatrizEmGridPaneLabel(matrixB);
		
		HBoxResultado.setAlignment(Pos.CENTER);
		HBoxResultado.getChildren().clear();
		HBoxResultado.getChildren().addAll(GPAuxA, new Label(simboloOperacao), GPAuxB, new Label("="),GPMatrizResposta);
		
	}
	
	//Gera os resultado da opera��o, onde mostra a matriz e o numero da passado junto com a operacao e o resultado 
	private void buildResultadoOneMatrixOneDouble(HBox HBoxResultado, Matriz matrix, Double scalar, GridPane GPMatrizResposta, String simboloOperacao) {
		GridPane GPMatrix = transformaMatrizEmGridPaneLabel(matrix);
		HBoxResultado.setAlignment(Pos.CENTER);
		HBoxResultado.getChildren().clear();
		HBoxResultado.getChildren().addAll(GPMatrix, new Label(simboloOperacao), new Label(""+scalar), new Label("="),GPMatrizResposta);
	}
	
	//Gera os resultado da opera��o, onde mostra a matriz e o expoente, junto com a operacao e o resultado 
	private void buildResultadoOneMatrixOneInt(HBox HBoxResultado, Matriz matrix, int expoente, GridPane GPMatrizResposta, String simboloOperacao) {
			GridPane GPMatrix = transformaMatrizEmGridPaneLabel(matrix);
			HBoxResultado.setAlignment(Pos.CENTER);
			HBoxResultado.getChildren().clear();
			HBoxResultado.getChildren().addAll(GPMatrix, new Label(simboloOperacao), new Label("" + expoente), new Label("="),GPMatrizResposta);
		}
	
	//Controi o GridPane e o preenche com TextField para as Martizes A e B
	private void geraTabelaMatriz(GridPane GPMatriz, TextField[][] TabelaMatriz,int linha, int coluna) {
    	TextField celula;
    	for(int y = 0; y < linha; y++)
    		for(int x = 0; x < coluna; x++){

    			celula = new TextField();
    			TabelaMatriz[y][x] = celula;

    			// Create a new TextField in each Iteration
    			celula.setPrefHeight(30);
    			celula.setPrefWidth(30);
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
	
}