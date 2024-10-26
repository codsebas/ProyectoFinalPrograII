/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectofinal.modelos;

import proyectofinal.vistas.VistaReporteDelDia;

/**
 *
 * @author javie
 */
public class ModeloReportesDia {

    VistaReporteDelDia vista;

    public ModeloReportesDia( ) {
    }

    public ModeloReportesDia(VistaReporteDelDia vista) {
        this.vista = vista;
    }

    public VistaReporteDelDia getVista() {
        return vista;
    }

    public void setVista(VistaReporteDelDia vista) {
        this.vista = vista;
    }
    
    
}
