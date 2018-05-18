package uniandes.Interfaz;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class PanelMapa extends JPanel implements ActionListener{

	private InterfazPrincipal padre;
	private JButton[][] botones;
	private String[][] estados;


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

				ImageIcon hola = new ImageIcon("./data/imagenes/3.jpg");
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

			if(apt.error.equals("puerta abierta"))
			{
				ImageIcon hola = new ImageIcon("./data/imagenes/3abierta.jpg");
				Image holo = hola.getImage( );
				Image newimg = holo.getScaledInstance(200, 100,java.awt.Image.SCALE_SMOOTH);
				hola = new ImageIcon(newimg);
				botones[numeroApartamento.charAt(0)][numeroApartamento.charAt(1)].setIcon( hola );
				estados[numeroApartamento.charAt(0)][numeroApartamento.charAt(1)] = "puerta abierta";
			}
			else if(apt.error.equals("apertura no permitida"))
			{
				ImageIcon hola = new ImageIcon("./data/imagenes/3apertura.jpg");
				Image holo = hola.getImage( );
				Image newimg = holo.getScaledInstance(200, 100,java.awt.Image.SCALE_SMOOTH);
				hola = new ImageIcon(newimg);
				botones[numeroApartamento.charAt(0)][numeroApartamento.charAt(1)].setIcon( hola );
				estados[numeroApartamento.charAt(0)][numeroApartamento.charAt(1)] = "apertura no permitida";
			}
			else if(apt.error.equals("apertura sospechosa"))
			{
				ImageIcon hola = new ImageIcon("./data/imagenes/3sospechosa.jpg");
				Image holo = hola.getImage( );
				Image newimg = holo.getScaledInstance(200, 100,java.awt.Image.SCALE_SMOOTH);
				hola = new ImageIcon(newimg);
				botones[numeroApartamento.charAt(0)][numeroApartamento.charAt(1)].setIcon( hola );
				estados[numeroApartamento.charAt(0)][numeroApartamento.charAt(1)] = "apertura sospechosa";
			}
			else if(apt.error.equals("bateria critica"))
			{
				ImageIcon hola = new ImageIcon("./data/imagenes/3critica.jpg");
				Image holo = hola.getImage( );
				Image newimg = holo.getScaledInstance(200, 100,java.awt.Image.SCALE_SMOOTH);
				hola = new ImageIcon(newimg);
				botones[numeroApartamento.charAt(0)][numeroApartamento.charAt(1)].setIcon( hola );
				estados[numeroApartamento.charAt(0)][numeroApartamento.charAt(1)] = "bateria critica";

			}
			else if(apt.error.equals("cerradura fuera de linea"))
			{
				ImageIcon hola = new ImageIcon("./data/imagenes/3cerradura.jpg");
				Image holo = hola.getImage( );
				Image newimg = holo.getScaledInstance(200, 100,java.awt.Image.SCALE_SMOOTH);
				hola = new ImageIcon(newimg);
				botones[numeroApartamento.charAt(0)][numeroApartamento.charAt(1)].setIcon( hola );
				estados[numeroApartamento.charAt(0)][numeroApartamento.charAt(1)] = "cerradura fuera de linea";

			}
			else if(apt.error.equals("hub fuera de linea"))
			{
				ImageIcon hola = new ImageIcon("./data/imagenes/3hub.jpg");
				Image holo = hola.getImage( );
				Image newimg = holo.getScaledInstance(200, 100,java.awt.Image.SCALE_SMOOTH);
				hola = new ImageIcon(newimg);
				botones[numeroApartamento.charAt(0)][numeroApartamento.charAt(1)].setIcon( hola );
				estados[numeroApartamento.charAt(0)][numeroApartamento.charAt(1)] = "hub fuera de linea";

			}
		}
	}

	public void filtroPuertaAbierta()
	{
		for(int pos = 0; pos < 5; pos++)
		{    
			for(int g = 0; g < 3; g++)
			{
				if(!estados[pos][g].equals("puerta abierta"))
				{
					ImageIcon hola = new ImageIcon("./data/imagenes/3.jpg");
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
				if(!estados[pos][g].equals("apertura no permitida"))
				{
					ImageIcon hola = new ImageIcon("./data/imagenes/3.jpg");
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
				if(!estados[pos][g].equals("apertura sospechosa"))
				{
					ImageIcon hola = new ImageIcon("./data/imagenes/3.jpg");
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
				if(!estados[pos][g].equals("bateria critica"))
				{
					ImageIcon hola = new ImageIcon("./data/imagenes/3.jpg");
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
				if(!estados[pos][g].equals("cerradura fuera de linea"))
				{
					ImageIcon hola = new ImageIcon("./data/imagenes/3.jpg");
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
				if(!estados[pos][g].equals("hub fuera de linea"))
				{
					ImageIcon hola = new ImageIcon("./data/imagenes/3.jpg");
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
		for(int pos = 0; pos < 5; pos++)
		{    
			for(int g = 0; g < 3; g++)
			{
				if(estados[pos][g].equals("normal"))
				{
					ImageIcon hola = new ImageIcon("./data/imagenes/3.jpg");
					Image holo = hola.getImage( );
					Image newimg = holo.getScaledInstance(200, 100,java.awt.Image.SCALE_SMOOTH);
					hola = new ImageIcon(newimg);
					botones[pos][g].setIcon( hola );
				}
				else if(estados[pos][g].equals("puerta abierta"))
				{
					ImageIcon hola = new ImageIcon("./data/imagenes/3abierta.jpg");
					Image holo = hola.getImage( );
					Image newimg = holo.getScaledInstance(200, 100,java.awt.Image.SCALE_SMOOTH);
					hola = new ImageIcon(newimg);
					botones[pos][g].setIcon( hola );
				}
				if(estados[pos][g].equals("apertura no permitida"))
				{
					ImageIcon hola = new ImageIcon("./data/imagenes/3apertura.jpg");
					Image holo = hola.getImage( );
					Image newimg = holo.getScaledInstance(200, 100,java.awt.Image.SCALE_SMOOTH);
					hola = new ImageIcon(newimg);
					botones[pos][g].setIcon( hola );
				}
				if(estados[pos][g].equals("apertura sospechosa"))
				{
					ImageIcon hola = new ImageIcon("./data/imagenes/3sospechosa.jpg");
					Image holo = hola.getImage( );
					Image newimg = holo.getScaledInstance(200, 100,java.awt.Image.SCALE_SMOOTH);
					hola = new ImageIcon(newimg);
					botones[pos][g].setIcon( hola );
				}
				if(estados[pos][g].equals("bateria critica"))
				{
					ImageIcon hola = new ImageIcon("./data/imagenes/3critica.jpg");
					Image holo = hola.getImage( );
					Image newimg = holo.getScaledInstance(200, 100,java.awt.Image.SCALE_SMOOTH);
					hola = new ImageIcon(newimg);
					botones[pos][g].setIcon( hola );
				}
				if(estados[pos][g].equals("cerradura fuera de linea"))
				{
					ImageIcon hola = new ImageIcon("./data/imagenes/3cerradura.jpg");
					Image holo = hola.getImage( );
					Image newimg = holo.getScaledInstance(200, 100,java.awt.Image.SCALE_SMOOTH);
					hola = new ImageIcon(newimg);
					botones[pos][g].setIcon( hola );
				}
				if(estados[pos][g].equals("hub fuera de linea"))
				{
					ImageIcon hola = new ImageIcon("./data/imagenes/3hub.jpg");
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
				ImageIcon hola = new ImageIcon("./data/imagenes/3.jpg");
				Image holo = hola.getImage( );
				Image newimg = holo.getScaledInstance(200, 100,java.awt.Image.SCALE_SMOOTH);
				hola = new ImageIcon(newimg);
				botones[pos][g].setIcon( hola );
				estados[pos][g] = "normal";
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		String comando = e.getActionCommand();
		
		ArrayList<String> arreglo = padre.pedirInfoApto(Integer.parseInt(comando));
		
		
		String mensaje = "propietarios del apartamento "+ e + ":";
		for(String nombre: arreglo)
		{
			mensaje += "/n" + nombre;
		}
		
		JOptionPane.showMessageDialog(null, mensaje);
	}
}
