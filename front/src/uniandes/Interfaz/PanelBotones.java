package uniandes.Interfaz;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class PanelBotones extends JPanel implements ActionListener{
	
	private InterfazPrincipal padre;
	private JButton puertaAbierta;
	private JButton aperturaNoPermitida;
	private JButton aperturaSospechosa;
	private JButton bateriaCritico;
	private JButton cerraduraFueraDeLinea;
	private JButton hubFueraDeLinea;
	
	private JPanel botones;

	
	public PanelBotones(InterfazPrincipal ppadre)
	{
		padre = ppadre;
		
		TitledBorder border = BorderFactory.createTitledBorder( "Filtros" );
        setBorder( border );
        setLayout(new BorderLayout());
        setPreferredSize( new Dimension(200,500) );
        

        
        puertaAbierta = new JButton("Puerta Abierta");
        puertaAbierta.setPreferredSize( new Dimension(175,26) );
        puertaAbierta.setActionCommand( "abierta" );
        puertaAbierta.addActionListener( this );
		
        aperturaNoPermitida = new JButton("Apertura No Permitida");
        aperturaNoPermitida.setPreferredSize( new Dimension(175,26) );
        aperturaNoPermitida.setActionCommand( "noPermitida" );
        aperturaNoPermitida.addActionListener( this );
		
        aperturaSospechosa = new JButton("Apertura Sospechosa");
        aperturaSospechosa.setPreferredSize( new Dimension(175,26) );
        aperturaSospechosa.setActionCommand( "sospechosa" );
        aperturaSospechosa.addActionListener( this );
		
        bateriaCritico = new JButton("Bateria Critica");
        bateriaCritico.setPreferredSize( new Dimension(175,26) );
        bateriaCritico.setActionCommand( "bateria" );
        bateriaCritico.addActionListener( this );
		
        cerraduraFueraDeLinea = new JButton("Cerradura Fuera de Linea");
        cerraduraFueraDeLinea.setPreferredSize( new Dimension(175,26) );
        cerraduraFueraDeLinea.setActionCommand( "cerLinea" );
        cerraduraFueraDeLinea.addActionListener( this );
		
        hubFueraDeLinea = new JButton("Hub Fuera de Linea");
        hubFueraDeLinea.setPreferredSize( new Dimension(175,26) );
        hubFueraDeLinea.setActionCommand( "hubLinea" );
        hubFueraDeLinea.addActionListener( this );
        
        botones = new JPanel();
        botones.setLayout( new GridLayout(12,0) );
        botones.add(puertaAbierta);
        botones.add(Box.createRigidArea(new Dimension(100,20)));
        botones.add(aperturaNoPermitida);
        botones.add(Box.createRigidArea(new Dimension(100,20)));
        botones.add(aperturaSospechosa);
        botones.add(Box.createRigidArea(new Dimension(100,20)));
        botones.add(bateriaCritico);
        botones.add(Box.createRigidArea(new Dimension(100,20)));
        botones.add(cerraduraFueraDeLinea);
        botones.add(Box.createRigidArea(new Dimension(100,20)));
        botones.add(hubFueraDeLinea);
        botones.add(Box.createRigidArea(new Dimension(100,20)));
        
        add(botones, BorderLayout.CENTER);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		
		String comando = e.getActionCommand();
		
		if(comando.equals("abierta"))
		{
			padre.filtroAbierta();
		}
		else if(comando.equals("noPermitida"))
		{
			padre.filtroAperturaNoPermitida();
		}
		else if(comando.equals("sospechosa"))
		{
			padre.filtroAperturaSospechosa();
		}
		else if(comando.equals("bateria"))
		{
			padre.filtroBateria();
		}
		else if(comando.equals("cerLinea"))
		{
			padre.filtroCerradura();
		}
		else if(comando.equals("hubLinea"))
		{
			padre.filtroHub();
		}
	}
}
