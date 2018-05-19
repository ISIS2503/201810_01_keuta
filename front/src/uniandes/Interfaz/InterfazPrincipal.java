package uniandes.Interfaz;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import controllerPackage.Controlador;

public class InterfazPrincipal extends JFrame implements ActionListener{

    private Controlador controlador;
    private PanelBotones panelBotones;
    private PanelTop panelTop;
    private PanelMapa panelMapa;

    JTextField usuariotext;
	JTextField contrasenatext;
	
	JFrame log;
	
	public InterfazPrincipal()
	{
		try
        {
			controlador = new Controlador(this);
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
		usuariotext.setPreferredSize(new Dimension(175, 26));
		usuariotext.setText("seguridad@seguridad.edu.co");
		contrasenatext = new JTextField();
		contrasenatext.setPreferredSize(new Dimension(175, 26));
		contrasenatext.setText("HolaArquisoft123");
		JButton aceptar = new JButton("enviar");
		
		aceptar.setActionCommand( "enviar" );
		aceptar.addActionListener( this );
		
		log.add(usuariotext);
		log.add(contrasenatext);
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
	
	public DetailedApto pedirInfoApto(int apto)
	{
		try {
			return controlador.darApto(apto);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public ArrayList<AptoHistorial> darHistorial() throws MalformedURLException, IOException
	{
		return controlador.darHistorial();
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
		if(comando.equals("enviar"))
		{
			String numero = "";
			try {
				numero = controlador.enviar(usuariotext.getText(), contrasenatext.getText());
			} catch (MalformedURLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
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
