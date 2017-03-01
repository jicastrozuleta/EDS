/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package junittest;

import co.com.servicentroguerrero.backup.BackUp;
import java.io.File;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *  Test para comprobar el funcionamientos de la clase que genera y restaura los BackUp's
 * @author JICZ4
 */
public class BackUpJUnitTest {
    
    public BackUpJUnitTest() {
    }


  
    /**
     * Prueba unitaria para comprobar el funciomamiento de generar el backUp
     */
    @Test
    public void generarBackUp(){
        boolean condition = BackUp.generarBackUp();
        assertTrue(condition);
    }
    
    
    /**
     * Prueba unitario para comprobar la restauracion del archivo de backUp
     */
    @Test
    public void restaurarBackUp(){
        boolean condition = false;
        File file = new File("C:\\backUpServicentro\\backup_2017-01-23.bkp");
        if(file.exists())
            condition = BackUp.restaurarBackup(file.getAbsolutePath());
        assertTrue(!condition);
    }
}
