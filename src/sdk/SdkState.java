/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sdk;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author farouk
 */
public class SdkState implements Serializable {
    
    private String openedProject = null;
        
    public void setOpenedProject(String name){
        openedProject = name;
    }
    
    public String getOpenedProject(){
        return openedProject;
    }
    
}
