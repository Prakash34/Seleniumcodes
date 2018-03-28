package Stanford;

/* The Bouncing Ball Applet
*
* Copyright (c) 1999-2002, Xiaoping Jia.  
* All Rights Reserved. 
* This is a doc comment.
*
*/

/**
* @see http://java.sun.com/j2se/1.4.2/docs/api/java/awt/package-summary.html
*/
import java.awt.*;


public class BouncingBall
   extends java.applet.Applet implements Runnable {
/**
* The ball color
* @see #update
*/
 protected Color color = Color.red;
/**
* The ball radius
* @see #init
* @see #update
*/
 protected int rhombus = 10;
/**
* The ball coordinates  of current position
* @see #init
* @see #update
*/
 protected int x, y;
/**
* The distance that ball passed during the move
* @see #update
*/
 protected int dx = -2, dy = -4;
/**
* Off-screen image
* @see #update
*/
 protected Image image;
/**
* Off-screen graphics
* @see #update
*/
 protected Graphics offscreen;
/**
* Size of the viewing area
* @see #init
* @see #update
*/
 protected Dimension d;
 
/**
* The initialization of the object
*/
 public void init() {
/**
* param delay Interval between two consecutive frames
*/
   String att = getParameter("delay");
   if (att != null) {
     delay = Integer.parseInt(att);
   }
   d = getSize();
   x = d.width * 2 / 3;
   y = d.height - rhombus;
 }

/**
* Representation of the ball movement
*/
 public void update(Graphics g) {
   // create the off-screen image buffer
   // if it is invoked the first time
   if (image == null) {
     image = createImage(d.width, d.height);
     offscreen = image.getGraphics();
   }

   // draw the background
   offscreen.setColor(Color.white);
   offscreen.fillRect(0,0,d.width,d.height);

   // adjust the position of the ball
   // reverse the direction if it touches
   // any of the four sides
   if (x < rhombus || x > d.width - rhombus) {
     dx  =  -dx;
   }
   if (y < rhombus || y > d.height - rhombus) {
     dy  =  -dy;
   }
   x += dx;
   y += dy;

   // draw the ball
   offscreen.setColor(color);
   offscreen.fillOval(x - rhombus, y - rhombus,
                     rhombus * 2, rhombus * 2);

   // copy the off-screen image to the screen
   g.drawImage(image, 0, 0, this);
 }

/**
* Paints the box and the ball
* @param g Graphics object to draw
* @see Graphics reference
*/
 public void paint(Graphics g) {
   update(g);
 }

 // The animation applet idiom
/**
* The thread that keeps the ball bouncing
* @see #start
* @see #stop
* @see #run
*/
 protected Thread bouncingThread;
/**
* If no parameter passed then we set 
* interval between two consecutive frames
*/
 protected int delay = 100;

/**
* Activates the applet
*/
 public void start() {
   bouncingThread = new Thread(this);
   bouncingThread.start();
 }

/**
* Deactivates the applet
*/
 public void stop() {
   bouncingThread = null;
 }

/**
* Contains inifinite loop and periodically calls repaint method
*/
 public void run() {
   while (Thread.currentThread() == bouncingThread) {
     try {
       Thread.currentThread().sleep(delay);
     } catch (InterruptedException  e) {}
     repaint();
   }
 }

}
	
