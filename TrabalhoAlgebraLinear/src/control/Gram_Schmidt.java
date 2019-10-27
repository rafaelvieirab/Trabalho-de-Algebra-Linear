package control;

import model.Sistema;
import model.Vetor;

public class Gram_Schmidt {
	
	//Projeta vector1 em vector2, ou seja, Faz com que vector1 seja perpendicular a vector2
	private Vetor projection(Vetor vector1,Vetor vector2) {//v = vector1, w = vector2
		//proj(v,w) = cw = 	((v*w) /(w*w)) * w
		float vw = multiplication(vector1, vector2);
		float ww= vector2.modulo();
		return vector2.multiplicaEscalar(vw/ww);
	}
	
	//Nome Alternativo:	orthogonalization
	public Vetor[] ortoganalizacion(Vetor[] base) {
		//Obs.: O produto que vem na base B precisa ser o usual, ou pode ser especificado?.
		base = verificaDependencia(base); //implementar depois
		
		Vetor[] newBase = new Vetor[base.length];
		
		for(int posAtual=0; posAtual<base.length ; posAtual++) {
			newBase[posAtual] = base[posAtual];
			for(int anterior=0; anterior<posAtual ; anterior++) 
				newBase[posAtual] = subtraction(newBase[posAtual], projection(base[posAtual], base[anterior])); 
		}
		return newBase;
	}

	//Nome Alternativo:	orthonormalization
	public Vetor[] ortonormalizacion(Vetor[] base) {
		Vetor[] newBase  = ortoganalizacion(base);
		for(int posAtual=0; posAtual<newBase.length ; posAtual++) {
			 newBase[posAtual] = newBase[posAtual].normaliza();
		}
		return newBase;
	}
	
	//Nome Alternativo:	check Linear Dependency
	private Vetor[] verificaDependencia(Vetor[] base) {
		//Verifica se cada vetor � combina��o linear dos outros
		//=> Basta transformar em um sistema e escalonar
		//=> Se tiver menos linhas n�o zeradas do que colunas(coordenadas), basta completar com as que falta 
		
		Sistema system = vectorArrayToSystem(base);
		if(OperationSystem.getInstance().analyzePost(system) == 0) 
			return systemToVectorArray(system);
		else { //deve ser igual a 1, se n�o t� errado
			return completeBase(system);
		}
		
	}
	
	//Deixa somente os vetores independentes
	private Sistema vectorArrayToSystem(Vetor[] base) {
		float[][] coordenadas = new float[base.length][base[0].getNumCoordenadas()+1]; //+1, pq tem que ter uma coluan zerada para n�o bugar o posto
		for(int i = 0; i< base.length; i++) {
			int j = 0;
			for( ; j< base[0].getNumCoordenadas(); j++) {
				coordenadas[i][j] = base[i].getValorCoordenada(j);
			}
			coordenadas[i][j] = 0;
		}
		Sistema system = new Sistema(coordenadas,base.length,base[0].getNumCoordenadas()+1);
		return OperationSystem.getInstance().gaussJordan(system);
	}
	
	/*
	private Vetor[] systemToVectorArray(Sistema system) {
		Vetor[] vectorArray = new Vetor[system.getNumEq()];
		float[][] coordenadas = system.getMatrizCoef();
		
		for(int i = 0; i< system.getNumEq(); i++) 
			vectorArray[i] = new Vetor(coordenadas[i]);
		return vectorArray;
	}
	*/
	
	//Completa a base com os vetores independentes restantes
	private Vetor[] completeBase(Sistema system) {
		//Fazer uma fun��o para tirar as linhas nulas
		//Fazer uma fun��o para ordenar as linhas de forma que todos na diagonal sejam igual a 1(pode est� ordenado mais)
		float[][] array = system.getMatrizCoef();
		for(int i =0 ; i< array.length; i++) {
			
			if(array[i][i] == 0) {//Sobrescreve a linha atual, criando o vetor independe que estava faltando
				for(int j = 0; j < system.getNumIncog(); j++) 
					array[i][i] = 0;
				array[i][i] = 1;
			}
		}
		
		//Transformando o array no Vetor[]
		Vetor[] vectorArray = new Vetor[array.length];
		for(int i = 0 ; i< array.length; i++) 
			vectorArray[i] = new Vetor(array[i]);
		return vectorArray;
	}
	
	//Nome Alternativo:	isOrthogonal
	public boolean isOrtogonal(Vetor vector1,Vetor vector2) {
		return multiplication(vector1, vector2) == 0;
	}
	
	private float multiplication(Vetor vector1,Vetor vector2) {
		float resultado = 0;
		for(int posCoordenada = 0; posCoordenada < vector1.getNumCoordenadas(); posCoordenada++) 
			resultado += vector1.getValorCoordenada(posCoordenada) * vector2.getValorCoordenada(posCoordenada); 
		return resultado;
	}
	
	private Vetor subtraction(Vetor vector1,Vetor vector2) {
		float[] resultado = new float[vector1.getNumCoordenadas()];
		for(int posCoordenada = 0; posCoordenada < vector1.getNumCoordenadas(); posCoordenada++) 
			resultado[posCoordenada] = vector1.getValorCoordenada(posCoordenada) - vector2.getValorCoordenada(posCoordenada); 
		return new Vetor(resultado);
	}
	
}
