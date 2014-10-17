
import java.awt.event.*;

public class Raton_Listener extends MouseAdapter 
{
	Main temp;
	
	public Raton_Listener(Main t)
	{
		temp = t;
	}
	
	public void mousePressed(MouseEvent me)
	{
		int nc = me.getClickCount();
		if(nc==2)
		{
			if(me.getX()>temp.F.contorno.getCenterX())
				temp.F.Rotar_Favor_Punto(1);
			else if(me.getX()<temp.F.contorno.getCenterX())
				temp.F.Rotar_Contra_Punto(1);
		}
		else
		{
			temp.posx = me.getX();
			temp.posy = me.getY();
			if(temp.posx>temp.F.minx && temp.posx<temp.F.maxx && temp.posy>temp.F.miny && temp.posy<temp.F.maxy)
			{
				temp.F.mover = true;
				temp.mover = true;
			}
			else
			{
				temp.F.mover =  false;
				temp.mover = false;
			}
			temp.repaint();	
		}		
	}
}
