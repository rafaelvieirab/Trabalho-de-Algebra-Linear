package model;

public class Vetor {
	private double[] coordenadas;
	
	public Vetor(int numCoordenadas) {
		this.coordenadas = new double[numCoordenadas];
	}
	
	public Vetor(double[] coordenadas) {
		this.coordenadas = coordenadas;
	}
	
	//Retorna o vetor atual normalizado
	public Vetor normaliza() {
		double norma = modulo();
		if(norma == 1)
			return new Vetor(this.coordenadas);
		
		Vetor newVector = new Vetor(coordenadas.length);
		for(int posCoordenada = 0; posCoordenada < this.coordenadas.length; posCoordenada++) 
			newVector.coordenadas[posCoordenada] = this.coordenadas[posCoordenada] / norma;
		return newVector;
	}
	
	//Retorna o modulo do vetor
	public double modulo() {
		double norma = 0;
		for(int posCoordenada = 0; posCoordenada < coordenadas.length; posCoordenada++) 
			norma += coordenadas[posCoordenada] * coordenadas[posCoordenada]; 
		return (double) Math.sqrt(norma);
	}
	
	//Retorna um vetor multiplicado por um Escalar
	public Vetor multiplicaEscalar(double scalar) {
		double[] vetorEscalonado = new double[this.coordenadas.length];
		for(int posCoordenada = 0; posCoordenada < coordenadas.length; posCoordenada++) 
			vetorEscalonado[posCoordenada] = scalar * coordenadas[posCoordenada]; 
		
		return new Vetor(vetorEscalonado);
	}
	
	//Retorna a quantidade de coordenadas do vetor
	public int getNumCoordenadas() {
		return coordenadas.length;
	}
	
	//Retorna o valor de uma determinada coordenada do vetor
	public double getValorCoordenada(int posCoordenada) {
		return (posCoordenada >= 0 && posCoordenada < coordenadas.length) ?
				coordenadas[posCoordenada] : 0;
	}

	//Retorna o valor de uma determinada coordenada do vetor
	public void setValorCoordenada(int posCoordenada, double valor) {
		if(posCoordenada >= 0 && posCoordenada < coordenadas.length)
			this.coordenadas[posCoordenada] = valor;
	}
	
	//retorna todas as coordenadas como um array de double
	public double[] getCoordenadas() {
		return coordenadas;
	}
	
	public void setCoordenadas(double[] coordenadas) {
		this.coordenadas = coordenadas;
	}
	
	@Override
	public String toString() {
		String acc = "( ";
		for(double coordenada : coordenadas)
			acc+= coordenada +", ";
		acc += " )";
		return acc;
	}
}
