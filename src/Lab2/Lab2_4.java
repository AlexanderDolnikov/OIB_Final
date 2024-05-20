package Lab2;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Lab2_4 {

    // Пример списка простых чисел
    static final int[] primeNumbers = {
            3001, 3011, 3019, 3023, 3037, 3041, 3049, 3061, 3067, 3079,
            3083, 3089, 3109, 3119, 3121, 3137, 3163, 3167, 3169, 3181,
            3187, 3191, 3203, 3209, 3217, 3221, 3229, 3251, 3253, 3257
    };

    // Функция для проверки, является ли число простым
    public static boolean isPrime(int num) {
        if (num <= 1) return false;
        for (int i = 2; i <= Math.sqrt(num); i++) {
            if (num % i == 0) return false;
        }
        return true;
    }

    // Функция для нахождения ближайшего простого числа
    public static int findNextPrime(int num) {
        while (!isPrime(num)) {
            num++;
        }
        return num;
    }

    // Функция для вычисления НОД
    public static int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    // Функция для вычисления обратного по модулю
    public static BigInteger modInverse(BigInteger a, BigInteger m) {
        return a.modInverse(m);
    }

    // Функция для вычисления секретного ключа
    public static BigInteger calculateD(BigInteger e, BigInteger phi) {
        return modInverse(e, phi);
    }

    public static void main(String[] args) {
        int n = 21;

        // Задание 1: Выбор простых чисел
        int index1 = 20; // 21-е число
        int index2 = (40 % 30) + 1 - 1; // (40 % 30) + 1 - 1 = 10
        int p = primeNumbers[index1];
        int q = primeNumbers[index2];

        System.out.println("p: " + p);
        System.out.println("q: " + q);

        // Задание 2: Вычисление N и ключей
        BigInteger P = BigInteger.valueOf(p);
        BigInteger Q = BigInteger.valueOf(q);
        BigInteger N = P.multiply(Q);
        BigInteger phi = (P.subtract(BigInteger.ONE)).multiply(Q.subtract(BigInteger.ONE));

        // Выбор открытого ключа K
        BigInteger K = BigInteger.valueOf(65537); // 2^16 + 1

        // Проверка, что НОД(φ(N), K) = 1
        if (gcd(K.intValue(), phi.intValue()) != 1) {
            throw new RuntimeException("Invalid public key. Choose a different one.");
        }

        // Вычисление секретного ключа D
        BigInteger D = calculateD(K, phi);

        System.out.println("N: " + N);
        System.out.println("φ(N): " + phi);
        System.out.println();
        System.out.println("Public Key (K): " + K);
        System.out.println("Private Key (D): " + D);

        // Задание 3: Преобразование ФИО в последовательность цифр и зашифрование первых 5 блоков
        String fullName = "лашкиндольниковматвейалександр";
        List<BigInteger> encryptedBlocks = new ArrayList<>();

        // Преобразование ФИО в числа
        List<String> blocks = new ArrayList<>();
        StringBuilder currentBlock = new StringBuilder();
        for (char c : fullName.toCharArray()) {
            currentBlock.append((int) c);
            if (currentBlock.length() >= 7) {
                blocks.add(currentBlock.toString());
                currentBlock.setLength(0);
            }
        }
        if (currentBlock.length() > 0) {
            blocks.add(currentBlock.toString());
        }

        System.out.println();

        // Зашифрование первых 5 блоков
        for (int i = 0; i < Math.min(5, blocks.size()); i++) {
            BigInteger message = new BigInteger(blocks.get(i));
            BigInteger encryptedMessage = message.modPow(K, N);
            encryptedBlocks.add(encryptedMessage);
            System.out.println(i + ":");
            System.out.println("Original Block: " + message);
            System.out.println("Encrypted Block: " + encryptedMessage);
        }

        System.out.println();

        // Задание 4: Расшифрование блоков
        int i = 0;
        for (BigInteger encryptedBlock : encryptedBlocks) {
            BigInteger decryptedMessage = encryptedBlock.modPow(D, N);
            System.out.println(i++ + ":");
            System.out.println("Decrypted Block: " + decryptedMessage);
        }

        System.out.println();

        // Задание 5: Вычисление хэш-функции
        BigInteger h = BigInteger.TEN; // h0 = 10
        for (BigInteger mi : encryptedBlocks) {
            h = h.add(mi).pow(3).mod(N);
        }
        System.out.println("Hash Value: " + h);

        // Задание 6: Цифровая подпись
        BigInteger signature = h.modPow(D, N);
        System.out.println("Signature: " + signature);

        // Проверка цифровой подписи
        BigInteger verifiedHash = signature.modPow(K, N);
        System.out.println("Verified Hash: " + verifiedHash);

        if (verifiedHash.equals(h)) {
            System.out.println("Signature is valid.");
        } else {
            System.out.println("Signature is invalid.");
        }
    }
}
