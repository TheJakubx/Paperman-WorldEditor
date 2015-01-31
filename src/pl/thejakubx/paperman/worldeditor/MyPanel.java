package pl.thejakubx.paperman.worldeditor;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

class MyPanel extends JPanel
{
  
	static Obiekty Obj[] = new Obiekty[1024];
	boolean frist = true;
	public void paintComponent(Graphics g)
     {
		if(frist){
			
			Obj[0] = new Obiekty(100,100,"Wall");
			Obj[1] = new Obiekty(100,100,"Wall");
			
			frist = false;
		}
		
		
		
		
		
		
		
		
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(Okno.bg, 0, 0, this);
		for(int i = 0;i<1024;i++){
			if(Obj[i] != null){
				g2d.drawImage(Okno.getImage(Obj[i].type), Obj[i].x, Obj[i].y, this);
			}
		}
     }
}