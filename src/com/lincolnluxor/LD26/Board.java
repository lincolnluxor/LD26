package com.lincolnluxor.LD26;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class Board extends JPanel implements ActionListener {
	
	private Timer timer;
	private Player player;
	public boolean activeGame = false;
	public boolean endGame = false;
	public boolean isInstructions = false;
	public boolean isMenu = true;
	public boolean isCredits = false;
	private Image imageWall;
	private Image imageTop;
	private Image imageMenu1on;
	private Image imageMenu1off;
	private Image imageMenu2on;
	private Image imageMenu2off;
	private Image imageMenu3on;
	private Image imageMenu3off;
	private Image imageMenu;
	private Image imageCredits;
	private Image imageInstructions;
	private Image imageCoin;
	private Image imageEnd;
	private Image imageSpeed;
	private Image imageO;
	private String imageWallName = "wall.png";
	private String imageOName = "O.png";
	private String imageTopName = "top.png";
	private String imageEndName = "end.png";
	private String imageMenuName = "menu.png";
	private String imageCreditsName = "credits.png";
	private String imageInstructionsName = "instructions.png";
	private String imageMenu1onName = "starton.png";
	private String imageMenu1offName = "startoff.png";
	private String imageMenu2onName = "instructionson.png";
	private String imageMenu2offName = "instructionsoff.png";
	private String imageMenu3onName = "creditson.png";
	private String imageMenu3offName = "creditsoff.png";
	private String imageCoinName = "coin.png";
	private String imageSpeedName = "speed.png";
	private int topX = 0;
	private int menuSelection = 1;
	private int x = 0;
	private int y = 580;
	private int spawnRate = 1;
	private int coinCounter = 0;
	Random random = new Random();
	Random generator = new Random();
	ArrayList<Block> blocksArray = new ArrayList<Block>();
	ArrayList<Coin> coinsArray = new ArrayList<Coin>();
	ArrayList<Hitbox> hitboxArray = new ArrayList<Hitbox>();
	ArrayList<Triangle> triangleArray = new ArrayList<Triangle>();
	Color greyColor = new Color(102,102,102);
	
	public Board() {
		addKeyListener(new TAdapter());
		setFocusable(true);
		setBackground(greyColor);
		setDoubleBuffered(true);
		timer = new Timer(5, this);
		timer.start();
		player = new Player(this);
	}
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D)g;
		getImages();		
		if (isMenu && !activeGame && !endGame && !isInstructions) {
			reset();
			player.reset();
			g2d.drawImage(imageMenu, 0, 0, this);
			x++;
			if (x > 600) {
				resetX();
				x-=118;
			}
			g2d.drawImage(imageO, 408, x, this);
			if (menuSelection == 1) {
				g2d.drawImage(imageMenu1on, 156, 348, this);
				g2d.drawImage(imageMenu2off, 156, 404, this);
				g2d.drawImage(imageMenu3off, 156, 460, this);
			}
			else if (menuSelection == 2) {
				g2d.drawImage(imageMenu1off, 156, 348, this);
				g2d.drawImage(imageMenu2on, 156, 404, this);
				g2d.drawImage(imageMenu3off, 156, 460, this);
			}
			else if (menuSelection == 3) {
				g2d.drawImage(imageMenu1off, 156, 348, this);
				g2d.drawImage(imageMenu2off, 156, 404, this);
				g2d.drawImage(imageMenu3on, 156, 460, this);
			}
			
		}
		if (isInstructions && !activeGame) {
			isMenu = false;
			g2d.drawImage(imageInstructions, 0, -10, this);
		}
		if (isCredits && !activeGame) {
			isMenu = false;
			g2d.drawImage(imageCredits, 0, -10, this);
		}
		if (activeGame) {
			x++;
			if (x < 1800 && x > 1250) {
				g2d.drawImage(imageSpeed, 0, y, this);
				y-=2;
				moveBlocks();
				moveCoins();
				moveHitboxes();
				moveTriangles();
			}
			if (x == 1250) {
				SoundEffects.WIND.play();
			}
			else if (x >= 1800) {
				resetX();
				y=600;
			}
			//g2d.drawString(Integer.toString(x), 571, 403);
			g2d.drawImage(imageWall, 0, 0, this);
			g2d.drawImage(imageWall, 775, 0, this);
			g2d.drawImage(player.getPlayerImage(), player.getPlayerX(), player.getPlayerY(), this);
			drawAll(g);
			moveAll();
			removeAll();
			updateSpawnRate();
			player.checkCollision(coinsArray, blocksArray, hitboxArray, triangleArray, coinCounter);
			player.checkTopCollision();
			activeGame = player.checkGame();
			drawScore(g);
			if (x < 1250 && x > 900) {
				g2d.setColor(Color.YELLOW);
				Font font = new Font("monospaced", Font.BOLD, 42);
				g2d.setFont(font);
				g2d.drawString("GET READY", 250, 150);	
			}
			if (activeGame == false) {
				endGame = true;
			}
		}
		if (endGame){
			isMenu = false;
			g2d.drawImage(imageEnd, 0, -10, this);
			g2d.setColor(Color.BLACK);
			Font font = new Font("monospaced", Font.BOLD, 82);
			g2d.setFont(font);
			g2d.drawString(Integer.toString(player.getScore()), 601, 403);
		}
		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}
	public void drawScore(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		ImageIcon coinii = new ImageIcon(this.getClass().getResource(imageCoinName));
		imageCoin = coinii.getImage();
		int scoreSpacer = 5;
		for (int i = 0; i < player.getScore(); i++) {
			g2d.drawImage(imageCoin, 5, scoreSpacer, this);
			if (player.getScore() < 23) {
				scoreSpacer += 25;
			}
			else if (player.getScore() > 38) {
				scoreSpacer += 10;
			}
			else {
				scoreSpacer += 15;
			}
		}
	}
	public void drawAll(Graphics g) {
		drawTop(g);
		drawBlocks(g);
		drawCoins(g);
		drawTriangle(g);
	}
	public void moveAll() {
		moveBlocks();
		moveCoins();
		moveHitboxes();
		moveTriangles();
		player.movePlayer();
	}
	public void removeAll() {
		removeBlocks();
		removeCoins();
		removeHitboxes();
		removeTriangles();
	}
	public void updateSpawnRate() {
		if (player.getScore() != 0) {
			spawnRate = player.getScore()/5 + 1;
		}
		else {
			spawnRate = 1;
		}
	}
	private void drawTop(Graphics g) {
		moveTop();
		topX++;
		Graphics2D g2d = (Graphics2D)g;
		g2d.drawImage(imageTop, topX, 0, this);
	}
	private int moveTop() {
		if (topX >= 20) {
			topX = 0;
		}
		return topX;
	}
	public void moveHitboxes() {
		for (Hitbox hitbox : hitboxArray) {
			hitbox.moveup();
		}
	}
	public void moveCoins() {
		for (Coin coin : coinsArray) {
			coin.moveup();
		}
		if (coinsArray.isEmpty()) {
			if (x % 25 == 0) {
				coinCounter++;
				if (coinCounter % 5 == 0) { 
					createBlueCoin();
				}
				else {
					createCoin();
				}
			}
		}
	}
	public void moveBlocks() {
		if (blocksArray.isEmpty()) {
			createBlock();
		}
		int trigger = 0;
		for (Block block : blocksArray) {
			block.moveup();
			if (block.getBlockY() % 25 == 0) {
				trigger++;
			}
		}
		if (trigger > 0) {
			for (int i = 0; i < spawnRate; i++) {
				createBlock();				
			}
		}	
	}
	public void moveTriangles() {
		for (Triangle triangle : triangleArray) {
			triangle.moveup();
		}
		if (x % 600 == 0 || x == 0) {
			if (x % 25 == 0) {
				createTriangle();
				createTriangle();
				createTriangle();
				createTriangle(); 
				createTriangle();				
			}
		}
	}
	public void removeBlocks() {
		for (int i = 0; i < blocksArray.size(); i++) {
			Block block = blocksArray.get(i);
			if (block.getBlockY() < 26) {
				blocksArray.remove(i);
			}
		}
	}
	public void removeCoins() {
		for (int i = 0; i < coinsArray.size(); i++) {
			Coin coin = coinsArray.get(i);
			if (coin.getCoinY() < 26) {
				coinsArray.remove(i);
			}
		}
	}
	public void removeHitboxes() {
		for (int i = 0; i < hitboxArray.size(); i++) {
			Hitbox hitbox = hitboxArray.get(i);
			if (hitbox.getHitboxY() < 26) {
				hitboxArray.remove(i);
			}
		}
	}
	public void removeTriangles() {
		for (int i = 0; i < triangleArray.size(); i++) {
			Triangle triangle = triangleArray.get(i);
			if (triangle.getTriangleY() < 26) {
				triangleArray.remove(i);
			}
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		repaint();
	}
	
	private class TAdapter extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();
			if (key == KeyEvent.VK_Q && activeGame) {
				reset();
				activeGame = false;
			}
			if ((key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) && isMenu && menuSelection < 3){
				menuSelection++;
			}
			else if ((key == KeyEvent.VK_UP || key == KeyEvent.VK_W) && isMenu && menuSelection > 1) {
				menuSelection--;
			}
			if (key == KeyEvent.VK_ENTER && isMenu && menuSelection == 1) {
				resetX();
				activeGame = true;
			}
			if (key == KeyEvent.VK_ENTER && isMenu && menuSelection == 2) {
				isInstructions = true;
			}
			if (key == KeyEvent.VK_ENTER && isMenu && menuSelection == 3) {
				isCredits = true;
			}
			if (key == KeyEvent.VK_ENTER && isInstructions && !isMenu) {
				isMenu = true;
				isInstructions = false;
			}
			if (key == KeyEvent.VK_ENTER && isCredits && !isMenu) {
				isMenu = true;
				isCredits = false;
			}
			if (key == KeyEvent.VK_ENTER && endGame && !isMenu) {
				isMenu = true;
				endGame = false;
			}
			player.keyPressed(e);
		}
	}
	private void reset() {
		blocksArray.clear();
		coinsArray.clear();
		hitboxArray.clear();
		triangleArray.clear();
		player.reset();
	}
	private void drawBlocks(Graphics g) {
		for (Block block : blocksArray) {
			Graphics2D g2d = (Graphics2D)g;
			g2d.drawImage(block.getBlockImage(), block.getBlockX(), block.getBlockY(), this);
		}
	}
	private void drawCoins(Graphics g) {
		for (Coin coin : coinsArray) {
			Graphics2D g2d = (Graphics2D)g;
			if (coinCounter % 5 == 0) {
				g2d.drawImage(coin.getBlueCoinImage(), coin.getCoinX()+5, coin.getCoinY(), this);
			}
			else {
				g2d.drawImage(coin.getCoinImage(), coin.getCoinX()+5, coin.getCoinY(), this);
			}
		}
	}
	private void drawTriangle(Graphics g) {
		for (Triangle triangle : triangleArray) {
			Graphics2D g2d = (Graphics2D)g;
			g2d.drawImage(triangle.getTriangleImage(), triangle.getTriangleX(), triangle.getTriangleY(), this);
		}
	}
	public void createBlock() {
		int blockX = generator.nextInt(750) + 25;
		blockX = (blockX/25) * 25;
		int blockY = 600;
		Block block = new Block(blockX, blockY);
		int blockID = blocksArray.size();
		String type = "block";
		Hitbox hitbox = new Hitbox(blockX, blockY, 25, 25, type, blockID);
		blocksArray.add(blocksArray.size(),block);				
		hitboxArray.add(hitbox);
	}
	public void createCoin() {
		int coinX = generator.nextInt(750)+ 25;
		coinX = (coinX/25)*25;
		int coinY = 600;
		Coin coin = new Coin(coinX, coinY, coinCounter);
		int coinID = coinsArray.size();
		String type = "coin";
		Hitbox hitbox = new Hitbox(coinX, coinY, 15, 25, type, coinID);
		coinsArray.add(coinsArray.size(),coin);
		hitboxArray.add(hitbox);
	}
	public void createBlueCoin() {
		int coinX = generator.nextInt(750)+ 25;
		coinX = (coinX/25)*25;
		int coinY = 600;
		Coin coin = new Coin(coinX, coinY, coinCounter);
		int coinID = coinsArray.size();
		String type = "coin";
		Hitbox hitbox = new Hitbox(coinX, coinY, 15, 25, type, coinID);
		coinsArray.add(coinsArray.size(),coin);
		hitboxArray.add(hitbox);
	}
	public void createTriangle() {
		int triangleX = generator.nextInt(750)+ 25;
		triangleX = (triangleX/25)*25;
		int triangleY = 600;
		Triangle triangle = new Triangle(triangleX, triangleY);
		int triangleID = triangleArray.size();
		String type = "triangle";
		Hitbox hitbox = new Hitbox(triangleX, triangleY, 15, 25, type, triangleID);
		triangleArray.add(triangleArray.size(),triangle);
		hitboxArray.add(hitbox);
	}
	public boolean activeGame() {
		activeGame = true;
		return activeGame;
	}
	public boolean deactiveGame() {
		activeGame = false;
		return activeGame;
	}
	public void resetX() {
		x = 0;
	}
	public void getImages() {
		ImageIcon topii = new ImageIcon(this.getClass().getResource(imageTopName));
		imageTop = topii.getImage();
		ImageIcon sii = new ImageIcon(this.getClass().getResource(imageSpeedName));
		imageSpeed = sii.getImage();
		ImageIcon wii = new ImageIcon(this.getClass().getResource(imageWallName));
		imageWall = wii.getImage();
		ImageIcon mii = new ImageIcon(this.getClass().getResource(imageMenuName));
		imageMenu = mii.getImage();
		ImageIcon m1onii = new ImageIcon(this.getClass().getResource(imageMenu1onName));
		imageMenu1on = m1onii.getImage();
		ImageIcon m2onii = new ImageIcon(this.getClass().getResource(imageMenu2onName));
		imageMenu2on = m2onii.getImage();
		ImageIcon m3onii = new ImageIcon(this.getClass().getResource(imageMenu3onName));
		imageMenu3on = m3onii.getImage();
		ImageIcon m1offii = new ImageIcon(this.getClass().getResource(imageMenu1offName));
		imageMenu1off = m1offii.getImage();
		ImageIcon m2offii = new ImageIcon(this.getClass().getResource(imageMenu2offName));
		imageMenu2off = m2offii.getImage();
		ImageIcon m3offii = new ImageIcon(this.getClass().getResource(imageMenu3offName));
		imageMenu3off = m3offii.getImage();
		ImageIcon iii = new ImageIcon(this.getClass().getResource(imageInstructionsName));
		imageInstructions = iii.getImage();
		ImageIcon cii = new ImageIcon(this.getClass().getResource(imageCreditsName));
		imageCredits = cii.getImage();
		ImageIcon eii = new ImageIcon(this.getClass().getResource(imageEndName));
		imageEnd = eii.getImage();
		ImageIcon oii = new ImageIcon(this.getClass().getResource(imageOName));
		imageO = oii.getImage();
	}
}

