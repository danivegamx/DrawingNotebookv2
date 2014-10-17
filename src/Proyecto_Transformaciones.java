
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

public class Proyecto_Transformaciones 
{
	double fig[][]; // Esta ser� la matriz de la figura.
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
	String nombrefig = "Nota Musical",status1="Dimensiones: Original",status2="Orientaci�n: Original",colors="Color: Original",reflejado="Reflejado: No";
	boolean mover,cont,fav;
	Shape figuramv; // Figura que se encontrar� en el mapeo de ventana.
	Shape copia; // �sta copia servir� para tener un respaldo de la figura, en caso de que queramos restaurarla.
	Shape figura; // Figura original a la cu�l se le aplicar�n las transformaciones.
	
	public Proyecto_Transformaciones(Shape fig) // Para CUALQUIER Figura.
	{
		figura = fig;
		copia = fig;
		figuramv=fig;
		Reflexion(-1,1);
	}
	
	public void Restaurar() // M�todo que restaurar� la figura original.
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
	
	public void Dibujar(Graphics g) // M�todo para dibujar la figura.
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
	
//	M�TODOS DE 'ESCALAR' --------------------------------------------------------------------------------------------------------------
	
	public void Escalar_H(double escx, double escy)
	{
		Rectangle2D pos=figura.getBounds2D();
		AffineTransform trans = new AffineTransform();
		trans.translate(pos.getCenterX(), pos.getCenterY() );
		trans.scale(escx, escy);
		trans.translate(-pos.getCenterX(), -pos.getCenterY() );
		figura = trans.createTransformedShape(figura);
	}
	
// M�TODOS DE 'DEFORMAR' --------------------------------------------------------------------------------------------------------------
	
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
				
		// 2. Aplicar la transformaci�n de Escalar
		Deformar(defx,defy);
				
		// 3. Regresar la figura al punto original
		Trasladar(tx,ty);
	}
	
//	M�TODOS DE 'ROTAR' ----------------------------------------------------------------------------------------------------------------
	
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
			status2="Orientaci�n: "+gradosfv+"� FMR";
		}
	}
	
	public void Rotar_Favor_Punto(int grados)
	{
	
		// 1. Trasladarla al origen
		double tx = rect.getCenterX(), ty = rect.getCenterY();
		Trasladar(-tx,-ty);
		
		// 2. Aplicar la transformaci�n de Escalar
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
			status2="Orientaci�n: "+gradosct+"� CMR";
		}
	}
	
	public void Rotar_Contra_Punto(int grados)
	{
		// 1. Trasladarla al origen
		double tx = rect.getCenterX(), ty = rect.getCenterY();
		Trasladar(-tx,-ty);
		
		// 2. Aplicar la transformaci�n de Escalar
		Rotar_Contra(grados);
				
		// 3. Regresar la figura al punto original
		Trasladar(tx,ty);
	}

//	M�TODOS DE 'TRASLADAR' ------------------------------------------------------------------------------------------------------------
	
	public void Trasladar(double tx, double ty)
	{
		AffineTransform trans = new AffineTransform();
		trans.setToTranslation(tx, ty);
		figura = trans.createTransformedShape(figura);
	}
	
//	M�TODOS DE 'REFLEJAR' --------------------------------------------------------------------------------------------------------------	
	
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
	
	public void Dibujar_MV(Graphics g) // M�todo para dibujar la figura.
	{
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2.setColor(color);
		g2.fill(figuramv);
	}
}

