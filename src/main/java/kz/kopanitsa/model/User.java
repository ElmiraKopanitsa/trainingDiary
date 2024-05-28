package kz.kopanitsa.model;

import lombok.*;

/**
 * Класс, представляющий пользователя.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class User {

    /**
     * Имя пользователя.
     */
    private String name;

    /**
     * Логин пользователя.
     */
    private String login;

    /**
     * Пароль пользователя.
     */
    private String password;

    /**
     * Роль пользователя (Role).
     */
    private Role role;

    /**
     * Конструктор класса User.
     * Создает нового пользователя с указанным именем, логином и паролем,
     * используется для получения данных непосредственно от пользователя.
     *
     * @param name имя пользователя
     * @param login логин пользователя
     * @param password пароль пользователя
     */
    public User(String name, String login, String password) {
        this.name = name;
        this.login = login;
        this.password = password;
    }
}