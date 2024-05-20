package Lab2;

import java.util.HashMap;
import java.util.Map;

public class Lab2_1_AffineCipher {

    private static final int M = 68; // ������ �������� ��� ������ ����� (����������� �������)
    private static final int N = 21; // ����� ��������
    private static final int A = N + 2; // �������� A ��� ��������� �����
    private static final int B = (N - 25 + M) % M; // �������� B ��� ��������� �����, ����� ������ ��� �������������
    private static Map<Character, Integer> charToInt = new HashMap<>(); // ������� ��� �������������� �������� � �����
    private static Map<Integer, Character> intToChar = new HashMap<>(); // ������� ��� �������������� ����� � �������
    private static final int A_INV; // �������� ������� ��� A �� ������ M

    static {
        // ������������� �������� �������� (����� �)
        String chars = "���������������������������������������������������������������� ,.";
        for (int i = 0; i < chars.length(); i++) {
            charToInt.put(chars.charAt(i), i);
            intToChar.put(i, chars.charAt(i));
        }
        // ���������� ��������� �������� ��� A �� ������ M
        A_INV = findModInverse(A, M);
    }

    public static void main(String[] args) {
        // �������� �����
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

        // ���������� ������
        String encryptedText = encrypt(text);
        System.out.println("������������� �����: " + encryptedText);

        // ����������� ������
        String decryptedText = decrypt(encryptedText);
        System.out.println("�������������� �����: " + decryptedText);

        // ������������ ������������� ������� extendedGCD � findModInverse
        int[] gcdResult = extendedGCD(A, M);
        System.out.println("��� " + A + " � " + M + ": " + gcdResult[2]);
        System.out.println("������������: x = " + gcdResult[0] + ", y = " + gcdResult[1]);
        System.out.println("�������� ������� " + A + " �� ������ " + M + ": " + A_INV);
    }

    // ������� ����������
    private static String encrypt(String text) {
        StringBuilder encrypted = new StringBuilder();
        for (char ch : text.toCharArray()) {
            if (charToInt.containsKey(ch)) {
                int x = charToInt.get(ch); // �������������� ������� � �����
                int y = (A * x + B) % M; // ���������� ��������� ��������������
                encrypted.append(intToChar.get(y)); // �������������� ����� ������� � ������
            } else {
                encrypted.append(ch); // ��������� ������� ��� ���������, ���� ��� �� � �������
            }
        }
        return encrypted.toString(); // ���������� ������������� �����
    }

    // ������� �����������
    private static String decrypt(String text) {
        StringBuilder decrypted = new StringBuilder();
        for (char ch : text.toCharArray()) {
            if (charToInt.containsKey(ch)) {
                int y = charToInt.get(ch); // �������������� ������� � �����
                int x = (A_INV * (y - B + M)) % M; // �������� �������� ��������������
                decrypted.append(intToChar.get(x)); // �������������� ����� ������� � ������
            } else {
                decrypted.append(ch); // ��������� ������� ��� ���������, ���� ��� �� � �������
            }
        }
        return decrypted.toString(); // ���������� �������������� �����
    }

    // ������� ��� ���������� ��������� �������� � �������������� ������������ ��������� ������� (����� � � �)
    public static int findModInverse(int a, int m) {
        int[] result = extendedGCD(a, m);
        if (result[2] != 1) { // ���������, ��� ��� ����� 1
            throw new IllegalArgumentException("�������� ������� �� ����������");
        } else {
            return (result[0] % m + m) % m; // ���������� �������� �������
        }
    }

    // ����������� �������� ������� (����� �)
    public static int[] extendedGCD(int a, int b) {
        if (b == 0) {
            return new int[]{1, 0, a}; // ���������� ��������� ������������ � ���
        } else {
            int[] result = extendedGCD(b, a % b); // ����������� �����
            int x = result[0];
            int y = result[1];
            int gcd = result[2];
            return new int[]{y, x - (a / b) * y, gcd}; // ��������� ������������
        }
    }
}
