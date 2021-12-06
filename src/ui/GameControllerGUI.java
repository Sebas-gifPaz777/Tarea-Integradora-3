package ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Game;
import model.Players;

public class GameControllerGUI {
	
	private Game game;
	
	private int answer;
	
	private Stage mainStage;
	
	private String defAnswer;
	
	private int points;
	
	@FXML
    private Pane mainPane;
	
	//BUTTONS
	@FXML
	private Button btOption1,btOption2,btOption3,btOption4;

	//Textfields
	@FXML
    private TextField tfName;
	
	//Labels
	@FXML
	private Label lbQuestion;
	
	@FXML
    private TextField lbExample;
	
	@FXML
    private Label lbPoints;
	
	//TableView and Columns
	@FXML
    private TableView<Players> tvScoreList;
	
	@FXML
	private TableColumn<Players, String> tcName;

	@FXML
	private TableColumn<Players, String> tcScore;
	
	List<Integer> options=new ArrayList<>();
	ObservableList<Players> observablelist;

	public  GameControllerGUI() {
		game =new Game();
		
	}

	public int getAnswer() {
		return answer;
	}

	public void setAnswer(int answer) {
		this.answer = answer;
	}

	public Stage getMainStage() {
		return mainStage;
	}

	public void setMainStage(Stage mainStage) {
		this.mainStage = mainStage;
	}
	
	public void firstWindow() throws IOException {
		changeWindows("Menu.fxml");
	}
	
	public String getDefAnswer() {
		return defAnswer;
	}

	public void setDefAnswer(String defAnswer) {
		this.defAnswer = defAnswer;
	}

	public void changeWindows(String jvfxml) throws IOException {//Correct
		FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource(jvfxml));
		fxmlLoader.setController(this);
		Parent form=fxmlLoader.load();
		mainPane.getChildren().setAll(form);
	}
	
	public void showChallenge() {
		String question=game.createQuest();
		int answer=game.getAnswer();
		defAnswer=String.valueOf(game.getAnswer());
		options.add(answer);
		int num=0;
		int max=answer+10;
		int min=answer-10;
		
		for (int i = 0; i <= 2; i++) {
		    num = (int) Math.floor(Math.random()*(max-min+1)+min);
		    if (options.contains(num)) {
		        i--;
		    } else {
		        options.add(num);
		    }
		}
		
		Random rand= new Random();
		List<Integer> givenOptions = new ArrayList<>();
		boolean next=false;
		int equAnswer=0;
		
		while(!next) {
			
			int posible1=options.get(rand.nextInt(options.size()));
			int posible2=options.get(rand.nextInt(options.size()));
			int posible3=options.get(rand.nextInt(options.size()));
			int posible4=options.get(rand.nextInt(options.size()));
			
			givenOptions.add(posible1);
			givenOptions.add(posible2);
			givenOptions.add(posible3);
			givenOptions.add(posible4);
			
			
			for(int i=0;i<4;i++) {// Check of the option answer
				if(givenOptions.get(i)==answer && !next) {
					next=true;
				}
				else if(givenOptions.get(i)==answer) {
					givenOptions.set(i,options.get(rand.nextInt(options.size())));
					i--;
				}
			}
			
			if(!next)
				givenOptions.clear();
			else {
				for(int i=1;i<3;i++) {
					for(int j=i+1;j<4;j++) {
						if(givenOptions.get(i)==givenOptions.get(j)) {
							givenOptions.set(j,options.get(rand.nextInt(options.size())));
							j--;
						}
					}
				}
			}
		}
		
		btOption1.setText(String.valueOf(givenOptions.get(0)));
		btOption2.setText(String.valueOf(givenOptions.get(1)));
		btOption3.setText(String.valueOf(givenOptions.get(2)));
		btOption4.setText(String.valueOf(givenOptions.get(3)));
		lbQuestion.setText(question);
		
		
		
		lbPoints.setText(String.valueOf(points));
	}
	
	public void replyAnswer(String selected) {// Correct
		if(defAnswer.equalsIgnoreCase(selected)) {
		points+=10;
		}
		else {
		points-=10;	
		}
		lbPoints.setText(String.valueOf(points));
	}
	
	@FXML
	public void answer1(ActionEvent event){//Correct
		replyAnswer(btOption1.getText());
		showChallenge();
	}
	
	@FXML
	public void answer2(ActionEvent event){//Correct
		replyAnswer(btOption2.getText());
		showChallenge();
	}
	
	@FXML
	public void answer3(ActionEvent event){//Correct
		replyAnswer(btOption3.getText());
		showChallenge();
	}
	
	@FXML
	public void answer4(ActionEvent event){//Correct
		replyAnswer(btOption4.getText());
		showChallenge();
	}
	
	@FXML
    public void startGame(ActionEvent event) throws IOException {//Correct
		changeWindows("Question-Pane.fxml");
		showChallenge();
    }
	
	private void initializeTableView() throws IOException {
		game.importData();
		//game.ejectInorden();
		lbExample.setText(game.getPlayer().get(0).getName());
		observablelist= FXCollections.observableArrayList(game.getPlayer());

		tvScoreList.setItems(observablelist);
		tcName.setCellValueFactory(new PropertyValueFactory<Players,String>("name"));
		tcScore.setCellValueFactory(new PropertyValueFactory<Players,String>("score"));
		
	}
	
	 @FXML
	 public void goScore(ActionEvent event) throws IOException {
		 changeWindows("ScoreBoard.fxml");
		 initializeTableView();
		 lbExample.setText(game.getPlayer().get(0).getName());
	 }
	 
	 @FXML
	 public void goMenu(ActionEvent event) throws IOException {
		 changeWindows("Menu.fxml");
	 }

}
