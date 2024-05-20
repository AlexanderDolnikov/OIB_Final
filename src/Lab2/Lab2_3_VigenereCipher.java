package Lab2;/*
public class VigenereCipher {
    private static final int M = 41;
    private static final char[] ALPHABET = {
            '�', '�', '�', '�', '�', '�', '�', '�', '�', '�', '�', '�', '�', '�', '�', '�', '�', '�', '�', '�', '�', '�', '�', '�', '�', '�', '�', '�', '�', '�', '�', '�', '�',
            '�', '�', '�', '�', '�', '�', '�', '�', '�', '�', '�', '�', '�', '�', '�', '�', '�', '�', '�', '�', '�', '�', '�', '�', '�', '�', '�', '�', '�', '�', '�', '�', '�',
            ' ', '.', ',', '!', '?', '-', ':', ';'
    };

    private static int getIndex(char c) {
        for (int i = 0; i < ALPHABET.length; i++) {
            if (ALPHABET[i] == c) {
                return i;
            }
        }
        throw new IllegalArgumentException("Character not found in alphabet: " + c);
    }

    private static char getChar(int index) {
        return ALPHABET[index];
    }

    public static String generateKey(String text, String key) {
        StringBuilder newKey = new StringBuilder(key);
        while (newKey.length() < text.length()) {
            newKey.append(key);
        }
        return newKey.toString().substring(0, text.length());
    }

    public static String encrypt(String text, String key) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            int x = (getIndex(text.charAt(i)) + getIndex(key.charAt(i))) % M;
            result.append(getChar(x));
        }
        return result.toString();
    }

    public static String decrypt(String text, String key) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            int x = (getIndex(text.charAt(i)) - getIndex(key.charAt(i)) + M) % M;
            result.append(getChar(x));
        }
        return result.toString();
    }

    public static void main(String[] args) {
        String text = "� ����� ��������, ��� ���� ��������, �� ����� ���� � ������������������� �������, ���������� �������������. �������� ���� ����������� � ���������. ��������� ������� �������� �������� �� �� ��, ��� �� ���� ����� ������ � ������������ ��������. ��� ����� ����� �������� �� ������ �����, �� �, ��������, ������ ��� ���� �����. � ������ ������ ������� ������������ ���� ������������ �������. ������ ����������� ������ ���������� �� ���������� ����������� ������� ��������� �� ���� ������ �����. ����� �������, ��� �������� �������� ����������� �����, ������ �������� �����, ���������� ��������� ��������������� �������� ���� ������� ��� ������������ ���� �������. � ����� ������������ ������� ���������� ���������� � �� ���������� �������.";

        String keyPhrase = "������������������������������"; // �������� �� ���� ���
        String key = generateKey(text, keyPhrase);

        String encrypted = encrypt(text, key);
        System.out.println("Encrypted text: " + encrypted);

        String decrypted = decrypt(encrypted, key);
        System.out.println("Decrypted text: " + decrypted);
    }
}
*/
import java.util.HashMap;
import java.util.Map;

public class Lab2_3_VigenereCipher {

    private static final String ALPHABET = "���������������������������������������������������������������� ,.";
    private static final int M = ALPHABET.length();
    private static Map<Character, Integer> charToInt = new HashMap<>();
    private static Map<Integer, Character> intToChar = new HashMap<>();

    static {
        for (int i = 0; i < ALPHABET.length(); i++) {
            charToInt.put(ALPHABET.charAt(i), i);
            intToChar.put(i, ALPHABET.charAt(i));
        }
    }

    public static void main(String[] args) {
        String text = "� ����� ��������, ��� ���� ��������, �� ����� ���� � " +
                      "������������������� �������, ���������� �������������. �������� ���� " +
                      "����������� � ���������. ��������� ������� �������� �������� �� �� ��, " +
                      "��� �� ���� ����� ������ � ������������ ��������. ��� ����� ����� " +
                      "�������� �� ������ �����, �� �, ��������, ������ ��� ���� �����. � " +
                      "������ ������ ������� ������������ ���� ������������ �������. ������ " +
                      "����������� ������ ���������� �� ���������� ����������� ������� " +
                      "��������� �� ���� ������ �����. ����� �������, ��� �������� �������� " +
                      "����������� �����, ������ �������� �����, ���������� ��������� " +
                      "��������������� �������� ���� ������� ��� ������������ ���� �������. " +
                      "� ����� ������������ ������� ���������� ���������� � �� ���������� " +
                      "�������.";
        String keyPhrase = "������������������������������";

        // ����� �: ���������� ������
        String encryptedText = encrypt(text, keyPhrase);
        System.out.println("������������� �����: " + encryptedText);

        // ����� �: ����������� ������
        String decryptedText = decrypt(encryptedText, keyPhrase);
        System.out.println("�������������� �����: " + decryptedText);
    }

    // ������� ����������
    private static String encrypt(String text, String keyPhrase) {
        StringBuilder encrypted = new StringBuilder();
        String extendedKey = extendKey(keyPhrase, text.length());

        for (int i = 0; i < text.length(); i++) {
            char textChar = text.charAt(i);
            char keyChar = extendedKey.charAt(i);

            if (charToInt.containsKey(textChar) && charToInt.containsKey(keyChar)) {
                int encryptedIndex = (charToInt.get(textChar) + charToInt.get(keyChar)) % M;
                encrypted.append(intToChar.get(encryptedIndex));
            } else {
                encrypted.append(textChar);
            }
        }
        return encrypted.toString();
    }

    // ������� �����������
    private static String decrypt(String text, String keyPhrase) {
        StringBuilder decrypted = new StringBuilder();
        String extendedKey = extendKey(keyPhrase, text.length());

        for (int i = 0; i < text.length(); i++) {
            char textChar = text.charAt(i);
            char keyChar = extendedKey.charAt(i);

            if (charToInt.containsKey(textChar) && charToInt.containsKey(keyChar)) {
                int decryptedIndex = (charToInt.get(textChar) - charToInt.get(keyChar) + M) % M;
                decrypted.append(intToChar.get(decryptedIndex));
            } else {
                decrypted.append(textChar);
            }
        }
        return decrypted.toString();
    }

    // ������� ��� ������������ ���������� �������� �����
    private static String extendKey(String key, int length) {
        StringBuilder extendedKey = new StringBuilder();
        int keyLength = key.length();

        for (int i = 0; i < length; i++) {
            extendedKey.append(key.charAt(i % keyLength));
        }
        return extendedKey.toString();
    }
}
