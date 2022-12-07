package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserService service = new UserServiceImpl();
        service.createUsersTable();
        for (int i = 1; i < 5; i++) {
            service.saveUser("Name" + i, "LastName" + i, (byte) i);
            System.out.println("User с именем – Name" + i + " добавлен в базу данных");
        }
        service.getAllUsers();
        service.cleanUsersTable();
        service.dropUsersTable();

    }
}
