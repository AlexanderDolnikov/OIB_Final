package Lab2;

import java.util.HashMap;
import java.util.Map;

public class Lab2_3_VigenereCipher {

    // Алфавит, используемый для шифрования и дешифрования
    private static final String ALPHABET = "АБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯабвгдежзийклмнопрстуфхцчшщъыьэюя ,.";
    private static final int M = ALPHABET.length(); // Размер алфавита
    private static Map<Character, Integer> charToInt = new HashMap<>(); // Карта для отображения символов в их числовое представление
    private static Map<Integer, Character> intToChar = new HashMap<>(); // Карта для отображения чисел в их символы

    static {
        // Инициализация карт charToInt и intToChar
        for (int i = 0; i < ALPHABET.length(); i++) {
            charToInt.put(ALPHABET.charAt(i), i);
            intToChar.put(i, ALPHABET.charAt(i));
        }
    }

    public static void main(String[] args) {
        // Исходный текст и ключевая фраза
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
        String keyPhrase = "ЛашкинДольниковМатвейАлександр";

        // Часть А: Шифрование текста
        String encryptedText = encrypt(text, keyPhrase);
        System.out.println("Зашифрованный текст: " + encryptedText);

        // Часть Б: Расшифровка текста
        String decryptedText = decrypt(encryptedText, keyPhrase);
        System.out.println("Расшифрованный текст: " + decryptedText);
    }

    // Функция шифрования
    private static String encrypt(String text, String keyPhrase) {
        StringBuilder encrypted = new StringBuilder();
        // Расширение ключевой фразы до длины текста
        String extendedKey = extendKey(keyPhrase, text.length());

        for (int i = 0; i < text.length(); i++) {
            char textChar = text.charAt(i);
            char keyChar = extendedKey.charAt(i);

            // Проверка наличия символов в алфавите
            if (charToInt.containsKey(textChar) && charToInt.containsKey(keyChar)) {
                // Шифрование символа с использованием таблицы Виженера
                int encryptedIndex = (charToInt.get(textChar) + charToInt.get(keyChar)) % M;
                encrypted.append(intToChar.get(encryptedIndex));
            } else {
                encrypted.append(textChar); // Если символ не в алфавите, оставить его без изменений
            }
        }
        return encrypted.toString();
    }

    // Функция расшифровки
    private static String decrypt(String text, String keyPhrase) {
        StringBuilder decrypted = new StringBuilder();
        // Расширение ключевой фразы до длины текста
        String extendedKey = extendKey(keyPhrase, text.length());

        for (int i = 0; i < text.length(); i++) {
            char textChar = text.charAt(i);
            char keyChar = extendedKey.charAt(i);

            // Проверка наличия символов в алфавите
            if (charToInt.containsKey(textChar) && charToInt.containsKey(keyChar)) {
                // Дешифрование символа с использованием таблицы Виженера
                int decryptedIndex = (charToInt.get(textChar) - charToInt.get(keyChar) + M) % M;
                decrypted.append(intToChar.get(decryptedIndex));
            } else {
                decrypted.append(textChar); // Если символ не в алфавите, оставить его без изменений
            }
        }
        return decrypted.toString();
    }

    // Функция для циклического расширения ключевой фразы до длины текста
    private static String extendKey(String key, int length) {
        StringBuilder extendedKey = new StringBuilder();
        int keyLength = key.length();

        // Циклическое добавление символов ключевой фразы
        for (int i = 0; i < length; i++) {
            extendedKey.append(key.charAt(i % keyLength));
        }
        return extendedKey.toString();
    }
}
