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

	public static final String NORMAL = "normal";
	public static final String PUERTA_ABIERTA = "puerta abierta";
	public static final String APERTURA_NO_PERMITIDA = "apertura no permitida";
	public static final String APERTURA_SOSPECHOSA = "apertura sospechosa";
	public static final String BATERIA_CRITICA = "bateria critica";
	public static final String CERRADURA_FUERA_DE_LINEA = "cerradura fuera de linea";
	public static final String HUB_FUERA_DE_LINEA = "hub fuera de linea";

	public PanelBotones(InterfazPrincipal ppadre)
	{
		padre = ppadre;
		
		TitledBorder border = BorderFactory.createTitledBorder( "Filtros" );
        setBorder( border );
        setLayout(new BorderLayout());
        setPreferredSize( new Dimension(200,500) );
        
        puertaAbierta = new JButton(PUERTA_ABIERTA);
        puertaAbierta.setPreferredSize( new Dimension(175,26) );
        puertaAbierta.setActionCommand(PUERTA_ABIERTA);
        puertaAbierta.addActionListener( this );
		
        aperturaNoPermitida = new JButton(APERTURA_NO_PERMITIDA);
        aperturaNoPermitida.setPreferredSize( new Dimension(175,26) );
        aperturaNoPermitida.setActionCommand(APERTURA_NO_PERMITIDA);
        aperturaNoPermitida.addActionListener( this );
		
        aperturaSospechosa = new JButton(APERTURA_SOSPECHOSA);
        aperturaSospechosa.setPreferredSize( new Dimension(175,26) );
        aperturaSospechosa.setActionCommand(APERTURA_SOSPECHOSA);
        aperturaSospechosa.addActionListener( this );
		
        bateriaCritico = new JButton(BATERIA_CRITICA);
        bateriaCritico.setPreferredSize( new Dimension(175,26) );
        bateriaCritico.setActionCommand( BATERIA_CRITICA );
        bateriaCritico.addActionListener( this );
		
        cerraduraFueraDeLinea = new JButton(CERRADURA_FUERA_DE_LINEA);
        cerraduraFueraDeLinea.setPreferredSize( new Dimension(175,26) );
        cerraduraFueraDeLinea.setActionCommand( CERRADURA_FUERA_DE_LINEA );
        cerraduraFueraDeLinea.addActionListener( this );
		
        hubFueraDeLinea = new JButton(HUB_FUERA_DE_LINEA);
        hubFueraDeLinea.setPreferredSize( new Dimension(175,26) );
        hubFueraDeLinea.setActionCommand(HUB_FUERA_DE_LINEA);
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
		
		if(comando.equals(PUERTA_ABIERTA))
		{
			padre.filtroAbierta();
		}
		else if(comando.equals(APERTURA_NO_PERMITIDA))
		{
			padre.filtroAperturaNoPermitida();
		}
		else if(comando.equals(APERTURA_SOSPECHOSA))
		{
			padre.filtroAperturaSospechosa();
		}
		else if(comando.equals(BATERIA_CRITICA))
		{
			padre.filtroBateria();
		}
		else if(comando.equals(CERRADURA_FUERA_DE_LINEA))
		{
			padre.filtroCerradura();
		}
		else if(comando.equals(HUB_FUERA_DE_LINEA))
		{
			padre.filtroHub();
		}
	}
}
