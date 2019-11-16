package operation;

import javax.swing.JOptionPane;

import model.Matriz;
import model.Sistema;

public class OperationMatriz {
	
	private static OperationMatriz instance = new OperationMatriz();
	
	private OperationMatriz() {}

	public static OperationMatriz getInstance() {
		return instance;
	}
	
	//Funções
	
	/*Verifica se duas matrizes tem a mesma Ordem (Número de Linhas e Colunas)*/
	private boolean sameOrder(Matriz a, Matriz b) {
		return (a.getLinha() == b.getLinha()) && (a.getColuna() == b.getColuna());
	}
	
	/*Verificar se é Possivel fazer a Multiplicação*/
	private boolean verifyMult(Matriz a, Matriz b) {
		return (a.getColuna() == b.getLinha());
	}
	
	/*Imprime um Erro*/
	private void error(String operation, String msg) {
		//TODO - Mudar esse null, pela janela da aplicação
		JOptionPane.showMessageDialog(null, msg, "Erro na Operação de " + operation , JOptionPane.ERROR_MESSAGE);
	}
	
	/*Operação de Adição de Martizes*/
	public Matriz additionMatriz(Matriz a, Matriz b) {
		if(a == null && b == null) {
			error("Soma de Matrizes", "As matrizes estão vazias");
			return null;
		}
		if(a == null) {
			error("Soma de Matrizes", "A matriz A está vazia");
			return null;
		}
		if(b == null) {
			error("Soma de Matrizes", "A matriz  B está vazia");
			return null;
		}
		if(!sameOrder(a,b)) { 
			error("Soma de Matrizes","As matrizes possuem ordens diferentes");
			return null;
		}
		double[][] result =  new double[a.getLinha()][a.getColuna()];
		
		for(int linha = 0; linha < a.getLinha(); linha++) 
			for(int coluna = 0; coluna < a.getColuna(); coluna++) 
				result[linha][coluna] = a.value(linha,coluna) + b.value(linha,coluna);
		return new Matriz(result);	
	}
	
	/*Operação de Subtração de Martizes*/
	public Matriz subMatriz(Matriz a, Matriz b) {
		if(a == null && b == null) {
			error("Subtração de Matrizes", "As matrizes estão vazias");
			return null;
		}
		if(a == null) {
			error("Subtração de Matrizes", "A 1° matriz está vazia");
			return null;
		}
		if(b == null) {
			error("Subtração", "A 2° matriz está vazia");
			return null;
		}
		if(!sameOrder(a,b)) { 
			error("Subtração de Matrizes","As matrizes possuem ordem diferentes");
			return null;
		}
		double[][] result =  new double[a.getLinha()][a.getColuna()];
		
		for(int linha = 0; linha < a.getLinha(); linha++) 
			for(int coluna = 0; coluna < a.getColuna(); coluna++) 
				result[linha][coluna] = a.value(linha,coluna) - b.value(linha,coluna);
		return new Matriz(result);	
	}
	
	/*Multiplicação por Escalar*/
	public Matriz scalarMultiplication(double scalar, Matriz matrix) {
		if(matrix == null) {
			error("Multiplicação por Escalar", "A matriz A está vazia");
			return null;
		}
		double[][] result =  new double[matrix.getLinha()][matrix.getColuna()];
		
		for(int linha = 0; linha < matrix.getLinha(); linha++) 
			for(int coluna = 0; coluna < matrix.getColuna(); coluna++) 
					result[linha][coluna] = scalar * matrix.value(linha,coluna);

		return new Matriz(result);	
	}
	
	/*Multiplicação de Matrizes*/
	public Matriz matrixMultiplication(Matriz a, Matriz b) {
		//TODO testar muito
		if(a == null && b == null) {
			error("Multiplicação de Matrizes", "As matrizes estão vazias");
			return null;
		}
		if(a == null) {
			error("Multiplicação de Matrizes", "A 1° matriz está vazia");
			return null;
		}
		if(b == null) {
			error("Multiplicação de Matrizes", "A 2° matriz está vazia");
			return null;
		}
		if(!verifyMult(a,b)) { 
			error("Multiplicação de Matrizes","O número de colunas da 1° matriz"
					+ " é diferente do número de linhas da 2° matriz");
			return null;
		}
		
		double[][] result =  new double[a.getLinha()][b.getColuna()];
		int soma;
		
		for(int linha = 0; linha < a.getLinha(); linha++) 
			for(int coluna = 0; coluna < b.getColuna(); coluna++) {
				soma = 0;
				for(int i = 0; i < b.getLinha(); i++) 
					soma += a.value(linha, i) * b.value(i, coluna) ;
				result[linha][coluna] = soma;
			}
		return new Matriz(result);	
	}
	
	/*Transposição*/
	public Matriz transposition(Matriz matrix) {
		if(matrix == null) {
			error("Transposição de Matrizes", "a matriz está vazia");
			return null;
		}
		double[][] result =  new double[matrix.getColuna()][matrix.getLinha()];
		
		for(int linha = 0; linha < matrix.getLinha(); linha++) 
			for(int coluna = 0; coluna < matrix.getColuna(); coluna++) 
				result[coluna][linha] = matrix.value(linha,coluna);

		return new Matriz(result);	
	}
	
	/*Gera uma matriz identidade*/
	private Matriz identity(int linha,int coluna) {
		double[][] result =  new double[linha][coluna];
		
		for(int i = 0; i < linha; i++) {
			int j;
			for(j = 0; j < i; j++) 
				result[i][j] = 0;
			result[i][j] = 1;
			for(j = i+1; j < coluna; j++) 
				result[i][j] = 0;
		}
		return new Matriz(result);
	}
	
	/*Potência*/
	public Matriz potency(Matriz matrix, int expoente) {
		if(!matrix.isSquare()) { 
			error("Potência de Matriz","A matriz não é quadrada");
			return null;
		}
		if(expoente == 0) //retorna a matriz Identidade da mesma ordem de matrix
			return identity(matrix.getLinha(),matrix.getColuna());
		
		if(expoente < 0) //Retorna o inverso da matriz elevado ao expoente
			return potency(inverse(matrix), -expoente);
		
		Matriz result = matrix;
		for(int i = 1; i < expoente; i++) 
			result = matrixMultiplication(result,matrix);
		return result;
	}
	
	/*Inversa*/
	public Matriz inverse(Matriz matrix) {
		if(!matrix.isSquare()) {
			error("Inversão de Matrizes","A matriz não é quadrada!");
			return null;
		}
		double det = determinante(matrix);
		if(det == 0) {
			error("Inversão de Matrizes","A matriz tem determinante igual a 0,"
					+ " logo não admie inversa!");
			return null;
		}
		return scalarMultiplication(1/det, adjunta(matrix)) ;
	}
	
	/*Matriz cofatora*/
	public Matriz matrizCofatora(Matriz matrix) {
		if(!matrix.isSquare()) {
			error("Matriz Cofatora","A matriz não é quadrada");
			return null;
		}
		if(matrix.getLinha() == 1) { //o que acontece aqui?
			return matrix;
		}
		
		double[][] result =  new double[matrix.getLinha()][matrix.getLinha()];
		
		for(int linha = 0; linha < matrix.getLinha(); linha++) 
			for(int coluna = 0; coluna < matrix.getColuna(); coluna++) {
					result[linha][coluna] = cofator(matrix,linha,coluna);
			}
		return new Matriz(result);
	}
	
	/*Gera o cofator com o determinante das filas excluidas*/
	private double cofator(Matriz matrix,int linhaExcluida,int colunaExcluida) {
		/*  O cofator do elemento aij desta matriz A é obtido da seguinte forma:
				Aij=((-1)^(i+j)) * Dij
		* Aij: cofator do elemento aij da matriz A
		* Dij: determinante da matriz A, excluindo a linha i e a coluna j. 
		*/
		double[][] result =  new double[matrix.getLinha()-1][matrix.getLinha()-1];
				
		for(int linha = 0; linha < linhaExcluida; linha++) {
			for(int coluna = 0; coluna < colunaExcluida; coluna++) 
				result[linha][coluna] = matrix.value(linha,coluna);
			for(int coluna = colunaExcluida; coluna < matrix.getColuna()-1; coluna++) {
				result[linha][coluna] = matrix.value(linha,coluna+1);
			}
		}
		for(int linha = linhaExcluida; linha < matrix.getLinha()-1; linha++) {
			for(int coluna = 0; coluna < colunaExcluida; coluna++) 
				result[linha][coluna] = matrix.value(linha+1,coluna);
			for(int coluna = colunaExcluida; coluna < matrix.getColuna()-1; coluna++) {
				result[linha][coluna] = matrix.value(linha+1,coluna+1);
			}
		}
		double det = determinante(new Matriz(result));
		return ((linhaExcluida+colunaExcluida+2) % 2 == 0) ? det : -det;
		//Obs.: Soma 2 pq comecamos a contar de 0, e não de 1, e como são dois valores, então 2*1 =2.
	}
	
	/*Matriz Adjunta*/
	public Matriz adjunta(Matriz matrix) {
		if(!matrix.isSquare()) {
			error("Matriz Adjunta","A matriz não é quadrada");
			return null;
		}
		return transposition(matrizCofatora(matrix));
	}
	
	/*Determinante*/
	public double determinante(Matriz matrix) {
		if(!matrix.isSquare()) {
			error("Cálculo do Determinante","A matriz não é quadrada!");
			return 0;
		}
		if(matrix.getLinha() == 0) return 0;
		if(matrix.getLinha() == 1) return matrix.value(0,0);
		if(matrix.getLinha() == 2) 
			return matrix.value(0,0)*matrix.value(1,1) - matrix.value(0,1)*matrix.value(1,0); 
		
		/*Cria uma nova matriz com os mesmos valores da anterior,
		 * só que com uma coluna a mais, formada somente de 0's*/
		double newMatriz[][] = new double[matrix.getLinha()][matrix.getColuna()+1];
		for(int i = 0 ; i < matrix.getLinha(); i++) {
			int j = 0;
			for(; j < matrix.getColuna(); j++) 
				newMatriz[i][j] = matrix.value(i, j);
			newMatriz[i][j] = 0;
		}
		
		//calcula o determinante a partir da diagonal principal
		double diagonal[][] = OperationSystem.getInstance().gauss(new Sistema(newMatriz,matrix.getLinha(),matrix.getColuna())).getMatrizAmpliada();
		double det = 1;
		for(int i = 0; i < diagonal.length; i++) 
			det *= diagonal[i][i]; 
		
		return det;
	}
		 
}