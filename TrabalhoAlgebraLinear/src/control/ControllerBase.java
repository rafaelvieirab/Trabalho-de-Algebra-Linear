package control;

import javax.swing.JOptionPane;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import model.Vetor;

public class ControllerBase {
	private static ControllerBase instance = new ControllerBase();
	private ControllerBase() {}
	public static ControllerBase getInstance() {return instance;}
	

	//Controi o GridPane da Base e o preenche com TextField para os Vetores e suas Coordenadas
	public void geraTabelaBase(GridPane GPBase, TextField[][] TabelaBase, int numVetores, int numCoordenadas) {
		TextField celula;
		Label simbolo;
		
		for(int linha = 0; linha < numVetores; linha++) {
			int coluna = 0;
			simbolo = new Label("(  ");
			GPBase.setRowIndex(simbolo,linha);
			GPBase.setColumnIndex(simbolo, coluna);    
			GPBase.getChildren().add(simbolo);
			
			for(; coluna < numCoordenadas-1; coluna++){
				celula = new TextField();
				TabelaBase[linha][coluna] = celula;

				celula.setPrefHeight(30);
				celula.setPrefWidth(30);
				celula.setEditable(true);
				celula.setText("0");

				GPBase.setRowIndex(celula,linha);
				GPBase.setColumnIndex(celula, 2*coluna+1);    
				GPBase.getChildren().add(celula);
				
				simbolo = new Label(" , ");
				GPBase.setRowIndex(simbolo,linha);
				GPBase.setColumnIndex(simbolo, 2*coluna+2);    
				GPBase.getChildren().add(simbolo);
			}
			//ultima Coordenada
			celula = new TextField();
			TabelaBase[linha][coluna] = celula;

			celula.setPrefHeight(30);
			celula.setPrefWidth(30);
			celula.setEditable(true);
			celula.setText("0");

			GPBase.setRowIndex(celula,linha);
			GPBase.setColumnIndex(celula, 2*coluna+1);    
			GPBase.getChildren().add(celula);
			
			simbolo = new Label("  )");
			GPBase.setRowIndex(simbolo,linha);
			GPBase.setColumnIndex(simbolo, 2*coluna+2);    
			GPBase.getChildren().add(simbolo);
		}
	}
	

	//Resgata os valores da tabela dos TextField[][] e o converte em no tipo dado "Vector[]"
	public Vetor[] converteTabelaINBase(TextField[][] TabelaBase,int numVet, int numCoord){

		try {
			if(numVet < 1 || numCoord < 1) {
				JOptionPane.showMessageDialog(null, "O número de Vetores e de Coordenadas devem ser maiores que 0!!", "Erro ao criar a Base" , JOptionPane.ERROR_MESSAGE);
				return null;
			}
			Vetor base[] = new Vetor[numVet];
			double vetor[] = new double[numCoord];
			for(int i = 0; i< numVet; i++) {
				for(int j = 0; j< numCoord; j++) 
					vetor[j] = Double.parseDouble(TabelaBase[i][j].getText());
				base[i] = new Vetor(vetor);
			}

			return base;

		}catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Digite apenas números nos campos", "Erro ao criar Base" , JOptionPane.ERROR_MESSAGE);
		}

		return null;
	}
	

	//Recebe uma Vetor[] e retorna um GridPane formado por Label's com os valores do vetor
	public GridPane transformaBaseEmGridPaneLabel(Vetor base[]) {
		Label celula;
		GridPane GPBase = new GridPane();
		String vetor;
		for(int linha = 0; linha < base.length; linha++) {
			int coluna = 0;
			vetor = "( ";
			
			for(; coluna < base[0].getNumCoordenadas() -1; coluna++)  
				vetor += base[linha].getValorCoordenada(coluna) + ", ";
			vetor += base[linha].getValorCoordenada(coluna) + " )";
			
			celula = new Label(vetor);
			GPBase.setRowIndex(celula,linha);
			GPBase.setColumnIndex(celula, coluna);    
			GPBase.getChildren().add(celula);
		}
		return GPBase;

	}
	

	//atribui valores ao GPSystemResposta
	public void geraGridPaneResultado(GridPane GPBaseResposta, Vetor base[]) {
		Label celula;
		GPBaseResposta.getChildren().clear();
		
		String vetor;
		for(int linha = 0; linha < base.length; linha++) {
			int coluna = 0;
			vetor = "( ";
			
			for(; coluna < base[0].getNumCoordenadas() -1; coluna++)
				vetor += base[linha].getValorCoordenada(coluna) + ", ";
			vetor += base[linha].getValorCoordenada(coluna) + " )";
			
			celula = new Label(vetor);
			GPBaseResposta.setRowIndex(celula,linha);
			GPBaseResposta.setColumnIndex(celula, coluna);    
			GPBaseResposta.getChildren().add(celula);
		}

	}
	

	//Gera os resultado da operação, onde mostra a matriz e o expoente, junto com a operacao e o resultado 
	public void buildResultadoBase(HBox HBoxResultado, Vetor base[], GridPane GPMatrizResposta) {
		GridPane GPSystem = transformaBaseEmGridPaneLabel(base);
		HBoxResultado.setAlignment(Pos.CENTER);
		HBoxResultado.getChildren().clear();
		HBoxResultado.getChildren().addAll(GPSystem, new Label("="), GPMatrizResposta);
	}

}
