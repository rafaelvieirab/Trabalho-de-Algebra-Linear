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
		
		for(int i = 0; i < system.getNumEq()-1 && i < system.getNumIncog()-1; i++) {
			
			if(matrixAmp[i][i] == 0) //Verifica se o Pivo Atual é igual a 0
				if(!rowsSwap(matrixAmp,i)) //troca por uma linha onde essa coluna nao é 0
					continue;//se não encontrar, pode ir para o proximo pivo
			double pivo = matrixAmp[i][i];
			System.out.println("\nPivô: " + pivo + "\n");
			
			for(int linha = i+1; linha < system.getNumEq(); linha++) {
				double firstTermo = matrixAmp[linha][i];//1° elemento que vai ser zerado
				System.out.println("L"+linha+" <- L"+linha+"- ("+firstTermo +"/"+pivo+" * L"+i+")");
				
				for(int coluna = i; coluna < system.getNumIncog()+1; coluna++) 
					matrixAmp[linha][coluna] -= ((firstTermo/pivo) * matrixAmp[i][coluna]);
			}
		}
		return new Sistema(matrixAmp,system.getNumEq(), system.getNumIncog());
	}
	
	//TODO tem que ajustar
	/*Gauss-Jordan*/
	public Sistema gaussJordan(Sistema system) {
		//TODO 
		/*E se o numero de linhas for diferente do número de colunas?
		começa por onde?
			*pelas Linhas ou
			*pelas colunas
		 * */
		System.out.println("\t\tEscalonamento por Gauss-Jordan: ");
		if(system.getNumEq() <= 1 || system.getNumIncog() <= 1)
			return system;
		
		//Podemos reduzir isso para gauss(system).getMatrizAmpliada(); ???
		system = gauss(system);
		double[][] matrixAmp = system.getMatrizAmpliada();
		
		int i = (system.getNumEq() < system.getNumIncog()) ? system.getNumEq() : system.getNumIncog();
		i--;
		for(; i >=0; i--) {//começa do final (das linahs ou das colunas)
			double pivo = matrixAmp[i][i];
			System.out.println("\nPivô: " + pivo + "\n");
			
			
			
			for(int linha = i-1; linha >= 0; linha--) {
				double firstTermo = matrixAmp[linha][i];	//pega o 1° elemento que vai ser zerado
				
				for(int coluna = i-1; coluna >= 0; coluna--) {
					System.out.println("L"+ linha + " <- L" + linha + "- ("+(firstTermo/pivo) +" * L"+ i +")");
					matrixAmp[linha][coluna] = matrixAmp[linha][coluna] - (firstTermo/pivo)*matrixAmp[i][linha];
					// L[linha] = L[linha] - (p/q)*Li, onde
					// p (firstTermo) = elemento a[linha]k (Linha que vai ser zerada)
					// q (pivo)= elemento aik (Linha do pivo)
				}
			}
		}
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
		int analyze = analyzePost(system); 
		if (analyze == 0) 
			return "Possui somente uma única solução!\nSistema Possivel e Determinado";
		else {
			if(analyze == 1) 
				return "Possui infinitas soluções!\nSistema Possivel e Indeterminado";
			else
				return "Possui somente uma solução!\nSistema Impossivel";
		}
	}
	
	/*Analisa o posto das matrizes ampliada e dos coeficientes*/
	public int analyzePost(Sistema system) {
		Sistema systemEscalonado = gaussJordan(system); //Escalona o sistema
		double[][] matrix = system.getMatrizAmpliada(); 
		int postoAmp = 0;	//número de linhas não nulas da matriz AMPLIADA
		int postoCoef = 0;	//" "	"	"	"	"	"	"	" 	   COEFICIENTES
		//olha se a mesma coisa que o de baixo
		for(int linha = 0; linha < system.getNumEq(); linha++) {
			int coluna = 0;
			for( ; coluna < system.getNumIncog(); coluna++) {
				if(matrix[linha][coluna] != 0)
					break;
			}
			//se não chegou no último elemento(termo independente), então alguém é diferente de 0
			if(coluna < system.getNumIncog()-1) {  
				postoAmp++;
				//se não chegou no último coeficiente, então alguém é diferente de 0
				if(coluna < system.getNumIncog()-2)
					postoCoef++;
			}
			
		}
		
		/*alternativo - Verifica somente os termos independentes
		 	* Se o termo daquela linha for igual a 0, então sabemos que a linha é Possivel
		 	* Se o termo daquela linha for diferente de 0, verifica se a linha está zerada
		 		* Se sim, então a linha é possivel 
		 		* Senão, então o sistema é impossivel 
		 */
		for(int linha = 0; linha < system.getNumEq(); linha++) 
			if(matrix[linha][system.getNumIncog()] != 0)  //termo independente != 0
				for(int coluna = 0; coluna < system.getNumIncog(); coluna++)  //verifica se pelo menos um dos coeficientes != 0
					if(matrix[linha][coluna] != 0)
						break;
		
		if(postoAmp == postoCoef) {
			if(postoAmp == system.getNumIncog())
				return 0; //  Sistema Possivel e Determinado
			return 1; //  Sistema Impossivel e Determinado
		}
		return 2;//  Sistema Impossivel
	}

	//Terminar .....
	/*Apresenta as possiveis soluções*/
	public void solucions(Sistema system) {
		//TODO
		system = gaussJordan(system);
		if(analyzePost(system) == 2)// Sistema Impossivel
			System.out.println("Não existe solução!");
		
		for(int linha = 0, coluna = 0; linha < system.getNumEq() && coluna < system.getNumIncog(); linha++, coluna++) {
			System.out.println("a["+ linha + "]" +"["+ coluna+ "]" +  
					           "" + system.getCoef(linha, coluna) + " = " + system.getCoef(linha, system.getNumIncog()-1));
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