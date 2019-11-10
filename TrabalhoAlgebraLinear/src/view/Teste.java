package view;

import model.*;
import operation.*;

public class Teste{
	
	public static void main(String[] args) {
		double[][] valoresA = {{2,1,-2,2},
								{1,-1,4,8},
								{-1,4,-14,-22}};
		/*Teste de escalonamento por Gauss_jordan  Origem:https://wp.ufpel.edu.br/nucleomatceng/files/2012/07/Sistemas-de-equações-lineares.pdf
		  {{2,4,16},
							   {5,-2,4},
							   {3,1,9},
							   {4,-5,-7}};
		  {{2,-8,24,18,84},
		  					   {4,-14,52,42,190}};
		 **/
		double[][] valoresB = {{2,1},
				   			  {5,3}};
		double[][] c = {{1}};
		Matriz a = new Matriz(valoresA);
		Matriz b = new Matriz(valoresB);
		
		Sistema sA = new Sistema(valoresA, 3, 3);
		double x;
		Matriz r = null;
		double sR[][] = new double[0][0];
		
//		double x = OperationMatriz.getInstance().determinante(a);
//		System.out.println("Determinante: " + x);
		
		//r = OperationMatriz.getInstance().inverse(a);
		//sR = OperationSystem.getInstance().gaussJordan(sA).getMatrizAmpliada();
		//System.out.println(OperationSystem.getInstance().analyzeSolucion(sA));
		OperationSystem.getInstance().solucions(sA);
		for(int i = 0; i < sR.length;i++) {
			for(int j = 0; j < sR[0].length;j++) {
				System.out.print(sR[i][j] + " ");
			}
			System.out.println();
		}
		
//		try {
//			//ctrl + 7 para comentar/descomentar
//			for(int i = 0; i<r.getLinha();i++) {
//				for(int j = 0; j<r.getColuna();j++) {
//					System.out.print(r.value(i, j) + " ");
//				}
//				System.out.println();
//			}
//		} catch (NullPointerException e) {
//			System.out.println("Erro: Matriz nula");
//		}
		
		
	}
}
