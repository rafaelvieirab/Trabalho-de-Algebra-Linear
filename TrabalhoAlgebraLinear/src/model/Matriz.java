package model;

public class Matriz {
	private int linha;
	private int coluna;
	private float[][] matriz;
	
	public Matriz(int linha, int coluna) {
		this.linha = linha;
		this.coluna = coluna;
		this.matriz = new float[linha][coluna];
	}
	
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
	
	public boolean isSquare() {
		return linha == coluna;
	}
	
	public float[][] getMatriz(){
		return matriz;
	}
	
	public float value(int linha, int coluna) {
		return (linha>=0 && coluna>=0) ? matriz[linha][coluna] : 0;
	}
	
	public void setValue(int linha, int coluna, float valor) {
		if(linha>=0 && coluna>=0)
			matriz[linha][coluna] = valor;
	}
}
