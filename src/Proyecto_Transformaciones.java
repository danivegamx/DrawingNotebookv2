
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

public class Proyecto_Transformaciones 
{
	double fig[][]; // Esta será la matriz de la figura.
	Rectangle2D rect;
	double ancho=324,alto=551;
	double maxx;
	double maxy;
	double miny;
	double minx;
	Color color= Color.black;
	Rectangle2D limite,contorno;
	int op,npuntos = 138,gradosfv=0,gradosct=0, opcolor=0,reflex=0;
	double posx, posy;
	String nombrefig = "Nota Musical",status1="Dimensiones: Original",status2="Orientación: Original",colors="Color: Original",reflejado="Reflejado: No";
	boolean mover,cont,fav;
	Shape figuramv; // Figura que se encontrará en el mapeo de ventana.
	Shape copia; // Ésta copia servirá para tener un respaldo de la figura, en caso de que queramos restaurarla.
	Shape figura; // Figura original a la cuál se le aplicarán las transformaciones.
	
	public Proyecto_Transformaciones(Shape fig) // Para CUALQUIER Figura.
	{
		figura = fig;
		copia = fig;
		figuramv=fig;
		Reflexion(-1,1);
	}
	
	public void Restaurar() // Método que restaurará la figura original.
	{
			figura = copia;
			Reflexion(-1,1);
			color = Color.black;
	}
	
	public void Encuentra_MinMax()
	{
		rect = figura.getBounds2D();
		minx = rect.getMinX();
		maxx = rect.getMaxX();
		miny = rect.getMinY();
		maxy = rect.getMaxY();
	}
	
	public void Dibujar(Graphics g) // Método para dibujar la figura.
	{	
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2.setColor(color);
		g2.fill(figura);
		
		if(mover == true)
		{
			g2.setStroke(new BasicStroke(1f,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND,0, new float[]{5}, 0));
			Encuentra_MinMax();
			contorno = new Rectangle2D.Double(minx, miny, maxx-minx, maxy-miny);
			g2.setColor(Color.DARK_GRAY);
			g2.draw(contorno);
			g2.setStroke(new BasicStroke(1f));
		}
	}
	
//	MÉTODOS DE 'ESCALAR' --------------------------------------------------------------------------------------------------------------
	
	public void Escalar_H(double escx, double escy)
	{
		Rectangle2D pos=figura.getBounds2D();
		AffineTransform trans = new AffineTransform();
		trans.translate(pos.getCenterX(), pos.getCenterY() );
		trans.scale(escx, escy);
		trans.translate(-pos.getCenterX(), -pos.getCenterY() );
		figura = trans.createTransformedShape(figura);
	}
	
// MÉTODOS DE 'DEFORMAR' --------------------------------------------------------------------------------------------------------------
	
	public void Deformar(double defx, double defy)
	{
		AffineTransform trans = new AffineTransform();
		trans.shear(defx, defy);
		figura = trans.createTransformedShape(figura);
	}
	
	public void Deformar_Punto(double defx, double defy)
	{
		// 1. Trasladarla al origen
		double tx = rect.getCenterX(), ty = rect.getCenterY();
		Trasladar(-tx,-ty);
				
		// 2. Aplicar la transformación de Escalar
		Deformar(defx,defy);
				
		// 3. Regresar la figura al punto original
		Trasladar(tx,ty);
	}
	
//	MÉTODOS DE 'ROTAR' ----------------------------------------------------------------------------------------------------------------
	
	public void Rotar_Favor(int grados)
	{
		double rad = Math.toRadians(grados);
		
		AffineTransform trans = new AffineTransform();
		trans.rotate(rad);
		figura = trans.createTransformedShape(figura);
		
		if(cont==true)
			gradosct--;
		else if(gradosfv==0||gradosfv>0)
		{
			gradosfv++;
			status2="Orientación: "+gradosfv+"° FMR";
		}
	}
	
	public void Rotar_Favor_Punto(int grados)
	{
	
		// 1. Trasladarla al origen
		double tx = rect.getCenterX(), ty = rect.getCenterY();
		Trasladar(-tx,-ty);
		
		// 2. Aplicar la transformación de Escalar
		Rotar_Favor(grados);
		
		// 3. Regresar la figura al punto original
		Trasladar(tx,ty);
	}
	
	public void Rotar_Contra(int grados)
	{
		double rad = Math.toRadians(grados);

		AffineTransform trans = new AffineTransform();
		trans.rotate(-rad);
		figura = trans.createTransformedShape(figura);
		
		if(fav==true)
			gradosfv--;
		else if(gradosct==0||gradosct>0)
		{
			gradosct++;
			status2="Orientación: "+gradosct+"° CMR";
		}
	}
	
	public void Rotar_Contra_Punto(int grados)
	{
		// 1. Trasladarla al origen
		double tx = rect.getCenterX(), ty = rect.getCenterY();
		Trasladar(-tx,-ty);
		
		// 2. Aplicar la transformación de Escalar
		Rotar_Contra(grados);
				
		// 3. Regresar la figura al punto original
		Trasladar(tx,ty);
	}

//	MÉTODOS DE 'TRASLADAR' ------------------------------------------------------------------------------------------------------------
	
	public void Trasladar(double tx, double ty)
	{
		AffineTransform trans = new AffineTransform();
		trans.setToTranslation(tx, ty);
		figura = trans.createTransformedShape(figura);
	}
	
//	MÉTODOS DE 'REFLEJAR' --------------------------------------------------------------------------------------------------------------	
	
	public void Reflexion(int rx, int ry)
	{
		Rectangle2D pos=figura.getBounds2D();
		AffineTransform f=new AffineTransform();
		f.translate(pos.getCenterX(), pos.getCenterY() );
		f.scale(rx, ry);
		f.translate(-pos.getCenterX(), -pos.getCenterY());
		figura=f.createTransformedShape(figura);
		
	   if(reflex==0)
		   reflejado = "Reflejado: No";
	   else if(reflex==1)
		   reflejado = "Reflejado: En el eje X";
	   else if(reflex==2)
		   reflejado = "Reflejado: En el eje Y";
	   else if(reflex==3)
		   reflejado = "Reflejado: En el eje XY";
	}
	
//	MAPEO DE VENTANA -------------------------------------------------------------------------------------------------------------------
	
	public void Mapeo_Ventana()
	{
		AffineTransform trans = new AffineTransform();
		trans.scale(0.2, 0.2);
		figuramv = trans.createTransformedShape(figuramv);
		AffineTransform translate = new AffineTransform();
		translate.translate(1100, 400);
		figuramv = translate.createTransformedShape(figuramv);
		Rectangle2D pos=figuramv.getBounds2D();
		AffineTransform transreflex=new AffineTransform();
		transreflex.translate(pos.getCenterX(), pos.getCenterY() );
		transreflex.scale(-1, 1);
		transreflex.translate(-pos.getCenterX(), -pos.getCenterY());
		figuramv=transreflex.createTransformedShape(figuramv);
	}
	
	public void Dibujar_MV(Graphics g) // Método para dibujar la figura.
	{
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2.setColor(color);
		g2.fill(figuramv);
	}
}

