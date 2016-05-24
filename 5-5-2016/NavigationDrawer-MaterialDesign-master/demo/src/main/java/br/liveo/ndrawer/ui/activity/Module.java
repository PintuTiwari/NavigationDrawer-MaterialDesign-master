package br.liveo.ndrawer.ui.activity;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Admin on 12-03-2016.
 */
public class Module {
    String moduleId;
   // Map<String,MenuDetails> menuDetailsMap = new HashMap<String, MenuDetails>();
   Map<String,MenuDetails> menuDetailsMap = new LinkedHashMap<String, MenuDetails>();



    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public Map<String, MenuDetails> getMenuDetailsMap() {
        return menuDetailsMap;
    }

    public void setMenuDetailsMap(Map<String, MenuDetails> menuDetailsMap) {
        this.menuDetailsMap = menuDetailsMap;
    }
}
