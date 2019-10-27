package model;

public class Sistema {
	private int numEq; //n�mero de equa��es    => Linhas
	private int numIncog; //n�mero de inc�gnitas => Colunas
	private float[][] coef; //Matriz dos Coeficientes das inc�gnitas
	private float[] termo; //Matriz dos Termos Independentes
	
	//Construtor: recebe tudo
	public Sistema(int numEq, int numIncog, float[][] coef, float[] termo) {
		this.numEq = numEq;
		this.numIncog = numIncog;
		this.coef = coef;
		this.termo = termo;
	}
	
	//Construtor: recebe a Matriz Ampliada, e o numero de linhas e colunas
	public Sistema(float[][] matrizAmp, int numEq, int numIncog ) {
		this.numEq = numEq;
		this.numIncog = numIncog;
		
		for(int i = 0; i < numEq; i++) 
			for(int j = 0; j < numIncog; j++)
				this.coef[i][j] = matrizAmp[i][j];
		
		for(int i = 0; i < numEq; i++) 
			this.termo[i] = matrizAmp[i][numIncog];
	}
	
	//Retorna o n�mero de Equa��es (Linhas do Sistema)
	public int getNumEq() {
		return numEq;
	}
	
	//Retorna o n�mero de Incongnitas (Colunas da Matriz dos Coeficientes)
	public int getNumIncog() {
		return numIncog;
	}
	
	//Retorna a matriz dos coeficientes
	public float[][] getMatrizCoef() {
		return coef;
	}
	
	//Retorna um determinado coeficiente da matriz dos coeficientes
	public float getCoef(int linha,int coluna) {
		return (linha>=0 && coluna>=0) ? coef[linha][coluna] : 0;
	}
	
	//Retorna a Matriz dos Termos Independentes
	public float[] getMatrizTermo() {
		return termo;
	}
	
	//Retorna um determinado termo independente
	public float getTermo(int eq) { //eq = equa��o correspondente
		return (eq>=0) ? termo[eq] : 0;
	}

	//Verifica se o sistema � quadrado
	public boolean isSquare() {
		return numEq == numIncog;
	}
	
	//Retorna a Matriz Ampliada
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
