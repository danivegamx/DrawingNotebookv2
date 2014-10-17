import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import javax.swing.*;

public class Ayuda extends JWindow
{
	private static final long serialVersionUID = 1L;
	URL fond;
	JLabel img;
	JButton aceptar;
	
	public Ayuda()
	{
		this.setLayout(null);
		this.setSize(650, 500);
		this.setBounds(358,185,650,500);
		
		fond = getClass().getResource("/Resources/fondoayuda.jpg");
		img = new JLabel();
			img.setIcon(new ImageIcon(fond));
			img.setBounds(0,0,650,500);
		aceptar = new JButton("Aceptar");
			aceptar.setBounds(47, 150, 100, 30);
			aceptar.addActionListener(new ActionListener() 
			{
				public void actionPerformed(ActionEvent arg0) 
				{
					Salir();
				}
			});
			
			add(aceptar);add(img);
			this.setVisible(true);
	}
	
	public void Salir()
	{
		this.setVisible(false);
	}
}
