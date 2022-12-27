import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;


public class Panel extends JPanel implements ActionListener {

	static final int screenWidth = 650;
	static final int screenHeight = 600;
	static final int unitSize = 25;
	static final int gameUnits = (screenWidth * screenHeight) / unitSize;
	static final int delay = 70;
	final int[] x = new int[gameUnits];
	final int[] y = new int[gameUnits];
	int bodyParts = 6;
	int applesEaten = 0;
	int appleX;
	int appleY;
	char direction = 'R';
	boolean running = false;
	Timer timer;
	Random random;
	
	
	Panel() {
		random = new Random();
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(new Color(241, 250, 238));
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		startGame();
	}
	
	
	public void startGame() {
		running = true;
		drawApple();
		timer = new Timer(delay, this);
		timer.start();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	
	public void draw(Graphics g) { 
		
		if (running) {
			g.setColor(new Color(230, 57, 70));
			g.fillOval(appleX, appleY, unitSize, unitSize);
			
			for (int i = 0; i < bodyParts; i++) {
				if (i == 0) {
					g.setColor(new Color(69, 123, 157));
					g.fillRect(x[i], y[i], unitSize, unitSize);
				} else {
					g.setColor(new Color(168, 218, 220));
					g.fillRect(x[i], y[i], unitSize, unitSize);				
				}
			}
			
			g.setColor(Color.red);
			g.setFont(new Font("Ink Free", Font.BOLD, 20));
			FontMetrics metrics = getFontMetrics(g.getFont());
			g.drawString("Score: " + applesEaten, (screenWidth - metrics.stringWidth("Score: " + applesEaten)) / 2, g.getFont().getSize());
		} else {
			gameOver(g);
		}
		
	}
	
	public void drawApple() {
		appleX = random.nextInt((int)(screenWidth / unitSize)) * unitSize;
		appleY = random.nextInt((int)(screenHeight / unitSize)) * unitSize;
	}
	
	public void move() {
		for (int i = bodyParts; i > 0; i--) {
			x[i] = x[i - 1];
			y[i] = y[i - 1];
		}
		
		switch (direction) {
			case 'U': {
				y[0] = y[0] - unitSize;
				break;
			} 
			case 'D': {
				y[0] = y[0] + unitSize;
				break;
			}
			case 'L': {
				x[0] = x[0] - unitSize;
				break;
			} 
			case 'R': {
				x[0] = x[0] + unitSize;
				break;
			}
		}
	}
	
	public void checkApple () {
		if ((x[0] == appleX) && (y[0] == appleY)) {
			bodyParts++;
			applesEaten++;
			drawApple();
		}
	}
	
	public void checkColition() {
		// Head collides with body;
		for (int i = bodyParts; i > 0; i--) {
			if((x[0] == x[i]) && (y[0] == y[i])) {
				running = false;
			}
		}
		
		// Head collides with border;
		if (x[0] < 0) {
			running = false;
		}
		else if (x[0] > screenWidth) {
			running = false;
		}
		else if (y[0] < 0) {
			running = false;
		}
		else if (y[0] > screenHeight) {
			running = false;
		} 
		else if (!running) {
			timer.stop(); 
		}
	}
	
	public void gameOver(Graphics g) {
		// Score at game end.
		g.setColor(new Color(230, 57, 70));
		g.setFont(new Font("Ink Free", Font.BOLD, 20));
		FontMetrics metricsScore = getFontMetrics(g.getFont());
		g.drawString("Score: " + applesEaten, (screenWidth - metricsScore.stringWidth("Score: " + applesEaten)) / 2, g.getFont().getSize());
		
		// Game over text;
		g.setColor(new Color(230, 57, 70));
		g.setFont(new Font("Ink Free", Font.BOLD, 50));
		FontMetrics metrics = getFontMetrics(g.getFont());
		g.drawString("Game Over", (screenWidth - metrics.stringWidth("Game Over")) / 2, screenHeight / 2 );		
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(running) {
			move();
			checkApple();
			checkColition();
		}
		repaint(); 
	}

	public class MyKeyAdapter extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			switch (e.getKeyCode()) {
				case KeyEvent.VK_LEFT: {
					if (direction != 'R') {
						direction = 'L';
					}
					break;
				}
				case KeyEvent.VK_RIGHT: {
					if (direction != 'L') {
						direction = 'R';
					}
					break;
				}
				case KeyEvent.VK_UP: {
					if (direction != 'D') {
						direction = 'U';
					}
					break;
				}
				case KeyEvent.VK_DOWN: {
					if (direction != 'U') {
						direction = 'D';
					}
					break;
				}
			}
		}
	}
}