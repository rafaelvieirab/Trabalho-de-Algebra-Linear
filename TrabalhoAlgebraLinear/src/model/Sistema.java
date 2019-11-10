package model;

public class Sistema {
	private int numEq; //número de equações    => Linhas
	private int numIncog; //número de incógnitas => Colunas
	private double[][] coef; //Matriz dos Coeficientes das incógnitas
	private double[] termo; //Matriz dos Termos Independentes
	
	//Construtor padrão
	public Sistema(int numEq, int numIncog, double[][] coef, double[] termo) {
		this.numEq = numEq;
		this.numIncog = numIncog;
		this.coef = coef;
		this.termo = termo;
	}
	
	//Construtor: recebe a Matriz Ampliada, e o numero de linhas e colunas
	public Sistema(double[][] matrizAmp, int numEq, int numIncog ) {
		this.numEq = numEq;
		this.numIncog = numIncog;
		this.coef = new double[numEq][numIncog];
		this.termo = new double[numEq];
		
		for(int i = 0; i < numEq; i++) 
			for(int j = 0; j < numIncog; j++)
				this.coef[i][j] = matrizAmp[i][j];
		
		for(int i = 0; i < numEq; i++) 
			this.termo[i] = matrizAmp[i][numIncog]; 
	}
	
	//Retorna o número de Equações (Linhas do Sistema)
	public int getNumEq() {
		return numEq;
	}
	
	//Retorna o número de Incongnitas (Colunas da Matriz dos Coeficientes)
	public int getNumIncog() {
		return numIncog;
	}
	
	//Retorna a matriz dos coeficientes
	public double[][] getMatrizCoef() {
		return coef;
	}
	
	//Retorna um determinado coeficiente da matriz dos coeficientes
	public double getCoef(int linha,int coluna) {
		return (linha>=0 && coluna>=0) ? coef[linha][coluna] : 0;
	}
	
	//Retorna a Matriz dos Termos Independentes
	public double[] getMatrizTermo() {
		return termo;
	}
	
	//Retorna um determinado termo independente
	public double getTermo(int eq) { //eq = equação correspondente
		return (eq>=0) ? termo[eq] : 0;
	}

	//Verifica se o sistema é quadrado
	public boolean isSquare() {
		return numEq == numIncog;
	}
	
	//Retorna a Matriz Ampliada
	public double[][] getMatrizAmpliada() {
		double[][] matrizAmp = new double[numEq][numIncog+1];
		for(int i = 0; i < numEq; i++) 
			for(int j = 0; j < numIncog; j++)
				matrizAmp[i][j] = coef[i][j];
		
		for(int i = 0; i < numEq; i++) 
			matrizAmp[i][numIncog] = termo[i];
		
		return matrizAmp;
	}
	
}
