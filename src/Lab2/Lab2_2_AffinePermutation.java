package Lab2;

import java.util.Arrays;

public class Lab2_2_AffinePermutation {

    private static final int M = 61;
    private static final int N = 21;//variant
    private static final int A = (N + 2);
    private static final int B = (N - 25 + M) % M;
    private static final int A_INV;

    static {
        A_INV = findModInverse(A, M); // ��������� �������� ������� ��� A �� ������ M
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

        // ����� �: ���������� ������
        String encryptedText = encrypt(text);
        System.out.println("������������� �����: " + encryptedText);

        // ����� �: ����������� ������
        String decryptedText = decrypt(encryptedText);
        System.out.println("�������������� �����: " + decryptedText);
    }

    // ������� ����������
    private static String encrypt(String text) {
        StringBuilder encrypted = new StringBuilder();
        int len = text.length();
        for (int i = 0; i < len; i += M) {
            String block = text.substring(i, Math.min(len, i + M));
            encrypted.append(encryptBlock(block));
        }
        return encrypted.toString();
    }

    // ������� ���������� �����
    private static String encryptBlock(String block) {
        char[] encryptedBlock = new char[M];
        Arrays.fill(encryptedBlock, ' '); // ��������� ������ ��������� ��� ��������� ������

        for (int x = 0; x < block.length(); x++) {
            int newIndex = (A * x + B) % M;
            encryptedBlock[newIndex] = block.charAt(x);
        }
        return new String(encryptedBlock);
    }

    // ������� �����������
    private static String decrypt(String text) {
        StringBuilder decrypted = new StringBuilder();
        int len = text.length();
        for (int i = 0; i < len; i += M) {
            String block = text.substring(i, Math.min(len, i + M));
            decrypted.append(decryptBlock(block));
        }
        return decrypted.toString();
    }

    // ������� ����������� �����
    private static String decryptBlock(String block) {
        char[] decryptedBlock = new char[M];
        Arrays.fill(decryptedBlock, ' '); // ��������� ������ ��������� ��� ��������� ������

        for (int y = 0; y < block.length(); y++) {
            int originalIndex = (A_INV * (y - B + M)) % M;
            if (originalIndex < block.length()) {
                decryptedBlock[originalIndex] = block.charAt(y);
            }
        }
        return new String(decryptedBlock).trim(); // ������� ������ ������� � �����
    }

    // ������� ��� ���������� ��������� �������� � �������������� ������������ ��������� ������� (����� � � �)
    public static int findModInverse(int a, int m) {
        int[] result = extendedGCD(a, m);
        if (result[2] != 1) {
            throw new IllegalArgumentException("�������� ������� �� ����������");
        } else {
            return (result[0] % m + m) % m;
        }
    }

    // ����������� �������� ������� (����� �)
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
