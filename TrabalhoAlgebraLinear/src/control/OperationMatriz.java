package control;

import model.Matriz;

public class OperationMatriz {
	
	/*Verifica se duas matrizes tem a mesma ordem*/
	private boolean sameOrder(Matriz a, Matriz b) {
		return (a.getLinha() == b.getLinha() ) && (a.getColuna() == b.getColuna());
	}
	
	/*Verificar se a Matriz a e a Matriz b, possuem as caracteristicas de nescessaria 
	 * para a Multiplicação*/
	private boolean verifyMult(Matriz a, Matriz b) {
		return (b.getLinha() == a.getColuna());
	}
	
	
	private void error(String tipo, String msn) {
		System.out.println("Tipo error: "+tipo+" ; Mensagem: "+msn);
	}
	
	public Matriz additionMatriz(Matriz a, Matriz b) {
		return operationSumSub(true,a,b);
	}
	
	public Matriz subMatriz(Matriz a, Matriz b) {
		return operationSumSub(false,a,b);
	}
	
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
	
	public Matriz scalarMultiplication(int scalar, Matriz matrix) {
		int[][] result =  new int[matrix.getLinha()][matrix.getColuna()];
		
		for(int linha = 0; linha < matrix.getLinha(); linha++) 
			for(int coluna = 0; coluna < matrix.getColuna(); coluna++) 
					result[linha][coluna] = scalar * matrix.value(linha,coluna);

		return new Matriz(result);	
	}
	
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
	
}