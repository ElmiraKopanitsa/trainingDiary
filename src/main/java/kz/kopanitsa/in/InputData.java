package kz.kopanitsa.in;

import lombok.Getter;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Класс для ввода данных с клавиатуры.
 *
 * Обеспечивает методы для ввода строк и целых чисел.
 */
@Getter
public class InputData {

    /**
     * Объект Scanner для чтения данных с консоли.
     */
    private Scanner scanner;

    /**
     * Конструктор класса InputData.
     * Создает новый объект Scanner для чтения данных с консоли.
     */
    public InputData() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Считывает строку, введенную пользователем.
     *
     * @return введенная пользователем строка
     */
    public String inputFromUserString() {
        return scanner.next();
    }

    /**
     * Считывает целое число, введенное пользователем.
     *
     * @return введенное пользователем целое число
     */
    public int inputFromUserInt() {
        int number;
        while (true) {
            try {
                number = scanner.nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Ошибка: введите целое число.");
                scanner.nextLine();
            }
        }
        return number;
    }
}
