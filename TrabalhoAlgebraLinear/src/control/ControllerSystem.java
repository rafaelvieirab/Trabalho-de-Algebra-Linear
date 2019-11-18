package control;

import javax.swing.JOptionPane;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Sistema;

public class ControllerSystem {
	
	private static ControllerSystem instance = new ControllerSystem();
	private ControllerSystem() {}
	public static ControllerSystem getInstance() {return instance;}
	

	//Resgata os valores da tabela dos TextField[][] e o converte em no tipo dado "Matriz"
	public Sistema converteTabelaINSistema(TextField[][] TabelaAmpliada,int eq, int incog){
		
		try {
			if(eq < 1 || incog < 1) {
				JOptionPane.showMessageDialog(null, "O numero de equações e coeficientes devem ser maiores que 0!!", "Erro ao criar Sistema" , JOptionPane.ERROR_MESSAGE);
				return null;
			}
			double[][] matrixCoef = new double[eq][incog];
			double[] matrixTermos = new double[eq];
			
			for(int i = 0; i< eq; i++) {
				for(int j = 0; j< incog; j++) 
					matrixCoef[i][j] = Double.parseDouble(TabelaAmpliada[i][j].getText());
				matrixTermos[i] = Double.parseDouble(TabelaAmpliada[i][incog].getText());
			}
			return new Sistema(matrixCoef, matrixTermos,eq,incog);
			
		}catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Digite apenas números nos campos", "Erro ao criar Sistema" , JOptionPane.ERROR_MESSAGE);
		}
		
		return null;
	}
	
	//Controi o GridPane do Sistema e o preenche com TextField para os coeficientes e os termos
	public void geraTabelaSystem(GridPane GPSystem, TextField[][] TabelaMatrizAmpliada,int numEq, int numIncog) {
    	TextField celula;
    	Label incognita;
    	
    	for(int linha = 0; linha < numEq; linha++) {
    		int coluna = 0;
    		for(; coluna < numIncog-1; coluna++){
    			celula = new TextField();
    			TabelaMatrizAmpliada[linha][coluna] = celula;

    			celula.setPrefHeight(30);
    			celula.setPrefWidth(30);
    			celula.setEditable(true);
    			celula.setText("0");

    			GPSystem.setRowIndex(celula,linha);
    			GPSystem.setColumnIndex(celula, 2*coluna);    
    			GPSystem.getChildren().add(celula);
    			
    			incognita = new Label(" x" + coluna + "  +");
    			GPSystem.setRowIndex(incognita,linha);
    			GPSystem.setColumnIndex(incognita, 2*coluna+1);    
    			GPSystem.getChildren().add(incognita);
    		}
    		//ultimo coeficiente
    		celula = new TextField();
    		TabelaMatrizAmpliada[linha][coluna] = celula;

    		celula.setPrefHeight(30);
    		celula.setPrefWidth(30);
    		celula.setEditable(true);
    		celula.setText("0");

    		GPSystem.setRowIndex(celula,linha);
    		GPSystem.setColumnIndex(celula, 2*coluna);    
    		GPSystem.getChildren().add(celula);

    		incognita = new Label(" x" + coluna + "  =  ");
    		GPSystem.setRowIndex(incognita,linha);
    		GPSystem.setColumnIndex(incognita, 2*coluna+1);    
    		GPSystem.getChildren().add(incognita);
    		
    		//Termo
    		coluna = numIncog;
    		celula = new TextField();
    		TabelaMatrizAmpliada[linha][coluna] = celula;

    		celula.setPrefHeight(30);
    		celula.setPrefWidth(30);
    		celula.setEditable(true);
    		celula.setText("0");

    		GPSystem.setRowIndex(celula,linha);
    		GPSystem.setColumnIndex(celula, 2*coluna);    
    		GPSystem.getChildren().add(celula);
    	}
    		
    }// The end do geraTabelaSystem()
	
	//Recebe um Sistema e retorna um GridPane formado por Label's com os valores do Sistema recebida 
	public GridPane transformaSistemaEmGridPaneLabel(Sistema system) {
		Label celula;
		GridPane GPSistemaLabel = new GridPane();
		
		for(int linha = 0; linha < system.getNumEq(); linha++) {
			int coluna = 0;
			for(; coluna < system.getNumIncog()-1; coluna++){
            	celula = new Label(" (" + system.getCoef(linha, coluna) + ")x" + coluna + " + ");
            	GPSistemaLabel.setRowIndex(celula,linha);
            	GPSistemaLabel.setColumnIndex(celula, coluna);    
            	GPSistemaLabel.getChildren().add(celula);
            }
			//ultimo coeficiente
			celula = new Label(" (" + system.getCoef(linha, coluna) + ")x" + coluna + " = ");
        	GPSistemaLabel.setRowIndex(celula,linha);
        	GPSistemaLabel.setColumnIndex(celula, coluna);    
        	GPSistemaLabel.getChildren().add(celula);
        	
        	//Termo
        	celula = new Label(" " + system.getTermo(linha));
        	GPSistemaLabel.setRowIndex(celula,linha);
        	GPSistemaLabel.setColumnIndex(celula, coluna+1);    
        	GPSistemaLabel.getChildren().add(celula);
		}
		return GPSistemaLabel;
		
	}
	
	//atribui valores ao GPSystemResposta
	private void geraGridPaneResultado(GridPane GPSystemResposta, Sistema system) {
		Label celula;
		GPSystemResposta.getChildren().clear();
		for(int linha = 0; linha < system.getNumEq(); linha++) {
			int coluna = 0;
			for(; coluna < system.getNumIncog()-1; coluna++){
            	celula = new Label(" (" + system.getCoef(linha, coluna) + ")x" + coluna + " + ");
            	GPSystemResposta.setRowIndex(celula,linha);
            	GPSystemResposta.setColumnIndex(celula, coluna);    
            	GPSystemResposta.getChildren().add(celula);
            }
			//ultimo coeficiente
			celula = new Label(" (" + system.getCoef(linha, coluna) + ")x" + coluna + " = ");
			GPSystemResposta.setRowIndex(celula,linha);
			GPSystemResposta.setColumnIndex(celula, coluna);    
			GPSystemResposta.getChildren().add(celula);
        	
        	//Termo
        	celula = new Label(" " + system.getTermo(linha));
        	GPSystemResposta.setRowIndex(celula,linha);
        	GPSystemResposta.setColumnIndex(celula, coluna+1);    
        	GPSystemResposta.getChildren().add(celula);
		}
		
		
	}
	
	//Gera os resultado da operação, onde mostra a matriz e o expoente, junto com a operacao e o resultado 
	public void buildResultadoSystem(HBox HBoxResultado, Sistema system, GridPane GPMatrizResposta) {
			GridPane GPSystem = transformaSistemaEmGridPaneLabel(system);
			HBoxResultado.setAlignment(Pos.CENTER);
			HBoxResultado.getChildren().clear();
			HBoxResultado.getChildren().addAll(GPSystem, new Label("="), GPMatrizResposta);
	}
	
	//Auxiliares do System para Imprimir Gauss e Gauss-Jordan

	/*Gauss*/
	public void gaussAdaptadoHBoxResultado(HBox HBoxResultado, Sistema system) {
		
		GridPane GPSystem = transformaSistemaEmGridPaneLabel(system); 
		HBoxResultado.setAlignment(Pos.CENTER);
		HBoxResultado.getChildren().clear();
		HBoxResultado.getChildren().add(GPSystem);
		VBox VBMudancaLinha;
		
		if(system.getNumEq() <= 1 || system.getNumIncog() <= 1) { 
			HBoxResultado.getChildren().addAll(new Label("<=>"), GPSystem);
			return; //return system;
		}
		
		double[][] matrixAmp = system.getMatrizAmpliada();
		boolean sistemaZerado = true; //identifica se todos os coeficientes estao zerados
		
		for(int i = 0; i < system.getNumEq()-1 && i < system.getNumIncog(); i++) {
			
			if(matrixAmp[i][i] == 0) 
				if(!rowsSwap(HBoxResultado, matrixAmp,i)) 
					continue;
			sistemaZerado = false;
			if(matrixAmp[i][i] != 1) { 
				double divisor = matrixAmp[i][i];
				VBMudancaLinha = new VBox(5);
				VBMudancaLinha.getChildren().addAll(new Label("        ~"),new Label("\nL"+i+" <- L"+i+"/"+divisor+"\n"));
				HBoxResultado.getChildren().add(VBMudancaLinha);
				
				for(int j = 0; j <= system.getNumIncog(); j++) 
					matrixAmp[i][j] /= divisor;
				HBoxResultado.getChildren().add(transformaSistemaEmGridPaneLabel(new Sistema(matrixAmp,system.getNumEq(),system.getNumIncog())));
			}
			
			String linhaAlterada = "";
			for(int linha = i+1; linha < system.getNumEq(); linha++) {
				double firstTermo = matrixAmp[linha][i];
				if(firstTermo == 0)
					continue;
				linhaAlterada += "L"+linha+" <- L"+linha+"- ("+firstTermo +" * L"+i+")\n";
				
				for(int coluna = i; coluna <= system.getNumIncog(); coluna++) 
					matrixAmp[linha][coluna] -= (firstTermo * matrixAmp[i][coluna]);
			}
			
			if(linhaAlterada.length() != 0) {
				VBMudancaLinha = new VBox(5);
				VBMudancaLinha.getChildren().add(new Label("        ~"));
				
				String lA[] = linhaAlterada.split("\n");
				for(String l : lA)
					VBMudancaLinha.getChildren().add(new Label(l));
				HBoxResultado.getChildren().addAll(VBMudancaLinha,transformaSistemaEmGridPaneLabel(new Sistema(matrixAmp,system.getNumEq(),system.getNumIncog())));
			}
		}
		if(sistemaZerado) 
			HBoxResultado.getChildren().addAll(new Label("<=>"),transformaSistemaEmGridPaneLabel(system));
		System.out.println("");
	}
	
	/*Gauss-Jordan*/
	public void gaussJordanAlterado(HBox HBoxResultado,Sistema system) {
		gaussAdaptadoHBoxResultado(HBoxResultado, system);
		
		if(system.getNumEq() <= 1 || system.getNumIncog() <= 1) 
			return ; //return system;
		
		double[][] matrixAmp = system.getMatrizAmpliada();
		VBox VBMudancaLinha;
		
		int i;
		
		if(system.getNumEq() < system.getNumIncog())
			i = system.getNumEq()-1;
		else
			i = system.getNumIncog()-1;
		for(; i >=0; i--) {
			if(matrixAmp[i][i] == 0)  //TODO - Verifica se o Pivo Atual != 0
				continue;
			if(matrixAmp[i][i] != 1) { //Verifica se o Pivo Atual != 1
				double divisor = matrixAmp[i][i];
				
				VBMudancaLinha = new VBox(5);
				VBMudancaLinha.getChildren().addAll(new Label("       ~"),new Label("\nL"+i+" <- L"+i+"/"+divisor+"\n"));
				HBoxResultado.getChildren().add(VBMudancaLinha);
				
				for(int j = 0; j <= system.getNumIncog(); j++) 
					matrixAmp[i][j] /= divisor;
				
				HBoxResultado.getChildren().add(transformaSistemaEmGridPaneLabel(new Sistema(matrixAmp,system.getNumEq(),system.getNumIncog())));
			}
			
			String linhaAlterada = "";
			for(int linha = i-1; linha >= 0; linha--) {
				double firstTermo = matrixAmp[linha][i];	//pega o 1° elemento que vai ser zerado
				if(firstTermo == 0)
					continue;
				linhaAlterada += "L"+linha+" <- L"+linha+"- ("+firstTermo +" * L"+i+")\n";
				
				for(int coluna = system.getNumIncog(); coluna >= 0; coluna--) 
					matrixAmp[linha][coluna] -=  (firstTermo*matrixAmp[i][coluna]);	
			}
			if(linhaAlterada.length() != 0) {
				VBMudancaLinha = new VBox(5);
				VBMudancaLinha.getChildren().add(new Label("        ~"));
				
				String lA[] = linhaAlterada.split("\n");
				for(String l : lA)
					VBMudancaLinha.getChildren().add(new Label(l));
				HBoxResultado.getChildren().addAll(VBMudancaLinha,transformaSistemaEmGridPaneLabel(new Sistema(matrixAmp,system.getNumEq(),system.getNumIncog())));
			}
		}
		
	}//Fim do Gauss-Jordan Alterado
	
	/*Se trocar as linhas retorna true, senão false*/
	public boolean rowsSwap(HBox HBoxResultado, double[][] matrix, int i) {
		int j = i+1;
		while(j < matrix.length && matrix[j][i] == 0)
			j++;
			
		if(j == matrix.length) 
			return false;
		if(matrix[j][i] != 0) {
			VBox VBMudancaLinha = new VBox(5);
			VBMudancaLinha.getChildren().addAll(new Label("    ~"),new Label("L" + i + " <-> L" + j));
			HBoxResultado.getChildren().add(VBMudancaLinha);
			
			double aux;
			for(int coluna = 0; coluna < matrix[0].length; coluna++) {
				aux = matrix[i][coluna];
				matrix[i][coluna] = matrix[j][coluna]; 
				matrix[j][coluna] = aux;
			}

			HBoxResultado.getChildren().add(transformaSistemaEmGridPaneLabel(new Sistema(matrix,matrix.length,matrix[0].length)));
		}
		return true;
	}
	
}	
