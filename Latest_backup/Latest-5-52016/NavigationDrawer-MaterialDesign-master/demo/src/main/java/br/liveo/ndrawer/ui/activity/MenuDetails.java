package br.liveo.ndrawer.ui.activity;

/**
 * Created by Admin on 12-03-2016.
 */
public class MenuDetails {
    String id;
    String companyId;
    String Menu;
    String SubMenu;
    String URL;
    String Order;
    String isHeading;
    String Module;
    String Role;
    String DisplyActivity;



    public String getDisplyActivity() {
        return DisplyActivity;
    }

    public void setDisplyActivity(String displyActivity) {
        DisplyActivity = displyActivity;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getMenu() {
        return Menu;
    }

    public void setMenu(String menu) {
        Menu = menu;
    }

    public String getSubMenu() {
        return SubMenu;
    }

    public void setSubMenu(String subMenu) {
        SubMenu = subMenu;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getOrder() {
        return Order;
    }

    public void setOrder(String order) {
        Order = order;
    }

    public String isHeading() {
        return isHeading;
    }

    public void setIsHeading(String isHeading) {
        this.isHeading = isHeading;
    }

    public String getModule() {
        return Module;
    }

    public void setModule(String module) {
        Module = module;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    @Override
    public String toString() {
        return "Id "+id+"DisplyActivity"+DisplyActivity+"CompanyId "+companyId+"Menu "+Menu+"SubMenu "+SubMenu+"URL "+URL+"Order "+Order+"isHeading "+isHeading+"Module "+Module+"Role "+Role;
    }
}
