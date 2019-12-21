package pl.com.app.service.manage;


import pl.com.app.connection.DataReader;
import pl.com.app.service.menu.Menu;
import pl.com.app.service.menu.TicketSalesSimulator;

import java.util.List;

public interface Manage<T> {
    void deleteByCustom();
    void deleteAll();
    void updateOne();
    void updateAll();
    void findByCustom();
    void findAll();
    String getCriteriums();
    void sortBy(List<T> items);

    static void manageMoviesCustomers(String manageName, Manage manage) {
        String data = null;
        do{
            System.out.println(TicketSalesSimulator.PREFIX);
            System.out.println(manageName);
            System.out.println("Napisz, co chcesz wykonać.");
            Menu manageMenu = Menu.builder().menuList(Menu.createManageMenu()).build();
            System.out.println(Menu.showMenu(manageMenu.getMenuList()));
            data = new DataReader().getString();
            System.out.println(TicketSalesSimulator.PREFIX);
            retrieveManageData(data, manage);
        } while (!data.equalsIgnoreCase("q"));
        System.out.println("Koniec. " + manageName);
    }

    static void retrieveManageData(String data, Manage manage) {
        switch (data){
            case "do":{
                manage.deleteByCustom();
                break;
            }
            case "da":{
                manage.deleteAll();
                break;
            }
            case "uo":{
                manage.updateOne();
                break;
            }
            case "ua":{
                manage.updateAll();
                break;
            }
            case "fo":{
                manage.findByCustom();
                break;
            }
            case "fa":{
                manage.findAll();
                break;
            }
            case "q":{
                break;
            }
            default:
                System.out.println("Niepopoprawny kod menu. Spróbuj jeszcze raz.");
        }
    }
}
