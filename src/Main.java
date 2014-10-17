/* Dani Vega's DRAWING NOTEBOOK - Proyecto Unidad 2 */

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.net.*;
import java.util.Vector;

public class Main extends JPanel implements ActionListener
{
	private static final long serialVersionUID = 1L;
	JFrame ventana;
	Container cont;
	JButton BFig, BEscO, BEscP, BDef, BRot, BTras, BRef;
	Proyecto_Transformaciones F;
	JToolBar barra;
	JLabel cordx,cordy,properties;
	Toolkit TK = Toolkit.getDefaultToolkit();
	BufferedImage buf;
	JPopupMenu pop;
    JMenuItem restaurar,salir;
    Color color;
	JColorChooser colorc;
	AffineTransform trans = new AffineTransform();
	URL resturl,salirurl,nuevourl,ucursor,ucursorpencil,diburl,escurl,defurl,rotcurl,rotsurl,trasurl,refurl,salurl,diburlg,escurlmg,escurlmng,defurlg,rotcurlg,rotsurlg,trasurlg,refurlg,salurlg,colorurl,ayurl;
	Cursor cursor;
	Rectangle2D limite,contorno;
	Shape figx;
	GeneralPath ruta;
	
	double fig[][]={{520.33,446.67},{537.67,428.33},{547.67,411.67},{551.67,396.33},{547.67,377.67},{537.67,361},{523.67,350},{507.67,345},{489.67,345},{453.67,355.5},{441.67,361},{428.33,377.67},{418.33,396.33},{413.67,411.67},{413.67,431},{418.33,446.33},{428.33,462.33},{439,472.33},{453.67,481},{467,487},
							  {483.97,490.33},{493.67,490.33},{507.67,487},{523.67,481},{537.67,472.33},{549.67,462.33},{567,446.33},{577.67,431},{589,411.67},{594.33,396.33},{598.33,377.67},{596.33,361},{594.33,347.5},{589,333},{577.67,317.67},{567,304.33},{551.67,293},{533.67,283},{520.33,277},{500.67,271.67},
							  {480,265},{462,261.67},{418.33,261.67},{402.33,256.33},{389,249},{375.67,241},{364.33,230.33},{357,218.33},{357,207.6},{360.67,202.33},{370,199},{382.33,199},{393,205},{410.33,218.33},{418.33,230.33},{426,245},{433.67,261.67},{493.67,467.33},{507.67,511.67},{512.33,531.67},
							  {520.33,547.67},{533.67,561.67},{549.67,570.17},{577.67,570.17},{595.33,561.67},{610.33,551},{623,535},{632.33,516.33},{632.33,500.33},{627.67,487},{616.67,476.67},{602.83,472.33},{589,474.5},{577.67,481},{567,493.67},{562.33,508.33},{564.67,521.67},{575,531.67},{586.5,535},{597.33,533.33},
							  {602.83,531.67},{600.08,539.67},{589.73,546.94},{573.32,554.69},{563.35,554.81},{551.67,551},{533.67,534.17},{529,521.67},{512.33,467.33},{453.67,259},{441.67,218.33},{429.83,202.33},{416,187},{399,175},{382.33,169.67},{370,166.59},{349.67,169.67},{339.67,178.33},{335.67,187},{335.67,211.87},
							  {339.67,227.33},{344.67,241},{357,259},{370,274.33},{387.67,288},{407.5,298.67},{428.33,307},{441.67,311},{470.33,314.33},{490.33,314.33},{507.67,317.67},{542.67,329.67},{558.33,345},{567,354.25},{575,369.33},{577.67,383},{576.33,396.33},{572.33,411.67},{564.67,424.33},{554.33,437.33},
							  {523.67,462.33},{512.33,467.33},{493.67,472.33},{480.33,469.83},{470.33,464.83},{460.33,458.33},{453.67,450.33},{447.67,438.67},{450.67,420},{454.83,411.67},{471.01,389.67},{489.67,383},{510,383},{527,396.33},{529,408.33},{529,415.83},{528,424.33},{520.33,446.67}};
	
	boolean mover,scalmas=false,scalmenos;
	int opc, posx, posy;
	double minx,ancho=324,alto=551;
	double maxx;
	double maxy;
	double miny;
	String nombrefig = "Nota Musical",status1="Dimensiones: Original",status2="Orientación: Original",colors="Color: Original",reflejado="Reflejado: No";
	static Vector<double[][]> figura = new Vector<double[][]>();
	
	public Main(String tit,Vector<double[][]> figure)
	{
		    JFrame.setDefaultLookAndFeelDecorated(true);
		    JDialog.setDefaultLookAndFeelDecorated(true);
		    
		    try {
		    	UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		    	} catch (Exception e){}
		    
		    	// Construcción de la ventana
				ventana = new JFrame(tit);
				cont = ventana.getContentPane();
				cont.setLayout(new BorderLayout());
				cont.setBackground(new Color(255,243,243));
				ventana.setSize(1000, 700);
				ventana.setBounds(0, 35, 1366, 730);
				buf = loadImage("/Resources/fondo.jpg");
			     
				// Convertir a Shape:
				ruta = new GeneralPath();
				ruta.moveTo(fig[0][0], fig[0][0]);
				for (int i = 0; i < fig.length; i++) 
				{
					ruta.lineTo(fig[i][0], fig[i][1]);
				}
				figx = ruta;
				
				// Menu PopUp
				this.addMouseListener(new MouseAdapter() {
				
					public void mousePressed(MouseEvent arg) 
					{
						resturl = getClass().getResource("/Resources/pencil.png");
						salurl = getClass().getResource("/Resources/salir.png");
						pop = new JPopupMenu();
						restaurar = new JMenuItem("Restaurar");
							restaurar.setIcon(new ImageIcon(resturl));
						salir = new JMenuItem("Salir");
							salir.setIcon(new ImageIcon(salurl));
						pop.add(restaurar);pop.add(salir);
						
						if(arg.getButton()==MouseEvent.BUTTON3)
						{
							pop.show(ventana, arg.getX()+50, arg.getY()+50);
							restaurar.addActionListener(new ActionListener() 
							{
								public void actionPerformed(ActionEvent arg0) 
								{
									if(arg0.getSource() == restaurar)
										F.Restaurar();
										F.opcolor=0;
									repaint();
								}
							});
							salir.addActionListener(new ActionListener()
							{
								public void actionPerformed(ActionEvent e) 
								{
									if(e.getSource()==salir)
										System.exit(0);
								}
							});
						}
					}
				});
				
				this.addMouseListener(new Raton_Listener(this));
				this.addMouseMotionListener(new Raton_MotionListener(this));
				this.addMouseWheelListener(new MouseWheelListener() 
				{
					public void mouseWheelMoved(MouseWheelEvent mwe) 
					{
						int mov = mwe.getWheelRotation();
						if(mov<0)
							F.Escalar_H(1.01, 1.01);
						else
							F.Escalar_H(0.09, 0.09);
						repaint();
					}
				});
				
				F = new Proyecto_Transformaciones(figx);
				F.Mapeo_Ventana();
				
				// Barra de herramientas.
				barra = new JToolBar("Transformaciones rápidas",JToolBar.VERTICAL);
				cont.add(barra,BorderLayout.WEST);
				/* --------------------------------------------------------------------------------------------------------------------------------------------------- */
				diburlg = getClass().getResource("/Resources/note.png");
				Action a1 = new AbstractAction("Escalar",new ImageIcon(diburlg))
				{
					private static final long serialVersionUID = 1L;

					public void actionPerformed(ActionEvent ap)
					{
						F.Restaurar();
						repaint();
					}
				};
				a1.putValue(Action.SHORT_DESCRIPTION, "Dibuja una rosa");
				a1.putValue(Action.MNEMONIC_KEY, new Integer('O'));
				a1.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_O,InputEvent.CTRL_MASK));
				/* --------------------------------------------------------------------------------------------------------------------------------------------------- */
				escurlmg = getClass().getResource("/Resources/escalarmasg.png");
				Action a2 = new AbstractAction("Escalar",new ImageIcon(escurlmg))
				{
					private static final long serialVersionUID = 1L;

					public void actionPerformed(ActionEvent ap)
					{
						F.Escalar_H(1.1, 1.1);
						repaint();
					}
				};
				a2.putValue(Action.SHORT_DESCRIPTION, "Escala la imagen de manera automática (+1).");
				a2.putValue(Action.MNEMONIC_KEY, new Integer('E'));
				a2.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_E,InputEvent.CTRL_MASK));
				/* --------------------------------------------------------------------------------------------------------------------------------------------------- */
				escurlmng = getClass().getResource("/Resources/escalarmenosg.png");
				Action a3 = new AbstractAction("Dibujar",new ImageIcon(escurlmng))
				{
					private static final long serialVersionUID = 1L;

					public void actionPerformed(ActionEvent ap)
					{
						F.Escalar_H(0.9, 0.9);
						repaint();
					}
				};
				a3.putValue(Action.SHORT_DESCRIPTION, "Escala la imagen de marea automática (-1).");
				a3.putValue(Action.MNEMONIC_KEY, new Integer('D'));
				a3.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_D,InputEvent.CTRL_MASK));
				/* --------------------------------------------------------------------------------------------------------------------------------------------------- */
				defurlg = getClass().getResource("/Resources/deformarg.png");
				Action a4 = new AbstractAction("Deformar",new ImageIcon(defurlg))
				{
					private static final long serialVersionUID = 1L;

					public void actionPerformed(ActionEvent ap)
					{
						F.Deformar_Punto(0.1,0.1);
						repaint();
					}
				};
				a4.putValue(Action.SHORT_DESCRIPTION, "Deforma la figura.");
				a4.putValue(Action.MNEMONIC_KEY, new Integer('f'));
				a4.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_F,InputEvent.CTRL_MASK));
				/* --------------------------------------------------------------------------------------------------------------------------------------------------- */
				rotsurlg = getClass().getResource("/Resources/rotarsg.png");
				Action a5 = new AbstractAction("Rotar en sentido a las  manecillas del reloj",new ImageIcon(rotsurlg))
				{
					private static final long serialVersionUID = 1L;

					public void actionPerformed(ActionEvent ap)
					{
						F.Rotar_Favor_Punto(1);
						F.fav=true;
						F.cont=false;
						repaint();
					}
				};
				a5.putValue(Action.SHORT_DESCRIPTION, "Rota la figura en sentido a las manecillas del reloj.");
				a5.putValue(Action.MNEMONIC_KEY, new Integer('S'));
				a5.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_S,InputEvent.CTRL_MASK));
				/* --------------------------------------------------------------------------------------------------------------------------------------------------- */
				rotcurlg = getClass().getResource("/Resources/rotarcg.png");
				Action a6 = new AbstractAction("Rotar en contra del sentido de las manecillas del reloj",new ImageIcon(rotcurlg))
				{
					private static final long serialVersionUID = 1L;

					public void actionPerformed(ActionEvent ap)
					{
						F.Rotar_Contra_Punto(1);
						F.cont=true;
						F.fav=false;
						repaint();
					}
				};
				a6.putValue(Action.SHORT_DESCRIPTION, "Rota la figura en sentido contrario a las manecillas del reloj.");
				a6.putValue(Action.MNEMONIC_KEY, new Integer('C'));
				a6.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_C,InputEvent.CTRL_MASK));
				/* --------------------------------------------------------------------------------------------------------------------------------------------------- */
				trasurlg = getClass().getResource("/Resources/trasladarg.png");
				Action a7 = new AbstractAction("Trasladar",new ImageIcon(trasurlg))
				{
					private static final long serialVersionUID = 1L;

					public void actionPerformed(ActionEvent ap)
					{
						Dialog_Trasladar obt = new Dialog_Trasladar(ventana,true);
						int vec[] = obt.Mostrar();
						F.Trasladar(vec[0], vec[1]);
						repaint();
					}
				};
				a7.putValue(Action.SHORT_DESCRIPTION, "Traslada la figura a un punto definido por el usuario.");
				a7.putValue(Action.MNEMONIC_KEY, new Integer('T'));
				a7.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_T,InputEvent.CTRL_MASK));
				/* --------------------------------------------------------------------------------------------------------------------------------------------------- */
				refurlg = getClass().getResource("/Resources/reflejarg.png");
				Action a8 = new AbstractAction("Reflejar",new ImageIcon(refurlg))
				{
					private static final long serialVersionUID = 1L;

					public void actionPerformed(ActionEvent ap)
					{
						Dialog_Reflexion obt = new Dialog_Reflexion(ventana,true);
						int opc[] = obt.Mostrar();
						
						if(opc[0]==1&&opc[1]==1)
							F.reflex=0;
						else if(opc[0]==-1&&opc[1]==1)
							F.reflex=1;
						else if(opc[0]==1&&opc[1]==-1)
							F.reflex=2;
						else if(opc[0]==-1&&opc[1]==-1)
							F.reflex=3;
						F.Reflexion(opc[0], opc[1]);
						repaint();
					}
				};
				a8.putValue(Action.SHORT_DESCRIPTION, "Refleja la figura en x, y o en yx.");
				a8.putValue(Action.MNEMONIC_KEY, new Integer('J'));
				a8.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_J,InputEvent.CTRL_MASK));
				/* --------------------------------------------------------------------------------------------------------------------------------------------------- */
				colorurl = getClass().getResource("/Resources/colorsel.png");
				Action a9 = new AbstractAction("Cambiar Color",new ImageIcon(colorurl))
				{
					private static final long serialVersionUID = 1L;

					public void actionPerformed(ActionEvent ap)
					{
						color = JColorChooser.showDialog(Main.this, "Selecciona el color a aplicar", Color.black);
						F.opcolor = 1;
						F.color = color;
						F.colors="Color: ["+F.color.getRed()+", "+F.color.getGreen()+", "+F.color.getBlue()+"]";
						repaint();
					}
				};
				a9.putValue(Action.SHORT_DESCRIPTION, "Selector de colores.");
				a9.putValue(Action.MNEMONIC_KEY, new Integer('X'));
				a9.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_X,InputEvent.CTRL_MASK));
				/* --------------------------------------------------------------------------------------------------------------------------------------------------- */
				
				ayurl = getClass().getResource("/Resources/ayuda.png");
				Action ayuda = new AbstractAction("Ayuda",new ImageIcon(ayurl))
				{
					private static final long serialVersionUID = 1L;

					public void actionPerformed(ActionEvent ap)
					{
						@SuppressWarnings("unused")
						Ayuda oba = new Ayuda();
					}
				};
				ayuda.putValue(Action.SHORT_DESCRIPTION, "Muestra la ayuda");
				ayuda.putValue(Action.MNEMONIC_KEY, 112);
				ayuda.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_F1,InputEvent.CTRL_MASK));
				/* --------------------------------------------------------------------------------------------------------------------------------------------------- */
				barra.add(a1);barra.add(a2);barra.add(a3);barra.add(a4);barra.add(a5);barra.add(a6);barra.add(a7);barra.add(a8);barra.add(a9);barra.add(ayuda);
				barra.setBackground(new Color(137,172,254));
				/* --------------------------------------------------------------------------------------------------------------------------------------------------- */
				
				cont.add(this,BorderLayout.CENTER);
				BFig = new JButton("Dibujar");
					BFig.addActionListener(this);
				BEscP = new JButton("Escalar");
					BEscP.addActionListener(this);
				BDef = new JButton("Deformación");
					BDef.addActionListener(this);
				BRot = new JButton("Rotación");
					BRot.addActionListener(this);
				BTras = new JButton("Traslación");
					BTras.addActionListener(this);
				BRef = new JButton("Reflexión");
					BRef.addActionListener(this);
				
				ventana.setResizable(false);
				ventana.setVisible(true);
				ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public BufferedImage loadImage(String nombre) 
	{
        URL url = null;
        try 
        {
        	url = getClass().getResource(nombre);
        	return ImageIO.read(url);

        } 
        catch (Exception e) 
        {
        	System.out.println("No se pudo cargar la imagen " + nombre +" de "+url);
        	System.out.println("El error fue : "+e.getClass().getName()+" "+e.getMessage());
        	return null; 
        }
     }
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(buf, 0, 0, this);
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		int ex = 0, ye=0;
			for (int i = 0; i < 22; i++) 
			{
				g2.setStroke(new BasicStroke(1f,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND,0, new float[]{10}, 0));
				g2.setColor(new Color(234,234,234));
				g2.drawLine(0, ye, 1070, ye);
				g2.setColor(Color.LIGHT_GRAY);
				g2.drawString(""+ex, ex+3, 15);
				
				g2.setColor(new Color(234,234,234));
				g2.drawLine(ex, 0, ex, 700);
				g2.setColor(Color.LIGHT_GRAY);
				g2.drawString(""+ye, 3, ye-5);
				
				g2.setStroke(new BasicStroke(1f));
				ex+=50;
				ye+=50;
			}
		
			if(scalmas==true)
			{
				trans.scale(1.1, 1.1);
				g2.transform(trans);
				F.Dibujar(g2);
				scalmas=false;
			}
			
			limite = new Rectangle2D.Double(1070,0,366,730);
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			GradientPaint gr = new GradientPaint((float)1183,(float)0,new Color(255,243,243),(float)1183,(float)700,new Color(255,63,63));
			g2.setPaint(gr);
			g2.fill(limite);
			
			if(mover == true)
			{
				g2.setStroke(new BasicStroke(1f,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND,0, new float[]{5}, 0));
				F.Encuentra_MinMax();
				contorno = new Rectangle2D.Double(F.minx, F.miny, F.maxx-F.minx, F.maxy-F.miny);
				g2.setColor(Color.DARK_GRAY);
				g2.draw(contorno);
				g2.setStroke(new BasicStroke(1f));
				g2.drawRect((int)contorno.getMinX()-3, (int)contorno.getMinY()-3, 5, 5);
				g2.drawRect((int)contorno.getMaxX()-3, (int)contorno.getMinY()-3, 5, 5);
				g2.drawRect((int)contorno.getMinX()-3, (int)contorno.getCenterY()-3, 5, 5);
				g2.drawRect((int)contorno.getMaxX()-3, (int)contorno.getCenterY()-3, 5, 5);
				g2.drawRect((int)contorno.getMinX()-3, (int)contorno.getMaxY()-3, 5, 5);
				g2.drawRect((int)contorno.getMaxX()-3, (int)contorno.getMaxY()-3, 5, 5);
				g2.drawRect((int)contorno.getCenterX()-3, (int)contorno.getMinY()-3, 5, 5);
				g2.drawRect((int)contorno.getCenterX()-3, (int)contorno.getMaxY()-3, 5, 5);
			}
			else
			{
				minx=0;maxx=0;miny=0;maxy=0;
			}
			g2.setColor(new Color(80,0,0,70));
			g2.fillRect(1070, 15, 366, 20);
			g2.fillRect(1090, 150, 366, 20);
			g2.fillRect(1090, 50, 366, 20);
			g2.fillRect(1090, 240, 366, 20);
			g2.fillRect(1070, 370, 366, 20);
			g2.fillRect(1087, 623, 217, 35);
			g2.setColor(Color.black);
			g2.setFont(new Font("Calibri",Font.BOLD+Font.ITALIC,18));
			g2.drawString("Propiedades de la figura:", 1080, 30);
			g2.setFont(new Font("Calibri",Font.PLAIN,12));
			// Propiedades: NOMBRE Y PUNTOS
			g2.setColor(Color.darkGray);
			g2.drawString("Nombre y descripción:", 1095, 65);
			g2.setColor(Color.black);
			g2.drawString("Nombre: "+F.nombrefig, 1110, 90);
			g2.drawString("Núm. de puntos: "+F.npuntos, 1110, 110);
			// Propiedades: TAMAÑO Y POSICIÓN
			F.Encuentra_MinMax();
			g2.setColor(Color.darkGray);
			g2.drawString("Posición y tamaño:", 1095, 165);
			g2.setColor(Color.black);
			g2.drawString("X: "+(int)F.minx, 1110, 190);
			g2.drawString("Y: "+(int)F.miny, 1190, 190);
			g2.drawString("Ancho: "+((int)F.maxx-(int)F.minx), 1110, 220);
			g2.drawString("Alto: "+((int)F.maxy-(int)F.miny), 1190, 220);
			// Propiedades: ESTADO
			g2.setColor(Color.darkGray);
			g2.drawString("Estado de imagen: ", 1095, 255);
			g2.setColor(Color.black);
			g2.drawString(status1, 1110, 280);
			g2.drawString(status2, 1110, 300);
			g2.drawString(colors, 1110, 320);
			g2.drawString(reflejado, 1110, 340);
			g2.setColor(Color.black);
			g2.setFont(new Font("Calibri",Font.BOLD+Font.ITALIC,18));
			// Mapeo de ventana:
			g2.drawString("Mapeo de ventana:", 1080, 385);
			g2.setColor(Color.white);
			g2.fillRect(1100, 400, 195, 173);
			g2.setColor(new Color(234,234,234));
			int exx=1100,yye=400;
			for (int i = 0; i < 16; i++) 
			{
				g2.setStroke(new BasicStroke(1f,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND,0, new float[]{3}, 0));
				g2.drawLine(1100, yye, 1295, yye);
				yye+=11;
			}
			for (int i = 0; i < 18; i++) 
			{
				g2.setStroke(new BasicStroke(1f,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND,0, new float[]{6}, 0));
				g2.drawLine(exx, 400, exx, 573);
				exx+=11;
			}
			g2.setStroke(new BasicStroke(1f));
			g2.setColor(Color.black);
			g2.drawLine(1100, 400, 1295, 400);
			g2.drawLine(1100, 400, 1100, 573);
			g2.drawLine(1100, 573, 1295, 573);
			g2.drawLine(1295, 573, 1295, 400);
			g2.setFont(new Font("Calibri",Font.ITALIC,13));
			g2.drawString("Para obtener ayuda, presione ALT + F1", 1090, 645);
			g2.setFont(new Font("Calibri",Font.PLAIN,12));
			g2.setColor(Color.darkGray);
			g2.drawString("Ratón: ["+posx+", "+posy+"]", 950, 690);
			F.Dibujar(g2);
			F.Encuentra_MinMax();
			F.Dibujar_MV(g);
	}
	
	@SuppressWarnings("unused")
	public static void main(String[] args) 
	{
		Loader obloader = new Loader();
		new Main("DaniVega's Drawing Notebook - Documento 1",figura);
	}
	
//	MÉTODOS DE 'ESCALAR' --------------------------------------------------------------------------------------------------------------
	
	public void Escalar_H(double escx, double escy)
	{
		
	}
	
	public void actionPerformed(ActionEvent arg0) {}
}