package model;

public class Players {

	private String name;
	private String score;
	private Players left;
	private Players right;
	public Players(String name, String score) {
		this.name = name;
		this.score = score;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getName() {
		return name;
	}
	public Players getLeft() {
		return left;
	}
	public void setLeft(Players left) {
		this.left = left;
	}
	public Players getRight() {
		return right;
	}
	public void setRight(Players right) {
		this.right = right;
	}
	
	public void insert(Players newPlayer) {
		if(Integer.parseInt(score)>Integer.parseInt(newPlayer.getScore())) {
			if(getLeft()==null) {
				setLeft(newPlayer);
			}
			else {
				getLeft().insert(newPlayer);
			}
		}
		else {
			if(getRight()==null) {
				setRight(newPlayer);
			}
			else {
				getRight().insert(newPlayer);
			}
		}
	}
}
