package kz.kopanitsa.auth;

import kz.kopanitsa.model.User;
import kz.kopanitsa.model.Role;

import java.util.HashMap;
import java.util.Map;

/**
 * Класс, представляющий систему аутентификации пользователей.
 */
public class UserAuth {

    /**
     * Хранит зарегистрированных пользователей.
     *
     * Пользователи хранятся в виде пар "логин"-"пользователь".
     * Данное хранилище находится в памяти приложения и не сохраняет данные между перезапусками.
     */
    private Map<String, User> users;

    /**
     * Конструктор класса UserAuth. Инициализирует систему аутентификации.
     */
    public UserAuth() {
        this.users = new HashMap<>();
        // Добавляем администратора в систему аутентификации
        users.put("admin", new User("Admin", "admin", "123", new Role("ADMIN")));
    }

    /**
     * Регистрирует нового пользователя в системе аутентификации.
     *
     * @param user новый пользователь для регистрации
     * @return сохраненный пользователь, если регистрация прошла успешно, в противном случае null
     */
    public User registerUser(User user) {
        try {
            String username = user.getName();
            String login = user.getLogin();
            if (users.containsKey(login)) {
                    throw new Exception("Пользователь с таким логином уже существует.");
                }
            String password = user.getPassword();
            Role role = new Role("USER");
            User userSave = new User(username, login, password, role);
            users.put(login, userSave);
            System.out.println("Пользователь успешно зарегистрирован.");
            return userSave;
        } catch (Exception e) {
            System.out.println("Произошла ошибка при регистрации пользователя: " + e.getMessage());
            return null;
        }
    }

    /**
     * Аутентифицирует пользователя в системе.
     *
     * @param user пользователь для аутентификации
     * @return аутентифицированный пользователь, если аутентификация прошла успешно, в противном случае null
     */
    public User authenticateUser(User user) {
        try {
            String login = user.getLogin();
           if (!users.containsKey(user.getLogin())) {
                    throw new Exception("Пользователь с таким логином не найден.");
           }
            User userExist = users.get(login);
           if (!userExist.getPassword().equals(user.getPassword())) {
                    throw new Exception("Пароль введен неверно.");
           }
            System.out.println("Вы успешно вошли в приложение.");
            return user;
        } catch (Exception e) {
            System.out.println("Произошла ошибка при аутентификации пользователя: " + e.getMessage());
            return null;
        }
    }
}
