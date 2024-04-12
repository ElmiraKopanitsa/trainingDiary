package kz.kopanitsa.model.trainingType;

/**
 * Абстрактный класс, представляющий общий тип тренировки.
 *
 * Этот класс является базовым классом для различных типов тренировок и предоставляет абстрактный метод
 * для получения названия типа тренировки.
 */
public abstract class TrainingType {

    /**
     * Получает название типа тренировки.
     *
     * @return название типа тренировки (String)
     */
    public abstract String getTrainingTypeName();
}