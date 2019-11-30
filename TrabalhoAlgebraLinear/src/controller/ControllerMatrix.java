package controller;

import javax.swing.JOptionPane;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import model.Matriz;

public class ControllerMatrix {
	private static ControllerMatrix instance = new ControllerMatrix();
	private ControllerMatrix() {}
	public static ControllerMatrix getInstance() {return instance;}
	

	//Recebe uma matriz e retorna um GridPane formado por Label's com os valores da Matriz recebida 
	public GridPane transformaMatrizEmGridPaneLabel(Matriz matrix) {
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
	public void geraGridPaneResultado(GridPane GPMatrizResposta, Matriz matrix) {
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
	public void buildResultadoTwoMatrix(HBox HBoxResultado, Matriz matrixA, Matriz matrixB, GridPane GPMatrizResposta, String simboloOperacao) {
		GridPane GPAuxA = transformaMatrizEmGridPaneLabel(matrixA);
		GridPane GPAuxB = transformaMatrizEmGridPaneLabel(matrixB);
		
		HBoxResultado.setAlignment(Pos.CENTER);
		HBoxResultado.getChildren().clear();
		HBoxResultado.getChildren().addAll(GPAuxA, new Label(simboloOperacao), GPAuxB, new Label("="),GPMatrizResposta);
		
	}
	
	//Gera os resultado da operação, onde mostra a matriz e o numero da passado junto com a operacao e o resultado 
	public void buildResultadoOneMatrixWithScalar(HBox HBoxResultado, Matriz matrix, Double scalar, GridPane GPMatrizResposta) {
		GridPane GPMatrix = transformaMatrizEmGridPaneLabel(matrix);
		HBoxResultado.setAlignment(Pos.CENTER);
		HBoxResultado.getChildren().clear();
		HBoxResultado.getChildren().addAll(GPMatrix, new Label("*"), new Label(""+scalar), new Label("="),GPMatrizResposta);
	}
	
	//Gera os resultado da operação, onde mostra a matriz e o expoente, junto com a operacao e o resultado 
	public void buildResultadoOneMatrixPotency(HBox HBoxResultado, Matriz matrix, int expoente, GridPane GPMatrizResposta) {
			GridPane GPMatrix = transformaMatrizEmGridPaneLabel(matrix);
			HBoxResultado.setAlignment(Pos.CENTER);
			HBoxResultado.getChildren().clear();
			HBoxResultado.getChildren().addAll(GPMatrix, new Label("^"), new Label("" + expoente), new Label("="),GPMatrizResposta);
		}
	
	//Controi o GridPane e o preenche com TextField para as Martizes A e B
	public void geraTabelaMatriz(GridPane GPMatriz, TextField[][] TabelaMatriz,int linha, int coluna) {
    	TextField celula;
    	for(int y = 0; y < linha; y++)
    		for(int x = 0; x < coluna; x++){

    			celula = new TextField();
    			TabelaMatriz[y][x] = celula;

    			celula.setPrefHeight(30);
    			celula.setPrefWidth(30);
    			celula.setEditable(true);
    			celula.setText("0");

    			GPMatriz.setRowIndex(celula,y);
    			GPMatriz.setColumnIndex(celula, x);    
    			GPMatriz.getChildren().add(celula);
    		}
    	
    }
	
	//Resgata os valores da tabela dos TextField[][] e o converte em no tipo dado "Matriz"
	public Matriz converteTabelaINMatriz(TextField[][] TabelaMatriz,int linha, int coluna){
		
		try {
			if(linha < 1 || coluna < 1) {
				JOptionPane.showMessageDialog(null, "O numero de linhas e colunas devem ser maiores que 0!!", "Erro ao criar Matriz" , JOptionPane.ERROR_MESSAGE);
				return null;
			}
			double[][] matrix = new double[linha][coluna];
			
			for(int i = 0; i< linha; i++)
				for(int j = 0; j< coluna; j++) 
					matrix[i][j] = Double.parseDouble(TabelaMatriz[i][j].getText());	
			return new Matriz(matrix);
			
		}catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Digite apenas numeros nos campos", "Erro ao criar Matriz" , JOptionPane.ERROR_MESSAGE);
		}
		
		return null;
	}
	
}
