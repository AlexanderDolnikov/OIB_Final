package Lab2;

import java.util.HashMap;
import java.util.Map;

public class Lab2_1_AffineCipher {

    private static final int M = 68; // Обновляем модуль для нового алфавита
    private static final int N = 21;//variant
    private static final int A = N + 2;
    private static final int B = (N - 25 + M) % M;
    private static Map<Character, Integer> charToInt = new HashMap<>();
    private static Map<Integer, Character> intToChar = new HashMap<>();
    private static final int A_INV;

    static {
        // Расширенный алфавит с заглавными и строчными буквами, пробелом и знаками препинания (Часть А)
        String chars = "АБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯабвгдежзийклмнопрстуфхцчшщъыьэюя ,.";
        for (int i = 0; i < chars.length(); i++) {
            charToInt.put(chars.charAt(i), i);
            intToChar.put(i, chars.charAt(i));
        }
        A_INV = findModInverse(A, M); // Вычисляем обратный элемент для A по модулю M
    }

    public static void main(String[] args) {
        // Исходный текст
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

        // Часть Б: Шифрование текста
        String encryptedText = encrypt(text);
        System.out.println("Зашифрованный текст: " + encryptedText);

        // Часть Г: Расшифровка текста
        String decryptedText = decrypt(encryptedText);
        System.out.println("Расшифрованный текст: " + decryptedText);

        // Часть Д: Показ использования методов extendedGCD и findModInverse
        int[] gcdResult = extendedGCD(A, M);
        System.out.println("НОД " + A + " и " + M + ": " + gcdResult[2]);
        System.out.println("Коэффициенты: x = " + gcdResult[0] + ", y = " + gcdResult[1]);
        System.out.println("Обратный элемент " + A + " по модулю " + M + ": " + A_INV);
    }

    // Функция шифрования
    private static String encrypt(String text) {
        StringBuilder encrypted = new StringBuilder();
        for (char ch : text.toCharArray()) {
            if (charToInt.containsKey(ch)) {
                int x = charToInt.get(ch);
                int y = (A * x + B) % M;
                encrypted.append(intToChar.get(y));
            } else {
                encrypted.append(ch); // Добавляем символы без изменений, если они не в карте
            }
        }
        return encrypted.toString();
    }

    // Функция расшифровки
    private static String decrypt(String text) {
        StringBuilder decrypted = new StringBuilder();
        for (char ch : text.toCharArray()) {
            if (charToInt.containsKey(ch)) {
                int y = charToInt.get(ch);
                int x = (A_INV * (y - B + M)) % M;
                decrypted.append(intToChar.get(x));
            } else {
                decrypted.append(ch); // Добавляем символы без изменений, если они не в карте
            }
        }
        return decrypted.toString();
    }

    // Функция для нахождения обратного элемента с использованием расширенного алгоритма Евклида (Часть Б и Г)
    public static int findModInverse(int a, int m) {
        int[] result = extendedGCD(a, m);
        if (result[2] != 1) {
            throw new IllegalArgumentException("Обратный элемент не существует");
        } else {
            return (result[0] % m + m) % m;
        }
    }

    // Расширенный алгоритм Евклида (Часть Б)
    public static int[] extendedGCD(int a, int b) {
        if (b == 0) {
            return new int[]{1, 0, a};
        } else {
            int[] result = extendedGCD(b, a % b);
            int x = result[0];
            int y = result[1];
            int gcd = result[2];
            return new int[]{y, x - (a / b) * y, gcd};
        }
    }
}



