package com.jvm.tucarta.model;

public class Seccion {
    
    private Integer id_seccion;
    private String nombre;
    
    public Seccion(){
        
    }
    
    public Seccion(Integer id_seccion, String nombre){
        this.id_seccion = id_seccion;
        this.nombre = nombre;
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
    
    
    
}
