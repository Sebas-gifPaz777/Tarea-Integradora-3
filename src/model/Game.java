package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Game {
	
	private int answer;
	
	private Players root;
	private List<Players>player= new ArrayList<>();
	public String FILE__TXT_PATH="data/playersData.txt";
	
	public Game() {
		this.answer = 0;
		root=null;
		player= new ArrayList<>();
		player.add(new Players("HOla","23"));
	}
	
	
	public int getAnswer() {
		return answer;
	}
	
	public void setAnswer(int answer) {
		this.answer = answer;
	}
	
	public List<Players> getPlayer() {
		return player;
	}
	
	public void addPlayerL(Players newPlayer) {
		player.add(newPlayer);
	}

	public String createQuest() {
		int dig1=(int)(Math.random()*100);
		int dig2=(int)(Math.random()*100);
		int sign=(int)(Math.random()*4);
		String sdig1=String.valueOf(dig1);
		String sdig2=String.valueOf(dig2);
		int ans=0;
		String question="";
		
		switch(sign) {
			
		case 0:
			ans=dig1+dig2;
			question=sdig1+"+"+sdig2;
			setAnswer(ans);
		break;
			
		case 1:
			if(dig2>dig1) {
				ans=(int)dig2/dig1;
				question=sdig2+"/"+sdig1;
			}
			else {
				ans=(int)dig1/dig2;
				question=sdig1+"/"+sdig2;
			}
			setAnswer(ans);
		break;
		
		case 2:
			ans=dig1*dig2;
			question=sdig1+"*"+sdig2;
			setAnswer(ans);
		break;
		
		case 3:
			ans=dig1-dig2;
			question=sdig1+"-"+sdig2;
			setAnswer(ans);
		break;
		
		}
		
		return question;
	}
	
	public int createAnswer() {
		return getAnswer();
	}
	
	public void addPlayer(Players newPlayer) {
		if(root==null)
			root=newPlayer;
		else 
			root.insert(newPlayer);
	}
	
	public void ejectInorden() {
		inorden(root);
	}
	
	public void inorden(Players node) {
		if(node==null) {
			return;
		}
		else {
			inorden(node.getLeft());
			player.add(node);
			inorden(node.getRight());
		}
	}
	
	public void importData() throws IOException {
		BufferedReader br=new BufferedReader(new FileReader(FILE__TXT_PATH));
		String line=br.readLine();
		while(line!=null) {
			String[]parts=line.split(";");
			Players register=new Players(parts[0],parts[1]);
			addPlayerL(register);
			line=br.readLine();
		}
		br.close();
	}
	
	public void exportData() throws IOException {

		FileWriter fw= new FileWriter(FILE__TXT_PATH,false);//false-> Reemplazar el archivo, true->Adicionar elementos al archivo
		for(int i=0;i<player.size();i++) {
			Players myPlayer=player.get(i);
			fw.write(myPlayer.getName()+";"+myPlayer.getScore()+"\n");
		}
		fw.close();
	}
}
