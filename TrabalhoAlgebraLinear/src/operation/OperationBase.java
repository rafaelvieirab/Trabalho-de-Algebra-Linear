package operation;

import model.Sistema;
import model.Vetor;

public class OperationBase {

	private static OperationBase instance = new OperationBase();
	private OperationBase() {}
	public static OperationBase getInstance() {return instance;}
	
	//Projeta vector1 em vector2, ou seja, Faz com que vector1 seja perpendicular ao vector2
	private Vetor projection(Vetor v,Vetor w) {//v = vector1, w = vector2
		//proj(v,w) = cw = 	((v*w) /(w*w)) * w
		double vw = productInternal(v, w);
		double ww= productInternal(w, w);
		Vetor cw = w.multiplicaEscalar(vw/ww);
		return cw;
	}
	
	//Ortoganaliza a base
	public Vetor[] orthogonalization(Vetor[] base) {
		int lengthBase = base[0].getNumCoordenadas();
		Vetor[] newBase = new Vetor[lengthBase];
		
		for(int posAtual = 0; posAtual < lengthBase ; posAtual++) {
			newBase[posAtual] = base[posAtual];
			for(int anterior = 0; anterior < posAtual ; anterior++) 
				newBase[posAtual] = subtraction(newBase[posAtual], projection(base[posAtual], newBase[anterior])); 
		}
		return newBase;
	}

	//Ortoganaliza a base e normaliza todos os seus vetores 
	public Vetor[] orthonormalization(Vetor[] base) {
		Vetor[] newBase  = orthogonalization(base);
		for(int posAtual=0; posAtual<newBase.length ; posAtual++) {
			 newBase[posAtual] = newBase[posAtual].normaliza();
		}
		return newBase;
	}
	
	//Retorna o Produto Interno entre dois vetores
	public double productInternal(Vetor vector1,Vetor vector2) {
		double resultado = 0;
		for(int posCoordenada = 0; posCoordenada < vector1.getNumCoordenadas(); posCoordenada++) 
			resultado += vector1.getValorCoordenada(posCoordenada) * vector2.getValorCoordenada(posCoordenada); 
		return resultado;
	}
	
	//Retorna o vetor resultante da subtração entre dois vetores
	private Vetor subtraction(Vetor vector1,Vetor vector2) {
		double[] resultado = new double[vector1.getNumCoordenadas()];
		for(int posCoordenada = 0; posCoordenada < vector1.getNumCoordenadas(); posCoordenada++) 
			resultado[posCoordenada] = vector1.getValorCoordenada(posCoordenada) - vector2.getValorCoordenada(posCoordenada); 
		return new Vetor(resultado);
	}
	
}