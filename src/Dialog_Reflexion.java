import java.awt.*;
import java.awt.event.*;
import java.net.URL;

import javax.swing.*;

class Dialog_Reflexion extends JDialog implements ActionListener
{
	private static final long serialVersionUID = 1L;
	JRadioButton equis,ye,equisye;
	ButtonGroup grupo;
	JButton ac, ca;
	JLabel ex,ey,exy;
	int opc[];
	URL xurl,yurl,xyurl;
	
	public Dialog_Reflexion(JFrame vent, boolean mod)
	{
		super(vent,mod);
		this.setLayout(new FlowLayout());
		this.setSize(550, 130);
		this.setTitle("Traslada la figura");
		this.setLocation(200, 200);
		vent.setBackground(Color.white);
		xurl = getClass().getResource("/Resources/refx.png");
		yurl = getClass().getResource("/Resources/refy.png");
		xyurl = getClass().getResource("/Resources/refxy.png");
		opc = new int[2];
		equis = new JRadioButton("",false);
			ex = new JLabel();
			ex.setIcon(new ImageIcon(xurl));
		ye = new JRadioButton("",false);
			ey = new JLabel();
			ey.setIcon(new ImageIcon(yurl));
		equisye = new JRadioButton("",false);
			exy = new JLabel();
			exy.setIcon(new ImageIcon(xyurl));
			
		ac = new JButton("Aceptar");
//		ac.setEnabled(false);
		ca = new JButton("Cancelar");
		grupo = new ButtonGroup();
		grupo.add(equis);grupo.add(ye);grupo.add(equisye);
		
		add(equis);add(ex);add(ye);add(ey);add(equisye);add(exy);add(ac);add(ca);
		ac.addActionListener(this);
		ca.addActionListener(this);
	}

	public int[] Mostrar()
	{
		this.setVisible(true);
		return opc;
	}

	public void actionPerformed(ActionEvent ae) 
	{
		if(ae.getSource() == ac)
		{
			if(equis.isSelected() == true)
			{
				opc[0] = -1;
				opc[1] = 1;
				Dialog_Reflexion.this.setVisible(false);
				Dialog_Reflexion.this.dispose();
			}
			if(ye.isSelected() == true)
			{
				opc[0] = 1;
				opc[1] = -1;
				ac.setEnabled(true);
				Dialog_Reflexion.this.setVisible(false);
				Dialog_Reflexion.this.dispose();
			}
			if(equisye.isSelected() == true)
			{
				opc[0] = -1;
				opc[1] = -1;
				ac.setEnabled(true);
				Dialog_Reflexion.this.setVisible(false);
				Dialog_Reflexion.this.dispose();
			}
		}
		if(ae.getSource() == ca)
		{
			opc[0] = 1;
			opc[1] = 1;
			Dialog_Reflexion.this.setVisible(false);
			Dialog_Reflexion.this.dispose();
		}
	}
}