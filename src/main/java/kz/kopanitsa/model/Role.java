package kz.kopanitsa.model;

import lombok.*;

/**
 * Класс, представляющий роль пользователя.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Role {

    /**
     * Название роли пользователя (String).
     */
    private String role;
}
