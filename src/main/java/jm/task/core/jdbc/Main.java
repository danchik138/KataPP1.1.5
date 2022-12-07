package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        UserService service = new UserServiceImpl();
        service.saveUser("Sam", "Vodka", (byte) 99);
        service.saveUser("Sam", "Putin", (byte) 99);
        System.out.println(service.getAllUsers());
    }
}
