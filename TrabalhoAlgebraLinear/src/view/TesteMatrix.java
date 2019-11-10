package view;

import model.Matriz;
import operation.OperationMatriz;

public class TesteMatrix {
	/*TODO -Matrizes*/
	/*
	 * Matriz Cofatora: caso base - quando a ordem da matriz é 1
	 * */
	public static void main(String[] args) {
		double[][] valoresA = {{0,1,-1,1,0},
								{1,1,-1,2,0}};
		
		double[][] valoresB = {{0,1,-1,1,0},
								{1,1,-1,2,0}};
		
		Matriz a = new Matriz(valoresA);
		Matriz b = new Matriz(valoresB);
		
		Matriz r = null;
		r = OperationMatriz.getInstance().matrixMultiplication(a, b);
		

		double[][] c = {{1}}; //Teste da matriz cofatora, no caso base
		
		try { 
			//ctrl + 7 para comentar/descomentar
			for(int i = 0; i < r.getLinha();i++) {
				for(int j = 0; j<r.getColuna();j++) 
					System.out.print(r.value(i, j) + " ");
				System.out.println();
			}
		} catch (NullPointerException e) {
			//System.out.println();
		}
		
	}

}
