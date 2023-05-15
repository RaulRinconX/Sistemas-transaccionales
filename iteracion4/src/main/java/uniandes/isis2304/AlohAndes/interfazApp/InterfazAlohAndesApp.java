package uniandes.isis2304.AlohAndes.interfazApp;

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
import java.sql.Date;
import java.util.List;

import javax.jdo.JDODataStoreException;
import javax.swing.ImageIcon;
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

import uniandes.isis2304.AlohAndes.negocio.*;

@SuppressWarnings("serial")

public class InterfazAlohAndesApp extends JFrame implements ActionListener {
	/*
	 * **************************************************************** Constantes
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

	/*
	 * **************************************************************** Atributos
	 *****************************************************************/
	/**
	 * Objeto JSON con los nombres de las tablas de la base de datos que se quieren
	 * utilizar
	 */
	private JsonObject tableConfig;

	/**
	 * Asociación a la clase principal del negocio.
	 */
	private AlohAndes aloha;

	/*
	 * **************************************************************** Atributos de
	 * interfaz
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

	/*
	 * **************************************************************** Métodos
	 *****************************************************************/
	/**
	 * Construye la ventana principal de la aplicación. <br>
	 * <b>post:</b> Todos los componentes de la interfaz fueron inicializados.
	 */
	public InterfazAlohAndesApp() {
		// Carga la configuración de la interfaz desde un archivo JSON
		guiConfig = openConfig("Interfaz", CONFIG_INTERFAZ);

		// Configura la apariencia del frame que contiene la interfaz gráfica
		configurarFrame();
		if (guiConfig != null) {
			crearMenu(guiConfig.getAsJsonArray("menuBar"));
		}
		tableConfig = openConfig("Tablas BD", CONFIG_TABLAS);
		aloha = new AlohAndes(tableConfig);

		String path = guiConfig.get("bannerPath").getAsString();
		panelDatos = new PanelDatos();

		setLayout(new BorderLayout());
		add(new JLabel(new ImageIcon(path)), BorderLayout.NORTH);
		add(panelDatos, BorderLayout.CENTER);
	}

	/*
	 * **************************************************************** Métodos de
	 * configuración de la interfaz
	 *****************************************************************/
	/**
	 * Lee datos de configuración para la aplicació, a partir de un archivo JSON o
	 * con valores por defecto si hay errores.
	 * 
	 * @param tipo       - El tipo de configuración deseada
	 * @param archConfig - Archivo Json que contiene la configuración
	 * @return Un objeto JSON con la configuración del tipo especificado NULL si hay
	 *         un error en el archivo.
	 */
	private JsonObject openConfig(String tipo, String archConfig) {
		JsonObject config = null;
		try {
			Gson gson = new Gson();
			FileReader file = new FileReader(archConfig);
			JsonReader reader = new JsonReader(file);
			config = gson.fromJson(reader, JsonObject.class);
			log.info("Se encontró un archivo de configuración válido: " + tipo);
		} catch (Exception e) {
			e.printStackTrace ();
			log.info("NO se encontró un archivo de configuración válido");
			JOptionPane.showMessageDialog(null,
					"No se encontró un archivo de configuración de interfaz válido: " + tipo, "AlohAndes App",
					JOptionPane.ERROR_MESSAGE);
		}
		return config;
	}

	/**
	 * Método para configurar el frame principal de la aplicación
	 */
	private void configurarFrame() {
		int alto = 0;
		int ancho = 0;
		String titulo = "";

		if (guiConfig == null) {
			log.info("Se aplica configuración por defecto");
			titulo = "Parranderos APP Default";
			alto = 300;
			ancho = 500;
		} else {
			log.info("Se aplica configuración indicada en el archivo de configuración");
			titulo = guiConfig.get("title").getAsString();
			alto = guiConfig.get("frameH").getAsInt();
			ancho = guiConfig.get("frameW").getAsInt();
		}

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(50, 50);
		setResizable(true);
		setBackground(Color.WHITE);

		setTitle(titulo);
		setSize(ancho, alto);
	}

	/**
	 * Método para crear el menú de la aplicación con base em el objeto JSON leído
	 * Genera una barra de menú y los menús con sus respectivas opciones
	 * 
	 * @param jsonMenu - Arreglo Json con los menùs deseados
	 */
	private void crearMenu(JsonArray jsonMenu) {
		// Creación de la barra de menús
		menuBar = new JMenuBar();
		for (JsonElement men : jsonMenu) {
			// Creación de cada uno de los menús
			JsonObject jom = men.getAsJsonObject();

			String menuTitle = jom.get("menuTitle").getAsString();
			JsonArray opciones = jom.getAsJsonArray("options");

			JMenu menu = new JMenu(menuTitle);

			for (JsonElement op : opciones) {
				// Creación de cada una de las opciones del menú
				JsonObject jo = op.getAsJsonObject();
				String lb = jo.get("label").getAsString();
				String event = jo.get("event").getAsString();

				JMenuItem mItem = new JMenuItem(lb);
				mItem.addActionListener(this);
				mItem.setActionCommand(event);

				menu.add(mItem);
			}
			menuBar.add(menu);
		}
		setJMenuBar(menuBar);
	}

	/* ****************************************************************
	 * Requerimientos de consulta
	 * ****************************************************************
	 */
	/*public void requerimientoC1() {
		try {
			List<VOOfertaComun> lista = aloha.darVOOfertaComun();

			String resultado = "En listarOfertaComun";
			resultado += "\n" + listarOfertaComun(lista);
			panelDatos.actualizarInterfaz(resultado);
			resultado += "\n Operación terminada";
		} catch (Exception e) {
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}*/
	/*
	 * **************************************************************** 
	 * CRUD de OfertaComun
	 *****************************************************************/
	/**
	 * Adiciona una oferta común con la información dada por el usuario Se crea una
	 * nueva tupla de ofertaComun en la base de datos.
	 */
	public void adicionarOfertaComun() {
		try {
			String capacidad = JOptionPane.showInputDialog(this, "Capacidad de la oferta?", "Adicionar oferta",
					JOptionPane.QUESTION_MESSAGE);
			int piscina = JOptionPane.showConfirmDialog(this, "Tiene piscina?");
			int parqueadero = JOptionPane.showConfirmDialog(this, "Tiene parqueadero?");
			int tvCable = JOptionPane.showConfirmDialog(this, "Tiene tvCable?");
			int wifi = JOptionPane.showConfirmDialog(this, "Tiene wifi?");
			String precio = JOptionPane.showInputDialog(this, "Precio de la oferta?", "Adicionar oferta",
					JOptionPane.QUESTION_MESSAGE);
			if (capacidad != null && piscina != 2 && parqueadero != 2 && tvCable != 2 && wifi != 2 && precio != null) {
				boolean pis = false, par = false, tv = false, wi = false;
				if (piscina == 0)
					pis = true;
				if (parqueadero == 0)
					par = true;
				if (tvCable == 0)
					tv = true;
				if (wifi == 0)
					wi = true;
				VOOfertaComun oc = aloha.adicionarOfertaComun(Integer.parseInt(capacidad), pis, par, tv, wi,
						Integer.parseInt(precio));
				if (oc == null) {
					throw new Exception("No se pudo crear una oferta común");
				}
				String resultado = "En adicionarOfertaComun\n\n";
				resultado += "Oferta Común adicionada exitosamente: " + oc;
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
			} else {
				panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
			}
		} catch (Exception e) {
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	/**
	 * Consulta en la base de datos las ofertas comunes existentes y los muestra en
	 * el panel de datos de la aplicación
	 */
	public void listarOfertaComun() {
		try {
			List<VOOfertaComun> lista = aloha.darVOOfertaComun();

			String resultado = "En listarOfertaComun";
			resultado += "\n" + listarOfertaComun(lista);
			panelDatos.actualizarInterfaz(resultado);
			resultado += "\n Operación terminada";
		} catch (Exception e) {
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	/**
	 * Borra de la base de datos la oferta comun con el identificador dado por el
	 * usuario Cuando dicha oferta comun no existe, se indica que se borraron 0
	 * registros de la base de datos
	 */
	public void eliminarOfertaComunPorId() {
		try {
			String idTipoStr = JOptionPane.showInputDialog(this, "Id de la oferta común?", "Borrar oferta común por Id",
					JOptionPane.QUESTION_MESSAGE);
			if (idTipoStr != null) {
				long idTipo = Long.valueOf(idTipoStr);
				long tbEliminados = aloha.eliminarOfertaComunPorId(idTipo);

				String resultado = "En eliminar OfertaComun\n\n";
				resultado += tbEliminados + " Oferta común eliminada\n";
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
			} else {
				panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
			}
		} catch (Exception e) {
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	/**
	 * Busca la oferta común con el idindicado por el usuario y lo muestra en el
	 * panel de datos
	 */
	public void buscarOfertaComunPorId() {
		try {
			String idTipoStr = JOptionPane.showInputDialog(this, "Id de la oferta común?", "Buscar oferta común por Id",
					JOptionPane.QUESTION_MESSAGE);
			if (idTipoStr != null) {
				long idTipo = Long.valueOf(idTipoStr);
				VOOfertaComun tipoBebida = aloha.darOfertaComunPorId(idTipo);
				String resultado = "En buscar Oferta Comun por id\n\n";
				if (tipoBebida != null) {
					resultado += "La oferta común es: " + tipoBebida;
				} else {
					resultado += "Una oferta común con id: " + idTipoStr + " NO EXISTE\n";
				}
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
			} else {
				panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
			}
		} catch (Exception e) {
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	/*
	 * **************************************************************** 
	 * CRUD de OfertaExclusiva
	 *****************************************************************/
	/**
	 * Adiciona una oferta exclusiva con la información dada por el usuario Se crea
	 * una nueva tupla de OfertaExclusiva en la base de datos.
	 */
	public void adicionarOfertaExclusiva() {
		try {
			int habitacion = JOptionPane.showConfirmDialog(this, "Tiene habitación compartida?");
			String precio = JOptionPane.showInputDialog(this, "Precio de la oferta?", "Adicionar oferta",
					JOptionPane.QUESTION_MESSAGE);
			if (habitacion != 2 && precio != null) {
				boolean hab = false;
				if (habitacion == 0)
					hab = true;
				VOOfertaExclusiva oe = aloha.adicionarOfertaExclusiva(hab, Integer.parseInt(precio));
				if (oe == null) {
					throw new Exception("No se pudo crear una oferta exclusiva");
				}
				String resultado = "En adicionarOfertaExclusiva\n\n";
				resultado += "Oferta Exclusiva adicionada exitosamente: " + oe;
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
			} else {
				panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
			}
		} catch (Exception e) {
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	/**
	 * Consulta en la base de datos las ofertas exclusivas existentes y los muestra
	 * en el panel de datos de la aplicación
	 */
	public void listarOfertaExclusiva() {
		try {
			List<VOOfertaExclusiva> lista = aloha.darVOOfertaExclusiva();

			String resultado = "En listarOfertaExclusiva";
			resultado += "\n" + listarOfertaExclusiva(lista);
			panelDatos.actualizarInterfaz(resultado);
			resultado += "\n Operación terminada";
		} catch (Exception e) {
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	/**
	 * Borra de la base de datos la oferta exclusiva con el identificador dado por
	 * el usuario Cuando dicha oferta exclusiva no existe, se indica que se borraron
	 * 0 registros de la base de datos
	 */
	public void eliminarOfertaExclusivaPorId() {
		try {
			String idTipoStr = JOptionPane.showInputDialog(this, "Id de la oferta exclusiva?",
					"Borrar oferta exclusiva por Id", JOptionPane.QUESTION_MESSAGE);
			if (idTipoStr != null) {
				long idTipo = Long.valueOf(idTipoStr);
				long tbEliminados = aloha.eliminarOfertaExclusivaPorId(idTipo);

				String resultado = "En eliminar OfertaExclusiva\n\n";
				resultado += tbEliminados + " Oferta Exclusiva eliminada\n";
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
			} else {
				panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
			}
		} catch (Exception e) {
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	/**
	 * Busca la oferta exclusiva con el idindicado por el usuario y lo muestra en el
	 * panel de datos
	 */
	public void buscarOfertaExclusivaPorId() {
		try {
			String idTipoStr = JOptionPane.showInputDialog(this, "Id de la oferta exclusiva?",
					"Buscar oferta común por Id", JOptionPane.QUESTION_MESSAGE);
			if (idTipoStr != null) {
				long idTipo = Long.valueOf(idTipoStr);
				VOOfertaExclusiva tipoBebida = aloha.darOfertaExclusivaPorId(idTipo);
				String resultado = "En buscar Oferta Comun por id\n\n";
				if (tipoBebida != null) {
					resultado += "La oferta común es: " + tipoBebida;
				} else {
					resultado += "Una oferta común con id: " + idTipoStr + " NO EXISTE\n";
				}
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
			} else {
				panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
			}
		} catch (Exception e) {
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}
	/*
	 * **************************************************************** 
	 * CRUD de VIVIENDAUNIVERSITARIA
	 *****************************************************************/
	/**
	 * Adiciona una vivienda universitaria con la información dada por el usuario Se
	 * crea una nueva tupla de ViviendaUniversitaria en la base de datos.
	 */
	public void adicionarViviendaUniversitaria() {
		try {
			String idAdm = JOptionPane.showInputDialog(this, "Id del administrador?", "Adicionar Vivienda Universitaria",
					JOptionPane.QUESTION_MESSAGE);
			int habitacion = JOptionPane.showConfirmDialog(this, "Tiene habitación compartida?");
			String precio = JOptionPane.showInputDialog(this, "Precio de la oferta?", "Adicionar Vivienda Universitaria",
					JOptionPane.QUESTION_MESSAGE);
			String rest = JOptionPane.showInputDialog(this, "Precio del restaurante?", "Adicionar Vivienda Universitaria",
					JOptionPane.QUESTION_MESSAGE);
			String sala = JOptionPane.showInputDialog(this, "Precio de las salas?", "Adicionar Vivienda Universitaria",
					JOptionPane.QUESTION_MESSAGE);
			String gym = JOptionPane.showInputDialog(this, "Precio del gimnasio?", "Adicionar Vivienda Universitaria",
					JOptionPane.QUESTION_MESSAGE);
			if (habitacion != 2 && precio != null && rest != null && sala != null && gym != null) {
				boolean hab = false;
				if (habitacion == 0)
					hab = true;
				VOViviendaUniversitaria vu = aloha.adicionarViviendaUniversitaria(hab, Integer.parseInt(precio),
						Integer.parseInt(rest), Integer.parseInt(sala), Integer.parseInt(gym), Long.parseLong(idAdm));
				if (vu == null) {
					throw new Exception("No se pudo crear una Vivienda Universitaria");
				}
				String resultado = "En adicionarViviendaUniversitaria\n\n";
				resultado += "Vivienda Universitaria adicionada exitosamente: " + vu;
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
			} else {
				panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
			}
		} catch (Exception e) {
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	/**
	 * Consulta en la base de datos las Viviendas Universitarias existentes y los
	 * muestra en el panel de datos de la aplicación
	 */
	public void listarViviendaUniversitaria() {
		try {
			List<VOViviendaUniversitaria> lista = aloha.darVOViviendaUniversitaria();

			String resultado = "En listarOfertaExclusiva";
			resultado += "\n" + listarViviendaUniversitaria(lista);
			panelDatos.actualizarInterfaz(resultado);
			resultado += "\n Operación terminada";
		} catch (Exception e) {
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}
	/**
	 * Borra de la base de datos la Vivienda Universitaria con el identificador dado
	 * por el usuario Cuando dicha Vivienda Universitariano existe, se indica que se
	 * borraron 0 registros de la base de datos
	 */
	public void eliminarViviendaUniversitariaPorId() {
		try {
			String idTipoStr = JOptionPane.showInputDialog(this, "Id de la Viviend aUniversitaria?",
					"Borrar Vivienda Universitaria por Id", JOptionPane.QUESTION_MESSAGE);
			if (idTipoStr != null) {
				long idTipo = Long.valueOf(idTipoStr);
				long tbEliminados = aloha.eliminarViviendaUniversitariaPorId(idTipo);

				String resultado = "En eliminar ViviendaUniversitaria\n\n";
				resultado += tbEliminados + "Vivienda Universitaria eliminada\n";
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
			} else {
				panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
			}
		} catch (Exception e) {
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	/**
	 * Busca la Vivienda Universitaria con el indicado por el usuario y lo muestra
	 * en el panel de datos
	 */
	public void buscarViviendaUniversitariaPorId() {
		try {
			String idTipoStr = JOptionPane.showInputDialog(this, "Id de la Vivienda Universitaria?",
					"Buscar Vivienda Universitaria por Id", JOptionPane.QUESTION_MESSAGE);
			if (idTipoStr != null) {
				long idTipo = Long.valueOf(idTipoStr);
				VOViviendaUniversitaria tipoBebida = aloha.darViviendaUniversitariaPorId(idTipo);
				String resultado = "En buscar Vivienda Universitaria por id\n\n";
				if (tipoBebida != null) {
					resultado += "La Vivienda Universitaria es: " + tipoBebida;
				} else {
					resultado += "Una Vivienda Universitaria con id: " + idTipoStr + " NO EXISTE\n";
				}
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
			} else {
				panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
			}
		} catch (Exception e) {
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}
	
	/*
	 * **************************************************************** 
	 * CRUD de Apartamento
	 *****************************************************************/
	/**
	 * Adiciona un apartamento con la información dada por el usuario Se
	 * crea una nueva tupla de Apartamento en la base de datos.
	 */
	public void adicionarApartamento() {
		try {
			String idMiem = JOptionPane.showInputDialog(this, "Id del miembro?", "Adicionar Apartamento",
					JOptionPane.QUESTION_MESSAGE);
			int habitacion = JOptionPane.showConfirmDialog(this, "Tiene habitación compartida?");
			String precio = JOptionPane.showInputDialog(this, "Precio de la oferta?", "Adicionar Apartamento",
					JOptionPane.QUESTION_MESSAGE);
			int amoblado = JOptionPane.showConfirmDialog(this, "Esta amoblado?");
			int comida = JOptionPane.showConfirmDialog(this, "Incluye comidas?");
			int bano = JOptionPane.showConfirmDialog(this, "Tiene baño compartido?");
			String costo = JOptionPane.showInputDialog(this, "Precio de los servicios?", "Adicionar Apartamento",
					JOptionPane.QUESTION_MESSAGE);
			if (habitacion != 2 && precio != null && amoblado != 2 && comida != 2 && bano != 2 && costo != null) {
				boolean hab = false, amo = false, com = false, ban = false;
				if (habitacion == 0)
					hab = true;
				if (amoblado == 0)
					amo = true;
				if (comida == 0)
					com = true;
				if (bano == 0)
					ban = true;
				VOApartamento oa = aloha.adicionarApartamento(hab, Integer.parseInt(precio), amo, com, ban, Integer.parseInt(costo), Long.parseLong(idMiem));
				if (oa == null) {
					throw new Exception("No se pudo crear un Apartamento");
				}
				String resultado = "En adicionarApartamento\n\n";
				resultado += "Apartamento adicionado exitosamente: " + oa;
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
			} else {
				panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
			}
		} catch (Exception e) {
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	/**
	 * Consulta en la base de datos los Apartamentos existentes y los
	 * muestra en el panel de datos de la aplicación
	 */
	public void listarApartamento() {
		try {
			List<VOApartamento> lista = aloha.darVOApartamento();

			String resultado = "En listarApartamento";
			resultado += "\n" + listarApartamento(lista);
			panelDatos.actualizarInterfaz(resultado);
			resultado += "\n Operación terminada";
		} catch (Exception e) {
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	/**
	 * Borra de la base de datos el Apartamento con el identificador dado
	 * por el usuario Cuando dicho Apartamento no existe, se indica que se
	 * borraron 0 registros de la base de datos
	 */
	public void eliminarApartamentoPorId() {
		try {
			String idTipoStr = JOptionPane.showInputDialog(this, "Id del Apartamento?",
					"Borrar Apartamento por Id", JOptionPane.QUESTION_MESSAGE);
			if (idTipoStr != null) {
				long idTipo = Long.valueOf(idTipoStr);
				long tbEliminados = aloha.eliminarApartamentoPorId(idTipo);

				String resultado = "En eliminar Apartamento\n\n";
				resultado += tbEliminados + "Vivienda Apartamento eliminada\n";
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
			} else {
				panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
			}
		} catch (Exception e) {
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	/**
	 * Busca el Apartamento con el id indicado por el usuario y lo muestra
	 * en el panel de datos
	 */
	public void buscarApartamentoPorId() {
		try {
			String idTipoStr = JOptionPane.showInputDialog(this, "Id del Apartamento?",
					"Buscar Apartamento por Id", JOptionPane.QUESTION_MESSAGE);
			if (idTipoStr != null) {
				long idTipo = Long.valueOf(idTipoStr);
				VOApartamento tipoBebida = aloha.darApartamentoPorId(idTipo);
				String resultado = "En buscar Apartamento por id\n\n";
				if (tipoBebida != null) {
					resultado += "El Apartamento es: " + tipoBebida;
				} else {
					resultado += "Un Apartamento con id: " + idTipoStr + " NO EXISTE\n";
				}
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
			} else {
				panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
			}
		} catch (Exception e) {
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}
	
	/*
	 * **************************************************************** 
	 * CRUD de Vivienda
	 *****************************************************************/
	/**
	 * Adiciona una vivienda con la información dada por el usuario Se crea una
	 * nueva tupla de Vivienda en la base de datos.
	 */
	public void adicionarVivienda() {
		try {
			String idVec = JOptionPane.showInputDialog(this, "Id del propietario?", "Adicionar Vivienda",
					JOptionPane.QUESTION_MESSAGE);
			String capacidad = JOptionPane.showInputDialog(this, "Capacidad de la Vivienda?", "Adicionar Vivienda",
					JOptionPane.QUESTION_MESSAGE);
			int piscina = JOptionPane.showConfirmDialog(this, "Tiene piscina?");
			int parqueadero = JOptionPane.showConfirmDialog(this, "Tiene parqueadero?");
			int tvCable = JOptionPane.showConfirmDialog(this, "Tiene tvCable?");
			int wifi = JOptionPane.showConfirmDialog(this, "Tiene wifi?");
			String precio = JOptionPane.showInputDialog(this, "Precio de la oferta?", "Adicionar Vivienda",
					JOptionPane.QUESTION_MESSAGE);
			String num = JOptionPane.showInputDialog(this, "Número de habitaciones?", "Adicionar Vivienda",
					JOptionPane.QUESTION_MESSAGE);
			String desc = JOptionPane.showInputDialog(this, "Tipo de Seguro?", "Adicionar Vivienda",
					JOptionPane.QUESTION_MESSAGE);
			if (capacidad != null && piscina != 2 && parqueadero != 2 && tvCable != 2 && wifi != 2 && precio != null && num != null && desc != null) {
				boolean pis = false, par = false, tv = false, wi = false;
				if (piscina == 0)
					pis = true;
				if (parqueadero == 0)
					par = true;
				if (tvCable == 0)
					tv = true;
				if (wifi == 0)
					wi = true;
				VOVivienda ov = aloha.adicionarVivienda(Integer.parseInt(capacidad), pis, par, tv, wi,
						Integer.parseInt(precio), Integer.parseInt(num), desc, Long.parseLong(idVec));
				if (ov == null) {
					throw new Exception("No se pudo crear una Vivienda");
				}
				String resultado = "En adicionarVivienda\n\n";
				resultado += "Vivienda adicionada exitosamente: " + ov;
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
			} else {
				panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
			}
		} catch (Exception e) {
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	/**
	 * Consulta en la base de datos las Viviendas existentes y los muestra en
	 * el panel de datos de la aplicación
	 */
	public void listarVivienda() {
		try {
			List<VOVivienda> lista = aloha.darVOVivienda();

			String resultado = "En listarVivienda";
			resultado += "\n" + listarVivienda(lista);
			panelDatos.actualizarInterfaz(resultado);
			resultado += "\n Operación terminada";
		} catch (Exception e) {
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	/**
	 * Borra de la base de datos la Vivienda con el identificador dado por el
	 * usuario Cuando dicha Vivienda no existe, se indica que se borraron 0
	 * registros de la base de datos
	 */
	public void eliminarViviendaPorId() {
		try {
			String idTipoStr = JOptionPane.showInputDialog(this, "Id de la Vivienda?", "Borrar Vivienda por Id",
					JOptionPane.QUESTION_MESSAGE);
			if (idTipoStr != null) {
				long idTipo = Long.valueOf(idTipoStr);
				long tbEliminados = aloha.eliminarViviendaPorId(idTipo);

				String resultado = "En eliminar Vivienda\n\n";
				resultado += tbEliminados + "Vivienda eliminada\n";
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
			} else {
				panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
			}
		} catch (Exception e) {
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	/**
	 * Busca la Vivienda con el indicado por el usuario y lo muestra en el
	 * panel de datos
	 */
	public void buscarViviendaPorId() {
		try {
			String idTipoStr = JOptionPane.showInputDialog(this, "Id de la Vivienda?", "Buscar Vivienda por Id",
					JOptionPane.QUESTION_MESSAGE);
			if (idTipoStr != null) {
				long idTipo = Long.valueOf(idTipoStr);
				VOVivienda tipoBebida = aloha.darViviendaPorId(idTipo);
				String resultado = "En buscar Vivienda por id\n\n";
				if (tipoBebida != null) {
					resultado += "La Vivienda es: " + tipoBebida;
				} else {
					resultado += "Una Vivienda con id: " + idTipoStr + " NO EXISTE\n";
				}
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
			} else {
				panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
			}
		} catch (Exception e) {
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}
	/*
	 * **************************************************************** 
	 * CRUD de Hospedaje
	 *****************************************************************/
	/**
	 * Adiciona un hospedaje con la información dada por el usuario Se crea una
	 * nueva tupla de Hospedaje en la base de datos.
	 */
	public void adicionarHospedaje() {
		try {
			String idEmp = JOptionPane.showInputDialog(this, "Id de la empresa?", "Adicionar Hospedaje",
					JOptionPane.QUESTION_MESSAGE);
			String capacidad = JOptionPane.showInputDialog(this, "Capacidad de Hospedaje?", "Adicionar Hospedaje",
					JOptionPane.QUESTION_MESSAGE);
			int piscina = JOptionPane.showConfirmDialog(this, "Tiene piscina?");
			int parqueadero = JOptionPane.showConfirmDialog(this, "Tiene parqueadero?");
			int tvCable = JOptionPane.showConfirmDialog(this, "Tiene tvCable?");
			int wifi = JOptionPane.showConfirmDialog(this, "Tiene wifi?");
			String precio = JOptionPane.showInputDialog(this, "Precio del Hospedaje?", "Adicionar Hospedaje",
					JOptionPane.QUESTION_MESSAGE);
			String cat = JOptionPane.showInputDialog(this, "Categoría del Hospedaje?", "Adicionar Hospedaje",
					JOptionPane.QUESTION_MESSAGE);
			String tama = JOptionPane.showInputDialog(this, "Tamaño del Hospedaje?", "Adicionar Hospedaje",
					JOptionPane.QUESTION_MESSAGE);
			int recepcion = JOptionPane.showConfirmDialog(this, "Tiene recepción 24h?");
			int restaurante = JOptionPane.showConfirmDialog(this, "Tiene restaurante?");
			if (capacidad != null && piscina != 2 && parqueadero != 2 && tvCable != 2 && wifi != 2 && precio != null && cat != null && tama != null && recepcion != 2 && restaurante != 2 ) {
				boolean pis = false, par = false, tv = false, wi = false, rec = false, rest = false;
				if (piscina == 0)
					pis = true;
				if (parqueadero == 0)
					par = true;
				if (tvCable == 0)
					tv = true;
				if (wifi == 0)
					wi = true;
				if (recepcion == 0)
					rec = true;
				if (restaurante == 0)
					rest = true;
				VOHospedaje ov = aloha.adicionarHospedaje(Integer.parseInt(capacidad), pis, par, tv, wi,
						Integer.parseInt(precio), cat, tama, rec, rest, Long.parseLong(idEmp));
				if (ov == null) {
					throw new Exception("No se pudo crear un Hospedaje");
				}
				String resultado = "En adicionar Hospedaje\n\n";
				resultado += "Hospedaje adicionado exitosamente: " + ov;
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
			} else {
				panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
			}
		} catch (Exception e) {
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	/**
	 * Consulta en la base de datos los Hospedajes existentes y los muestra en
	 * el panel de datos de la aplicación
	 */
	public void listarHospedaje() {
		try {
			List<VOHospedaje> lista = aloha.darVOHospedaje();

			String resultado = "En listarHospedaje";
			resultado += "\n" + listarHospedaje(lista);
			panelDatos.actualizarInterfaz(resultado);
			resultado += "\n Operación terminada";
		} catch (Exception e) {
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	/**
	 * Borra de la base de datos el Hospedaje con el identificador dado por el
	 * usuario Cuando dicho Hospedaje no existe, se indica que se borraron 0
	 * registros de la base de datos
	 */
	public void eliminarHospedajePorId() {
		try {
			String idTipoStr = JOptionPane.showInputDialog(this, "Id de la Hospedaje?", "Borrar Hospedaje por Id",
					JOptionPane.QUESTION_MESSAGE);
			if (idTipoStr != null) {
				long idTipo = Long.valueOf(idTipoStr);
				long tbEliminados = aloha.eliminarHospedajePorId(idTipo);

				String resultado = "En eliminar Hospedaje\n\n";
				resultado += tbEliminados + "Hospedaje eliminada\n";
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
			} else {
				panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
			}
		} catch (Exception e) {
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	/**
	 * Busca la Hospedaje con el indicado por el usuario y lo muestra en el
	 * panel de datos
	 */
	public void buscarHospedajePorId() {
		try {
			String idTipoStr = JOptionPane.showInputDialog(this, "Id de la Hospedaje?", "Buscar Hospedaje por Id",
					JOptionPane.QUESTION_MESSAGE);
			if (idTipoStr != null) {
				long idTipo = Long.valueOf(idTipoStr);
				VOHospedaje tipoBebida = aloha.darHospedajePorId(idTipo);
				String resultado = "En buscar Hospedaje por id\n\n";
				if (tipoBebida != null) {
					resultado += "La Hospedaje es: " + tipoBebida;
				} else {
					resultado += "Una Hospedaje con id: " + idTipoStr + " NO EXISTE\n";
				}
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
			} else {
				panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
			}
		} catch (Exception e) {
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}
	/*
	 * **************************************************************** 
	 * CRUD de Empresa
	 *****************************************************************/
	/**
	 * Adiciona una Empresa con la información dada por el usuario Se crea una
	 * nueva tupla de la Empresa en la base de datos.
	 */
	public void adicionarEmpresa() {
		try {
			String nombre = JOptionPane.showInputDialog(this, "Nombre de la Empresa?", "Adicionar Empresa",
					JOptionPane.QUESTION_MESSAGE);
			String idEmpresa = JOptionPane.showInputDialog(this, "Identificador de la Empresa?", "Adicionar Empresa",
					JOptionPane.QUESTION_MESSAGE);	
			String tipoId = JOptionPane.showInputDialog(this, "Tipo de identificación?", "Adicionar Empresa",
					JOptionPane.QUESTION_MESSAGE);
			String registro = JOptionPane.showInputDialog(this, "Registro de la Empresa?", "Adicionar Empresa",
					JOptionPane.QUESTION_MESSAGE);
			String tipo = JOptionPane.showInputDialog(this, "Tipo de la Empresa?", "Adicionar Empresa",
					JOptionPane.QUESTION_MESSAGE);
			if (nombre != null && idEmpresa != null && tipoId != null && registro != null && tipo != null) {
				VOEmpresa oe = aloha.adicionarEmpresa(Long.parseLong(idEmpresa), nombre, tipoId, registro, tipo);
				if (oe == null) {
					throw new Exception("No se pudo crear una Empresa");
				}
				String resultado = "En adicionar Empresa\n\n";
				resultado += "Empresa adicionada exitosamente: " + oe;
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
			} else {
				panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
			}
		} catch (Exception e) {
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	/**
	 * Consulta en la base de datos las Empresas existentes y los muestra en
	 * el panel de datos de la aplicación
	 */
	public void listarEmpresa() {
		try {
			List<VOEmpresa> lista = aloha.darVOEmpresas();

			String resultado = "En listarEmpresa";
			resultado += "\n" + listarEmpresa(lista);
			panelDatos.actualizarInterfaz(resultado);
			resultado += "\n Operación terminada";
		} catch (Exception e) {
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	/**
	 * Borra de la base de datos el Empresa con el identificador dado por el
	 * usuario Cuando dicho Empresa no existe, se indica que se borraron 0
	 * registros de la base de datos
	 */
	public void eliminarEmpresaPorId() {
		try {
			String idTipoStr = JOptionPane.showInputDialog(this, "Id de la Empresa?", "Borrar Empresa por Id",
					JOptionPane.QUESTION_MESSAGE);
			if (idTipoStr != null) {
				long idTipo = Long.valueOf(idTipoStr);
				long tbEliminados = aloha.eliminarEmpresaPorId(idTipo);

				String resultado = "En eliminar Empresa\n\n";
				resultado += tbEliminados + "Empresa eliminada\n";
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
			} else {
				panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
			}
		} catch (Exception e) {
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}
	/*
	 * **************************************************************** 
	 * CRUD de Vecino
	 *****************************************************************/
	/**
	 * Adiciona un Vecino con la información dada por el usuario Se crea una
	 * nueva tupla del Vecino en la base de datos.
	 */
	public void adicionarVecino() {
		try {
			String nombre = JOptionPane.showInputDialog(this, "Nombre del Vecino?", "Adicionar Vecino",
					JOptionPane.QUESTION_MESSAGE);
			String idVecino = JOptionPane.showInputDialog(this, "Identificador del Vecino?", "Adicionar Vecino",
					JOptionPane.QUESTION_MESSAGE);	
			String tipoId = JOptionPane.showInputDialog(this, "Tipo de identificación?", "Adicionar Vecino",
					JOptionPane.QUESTION_MESSAGE);
			String ubicacion = JOptionPane.showInputDialog(this, "Ubicación del Vecino?", "Adicionar Vecino",
					JOptionPane.QUESTION_MESSAGE);
			if (nombre != null && idVecino != null && tipoId != null && ubicacion != null) {
				VOVecino ov = aloha.adicionarVecino(Long.parseLong(idVecino), nombre, tipoId, ubicacion);
				if (ov == null) {
					throw new Exception("No se pudo crear una Vecino");
				}
				String resultado = "En adicionar Vecino\n\n";
				resultado += "Vecino adicionada exitosamente: " + ov;
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
			} else {
				panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
			}
		} catch (Exception e) {
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	/**
	 * Consulta en la base de datos los Vecinos existentes y los muestra en
	 * el panel de datos de la aplicación
	 */
	public void listarVecino() {
		try {
			List<VOVecino> lista = aloha.darVOVecinos();

			String resultado = "En listarVecino";
			resultado += "\n" + listarVecino(lista);
			panelDatos.actualizarInterfaz(resultado);
			resultado += "\n Operación terminada";
		} catch (Exception e) {
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	/**
	 * Borra de la base de datos el Vecino con el identificador dado por el
	 * usuario Cuando dicho Vecino no existe, se indica que se borraron 0
	 * registros de la base de datos
	 */
	public void eliminarVecinoPorId() {
		try {
			String idTipoStr = JOptionPane.showInputDialog(this, "Id del Vecino?", "Borrar Vecino por Id",
					JOptionPane.QUESTION_MESSAGE);
			if (idTipoStr != null) {
				long idTipo = Long.valueOf(idTipoStr);
				long tbEliminados = aloha.eliminarVecinoPorId(idTipo);

				String resultado = "En eliminar Vecino\n\n";
				resultado += tbEliminados + "Vecino eliminado\n";
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
			} else {
				panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
			}
		} catch (Exception e) {
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}
	/*
	 * **************************************************************** 
	 * CRUD de AdminVivienda
	 *****************************************************************/
	/**
	 * Adiciona un AdminVivienda con la información dada por el usuario Se crea una
	 * nueva tupla del AdminVivienda en la base de datos.
	 */
	public void adicionarAdminVivienda() {
		try {
			String nombre = JOptionPane.showInputDialog(this, "Nombre del AdminVivienda?", "Adicionar AdminVivienda",
					JOptionPane.QUESTION_MESSAGE);
			String idAdminVivienda = JOptionPane.showInputDialog(this, "Identificador del AdminVivienda?", "Adicionar AdminVivienda",
					JOptionPane.QUESTION_MESSAGE);	
			String tipoId = JOptionPane.showInputDialog(this, "Tipo de identificación?", "Adicionar AdminVivienda",
					JOptionPane.QUESTION_MESSAGE);
			String ubicacion = JOptionPane.showInputDialog(this, "Ubicación del AdminVivienda?", "Adicionar AdminVivienda",
					JOptionPane.QUESTION_MESSAGE);
			if (nombre != null && idAdminVivienda != null && tipoId != null && ubicacion != null) {
				VOAdminVivienda ov = aloha.adicionarAdminVivienda(Long.parseLong(idAdminVivienda), nombre, tipoId, ubicacion);
				if (ov == null) {
					throw new Exception("No se pudo crear una AdminVivienda");
				}
				String resultado = "En adicionar AdminVivienda\n\n";
				resultado += "AdminVivienda adicionada exitosamente: " + ov;
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
			} else {
				panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
			}
		} catch (Exception e) {
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	/**
	 * Consulta en la base de datos los AdminViviendas existentes y los muestra en
	 * el panel de datos de la aplicación
	 */
	public void listarAdminVivienda() {
		try {
			List<VOAdminVivienda> lista = aloha.darVOAdminVivienda();

			String resultado = "En listarAdminVivienda";
			resultado += "\n" + listarAdminVivienda(lista);
			panelDatos.actualizarInterfaz(resultado);
			resultado += "\n Operación terminada";
		} catch (Exception e) {
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	/**
	 * Borra de la base de datos el AdminVivienda con el identificador dado por el
	 * usuario Cuando dicho AdminVivienda no existe, se indica que se borraron 0
	 * registros de la base de datos
	 */
	public void eliminarAdminViviendaPorId() {
		try {
			String idTipoStr = JOptionPane.showInputDialog(this, "Id del AdminVivienda?", "Borrar AdminVivienda por Id",
					JOptionPane.QUESTION_MESSAGE);
			if (idTipoStr != null) {
				long idTipo = Long.valueOf(idTipoStr);
				long tbEliminados = aloha.eliminarAdminViviendaPorId(idTipo);

				String resultado = "En eliminar AdminVivienda\n\n";
				resultado += tbEliminados + "AdminVivienda eliminada\n";
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
			} else {
				panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
			}
		} catch (Exception e) {
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	/*
	 * **************************************************************** 
	 * CRUD de PropietarioMiembro
	 *****************************************************************/
	/**
	 * Adiciona un PropietarioMiembro con la información dada por el usuario Se crea una
	 * nueva tupla del PropietarioMiembro en la base de datos.
	 */
	public void adicionarPropietarioMiembro() {
		try {
			String nombre = JOptionPane.showInputDialog(this, "Nombre del PropietarioMiembro?", "Adicionar PropietarioMiembro",
					JOptionPane.QUESTION_MESSAGE);
			String idPropietarioMiembro = JOptionPane.showInputDialog(this, "Identificador del PropietarioMiembro?", "Adicionar PropietarioMiembro",
					JOptionPane.QUESTION_MESSAGE);	
			String tipoId = JOptionPane.showInputDialog(this, "Tipo de identificación?", "Adicionar PropietarioMiembro",
					JOptionPane.QUESTION_MESSAGE);
			String tipo = JOptionPane.showInputDialog(this, "Tipo de miembro?", "Adicionar PropietarioMiembro",
					JOptionPane.QUESTION_MESSAGE);
			String ubicacion = JOptionPane.showInputDialog(this, "Ubicación del PropietarioMiembro?", "Adicionar PropietarioMiembro",
					JOptionPane.QUESTION_MESSAGE);
			if (nombre != null && idPropietarioMiembro != null && tipoId != null && ubicacion != null) {
				VOPropietarioMiembro pm = aloha.adicionarPropietarioMiembro(Long.parseLong(idPropietarioMiembro), nombre, tipoId, tipo, ubicacion);
				if (pm == null) {
					throw new Exception("No se pudo crear una PropietarioMiembro");
				}
				String resultado = "En adicionar PropietarioMiembro\n\n";
				resultado += "PropietarioMiembro adicionada exitosamente: " + pm;
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
			} else {
				panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
			}
		} catch (Exception e) {
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	/**
	 * Consulta en la base de datos los PropietarioMiembros existentes y los muestra en
	 * el panel de datos de la aplicación
	 */
	public void listarPropietarioMiembro() {
		try {
			List<VOPropietarioMiembro> lista = aloha.darVOPropietarioMiembro();

			String resultado = "En listarPropietarioMiembro";
			resultado += "\n" + listarPropietarioMiembro(lista);
			panelDatos.actualizarInterfaz(resultado);
			resultado += "\n Operación terminada";
		} catch (Exception e) {
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	/**
	 * Borra de la base de datos el PropietarioMiembro con el identificador dado por el
	 * usuario Cuando dicho PropietarioMiembro no existe, se indica que se borraron 0
	 * registros de la base de datos
	 */
	public void eliminarPropietarioMiembroPorId() {
		try {
			String idTipoStr = JOptionPane.showInputDialog(this, "Id del PropietarioMiembro?", "Borrar PropietarioMiembro por Id",
					JOptionPane.QUESTION_MESSAGE);
			if (idTipoStr != null) {
				long idTipo = Long.valueOf(idTipoStr);
				long tbEliminados = aloha.eliminarPropietarioMiembroPorId(idTipo);

				String resultado = "En eliminar PropietarioMiembro\n\n";
				resultado += tbEliminados + "PropietarioMiembro eliminada\n";
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
			} else {
				panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
			}
		} catch (Exception e) {
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	/*
	 * **************************************************************** 
	 * CRUD de Cliente
	 *****************************************************************/
	/**
	 * Adiciona un Cliente con la información dada por el usuario Se crea una
	 * nueva tupla del Cliente en la base de datos.
	 */
	public void adicionarCliente() {
		try {
			String nombre = JOptionPane.showInputDialog(this, "Nombre del Cliente?", "Adicionar Cliente",
					JOptionPane.QUESTION_MESSAGE);
			String idCliente = JOptionPane.showInputDialog(this, "Identificador del Cliente?", "Adicionar Cliente",
					JOptionPane.QUESTION_MESSAGE);	
			String correo = JOptionPane.showInputDialog(this, "Correo del Cliente?", "Adicionar Cliente",
					JOptionPane.QUESTION_MESSAGE);
			if (nombre != null && idCliente != null && correo != null) {
				VOCliente pm = aloha.adicionarCliente(Long.parseLong(idCliente), correo, nombre);
				if (pm == null) {
					throw new Exception("No se pudo crear un Cliente");
				}
				String resultado = "En adicionar Cliente\n\n";
				resultado += "Cliente adicionada exitosamente: " + pm;
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
			} else {
				panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
			}
		} catch (Exception e) {
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	/**
	 * Consulta en la base de datos los Clientes existentes y los muestra en
	 * el panel de datos de la aplicación
	 */
	public void listarCliente() {
		try {
			List<VOCliente> lista = aloha.darVOCliente();

			String resultado = "En listarCliente";
			resultado += "\n" + listarCliente(lista);
			panelDatos.actualizarInterfaz(resultado);
			resultado += "\n Operación terminada";
		} catch (Exception e) {
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	/**
	 * Borra de la base de datos el Cliente con el identificador dado por el
	 * usuario Cuando dicho Cliente no existe, se indica que se borraron 0
	 * registros de la base de datos
	 */
	public void eliminarClientePorId() {
		try {
			String idTipoStr = JOptionPane.showInputDialog(this, "Id del Cliente?", "Borrar Cliente por Id",
					JOptionPane.QUESTION_MESSAGE);
			if (idTipoStr != null) {
				long idTipo = Long.valueOf(idTipoStr);
				long tbEliminados = aloha.eliminarCliente(idTipo);

				String resultado = "En eliminar Cliente\n\n";
				resultado += tbEliminados + "Cliente eliminada\n";
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
			} else {
				panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
			}
		} catch (Exception e) {
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}
	/*
	 * **************************************************************** 
	 * CRUD de ClienteMiembroComunidad
	 *****************************************************************/
	/**
	 * Adiciona un ClienteMiembroComunidad con la información dada por el usuario Se crea una
	 * nueva tupla del ClienteMiembroComunidad en la base de datos.
	 */
	public void adicionarClienteMiembro() {
		try {
			String nombre = JOptionPane.showInputDialog(this, "Nombre del ClienteMiembroComunidad?", "Adicionar ClienteMiembroComunidad",
					JOptionPane.QUESTION_MESSAGE);
			String idClienteMiembroComunidad = JOptionPane.showInputDialog(this, "Identificador del ClienteMiembroComunidad?", "Adicionar ClienteMiembroComunidad",
					JOptionPane.QUESTION_MESSAGE);	
			String correo = JOptionPane.showInputDialog(this, "Correo del ClienteMiembroComunidad?", "Adicionar ClienteMiembroComunidad",
					JOptionPane.QUESTION_MESSAGE);
			String tipo = JOptionPane.showInputDialog(this, "Tipo de miembro?", "Adicionar ClienteMiembroComunidad",
					JOptionPane.QUESTION_MESSAGE);
			if (nombre != null && idClienteMiembroComunidad != null && correo != null) {
				VOClienteMiembro pm = aloha.adicionarClienteMiembro(Long.parseLong(idClienteMiembroComunidad), correo, nombre, tipo);
				if (pm == null) {
					throw new Exception("No se pudo crear una ClienteMiembroComunidad");
				}
				String resultado = "En adicionar ClienteMiembroComunidad\n\n";
				resultado += "ClienteMiembroComunidad adicionada exitosamente: " + pm;
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
			} else {
				panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
			}
		} catch (Exception e) {
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	/**
	 * Consulta en la base de datos los ClienteMiembroComunidads existentes y los muestra en
	 * el panel de datos de la aplicación
	 */
	public void listarClienteMiembro() {
		try {
			List<VOClienteMiembro> lista = aloha.darVOClienteMiembro();

			String resultado = "En listarClienteMiembroComunidad";
			resultado += "\n" + listarClienteMiembro(lista);
			panelDatos.actualizarInterfaz(resultado);
			resultado += "\n Operación terminada";
		} catch (Exception e) {
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	/**
	 * Borra de la base de datos el ClienteMiembroComunidad con el identificador dado por el
	 * usuario Cuando dicho ClienteMiembroComunidad no existe, se indica que se borraron 0
	 * registros de la base de datos
	 */
	public void eliminarClienteMiembroPorId() {
		try {
			String idTipoStr = JOptionPane.showInputDialog(this, "Id del ClienteMiembroComunidad?", "Borrar ClienteMiembroComunidad por Id",
					JOptionPane.QUESTION_MESSAGE);
			if (idTipoStr != null) {
				long idTipo = Long.valueOf(idTipoStr);
				long tbEliminados = aloha.eliminarClienteMiembro(idTipo);

				String resultado = "En eliminar ClienteMiembroComunidad\n\n";
				resultado += tbEliminados + "ClienteMiembroComunidad eliminada\n";
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
			} else {
				panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
			}
		} catch (Exception e) {
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}
	/*
	 * **************************************************************** 
	 * CRUD de ReservaComun
	 *****************************************************************/
	/**
	 * Adiciona un ReservaComun con la información dada por el usuario Se crea una
	 * nueva tupla del ReservaComun en la base de datos.
	 */
	public void adicionarReservaComun() {
		try {
			String idCliente = JOptionPane.showInputDialog(this, "Identificador del Cliente?", "Adicionar ReservaComun",
					JOptionPane.QUESTION_MESSAGE);
			String idOferta = JOptionPane.showInputDialog(this, "Identificador de la Oferta?", "Adicionar ReservaComun",
					JOptionPane.QUESTION_MESSAGE);
			String inic = JOptionPane.showInputDialog(this, "Dar la fecha de inicio de la forma YYYY-MM-DD?", "Adicionar ReservaComun",
							JOptionPane.QUESTION_MESSAGE);
			String fin = JOptionPane.showInputDialog(this, "Dar la fecha de fin de la forma YYYY-MM-DD?", "Adicionar ReservaComun",
							JOptionPane.QUESTION_MESSAGE);
			if (idCliente != null && idOferta != null && inic != null && fin != null) {
				VOReservaComun pm = aloha.adicionarReservaComun(Long.parseLong(idCliente), Long.parseLong(idOferta), Date.valueOf(inic), Date.valueOf(fin));
				if (pm == null) {
					throw new Exception("No se pudo crear una ReservaComun");
				}
				String resultado = "En adicionar ReservaComun\n\n";
				resultado += "ReservaComun adicionada exitosamente: " + pm;
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
			} else {
				panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
			}
		} catch (Exception e) {
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	/**
	 * Consulta en la base de datos los ReservaComuns existentes y los muestra en
	 * el panel de datos de la aplicación
	 */
	public void listarReservaComun() {
		try {
			List<VOReservaComun> lista = aloha.darVOReservaComun();

			String resultado = "En listarReservaComun";
			resultado += "\n" + listarReservaComun(lista);
			panelDatos.actualizarInterfaz(resultado);
			resultado += "\n Operación terminada";
		} catch (Exception e) {
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	/**
	 * Borra de la base de datos el ReservaComun con el identificador dado por el
	 * usuario Cuando dicho ReservaComun no existe, se indica que se borraron 0
	 * registros de la base de datos
	 */
	public void eliminarReservaComunPorId() {
		try {
			String idTipoStr = JOptionPane.showInputDialog(this, "Id del Cliente?", "Borrar ReservaComun por Id",
					JOptionPane.QUESTION_MESSAGE);
			String idTipoStr2 = JOptionPane.showInputDialog(this, "Id de la Oferta?", "Borrar ReservaComun por Id",
					JOptionPane.QUESTION_MESSAGE);
			if (idTipoStr != null || idTipoStr2 != null) {
				long idTipo = Long.valueOf(idTipoStr);
				long idTipo2 = Long.valueOf(idTipoStr2);
				long tbEliminados = aloha.eliminarReservaComun(idTipo, idTipo2);

				String resultado = "En eliminar ReservaComun\n\n";
				resultado += tbEliminados + "ReservaComun eliminada\n";
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
			} else {
				panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
			}
		} catch (Exception e) {
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}
	/*
	 * **************************************************************** 
	 * CRUD de Contrato
	 *****************************************************************/
	/**
	 * Adiciona un Contrato con la información dada por el usuario Se crea una
	 * nueva tupla del Contrato en la base de datos.
	 */
	public void adicionarContrato() {
		try {
			String idCliente = JOptionPane.showInputDialog(this, "Identificador del Cliente?", "Adicionar Contrato",
					JOptionPane.QUESTION_MESSAGE);
			String idOferta = JOptionPane.showInputDialog(this, "Identificador de la Oferta?", "Adicionar Contrato",
					JOptionPane.QUESTION_MESSAGE);
			String inic = JOptionPane.showInputDialog(this, "Dar la fecha de inicio de la forma YYYY-MM-DD?", "Adicionar Contrato",
							JOptionPane.QUESTION_MESSAGE);
			String fin = JOptionPane.showInputDialog(this, "Dar la fecha de fin de la forma YYYY-MM-DD?", "Adicionar Contrato",
							JOptionPane.QUESTION_MESSAGE);
			if (idCliente != null && idOferta != null && inic != null && fin != null) {
				VOContrato pm = aloha.adicionarContrato(Long.parseLong(idCliente), Long.parseLong(idOferta), Date.valueOf(inic), Date.valueOf(fin));
				if (pm == null) {
					throw new Exception("No se pudo crear una Contrato");
				}
				String resultado = "En adicionar Contrato\n\n";
				resultado += "Contrato adicionado exitosamente: " + pm;
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
			} else {
				panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
			}
		} catch (Exception e) {
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	/**
	 * Consulta en la base de datos los Contratos existentes y los muestra en
	 * el panel de datos de la aplicación
	 */
	public void listarContrato() {
		try {
			List<VOContrato> lista = aloha.darVOContrato();

			String resultado = "En listarContrato";
			resultado += "\n" + listarContrato(lista);
			panelDatos.actualizarInterfaz(resultado);
			resultado += "\n Operación terminada";
		} catch (Exception e) {
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	/**
	 * Borra de la base de datos el Contrato con el identificador dado por el
	 * usuario Cuando dicho Contrato no existe, se indica que se borraron 0
	 * registros de la base de datos
	 */
	public void eliminarContratoPorId() {
		try {
			String idTipoStr = JOptionPane.showInputDialog(this, "Id del Cliente?", "Borrar Contrato por Id",
					JOptionPane.QUESTION_MESSAGE);
			String idTipoStr2 = JOptionPane.showInputDialog(this, "Id de la Oferta?", "Borrar Contrato por Id",
					JOptionPane.QUESTION_MESSAGE);
			if (idTipoStr != null || idTipoStr2 != null) {
				long idTipo = Long.valueOf(idTipoStr);
				long idTipo2 = Long.valueOf(idTipoStr2);
				long tbEliminados = aloha.eliminarContrato(idTipo, idTipo2);

				String resultado = "En eliminar Contrato\n\n";
				resultado += tbEliminados + "Contrato eliminado\n";
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
			} else {
				panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
			}
		} catch (Exception e) {
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}
	/*
	 * **************************************************************** 
	 * Métodos administrativos
	 *****************************************************************/
	/**
	 * Muestra el log de Parranderos
	 */
	public void mostrarLogParranderos() {
		mostrarArchivo("parranderos.log");
	}

	/**
	 * Muestra el log de datanucleus
	 */
	public void mostrarLogDatanuecleus() {
		mostrarArchivo("datanucleus.log");
	}

	/**
	 * Limpia el contenido del log de parranderos Muestra en el panel de datos la
	 * traza de la ejecución
	 */
	public void limpiarLogParranderos() {
		// Ejecución de la operación y recolección de los resultados
		boolean resp = limpiarArchivo("parranderos.log");

		// Generación de la cadena de caracteres con la traza de la ejecución de la demo
		String resultado = "\n\n************ Limpiando el log de parranderos ************ \n";
		resultado += "Archivo " + (resp ? "limpiado exitosamente" : "NO PUDO ser limpiado !!");
		resultado += "\nLimpieza terminada";

		panelDatos.actualizarInterfaz(resultado);
	}

	/**
	 * Limpia el contenido del log de datanucleus Muestra en el panel de datos la
	 * traza de la ejecución
	 */
	public void limpiarLogDatanucleus() {
		// Ejecución de la operación y recolección de los resultados
		boolean resp = limpiarArchivo("datanucleus.log");

		// Generación de la cadena de caracteres con la traza de la ejecución de la demo
		String resultado = "\n\n************ Limpiando el log de datanucleus ************ \n";
		resultado += "Archivo " + (resp ? "limpiado exitosamente" : "NO PUDO ser limpiado !!");
		resultado += "\nLimpieza terminada";

		panelDatos.actualizarInterfaz(resultado);
	}

	/**
	 * Limpia todas las tuplas de todas las tablas de la base de datos de
	 * parranderos Muestra en el panel de datos el número de tuplas eliminadas de
	 * cada tabla
	 */
	public void limpiarBD() {
		try {
			// Ejecución de la demo y recolección de los resultados
			long eliminados[] = aloha.limpiarAlohAndes();

			// Generación de la cadena de caracteres con la traza de la ejecución de la demo
			String resultado = "\n\n************ Limpiando la base de datos ************ \n";
			resultado += eliminados[0] + " Gustan eliminados\n";
			resultado += eliminados[1] + " Sirven eliminados\n";
			resultado += eliminados[2] + " Visitan eliminados\n";
			resultado += eliminados[3] + " Bebidas eliminadas\n";
			resultado += eliminados[4] + " Tipos de bebida eliminados\n";
			resultado += eliminados[5] + " Bebedores eliminados\n";
			resultado += eliminados[6] + " Bares eliminados\n";
			resultado += "\nLimpieza terminada";

			panelDatos.actualizarInterfaz(resultado);
		} catch (Exception e) {
//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	/**
	 * Muestra la presentación general del proyecto
	 */
	public void mostrarPresentacionGeneral() {
		mostrarArchivo("data/00-ST-ParranderosJDO.pdf");
	}

	/**
	 * Muestra el modelo conceptual de Parranderos
	 */
	public void mostrarModeloConceptual() {
		mostrarArchivo("data/Modelo Conceptual Parranderos.pdf");
	}

	/**
	 * Muestra el esquema de la base de datos de Parranderos
	 */
	public void mostrarEsquemaBD() {
		mostrarArchivo("data/Esquema BD Parranderos.pdf");
	}

	/**
	 * Muestra el script de creación de la base de datos
	 */
	public void mostrarScriptBD() {
		mostrarArchivo("data/EsquemaParranderos.sql");
	}

	/**
	 * Muestra la arquitectura de referencia para Parranderos
	 */
	public void mostrarArqRef() {
		mostrarArchivo("data/ArquitecturaReferencia.pdf");
	}

	/**
	 * Muestra la documentación Javadoc del proyectp
	 */
	public void mostrarJavadoc() {
		mostrarArchivo("doc/index.html");
	}

	/**
	 * Muestra la información acerca del desarrollo de esta apicación
	 */
	public void acercaDe() {
		String resultado = "\n\n ************************************\n\n";
		resultado += " * Universidad	de	los	Andes	(Bogotá	- Colombia)\n";
		resultado += " * Departamento	de	Ingeniería	de	Sistemas	y	Computación\n";
		resultado += " * Curso: isis2304 - Sistemas Transaccionales\n";
		resultado += " * Proyecto: AlohAndes\n";
		resultado += " * @author Raul Rincon - Camilo Linares\n";
		resultado += " * Mayo de 2023\n";
		resultado += "\n ************************************\n\n";

		panelDatos.actualizarInterfaz(resultado);
	}

	/*
	 * **************************************************************** 
	 * Métodos privados para la presentación de resultados y otras operaciones
	 *****************************************************************/
	/**
	 * Genera una cadena de caracteres con la lista de los tipos de bebida recibida:
	 * una línea por cada tipo de bebida
	 * 
	 * @param lista - La lista con los tipos de bebida
	 * @return La cadena con una líea para cada tipo de bebida recibido
	 */
	private String listarOfertaComun(List<VOOfertaComun> lista) {
		String resp = "Las ofertas comunes existentes son:\n";
		int i = 1;
		for (VOOfertaComun tb : lista) {
			resp += i++ + ". " + tb.toString() + "\n";
		}
		return resp;
	}
	private String listarOfertaExclusiva(List<VOOfertaExclusiva> lista) {
		String resp = "Las ofertas exclusivas existentes son:\n";
		int i = 1;
		for (VOOfertaExclusiva tb : lista) {
			resp += i++ + ". " + tb.toString() + "\n";
		}
		return resp;
	}
	private String listarViviendaUniversitaria(List<VOViviendaUniversitaria> lista) {
		String resp = "Las Viviendas Universitarias existentes son:\n";
		int i = 1;
		for (VOViviendaUniversitaria tb : lista) {
			resp += i++ + ". " + tb.toString() + "\n";
		}
		return resp;
	}
	private String listarApartamento(List<VOApartamento> lista) {
		String resp = "Los Apartamentos existentes son:\n";
		int i = 1;
		for (VOApartamento tb : lista) {
			resp += i++ + ". " + tb.toString() + "\n";
		}
		return resp;
	}
	private String listarVivienda(List<VOVivienda> lista) {
		String resp = "Las Viviendas existentes son:\n";
		int i = 1;
		for (VOVivienda tb : lista) {
			resp += i++ + ". " + tb.toString() + "\n";
		}
		return resp;
	}
	private String listarHospedaje(List<VOHospedaje> lista) {
		String resp = "Los Hospedaje existentes son:\n";
		int i = 1;
		for (VOHospedaje tb : lista) {
			resp += i++ + ". " + tb.toString() + "\n";
		}
		return resp;
	}
	private String listarEmpresa(List<VOEmpresa> lista) {
		String resp = "Las Empresas existentes son:\n";
		int i = 1;
		for (VOEmpresa tb : lista) {
			resp += i++ + ". " + tb.toString() + "\n";
		}
		return resp;
	}
	private String listarVecino(List<VOVecino> lista) {
		String resp = "Los Vecinos existentes son:\n";
		int i = 1;
		for (VOVecino tb : lista) {
			resp += i++ + ". " + tb.toString() + "\n";
		}
		return resp;
	}
	private String listarAdminVivienda(List<VOAdminVivienda> lista) {
		String resp = "Los AdminViviendas existentes son:\n";
		int i = 1;
		for (VOAdminVivienda tb : lista) {
			resp += i++ + ". " + tb.toString() + "\n";
		}
		return resp;
	}
	private String listarPropietarioMiembro(List<VOPropietarioMiembro> lista) {
		String resp = "Los PropietarioMiembros existentes son:\n";
		int i = 1;
		for (VOPropietarioMiembro tb : lista) {
			resp += i++ + ". " + tb.toString() + "\n";
		}
		return resp;
	}
	private String listarCliente(List<VOCliente> lista) {
		String resp = "Los Clientes existentes son:\n";
		int i = 1;
		for (VOCliente tb : lista) {
			resp += i++ + ". " + tb.toString() + "\n";
		}
		return resp;
	}
	private String listarClienteMiembro(List<VOClienteMiembro> lista) {
		String resp = "Los ClienteMiembroComunidades existentes son:\n";
		int i = 1;
		for (VOClienteMiembro tb : lista) {
			resp += i++ + ". " + tb.toString() + "\n";
		}
		return resp;
	}
	private String listarReservaComun(List<VOReservaComun> lista) {
		String resp = "Las ReservaComuns existentes son:\n";
		int i = 1;
		for (VOReservaComun tb : lista) {
			resp += i++ + ". " + tb.toString() + "\n";
		}
		return resp;
	}
	private String listarContrato(List<VOContrato> lista) {
		String resp = "Los Contratos existentes son:\n";
		int i = 1;
		for (VOContrato tb : lista) {
			resp += i++ + ". " + tb.toString() + "\n";
		}
		return resp;
	}
	/**
	 * Genera una cadena de caracteres con la descripción de la excepcion e,
	 * haciendo énfasis en las excepcionsde JDO
	 * 
	 * @param e - La excepción recibida
	 * @return La descripción de la excepción, cuando es
	 *         javax.jdo.JDODataStoreException, "" de lo contrario
	 */
	private String darDetalleException(Exception e) {
		String resp = "";
		if (e.getClass().getName().equals("javax.jdo.JDODataStoreException")) {
			JDODataStoreException je = (javax.jdo.JDODataStoreException) e;
			return je.getNestedExceptions()[0].getMessage();
		}
		return resp;
	}

	/**
	 * Genera una cadena para indicar al usuario que hubo un error en la aplicación
	 * 
	 * @param e - La excepción generada
	 * @return La cadena con la información de la excepción y detalles adicionales
	 */
	private String generarMensajeError(Exception e) {
		String resultado = "************ Error en la ejecución\n";
		resultado += e.getLocalizedMessage() + ", " + darDetalleException(e);
		resultado += "\n\nRevise datanucleus.log y parranderos.log para más detalles";
		return resultado;
	}

	/**
	 * Limpia el contenido de un archivo dado su nombre
	 * 
	 * @param nombreArchivo - El nombre del archivo que se quiere borrar
	 * @return true si se pudo limpiar
	 */
	private boolean limpiarArchivo(String nombreArchivo) {
		BufferedWriter bw;
		try {
			bw = new BufferedWriter(new FileWriter(new File(nombreArchivo)));
			bw.write("");
			bw.close();
			return true;
		} catch (IOException e) {
//			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Abre el archivo dado como parámetro con la aplicación por defecto del sistema
	 * 
	 * @param nombreArchivo - El nombre del archivo que se quiere mostrar
	 */
	private void mostrarArchivo(String nombreArchivo) {
		try {
			Desktop.getDesktop().open(new File(nombreArchivo));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * **************************************************************** Métodos de
	 * la Interacción
	 *****************************************************************/
	/**
	 * Método para la ejecución de los eventos que enlazan el menú con los métodos
	 * de negocio Invoca al método correspondiente según el evento recibido
	 * 
	 * @param pEvento - El evento del usuario
	 */
	@Override
	public void actionPerformed(ActionEvent pEvento) {
		String evento = pEvento.getActionCommand();
		try {
			Method req = InterfazAlohAndesApp.class.getMethod(evento);
			req.invoke(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * **************************************************************** Programa
	 * principal
	 *****************************************************************/
	/**
	 * Este método ejecuta la aplicación, creando una nueva interfaz
	 * 
	 * @param args Arreglo de argumentos que se recibe por línea de comandos
	 */
	public static void main(String[] args) {
		try {

			// Unifica la interfaz para Mac y para Windows.
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
			InterfazAlohAndesApp interfaz = new InterfazAlohAndesApp();
			interfaz.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
