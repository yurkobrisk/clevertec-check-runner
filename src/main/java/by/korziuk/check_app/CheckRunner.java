package by.korziuk.check_app;

import by.korziuk.check_app.data.Data;
import by.korziuk.check_app.factory.Check;
import by.korziuk.check_app.factory.CheckFactory;
import by.korziuk.check_app.factory.CustomerCheckFactory;

public class CheckRunner {

    public static Data data;

    public String init() {
        data = new Data();
        return "init CheckRunner ...";
    }

    public static void main(String[] args) {
        System.out.println(new CheckRunner().init());

        CheckFactory checkFactory = new CustomerCheckFactory();
        Check check = checkFactory.createCheck();
        check.create(args);
        check.printCheck();
    }
}
