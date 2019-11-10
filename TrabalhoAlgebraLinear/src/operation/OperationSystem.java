package operation;

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
				System.out.println("\nL"+i+" <- L"+i+"/"+divisor+"\n");
				for(int j = 0; j <= system.getNumIncog(); j++) 
					matrixAmp[i][j] /= divisor;
			}
			
			for(int linha = i+1; linha < system.getNumEq(); linha++) {
				double firstTermo = matrixAmp[linha][i];//1° elemento que vai ser zerado
				System.out.println("L"+linha+" <- L"+linha+"- ("+firstTermo +" * L"+i+")");
				
				for(int coluna = i; coluna <= system.getNumIncog(); coluna++) 
					matrixAmp[linha][coluna] -= (firstTermo * matrixAmp[i][coluna]);
			}
		}
		
		return new Sistema(matrixAmp,system.getNumEq(), system.getNumIncog());
	}
	
	/*Gauss-Jordan*/
	public Sistema gaussJordan(Sistema system) {
		//TODO 
		
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
				for(int j = 0; j <= system.getNumIncog(); j++) 
					matrixAmp[i][j] /= divisor;
			}
			
			for(int linha = i-1; linha >= 0; linha--) {
				double firstTermo = matrixAmp[linha][i];	//pega o 1° elemento que vai ser zerado
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
			if(matrix[linha][system.getNumIncog()] == 0) {  //termo independente != 0
				int coluna = 0;
				for(; coluna < system.getNumIncog(); coluna++)   //verifica se pelo menos um dos coeficientes != 0
					if(matrix[linha][coluna] != 0)
						break;
				double a = matrix[linha][coluna];
				if(coluna < system.getNumIncog())  // Então há um elemento na linha que é != 0, logo S.I
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
	public void solucions(Sistema system) {
		system = gaussJordan(system);
		int analyzePost = analyzePost(system);
		if(analyzePost == 2) // Sistema Impossivel
			System.out.println("Não existe solução!");
		
		else if(analyzePost == 0) //S.P.D.
			for(int pos = 0; pos < system.getNumEq(); pos++)
					System.out.println("x"+ pos +" = "+system.getTermo(pos));	
		
		else { //S.P.I.
			double matrix[][] = system.getMatrizAmpliada();
			String coeficientes;
			for(int pos = 0; pos < system.getNumEq() && pos < system.getNumIncog() ; pos++) { 
				coeficientes = "";
				//pegar todos os outros coeficintes diferentes de 0;
				for(int coef = 0; coef < pos; coef++) 
					if(matrix[pos][coef] != 0)
						coeficientes += "+(" + (-matrix[pos][coef]) + "*x" + coef + ")";
				for(int coef = pos+1; coef < system.getNumIncog(); coef++) 
					if(matrix[pos][coef] != 0)
						coeficientes += "+(" + (-matrix[pos][coef]) + "*x" + coef + ")";
				System.out.println("x"+ pos+" = "+system.getTermo(pos) + coeficientes);
			}
		}
	}
	
	//Terminar
	/*Fatora o sistema na matriz L e na matriz U*/
	public void fatoracaoLU(Sistema system) {
		//TODO
		//A = L*U
		//A*x = b
		//L*U*x = b
			//Ly = b
			//U*x = y

		//L é uma matriz triangular inferior
		//U "  "	"		 "	 	superior
		/*=>Como obter L e U		
			*=> Coloca-se a matriz dos coeficientes da original ao lado da matriz identidade;
			*=>Realiza Gauss em cima das duas;
			*=> A matriz original escalada é a matriz U
			*=> A matriz identidade escalada é a matriz L
			**/
		double[][] matrixLU = new double[system.getNumEq()][2*system.getNumIncog()];
		
		for(int linha = 0; linha < system.getNumEq(); linha++) {
			//copiando os valores da matriz dos coeficientes original
			int coluna = 0;
			for( ; coluna < system.getNumIncog(); coluna++) {
				matrixLU[linha][coluna] = system.getCoef(linha, coluna);
			}
			
			//Verificar isso
			//Escrevendo os valores da matriz Identidade
			for( ; coluna < 2*system.getNumIncog() && (coluna-system.getNumIncog()) != linha; coluna++) {
				matrixLU[linha][coluna] = 0;
			}
			matrixLU[linha][coluna] = 1; //elemento da diagonal da matriz Identidade
			coluna++;
			for( ; coluna < 2 * system.getNumIncog(); coluna++) {
				matrixLU[linha][coluna] = 0;
			}
		}
		//Escalona as matrizes original e identidade
		double[][] lu = gauss(new Sistema(matrixLU, system.getNumEq(), 2*system.getNumIncog())).getMatrizAmpliada();
		
		//Separa a matriz escalonada em nas matrizes L e U correspondentes
		double[][] matrixU = new double[system.getNumEq()][system.getNumIncog()];
		double[][] matrixL = new double[system.getNumEq()][system.getNumIncog()];
		
		for(int linha = 0; linha < system.getNumEq(); linha++) {
			for(int coluna = 0; coluna < system.getNumIncog(); coluna++) {
				matrixU[linha][coluna] = lu[linha][coluna];
				matrixL[linha][coluna] = lu[linha][coluna + system.getNumIncog()]; 
			}
		}
		
		//Mostrando na tela
		System.out.println("\nMatriz U:");
		for(int linha = 0; linha < system.getNumEq(); linha++) {
			System.out.println("\n");
			for(int coluna = 0; coluna < system.getNumIncog(); coluna++) {
				System.out.println(matrixU[linha][coluna]+" "); 
			}
		}
		System.out.println("\n\nMatriz L:");
		for(int linha = 0; linha < system.getNumEq(); linha++) {
			System.out.println("\n");
			for(int coluna = 0; coluna < system.getNumIncog(); coluna++) {
				System.out.println(matrixL[linha][coluna]+" "); 
			}
		}
		
	}

}