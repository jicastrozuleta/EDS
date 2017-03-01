/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.formulavolumen;


/**
 * Clase que permite realizar el calculo de volumen de combustible 
 * en un cilindro.
 *
 * @author JICZ4
 */
public class VolumenCombustible {
    
    
    /**
     * Metodo que permite calcular el volumen de combustible que hay
     * en un cilindro <br> una vez ingresado la altura de combustible (medida de la regla), longitud 
     * y radio del cilindro.
     * @param lecturaRegla es la lectura que lee el operario al insertar la regla en el cilindro para obtener su altura.
     * @param longitudCilindro es la longitud del cilindro.
     * @param radioCilindro es el radio del cilindro.
     * @return 
     */
    public double calcularVolumen(final double lecturaRegla, final double longitudCilindro, final double radioCilindro) {
        double angulo = calcularAngulo(lecturaRegla, radioCilindro);
        double areaSector = calcularAreaDelSector(radioCilindro, angulo);
        double baseTriangulo = calcularBaseDelTriangulo(angulo, radioCilindro, lecturaRegla);
        return (areaSector - baseTriangulo) * longitudCilindro;
    }
    
    
    /**
     * El angulo se calcula =
     * 2 * acos((radioCilindro - lecturaRegla)/radioCilindro)
     * El angulo debe ser entregado en grados.
     * @param lecturaRegla
     * @param longitudCilindro
     * @param radioCilindro
     * @return el angulo del cilindro.
     */
    private double calcularAngulo (final double lecturaRegla, final double radioCilindro){
        double anguloInverso = (radioCilindro - lecturaRegla) / radioCilindro;
        return Math.toDegrees(Math.acos(anguloInverso)*2.0);
    }
    
    
    /**
     * 
     * @param radioCilindro
     * @param angulo
     * @return 
     */
    private double calcularAreaDelSector(final double radioCilindro, final double angulo){
        return (Math.PI * Math.pow(radioCilindro,2) * angulo)/360.0;
    }
    
    /**
     * 
     * @param angulo
     * @param radioCilindro
     * @param lecturaRegla
     * @return 
     */
    private double calcularBaseDelTriangulo(final double angulo, final double radioCilindro, final double lecturaRegla){
        return ((2 * Math.sin(Math.toRadians(angulo/2)) * radioCilindro * (radioCilindro - lecturaRegla))/2);
    }
}
