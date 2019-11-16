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
			
			if(matrixAmp[i][i] == 0) //Verifica se o Pivo Atual � igual a 0
				if(!rowsSwap(matrixAmp,i)) 
					continue;
			if(matrixAmp[i][i] != 1) { //Verifica se o Pivo Atual != 1
				double divisor = matrixAmp[i][i];
				System.out.println("\nL"+i+" <- L"+i+"/"+divisor+"\n");
				for(int j = 0; j <= system.getNumIncog(); j++) 
					matrixAmp[i][j] /= divisor;
			}
			
			for(int linha = i+1; linha < system.getNumEq(); linha++) {
				double firstTermo = matrixAmp[linha][i];//1� elemento que vai ser zerado
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
				System.out.println("\nL"+i+" <- L"+i+"/"+divisor+"\n");
				for(int j = 0; j <= system.getNumIncog(); j++) 
					matrixAmp[i][j] /= divisor;
			}
			
			for(int linha = i-1; linha >= 0; linha--) {
				double firstTermo = matrixAmp[linha][i];	//pega o 1� elemento que vai ser zerado
				System.out.println("L"+linha+" <- L"+linha+"- ("+firstTermo +" * L"+i+")");
				
				for(int coluna = system.getNumIncog(); coluna >= 0; coluna--) 
					matrixAmp[linha][coluna] -=  (firstTermo*matrixAmp[i][coluna]);	
			}
		}
		
		System.out.println();
		
		return new Sistema(matrixAmp,system.getNumEq(), system.getNumIncog());
	}
	
	/*Se trocar as linhas retorna true, sen�o false*/
	private boolean rowsSwap(double[][] matrix, int i) {
		int j = i+1;
		while(j < matrix.length && matrix[j][i] == 0)
			j++;
			
		if(j == matrix.length) //A coluna inteira est� zerada, logo pode ir para a proxima coluna
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
	
	/*Retorna a Classifica��o (SPD,SPI ou SI) do sistema*/
	public String analyzeSolucion(Sistema system) {
		system = gaussJordan(system);
		int analyze = analyzePost(system); 
		if (analyze == 0) 
			return "Possui somente uma �nica solu��o!\nSistema Possivel e Determinado";
		else {
			if(analyze == 1) 
				return "Possui infinitas solu��es!\nSistema Possivel e Indeterminado";
			else
				return "N�o possui solu��o!\nSistema Impossivel";
		}
	}
	
	/*Analisa o posto das matrizes ampliada e dos coeficientes*/
	public int analyzePost(Sistema system) {
		double[][] matrix = system.getMatrizAmpliada(); //Escalona o sistema
		int numLinhasZeradas = 0;
		
		for(int linha = 0; linha < system.getNumEq(); linha++) {
			if(matrix[linha][system.getNumIncog()] != 0) {  //termo independente != 0
				int coluna = 0;
				for(; coluna < system.getNumIncog(); coluna++)   //verifica se pelo menos um dos coeficientes != 0
					if(matrix[linha][coluna] != 0)
						break;
				if(coluna == system.getNumIncog())  // Ent�o h� um elemento na linha que � != 0, logo S.I
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

	/*Apresenta as possiveis solu��es*/
	public String solucions(Sistema system) {
		system = gaussJordan(system);
		int analyzePost = analyzePost(system);
		String solucao = "";
		if(analyzePost == 2) {// Sistema Impossivel
			solucao = "N�o existe solu��o!";
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
	
	/*Retorna todos os coeficientes diferentes de 0, e da posi��o dada*/
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
	
	//TODO - /erifica se � inversivel?
	/*Fatora o sistema na matriz L e na matriz U*/
	public void fatoracaoLU(Sistema system) {
		//TODO
		//L � uma matriz triangular inferior, oriunda da Identidade com uma diagonal Principal igual 1
		//U "  "	"		 "	 	superior, oriunda da matriz Original
		/*=>Como obter L e U?		
			*=> Coloca-se a matriz dos COEFICIENTES da original ao lado da matriz identidade;
			*=>Realiza Gauss em cima das duas;
			*=> A matriz identidade escalada � a matriz L (vai continuar tendo a diagonal == 1)
			*=> A matriz original escalada � a matriz U
		*/
		double[][] matrixLU = buildMatrixIdentityOriginalLU(system);
		matrixLU = gauss(new Sistema(matrixLU, system.getNumEq(), 2*system.getNumIncog())).getMatrizAmpliada();
		
		/*Garante que a diagonal Principal da matriz L ser� igual a 1*/
		for(int linha = 0; linha < system.getNumEq(); linha++) 
			if(matrixLU[linha][linha+system.getNumIncog()] != 1) {
				double mult = 1/matrixLU[linha][linha+system.getNumIncog()];
				for(int coluna = 0; coluna < 2*system.getNumIncog(); coluna++) 
					matrixLU[linha][coluna] *= mult;
			}
		
		//Separa a matriz escalonada nas matrizes L e U correspondentes
		double[][] matrixU = new double[system.getNumEq()][system.getNumIncog()];
		double[][] matrixL = new double[system.getNumEq()][system.getNumIncog()];
		
		for(int linha = 0; linha < system.getNumEq(); linha++) 
			for(int coluna = 0; coluna < system.getNumIncog(); coluna++) {
				matrixU[linha][coluna] = matrixLU[linha][coluna];
				matrixL[linha][coluna] = matrixLU[linha][coluna + system.getNumIncog()]; 
			}
		
		//Mostrando na tela
		System.out.println("\nMatriz U:");
		for(int linha = 0; linha < system.getNumEq(); linha++) {
			System.out.println("");
			for(int coluna = 0; coluna < system.getNumIncog(); coluna++) 
				System.out.print(matrixU[linha][coluna]+" "); 
		}
		System.out.println("\n\nMatriz L:");
		for(int linha = 0; linha < system.getNumEq(); linha++) {
			System.out.println("");
			for(int coluna = 0; coluna < system.getNumIncog(); coluna++) 
				System.out.print(matrixL[linha][coluna]+" "); 
		}
		System.out.println();
		
	} //fim da fatoracaoLU

	/*Constroi uma matriz no qual
	 	*  a metade dos coeficientes s�o os coeficientes do sistema
	 	*  e a outra metade, representa a matriz identidade
	 	*  j� a ultima coluna � formada somente de 0's = representando os termos independentes
	 	*  */
	private double[][] buildMatrixIdentityOriginalLU(Sistema system){
		double[][] matrixLU = new double[system.getNumEq()][2*system.getNumIncog()+1];
		
		for(int linha = 0; linha < system.getNumEq(); linha++) {
			int coluna = 0;
			//Copiando os valores da matriz dos coeficientes original
			for(; coluna < system.getNumIncog(); coluna++) 
				matrixLU[linha][coluna] = system.getCoef(linha, coluna);
			
			//Escrevendo os valores da matriz Identidade
			for( ; coluna < system.getNumIncog()+linha; coluna++) 
				matrixLU[linha][coluna] = 0;
			matrixLU[linha][coluna++] = 1;
			for(; coluna < 2*system.getNumIncog(); coluna++) 
				matrixLU[linha][coluna] = 0;
			
			matrixLU[linha][coluna] = 0; //Termo Independente
		}
		return matrixLU;
	}
	
}