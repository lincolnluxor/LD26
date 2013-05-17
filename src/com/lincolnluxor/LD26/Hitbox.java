package com.lincolnluxor.LD26;

import java.awt.Rectangle;

public class Hitbox {
	private Rectangle hitbox;
	private int posX;
	private int posY;
	private int width;
	private int height;
	private int id;
	private String type;
	
	public Hitbox(int posX2, int posY2, int width2, int height2, String type2, int id2) {
		posX = posX2;
		posY = posY2; 
		width = width2;
		height = height2;
		type = type2;
		id = id2;
		hitbox = new Rectangle(posX, posY, width, height);
		
	}
	public void moveup() {
		posY = posY - 1;
	}
	public String getType() {
		return type;
	}
	public Rectangle getHitbox() {
		return hitbox;
	}
	public int getID() {
		return id;
	}
	public int getHitboxY() {
		return posY;
	}
	public int getHitboxX() {
		return posX;
	}
	public int getHitboxHeight() {
		return height;
	}
}
