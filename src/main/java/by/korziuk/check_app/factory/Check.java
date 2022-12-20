package by.korziuk.check_app.factory;

import by.korziuk.check_app.exception.IncorrectDataException;
import by.korziuk.check_app.exception.NoDataException;

public interface Check {
    void create(String[] data) throws IncorrectDataException, NoDataException;

    void printCheck();
}
