package pl.thejakubx.paperman.worldeditor;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Okno extends JFrame implements MouseListener, MouseMotionListener, ActionListener, Runnable, KeyListener {
	JButton BXD, BExit;
	JLabel Px, Py, Pt, Pid1,Pid2;
	JTextField Tx, Ty, TCopy;
	JComboBox CObiectId, CDir;
	JMenuBar MBar;
	JMenu MPlik, MObiekt,MMapa,MOpcje;
	JMenuItem mOpen, mZapisz, mDodaj,mCzysc,mCofnij;
	Thread okno;
	
    @SuppressWarnings("unchecked")
	public Okno() throws IOException{
        addMouseListener(this);
       // addMouseMotionListener(this);
    	setSize(960+150,640+30+20);
    	setTitle("Paperman WorldEditor");
    	setLayout(null);
    	setResizable(false);
    	setLocationRelativeTo(null);
    	


    	JPanel panel = new MyPanel();
    	panel.setBounds(0,0,960,680);
    	add(panel);
    	
    	
    	
    	MBar = new JMenuBar();
    	MPlik = new JMenu("Plik");
    	MObiekt = new JMenu("Obiekt");
    	MMapa = new JMenu("Mapa");
    	MOpcje = new JMenu("Opcje");
    	setJMenuBar(MBar);
    	MBar.add(MPlik);
    	MBar.add(MObiekt);
    	MBar.add(MMapa);
    	
    	mOpen = new JMenuItem("Otwórz");
    	mZapisz = new JMenuItem("Zapisz");
    	mDodaj = new JMenuItem("Dodaj");
    	
    	mCofnij = new JMenuItem("Cofnij");
    	
    	mCzysc = new JMenuItem("Czysc");
    	
    	MPlik.add(mOpen);
    	MPlik.add(mZapisz);
    	
    	MObiekt.add(mDodaj);
    	
    	MMapa.add(mCzysc);
    	MOpcje.add(mCofnij);
    	mCzysc.addActionListener(this);
    	mDodaj.addActionListener(this);
    	mOpen.addActionListener(this);
    	mZapisz.addActionListener(this);
    	
    	Pid1 = new JLabel("ID:");
    	Pid1.setBounds(958,10,100,10);
    	add(Pid1);
    	
    	Pid2 = new JLabel("176");
    	Pid2.setBounds(958+15,10,100,10);
    	add(Pid2);
    	
    	Px = new JLabel("X: ");
    	Px.setBounds(958,35,100,10);
    	add(Px);
    	
    	Py = new JLabel("Y: ");
    	Py.setBounds(958+55,35,100,10);
    	add(Py);
    	
    	
    	Tx = new JTextField("x");
    	Tx.setBounds(960+10,30,40,20);
    	add(Tx);
    	Tx.addActionListener(this);
    	
    	Ty = new JTextField("y");
    	Ty.setBounds(960+10+55,30,40,20);
    	add(Ty);
    	Ty.addActionListener(this);
    	
    	
    	Pt = new JLabel("Type:");
    	Pt.setBounds(960,60,40,20);
    	add(Pt);
    	
    	CObiectId =new JComboBox();
    	CObiectId.setBounds(960+30, 60, 150, 20);
    	CObiectId.addItem("---");
    	CObiectId.addItem("Wall");
    	CObiectId.addItem("R_Wall");
    	CObiectId.addItem("L_Wall");
    	CObiectId.addItem("Roof");
    	CObiectId.addItem("Floor");
    	CObiectId.addItem("Start");
    	CObiectId.addItem("End");
    	CObiectId.addItem("Saw");
    	CObiectId.addItem("Point");
    	
    	CObiectId.addItem("Down Spikes");
    	CObiectId.addItem("Up Spikes");
    	CObiectId.addItem("Left Spikes");
    	CObiectId.addItem("Rigth Spikes");
    	
    	
    	CObiectId.addItem("Mag Min Down");
    	CObiectId.addItem("Mag Min Up");
    	CObiectId.addItem(" ");
    	CObiectId.addItem(" ");
    	
    	CObiectId.addItem("Mag Plus Down");
    	CObiectId.addItem("Mag Plus Up");
    	CObiectId.addItem(" ");
    	CObiectId.addItem(" ");
    	
    	CObiectId.addActionListener(this);
    	add(CObiectId);
    	
    	
    	
    	
    	
    	
    	CDir =new JComboBox();
    	CDir.setBounds(960+60, 200, 80, 20);
    	CDir.addItem("Up");
    	CDir.addItem("Rigth");
    	CDir.addItem("Down");
    	CDir.addItem("Left");
    	
    	CDir.addActionListener(this);
    	add(CDir);
    	
    	
    	
    	TCopy = new JTextField("");
    	TCopy.setBounds(960+10,200,40,20);
    	add(TCopy);
    	TCopy.addActionListener(this);
    	
		File imageFile = new File("floor.png");
		floor = ImageIO.read(imageFile);
		imageFile = new File("start.png");
		start = ImageIO.read(imageFile);
		imageFile = new File("end.png");
		end = ImageIO.read(imageFile);
		imageFile = new File("roof.png");
		roof = ImageIO.read(imageFile);
		imageFile = new File("wall_l.png");
		wall_l = ImageIO.read(imageFile);
		imageFile = new File("wall_r.png");
		wall_r = ImageIO.read(imageFile);
		imageFile = new File("wall.png");
		wall = ImageIO.read(imageFile);
		imageFile = new File("saw.png");
		saw = ImageIO.read(imageFile);
		imageFile = new File("point.png");
		point = ImageIO.read(imageFile);
		
		imageFile = new File("d_spikes.png");
		d_spikes = ImageIO.read(imageFile);
		imageFile = new File("u_spikes.png");
		u_spikes = ImageIO.read(imageFile);
		imageFile = new File("l_spikes.png");
		l_spikes = ImageIO.read(imageFile);
		imageFile = new File("p_spikes.png");
		p_spikes = ImageIO.read(imageFile);
		
		imageFile = new File("gb.png");
		bg = ImageIO.read(imageFile);
		
		
		
		imageFile = new File("mag_m_d.png");
		MgMd = ImageIO.read(imageFile);
		imageFile = new File("mag_m_u.png");
		MgMu = ImageIO.read(imageFile);
		imageFile = new File("mag_m_l.png");
		MgMl = ImageIO.read(imageFile);
		imageFile = new File("mag_m_r.png");
		MgMr = ImageIO.read(imageFile);
		
		imageFile = new File("mag_p_d.png");
		MgPd = ImageIO.read(imageFile);
		imageFile = new File("mag_p_u.png");
		MgPu = ImageIO.read(imageFile);
		imageFile = new File("mag_p_l.png");
		MgPl = ImageIO.read(imageFile);
		imageFile = new File("mag_p_r.png");
		MgPr = ImageIO.read(imageFile);
		
		
		
		
		
		okno = new Thread(this);	
		okno.start();

    }
    public static BufferedImage getImage(String Image){
    	if(Image.equalsIgnoreCase("Wall"))return wall;
    	if(Image.equalsIgnoreCase("R_Wall"))return wall_r;
    	if(Image.equalsIgnoreCase("L_Wall"))return wall_l;
    	if(Image.equalsIgnoreCase("Roof"))return roof;
    	if(Image.equalsIgnoreCase("Floor"))return floor;
    	if(Image.equalsIgnoreCase("Start"))return start;
    	if(Image.equalsIgnoreCase("End"))return end;
    	if(Image.equalsIgnoreCase("Saw"))return saw;
    	if(Image.equalsIgnoreCase("Point"))return point;
    	if(Image.equalsIgnoreCase("Down spikes"))return d_spikes;
    	if(Image.equalsIgnoreCase("Up spikes"))return u_spikes;
    	if(Image.equalsIgnoreCase("Left spikes"))return l_spikes;
    	if(Image.equalsIgnoreCase("Rigth spikes"))return p_spikes;
    	
    	if(Image.equalsIgnoreCase("Mag Min Down"))return MgMd;
    	if(Image.equalsIgnoreCase("Mag Min Up"))return MgMu;
    	if(Image.equalsIgnoreCase("Mag Min Left"))return MgMl;
    	if(Image.equalsIgnoreCase("Mag Min Rigth"))return MgMr;
    	
    	if(Image.equalsIgnoreCase("Mag Plus Down"))return MgPd;
    	if(Image.equalsIgnoreCase("Mag Plus Up"))return MgPu;
    	if(Image.equalsIgnoreCase("Mag Plus Left"))return MgPl;
    	if(Image.equalsIgnoreCase("Mag Plus Rigth"))return MgPr;
    	
    	
		return null;
    }
	
    public static int getId(String Image){
    	if(Image.equalsIgnoreCase("Wall"))return 1;
    	if(Image.equalsIgnoreCase("R_Wall"))return 2;
    	if(Image.equalsIgnoreCase("L_Wall"))return 3;
    	if(Image.equalsIgnoreCase("Roof"))return 4;
    	if(Image.equalsIgnoreCase("Floor"))return 5;
    	if(Image.equalsIgnoreCase("Start"))return 6;
    	if(Image.equalsIgnoreCase("End"))return 7;
    	if(Image.equalsIgnoreCase("Saw"))return 8;
    	if(Image.equalsIgnoreCase("Point"))return 9;
    	if(Image.equalsIgnoreCase("Down spikes"))return 10;
    	if(Image.equalsIgnoreCase("Up spikes"))return 11;
    	if(Image.equalsIgnoreCase("Left spikes"))return 12;
    	if(Image.equalsIgnoreCase("Rigth spikes"))return 13;
    	
    	if(Image.equalsIgnoreCase("Mag Min Down"))return 14;
    	if(Image.equalsIgnoreCase("Mag Min Up"))return 15;
    	if(Image.equalsIgnoreCase("Mag Min Left"))return 16;
    	if(Image.equalsIgnoreCase("Mag Min Rigth"))return 17;
    	
    	if(Image.equalsIgnoreCase("Mag Plus Down"))return 18;
    	if(Image.equalsIgnoreCase("Mag Plus Up"))return 19;
    	if(Image.equalsIgnoreCase("Mag Plus Left"))return 20;
    	if(Image.equalsIgnoreCase("Mag Plus Rigth"))return 21;
    	
		return 0;
    }
    public static String getName(int Image){
    	if(Image==1)return "Wall";
    	if(Image==2)return "R_Wall";
    	if(Image==3)return "L_Wall";
    	if(Image==4)return "Roof";
    	if(Image==5)return "Floor";
    	if(Image==6)return "Start";
    	if(Image==7)return "End";
    	if(Image==8)return "Saw";
    	if(Image==9)return "Point";
    	if(Image==10)return "Down Spikes";
    	if(Image==11)return "Up spikes";
    	if(Image==12)return "Left spikes";
    	if(Image==13)return "Rigth spikes";
    	
    	if(Image==14)return "Mag Min Down";
    	if(Image==15)return "Mag Min Up";
    	if(Image==16)return "Mag Min Left";
    	if(Image==17)return "Mag Min Rigth";
    	
    	if(Image==18)return "Mag Plus Down";
    	if(Image==19)return "Mag Plus Up";
    	if(Image==20)return "Mag Plus Left";
    	if(Image==21)return "Mag Plus Rigth";
    	
		return "";
    }
    
    public static int MOVE = 0;
    public static BufferedImage bg;
    
    public static BufferedImage floor;
    public static BufferedImage roof;
    public static BufferedImage wall;
    public static BufferedImage wall_r;
    public static BufferedImage wall_l;
    public static BufferedImage start;
    public static BufferedImage end;
    public static BufferedImage saw;
    public static BufferedImage point;
    public static BufferedImage d_spikes;
    public static BufferedImage u_spikes;
    public static BufferedImage l_spikes;
    public static BufferedImage p_spikes;
    public static BufferedImage MgMd;
    public static BufferedImage MgMu;
    public static BufferedImage MgMl;
    public static BufferedImage MgMr;
	
    public static BufferedImage MgPd;
    public static BufferedImage MgPu;
	public static BufferedImage MgPl;
	public static BufferedImage MgPr;
    
	public static boolean MousePress = false;
	@Override
	public void mouseClicked(MouseEvent e) {
		XXX = X - e.getX();
		YYY = Y - e.getY();
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		
		XXX = X - e.getX();
		YYY = Y - e.getY();
	}
	@Override
	public void mouseExited(MouseEvent e) {
		XXX = X - e.getX();
		YYY = Y - e.getY();
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		XXX = X - e.getX();
		YYY = Y - e.getY();
	}
	@Override
	public void mousePressed(MouseEvent e) {
		XXX = X - e.getX();
		YYY = Y - e.getY();
		MousePress = true;
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		XXX = X - e.getX();
		YYY = Y - e.getY();
		MousePress = false;
		Active = -1;
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		
		XXX = X - e.getX();
		YYY = Y - e.getY();
	}
	
	int Active = -1;
	int Select = -1;
	int tempX = 0;
	int tempY = 0;
	boolean change = false;
	public void run()
	{
		while (true)
		{

			if(Select >=0 && !change){
				Tx.setText(MyPanel.Obj[Select].x*2+"");
				Ty.setText(MyPanel.Obj[Select].y*2+"");
				Pid2.setText(Select+"");
				CObiectId.setSelectedIndex(getId(MyPanel.Obj[Select].type));
				change = true;
				
			}
			if(Select ==-1){
				Tx.setText("");
				Ty.setText("");
				Pid2.setText("Null");
				CObiectId.setSelectedIndex(0);
				
			}
			PointerInfo a = MouseInfo.getPointerInfo();
			Point b = a.getLocation();
			X = (int) (b.getX() -getLocationOnScreen().getX());
			Y = (int) (b.getY() - getLocationOnScreen().getY());
			if(MousePress && Active == -1){
				for(int i = 0;i<1024;i++){
					if(MyPanel.Obj[i] != null){
						int AX = 20;
						int AY = 20;
						if(MyPanel.Obj[i].type.equalsIgnoreCase("start")){
							AX = 50;
							AY = 40;
						}
						if(MyPanel.Obj[i].type.equalsIgnoreCase("finish")){
							AX = 50;
							AY = 40;
						}
						if(MyPanel.Obj[i].type.equalsIgnoreCase("saw")){
							AX = 75;
							AY = 75;
						}
						if(MyPanel.Obj[i].type.equalsIgnoreCase("Down spikes")){
							AX = 38;
							AY = 15;
						}
						if(MyPanel.Obj[i].type.equalsIgnoreCase("Up spikes")){
							AX = 38;
							AY = 15;
						}
						if(MyPanel.Obj[i].type.equalsIgnoreCase("Rigth spikes")){
							AX = 15;
							AY = 38;
						}
						if(MyPanel.Obj[i].type.equalsIgnoreCase("Left spikes")){
							AX = 15;
							AY = 38;
						}
						
						if(MyPanel.Obj[i].type.equalsIgnoreCase("Mag Plus Down")){
							AX = 50;
							AY = 50;
						}
						if(MyPanel.Obj[i].type.equalsIgnoreCase("Mag Plus Up")){
							AX = 50;
							AY = 50;
						}
						if(MyPanel.Obj[i].type.equalsIgnoreCase("Mag Min Down")){
							AX = 50;
							AY = 50;
						}
						if(MyPanel.Obj[i].type.equalsIgnoreCase("Mag Min Up")){
							AX = 50;
							AY = 50;
						}
						if(X > MyPanel.Obj[i].x && X < MyPanel.Obj[i].x+AX&&Y-50 > MyPanel.Obj[i].y && Y-50 < MyPanel.Obj[i].y+AY){
										Active = i;
										Select = i;
										tempX = X - MyPanel.Obj[i].x;
										tempY = Y - MyPanel.Obj[i].y;
										change = false;
										break;
						}
						else{
						Select = -1;
						Active = -1;
						}
						
					}
				}
			}
			if(Active >= 0 && MousePress){
				MyPanel.Obj[Active].y = Y-tempY;
				MyPanel.Obj[Active].x = X-tempX;
				change = false;
				
			}
			
			
		   repaint();
	/*	   try
		   {
				Thread.sleep(10);
		   }
		   catch (InterruptedException e)
		   {
				System.out.println(e);
			}*/
		}
	}
    public static int X = 0;
    public static int Y = 0;
    public static int XXX;
    public static int YYY;
    public static void main(String[] args) throws IOException{
    	Okno okno = new Okno();
    	int XXX = okno.getX();
    	int YYY = okno.getX();
    	
    	okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	okno.setVisible(true);
    }
    
    

    
	@Override
	public void actionPerformed(ActionEvent e) {
		Object zrodlo  = e.getSource();
		if(zrodlo == mCofnij){
			if(MOVE>1)MOVE--;
		}
		if(zrodlo == mCzysc){
			for(int i = 0;i<1024;i++)MyPanel.Obj[i] = null;
		}
		if(zrodlo == mOpen){
			JFileChooser fc = new JFileChooser();
			 FileNameExtensionFilter filter = new FileNameExtensionFilter(
				        "Paperman maps", "pmap");
				    fc.setFileFilter(filter);
			if(fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
				File plik = fc.getSelectedFile();
				try {
					Scanner in = new Scanner(plik);
					String a = "";
					int Ob = 0;
					for(int i = 0;i<1024;i++)MyPanel.Obj[i] = null;
					while(in.hasNext()){
						 a = in.nextLine();
						 int i = 0;
						 String temp = "";
						 for(;i<a.length();i++){
							 if(a.toCharArray()[i] == ',')
								 break;
							 temp = temp+a.toCharArray()[i];
						 }
						 int x = Integer.parseInt(temp);
						 temp = "";
						 i++;
						 for(;i<a.length();i++){
							 if(a.toCharArray()[i] == ',')
								 break;
							 temp = temp+a.toCharArray()[i];
						 }
						 int y = Integer.parseInt(temp);
						 temp = "";
						 i++;
						 for(;i<a.length();i++){
							 if(a.toCharArray()[i] == ',')
								 break;
							 temp = temp+a.toCharArray()[i];
						 }
						 int id = Integer.parseInt(temp);
						 MyPanel.Obj[Ob] = new Obiekty(x,y, getName(id));
						 Ob++;
					}
					in.close();
					
					
					
					
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
				
			}
		}
		if(zrodlo == mZapisz){
			JFileChooser fc = new JFileChooser();
			 FileNameExtensionFilter filter = new FileNameExtensionFilter(
				        "Paperman maps", "pmap");
				    fc.setFileFilter(filter);
			if(fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION){
				File plik = fc.getSelectedFile();
				try {
					PrintWriter pw = new PrintWriter(plik+".pmap");
					for(int i = 0;i<1024;i++){
						if(MyPanel.Obj[i] != null){
							pw.println(MyPanel.Obj[i].x+","+MyPanel.Obj[i].y+","+getId(MyPanel.Obj[i].type));
						}
					}
					pw.close();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
				
			}
		}
		
		
		
		
		if(zrodlo == Ty){
			if(Select >= 0)MyPanel.Obj[Select].y = Integer.parseInt(Ty.getText())/2;
		}
		if(zrodlo == Tx){
			if(Select >= 0)MyPanel.Obj[Select].x = Integer.parseInt(Tx.getText())/2;
		}
		if(zrodlo == CObiectId){
			if(Select >= 0)MyPanel.Obj[Select].type = CObiectId.getSelectedItem().toString();
		}
		if(zrodlo == mDodaj){
			for(int i = 0;i<1024;i++){
				if(MyPanel.Obj[i] == null){
					MyPanel.Obj[i] = new Obiekty(500,500,"Wall");
					break;
				}
			}
		}
		if(zrodlo == TCopy){
			
			int a = 0;
			for(int i = 0;i<1024;i++){
				if(MyPanel.Obj[i] == null){
					a++;
					if(a<=Integer.parseInt(TCopy.getText())){
						int z = 20;
						if(MyPanel.Obj[Select].type.equalsIgnoreCase("d_spikes"))z = 75/2;
						if(MyPanel.Obj[Select].type.equalsIgnoreCase("u_spikes"))z = 75/2;
						
						if(CDir.getSelectedIndex() == 0)MyPanel.Obj[i] = new Obiekty(MyPanel.Obj[Select].x,MyPanel.Obj[Select].y-z*a,MyPanel.Obj[Select].type);
						if(CDir.getSelectedIndex() == 1)MyPanel.Obj[i] = new Obiekty(MyPanel.Obj[Select].x+z*a,MyPanel.Obj[Select].y,MyPanel.Obj[Select].type);
						if(CDir.getSelectedIndex() == 2)MyPanel.Obj[i] = new Obiekty(MyPanel.Obj[Select].x,MyPanel.Obj[Select].y+z*a,MyPanel.Obj[Select].type);
						if(CDir.getSelectedIndex() == 3)MyPanel.Obj[i] = new Obiekty(MyPanel.Obj[Select].x-z*a,MyPanel.Obj[Select].y,MyPanel.Obj[Select].type);
						
					}
					else
						break;
				}
			}
		}
	/*	if(zrodlo == BXD){
			LWypisz.setText(LWypisz.getText()+"XD");
		}
		if(zrodlo == BExit){
			dispose();
		}
		if(zrodlo == Ttekst){
			LWypisz.setText(Ttekst.getText());
		}*/
		
	}
    @Override
	public void keyPressed(KeyEvent evt) {
    	if(evt.getKeyCode() == 46){
    		if(Select >= 0){
    			MyPanel.Obj[Select] = null;
    		}
    		Select = -1;
    		Active = -1;
    	}
    	System.out.println(evt.getKeyCode());
	}
	@Override
	public void keyReleased(KeyEvent evt) {
    	if(evt.getKeyCode() == 46){
    		if(Select >= 0){
    			MyPanel.Obj[Select] = null;
    		}
    		Select = -1;
    		Active = -1;
    	}
		
    	System.out.println(evt.getKeyCode());
	}
	@Override
	public void keyTyped(KeyEvent evt) {
    	if(evt.getKeyCode() == 46){
    		if(Select >= 0){
    			MyPanel.Obj[Select] = null;
    		}
    		Select = -1;
    		Active = -1;
    	}
    	System.out.println(evt.getKeyCode());
		
	}
    
    
 
}