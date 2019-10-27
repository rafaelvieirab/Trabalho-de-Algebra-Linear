package model;

public class Vetor {
	private float[] coordenadas;
	
	public Vetor(int numCoordenadas) {
		this.coordenadas = new float[numCoordenadas];
	}
	
	public Vetor(float[] coordenadas) {
		this.coordenadas = coordenadas;
	}
	
	//Retorna o vetor atual normalizado
	public Vetor normaliza() {
		float norma = modulo();
		if(norma == 1)
			return new Vetor(this.coordenadas);
		
		Vetor newVector = new Vetor(coordenadas.length);
		for(int posCoordenada = 0; posCoordenada < this.coordenadas.length; posCoordenada++) 
			newVector.coordenadas[posCoordenada] = this.coordenadas[posCoordenada] / norma;
		return newVector;
	}
	
	//Retorna o modulo do vetor
	public float modulo() {
		float norma = 0;
		for(int posCoordenada = 0; posCoordenada < coordenadas.length; posCoordenada++) 
			norma += coordenadas[posCoordenada] * coordenadas[posCoordenada]; 
		return (float) Math.sqrt(norma);
	}
	
	//Retorna um vetor multiplicado por um Escalar
	public Vetor multiplicaEscalar(float scalar) {
		float[] vetorEscalonado = new float[this.coordenadas.length];
		for(int posCoordenada = 0; posCoordenada < coordenadas.length; posCoordenada++) 
			vetorEscalonado[posCoordenada] = scalar * coordenadas[posCoordenada]; 
		
		return new Vetor(vetorEscalonado);
	}
	
	//Retorna a quantidade de coordenadas do vetor
	public int getNumCoordenadas() {
		return coordenadas.length;
	}
	
	//Retorna o valor de uma determinada coordenada do vetor
	public float getValorCoordenada(int posCoordenada) {
		return (posCoordenada >= 0 && posCoordenada < coordenadas.length) ?
				coordenadas[posCoordenada] : 0;
	}
	
	//retorna todas as coordenadas como um array de float
	public float[] getCoordenadas() {
		return coordenadas;
	}
	
	public void setCoordenadas(float[] coordenadas) {
		this.coordenadas = coordenadas;
	}
	
}
