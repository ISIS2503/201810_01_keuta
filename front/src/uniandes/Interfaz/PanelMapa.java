package uniandes.Interfaz;

import javafx.scene.layout.Pane;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class PanelMapa extends JPanel implements ActionListener{

	private InterfazPrincipal padre;
	private JButton[][] botones;
	private String[][] estados;
	
	public static final String IMAGEN3 = "./data/imagenes/3.jpg";
	public static final String IMAGEN3ABIERTA = "./data/imagenes/3abierta.jpg";
	public static final String IMAGEN3APERTURA = "./data/imagenes/3apertura.jpg";
	public static final String IMAGEN3CERRADURA = "./data/imagenes/3cerradura.jpg";
	public static final String IMAGEN3CRITICA = "./data/imagenes/3critica.jpg";
	public static final String IMAGEN3HUB = "./data/imagenes/3hub.jpg";
	public static final String IMAGEN3SOSPECHOSA = "./data/imagenes/3sospechosa.jpg";

	public PanelMapa(InterfazPrincipal ppadre)
	{
		padre = ppadre;

		botones = new JButton[5][3];
		estados = new String[5][3];

		setBorder(new EmptyBorder(30, 30, 30, 60));

		setLayout(new GridLayout(5,3));
		for(int pos = 0; pos < 5; pos++)
		{    
			for(int g = 0; g < 3; g++)
			{
				JButton sasa = new JButton();
				sasa.setActionCommand( "" + pos + g);
				sasa.addActionListener( this );
				botones[pos][g] = sasa;
				botones[pos][g].setPreferredSize( new Dimension(200, 30) );
				estados[pos][g] = "normal";

				ImageIcon hola = new ImageIcon(IMAGEN3);
				Image holo = hola.getImage( );
				Image newimg = holo.getScaledInstance(200, 100,java.awt.Image.SCALE_SMOOTH);
				hola = new ImageIcon(newimg);
				botones[pos][g].setIcon( hola );

				add(botones[pos][g]);	                
			}
		}
	}

	public void Actualizar(ArrayList<Apto> lista)
	{
		Reset();

		for(Apto apt: lista)
		{
			String numeroApartamento = "" + apt.numero;

			if(apt.error.equals(PanelBotones.PUERTA_ABIERTA))
			{
				ImageIcon hola = new ImageIcon(IMAGEN3ABIERTA);
				Image holo = hola.getImage( );
				Image newimg = holo.getScaledInstance(200, 100,java.awt.Image.SCALE_SMOOTH);
				hola = new ImageIcon(newimg);
				botones[numeroApartamento.charAt(0)-48][numeroApartamento.charAt(1)-48].setIcon( hola );
				estados[numeroApartamento.charAt(0)-48][numeroApartamento.charAt(1)-48] = "puerta abierta";
			}
			else if(apt.error.equals(PanelBotones.APERTURA_NO_PERMITIDA))
			{
				ImageIcon hola = new ImageIcon(IMAGEN3APERTURA);
				Image holo = hola.getImage( );
				Image newimg = holo.getScaledInstance(200, 100,java.awt.Image.SCALE_SMOOTH);
				hola = new ImageIcon(newimg);
				botones[numeroApartamento.charAt(0)-48][numeroApartamento.charAt(1)-48].setIcon( hola );
				estados[numeroApartamento.charAt(0)-48][numeroApartamento.charAt(1)-48] = "apertura no permitida";
			}
			else if(apt.error.equals(PanelBotones.APERTURA_SOSPECHOSA))
			{
				ImageIcon hola = new ImageIcon(IMAGEN3SOSPECHOSA);
				Image holo = hola.getImage( );
				Image newimg = holo.getScaledInstance(200, 100,java.awt.Image.SCALE_SMOOTH);
				hola = new ImageIcon(newimg);
				botones[numeroApartamento.charAt(0)-48][numeroApartamento.charAt(1)-48].setIcon( hola );
				estados[numeroApartamento.charAt(0)-48][numeroApartamento.charAt(1)-48] = "apertura sospechosa";
			}
			else if(apt.error.equals(PanelBotones.BATERIA_CRITICA))
			{
				ImageIcon hola = new ImageIcon(IMAGEN3CRITICA);
				Image holo = hola.getImage( );
				Image newimg = holo.getScaledInstance(200, 100,java.awt.Image.SCALE_SMOOTH);
				hola = new ImageIcon(newimg);
				botones[numeroApartamento.charAt(0)-48][numeroApartamento.charAt(1)-48].setIcon( hola );
				estados[numeroApartamento.charAt(0)-48][numeroApartamento.charAt(1)-48] = "bateria critica";

			}
			else if(apt.error.equals(PanelBotones.CERRADURA_FUERA_DE_LINEA))
			{
				ImageIcon hola = new ImageIcon(IMAGEN3CERRADURA);
				Image holo = hola.getImage( );
				Image newimg = holo.getScaledInstance(200, 100,java.awt.Image.SCALE_SMOOTH);
				hola = new ImageIcon(newimg);
				botones[numeroApartamento.charAt(0)-48][numeroApartamento.charAt(1)-48].setIcon( hola );
				estados[numeroApartamento.charAt(0)-48][numeroApartamento.charAt(1)-48] = "cerradura fuera de linea";

			}
			else if(apt.error.equals(PanelBotones.HUB_FUERA_DE_LINEA))
			{
				ImageIcon hola = new ImageIcon(IMAGEN3HUB);
				Image holo = hola.getImage( );
				Image newimg = holo.getScaledInstance(200, 100,java.awt.Image.SCALE_SMOOTH);
				hola = new ImageIcon(newimg);
				botones[numeroApartamento.charAt(0)-48][numeroApartamento.charAt(1)-48].setIcon( hola );
				estados[numeroApartamento.charAt(0)-48][numeroApartamento.charAt(1)-48] = "hub fuera de linea";

			}
		}

//		if(PanelBotones.ESTADO_FILTRO.equals(PanelBotones.APERTURA_NO_PERMITIDA))
//		{
//			filtro
//		}
		switch (PanelBotones.ESTADO_FILTRO){
			case PanelBotones.APERTURA_NO_PERMITIDA:
				filtroAperturaNoPermitida();
				break;
			case PanelBotones.PUERTA_ABIERTA:
				filtroPuertaAbierta();
				break;
			case PanelBotones.APERTURA_SOSPECHOSA:
				filtroAperturaSospechosa();
				break;
			case PanelBotones.BATERIA_CRITICA:
				filtroBateriaCritica();
				break;
			case PanelBotones.CERRADURA_FUERA_DE_LINEA:
				filtroCerraduraFueraDeLinea();
				break;
			case PanelBotones.HUB_FUERA_DE_LINEA:
				filtroHubFueraDeLinea();
				break;
		}
	}

	public void filtroPuertaAbierta()
	{
		for(int pos = 0; pos < 5; pos++)
		{    
			for(int g = 0; g < 3; g++)
			{
				if(!estados[pos][g].equals(PanelBotones.PUERTA_ABIERTA))
				{
					ImageIcon hola = new ImageIcon(IMAGEN3);
					Image holo = hola.getImage( );
					Image newimg = holo.getScaledInstance(200, 100,java.awt.Image.SCALE_SMOOTH);
					hola = new ImageIcon(newimg);
					botones[pos][g].setIcon( hola );

				}
			}
		}
	}
	
	public void filtroAperturaNoPermitida()
	{
		for(int pos = 0; pos < 5; pos++)
		{    
			for(int g = 0; g < 3; g++)
			{
				if(!estados[pos][g].equals(PanelBotones.APERTURA_NO_PERMITIDA))
				{
					ImageIcon hola = new ImageIcon(IMAGEN3);
					Image holo = hola.getImage( );
					Image newimg = holo.getScaledInstance(200, 100,java.awt.Image.SCALE_SMOOTH);
					hola = new ImageIcon(newimg);
					botones[pos][g].setIcon( hola );
				}
			}
		}
	}
	
	public void filtroAperturaSospechosa()
	{
		for(int pos = 0; pos < 5; pos++)
		{    
			for(int g = 0; g < 3; g++)
			{
				if(!estados[pos][g].equals(PanelBotones.APERTURA_SOSPECHOSA))
				{
					ImageIcon hola = new ImageIcon(IMAGEN3);
					Image holo = hola.getImage( );
					Image newimg = holo.getScaledInstance(200, 100,java.awt.Image.SCALE_SMOOTH);
					hola = new ImageIcon(newimg);
					botones[pos][g].setIcon( hola );
				}
			}
		}
	}
	
	public void filtroBateriaCritica()
	{
		for(int pos = 0; pos < 5; pos++)
		{    
			for(int g = 0; g < 3; g++)
			{
				if(!estados[pos][g].equals(PanelBotones.BATERIA_CRITICA))
				{
					ImageIcon hola = new ImageIcon(IMAGEN3);
					Image holo = hola.getImage( );
					Image newimg = holo.getScaledInstance(200, 100,java.awt.Image.SCALE_SMOOTH);
					hola = new ImageIcon(newimg);
					botones[pos][g].setIcon( hola );
				}
			}
		}
	}
	
	public void filtroCerraduraFueraDeLinea()
	{
		for(int pos = 0; pos < 5; pos++)
		{    
			for(int g = 0; g < 3; g++)
			{
				if(!estados[pos][g].equals(PanelBotones.CERRADURA_FUERA_DE_LINEA))
				{
					ImageIcon hola = new ImageIcon(IMAGEN3);
					Image holo = hola.getImage( );
					Image newimg = holo.getScaledInstance(200, 100,java.awt.Image.SCALE_SMOOTH);
					hola = new ImageIcon(newimg);
					botones[pos][g].setIcon( hola );
				}
			}
		}
	}
	
	public void filtroHubFueraDeLinea()
	{
		for(int pos = 0; pos < 5; pos++)
		{    
			for(int g = 0; g < 3; g++)
			{
				if(!estados[pos][g].equals(PanelBotones.HUB_FUERA_DE_LINEA))
				{
					ImageIcon hola = new ImageIcon(IMAGEN3);
					Image holo = hola.getImage( );
					Image newimg = holo.getScaledInstance(200, 100,java.awt.Image.SCALE_SMOOTH);
					hola = new ImageIcon(newimg);
					botones[pos][g].setIcon( hola );
				}
			}
		}
	}
	
	public void quitarFiltros()
	{
		PanelBotones.ESTADO_FILTRO = "Chocolate";
		for(int pos = 0; pos < 5; pos++)
		{    
			for(int g = 0; g < 3; g++)
			{
				if(estados[pos][g].equals(PanelBotones.NORMAL))
				{
					ImageIcon hola = new ImageIcon(IMAGEN3);
					Image holo = hola.getImage( );
					Image newimg = holo.getScaledInstance(200, 100,java.awt.Image.SCALE_SMOOTH);
					hola = new ImageIcon(newimg);
					botones[pos][g].setIcon( hola );
				}
				else if(estados[pos][g].equals(PanelBotones.PUERTA_ABIERTA))
				{
					ImageIcon hola = new ImageIcon(IMAGEN3ABIERTA);
					Image holo = hola.getImage( );
					Image newimg = holo.getScaledInstance(200, 100,java.awt.Image.SCALE_SMOOTH);
					hola = new ImageIcon(newimg);
					botones[pos][g].setIcon( hola );
				}
				if(estados[pos][g].equals(PanelBotones.APERTURA_NO_PERMITIDA))
				{
					ImageIcon hola = new ImageIcon(IMAGEN3APERTURA);
					Image holo = hola.getImage( );
					Image newimg = holo.getScaledInstance(200, 100,java.awt.Image.SCALE_SMOOTH);
					hola = new ImageIcon(newimg);
					botones[pos][g].setIcon( hola );
				}
				if(estados[pos][g].equals(PanelBotones.APERTURA_SOSPECHOSA))
				{
					ImageIcon hola = new ImageIcon(IMAGEN3SOSPECHOSA);
					Image holo = hola.getImage( );
					Image newimg = holo.getScaledInstance(200, 100,java.awt.Image.SCALE_SMOOTH);
					hola = new ImageIcon(newimg);
					botones[pos][g].setIcon( hola );
				}
				if(estados[pos][g].equals(PanelBotones.BATERIA_CRITICA))
				{
					ImageIcon hola = new ImageIcon(IMAGEN3CRITICA);
					Image holo = hola.getImage( );
					Image newimg = holo.getScaledInstance(200, 100,java.awt.Image.SCALE_SMOOTH);
					hola = new ImageIcon(newimg);
					botones[pos][g].setIcon( hola );
				}
				if(estados[pos][g].equals(PanelBotones.CERRADURA_FUERA_DE_LINEA))
				{
					ImageIcon hola = new ImageIcon(IMAGEN3CERRADURA);
					Image holo = hola.getImage( );
					Image newimg = holo.getScaledInstance(200, 100,java.awt.Image.SCALE_SMOOTH);
					hola = new ImageIcon(newimg);
					botones[pos][g].setIcon( hola );
				}
				if(estados[pos][g].equals(PanelBotones.HUB_FUERA_DE_LINEA))
				{
					ImageIcon hola = new ImageIcon(IMAGEN3HUB);
					Image holo = hola.getImage( );
					Image newimg = holo.getScaledInstance(200, 100,java.awt.Image.SCALE_SMOOTH);
					hola = new ImageIcon(newimg);
					botones[pos][g].setIcon( hola );
				}
			}
		}
	}

	public void Reset()
	{
		for(int pos = 0; pos < 5; pos++)
		{    
			for(int g = 0; g < 3; g++)
			{
				ImageIcon hola = new ImageIcon(IMAGEN3);
				Image holo = hola.getImage( );
				Image newimg = holo.getScaledInstance(200, 100,java.awt.Image.SCALE_SMOOTH);
				hola = new ImageIcon(newimg);
				botones[pos][g].setIcon( hola );
				estados[pos][g] = PanelBotones.NORMAL;
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		String comando = e.getActionCommand();
		
		DetailedApto detailed = padre.pedirInfoApto(Integer.parseInt(comando));
		
		
		String mensaje = "propietario del apartamento "+ comando + ":" + detailed.nombrePropietario + "\n" + " unidad residencial: " + detailed.unidadResidencial + "\n" + " id del apartamento" + detailed.id;
		
		
		JOptionPane.showMessageDialog(null, mensaje, "propietarios", JOptionPane.INFORMATION_MESSAGE);
	}
}
