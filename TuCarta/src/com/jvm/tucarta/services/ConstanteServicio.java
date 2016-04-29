package com.jvm.tucarta.services;

public class ConstanteServicio {

	/**
	 * La llamada al servicio fue exitosa
	 */
	public static final String SERVICIO_OK = "true";

	/**
	 * La llamada al servicio fallo
	 */
	public static final String SERVICIO_ERROR = "false";

	/**
	 * Mensaje a mostrar cuando no se puede salir a internet para comunicar al
	 * servicio
	 */
	public static final String MENSAJE_PROBLEMA_CONEXION = "No se pudo conectar con el servidor. Revise su conexión.";

	/**
	 * Mensaje de servicio no disponible
	 */
	public static final String MENSAJE_SERVICIO_NO_DISPONIBLE = "El servicio solicitado no está disponible en el servidor.";
	
	/**
	 * Error al recibir mensaje
	 * */
	public static final String ERROR_RECIBIR_MENSAJE = "Hubo un problema con el servidor.";
}
