package control;

import model.Matriz;

public class OperationMatriz {
	
	/*Verifica se duas matrizes tem a mesma ordem*/
	private boolean sameOrder(Matriz a, Matriz b) {
		return (a.getLinha() == b.getLinha() ) && (a.getColuna() == b.getColuna());
	}
	
	/*Verificar se é possivel fazer a Multiplicação*/
	private boolean verifyMult(Matriz a, Matriz b) {
		return (b.getLinha() == a.getColuna());
	}
	
	
	private void error(String tipo, String msn) {
		System.out.println("Tipo error: "+tipo+" ; Mensagem: "+msn);
	}
	
	/*Adição*/
	public Matriz additionMatriz(Matriz a, Matriz b) {
		return operationSumSub(true,a,b);
	}
	
	/*Subtração*/
	public Matriz subMatriz(Matriz a, Matriz b) {
		return operationSumSub(false,a,b);
	}
	
	/*Função Auxiliar da Adição e Subtração*/
	private Matriz operationSumSub(boolean operation, Matriz a, Matriz b) {
		
		if(!sameOrder(a,b)) { 
			error("Soma ou subtração de Matrizes","As matrizes possuem ordem diferentes");
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
	
	/*Multiplicação por Escalar*/
	public Matriz scalarMultiplication(int scalar, Matriz matrix) {
		int[][] result =  new int[matrix.getLinha()][matrix.getColuna()];
		
		for(int linha = 0; linha < matrix.getLinha(); linha++) 
			for(int coluna = 0; coluna < matrix.getColuna(); coluna++) 
					result[linha][coluna] = scalar * matrix.value(linha,coluna);

		return new Matriz(result);	
	}
	
	/*Multiplicação de Matrizes*/
	public Matriz matrixMultiplication(Matriz a, Matriz b) {
		//TODO testar muito
		if(!verifyMult(a,b)) { 
			error("Multiplicação de Matrizes","O número de colunas da primeira"
					+ "matriz é diferente do número de linhas da segunda matriz");
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
	
	/*Transição*/
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
			error("Potência de Matriz","A matriz não é quadrada");
			return null;
		}
		if(expoente < 0) {
			error("Potência de Matriz","O expoente é negativo");
			return null;
		}
		if(expoente == 0) 
			return identity(matrix.getLinha(),matrix.getColuna());
		
		Matriz result = matrix;
		for(int i = 1; i < expoente; i++) 
			result = matrixMultiplication(result,matrix);
		return result;
	}
	
	/*Inversa*/		//Página  130
	//A inversa de uma matriz M é igual a (1/(det M)) * (matriz adjunta de M)
	public Matriz inverse(Matriz matrix) {
		int escalar = 1/determinante(matrix);
		return scalarMultiplication(escalar, adjunta(matrix)) ;
	}
	
	/*Matriz cofatora*/
	/*  O cofator do elemento aij desta matriz A é obtido da seguinte forma:
		   				Aij=((-1)^(i+j)) * Dij
	Devemos compreender os elementos dessa expressão. O valor Aij é justamente o 
	cofator do elemento aij da matriz A, enquanto que Dij será o determinante da matriz 
	obtida através da matriz A, entretanto você deverá excluir da matriz A os elementos
	 da linha i e da coluna j. 
	*/
	public Matriz matrizCofatora(Matriz matrix) {
		if(!matrix.isSquare()) {
			error("Matriz Cofatora","A matriz não é quadrada");
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
	//É a transposta da matriz de cofatores
	public Matriz adjunta(Matriz matrix) {
		return transposition(matrizCofatora(matrix));
	}
	
	/*Determinantes*/
	//Da para otimizar com cofator, pág 87,92 e 114do livro de algebra
	public int determinante(Matriz matrix) {
		if(!matrix.isSquare()) {
			error("Cálculo de determinante","A matriz não é quadrada");
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
		//Padrão = aij, tal que i  cresce incrementalmente, 
		//		e o j é a soma do incrementalmente mais o valor de i
		for(int j = 0; j < matrix.getLinha(); j++) {
			int mult = 1;
			for(int i= 0; i < matrix.getLinha(); i++) 
				mult *= matrix.value(i,i+j);
			deter+=mult;
		}
		
		/*Subtraindo Valores*/
		//Identificar padrão
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