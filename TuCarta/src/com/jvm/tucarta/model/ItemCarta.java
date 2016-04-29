package com.jvm.tucarta.model;

import java.io.Serializable;

public class ItemCarta implements Serializable{
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id_item;
    private String nombre;
    private String descripcion;
    private Double precio;
    private String imagen_url;
    private Integer id_seccion;
    private Integer cantidad;
    
    public ItemCarta(){
        
    }
    
    public ItemCarta(Integer id_item, String nombre, String descripcion, Double precio, String imagen_url,Integer id_seccion){
        this.id_item = id_item;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.imagen_url = imagen_url;
        this.id_seccion = id_seccion;
        
    }

    /**
     * @return the id_item
     */
    public Integer getId_item() {
        return id_item;
    }

    /**
     * @param id_item the id_item to set
     */
    public void setId_item(Integer id_item) {
        this.id_item = id_item;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the precio
     */
    public Double getPrecio() {
        return precio;
    }

    /**
     * @param precio the precio to set
     */
    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    /**
     * @return the imagen_url
     */
    public String getImagen_url() {
        return imagen_url;
    }

    /**
     * @param imagen_url the imagen_url to set
     */
    public void setImagen_url(String imagen_url) {
        this.imagen_url = imagen_url;
    }

    /**
     * @return the id_seccion
     */
    public Integer getId_seccion() {
        return id_seccion;
    }

    /**
     * @param id_seccion the id_seccion to set
     */
    public void setId_seccion(Integer id_seccion) {
        this.id_seccion = id_seccion;
    }

    public Integer getCantidad() {
		return cantidad;
	}

    public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
    
    
    
}
