package testes;

import model.Vetor;
import operation.OperationBase;

public class TesteBase {
	static OperationBase op = OperationBase.getInstance();
	
	static boolean verificaOrtogonalidade(Vetor base[]) {
		boolean isOrtogonal = true;
		for(int vectorActual = 0; vectorActual < base.length; vectorActual++) {
			for(int i = 0; i < vectorActual; i++)
				isOrtogonal = (op.productInternal(base[vectorActual], base[i]) == 0);
			for(int i = vectorActual+1; i < base.length; i++)
				isOrtogonal = (op.productInternal(base[vectorActual], base[i]) == 0);
		}
		System.out.println("isOrtogonal: " + isOrtogonal);
		return isOrtogonal;
	}
	
	public static void main(String[] args) {
		double[][] vetores = {{1,2,0},
							{0,1,0},
							{0,0,1}};
		Vetor[] base = new Vetor[vetores.length];
		
		for(int i = 0; i < vetores.length; i++)
			base[i] = new Vetor(vetores[i]);

		
		base = op.orthogonalization(base);
		verificaOrtogonalidade(base);
	}

}