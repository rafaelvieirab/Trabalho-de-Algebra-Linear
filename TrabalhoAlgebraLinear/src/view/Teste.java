package view;

import model.*;
import operation.*;

public class Teste{
	
	public static void main(String[] args) {
		double[][] valoresA = {{0,1,0},
							   {2,1,0},
							   {1,2,1}};

		double[][] valoresB = {{2,1},
				   			  {5,3}};
		double[][] c = {{1}};
		Matriz a = new Matriz(valoresA);
		Matriz b = new Matriz(valoresB);
		
		
		double x = OperationMatriz.getInstance().determinante(a);
		System.out.println("Determinante: " + x);
		
		Matriz r = null;
		//r = OperationMatriz.getInstance().inverse(a);
		
		try {
			//ctrl + 7 para comentar/descomentar
			for(int i = 0; i<r.getLinha();i++) {
				for(int j = 0; j<r.getColuna();j++) {
					System.out.print(r.value(i, j) + " ");
				}
				System.out.println();
			}
		} catch (NullPointerException e) {
			System.out.println("Erro: Matriz nula");
		}
		
		
	}
}
