package control;

import model.Matriz;

public class OperationMatriz {
	//Singleton ou Deixa todos os met�dos est�ticos
	private static OperationMatriz instance = new OperationMatriz();
	
	private OperationMatriz() {}

	public static OperationMatriz getInstance() {
		return instance;
	}
	
	//Fun��es
	
	/*Verifica se duas matrizes tem a mesma Ordem (N�mero de Linhas e Colunas)*/
	private boolean sameOrder(Matriz a, Matriz b) {
		return (a.getLinha() == b.getLinha() ) && (a.getColuna() == b.getColuna());
	}
	
	/*Verificar se � Possivel fazer a Multiplica��o*/
	private boolean verifyMult(Matriz a, Matriz b) {
		return (b.getLinha() == a.getColuna());
	}
	
	/*Imprime um Erro*/
	private void error(String tipo, String msn) {
		System.out.println("Tipo error: "+tipo+" ; Mensagem: "+msn);
	}
	
	/*Opera��o de Adi��o de Martizes*/
	public Matriz additionMatriz(Matriz a, Matriz b) {
		if(!sameOrder(a,b)) { 
			error("Soma ou subtra��o de Matrizes","As matrizes possuem ordens diferentes");
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
		if(!sameOrder(a,b)) { 
			error("Soma ou subtra��o de Matrizes","As matrizes possuem ordem diferentes");
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
		double[][] result =  new double[matrix.getLinha()][matrix.getColuna()];
		
		for(int linha = 0; linha < matrix.getLinha(); linha++) 
			for(int coluna = 0; coluna < matrix.getColuna(); coluna++) 
					result[linha][coluna] = scalar * matrix.value(linha,coluna);

		return new Matriz(result);	
	}
	
	/*Multiplica��o de Matrizes*/
	public Matriz matrixMultiplication(Matriz a, Matriz b) {
		//TODO testar muito
		if(!verifyMult(a,b)) { 
			error("Multiplica��o de Matrizes","O n�mero de colunas da primeira"
					+ "matriz � diferente do n�mero de linhas da segunda matriz");
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
		double[][] result =  new double[matrix.getColuna()][matrix.getLinha()];
		
		for(int linha = 0; linha < matrix.getLinha(); linha++) 
			for(int coluna = 0; coluna < matrix.getColuna(); coluna++) 
				result[coluna][linha] = matrix.value(linha,coluna);

		return new Matriz(result);	
	}
	
	/*Gera uma matriz identidade*/
	private Matriz identity(int linha,int coluna) {
		double[][] result =  new double[linha][coluna];
		
		for(int i = 0; i < linha; i++) 
			for(int j = 0; j < coluna; j++) 
				result[i][j] = (i == j) ? 1 : 0;
		
		return new Matriz(result);
	}
	
	/*Pot�ncia*/
	public Matriz potency(Matriz matrix, int expoente) {
		if(!matrix.isSquare()) { 
			error("Pot�ncia de Matriz","A matriz n�o � quadrada");
			return null;
		}
		if(expoente < 0) {
			error("Pot�ncia de Matriz","O expoente � negativo");
			return null;
		}
		if(expoente == 0) 
			return identity(matrix.getLinha(),matrix.getColuna());
		
		Matriz result = matrix;
		for(int i = 1; i < expoente; i++) 
			result = matrixMultiplication(result,matrix);
		return result;
	}
	
	/*Inversa*/		//P�gina  130 do Livro
	//A inversa de uma matriz M � igual a (1/(det M)) * (matriz adjunta de M)
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
		if(matrix.getLinha() == 1) {
			Matriz result = new Matriz(1,1);
			result.setValue(0, 0, 1/matrix.value(0, 0));
			return result;
		}
		if(matrix.getLinha() == 2) {
			double[][] result = new double[2][2];
			result[0][0] =  matrix.value(1, 1) / det;
			result[0][1] =  -matrix.value(0, 1) / det;
			result[1][0] =  -matrix.value(1, 0) / det;
			result[1][1] =  matrix.value(0, 0) / det;
			return new Matriz(result);
		}
		double escalar = 1/det;
		return scalarMultiplication(escalar, adjunta(matrix)) ;
	}
	
	/*Matriz cofatora*/
	public Matriz matrizCofatora(Matriz matrix) {
		/*  O cofator do elemento aij desta matriz A � obtido da seguinte forma:
					Aij=((-1)^(i+j)) * Dij
		 * Aij: cofator do elemento aij da matriz A
		 * Dij: determinante da matriz obtida atrav�s da matriz A, excluindo a linha i e a coluna j. 
		 */
		if(!matrix.isSquare()) {
			error("Matriz Cofatora","A matriz n�o � quadrada");
			return null;
		}
		if(matrix.getLinha() == 1) {//O que acontece aqui?
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
		return ((-1)^(linhaExcluida+colunaExcluida+2)) * determinante(new Matriz(result));
		//Obs.: Soma 2 pq comecamos a contar de 0, e n�o de 1
	}
	
	/*Matriz Adjunta*/
	public Matriz adjunta(Matriz matrix) {
		//� a transposta da matriz de cofatores
		return transposition(matrizCofatora(matrix));
	}
	
	/*Determinante*/
	public double determinante(Matriz matrix) {
		if(!matrix.isSquare()) {
			error("C�lculo de determinante","A matriz n�o � quadrada!");
			return 0;
		}
		if(matrix.getLinha() == 1) 
			return matrix.value(0,0);
		if(matrix.getLinha() == 2) 
			return matrix.value(0,0)*matrix.value(1,1) - matrix.value(0,1)*matrix.value(1,0); 

		//qualquer outra ordem fica recursivo
		
		Matriz result = new Matriz(matrix.getLinha(),matrix.getColuna());
		//Regra de Chi�
		if(matrix.value(0,0) == 0) {
			for(int i = 1; i < matrix.getLinha(); i++)
				if(matrix.value(i,0) != 0) {
					//trocar linhas
				}
			for(int i = 1; i < matrix.getColuna(); i++)
				if(matrix.value(0,i) != 0) {
					//trocar colunas
				}
			System.out.println("N�o sei o que fazer");
		}
		
		double elem = 1 / matrix.value(0,0);
		//Multiplicando a 1 coluna
		if(matrix.value(0,0) != 1) {
			for(int i = 0; i < matrix.getLinha(); i++)
				result.setValue(i, 0, elem * matrix.value(i,0));
		}
		
		for(int i = 1; i < matrix.getLinha(); i++)
			for(int j = 1; j < matrix.getColuna(); j++)
				result.setValue(i, j, matrix.value(i,j) - matrix.value(0, i) * matrix.value(i,0));
		
		return elem * determinante(result);
	}
	 
}