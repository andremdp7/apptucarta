package com.jvm.tucarta.forms;

import java.util.List;
import com.jvm.tucarta.model.*;

public class LugarCartaResponse {
    
    private String response_code;
    private String mensaje;
    private Integer id_lugar;
    private List<ItemCarta> items;
    private List<Seccion> secciones;
    private String logo_url;
    private String color_nombres;
    private String color_descripcion;
    private String color_fondo;

    /**
     * @return the response_code
     */
    public String getResponse_code() {
        return response_code;
    }

    /**
     * @param response_code the response_code to set
     */
    public void setResponse_code(String response_code) {
        this.response_code = response_code;
    }

    /**
     * @return the mensaje
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * @param mensaje the mensaje to set
     */
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    /**
     * @return the id_lugar
     */
    public Integer getId_lugar() {
        return id_lugar;
    }

    /**
     * @param id_lugar the id_lugar to set
     */
    public void setId_lugar(Integer id_lugar) {
        this.id_lugar = id_lugar;
    }

    /**
     * @return the items
     */
    public List<ItemCarta> getItems() {
        return items;
    }

    /**
     * @param items the items to set
     */
    public void setItems(List<ItemCarta> items) {
        this.items = items;
    }

    /**
     * @return the secciones
     */
    public List<Seccion> getSecciones() {
        return secciones;
    }

    /**
     * @param secciones the secciones to set
     */
    public void setSecciones(List<Seccion> secciones) {
        this.secciones = secciones;
    }

    /**
     * @return the logo_url
     */
    public String getLogo_url() {
        return logo_url;
    }

    /**
     * @param logo_url the logo_url to set
     */
    public void setLogo_url(String logo_url) {
        this.logo_url = logo_url;
    }

    /**
     * @return the color_nombres
     */
    public String getColor_nombres() {
        return color_nombres;
    }

    /**
     * @param color_nombres the color_nombres to set
     */
    public void setColor_nombres(String color_nombres) {
        this.color_nombres = color_nombres;
    }

    /**
     * @return the color_descripcion
     */
    public String getColor_descripcion() {
        return color_descripcion;
    }

    /**
     * @param color_descripcion the color_descripcion to set
     */
    public void setColor_descripcion(String color_descripcion) {
        this.color_descripcion = color_descripcion;
    }

    /**
     * @return the color_fondo
     */
    public String getColor_fondo() {
        return color_fondo;
    }

    /**
     * @param color_fondo the color_fondo to set
     */
    public void setColor_fondo(String color_fondo) {
        this.color_fondo = color_fondo;
    }
    
}
