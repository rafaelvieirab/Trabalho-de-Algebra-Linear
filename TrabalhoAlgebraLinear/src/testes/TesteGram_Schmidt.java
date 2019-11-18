package testes;

import model.Vetor;
import operation.Gram_Schmidt;

public class TesteGram_Schmidt {

	public static void main(String[] args) {
		double[][] vetores = {{1,0,0},
							{0,1,0},
							{0,0,1}};
		Vetor[] base = new Vetor[vetores.length];
		
		for(int i = 0; i < vetores.length; i++)
			base[i] = new Vetor(vetores[i]);

		Gram_Schmidt op = Gram_Schmidt.getInstance();
		base = op.orthogonalization(base);
		
		//System.out.println(base[0].toString());
		for(Vetor v : base)
			System.out.print(v.toString());
		
		
		//ctrl + 7 para comentar/descomentar
//		for(int i = 0; i < r.getLinha();i++) {
//			for(int j = 0; j<r.getColuna();j++) 
//				System.out.print(r.value(i, j) + " ");
//			System.out.println();
//		}
		
	}

}