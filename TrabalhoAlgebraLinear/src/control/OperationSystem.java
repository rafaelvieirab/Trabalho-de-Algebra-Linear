package control;

import model.Matriz;
import model.Sistema;

public class OperationSystem {
	public Sistema gauss(Sistema system) {
		float[][] matrixAmp = system.getMatrizAmpliada();
		
		if(system.getNumEq() <= 1)
			return system;
		
		for(int i = 0; i < system.getNumEq(); i++) {
			float pivo = matrixAmp[i][i];
			//come�a de i+1 : pq s� precisa reduzir as equa��es que est�o abaixo
			
			for(int j = i+1; j < system.getNumEq(); j++) {
				float firstTermo = matrixAmp[j][i];	//pega o 1� elemento que vai ser zerado
				
				for(int k = i+1; k < system.getNumIncog(); k++) {
					matrixAmp[j][k] = matrixAmp[j][k] - (firstTermo/pivo)*matrixAmp[i][j];
					// Lj = Lj - (p/q)*Li, onde
					// p (firstTermo) = elemento ajk (Linha que vai ser zerada)
					// q (pivo)= elemento aik (Linha do pivo)
				}
			}
		}
		return new Sistema(matrixAmp,system.getNumEq(), system.getNumIncog());
	}
	
	public Sistema gaussJordan(Sistema system) {
		if(system.getNumEq() <= 1)
			return system;
		system = gauss(system);
		float[][] matrixAmp = system.getMatrizAmpliada();
		
		//Verificar depois
		for(int i = system.getNumEq()-1; i >=0; i--) {
			float pivo = matrixAmp[i][i];
			//come�a do final
			
			for(int j = i-1; j >= 0; j--) {
				float firstTermo = matrixAmp[j][i];	//pega o 1� elemento que vai ser zerado
				
				for(int k = i-1; k >= 0; k--) {
					matrixAmp[j][k] = matrixAmp[j][k] - (firstTermo/pivo)*matrixAmp[i][j];
					// Lj = Lj - (p/q)*Li, onde
					// p (firstTermo) = elemento ajk (Linha que vai ser zerada)
					// q (pivo)= elemento aik (Linha do pivo)
				}
			}
		}
		return new Sistema(matrixAmp,system.getNumEq(), system.getNumIncog());
	}
	
}
