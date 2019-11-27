package maze;

import java.awt.*;
import javax.swing.*;
public class Grid extends JFrame {
	 /**
	 * 
	 */
	private static final long serialVersionUID = 2883516016805876884L;
	private int marging=30;
	 
 public Grid()    {       
 setSize( 500, 500 );
 setVisible( true );   
 } 
public void paint( Graphics g)    
 {  

 for ( int x =marging ; x < 30*10+marging; x += 30 )
 for ( int y = marging; y < 30*10+marging; y += 30 ) 
	 
 g.drawRect( x, y, 30, 30 );

 } 
 public static void main( String args[] ) 
 {
     Grid application = new Grid();
 application.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );   }  } 
