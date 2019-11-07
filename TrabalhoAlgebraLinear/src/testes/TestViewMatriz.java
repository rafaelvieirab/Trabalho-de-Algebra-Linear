package testes;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class TestViewMatriz extends Application  {
	
	private  int linha = 0;
	private  int coluna = 0;
	
	private TextField tabela[][];
	
	public static void main(String[] args) {    
        launch(args);
    }
	
	@SuppressWarnings({ "static-access", "rawtypes", "unchecked" })
	@Override
    public void start(Stage primaryStage) {
		/*Ideia: Usar um text Fild para ser uma celula;
		 * Definir: o tamanho da "célula" e o espaço maximo; 
		 * */

		
		final TextField colunaTF = new TextField("");
		final TextField linhaTF = new TextField("");
		
		GridPane grade = new GridPane();    
		VBox raiz = new VBox(10);// VBox: ira colocar tudo na vertical
		raiz.setTranslateX(10);
		raiz.setTranslateY(20);
		
		
		HBox linhaHBox = new HBox(60);
		HBox colunaHBox = new HBox(70);
		
		linhaHBox.getChildren().addAll(new Label("Linha:"), linhaTF);
		colunaHBox.getChildren().addAll(new Label("Coluna:"), colunaTF);
		
		Button ok = new Button("OK");
		Button BPegar = new Button("Pegar Valores");
		
		ok.setMaxSize(80, 100);
		HBox botao1 = new HBox(30);//ira colocar tudo na Horizontal
		
		botao1.getChildren().addAll(ok,BPegar);

        raiz.getChildren().addAll(linhaHBox,colunaHBox, botao1,grade);
        Scene scene = new Scene(raiz, 500, 500);    
        primaryStage.setTitle("Random Binary Matrix (JavaFX)");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        //Ações dos butoes
	    ok.setOnAction(new EventHandler() {
			
			public void handle(Event arg0) {
				TextField celula;
				linha = Integer.parseInt(linhaTF.getText());
				coluna = Integer.parseInt(colunaTF.getText());
				
				tabela = new TextField[linha][coluna];
				
		        for(int y = 0; y < linha; y++){
		            for(int x = 0; x < coluna; x++){

		            	celula = new TextField();
		            	tabela[y][x] = celula;
		            	
		                // Create a new TextField in each Iteration
		                celula.setPrefHeight(50);
		                celula.setPrefWidth(50);
		                //celula.setAlignment(Pos.CENTER);
		                celula.setEditable(true);
		                celula.setText("0");

		                // Iterate the Index using the loops
		                grade.setRowIndex(celula,y);
		                grade.setColumnIndex(celula, x);    
		                grade.getChildren().add(celula);
		            }
		        }
			}//Fim do loop X; 
		  });
	    BPegar.setOnAction(new EventHandler() {
			
			public void handle(Event arg0) {
				TextField celula;
				String str = "";
				
				System.out.println("Exibindo o que foi digitado pelo usuario:");
				
		        for(int y = 0; y < linha; y++){
		            for(int x = 0; x < coluna; x++){

		            	celula = tabela[y][x];
		            	
		                str = str + celula.getText() + " ";
		        
		                

		            }//Fim do loop X; 
		            System.out.println(str);
		            str = "";
		        }
			}
		  });
	    
		}//close Start	

}//close class


