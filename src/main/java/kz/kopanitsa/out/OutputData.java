package kz.kopanitsa.out;

import kz.kopanitsa.auth.UserAuth;
import kz.kopanitsa.in.InputData;
import kz.kopanitsa.model.Training;
import kz.kopanitsa.model.User;
import kz.kopanitsa.model.trainingType.*;
import kz.kopanitsa.service.TrainingService;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Класс, обеспечивающий основное взаимодействие с пользователем в консольном приложении.
 */
@NoArgsConstructor
public class OutputData {

    /**
     * Объект для взаимодействия с пользовательским вводом.
     */
    private InputData input = new InputData();

    /**
     * Объект для аутентификации и регистрации пользователей.
     */
    private UserAuth userAuth = new UserAuth();

    /**
     * Объект для выполнения операций над тренировками пользователей, таких как добавление, обновление, удаление и просмотр.
     */
    private TrainingService trainingService = new TrainingService();

    /**
     * Метод для ввода данных нового пользователя.
     *
     * @return объект User с введенными данными без данных о роли (Role)
     */
    public User enterUser() {
        // Ввод имени, логина и пароля пользователя
        System.out.println("Введите имя пользователя: ");
        String name = input.inputFromUserString();
        System.out.println("Введите логин: ");
        String login = input.inputFromUserString();
        System.out.println("Введите пароль: ");
        String password = input.inputFromUserString();
        return new User(name, login, password);
    }

    /**
     * Метод для ввода данных о тренировке.
     *
     * @return объект Training с введенными данными
     */
    public Training enterTraining() {
        // Ввод даты тренировки
        System.out.println("Введите дату тренировки (формат даты 'dd-MM-yyyy'): ");
        String date = input.inputFromUserString();
        // Ввод типа тренировки
        System.out.println("Введите тип тренировки: ");
        TrainingType trainingType = null;
        boolean isValidInputType = false;
        while (!isValidInputType) {
            System.out.println("Выберите нужную тренировку или введите другой тип тренировки:");
            System.out.println("1. Кардио тренировка");
            System.out.println("2. Силовая тренировка");
            System.out.println("3. Йога");
            System.out.println("4. Ввести другой тип тренировки");
            String choice = input.inputFromUserString();
            switch (choice) {
                case "1" -> {
                    System.out.println("Вы выбрали Кардио тренировка.");
                    trainingType = new CardioTraining();
                    isValidInputType = true;
                }
                case "2" -> {
                    System.out.println("Вы выбрали Силовая тренировка.");
                    trainingType = new StrengthTraining();
                    isValidInputType = true;
                }
                case "3" -> {
                    System.out.println("Вы выбрали Йога.");
                    trainingType = new YogaTraining();
                    isValidInputType = true;
                }
                case "4" -> {
                    System.out.println("Введите другой тип тренировки (название): ");
                    String type = input.inputFromUserString();
                    trainingType = new OtherTraining(type);
                    isValidInputType = true;
                }
                default -> System.out.println("Пожалуйста, выберите корректное значение.");
            }
        }
        // Ввод продолжительности тренировки
        System.out.println("Введите время продолжительности тренировки: ");
        int durationMinutes = input.inputFromUserInt();
        // Ввод количества потраченных на тренировке каллорий
        System.out.println("Введите количество потраченных каллорий: ");
        int caloriesBurned = input.inputFromUserInt();
        // Ввод дополнительных данных о тренировке по желанию пользователя
        HashMap<String, Integer> additionalInfo = new HashMap<>();
        boolean isValidInput = false;
        while (!isValidInput) {
            System.out.println("Хотите ввести дополнительные данные о тренировке: ");
            System.out.println("1. Да");
            System.out.println("2. Нет");
            String choice = input.inputFromUserString();
            switch (choice) {
                case "1" -> {
                    System.out.println("Введите тип дополнительных данных (например, 'расстояние'): ");
                    String description = input.inputFromUserString();
                    System.out.println("Введите числовую характеристику дополнительных данных (например, '4'): ");
                    Integer characteristic = input.inputFromUserInt();
                    additionalInfo.put(description, characteristic);
                    isValidInput = true;
                }
                case "2" -> {
                    isValidInput = true;
                }
                default -> System.out.println("Пожалуйста, выберите корректное значение.");
            }
        }
        return new Training(date, trainingType, durationMinutes, caloriesBurned, additionalInfo);
    }

    /**
     * Метод для аутентификации или регистрации пользователя.
     *
     * @return объект User, представляющий аутентифицированного пользователя
     */
    public User menuAuth() {
        User user = null;
        while (user==null) {
            System.out.println("Выберите действие:");
            System.out.println("1. Регистрация");
            System.out.println("2. Аутентификация");
            String choice = input.inputFromUserString();
            switch (choice) {
                case "1" -> {
                    System.out.println("Вы выбрали регистрацию.");
                    User enterUser = enterUser();
                    user = userAuth.registerUser(enterUser);
                }
                case "2" -> {
                    System.out.println("Вы выбрали аутентификацию.");
                    User enterUser = enterUser();
                    if (enterUser.getName().equals("Admin") && enterUser.getLogin().equals("admin") &
                            enterUser.getPassword().equals("123")) {
                        Map.Entry<User, List<Training>> entry = trainingService.getUserTrainingMap().entrySet().iterator().next();
                        user = entry.getKey();
                    } else {
                        user = userAuth.authenticateUser(enterUser);
                    }
                }
                default -> System.out.println("Пожалуйста, выберите 1 или 2.");
            }
        }
        return user;
    }

    /**
     * Метод для отображения основного меню приложения.
     *
     * @param user пользователь
     */
    public void menu(User user) {
        boolean isValidInput = false;
        while (!isValidInput) {
            System.out.println("Выберите действие:");
            System.out.println("1. Добавить тренировку");
            System.out.println("2. Изменить тренировку");
            System.out.println("3. Удалить тренировку");
            System.out.println("4. Посмотреть все тренировки");
            System.out.println("5. Посмотреть все тренировки одного типа");
            System.out.println("6. Посмотреть все тренировки за определенный период");
            System.out.println("7. Посмотреть дополнительную информацию по тренировкам определенного типа");
            System.out.println("8. Функции администратора");
            System.out.println("9. Выход из программы");
            String choice = input.inputFromUserString();
            switch (choice) {
                case "1" -> {
                    System.out.println("Вы выбрали добавление тренировки.");
                    Training training = enterTraining();
                    trainingService.addTraining(user, training);
                    isValidInput = true;
                }
                case "2" -> {
                    System.out.println("Вы выбрали изменение тренировки.");
                    Training training = enterTraining();
                    trainingService.updateTraining(user, training);
                    isValidInput = true;
                }
                case "3" -> {
                    System.out.println("Вы выбрали удаление тренировки.");
                    Training training = enterTraining();
                    trainingService.removeTraining(user, training);
                    isValidInput = true;
                }
                case "4" -> {
                    System.out.println("Вы выбрали посмотреть все тренировки.");
                    System.out.println(trainingService.getUserTrainings(user));
                    isValidInput = true;
                }
                case "5" -> {
                    System.out.println("Вы выбрали посмотреть все тренировки одного типа.");
                    System.out.println("Введите тип тренировки: ");
                    String type = input.inputFromUserString();
                    System.out.println(trainingService.getUserTrainingsByType(user, type));
                    isValidInput = true;
                }
                case "6" -> {
                    System.out.println("Вы выбрали посмотреть все тренировки все тренировки за определенный период.");
                    System.out.println("Введите начало периода (формат даты 'dd-MM-yyyy'): ");
                    String startDate = input.inputFromUserString();
                    System.out.println("Введите окончание периода (формат даты 'dd-MM-yyyy'): ");
                    String endDate = input.inputFromUserString();
                    System.out.println(trainingService.getUserTrainingsInDateRange(user, startDate, endDate));
                    isValidInput = true;
                }
                case "7" -> {
                    System.out.println("Вы выбрали посмотреть дополнительную информацию по тренировкам определенного типа.");
                    System.out.println("Введите тип тренировки: ");
                    String type = input.inputFromUserString();
                    System.out.println(trainingService.getAdditionalInfoStatistics(user, type));
                    isValidInput = true;
                }
                case "8" -> {
                    System.out.println("Вы выбрали функции администратора.");
                    if (user.getRole().getRole().equals("ADMIN")) {
                        boolean isValidAction = false;
                        while (!isValidAction) {
                            System.out.println("Выберите действие: ");
                            System.out.println("1. Посмотреть данные о тренировках всех пользователей");
                            System.out.println("2. Вернуться в основное меню.");
                            String choiceAdmin = input.inputFromUserString();
                            switch (choiceAdmin) {
                                case "1" -> {
                                    System.out.println(trainingService.getAllUserTrainings());
                                    isValidAction = true;
                                }
                                case "2" -> {
                                    isValidAction = true;
                                }
                                default -> System.out.println("Пожалуйста, выберите корректное значение.");
                            }
                        }
                    } else {
                        System.out.println("К сожалению, у вас нет прав администратора.");
                    }
                }
                case "9" -> {
                    System.out.println("Выход из программы");
                    input.getScanner().close();
                    return;
                }
                default -> System.out.println("Пожалуйста, выберите 1 или 2.");
            }
        }
    }

    /**
     * Метод для отображения приветственного сообщения.
     */
    public void greetings() {
        System.out.println("Приложение Training Diary (Дневник тренировок) позволит Вам " +
                "вносить информацию о ваших тренировках и отслеживать статистику своего програсса.\n" +
                "Поскольку приложение в настоящий момент поддерживает только кириллицу, проверьте, пожалуйста, " +
                "выбранный язык ввода.");
        System.out.println();
    }
}