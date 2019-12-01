package testes;

import model.Vetor;
import operation.OperationBase;

public class TesteBase {
	static OperationBase op = OperationBase.getInstance();
	
	static void teste1() {
		double[][] vetores = {{1,2},
							  {3,-8}};
		
		/*Base Ortogonalizada
		 	{(1,2),
		 	(-13/5, -26/5)}
		 */
		
		Vetor[] base = new Vetor[vetores.length];
		for(int i = 0; i < vetores.length; i++)
			base[i] = new Vetor(vetores[i]);
		
		imprimeBase(base);
	}
	static void teste2() {
		double[][] vetores = {{1,0,1},
							  {1,1,0},
							  {0,1,1}};
		
		/*Base Ortogonalizada
		 	{{1,0,1},
			{1/2,1,-1/2},
			{-2/3,2/3,2/3}}
		 */
		
		Vetor[] base = new Vetor[vetores.length];
		for(int i = 0; i < vetores.length; i++)
			base[i] = new Vetor(vetores[i]);
		
		imprimeBase(base);
	}
	static void teste3() {
		double[][] vetores = {{12,6,-4},
							  {-51,167,24},
							  {4,-68,-41}};
		
		/*Base Ortogonalizada
		 	{{12,6,-4},
			{-69,158,30},
			{-58/5,6/5,-33}}
		 */
		
		Vetor[] base = new Vetor[vetores.length];
		for(int i = 0; i < vetores.length; i++)
			base[i] = new Vetor(vetores[i]);
		
		imprimeBase(base);
	}
	static void imprimeBase(Vetor base[]) {
		base = op.orthogonalization(base);
		for(Vetor vector : base)
			System.out.println(vector.toString());
	}
	
	public static void main(String[] args) {
		//teste1();
		//teste2();
		teste3();
	}

}