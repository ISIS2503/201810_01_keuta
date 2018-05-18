package uniandes.Interfaz;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class PanelTop extends JPanel implements ActionListener{

	private InterfazPrincipal padre;
	private PanelIngreso ingreso;
    private JLabel imagenTop;
    private JButton quitarFiltros;
    private JButton historial;

	public PanelTop(InterfazPrincipal ppadre)
	{
		ingreso = new PanelIngreso(padre);
		
		quitarFiltros = new JButton("quitar filtros");
		quitarFiltros.setActionCommand( "filtros" );
		quitarFiltros.addActionListener( this );
		historial = new JButton("historial");
		historial.setActionCommand( "historial" );
		historial.addActionListener( this );
		quitarFiltros.setPreferredSize( new Dimension(175,10) );
        historial.setPreferredSize( new Dimension(175,10) );
        
        
		JPanel east = new JPanel();
		east.setLayout(new GridLayout(3, 0));
		east.add(Box.createRigidArea(new Dimension(110,20)));
		east.add(historial);
		east.add(Box.createRigidArea(new Dimension(110,20)));
		
		JPanel west = new JPanel();
		west.setLayout(new GridLayout(3, 0));
		west.add(Box.createRigidArea(new Dimension(110,20)));
		west.add(quitarFiltros);
		west.add(Box.createRigidArea(new Dimension(110,20)));

		
		padre = ppadre;
        ImageIcon hola = new ImageIcon("./data/imagenes/yale.jpg");
        imagenTop = new JLabel();
        imagenTop.setIcon(hola);
        
        JPanel temp = new JPanel();
        temp.setLayout(new FlowLayout());
        temp.add(imagenTop);
        
        setLayout(new BorderLayout());
        
		setBorder(new EmptyBorder(0, 20, 0, 20));

        add( east, BorderLayout.EAST );
        add( west, BorderLayout.WEST );
        add( temp, BorderLayout.CENTER );
        add( ingreso, BorderLayout.NORTH );
		
	}

	@Override
	public void actionPerformed(ActionEvent e){

		String comando = e.getActionCommand();
		
		if(comando.equals("historial"))
		{
			ArrayList<AptoHistorial> historial = new ArrayList<>();
			try {
				historial = padre.darHistorial();
			} catch (MalformedURLException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			JFrame temp = new JFrame("historial");
			temp.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
			temp.setSize( 500, 300 );
			temp.setResizable( false );
			temp.setLocationRelativeTo(null);
			temp.setLayout(new FlowLayout());
			
			for(AptoHistorial apthist: historial)
			{
				JLabel apto = new JLabel("apartamento numuero: " + apthist.numero);
				JLabel error = new JLabel("error: " + apthist.error);
				JLabel fecha = new JLabel("fecha: " + apthist.fecha);
				JPanel nuevo = new JPanel();
				nuevo.setLayout(new FlowLayout());
				nuevo.add(apto);
				nuevo.add(error);
				nuevo.add(fecha);
				temp.add(nuevo);
			}
			
			temp.setVisible( true );
		}
		else if(comando.equals("filtros"))
		{
			padre.quitarFiltros();
		}
	}
}
