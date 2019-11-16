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
	
	//Fun��es
	
	/*Verifica se duas matrizes tem a mesma Ordem (N�mero de Linhas e Colunas)*/
	private boolean sameOrder(Matriz a, Matriz b) {
		return (a.getLinha() == b.getLinha()) && (a.getColuna() == b.getColuna());
	}
	
	/*Verificar se � Possivel fazer a Multiplica��o*/
	private boolean verifyMult(Matriz a, Matriz b) {
		return (a.getColuna() == b.getLinha());
	}
	
	/*Imprime um Erro*/
	private void error(String operation, String msg) {
		//TODO - Mudar esse null, pela janela da aplica��o
		JOptionPane.showMessageDialog(null, msg, "Erro na Opera��o de " + operation , JOptionPane.ERROR_MESSAGE);
	}
	
	/*Opera��o de Adi��o de Martizes*/
	public Matriz additionMatriz(Matriz a, Matriz b) {
		if(a == null && b == null) {
			error("Soma de Matrizes", "As matrizes est�o vazias");
			return null;
		}
		if(a == null) {
			error("Soma de Matrizes", "A matriz A est� vazia");
			return null;
		}
		if(b == null) {
			error("Soma de Matrizes", "A matriz  B est� vazia");
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
	
	/*Opera��o de Subtra��o de Martizes*/
	public Matriz subMatriz(Matriz a, Matriz b) {
		if(a == null && b == null) {
			error("Subtra��o de Matrizes", "As matrizes est�o vazias");
			return null;
		}
		if(a == null) {
			error("Subtra��o de Matrizes", "A 1� matriz est� vazia");
			return null;
		}
		if(b == null) {
			error("Subtra��o", "A 2� matriz est� vazia");
			return null;
		}
		if(!sameOrder(a,b)) { 
			error("Subtra��o de Matrizes","As matrizes possuem ordem diferentes");
			return null;
		}
		double[][] result =  new double[a.getLinha()][a.getColuna()];
		
		for(int linha = 0; linha < a.getLinha(); linha++) 
			for(int coluna = 0; coluna < a.getColuna(); coluna++) 
				result[linha][coluna] = a.value(linha,coluna) - b.value(linha,coluna);
		return new Matriz(result);	
	}
	
	/*Multiplica��o por Escalar*/
	public Matriz scalarMultiplication(double scalar, Matriz matrix) {
		if(matrix == null) {
			error("Multiplica��o por Escalar", "A matriz A est� vazia");
			return null;
		}
		double[][] result =  new double[matrix.getLinha()][matrix.getColuna()];
		
		for(int linha = 0; linha < matrix.getLinha(); linha++) 
			for(int coluna = 0; coluna < matrix.getColuna(); coluna++) 
					result[linha][coluna] = scalar * matrix.value(linha,coluna);

		return new Matriz(result);	
	}
	
	/*Multiplica��o de Matrizes*/
	public Matriz matrixMultiplication(Matriz a, Matriz b) {
		//TODO testar muito
		if(a == null && b == null) {
			error("Multiplica��o de Matrizes", "As matrizes est�o vazias");
			return null;
		}
		if(a == null) {
			error("Multiplica��o de Matrizes", "A 1� matriz est� vazia");
			return null;
		}
		if(b == null) {
			error("Multiplica��o de Matrizes", "A 2� matriz est� vazia");
			return null;
		}
		if(!verifyMult(a,b)) { 
			error("Multiplica��o de Matrizes","O n�mero de colunas da 1� matriz"
					+ " � diferente do n�mero de linhas da 2� matriz");
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
	
	/*Transposi��o*/
	public Matriz transposition(Matriz matrix) {
		if(matrix == null) {
			error("Transposi��o de Matrizes", "a matriz est� vazia");
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
	
	/*Pot�ncia*/
	public Matriz potency(Matriz matrix, int expoente) {
		if(!matrix.isSquare()) { 
			error("Pot�ncia de Matriz","A matriz n�o � quadrada");
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
			error("Invers�o de Matrizes","A matriz n�o � quadrada!");
			return null;
		}
		double det = determinante(matrix);
		if(det == 0) {
			error("Invers�o de Matrizes","A matriz tem determinante igual a 0,"
					+ " logo n�o admie inversa!");
			return null;
		}
		return scalarMultiplication(1/det, adjunta(matrix)) ;
	}
	
	/*Matriz cofatora*/
	public Matriz matrizCofatora(Matriz matrix) {
		if(!matrix.isSquare()) {
			error("Matriz Cofatora","A matriz n�o � quadrada");
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
		/*  O cofator do elemento aij desta matriz A � obtido da seguinte forma:
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
		//Obs.: Soma 2 pq comecamos a contar de 0, e n�o de 1, e como s�o dois valores, ent�o 2*1 =2.
	}
	
	/*Matriz Adjunta*/
	public Matriz adjunta(Matriz matrix) {
		if(!matrix.isSquare()) {
			error("Matriz Adjunta","A matriz n�o � quadrada");
			return null;
		}
		return transposition(matrizCofatora(matrix));
	}
	
	/*Determinante*/
	public double determinante(Matriz matrix) {
		if(!matrix.isSquare()) {
			error("C�lculo do Determinante","A matriz n�o � quadrada!");
			return 0;
		}
		if(matrix.getLinha() == 0) return 0;
		if(matrix.getLinha() == 1) return matrix.value(0,0);
		if(matrix.getLinha() == 2) 
			return matrix.value(0,0)*matrix.value(1,1) - matrix.value(0,1)*matrix.value(1,0); 
		
		/*Cria uma nova matriz com os mesmos valores da anterior,
		 * s� que com uma coluna a mais, formada somente de 0's*/
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