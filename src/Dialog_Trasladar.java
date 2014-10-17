import java.awt.*;
import java.awt.event.*;

import javax.swing.*;


public class Dialog_Trasladar extends JDialog implements ActionListener
{
	private static final long serialVersionUID = 1L;
	JLabel et1,et2;
	JTextField ct1, ct2;
	JButton ac, ca;
	int coords[];
	
	public Dialog_Trasladar(JFrame vent, boolean mod)
	{
		super(vent,mod);
		et1 = new JLabel("X:");
		et2 = new JLabel("Y:");
		
		ct1 = new JTextField(5);
		ct2 = new JTextField(5);
		
		ac = new JButton("Aceptar");
		ca = new JButton("Cancelar");
		coords = new int[2];
		this.setLayout(new FlowLayout());
		this.setSize(380, 75);
		this.setTitle("Traslada la figura");
		this.setLocation(200, 200);
		add(et1);add(ct1);add(et2);add(ct2);add(ac);add(ca);
		ac.addActionListener(this);
		ca.addActionListener(this);
	}

	public int[] Mostrar()
	{
		this.setVisible(true);
		return coords;
	}

	public void actionPerformed(ActionEvent ae) 
	{
		if(ae.getSource() == ac)
		{
			String x = ct1.getText();
			String y = ct2.getText();
			
			coords[0] = Integer.parseInt(x);
			coords[1] = Integer.parseInt(y);
			
			this.setVisible(false);
			this.dispose();
		}
		
		if(ae.getSource() == ca)
		{
			coords[0] = 0;  coords[1] = 0;
			Dialog_Trasladar.this.setVisible(false);
			Dialog_Trasladar.this.dispose();
		}
	}
}
