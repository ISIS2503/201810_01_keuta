package uniandes.Interfaz;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public class PanelIngreso extends JPanel implements ActionListener{

	private JButton boton;
	private JPanel panel;
	private InterfazPrincipal main;

	public PanelIngreso(InterfazPrincipal pmain) {
		TitledBorder border = BorderFactory.createTitledBorder( "Ingreso" );
        setBorder( border );
		panel = new JPanel();
		boton = new JButton("login");
		boton.setPreferredSize( new Dimension(175,26) );
		boton.setActionCommand( "boton" );
		boton.addActionListener( this );
		main = pmain;
		setPreferredSize(new Dimension(50, 65));
        setLayout(new BorderLayout());
		panel.setLayout(new FlowLayout());
		panel.add(boton);
        add(panel, BorderLayout.EAST);
	}

	public void hizoLogin()
	{
		boton.setText("logout");
	}

	public void hizoLogout()
	{
		boton.setText("login");
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		String comando = e.getActionCommand();

		if(comando.equals( "boton" ) && boton.getText().equals("login"))
		{
			this.hizoLogin();
			main.hizoLogin();
		}
		if(comando.equals( "boton" ) && boton.getText().equals("logout"))
		{
			this.hizoLogout();
			main.hizoLogout();
		}
	}

}
