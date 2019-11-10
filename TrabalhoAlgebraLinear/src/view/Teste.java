package view;

import model.*;
import operation.*;

public class Teste{
	
	public static void main(String[] args) {
		double[][] valoresA = {{2,1,1,0,0},
								{4,3,3,1,0},
								{8,7,9,5,0},
								{6,7,9,8,0}};

		/*Teste de escalonamento por Gauss_jordan  Origem:https://wp.ufpel.edu.br/nucleomatceng/files/2012/07/Sistemas-de-equações-lineares.pdf
		  {{2,4,16},
							   {5,-2,4},
							   {3,1,9},
							   {4,-5,-7}};
		  {{2,-8,24,18,84},
		  					   {4,-14,52,42,190}};
		 **/
		double[][] valoresB = {{2,3,0},
				   			  {8,5,0}};
		
		double[][] l = {{1,0},
						{4,1}};
		
		double[][] u = {{2,3},
						{0,-7}};
		
		
		/*Testes que deram erros - Fatoração LU*/
		/*
		 						{{2,1,1,0,0},
								{4,3,3,1,0},
								{8,7,9,5,0},
								{6,7,9,8,0}};
								
								{{2,3,0},
				   			  {8,5,0}};		
								
		 */
		double[][] c = {{1}};
		Matriz a = new Matriz(valoresA);
		Matriz b = new Matriz(valoresB);
		
		Sistema sA = new Sistema(valoresA, valoresA.length, valoresA[0].length-1);
		Sistema sB = new Sistema(valoresB, valoresB.length, valoresB[0].length-1);
		
		Matriz r = null;
		double sR[][] = new double[0][0];
		
		
		r = OperationMatriz.getInstance().matrixMultiplication(new Matriz(l), new Matriz(u));
		//sR = OperationSystem.getInstance().gaussJordan(sA).getMatrizAmpliada();
		//System.out.println(OperationSystem.getInstance().analyzeSolucion(sA));
		
		//   =>  Verficar depois
		//OperationSystem.getInstance().fatoracaoLU(sA);
		OperationSystem.getInstance().fatoracaoLU(sB);
		
		for(int i = 0; i < sR.length;i++) {
			for(int j = 0; j < sR[0].length;j++) 
				System.out.print(sR[i][j] + " ");
			System.out.println();
		}
		
		try {
			//ctrl + 7 para comentar/descomentar
			System.out.println("\nMatriz Resultado:\n");
			for(int i = 0; i<r.getLinha();i++) {
				for(int j = 0; j<r.getColuna();j++) 
					System.out.print(r.value(i, j) + " ");
				System.out.println();
			}
		} catch (NullPointerException e) {
			//System.out.println();
		}
		
		
	}
}
