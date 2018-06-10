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
public class ProjectProperty implements Serializable{
    private String projectName = null;
    private String projectDirectory = null;
    private String appName = null;
    private ArrayList<String> projectFiles = null;
    
    public void setProjectName(String name){
        projectName = name;
    }
    public String  getProjectName(){
        return projectName;
    }
}
