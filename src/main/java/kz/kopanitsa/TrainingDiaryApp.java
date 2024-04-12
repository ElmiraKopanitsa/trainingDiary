package kz.kopanitsa;

import kz.kopanitsa.model.User;
import kz.kopanitsa.out.OutputData;

/**
 * Класс TrainingDiaryApp представляет собой основную точку входа в приложение ежедневника тренировок.
 * Он инициализирует пользователя с правами администратора и демонстрирует функциональность приложения.
 * Чтобы войти в приложение с правами администратора введите при аутентификации имя Admin, логин admin, пароль 123.
 */
public class TrainingDiaryApp {

    /**
     * Метод main инициализирует пользователя с правами администратора, создает экземпляр OutputData
     * и демонстрирует функциональность приложения ежедневника тренировок.
     *
     * @param args Аргументы командной строки, переданные в приложение.
     */
    public static void main(String[] args) {

        OutputData outputData = new OutputData();
        // Отображение приветственного сообщения
        outputData.greetings();
        // Отображение меню аутентификации
        User user = outputData.menuAuth();
        // Отображение основного меню
        outputData.menu(user);
    }
}