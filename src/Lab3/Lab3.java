package Lab3;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Lab3 {
    public static void main(String[] args) {
        int n = 21;
        BigInteger m = BigInteger.valueOf(17);

        // Задание 1: Альфа
        BigInteger alpha = BigInteger.valueOf(3).modPow(BigInteger.valueOf(2 * n + 1), m);
        System.out.println("1. Альфа\nAlpha: " + alpha);

        // Задание 2: Коэффициенты многочлена в поле GF(17)
        BigInteger[] alphaPowers = new BigInteger[5];
        for (int i = 1; i <= 4; i++) {
            alphaPowers[i] = alpha.modPow(BigInteger.valueOf(i), m);
        }
        // Correct initialization of g(x)
        BigInteger g = BigInteger.ONE;
        for (int i = 1; i <= 4; i++) {
            g = g.multiply(BigInteger.valueOf(-1)).multiply(alphaPowers[i]).mod(m);
        }
        System.out.println("\n2. Коэффициенты многочлена в поле GF(17)\ng(x) coefficients: " + g);

        // Задание 3: Многочлены i_0(x), i_1(x), i_2(x)
        String fullName = "лашкиндольниковматвейалександр";
        List<Integer> sequence = new ArrayList<>();
        for (char c : fullName.toCharArray()) {
            sequence.add((int) c);
        }
        List<Integer> i0 = sequence.subList(0, Math.min(12, sequence.size()));
        List<Integer> i1 = sequence.subList(Math.min(12, sequence.size()), Math.min(24, sequence.size()));
        List<Integer> i2 = sequence.subList(Math.min(24, sequence.size()), Math.min(36, sequence.size()));

        System.out.println("\n3. Многочлены i_0(x), i_1(x), i_2(x)\ni0(x): " + i0);
        System.out.println("i1(x): " + i1);
        System.out.println("i2(x): " + i2);

        // Задание 4: Многочлены c_0(x), c_1(x), c_2(x)
        List<Integer> c0 = modPoly(i0, g, m);
        List<Integer> c1 = modPoly(i1, g, m);
        List<Integer> c2 = modPoly(i2, g, m);

        System.out.println("\n4. Многочлены c_0(x), c_1(x), c_2(x)\nc0(x): " + c0);
        System.out.println("c1(x): " + c1);
        System.out.println("c2(x): " + c2);

        // Задание 5: Построение слова v(x)
        List<Integer> v = new ArrayList<>(i0);
        v.addAll(i1);
        v.addAll(i2);

        System.out.println("\n5. Построение слова v(x)\nv(x): " + v);

        // Задание 6: Нахождение t_j
        int tj = findTj(v, alpha, m);
        System.out.println("\n6. Нахождение t_j\ntj: " + tj);

        // Задание 7: Решение системы Ax = b
        BigInteger[][] A = buildMatrix(v, tj, alpha, m);
        BigInteger[] b = buildVector(v, tj, alpha, m);
        BigInteger[] x = solveSystem(A, b, m);
        System.out.println("\n7. Решение системы Ax = b\nSolution x: ");
        for (BigInteger xi : x) {
            System.out.println(xi);
        }

        // Задание 8: Нахождение корней многочлена
        BigInteger[] roots = findRoots(x, m);
        System.out.println("\n8. Нахождение корней многочлена\nRoots: ");
        for (BigInteger root : roots) {
            System.out.println(root);
        }

        // Задание 9: Сравнение v_j(x)/g(x) с c_j(x)
        List<Integer> vjOverG = modPoly(v, g, m);
        System.out.println("\n9. Сравнение v_j(x)/g(x) с c_j(x)\nv_j(x): " + vjOverG);
        System.out.println("c_j(x): " + c0 + ", " + c1 + ", " + c2);
    }

    public static List<Integer> modPoly(List<Integer> poly, BigInteger g, BigInteger m) {
        List<Integer> result = new ArrayList<>();
        BigInteger gMod = g.mod(m);
        for (int coef : poly) {
            BigInteger coefMod = BigInteger.valueOf(coef).mod(gMod);
            result.add(coefMod.intValue());
        }
        return result;
    }

    public static int findTj(List<Integer> poly, BigInteger alpha, BigInteger m) {
        int maxTj = 2; // Поскольку t_j ≤ 2 по условию
        for (int t = maxTj; t >= 0; t--) {
            boolean hasNonZeroDet = hasNonZeroDeterminant(poly, alpha, m, t);
            if (hasNonZeroDet) {
                return t;
            }
        }
        return 0; // Если ни один t_j не подошел, возвращаем 0
    }

    public static boolean hasNonZeroDeterminant(List<Integer> poly, BigInteger alpha, BigInteger m, int t) {
        // Построение матрицы A_tj и проверка ее детерминанта
        BigInteger[][] A = new BigInteger[t][t];
        for (int i = 0; i < t; i++) {
            for (int j = 0; j < t; j++) {
                A[i][j] = alpha.modPow(BigInteger.valueOf((i + 1) * (j + 1)), m);
            }
        }
        BigInteger det = calculateDeterminant(A, m);
        return !det.equals(BigInteger.ZERO);
    }

    public static BigInteger calculateDeterminant(BigInteger[][] matrix, BigInteger mod) {
        int n = matrix.length;
        BigInteger det = BigInteger.ZERO;
        if (n == 1) {
            return matrix[0][0].mod(mod);
        }
        for (int i = 0; i < n; i++) {
            BigInteger[][] subMatrix = new BigInteger[n - 1][n - 1];
            for (int j = 1; j < n; j++) {
                int columnIndex = 0;
                for (int k = 0; k < n; k++) {
                    if (k == i) continue;
                    subMatrix[j - 1][columnIndex++] = matrix[j][k];
                }
            }
            BigInteger term = matrix[0][i].multiply(calculateDeterminant(subMatrix, mod));
            if (i % 2 == 0) {
                det = det.add(term);
            } else {
                det = det.subtract(term);
            }
        }
        return det.mod(mod);
    }

    public static BigInteger[][] buildMatrix(List<Integer> v, int tj, BigInteger alpha, BigInteger m) {
        BigInteger[][] A = new BigInteger[tj][tj];
        for (int i = 0; i < tj; i++) {
            for (int j = 0; j < tj; j++) {
                A[i][j] = alpha.modPow(BigInteger.valueOf((i + 1) * (j + 1)), m);
            }
        }
        return A;
    }

    public static BigInteger[] buildVector(List<Integer> v, int tj, BigInteger alpha, BigInteger m) {
        BigInteger[] b = new BigInteger[tj];
        for (int i = 0; i < tj; i++) {
            b[i] = alpha.modPow(BigInteger.valueOf(2 * (i + 1)), m);
        }
        return b;
    }

    public static BigInteger[] solveSystem(BigInteger[][] A, BigInteger[] b, BigInteger m) {
        int n = A.length;
        BigInteger[] x = new BigInteger[n];

        // Преобразуем матрицу A и вектор b к расширенной форме
        BigInteger[][] augmentedMatrix = new BigInteger[n][n + 1];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                augmentedMatrix[i][j] = A[i][j];
            }
            augmentedMatrix[i][n] = b[i];
        }

        // Применяем метод Гаусса для решения системы уравнений
        for (int i = 0; i < n; i++) {
            // Находим максимальный элемент в столбце
            int maxRow = i;
            for (int k = i + 1; k < n; k++) {
                if (augmentedMatrix[k][i].abs().compareTo(augmentedMatrix[maxRow][i].abs()) > 0) {
                    maxRow = k;
                }
            }

            // Меняем строки местами
            BigInteger[] temp = augmentedMatrix[i];
            augmentedMatrix[i] = augmentedMatrix[maxRow];
            augmentedMatrix[maxRow] = temp;

            // Нормализуем текущую строку
            for (int k = i + 1; k < n; k++) {
                BigInteger factor = augmentedMatrix[k][i].divide(augmentedMatrix[i][i]).mod(m);
                for (int j = i; j <= n; j++) {
                    augmentedMatrix[k][j] = augmentedMatrix[k][j].subtract(factor.multiply(augmentedMatrix[i][j])).mod(m);
                }
            }
        }

        // Обратный ход метода Гаусса
        for (int i = n - 1; i >= 0; i--) {
            BigInteger sum = BigInteger.ZERO;
            for (int j = i + 1; j < n; j++) {
                sum = sum.add(augmentedMatrix[i][j].multiply(x[j]));
            }
            x[i] = augmentedMatrix[i][n].subtract(sum).divide(augmentedMatrix[i][i]).mod(m);
        }

        return x;
    }

    public static BigInteger[] findRoots(BigInteger[] x, BigInteger m) {
        List<BigInteger> roots = new ArrayList<>();
        for (BigInteger i = BigInteger.ZERO; i.compareTo(m) < 0; i = i.add(BigInteger.ONE)) {
            BigInteger sum = BigInteger.ZERO;
            for (int j = 0; j < x.length; j++) {
                sum = sum.add(x[j].multiply(i.pow(j))).mod(m);
            }
            if (sum.equals(BigInteger.ZERO)) {
                roots.add(i);
            }
        }
        return roots.toArray(new BigInteger[0]);
    }
}
