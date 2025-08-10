package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;

public class GamingPanel extends JPanel implements Runnable{
	
	//Gaming screen settings
	final int originalTileSize = 16; //16x16 tile
	final int scale = 3;
	
	public final int tileSize = originalTileSize * scale; //48x48 tile
	final int maxScreenCol = 16;
	final int maxScreenRow = 12;
	final int screenWidth = tileSize * maxScreenCol;	//768 pixels
	final int screenHeight = tileSize * maxScreenRow;	//576 pixels
	
	//FPS
	int FPS = 60;
	
	KeyHandler keyH = new KeyHandler(); //Used for controls
	Thread gamingThread;
	Player player = new Player(this,keyH);
	
	//setting character default position
	int xPosition = 100;
	int yPosition = 100;
	int characterSpeed = 4;
	
	public GamingPanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);		//Better Rendering Performance
		this.addKeyListener(keyH);
		this.setFocusable(true);
	}
	
	public void startGameThread()
	{
		gamingThread = new Thread(this);
		gamingThread.start();
	}
	
	@Override
	public void run(){		//Called when GamingThread is started
		
		//Draw time interval
		double drawInterval = 1000000000/FPS;
		double nextDrawTime = System.nanoTime() + drawInterval;
		
		while(gamingThread != null)
		{			
			//Update character position
			update();
			//Draw updated character
			repaint();
			
			
			try {
				 double remainingTime = nextDrawTime - System.nanoTime();
				 remainingTime = remainingTime/1000000;		//converting to milliseconds
				 
				 if(remainingTime < 0)
				 {
					 remainingTime = 0;		//error checker.
				 }
				 
				 Thread.sleep((long)remainingTime);
				 
				 nextDrawTime += drawInterval;
				 
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
//	@Override
//	public void run() {
//		
//		double drawInterval = 1000000000/FPS;
//		double delta = 0;
//		long lastTime = System.nanoTime();
//		long currentTime;
//		
//		while(gamingThread != null)
//		{
//			currentTime = System.nanoTime();
//			
//			delta += (currentTime - lastTime)/drawInterval;
//			
//			lastTime = currentTime;
//			
//			if(delta >= 1)
//			{
//				update();
//				repaint();
//				delta--;
//			}
//			
//		}
//	}
	
	public void update() 
	{
		player.update();
	}
	
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);	//Inherits from JPanel
		
		Graphics2D g2 = (Graphics2D)g;
		
		player.draw(g2);
		
		g2.dispose();
	}
}
