package operation;

import model.Sistema;
import model.Vetor;

public class Gram_Schmidt {

	private static Gram_Schmidt instance = new Gram_Schmidt();
	private Gram_Schmidt() {}
	public static Gram_Schmidt getInstance() {return instance;}
	
	//Projeta vector1 em vector2, ou seja, Faz com que vector1 seja perpendicular ao vector2
	private Vetor projection(Vetor v,Vetor w) {//v = vector1, w = vector2
		//proj(v,w) = cw = 	((v*w) /(w*w)) * w
		double vw = productInternal(v, w);
		double ww= w.modulo();
		Vetor cw = w.multiplicaEscalar(vw/ww);
		return cw;
	}
	
	//Ortoganaliza a base
	public Vetor[] orthogonalization(Vetor[] base) {
		base = verificaDependencia(base); //implementar depois
		int lengthBase = base[0].getNumCoordenadas();
		Vetor[] newBase = new Vetor[lengthBase];
		
		for(int posAtual = 0; posAtual < lengthBase ; posAtual++) {
			newBase[posAtual] = base[posAtual];
			for(int anterior = 0; anterior < posAtual ; anterior++) 
				newBase[posAtual] = subtraction(newBase[posAtual], projection(base[posAtual], base[anterior])); 
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
	
	//Verifica se cada vetor é combinação linear dos outros
	private Vetor[] verificaDependencia(Vetor[] base) {
		Sistema system = vectorArrayToSystem(base);
		return completeBase(system);
	}
	
	//Transforma os vetores em um sistema escalonado
	private Sistema vectorArrayToSystem(Vetor[] base) {
		double[][] coordenadas = new double[base.length][base[0].getNumCoordenadas()];
		
		for(int i = 0; i< base.length; i++) 
			for(int j = 0; j< base[0].getNumCoordenadas(); j++) 
				coordenadas[i][j] = base[i].getValorCoordenada(j);
		
		Sistema system = new Sistema(coordenadas,new double[base.length], base.length,base[0].getNumCoordenadas());
		return OperationSystem.getInstance().gauss(system);
	}
	
	/*
	private Vetor[] systemToVectorArray(Sistema system) {
		Vetor[] vectorArray = new Vetor[system.getNumEq()];
		double[][] coordenadas = system.getMatrizCoef();
		
		for(int i = 0; i< system.getNumEq(); i++) 
			vectorArray[i] = new Vetor(coordenadas[i]);
		return vectorArray;
	}
	*/
	
	//TODO - Completa a base com os vetores independentes restantes
	private Vetor[] completeBase(Sistema system) {
		double[][] array = system.getMatrizCoef();
		
		for(int i = 0 ; i < array.length; i++) 
			if(array[i][i] == 0) {//Sobrescreve a linha atual, criando o vetor independe que estava faltando
				for(int j = 0; j < system.getNumIncog(); j++) 
					array[i][j] = 0;
				array[i][i] = 1;
			}
		
		//Transformando o array no Vetor[]
		Vetor[] vectorArray = new Vetor[array.length];
		for(int i = 0 ; i< array.length; i++) 
			vectorArray[i] = new Vetor(array[i]);
		return vectorArray;
	}
	
	//Retorna o Produto Interno entre dois vetores
	private double productInternal(Vetor vector1,Vetor vector2) {
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