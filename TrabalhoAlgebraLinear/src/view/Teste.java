package view;

import model.*;
import control.*;

public class Teste{
	public static void main(String[] args) {
		
		double[][] valoresA = {{5,0},{7,2},{2,1}};
		Matriz a = new Matriz(valoresA);
		double[][] valoresB = {{2,0},{1,(-1)},{1,2}};
		Matriz b = new Matriz(valoresB);
		
		Matriz r = OperationMatriz.getInstance().subMatriz(a, b);
		
		for(int i = 0; i<r.getLinha();i++) {
			for(int j = 0; j<r.getColuna();j++) {
				System.out.print(r.value(i, j));
			}
		}
		
	}
}
