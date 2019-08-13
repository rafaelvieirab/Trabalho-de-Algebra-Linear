package model;

public class Matriz {
	private int linha;
	private int coluna;
	private int[][] matriz;
	
	public Matriz(int linha, int coluna) {
		this.linha = linha;
		this.coluna = coluna;
		this.matriz = new int[linha][coluna];
	}
	
	public Matriz(int[][] m) {
		this.matriz = m;
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
	
	public int[][] getMatriz(){
		return matriz;
	}
	
	public int value(int linha, int coluna) {
		return matriz[linha][coluna];
	}
	
	
}
