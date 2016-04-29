package com.jvm.tucarta.forms;

import java.util.List;

import com.jvm.tucarta.model.Lugar;

public class LugarListaResponse {
    
    private String response_code;
    private String mensaje;
    private List<Lugar> lugares;

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
     * @return the lugares
     */
    public List<Lugar> getLugares() {
        return lugares;
    }

    /**
     * @param lugares the lugares to set
     */
    public void setLugares(List<Lugar> lugares) {
        this.lugares = lugares;
    }
    
    
    
}