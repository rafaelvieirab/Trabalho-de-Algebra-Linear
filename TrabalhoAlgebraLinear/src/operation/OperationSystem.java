package operation;

import javax.swing.JOptionPane;

import model.Matriz;
import model.Sistema;

public class OperationSystem {
	
	private static OperationSystem instance = new OperationSystem();
	private OperationSystem() {}
	public static OperationSystem getInstance() {return instance;}
	
	/*Gauss*/
	public Sistema gauss(Sistema system) {
		System.out.println("\t\tEscalonamento por Gauss: ");
		if(system.getNumEq() <= 1 || system.getNumIncog() <= 1)
			return system;
		
		double[][] matrixAmp = system.getMatrizAmpliada();
		
		for(int i = 0; i < system.getNumEq()-1 && i < system.getNumIncog(); i++) {
			
			if(matrixAmp[i][i] == 0) //Verifica se o Pivo Atual é igual a 0
				if(!rowsSwap(matrixAmp,i)) 
					continue;
			if(matrixAmp[i][i] != 1) { //Verifica se o Pivo Atual != 1
				double divisor = matrixAmp[i][i];
				for(int j = 0; j <= system.getNumIncog(); j++) 
					matrixAmp[i][j] /= divisor;
			}
			
			for(int linha = i+1; linha < system.getNumEq(); linha++) {
				double firstTermo = matrixAmp[linha][i];//1° elemento que vai ser zerado
				if(firstTermo == 0)
					continue;
				
				for(int coluna = i; coluna <= system.getNumIncog(); coluna++) 
					matrixAmp[linha][coluna] -= (firstTermo * matrixAmp[i][coluna]);
			}
		}
		
		return new Sistema(matrixAmp,system.getNumEq(), system.getNumIncog());
	}
	
	/*Gauss-Jordan*/
	public Sistema gaussJordan(Sistema system) {
		
		System.out.println("\t\tEscalonamento por Gauss-Jordan: ");
		if(system.getNumEq() <= 1 || system.getNumIncog() <= 1)
			return system;
		double[][] matrixAmp = gauss(system).getMatrizAmpliada();
		
		System.out.println("\t\tParte do Gauss-Jordan: ");
		
		int i = (system.getNumEq() < system.getNumIncog()) ? system.getNumEq() : system.getNumIncog();
		i--;
		for(; i >=0; i--) {
			if(matrixAmp[i][i] == 0)  //Verifica se o Pivo Atual != 0
				continue;
			if(matrixAmp[i][i] != 1) { //Verifica se o Pivo Atual != 1
				double divisor = matrixAmp[i][i];
				System.out.println("\nL"+i+" <- L"+i+"/"+divisor+"\n");
				for(int j = 0; j <= system.getNumIncog(); j++) 
					matrixAmp[i][j] /= divisor;
			}
			
			for(int linha = i-1; linha >= 0; linha--) {
				double firstTermo = matrixAmp[linha][i];	//pega o 1° elemento que vai ser zerado
				if(firstTermo == 0)
					continue;
				System.out.println("L"+linha+" <- L"+linha+"- ("+firstTermo +" * L"+i+")");
				
				for(int coluna = system.getNumIncog(); coluna >= 0; coluna--) 
					matrixAmp[linha][coluna] -=  (firstTermo*matrixAmp[i][coluna]);	
			}
		}
		
		System.out.println();
		
		return new Sistema(matrixAmp,system.getNumEq(), system.getNumIncog());
	}
	
	/*Se trocar as linhas retorna true, senão false*/
	private boolean rowsSwap(double[][] matrix, int i) {
		int j = i+1;
		while(j < matrix.length && matrix[j][i] == 0)
			j++;
			
		if(j == matrix.length) //A coluna inteira está zerada, logo pode ir para a proxima coluna
			return false;
		if(matrix[j][i] != 0) {//Troca linhas
			System.out.println("L" + i + " <-> L" + j);
			double aux;
			for(int coluna = 0; coluna < matrix[0].length; coluna++) {
				aux = matrix[i][coluna]; //linha que tinha valor 0
				matrix[i][coluna] = matrix[j][coluna]; //linha que tem o valor diferente de 0
				matrix[j][coluna] = aux;
			}
		}
		return true;
	}
	
	/*Retorna a Classificação (SPD,SPI ou SI) do sistema*/
	public String analyzeSolucion(Sistema system) {
		system = gaussJordan(system);
		int analyze = analyzePost(system); 
		if (analyze == 0) 
			return "Possui somente uma única solução!\nSistema Possivel e Determinado";
		else {
			if(analyze == 1) 
				return "Possui infinitas soluções!\nSistema Possivel e Indeterminado";
			else
				return "Não possui solução!\nSistema Impossivel";
		}
	}
	
	/*Analisa o posto das matrizes ampliada e dos coeficientes*/
	public int analyzePost(Sistema system) {
		double[][] matrix = system.getMatrizAmpliada(); //Escalona o sistema
		int numLinhasZeradas = 0;
		
		for(int linha = 0; linha < system.getNumEq(); linha++) {
			if(Math.abs(matrix[linha][system.getNumIncog()]) != 0) {  //termo independente != 0
				int coluna = 0;
				for(; coluna < system.getNumIncog(); coluna++)   //verifica se pelo menos um dos coeficientes != 0
					if(Math.abs(matrix[linha][coluna]) != 0)
						break;
				if(coluna == system.getNumIncog())  // Então há um elemento na linha que é != 0, logo S.I
					return 2; // Sistema Impossivel
			}else {
				int coluna = 0;
				while(coluna < system.getNumIncog() && matrix[linha][coluna] == 0) 
					coluna++;
				if(coluna == system.getNumIncog())
					numLinhasZeradas++;
			}
		}
		if((system.getNumEq() - numLinhasZeradas) < system.getNumIncog())
			return 1; // Sistema Impossivel e Determinado
		return 0; // Sistema Possivel e Determinado
	}

	/*Apresenta as possiveis soluções*/
	public String solucions(Sistema system) {
		system = gaussJordan(system);
		int analyzePost = analyzePost(system);
		String solucao = "";
		if(analyzePost == 2) {// Sistema Impossivel
			solucao = "Não existe solução!";
			System.out.println(solucao);
		}
		else if(analyzePost == 0) //S.P.D.{
			for(int pos = 0; pos < system.getNumEq(); pos++) {
				solucao += "x"+ pos +" = "+ system.getTermo(pos) + "\n";
				System.out.println("x"+ pos +" = "+system.getTermo(pos));	
			}
		
		else { //S.P.I.
			double matrix[][] = system.getMatrizAmpliada();
			for(int pos = 0; pos < system.getNumEq() && pos < system.getNumIncog() ; pos++) { 
				
				if(matrix[pos][pos] == 0) { //coeficiente == 0
					solucao += "x"+ pos + " = x" + pos + "\n";
					System.out.println("x"+ pos + " = x" + pos);
				}
				else {
					solucao += "x"+ pos+" = "+system.getTermo(pos) + returnCoef(matrix,pos) + "\n";
					System.out.println("x"+ pos+" = "+system.getTermo(pos) + returnCoef(matrix,pos));
				}
				
			}
		}
		return solucao;
	}
	
	/*Retorna todos os coeficientes diferentes de 0, e da posição dada*/
	private String returnCoef(double[][] matrix, int pos) {
		String coeficientes = "";

		for(int coef = 0; coef < pos; coef++) 
			if(matrix[pos][coef] != 0)
				coeficientes += "+(" + (-matrix[pos][coef]) + "*x" + coef + ")";
		for(int coef = pos+1; coef < matrix[0].length-1; coef++) 
			if(matrix[pos][coef] != 0)
				coeficientes += "+(" + (-matrix[pos][coef]) + "*x" + coef + ")";

		return coeficientes;
	}
	
	/*Fatora o sistema na matriz L e na matriz U*/
	public Matriz[] fatoracaoLU(Sistema system) {
		
		if(system.getNumEq() != system.getNumIncog()) {
			JOptionPane.showMessageDialog(null, "O número de equações é diferente do de incognitas"
					+ ", logo não é possivel fatorar a Matriz dos Coeficientes", "Fatoração LU", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		
		if(OperationMatriz.getInstance().determinante(new Matriz(system.getMatrizCoef())) == 0) {
			JOptionPane.showMessageDialog(null, "A Matriz dos Coeficientes tem determinante igual a 0, logo não admite inversa e nem FatoraçãoLU!", "Erro na Fatoração LU do sistema" , JOptionPane.ERROR_MESSAGE);
			return null;
		}
		
		double matrixLower[][] = new double[system.getNumEq()][system.getNumIncog()];
		
		for(int i = 0; i < system.getNumEq(); i++) {
			matrixLower[i][i] = 1;
			for(int j = i+1; j < system.getNumIncog(); j++)
				matrixLower[i][j] = 0;
		}
		
		double matrixUpper[][] = gaussLU(system, matrixLower);
		
		Matriz lu[] = {new Matriz(matrixUpper),new Matriz(matrixLower)};
		return lu;
		
	} //fim da fatoracaoLU
	
	public double[][] gaussLU(Sistema system, double matrixLower[][]) {
		double matrixUpper[][] = system.getMatrizCoef();
		
		for(int i = 0; i < system.getNumEq()-1; i++) {
			if(matrixUpper[i][i] == 0) 
				if(!rowsSwap(matrixUpper,i)) 
					continue;
			for(int linha = i+1; linha < system.getNumEq(); linha++) {
				
				if(matrixUpper[linha][i] == 0) continue;
				
				double firstTermo = matrixUpper[linha][i] / matrixUpper[i][i]; 
				matrixLower[linha][i]= firstTermo;
				
				for(int coluna = i; coluna < system.getNumIncog(); coluna++) 
					matrixUpper[linha][coluna] -= (firstTermo * matrixUpper[i][coluna]);
			}
		}
		return matrixUpper;
	}
	
}