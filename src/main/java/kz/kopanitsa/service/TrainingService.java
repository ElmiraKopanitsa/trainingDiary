package kz.kopanitsa.service;

import kz.kopanitsa.model.Role;
import kz.kopanitsa.model.Training;
import kz.kopanitsa.model.User;
import kz.kopanitsa.model.trainingType.TrainingType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Класс, обеспечивающий основные операции с тренировками пользователей.
 */
@Getter
@Setter
@AllArgsConstructor
public class TrainingService {

    /**
     * Карта, связывающая пользователей с их списками тренировок.
     * Ключами являются объекты пользователей, а значениями - списки их тренировок.
     */
    private Map<User, List<Training>> userTrainingMap;

    /**
     * Конструктор по умолчанию.
     * Инициализирует пустую карту пользовательских тренировок.
     * При инициализации добавляется пользователь Администратор
     */
    public TrainingService() {

        this.userTrainingMap = new HashMap<>();
        userTrainingMap.put(new User("Admin", "admin", "123", new Role("ADMIN")),
                new ArrayList<>());
    }

    /**
     * Добавляет тренировку для указанного пользователя.
     * Если тренировка с таким типом уже существует на указанную дату, генерируется исключение.
     *
     * @param user пользователь, для которого добавляется тренировка
     * @param training тренировка, которая добавляется
     */
    public void addTraining(User user, Training training) {
        try {
            List<Training> userTrainings = userTrainingMap.getOrDefault(user, new ArrayList<>());
            boolean trainingExists = userTrainings.stream()
                    .anyMatch(existingTraining ->
                            existingTraining.getTrainingType().equals(training.getTrainingType()) &&
                                    existingTraining.getDate().equals(training.getDate()));
            if (!trainingExists) {
                userTrainings.add(training);
                userTrainingMap.put(user, userTrainings);
                System.out.println("Тренировка успешно добавлена.");
            } else {
                throw new Exception("Тренировка данного типа уже добавлена в указанную вами дату.");
            }
        } catch (Exception e) {
            System.out.println("Ошибка при добавлении тренировки: " + e.getMessage());
        }
    }

    /**
     * Метод для изменения ранее введенной пользователем тренировки.
     *
     * @param user пользователь, тренировки которого нужно получить
     * @param training тип тренировки
     */
    public void updateTraining(User user, Training training) {
        try {
            List<Training> userTrainings = userTrainingMap.get(user);
            if (userTrainings == null) {
                throw new Exception("Пользователь не найден.");
            }
            for (int i = 0; i < userTrainings.size(); i++) {
                Training existingTraining = userTrainings.get(i);
                if (existingTraining.getDate().equals(training.getDate()) &&
                        existingTraining.getTrainingType().equals(training.getTrainingType())) {
                    userTrainings.set(i, training);
                    System.out.println("Тренировка успешно изменена.");
                    return;
                }
            }
            throw new Exception("Данный вид тренировки не найден в выбранную вами дату.");
        } catch (Exception e) {
            System.out.println("Ошибка при обновлении тренировки: " + e.getMessage());
        }
    }

    /**
     * Метод для удаления ранее введенной пользователем тренировки.
     *
     * @param user пользователь, тренировки которого нужно получить
     * @param training тип тренировки
     */
    public void removeTraining(User user, Training training) {
        List<Training> userTrainings = userTrainingMap.getOrDefault(user, new ArrayList<>());
        userTrainings.remove(training);
        userTrainingMap.put(user, userTrainings);
        System.out.println("Тренировка успешно удалена.");
    }

    /**
     * Метод для получения списка всех тренировок пользователя.
     *
     * @param user пользователь, тренировки которого нужно получить
     * @return строка со списком всех тренировок пользователя
     */
    public String getUserTrainings(User user) {
        System.out.println("Список всех тренировок пользователя " + user.getName() + ":");
        List<Training> listUserTrainings = userTrainingMap.getOrDefault(user, new ArrayList<>());
        return statisticOfListTraining(listUserTrainings);

    }

    /**
     * Метод для получения списка тренировок пользователя определенного типа.
     *
     * @param user пользователь, тренировки которого нужно получить
     * @param trainingType тип тренировки
     * @return строка со списком тренировок пользователя определенного типа
     */
    public String getUserTrainingsByType(User user, String trainingType) {
        List<Training> trainingByType = userTrainingMap.getOrDefault(user, new ArrayList<>())
                .stream()
                .filter(training -> training.getTrainingType().toString().equals(trainingType))
                .toList();
        return statisticOfListTraining(trainingByType);
    }

    /**
     * Метод для получения списка тренировок пользователя в определенном периоде.
     *
     * @param user пользователь, тренировки которого нужно получить
     * @param startDate начальная дата периода
     * @param endDate конечная дата периода
     * @return строка со списком тренировок пользователя в определенном периоде
     */
    public String getUserTrainingsInDateRange(User user, String startDate, String endDate) {
        List<Training> userTrainings = userTrainingMap.getOrDefault(user, new ArrayList<>());
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date start = sdf.parse(startDate);
            Date end = sdf.parse(endDate);
            List<Training> userTrainingsInDateRange = userTrainings.stream()
                    .filter(training -> {
                        try {
                            Date trainingDate = sdf.parse(training.getDate());
                            return trainingDate.after(start) && trainingDate.before(end);
                        } catch (ParseException e) {
                            return false;
                        }
                    })
                    .toList();
            return statisticOfListTraining(userTrainingsInDateRange);
        } catch (ParseException e) {
            System.out.println("Ошибка при обработке даты.");
            return "Ошибка при обработке даты. Проверьте формат введенных данных (формат даты 'dd-MM-yyyy').";
        }
    }

    /**
     * Метод для получения дополнительной информации о тренировках пользователя определенного типа.
     *
     * @param user пользователь, тренировки которого нужно получить
     * @param trainingType тип тренировки
     * @return строка с дополнительной информацией о тренировках пользователя определенного типа
     */
    public String getAdditionalInfoStatistics(User user, String trainingType) {
        List<Training> userTrainings = userTrainingMap.getOrDefault(user, new ArrayList<>())
                .stream()
                .filter(training -> training.getTrainingType().toString().equals(trainingType))
                .toList();
        Map<String, Integer> additionalInfoStatistics = new HashMap<>();
        for (Training training : userTrainings) {
            Map<String, Integer> additionalInfo = training.getAdditionalInfo();
            for (Map.Entry<String, Integer> entry : additionalInfo.entrySet()) {
                String key = entry.getKey();
                int value = entry.getValue();
                additionalInfoStatistics.put(key, additionalInfoStatistics.getOrDefault(key, 0) + value);
            }
        }
        return additionalInfoStatistics.toString();
    }

    /**
     * Метод для получения списка всех тренировок всех пользователей.
     *
     * @return строка со списком всех тренировок всех пользователей
     */
    public String  getAllUserTrainings() {
        return userTrainingMap.toString();
    }

    /**
     * Преобразует список тренировок в строку и собирает общую статистику для тренировок одного типа.
     * Если в списке присутствуют тренировки одного типа, то вычисляет общее количество тренировок,
     * общее время продолжительности и общее количество потраченных калорий для каждого типа тренировки.
     *
     * @param trainings список тренировок
     * @return строка с общей статистикой для тренировок одного типа
     */
    private String statisticOfListTraining(List<Training> trainings) {
        Map<TrainingType, Training> statisticsMap = new HashMap<>();
        // Проходим по всем тренировкам и собираем статистику
        for (Training training : trainings) {
            TrainingType type = training.getTrainingType();
            int durationMinutes = training.getDurationMinutes();
            int caloriesBurned = training.getCaloriesBurned();
            // Если уже есть тренировка такого типа в статистике, обновляем её данные
            if (statisticsMap.containsKey(type)) {
                Training existingTraining = statisticsMap.get(type);
                int updatedDuration = existingTraining.getDurationMinutes() + durationMinutes;
                int updatedCalories = existingTraining.getCaloriesBurned() + caloriesBurned;
                existingTraining.setDurationMinutes(updatedDuration);
                existingTraining.setCaloriesBurned(updatedCalories);
            } else {
                // Иначе добавляем новую тренировку в статистику
                Training newTraining = new Training();
                newTraining.setTrainingType(type);
                newTraining.setDurationMinutes(durationMinutes);
                newTraining.setCaloriesBurned(caloriesBurned);
                statisticsMap.put(type, newTraining);
            }
        }
        // Формируем строку с результатами статистики
        StringBuilder result = new StringBuilder();
        for (Map.Entry<TrainingType, Training> entry : statisticsMap.entrySet()) {
            TrainingType type = entry.getKey();
            Training training = entry.getValue();
            result.append("Тренировка типа: ").append(type.getTrainingTypeName())
                    .append(", продолжительность: ").append(training.getDurationMinutes())
                    .append(" минут, потрачено каллорий: ").append(training.getCaloriesBurned())
                    .append(".\n");
        }
        return result.toString();
    }
}
