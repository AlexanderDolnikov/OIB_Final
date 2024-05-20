package Lab2;

import java.util.Arrays;

public class Lab2_2_AffinePermutation {

    private static final int M = 61; // Размер блока шифрования
    private static final int N = 21; // Вариант
    private static final int A = (N + 2); // Параметр A для аффинного шифра
    private static final int B = (N - 25 + M) % M; // Параметр B для аффинного шифра, приведенный по модулю M
    private static final int A_INV; // Обратный элемент для A по модулю M

    static {
        A_INV = findModInverse(A, M); // Вычисляем обратный элемент для A по модулю M
    }

    public static void main(String[] args) {
        String text = "В шифре Виженера, как было отмечено, мы имеем дело с " +
                      "последовательностью сдвигов, циклически повторяющейся. Основная идея " +
                      "заключается в следующем. Создается таблица Виженера размером эн на эн, " +
                      "где эн есть число знаков в используемом алфавите. Эти знаки могут " +
                      "включать не только буквы, но и, например, пробел или иные знаки. В " +
                      "первой строке таблицы записывается весь используемый алфавит. Каждая " +
                      "последующая строка получается из предыдущей циклическим сдвигом " +
                      "последней на один символ влево. Таким образом, при мощности алфавита " +
                      "английского языка, равной двадцать шесть, необходимо выполнить " +
                      "последовательно двадцать пять сдвигов для формирования всей таблицы. " +
                      "А можно использовать простую модулярную арифметику и не составлять " +
                      "таблицу.";

        // Часть А: Шифрование текста
        String encryptedText = encrypt(text);
        System.out.println("Зашифрованный текст: " + encryptedText);

        // Часть Б: Расшифровка текста
        String decryptedText = decrypt(encryptedText);
        System.out.println("Расшифрованный текст: " + decryptedText);
    }

    // Функция шифрования
    private static String encrypt(String text) {
        StringBuilder encrypted = new StringBuilder();
        int len = text.length();
        // Обработка текста блоками длиной M
        for (int i = 0; i < len; i += M) {
            String block = text.substring(i, Math.min(len, i + M));
            encrypted.append(encryptBlock(block));
        }
        return encrypted.toString();
    }

    // Функция шифрования блока
    private static String encryptBlock(String block) {
        char[] encryptedBlock = new char[M];
        Arrays.fill(encryptedBlock, ' '); // Заполняем массив пробелами для последних блоков

        for (int x = 0; x < block.length(); x++) {
            // Вычисляем новое положение символа в блоке с использованием аффинного преобразования
            int newIndex = (A * x + B) % M;
            encryptedBlock[newIndex] = block.charAt(x);
        }
        return new String(encryptedBlock);
    }

    // Функция расшифровки
    private static String decrypt(String text) {
        StringBuilder decrypted = new StringBuilder();
        int len = text.length();
        // Обработка зашифрованного текста блоками длиной M
        for (int i = 0; i < len; i += M) {
            String block = text.substring(i, Math.min(len, i + M));
            decrypted.append(decryptBlock(block));
        }
        return decrypted.toString();
    }

    // Функция расшифровки блока
    private static String decryptBlock(String block) {
        char[] decryptedBlock = new char[M];
        Arrays.fill(decryptedBlock, ' '); // Заполняем массив пробелами для последних блоков

        for (int y = 0; y < block.length(); y++) {
            // Вычисляем исходное положение символа в блоке с использованием обратного аффинного преобразования
            int originalIndex = (A_INV * (y - B + M)) % M;
            if (originalIndex < block.length()) {
                decryptedBlock[originalIndex] = block.charAt(y);
            }
        }
        return new String(decryptedBlock).trim(); // Удаляем лишние пробелы в конце
    }

    // Функция для нахождения обратного элемента с использованием расширенного алгоритма Евклида
    public static int findModInverse(int a, int m) {
        int[] result = extendedGCD(a, m);
        if (result[2] != 1) { // Проверяем, что НОД равен 1
            throw new IllegalArgumentException("Обратный элемент не существует");
        } else {
            return (result[0] % m + m) % m; // Возвращаем обратный элемент
        }
    }

    // Расширенный алгоритм Евклида для нахождения НОД и коэффициентов для линейной комбинации чисел
    public static int[] extendedGCD(int a, int b) {
        if (b == 0) {
            return new int[]{1, 0, a}; // Возвращаем начальные коэффициенты и НОД
        } else {
            int[] result = extendedGCD(b, a % b); // Рекурсивный вызов
            int x = result[0];
            int y = result[1];
            int gcd = result[2];
            return new int[]{y, x - (a / b) * y, gcd}; // Обновляем коэффициенты
        }
    }
}
