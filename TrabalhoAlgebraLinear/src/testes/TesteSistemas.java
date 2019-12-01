package testes;

import model.Matriz;
import model.Sistema;
import operation.OperationSystem;

public class TesteSistemas {

	/*TODO*/
	/* FatoracaoLU - Não funciona
	 * Além disso, conseguir uma forma de trocar as linhas da matriz  
	 * 	=> Solucao: Matriz de Permutacao
	 * */
	public static boolean testeFU1() {
		double[][] valores = {{3,5,2},
				{0,8 ,2},
				{6,2,8}};
		
		Sistema sA = new Sistema(valores, new double[valores.length], valores.length, valores[0].length);
		OperationSystem.getInstance().fatoracaoLU(sA);
		return false;
	}
	public static void testeFU2() {
		double[][] valores = {{3,2,4},
							  {1,1,2},
							  {4,3,-2}};
		
		//Resposta
		/*
				L = {{1,0,0},
					{1/3,1,0},
					{4/3,1,1}};
				
				U = {{3,2,4},
					{0,1/3,2/3},
					{0,0,-8}};
		*/
		Sistema sA = new Sistema(valores, new double[valores.length], valores.length, valores[0].length);
		OperationSystem.getInstance().fatoracaoLU(sA);
	}	
	public static void testeFU3() {
		double[][] valores = {{2,1,1,0},
							{4,3,3,1},
							{8,7,9,5},
							{6,7,9,8}};
		
		//Resposta
		/*
				L ={{1,0,0,0},
					{2,1,0,0},
					{3,3,1,0},
					{4,4,1,1}};
				
				U ={{2,1,1,0},
					{0,1,1,1},
					{0,0,2,2},
					{0,0,0,2}};
					
		*/
		Sistema sA = new Sistema(valores, new double[valores.length], valores.length, valores[0].length);
		OperationSystem.getInstance().fatoracaoLU(sA);
	}
	public static void testeFU4() {
		double[][] valores = {{3,5,2},
							{0,8 ,2},
							{6,2,8}};

		/*L
			1 0 0
			0 1 0
			2 −1 1
		*/
		/*U
			3 5 2
			0 8 2
			0 0 6
		*/
		Sistema sA = new Sistema(valores, new double[valores.length], valores.length, valores[0].length);
		OperationSystem.getInstance().fatoracaoLU(sA);
	}
	
	public static void main(String[] args) {
		//testeFU1();
		testeFU2();
		//testeFU3();
		//testeFU4();
	}
}