package com.jvm.tucarta.services;

public class Servicio {
	
	/**
	 * Servicio de login
	 */
	public static final String Login = "https://servidortucarta.herokuapp.com/usuarios/login";
	
	/**
	 * Servicio de registro de usuario
	 */
	public static final String Registro = "https://servidortucarta.herokuapp.com/usuarios/registrar";
	
	/**
	 * Servicio de listar lugares para comer
	 */
	public static final String ListaLugares = "https://servidortucarta.herokuapp.com/lugares/lista";

	/**
	 * Servicio de obtener carta de lugar seleccionado
	 */
	public static final String CartaLugarSelec = "https://servidortucarta.herokuapp.com/lugares/carta";
	
	/**
	 * Servicio de obtener carta de lugar en el que me encuentro
	 */
	public static final String CartaOrden = "https://servidortucarta.herokuapp.com/orden/carta";
	

}
