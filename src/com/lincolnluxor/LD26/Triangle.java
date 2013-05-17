package com.lincolnluxor.LD26;

import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class Triangle {
	private int triangleX;
	private int triangleY = 600;
	private int triangleWidth = 25 ;
	private int triangleHeight = 25;
	private Image imageTriangle;
	private String imageTriangleName = "triangle.png";
	private Rectangle hitbox;
	
	public Triangle(int triangleX2, int triangleY2) {
		triangleX = triangleX2;
		triangleY = triangleY2;
		getTriangleImage();
		hitbox = new Rectangle(triangleX, triangleY, triangleWidth, triangleHeight);
	}
	public int getTriangleX() {
		return triangleX;
	}
	public int getTriangleY() {
		return triangleY;
	}
	public int getTriangleWidth() {
		return triangleWidth;
	}
	public int getTriangleHeight() {
		return triangleHeight;
	}
	public Rectangle getHitbox() {
		return hitbox;
	}
	public Image getTriangleImage() {
		ImageIcon tii = new ImageIcon(this.getClass().getResource(imageTriangleName));
		imageTriangle = tii.getImage();
		return imageTriangle;
	}
	public int moveup() {
		triangleY = triangleY - 1;
		hitbox = new Rectangle(triangleX, triangleY, triangleWidth, triangleHeight);
		return triangleY;
	}
}
