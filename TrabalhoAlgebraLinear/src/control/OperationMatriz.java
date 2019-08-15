package control;

import model.Matriz;

public class OperationMatriz {
	
	/*Verifica se duas matrizes tem a mesma ordem*/
	private boolean sameOrder(Matriz a, Matriz b) {
		return (a.getLinha() == b.getLinha() ) && (a.getColuna() == b.getColuna());
	}
	
	/*Verificar se � possivel fazer a Multiplica��o*/
	private boolean verifyMult(Matriz a, Matriz b) {
		return (b.getLinha() == a.getColuna());
	}
	
	
	private void error(String tipo, String msn) {
		System.out.println("Tipo error: "+tipo+" ; Mensagem: "+msn);
	}
	
	/*Adi��o*/
	public Matriz additionMatriz(Matriz a, Matriz b) {
		return operationSumSub(true,a,b);
	}
	
	/*Subtra��o*/
	public Matriz subMatriz(Matriz a, Matriz b) {
		return operationSumSub(false,a,b);
	}
	
	/*Fun��o Auxiliar da Adi��o e Subtra��o*/
	private Matriz operationSumSub(boolean operation, Matriz a, Matriz b) {
		
		if(!sameOrder(a,b)) { 
			error("Soma ou subtra��o de Matrizes","As matrizes possuem ordem diferentes");
			return null;
		}
		
		int[][] result =  new int[a.getLinha()][a.getColuna()];
		
		for(int linha = 0; linha < a.getLinha(); linha++) 
			for(int coluna = 0; coluna < a.getColuna(); coluna++) 
				if(operation)//soma
					result[linha][coluna] = a.value(linha,coluna) + b.value(linha,coluna);
				else
					result[linha][coluna] = a.value(linha,coluna) - b.value(linha,coluna);
		
		return new Matriz(result);	
	}
	
	/*Multiplica��o por Escalar*/
	public Matriz scalarMultiplication(int scalar, Matriz matrix) {
		int[][] result =  new int[matrix.getLinha()][matrix.getColuna()];
		
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
		
		int[][] result =  new int[a.getLinha()][b.getColuna()];
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
	
	/*Transi��o*/
	public Matriz transposition(Matriz matrix) {
		int[][] result =  new int[matrix.getColuna()][matrix.getLinha()];
		
		for(int linha = 0; linha < matrix.getLinha(); linha++) 
			for(int coluna = 0; coluna < matrix.getColuna(); coluna++) 
					result[coluna][linha] = matrix.value(linha,coluna);

		return new Matriz(result);	
	}
	
	/*Gera uma matriz identidade*/
	private Matriz identity(int linha,int coluna) {
		int[][] result =  new int[linha][coluna];
		
		for(int i = 0; i < linha; i++) 
			for(int j = 0; j < coluna; j++) 
				result[i][j] = (i == j) ? 1 : 0;
		
		return new Matriz(result);
	}
	
	/*Potencia*/
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
	
	/*Inversa*/		//P�gina  130
	//A inversa de uma matriz M � igual a (1/(det M)) * (matriz adjunta de M)
	public Matriz inverse(Matriz matrix) {
		int escalar = 1/determinante(matrix);
		return scalarMultiplication(escalar, adjunta(matrix)) ;
	}
	
	/*Matriz cofatora*/
	/*  O cofator do elemento aij desta matriz A � obtido da seguinte forma:
		   				Aij=((-1)^(i+j)) * Dij
	Devemos compreender os elementos dessa express�o. O valor Aij � justamente o 
	cofator do elemento aij da matriz A, enquanto que Dij ser� o determinante da matriz 
	obtida atrav�s da matriz A, entretanto voc� dever� excluir da matriz A os elementos
	 da linha i e da coluna j. 
	*/
	public Matriz matrizCofatora(Matriz matrix) {
		if(!matrix.isSquare()) {
			error("Matriz Cofatora","A matriz n�o � quadrada");
			return null;
		}
		if(matrix.getLinha() == 1) {//O que acontece aqui?
			return null;
		}
		int[][] result =  new int[matrix.getLinha()][matrix.getLinha()];
		
		for(int linha = 0; linha < matrix.getLinha(); linha++) 
			for(int coluna = 0; coluna < matrix.getColuna(); coluna++) {
					result[linha][coluna] = (-1)^(linha+coluna) * cofator(matrix,linha,coluna);
			}
		return new Matriz(result);
	}
	
	/*Gera o determinante das linhas excluidas*/
	private int cofator(Matriz matrix,int linhaExcluida,int colunaExcluida) {
		int[][] result =  new int[matrix.getLinha()-1][matrix.getLinha()-1];
				
		for(int linha = 0; linha < matrix.getLinha(); linha++) {
			if(linha == linhaExcluida) continue;//pula
			for(int coluna = 0; coluna < matrix.getColuna(); coluna++) {
				if(coluna == colunaExcluida) continue;//pula
				result[linha][coluna] = matrix.value(linha,coluna);
			}
		}
		return determinante(new Matriz(result));
	}
	
	/*Matriz Adjunta*/
	//� a transposta da matriz de cofatores
	public Matriz adjunta(Matriz matrix) {
		return transposition(matrizCofatora(matrix));
	}
	
	/*Determinantes*/
	//Da para otimizar com cofator, p�g 87,92 e 114do livro de algebra
	public int determinante(Matriz matrix) {
		if(!matrix.isSquare()) {
			error("C�lculo de determinante","A matriz n�o � quadrada");
			return -1;
		}
		if(matrix.getLinha() == 1) {
			return matrix.value(0,0);
		}
		if(matrix.getLinha() == 2) 
			return matrix.value(0,0)*matrix.value(1,1) - matrix.value(0,1)*matrix.value(1,0); 

		//qualquer outra ordem fica recursivo
		//para ordem 3
		int deter = 0;
		/*Somando Valores*/
		//Padr�o = aij, tal que i  cresce incrementalmente, 
		//		e o j � a soma do incrementalmente mais o valor de i
		for(int j = 0; j < matrix.getLinha(); j++) {
			int mult = 1;
			for(int i= 0; i < matrix.getLinha(); i++) 
				mult *= matrix.value(i,i+j);
			deter+=mult;
		}
		
		/*Subtraindo Valores*/
		//Identificar padr�o
		for(int j = 0; j < matrix.getLinha(); j++) {
			int mult = 1;
			for(int i= 0; i < matrix.getLinha(); i++) { 
				mult *= matrix.value(i-2,i-1);
				if(i-2 < 0) {
					
				}
			}
			deter-=mult;
			
			
		}
		
		return deter;
	}
	 
	  
	 
}