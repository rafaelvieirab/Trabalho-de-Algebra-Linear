package model;

public class Sistema {
	private int numEq; //número de equações
	private int numIncog; //número de incógnitas
	private float[][] coef; //coeficientes das incógnitas
	private float[] termo; //termo constante
	
	public Sistema(int numEq, int numIncog, float[][] coef, float[] termo) {
		this.numEq = numEq;
		this.numIncog = numIncog;
		this.coef = coef;
		this.termo = termo;
	}
	
	public Sistema(float[][] matrizAmp, int numEq, int numIncog ) {
		this.numEq = numEq;
		this.numIncog = numIncog;
		
		for(int i = 0; i < numEq; i++) 
			for(int j = 0; j < numIncog; j++)
				this.coef[i][j] = matrizAmp[i][j];
		
		for(int i = 0; i < numEq; i++) 
			this.termo[i] = matrizAmp[i][numIncog];
	}
	
	public int getNumEq() {
		return numEq;
	}

	public int getNumIncog() {
		return numIncog;
	}

	public float[][] getMatrizCoef() {
		return coef;
	}

	public float getCoef(int linha,int coluna) {
		return (linha>=0 && coluna>=0) ? coef[linha][coluna] : 0;
	}

	public float[] getMatrizTermo() {
		return termo;
	}

	public float getTermo(int eq) { //eq = equação correspondente
		return (eq>=0) ? termo[eq] : 0;
	}

	public boolean isSquare() {
		return numEq == numIncog;
	}

	public float[][] getMatrizAmpliada() {
		float[][] matrizAmp = new float[numEq][numIncog+1];
		for(int i = 0; i < numEq; i++) 
			for(int j = 0; j < numIncog; j++)
				matrizAmp[i][j] = coef[i][j];
		
		for(int i = 0; i < numEq; i++) 
			matrizAmp[i][numIncog] = termo[i];
		
		return matrizAmp;
	}
	
}
