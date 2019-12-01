package controller;

import javax.swing.JOptionPane;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Vetor;

public class ControllerBase {
	private static ControllerBase instance = new ControllerBase();
	private ControllerBase() {}
	public static ControllerBase getInstance() {return instance;}
	

	//Controi o GridPane da Base e o preenche com TextField para os Vetores e suas Coordenadas
	public void geraTabelaBase(GridPane GPBase, TextField[][] TabelaBase, int numVetores) {
		TextField celula;
		Label simbolo;
		
		for(int linha = 0; linha < numVetores; linha++) {
			int coluna = 0;
			simbolo = new Label("(  ");
			GPBase.setRowIndex(simbolo,linha);
			GPBase.setColumnIndex(simbolo, coluna);    
			GPBase.getChildren().add(simbolo);
			
			for(; coluna < numVetores-1; coluna++){
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
	public Vetor[] converteTabelaINBase(TextField[][] TabelaBase,int numVet){

		try {
			if(numVet < 1) {
				JOptionPane.showMessageDialog(null, "O número de Vetores e de Coordenadas devem ser maiores que 0!!", "Erro ao criar a Base" , JOptionPane.ERROR_MESSAGE);
				return null;
			}
			
			Vetor base[] = new Vetor[numVet];
			Vetor vector;
			for(int i = 0; i< numVet; i++) {
				vector = new Vetor(numVet);
				for(int j = 0; j< numVet; j++) 
					vector.setValorCoordenada(j, Double.parseDouble(TabelaBase[i][j].getText()));
				base[i] = vector;
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
	
	//recebe a base e gera um VBox deacordo com as coordenadas(toString()) de cada vetor
	public VBox geraGridPaneResultado(Vetor base[]) {
		VBox VBVetores = new VBox(5);
		for(Vetor vetor : base) 
			VBVetores.getChildren().add(new Label(vetor.toString()));
		return VBVetores;
	}
	
	//Gera o HoxResultado, com a Base Anterior e a Base Resposta
	public void buildResultadoBase(HBox HBoxResultado, Vetor base[], VBox VBResposta) {
		GridPane GPSystem = transformaBaseEmGridPaneLabel(base);
		HBoxResultado.setAlignment(Pos.CENTER);
		HBoxResultado.getChildren().clear();
		HBoxResultado.getChildren().addAll(GPSystem, new Label("="), VBResposta);
	}

}
