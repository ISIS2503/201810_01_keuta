package uniandes.Interfaz;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class InterfazPrincipal extends JFrame implements ActionListener{

    private Controlador controlador;
    private PanelBotones panelBotones;
    private PanelTop panelTop;
    private PanelMapa panelMapa;

    JTextField usuariotext;
	JTextField contraseñatext;
	
	JFrame log;
	
	public InterfazPrincipal()
	{
		try
        {
			controlador = new Controlador();
        }
        catch( Exception e )
        {
            e.printStackTrace();
        }

		panelBotones = new PanelBotones(this);
		panelTop = new PanelTop(this);
		panelMapa = new PanelMapa (this);
		
		panelBotones.setVisible(false);
		panelTop.setVisible(true);
		panelMapa.setVisible(false);

		
        setLayout(new BorderLayout());
        
        add(panelTop, BorderLayout.NORTH);
        add(panelBotones, BorderLayout.WEST);
        add(panelMapa, BorderLayout.EAST);
        
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setSize( 915, 700 );
        setResizable( false );
        setLocationRelativeTo(null);
        setVisible( true );
	}
	
	
	public void hizoLogin()
	{
		
		log = new JFrame("iniciar sesión");
		
		log.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		log.setSize( 915, 700 );
		log.setResizable( false );
		log.setLocationRelativeTo(null);
		log.setLayout(new FlowLayout());
		
		usuariotext = new JTextField();
		contraseñatext = new JTextField();
		JButton aceptar = new JButton("enviar");
		
		aceptar.setActionCommand( "enviar" );
		aceptar.addActionListener( this );
		
		log.add(usuariotext);
		log.add(contraseñatext);
		log.add(aceptar);
		
		log.setVisible( true );
		
	}
	
	public void hizoLogout()
	{
		panelBotones.setVisible(false);
		panelMapa.setVisible(false);
	}
	
	public void quitarFiltros() 
	{
		panelMapa.quitarFiltros();
	}
	
	public void filtroAbierta() 
	{
		panelMapa.filtroPuertaAbierta();
	}
	
	public void filtroAperturaNoPermitida() 
	{
		panelMapa.filtroAperturaNoPermitida();
	}
	
	public void filtroAperturaSospechosa() 
	{
		panelMapa.filtroAperturaSospechosa();
	}
	
	public void filtroBateria() 
	{
		panelMapa.filtroBateriaCritica();
	}
	
	public void filtroCerradura() 
	{
		panelMapa.filtroCerraduraFueraDeLinea();
	}
	
	public void filtroHub() 
	{
		panelMapa.filtroHubFueraDeLinea();
	}
	
	public ArrayList<String> pedirInfoApto(int apto)
	{
		controlador.darApto(apto);
	}
	
	public ArrayList<AptoHistorial> darHistorial()
	{
		controlador.darHistorial();
	}
	
	public static void main( String[] args ) 
    {
        new InterfazPrincipal();
    }
	
	public void Actualizar(ArrayList<Apto> lista)
	{
		panelMapa.Actualizar(lista);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		String comando = e.getActionCommand();
		if(e.equals("enviar"))
		{
			String numero = controlador.enviar(usuariotext.getText(), contraseñatext.getText());
			
			if(numero.equals("200"))
			{
				panelBotones.setVisible(true);
				panelTop.setVisible(true);
				panelMapa.setVisible(true);
				log.dispose();
			}
			else
			{
				log.dispose();
			    JOptionPane.showMessageDialog(null, "Ingrese bien su cuenta por favor", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

}
