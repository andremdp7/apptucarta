package com.jvm.tucarta.model;

public class Lugar {
	private int id_lugar;
	private String nombre;
	private String descripcion;
	private String imagen_url;
	
	public Lugar(int id, String nombre, String descripcion, String imagen){
		this.id_lugar = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.imagen_url = imagen;
	}
	
	public int getId() {
		return id_lugar;
	}
	public void setId(int id) {
		this.id_lugar = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getImagen() {
		return imagen_url;
	}
	public void setImagen(String imagen) {
		this.imagen_url = imagen;
	}

}
