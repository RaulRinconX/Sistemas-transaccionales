/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Universidad	de	los	Andes	(Bogotá	- Colombia)
 * Departamento	de	Ingeniería	de	Sistemas	y	Computación
 * Licenciado	bajo	el	esquema	Academic Free License versión 2.1
 * 		
 * Curso: isis2304 - Sistemas Transaccionales
 * Proyecto: Parranderos Uniandes
 * @version 1.0
 * @author Germán Bravo
 * Julio de 2018
 * 
 * Revisado por: Claudia Jiménez, Christian Ariza
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */

package uniandes.isis2304.parranderos.interfazApp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

import javax.jdo.JDODataStoreException;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import oracle.net.aso.t;
import uniandes.isis2304.parranderos.negocio.AlohAndes;
import uniandes.isis2304.parranderos.negocio.AlojamientosPopulares;
import uniandes.isis2304.parranderos.negocio.Cliente;
import uniandes.isis2304.parranderos.negocio.ClientesFrecuentes;
import uniandes.isis2304.parranderos.negocio.GananciaProveedor;
import uniandes.isis2304.parranderos.negocio.Oferta;
import uniandes.isis2304.parranderos.negocio.UsoAlohandes;
import uniandes.isis2304.parranderos.negocio.UsoUsuario;
import uniandes.isis2304.parranderos.negocio.VOAdicional;
import uniandes.isis2304.parranderos.negocio.VOCliente;
import uniandes.isis2304.parranderos.negocio.VOContrato;
import uniandes.isis2304.parranderos.negocio.VOOferta;
import uniandes.isis2304.parranderos.negocio.VOOfertaApartamento;
import uniandes.isis2304.parranderos.negocio.VOOfertaEsporadica;
import uniandes.isis2304.parranderos.negocio.VOOfertaHabitacionDiaria;
import uniandes.isis2304.parranderos.negocio.VOOfertaHabitacionMensual;
import uniandes.isis2304.parranderos.negocio.VOOfertaViviendaUniversitaria;
import uniandes.isis2304.parranderos.negocio.VOPersonaJuridica;
import uniandes.isis2304.parranderos.negocio.VOPersonaNatural;
import uniandes.isis2304.parranderos.negocio.VOReserva;
import uniandes.isis2304.parranderos.negocio.VOUsuarios;

/**
 * Clase principal de la interfaz
 * @author Germán Bravo
 */
@SuppressWarnings("serial")

public class InterfazAlohAndesApp extends JFrame implements ActionListener
{
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Logger para escribir la traza de la ejecución
	 */
	private static Logger log = Logger.getLogger(InterfazAlohAndesApp.class.getName());
	
	/**
	 * Ruta al archivo de configuración de la interfaz
	 */
	private static final String CONFIG_INTERFAZ = "./src/main/resources/config/interfaceConfigApp.json"; 
	
	/**
	 * Ruta al archivo de configuración de los nombres de tablas de la base de datos
	 */
	private static final String CONFIG_TABLAS = "./src/main/resources/config/TablasBD_A.json"; 
	
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
    /**
     * Objeto JSON con los nombres de las tablas de la base de datos que se quieren utilizar
     */
    private JsonObject tableConfig;
    
    /**
     * AsociaciÃ³n a la clase principal del negocio.
     */
    private AlohAndes alohAndes;
    
    
	/* ****************************************************************
	 * 			Atributos de interfaz
	 *****************************************************************/
    /**
     * Objeto JSON con la configuración de interfaz de la app.
     */
    private JsonObject guiConfig;
    
    /**
     * Panel de despliegue de interacción para los requerimientos
     */
    private PanelDatos panelDatos;
    
    /**
     * Menú de la aplicación
     */
    private JMenuBar menuBar;

	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
    /**
     * Construye la ventana principal de la aplicación. <br>
     * <b>post:</b> Todos los componentes de la interfaz fueron inicializados.
     */
    public InterfazAlohAndesApp( )
    {
        // Carga la configuración de la interfaz desde un archivo JSON
        guiConfig = openConfig ("Interfaz", CONFIG_INTERFAZ);
        
        // Configura la apariencia del frame que contiene la interfaz gráfica
        configurarFrame ( );
        if (guiConfig != null) 	   
        {
     	   crearMenu( guiConfig.getAsJsonArray("menuBar") );
        }
        
        tableConfig = openConfig ("Tablas BD", CONFIG_TABLAS);
        alohAndes = new AlohAndes (tableConfig);
        
    	String path = guiConfig.get("bannerPath").getAsString();
        panelDatos = new PanelDatos ( );

        setLayout (new BorderLayout());
        add (new JLabel (new ImageIcon (path)), BorderLayout.NORTH );          
        add( panelDatos, BorderLayout.CENTER );        
    }
    
	/* ****************************************************************
	 * 			Métodos de configuración de la interfaz
	 *****************************************************************/
    /**
     * Lee datos de configuración para la aplicació, a partir de un archivo JSON o con valores por defecto si hay errores.
     * @param tipo - El tipo de configuración deseada
     * @param archConfig - Archivo Json que contiene la configuración
     * @return Un objeto JSON con la configuración del tipo especificado
     * 			NULL si hay un error en el archivo.
     */
    private JsonObject openConfig (String tipo, String archConfig)
    {
    	JsonObject config = null;
		try 
		{
			Gson gson = new Gson( );
			FileReader file = new FileReader (archConfig);
			JsonReader reader = new JsonReader ( file );
			config = gson.fromJson(reader, JsonObject.class);
			log.info ("Se encontró un archivo de configuración válido: " + tipo);
		} 
		catch (Exception e)
		{
//			e.printStackTrace ();
			log.info ("NO se encontró un archivo de configuración válido");			
			JOptionPane.showMessageDialog(null, "No se encontró un archivo de configuración de interfaz válido: " + tipo, "Parranderos App", JOptionPane.ERROR_MESSAGE);
		}	
        return config;
    }
    
    /**
     * Método para configurar el frame principal de la aplicación
     */
    private void configurarFrame(  )
    {
    	int alto = 0;
    	int ancho = 0;
    	String titulo = "";	
    	
    	if ( guiConfig == null )
    	{
    		log.info ( "Se aplica configuración por defecto" );			
			titulo = "Parranderos APP Default";
			alto = 300;
			ancho = 500;
    	}
    	else
    	{
			log.info ( "Se aplica configuración indicada en el archivo de configuración" );
    		titulo = guiConfig.get("title").getAsString();
			alto= guiConfig.get("frameH").getAsInt();
			ancho = guiConfig.get("frameW").getAsInt();
    	}
    	
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setLocation (50,50);
        setResizable( true );
        setBackground( Color.WHITE );

        setTitle( titulo );
		setSize ( ancho, alto);        
    }

    /**
     * Método para crear el menú de la aplicación con base em el objeto JSON leído
     * Genera una barra de menú y los menús con sus respectivas opciones
     * @param jsonMenu - Arreglo Json con los menùs deseados
     */
    private void crearMenu(  JsonArray jsonMenu )
    {    	
    	// Creación de la barra de menús
        menuBar = new JMenuBar();       
        for (JsonElement men : jsonMenu)
        {
        	// Creación de cada uno de los menús
        	JsonObject jom = men.getAsJsonObject(); 

        	String menuTitle = jom.get("menuTitle").getAsString();        	
        	JsonArray opciones = jom.getAsJsonArray("options");
        	
        	JMenu menu = new JMenu( menuTitle);
        	
        	for (JsonElement op : opciones)
        	{       	
        		// Creación de cada una de las opciones del menú
        		JsonObject jo = op.getAsJsonObject(); 
        		String lb =   jo.get("label").getAsString();
        		String event = jo.get("event").getAsString();
        		
        		JMenuItem mItem = new JMenuItem( lb );
        		mItem.addActionListener( this );
        		mItem.setActionCommand(event);
        		
        		menu.add(mItem);
        	}       
        	menuBar.add( menu );
        }        
        setJMenuBar ( menuBar );	
    }
    
	/* ****************************************************************
	 * 			CRUD de la app
	 *****************************************************************/
    
    
    public void adicionarCliente( )
    {
    	try 
    	{
    		String documento = JOptionPane.showInputDialog (this, "Numero de documento del cliente?", "Adicionar Cliente", JOptionPane.QUESTION_MESSAGE);
    		Long numDoc = Long.parseLong(documento);
    		

			String[] tipos = {"CC", "TI", "CE"};
			JComboBox<String> combo = new JComboBox<String>(tipos);
			JOptionPane.showMessageDialog(null, combo, "Seleccione el tipo de documento", JOptionPane.QUESTION_MESSAGE);
    		String tipoDocCliente = (String) combo.getSelectedItem();
    		
			String nombre = JOptionPane.showInputDialog (this, "Nombre  del cliente?", "Adicionar Cliente", JOptionPane.QUESTION_MESSAGE);
    		
			String nacionalidad = JOptionPane.showInputDialog (this, "Nacionalidad del cliente?", "Adicionar Cliente", JOptionPane.QUESTION_MESSAGE);
    		
			String tipo = JOptionPane.showInputDialog (this, "Tipo de cliente? (ESTUDIANTE,PROFESOR,EMPLEADO,PADRE ESTUDIANTE,PROFESOR INVITADO,REGISTRADO EVENTO)", "Adicionar Cliente", JOptionPane.QUESTION_MESSAGE);
    		
			String username = JOptionPane.showInputDialog (this, "Nombre de usuario del cliente?", "Adicionar Cliente", JOptionPane.QUESTION_MESSAGE);
    		
			String contrasena = JOptionPane.showInputDialog (this, "Clave del cliente?", "Adicionar Cliente", JOptionPane.QUESTION_MESSAGE);
    		
			if (numDoc != null && tipoDocCliente != null && nombre != null  && nacionalidad != null && tipo != null 
    				&& username != null && contrasena != null)
    		{
        		VOCliente c = alohAndes.adicionarCliente(numDoc, tipoDocCliente, nombre, nacionalidad, tipo, username, contrasena);
        		System.out.println(c);
        		if (c == null)
        		{
        			throw new Exception ("No se pudo crear un Cliente de documento: " + documento);
        		}
        		String resultado = "En adicionarCliente\n\n";
        		resultado += "Cliente adicionado exitosamente: " + c;
    			resultado += "\n Operacion terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operacion cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    
    public void adicionarOferta( )
    {
    	try 
    	{
    		Long id = Long.parseLong(JOptionPane.showInputDialog (this, "Id de la oferta?", "Adicionar Oferta", JOptionPane.QUESTION_MESSAGE));
    		
			Long id_operador = Long.parseLong(JOptionPane.showInputDialog (this, "Id proveedor?", "Adicionar Oferta", JOptionPane.QUESTION_MESSAGE));

    		String tipoOferta = JOptionPane.showInputDialog (this, "Tipo de la oferta (VIVIENDA UNVERSITARIA, HABITACION DIARIA, "
    				+ "HABITACION MENSUAL, ESPORADICA O APARTAMENTO)?", "Adicionar Oferta", JOptionPane.QUESTION_MESSAGE);
    		
    		Boolean disponible = Boolean.parseBoolean(JOptionPane.showInputDialog(this, "¿Está disponible? (true/false)","Adicionar Oferta", JOptionPane.QUESTION_MESSAGE));
    		
    		Integer precio = Integer.parseInt(JOptionPane.showInputDialog(this,"Ingrese el precio","Adicionar Oferta",JOptionPane.QUESTION_MESSAGE));

			String fechaInicio = JOptionPane.showInputDialog (this, "Ingrese fecha de activado de oferta", "Adicionar oferta", JOptionPane.QUESTION_MESSAGE);

    		if (id != null && tipoOferta != null)
    		{
        		Oferta o = alohAndes.adicionarOferta(id, id_operador, tipoOferta, disponible, precio, fechaInicio);

        		if(tipoOferta.equalsIgnoreCase("vivienda universitaria"))
        			adicionarOfertaViviendaUniversitaria(id, disponible, precio);
        		
        		else if(tipoOferta.equalsIgnoreCase("HABITACION DIARIA"))
        			adicionarOfertaHabitacionDiaria(id, disponible, precio);
        		
        		else if(tipoOferta.equalsIgnoreCase("HABITACION MENSUAL"))
        			adicionarOfertaHabitacionMensual(id, disponible, precio);
        		
        		else if(tipoOferta.equalsIgnoreCase("ESPORADICA"))
        			adicionarOfertaEsporadica(id, disponible, precio);
        		
        		else if(tipoOferta.equalsIgnoreCase("APARTAMENTO"))
        			adicionarOfertaApartamento(id, disponible, precio);
        		
        		
        		if (o == null)
        		{
        			throw new Exception ("No se pudo crear un oferta de id: " + id);
        		}
        		String resultado = "En adicionarOferta\n\n";
        		resultado += "Oferta adicionado exitosamente: " + o;
    			resultado += "\n Operacion terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    
    public void adicionarAdicional()
    {
    	try 
    	{
			Long id_oferta = Long.parseLong(JOptionPane.showInputDialog (this, "Ingrese el id de la oferta", "Adicionar servicio adicional", JOptionPane.QUESTION_MESSAGE));
			String nombre= JOptionPane.showInputDialog (this, "Ingrese el nombre del servicio ", "Adicionar servicio adicional", JOptionPane.QUESTION_MESSAGE);
			Integer precio= Integer.parseInt(JOptionPane.showInputDialog (this, "Ingrese el precio del servicio ", "Adicionar servicio adicional", JOptionPane.QUESTION_MESSAGE));
			
			if(id_oferta!=null && nombre!=null && precio!=null)
			{
				VOAdicional adicional= alohAndes.adicionarAdicional(id_oferta, nombre, precio);
				
				if(adicional==null)
					throw new Exception("No se pudo crear el adicional asociado a la oferta:" +Long.toString(id_oferta)+ ", con nombre:"+nombre);
				
				String resultado = "En adicionarAdicional\n\n";
        		resultado += "Adicional adicionada exitosamente: " + adicional;
    			resultado += "\n Operacion terminada";
    			panelDatos.actualizarInterfaz(resultado);
			}
			else
				panelDatos.actualizarInterfaz("Operacion cancelada por el usuario");
		}
    	catch (Exception e) {
			
		}
    }
    
    public void adicionarPersonaJuridica( )
    {
    	try 
    	{
    		String nit = JOptionPane.showInputDialog (this, "Numero de nit de la empresa?", "Adicionar operador", JOptionPane.QUESTION_MESSAGE);
    		Long numNit = Long.parseLong(nit);
    		
    		String nombre = JOptionPane.showInputDialog (this, "nombre? ", "Adicionar operador", JOptionPane.QUESTION_MESSAGE);
    		String tipo = JOptionPane.showInputDialog (this, "tipo?", "Adicionar operador", JOptionPane.QUESTION_MESSAGE);
    		String horaApertura = JOptionPane.showInputDialog (this, "hora apertura?", "Adicionar operador", JOptionPane.QUESTION_MESSAGE);
    		String horaCierre = JOptionPane.showInputDialog (this, "hora cierre?", "Adicionar operador", JOptionPane.QUESTION_MESSAGE);
    		String username = JOptionPane.showInputDialog (this, "Username del operador?", "Adicionar operador", JOptionPane.QUESTION_MESSAGE);
    		String contrasena = JOptionPane.showInputDialog (this, "Contrasena del operador?", "Adicionar operador", JOptionPane.QUESTION_MESSAGE);
    		if (numNit != null && nombre != null && tipo != null  && horaApertura != null && horaCierre != null 
    				&& username != null && contrasena != null)
    		{

        		VOPersonaJuridica pj = alohAndes.adicionarPersonaJuridica(numNit, nombre, tipo, horaApertura, horaCierre, username, contrasena);
        		System.out.println(pj);
    			if (pj == null)
        		{
        			throw new Exception ("No se pudo crear un PJ de documento: " + numNit);
        		}
        		String resultado = "En adicionarCliente\n\n";
        		resultado += "Cliente adicionado exitosamente: " + pj;
    			resultado += "\n Operacion terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operacion cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    
    public void adicionarPersonaNatural( )
    {
    	try 
    	{
    		String documento = JOptionPane.showInputDialog (this, "Numero de documento del operador?", "Adicionar operador", JOptionPane.QUESTION_MESSAGE);
    		Long numDoc = Long.parseLong(documento);
    		
    		String tipoDocumento = JOptionPane.showInputDialog (this, "Tipo de documento del operador? (CC,CE,TI o PA)", "Adicionar operador", JOptionPane.QUESTION_MESSAGE);
    		String nombre = JOptionPane.showInputDialog (this, "Nombre  del cliente?", "Adicionar operador", JOptionPane.QUESTION_MESSAGE);
    		String nacionalidad = JOptionPane.showInputDialog (this, "Nacionalidad del operador?", "Adicionar operador", JOptionPane.QUESTION_MESSAGE);
    		String tipo = JOptionPane.showInputDialog (this, "Tipo de operador? UNIANDINO/VECINO", "Adicionar operador", JOptionPane.QUESTION_MESSAGE);
    		String username = JOptionPane.showInputDialog (this, "Username del operador?", "Adicionar operador", JOptionPane.QUESTION_MESSAGE);
    		String contrasena = JOptionPane.showInputDialog (this, "Contrasena del operador?", "Adicionar operador", JOptionPane.QUESTION_MESSAGE);
    		if (numDoc != null && tipoDocumento != null && nombre != null  && nacionalidad != null && tipo != null 
    				&& username != null && contrasena != null)
    		{
        		VOPersonaNatural pN = alohAndes.adicionarPersonaNatural(numDoc, tipoDocumento, nombre, nacionalidad, tipo, username, contrasena);
        		System.out.println(pN);
        		if (pN == null)
        		{
        			throw new Exception ("No se pudo crear un operador de documento: " + documento);
        		}
        		String resultado = "En adicionarCliente\n\n";
        		resultado += "Cliente adicionado exitosamente: " + pN;
    			resultado += "\n Operacion terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("OperaciÃ³n cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    public void adicionarOperador()
    {
    	try {
    		
    		String res = JOptionPane.showInputDialog (this, "Qué tipo de operador desea agregar? (persona natural o persona juridica)", "Adicionar operador", JOptionPane.QUESTION_MESSAGE);

    		if(res.equalsIgnoreCase("Persona Juridica")){
    			
    			adicionarPersonaJuridica( );
    		}
    		else if (res.equalsIgnoreCase("Persona Natural")){
    			adicionarPersonaNatural( );
    		}
    		else{
    			throw new Exception("deberia ser o persona juridica o persona natural");
    		}

			
		} catch (Exception e) {
			// TODO: handle exception
		}
    }
    
    
    private void adicionarOfertaHabitacionDiaria(Long id, Boolean disponible, Integer precio)
	{
		try 
		{
			String compartida =JOptionPane.showInputDialog (this, "¿La habitación es compartida? (si,no)", "Adicionar Oferta", JOptionPane.QUESTION_MESSAGE);
			Boolean esCompartida=null;
			if(compartida.equalsIgnoreCase("si"))
				esCompartida=true;
			else if (compartida.equalsIgnoreCase("no"))
				esCompartida=false;

			String ubicacion= JOptionPane.showInputDialog (this, "¿Cuál es la ubicación?", "Adicionar Oferta", JOptionPane.QUESTION_MESSAGE);

			String id_operador= JOptionPane.showInputDialog (this, "¿Cuál es el id del operador?", "Adicionar Oferta", JOptionPane.QUESTION_MESSAGE);

			if (id!=null && esCompartida!=null && precio!=null && ubicacion!=null && id_operador!=null)
			{
				VOOfertaHabitacionDiaria c = alohAndes.adicionarOfertaHabitacionDiaria(id, "HABITACION DIARIA", disponible, precio, esCompartida, ubicacion, id_operador);
				System.out.println(c);
				if (c == null)
				{
					throw new Exception ("No se pudo crear una ofertaHabitaciónDiaria de id: " + id);
				}
				String resultado = "En adicionarofertaHabitacionDiaria\n\n";
				resultado += "OfertaHabitacionDiaria adicionada exitosamente: " + c;
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
			}
			else
			{
				panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
			}
		} 
		catch (Exception e) 
		{
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	private void adicionarOfertaViviendaUniversitaria(Long id, Boolean disponible, Integer precio)
	{
		try 
		{
			Integer capacidad= Integer.parseInt(JOptionPane.showInputDialog (this, "¿Cuál es la capacidad?", "Adicionar Oferta", JOptionPane.QUESTION_MESSAGE));
			
			String duracion=JOptionPane.showInputDialog (this, "¿Cual es la duración de la oferta?", "Adicionar Oferta", JOptionPane.QUESTION_MESSAGE);
			
			String compartida =JOptionPane.showInputDialog (this, "¿La vivienda es compartida? (si,no)", "Adicionar Oferta", JOptionPane.QUESTION_MESSAGE);
			Boolean esCompartida=null;
			if(compartida.equalsIgnoreCase("si"))
				esCompartida=true;
			else if(compartida.equalsIgnoreCase("no"))
				esCompartida=false;

			String id_operador= JOptionPane.showInputDialog (this, "¿Cuál es el id del operador?", "Adicionar Oferta", JOptionPane.QUESTION_MESSAGE);
			
			if (id!=null && capacidad != null && duracion!=null && esCompartida!=null && precio!= null 
					&& id_operador != null)
			{
				VOOfertaViviendaUniversitaria c = alohAndes.adicionarOfertaViviendaUniversitaria(id, "VIVIENDA UNIVERSITARIA", disponible, precio, capacidad, duracion, esCompartida, id_operador);
				System.out.println(c);
				if (c == null)
				{
					throw new Exception ("No se pudo crear una ofertaViviendaUniversitaria con id: " + id);
				}
				String resultado = "En adicionar OfertaViviendaUniversitaria\n\n";
				resultado += "OfertaViviendaUniversitaria adicionado exitosamente: " + c;
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
			}
			else
			{
				panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
			}
		} 
		catch (Exception e) 
		{
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	
	private void adicionarOfertaEsporadica(Long id, Boolean disponible, Integer precio )
	{
		try 
		{
			Integer duracion= Integer.parseInt(JOptionPane.showInputDialog (this, "¿Cuantos dias durara la oferta?", "Adicionar Oferta", JOptionPane.QUESTION_MESSAGE));
			
			String descripcion=JOptionPane.showInputDialog (this, "Agregue un descripción", "Adicionar Oferta", JOptionPane.QUESTION_MESSAGE);
			
			String descripcion_seguro =JOptionPane.showInputDialog (this, "Describa el seguro", "Adicionar Oferta", JOptionPane.QUESTION_MESSAGE);
			
			Integer num_habitaciones= Integer.parseInt(JOptionPane.showInputDialog (this, "¿Cuantas habitaciones tiene el inmueble?", "Adicionar Oferta", JOptionPane.QUESTION_MESSAGE));

			String ubicacion=JOptionPane.showInputDialog (this, "Agregue la ubicación.", "Adicionar Oferta", JOptionPane.QUESTION_MESSAGE);
			
			if (id != null && duracion != null && descripcion != null  && descripcion_seguro != null && num_habitaciones != null 
					&& ubicacion != null && precio!= null )
			{
				VOOfertaEsporadica c = alohAndes.adicionarOfertaEsporadica(id, "ESPORADICA", disponible, precio, duracion, descripcion, descripcion_seguro, num_habitaciones, ubicacion);
				System.out.println(c);
				if (c == null)
				{
					throw new Exception ("No se pudo crear la ofertaEsporadica con id: " + id);
				}
				String resultado = "En adicionar OfertaEsporadica\n\n";
				resultado += "OfertaEsporadica adicionada exitosamente: " + c;
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
			}
			else
			{
				panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
			}
		} 
		catch (Exception e) 
		{
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}
    
    
    public void adicionarReserva( )
    {
    	try 
    	{
    		String R = JOptionPane.showInputDialog (this, "Numero de la reserva?", "Adicionar Reserva", JOptionPane.QUESTION_MESSAGE);
    		Long numR = Long.parseLong(R);
    		
    		String fechaInicio = JOptionPane.showInputDialog (this, "fecha de inicio?", "Adicionar Reserva", JOptionPane.QUESTION_MESSAGE);
    		String fechaFin = JOptionPane.showInputDialog (this, "fecha final?", "Adicionar Reserva", JOptionPane.QUESTION_MESSAGE);
    		String idO = JOptionPane.showInputDialog (this, "Id de la oferta?", "Adicionar Reserva", JOptionPane.QUESTION_MESSAGE);
    		Long idOferta = Long.parseLong(idO);
    		String doc = JOptionPane.showInputDialog (this, "documento del cliente?", "Adicionar Reserva", JOptionPane.QUESTION_MESSAGE);
    		Long docCliente = Long.parseLong(doc);
    		String tipoDoc = JOptionPane.showInputDialog (this, "Tipo de documento del cliente? (CC,CE,TI o PA)", "Adicionar Reserva", JOptionPane.QUESTION_MESSAGE);
    		String fechaCancelacion = JOptionPane.showInputDialog (this, "Fecha de cancelacion oportuna?", "Adicionar Reserva", JOptionPane.QUESTION_MESSAGE);
    		if (R != null && fechaInicio != null && fechaFin != null  && idO != null && doc != null 
    				&& tipoDoc != null && fechaCancelacion != null)
    		{
        		VOReserva c = alohAndes.adicionarReserva(numR, fechaInicio, fechaFin, idOferta, docCliente, tipoDoc, fechaCancelacion);
        		System.out.println(c);
        		if (c == null)
        		{
        			throw new Exception ("No se pudo crear la reserva: " + numR);
        		}
        		String resultado = "En adicionarReserva\n\n";
        		resultado += "Reserva creada exitosamente: " + c;
    			resultado += "\n Operacion terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operacion cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    
    public void eliminarReservaPorId( )
    {
    	try 
    	{
    		String id = JOptionPane.showInputDialog (this, "Id de la reserva?", "Borrar reserva por Id", JOptionPane.QUESTION_MESSAGE);
    		Long lId =Long.parseLong(id);
    		String id2 = JOptionPane.showInputDialog (this, "Id de la oferta para esta reserva?", "Borrar reserva por Id", JOptionPane.QUESTION_MESSAGE);
    		Long lId2 =Long.parseLong(id2);
    		if (id != null)
    		{
    			long tbEliminados = alohAndes.eliminarReserva(lId,lId2);

    			String resultado = "En eliminar reserva\n\n";
    			resultado += tbEliminados + " Reserva eliminada\n";
    			resultado += "\n Operacion terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }


	public void eliminarAlojamientoPorId( ){
		try 
		{
			String idTipoStr = JOptionPane.showInputDialog (this, "Id del alojamiento?", "Borrar alojamiento por Id", JOptionPane.QUESTION_MESSAGE);
			if (idTipoStr != null)
			{
				long idTipo = Long.valueOf (idTipoStr);
				long tbEliminados = alohAndes.eliminarAlojamiento(idTipo);

				String resultado = "En eliminar oferta de alojamiento\n\n";
				resultado += tbEliminados + " oferta(s) eliminada(s)\n";
				resultado += "\n Operacion terminada";
				panelDatos.actualizarInterfaz(resultado);
			}
			else
			{
				panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
			}
		} 
		catch (Exception e) 
		{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}
    
    public void RF8( )
    {
    	try 
    	{
    		String id = JOptionPane.showInputDialog (this, "numero de la reserva colectiva?", "Borrar reserva por Id", JOptionPane.QUESTION_MESSAGE);
    		Long lId =Long.parseLong(id);

    		if (id != null)
    		{
    			long tbEliminados = alohAndes.eliminarReservaNumReserva(lId);

    			String resultado = "En eliminar TipoBebida\n\n";
    			resultado += tbEliminados + " Reservas eliminadas\n";
    			resultado += "\n Operación terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    
    public void RF9( )
    {
    	try 
    	{
    		String id = JOptionPane.showInputDialog (this, "id de la oferta a deshabilitar?", "cambiar oferta por Id", JOptionPane.QUESTION_MESSAGE);
    		Long lId =Long.parseLong(id);

    		if (id != null)
    		{
    			long tbEliminados = alohAndes.RF9(lId);

    			String resultado = "En cambiar Ofertas\n\n";
    			resultado += tbEliminados + " Reservas cambiadas\n";
    			resultado += "\n Operación terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    
    public void RF10( )
    {
    	try 
    	{
    		String id = JOptionPane.showInputDialog (this, "id de la oferta a habilitar?", "cambiar oferta por Id", JOptionPane.QUESTION_MESSAGE);
    		Long lId =Long.parseLong(id);

    		if (id != null)
    		{
    			long tbEliminados = alohAndes.RF10(lId);

    			String resultado = "En cambiar Ofertas\n\n";
    			resultado += tbEliminados + " Reservas cambiadas\n";
    			resultado += "\n Operación terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    
    
    private void adicionarOfertaApartamento(Long id, Boolean disponible, Integer precio)
    {
    	try 
    	{

    		
    		String cap = JOptionPane.showInputDialog (this, "capacidad? ", "Adicionar oferta apartamento", JOptionPane.QUESTION_MESSAGE);
    		Integer numCap = Integer.parseInt(cap);
    		String descripcion = JOptionPane.showInputDialog (this, "descripcion de la oferta?", "Adicionar oferta apartamento", JOptionPane.QUESTION_MESSAGE);
    		String amoblado = JOptionPane.showInputDialog (this, "es amoblado? (1 si, 0 no)", "Adicionar oferta apartamento", JOptionPane.QUESTION_MESSAGE);
    		Integer amo = Integer.parseInt(amoblado);
    		Boolean esAmo = false;
    		if(amo == 1)
    			esAmo = true;
    		String ubicacion = JOptionPane.showInputDialog (this, "ubicacion?", "Adicionar oferta apartamento", JOptionPane.QUESTION_MESSAGE);
    		String operador = JOptionPane.showInputDialog (this, "documento del operador responsable?", "Adicionar oferta apartamento", JOptionPane.QUESTION_MESSAGE);
    		Long docOperador = Long.parseLong(operador);
    		
    		String tipoDoc = JOptionPane.showInputDialog (this, "Tipo de documento del operador? (CC,CE,TI o PA)", "Adicionar oferta apartamento", JOptionPane.QUESTION_MESSAGE);
    		
    		String contrato= JOptionPane.showInputDialog(this, "¿Cuál es número del contrato?", "Adicionar contrato", JOptionPane.QUESTION_MESSAGE);
    		Long numContrato = null;
    		if(!contrato.equalsIgnoreCase(""))
    			numContrato = Long.parseLong(contrato);
    		
    		if (id != null && cap != null && descripcion != null  && amoblado != null && ubicacion != null 
    				&& operador != null && tipoDoc != null && precio != null)
    		{
    			VOOfertaApartamento oA = alohAndes.adicionarOfertaApartamento(id, "APARTAMENTO", disponible, precio, numCap, descripcion, esAmo, ubicacion, docOperador, tipoDoc, numContrato);
    			if (oA == null)
        		{
        			throw new Exception ("No se pudo crear una oferta apartamento: " + id);
        		}
        		String resultado = "En adicionarOfertaApartamento\n\n";
        		resultado += "oferta apartamento adicionada exitosamente: " + id;
    			resultado += "\n OperaciÃ³n terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("OperaciÃ³n cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
			String resultado = generarMensajeError(e);
			e.printStackTrace();
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    
    private void adicionarOfertaHabitacionMensual(Long id, Boolean disponible, Integer precio)
    {
    	try 
    	{

    		
    		String cap = JOptionPane.showInputDialog (this, "capacidad? ", "Adicionar oferta habitacion mensual", JOptionPane.QUESTION_MESSAGE);
    		Integer numCap = Integer.parseInt(cap);
    		String descripcion = JOptionPane.showInputDialog (this, "descripcion de la oferta?", "Adicionar oferta habitacion mensual", JOptionPane.QUESTION_MESSAGE);

    		String ubicacion = JOptionPane.showInputDialog (this, "ubicacion?", "Adicionar oferta habitacion mensual", JOptionPane.QUESTION_MESSAGE);
    		String operador = JOptionPane.showInputDialog (this, "documento del operador responsable?", "Adicionar oferta apartamento", JOptionPane.QUESTION_MESSAGE);
    		Long docOperador = Long.parseLong(operador);
    		
    		String tipoDoc = JOptionPane.showInputDialog (this, "Tipo de documento del operador? (CC,CE,TI o PA)", "Adicionar oferta apartamento", JOptionPane.QUESTION_MESSAGE);
    		if (id != null && cap != null && descripcion != null && ubicacion != null 
    				&& operador != null && tipoDoc != null && precio != null)
    		{

    			VOOfertaHabitacionMensual oA = alohAndes.adicionarOfertaHabitacionMensual(id, "HABITACION MENSUAL", disponible, precio, numCap, descripcion, ubicacion, docOperador, tipoDoc);
    			if (oA == null)
        		{
        			throw new Exception ("No se pudo crear una oferta habitacion mensual: " + id);
        		}
        		String resultado = "En adicionarCliente\n\n";
        		resultado += "oferta habitacion mensual adicionada exitosamente: " + id;
    			resultado += "\n OperaciÃ³n terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("OperaciÃ³n cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    
    
    public void adicionarContrato()
    {
    	try{
    		Long id= Long.parseLong(JOptionPane.showInputDialog(this, "Ingrese el numero del contrato", "Adicionar Contrato",JOptionPane.QUESTION_MESSAGE));
    		Integer duracion =Integer.parseInt(JOptionPane.showInputDialog(this, "Cuantos meses dura el contrato?", "Adicionar Contrato",JOptionPane.QUESTION_MESSAGE));
    		Long numReserva= Long.parseLong(JOptionPane.showInputDialog(this, "Ingrese el numero de la reserva", "Adicionar Contrato",JOptionPane.QUESTION_MESSAGE));

    		if (id != null && duracion != null && numReserva != null )
    		{

    			VOContrato oA = alohAndes.adicionarContrato(id, duracion, numReserva);
    			if (oA == null)
    			{
    				throw new Exception ("No se pudo crear El contrato con numero: " + id);
    			}
    			String resultado = "En adicionarContrato\n\n";
    			resultado += "Contrato adicionado exitosamente: " + id;
    			resultado += "\n Operacion terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("OperaciÃ³n cancelada por el usuario");
    		}
    	} 
    	catch (Exception e) {
    		String resultado = generarMensajeError(e);
    		panelDatos.actualizarInterfaz(resultado);
    	}

    }
    
    /**
     * Consulta en la base de datos los tipos de bebida existentes y los muestra en el panel de datos de la aplicación
     */
    public void listarOfertas( )
    {
    	try 
    	{
			List <VOOferta> lista = alohAndes.darVOOfertas();

			String resultado = "En listarOfertas";
			resultado +=  "\n" + listarOfertas(lista);
			panelDatos.actualizarInterfaz(resultado);
			resultado += "\n Operacion terminada";
		} 
    	catch (Exception e) 
    	{
    		e.printStackTrace();
			String resultado = generarMensajeError(e);
			
			panelDatos.actualizarInterfaz(resultado);
		}
    }

	public void listarClientes()
	{
		try
		{
			List<VOCliente> lista = alohAndes.darVOClientes();
			String resultado = "En listarClientes";
			resultado +=  "\n" + listarClientes (lista);
			panelDatos.actualizarInterfaz(resultado);
			resultado += "\n Operación terminada";
		}
		catch (Exception e)
		{
			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	private String listarClientes(List<VOCliente> lista) {
		String resp = "Los clientes existentes son: ";
		int i = 1;
		for (VOCliente tb : lista)
		{
			resp += i++ + ". " + tb.toString() + "\n";
		}
		return resp;
	}

    
    public void listarOfertasApartamento( )
    {
    	try 
    	{
			List <VOOfertaApartamento> lista = alohAndes.darVOOfertasApartamento();

			String resultado = "En listarTipoBebida";
			resultado +=  "\n" + listarOfertasApartamento (lista);
			panelDatos.actualizarInterfaz(resultado);
			resultado += "\n Operación terminada";
		} 
    	catch (Exception e) 
    	{
    		e.printStackTrace();
			String resultado = generarMensajeError(e);
			
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    
    public void listarAdicionales( )
    {
    	try 
    	{
			List <VOAdicional> lista = alohAndes.darVOAdicionales();

			String resultado = "En listarAdicionales";
			resultado +=  "\n" + listarAdicionales (lista);
			panelDatos.actualizarInterfaz(resultado);
			resultado += "\n Operacion terminada";
		} 
    	catch (Exception e) 
    	{
    		e.printStackTrace();
			String resultado = generarMensajeError(e);
			
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    
    public void listarReservas( )
    {
    	try 
    	{
			List <VOReserva> lista = alohAndes.darVOReservas();

			String resultado = "En listaReservas";
			resultado +=  "\n" + listarReservas (lista);
			panelDatos.actualizarInterfaz(resultado);
			resultado += "\n Operacion terminada";
		} 
    	catch (Exception e) 
    	{
    		e.printStackTrace();
			String resultado = generarMensajeError(e);
			
			panelDatos.actualizarInterfaz(resultado);
		}
    }

    public void listarUsuarios( )
    {
    	try 
    	{
			List<VOUsuarios> lista = alohAndes.darVOUsuarios();

			String resultado = "En listaUsuarios";
			resultado +=  "\n" + listarUsuarios(lista);
			panelDatos.actualizarInterfaz(resultado);
			resultado += "\n Operacion terminada";
		} 
    	catch (Exception e) 
    	{
    		e.printStackTrace();
			String resultado = generarMensajeError(e);
			
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    
    public void listarReservasOfertaEnFecha( )
    {
    	try 
    	{
    		Long idOferta= Long.parseLong(JOptionPane.showInputDialog(this, "Ingrese el id de la oferta", "Buscar Reservas por Oferta y fecha",JOptionPane.QUESTION_MESSAGE));
    		String fechaI = JOptionPane.showInputDialog(this, "Ingrese la fecha inicial (DD/MM/YYYY)", "Buscar Reservas por Oferta y fecha",JOptionPane.QUESTION_MESSAGE);
    		String fechaF = JOptionPane.showInputDialog(this, "Ingrese la fecha final (DD/MM/YYYY)", "Buscar Reservas por Oferta y fecha",JOptionPane.QUESTION_MESSAGE);
    		
    		List <VOReserva> lista = alohAndes.darVOReservasOfertaEnFecha(idOferta, fechaI, fechaF);

			String resultado = "En listarReserva/oferta en fecha";
			resultado +=  "\n" + listarReservas (lista);
			panelDatos.actualizarInterfaz(resultado);
			resultado += "\n Operacion terminada";
		} 
    	catch (Exception e) 
    	{
    		e.printStackTrace();
			String resultado = generarMensajeError(e);
			
			panelDatos.actualizarInterfaz(resultado);
		}
    }
    
    
    public void RF7()
    {
    	try 
    	{
    		String tipoOferta = JOptionPane.showInputDialog(this, "Ingrese el tipo de oferta(VIVIENDA UNVERSITARIA, HABITACION DIARIA, "
    				+ "HABITACION MENSUAL, ESPORADICA O APARTAMENTO)", "RF7",JOptionPane.QUESTION_MESSAGE);
    		
    		Long numReserva= Long.parseLong(JOptionPane.showInputDialog(this, "Ingrese el numero de la nueva reserva", "RF7",JOptionPane.QUESTION_MESSAGE));
    		String fechaI = JOptionPane.showInputDialog(this, "Ingrese la fecha inicial (DD/MM/YYYY)", "RF7",JOptionPane.QUESTION_MESSAGE);
    		String fechaF = JOptionPane.showInputDialog(this, "Ingrese la fecha final (DD/MM/YYYY)", "RF7",JOptionPane.QUESTION_MESSAGE);
    		Long docCliente= Long.parseLong(JOptionPane.showInputDialog(this, "Ingrese el numero de documento del cliente", "RF7",JOptionPane.QUESTION_MESSAGE));
    		String tipoDoc = JOptionPane.showInputDialog(this, "Ingrese el tipo de documento(CC, CE, TI o PA)", "RF7",JOptionPane.QUESTION_MESSAGE);
    		String fechaCancelacion = JOptionPane.showInputDialog(this, "Ingrese la fecha de cancelacion (DD/MM/YYYY)", "RF7",JOptionPane.QUESTION_MESSAGE);
    		Integer cantidad= Integer.parseInt(JOptionPane.showInputDialog(this, "Ingrese la cantidad de ofertas a solicitar", "RF7",JOptionPane.QUESTION_MESSAGE));
    		String ad = JOptionPane.showInputDialog(this, "Ingrese los adicionales desados separados por una ,", "RF7",JOptionPane.QUESTION_MESSAGE);
    		
    		
    		String[] adicionales = ad.split(",");
    		
    		for(String a: adicionales){
    			System.out.println(a);
    		}


    		List<VOReserva> rf7 = alohAndes.RF7(tipoOferta, numReserva, fechaI, fechaF, docCliente, tipoDoc, fechaCancelacion, cantidad, adicionales);

    		String resultado = "En adicionarReserva\n\n";
    		resultado +=  "\n" + listarReservas (rf7);
			resultado += "\n Operacion terminada";
			panelDatos.actualizarInterfaz(resultado);
		} 
    	catch (Exception e) 
    	{
    		e.printStackTrace();
			String resultado = generarMensajeError(e);
			
			panelDatos.actualizarInterfaz(resultado);
		}
    	
    	
    }
    /**
     * Genera una cadena de caracteres con la lista de los tipos de bebida recibida: una línea por cada tipo de bebida
     * @param lista - La lista con los tipos de bebida
     * @return La cadena con una líea para cada tipo de bebida recibido
     */
    private String listarOfertas(List<VOOferta> lista) 
    {
    	String resp = "Las ofertas son:\n";
    	int i = 1;
        for (VOOferta tb : lista)
        {
        	resp += i++ + ". " + tb.toString() + "\n";
        }
        return resp;
	}

	private String listarUsuarios(List<VOUsuarios> lista)
	{
		String resp = "Los usuarios son:\n";
		int i = 1;
		for (VOUsuarios tb : lista)
		{
			resp += i++ + ". " + tb.toString() + "\n";
		}
		return resp;
	}

    
    private String listarOfertasApartamento(List<VOOfertaApartamento> lista) 
    {
    	String resp = "Los tipos de bebida existentes son:\n";
    	int i = 1;
        for (VOOfertaApartamento tb : lista)
        {
        	resp += i++ + ". " + tb.toString() + "\n";
        }
        return resp;
	}
    
    private String listarAdicionales(List<VOAdicional> lista) 
    {
    	String resp = "Los adicionales son:\n";
    	int i = 1;
        for (VOAdicional tb : lista)
        {
        	resp += i++ + ". " + tb.toString() + "\n";
        }
        return resp;
	}
    
    private String listarReservas(List<VOReserva> lista) 
    {
    	String resp = "Las reservas son:\n";
    	int i = 1;
        for (VOReserva tb : lista)
        {
        	resp += i++ + ". " + tb.toString() + "\n";
        }
        return resp;
	}
    /**
     * Busca el tipo de bebida con el nombre indicado por el usuario y lo muestra en el panel de datos
     */
    public void buscarOfertaPorId( )
    {
    	try 
    	{
    		String idOferta = JOptionPane.showInputDialog (this, "id de la oferta?", "Buscar ofera por Id", JOptionPane.QUESTION_MESSAGE);
    		Long id = Long.parseLong(idOferta);
    		if (idOferta != null)
    		{
    			VOOferta oferta = alohAndes.darOfertaPorId(id);
    			String resultado = "En buscar oferta por id\n\n";
    			if (oferta != null)
    			{
        			resultado += "La oferta es: " + oferta;
    			}
    			else
    			{
        			resultado += "Una oferta con: " + idOferta + " NO EXISTE\n";    				
    			}
    			resultado += "\n Operacion terminada";
    			panelDatos.actualizarInterfaz(resultado);
    		}
    		else
    		{
    			panelDatos.actualizarInterfaz("Operacion cancelada por el usuario");
    		}
		} 
    	catch (Exception e) 
    	{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			e.printStackTrace();
			panelDatos.actualizarInterfaz(resultado);
		}
    }

   // RFC1
  	public void listarDineroPorProveedor( ){
	try 
	{
		 List<GananciaProveedor> lista = alohAndes.gananciaProveedores();

		String resultado = "En listarDineroPorProveedor";
		resultado +=  "\n" + listarDineroPorProveedor (lista);
		panelDatos.actualizarInterfaz(resultado);
		resultado += "\n Operación terminada";
	} 
	catch (Exception e) 
	{ 
//			e.printStackTrace();
		String resultado = generarMensajeError(e);
		panelDatos.actualizarInterfaz(resultado);
	}
	}
	private String listarDineroPorProveedor(List<GananciaProveedor> lista) {
	String resp = "Los proveedores con mas ganancias son: \n";
	int i = 1;
	for (GananciaProveedor tb : lista)
	{
		resp += i++ + ". " + tb.toString() + "\n";
	}
	return resp;
	}

	public void listarOfertasPopulares( )
	{
		try 
		{
			List <AlojamientosPopulares> lista = alohAndes.alojamientosPopulares();

			String resultado = "En listarOfertasPopulares";
			resultado +=  "\n" + listarOfertasPopulares (lista);
			panelDatos.actualizarInterfaz(resultado);
			resultado += "\n Operación terminada";
		} 
		catch (Exception e) 
		{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	private String listarOfertasPopulares(List<AlojamientosPopulares> lista) {
		String resp = "Los alojamientos mas populares son: \n";
		int i = 1;
		for (AlojamientosPopulares tb : lista) 
		{
			resp += i++ + ". " + tb.toString() + "\n";
		}
		return resp;
	}

	public void darClientesFrecuentes( )
	{
		try 
		{
			List <ClientesFrecuentes> lista = alohAndes.darClientesFrecuentes();

			String resultado = "En ListaClientesFrecuentes";
			resultado +=  "\n" + darClientesFrecuentes (lista);
			panelDatos.actualizarInterfaz(resultado);
			resultado += "\n Operación terminada";
		} 
		catch (Exception e) 
		{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	private String darClientesFrecuentes(List<ClientesFrecuentes> lista) {
		String resp = "Los clientes frecuentes son: \n";
		int i = 1;
		for (ClientesFrecuentes tb : lista) 
		{
			resp += i++ + ". " + tb.toString() + "\n";
		}
		return resp;
	}


	public void noDemanda( )
	{
		try 
		{
			List <VOOferta> lista = alohAndes.noDemanda();

			String resultado = "En listarOfertasPopulares";
			resultado +=  "\n" + noDemanda (lista);
			panelDatos.actualizarInterfaz(resultado);
			resultado += "\n Operacion terminada";
		} 
		catch (Exception e) 
		{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	private String noDemanda(List<VOOferta> lista) {
		String resp = "Las ofertas cono menos demanda: \n";
		int i = 1;
		for (VOOferta tb : lista) 
		{
			resp += i++ + ". " + tb.toString() + "\n";
		}
		return resp;
	}

	public void mostrarUsoAlohandes()
	{
		try 
		{
			List <UsoAlohandes> lista = alohAndes.usoAlohandes();

			String resultado = "En listarUsoAlohandes";
			resultado +=  "\n" + mostrarUsoAlohandes (lista);
			panelDatos.actualizarInterfaz(resultado);
			resultado += "\n Operacion terminada";
		} 
		catch (Exception e) 
		{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	private String mostrarUsoAlohandes(List<UsoAlohandes> lista) {
		String resp = "El uso de alohandes es: \n";
		int i = 1;
		for (UsoAlohandes tb : lista) 
		{
			resp += i++ + ". " + tb.toString() + "\n";
		}
		return resp;
	}


	public void mostrarUsoUsuario()
	{
		try 
		{
			
			String id_usuario = JOptionPane.showInputDialog(this, "Ingrese el id del usuario", "RFC6",JOptionPane.QUESTION_MESSAGE);
			List<UsoUsuario> lista = alohAndes.usoUsuario(id_usuario);

			String resultado = "En listarUsoAlohandes";
			resultado +=  "\n" + mostrarUsoUsuario (lista);
			panelDatos.actualizarInterfaz(resultado);
			resultado += "\n Operacion terminada";
		} 
		catch (Exception e) 
		{
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	private String mostrarUsoUsuario(List<UsoUsuario> lista) {
		String resp = "El uso de del usuario de alohandes es: \n";
		int i = 1;
		for (UsoUsuario tb : lista) 
		{
			resp += i++ + ". " + tb.toString() + "\n";
		}
		return resp;
	}
		
	public void RFC4()
	{
		try 
		{
			String servicios = JOptionPane.showInputDialog (this, "Que servicios busca? Escribalos separados por coma", "Buscar ofertas por servicio y fecha", JOptionPane.QUESTION_MESSAGE);
			String fechaLlegadaStr = JOptionPane.showInputDialog (this, "Fecha de inicio en (DD/MM/YYYY)?", "Adicionar reserva", JOptionPane.QUESTION_MESSAGE);
			String fechaSalidaStr = JOptionPane.showInputDialog (this, "Fecha fin en (DD/MM/YYYY)?", "Adicionar reserva", JOptionPane.QUESTION_MESSAGE);

			if (servicios != null && fechaLlegadaStr != null && fechaSalidaStr != null)
			{
				List<VOOferta> lista = alohAndes.RFC4(servicios, fechaLlegadaStr, fechaSalidaStr);
				String resultado = "En buscar ofertas por servicio y fecha\n\n";
				resultado +=  "\n" + listarOfertas (lista);
				panelDatos.actualizarInterfaz(resultado);
				resultado += "\n Operación terminada";
			}
			else
			{
				panelDatos.actualizarInterfaz("Operacion cancelada por el usuario");
			}
		} 
		catch (Exception e) 
		{

			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}

	}
		

   

	/* ****************************************************************
	 * 			Métodos administrativos
	 *****************************************************************/
	/**
	 * Muestra el log de Parranderos
	 */
	public void mostrarLogParranderos ()
	{
		mostrarArchivo ("parranderos.log");
	}
	
	/**
	 * Muestra el log de datanucleus
	 */
	public void mostrarLogDatanuecleus ()
	{
		mostrarArchivo ("datanucleus.log");
	}
	
	/**
	 * Limpia el contenido del log de parranderos
	 * Muestra en el panel de datos la traza de la ejecución
	 */
	public void limpiarLogParranderos ()
	{
		// Ejecución de la operación y recolección de los resultados
		boolean resp = limpiarArchivo ("parranderos.log");

		// Generación de la cadena de caracteres con la traza de la ejecución de la demo
		String resultado = "\n\n************ Limpiando el log de parranderos ************ \n";
		resultado += "Archivo " + (resp ? "limpiado exitosamente" : "NO PUDO ser limpiado !!");
		resultado += "\nLimpieza terminada";

		panelDatos.actualizarInterfaz(resultado);
	}
	
	/**
	 * Limpia el contenido del log de datanucleus
	 * Muestra en el panel de datos la traza de la ejecución
	 */
	public void limpiarLogDatanucleus ()
	{
		// Ejecución de la operación y recolección de los resultados
		boolean resp = limpiarArchivo ("datanucleus.log");

		// Generación de la cadena de caracteres con la traza de la ejecución de la demo
		String resultado = "\n\n************ Limpiando el log de datanucleus ************ \n";
		resultado += "Archivo " + (resp ? "limpiado exitosamente" : "NO PUDO ser limpiado !!");
		resultado += "\nLimpieza terminada";

		panelDatos.actualizarInterfaz(resultado);
	}
	

	
	/**
	 * Muestra la presentación general del proyecto
	 */
	public void mostrarPresentacionGeneral ()
	{
		mostrarArchivo ("data/00-ST-ParranderosJDO.pdf");
	}
	
	/**
	 * Muestra el modelo conceptual de Parranderos
	 */
	public void mostrarModeloConceptual ()
	{
		mostrarArchivo ("data/Class Diagram1.jpg");
	}
	
	/**
	 * Muestra el esquema de la base de datos de Parranderos
	 */
	public void mostrarEsquemaBD ()
	{
		mostrarArchivo ("data/relacional.xlsx");
	}
	
	/**
	 * Muestra el script de creación de la base de datos
	 */
	public void mostrarScriptBD ()
	{
		mostrarArchivo ("data/CREACION BD ORDENADA.sql");
	}
	
	/**
	 * Muestra la arquitectura de referencia para Parranderos
	 */
	public void mostrarArqRef ()
	{
		mostrarArchivo ("data/ArquitecturaReferencia.pdf");
	}
	
	/**
	 * Muestra la documentación Javadoc del proyectp
	 */
	public void mostrarJavadoc ()
	{
		mostrarArchivo ("doc/index.html");
	}
	
	/**
     * Muestra la información acerca del desarrollo de esta apicación
     */
    public void acercaDe ()
    {
		String resultado = "\n\n ************************************\n\n";
		resultado += " * Universidad	de	los	Andes	(Bogotá	- Colombia)\n";
		resultado += " * Departamento	de	Ingeniería	de	Sistemas	y	Computación\n";
		resultado += " * Licenciado	bajo	el	esquema	Academic Free License versión 2.1\n";
		resultado += " * \n";		
		resultado += " * Curso: isis2304 - Sistemas Transaccionales\n";
		resultado += " * Proyecto: AlohAndes Uniandes\n";
		resultado += " * @version 1.0\n";
		resultado += " * @authores Camilo Linares, Raul Rincon\n";
		resultado += " * Mayo de 2023\n";
		resultado += " * \n";
		resultado += "\n ************************************\n\n";

		panelDatos.actualizarInterfaz(resultado);		
    }
    

	
    /**
     * Genera una cadena de caracteres con la descripción de la excepcion e, haciendo énfasis en las excepcionsde JDO
     * @param e - La excepción recibida
     * @return La descripción de la excepción, cuando es javax.jdo.JDODataStoreException, "" de lo contrario
     */
	private String darDetalleException(Exception e) 
	{
		String resp = "";
		if (e.getClass().getName().equals("javax.jdo.JDODataStoreException"))
		{
			JDODataStoreException je = (javax.jdo.JDODataStoreException) e;
			return je.getNestedExceptions() [0].getMessage();
		}
		return resp;
	}

	/**
	 * Genera una cadena para indicar al usuario que hubo un error en la aplicación
	 * @param e - La excepción generada
	 * @return La cadena con la información de la excepción y detalles adicionales
	 */
	private String generarMensajeError(Exception e) 
	{
		String resultado = "************ Error en la ejecución\n";
		resultado += e.getLocalizedMessage() + ", " + darDetalleException(e);
		resultado += "\n\nRevise datanucleus.log y parranderos.log para más detalles";
		return resultado;
	}

	/**
	 * Limpia el contenido de un archivo dado su nombre
	 * @param nombreArchivo - El nombre del archivo que se quiere borrar
	 * @return true si se pudo limpiar
	 */
	private boolean limpiarArchivo(String nombreArchivo) 
	{
		BufferedWriter bw;
		try 
		{
			bw = new BufferedWriter(new FileWriter(new File (nombreArchivo)));
			bw.write ("");
			bw.close ();
			return true;
		} 
		catch (IOException e) 
		{
//			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Abre el archivo dado como parámetro con la aplicación por defecto del sistema
	 * @param nombreArchivo - El nombre del archivo que se quiere mostrar
	 */
	private void mostrarArchivo (String nombreArchivo)
	{
		try
		{
			Desktop.getDesktop().open(new File(nombreArchivo));
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/* ****************************************************************
	 * 			Métodos de la Interacción
	 *****************************************************************/
    /**
     * Método para la ejecución de los eventos que enlazan el menú con los métodos de negocio
     * Invoca al método correspondiente según el evento recibido
     * @param pEvento - El evento del usuario
     */
    @Override
	public void actionPerformed(ActionEvent pEvento)
	{
		String evento = pEvento.getActionCommand( );		
        try 
        {
			Method req = InterfazAlohAndesApp.class.getMethod ( evento );			
			req.invoke ( this );
		} 
        catch (Exception e) 
        {
			e.printStackTrace();
		} 
	}
    
	/* ****************************************************************
	 * 			Programa principal
	 *****************************************************************/
    /**
     * Este método ejecuta la aplicación, creando una nueva interfaz
     * @param args Arreglo de argumentos que se recibe por línea de comandos
     */
    public static void main( String[] args )
    {
        try
        {
        	
            // Unifica la interfaz para Mac y para Windows.
            UIManager.setLookAndFeel( UIManager.getCrossPlatformLookAndFeelClassName( ) );
            InterfazAlohAndesApp interfaz = new InterfazAlohAndesApp( );
            interfaz.setVisible( true );
        }
        catch( Exception e )
        {
            e.printStackTrace( );
        }
    }
}
