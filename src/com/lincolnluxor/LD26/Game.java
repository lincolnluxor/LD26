package com.lincolnluxor.LD26;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Game extends JFrame {

	/**
	 * @param args
	 */
	@SuppressWarnings("static-access")
	public Game() {
		add(new Board());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(807,600);
		setLocationRelativeTo(null);
		setTitle("LD26 - Tate's entry");
		setResizable(false);
		setVisible(true);
		
		SoundEffects.init();
		SoundEffects.volume = SoundEffects.volume.LOW;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Game();
	}

}
