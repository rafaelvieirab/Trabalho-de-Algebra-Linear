package model;

public class Matriz {
	private int linha;
	private int coluna;
	private float[][] matriz;
	
	//Construtor que recebe o numero de linhas e colunas
	public Matriz(int linha, int coluna) {
		this.linha = linha;
		this.coluna = coluna;
		this.matriz = new float[linha][coluna];
	}
	
	//Construtor que recebe um array
	public Matriz(float[][] array) {
		this.linha = array.length;
		this.coluna = array[0].length;
		this.matriz = array;
	}
	
	public int getLinha() {
		return linha;
	}

	public int getColuna() {
		return coluna;
	}
	
	//Verifica se a matriz é quadrada
	public boolean isSquare() {
		return linha == coluna;
	}
	
	//Retorna a matriz 
	public float[][] getMatriz(){
		return matriz;
	}
	
	//Retorna um determinado valor da matriz 
	public float value(int linha, int coluna) {
		return (linha>=0 && coluna>=0) ? matriz[linha][coluna] : 0;
	}
	
	//Muda um valor de uma determinada posição da matriz
	public void setValue(int linha, int coluna, float valor) {
		if(linha>=0 && coluna>=0)
			matriz[linha][coluna] = valor;
	}
}
