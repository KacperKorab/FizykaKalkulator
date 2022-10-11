package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static java.lang.Math.sqrt;

public class PendulumCalcV2 {
    // Wprowadź dane eksperymentu do poniższych zmiennych:
    // Pomiary czasu trwania okresów wahadła (s).
    private static final List<Double> periodMeasurements = new ArrayList<>(
            List.of(16.08, 16.16, 16.13, 16.09, 16.07, 15.83, 16.08, 16.16, 16.05, 16.31));
    private static final double periodMeasurementError = 0.01;
    private static final double periodAmount = 10;

    // Pomiary długości nitki (cm).
    private static final List<Double> stringMeasurements = new ArrayList<>(
            List.of(63.0, 64.0, 63.7));
    private static final double stringMeasurementError = 0.1;

    // Pomiary średnicy kulki (mm).
    private static final List<Double> ballMeasurements = new ArrayList<>(
            List.of(19.0, 19.0, 19.03));
    private static final double ballMeasurementError = 0.03;

    // Niżej nic nie zmieniać!
    public static void main(String[] args) {
        Acceleration(Period(), Length(String(), Ball()));
    }

    // Czas okresu wahadła.
    private static double[] Period() {
        double periodSum = periodMeasurements.stream().mapToDouble(i -> i).sum();
        double periodArithmeticAverage = periodSum / periodMeasurements.size();
        System.out.printf("\nWprowadzono pomiary czasu trwania 10 okresów wahadła:\n" +
                periodMeasurements +
                "\nŚrednia arytmetyczna pomiarów wynosi:\n" +
                "%.4f s\n", periodArithmeticAverage);

        // Estymator odchylenia standardowego
        List<Double> sumList = new ArrayList<>();
        // dla każdego pomiaru: pomiar minus średnia do kwadratu
        for (double measurement : periodMeasurements) {
            sumList.add((measurement - periodArithmeticAverage) * (measurement - periodArithmeticAverage));
        }
        // pierwiastek sumy elementów wyżej, podzielonej przez ich ilość minus jeden
        double standardDeviation = sqrt(
                sumList.stream().mapToDouble(i -> i).sum() / (sumList.size() - 1));
        System.out.printf("\nEstymator odchylenia standardowego:\n" +
                "%.4f s", standardDeviation);

        // Niepewność A
        double periodUncertaintyA = standardDeviation / sqrt(sumList.size());
        System.out.printf("\nNiepewność pomiaru związana ze statystycznym rozrzutem wyników:\n" +
                "%.4f s", periodUncertaintyA);
        // Niepewność B
        double periodUncertaintyB = (periodMeasurementError / 2) / sqrt(3);
        System.out.printf("\nNiepewność pomiaru związana ze błędem narzędzia pomiaru:\n" +
                "%.4f s", periodUncertaintyB);
        // Całkowita niepewność
        double periodUncertaintyTotal = sqrt(periodUncertaintyA * periodUncertaintyA +
                periodUncertaintyB * periodUncertaintyB);
        System.out.printf("\nCałkowita niepewność pomiaru czasu okresów wahadła:\n" +
                "%.4f s", periodUncertaintyTotal);

        double pendulumPeriod = periodArithmeticAverage / periodAmount;
        double pendulumPeriodUncertainty = periodUncertaintyTotal / periodAmount;
        System.out.printf("\n\nCzas jednego okresu oraz jego niepewność:\n" +
                "%.4f(%.4f) s\n", pendulumPeriod, pendulumPeriodUncertainty);
        System.out.println("\n****************************************************************");

        return new double[]{pendulumPeriod, pendulumPeriodUncertainty};
    }

    // Długość nici.
    private static double[] String() {
        double stringSum = stringMeasurements.stream().mapToDouble(i -> i).sum();
        double stringArithmeticAverage = stringSum / stringMeasurements.size();
        System.out.printf("\nWprowadzono pomiary długości nici wahadła:\n" +
                stringMeasurements +
                "\nŚrednia arytmetyczna pomiarów wynosi:\n" +
                "%.4f cm\n", stringArithmeticAverage);

        double maxH = stringMeasurements.stream().mapToDouble(i -> i).max().orElseThrow(NoSuchElementException::new);
        double minH = stringMeasurements.stream().mapToDouble(i -> i).min().orElseThrow(NoSuchElementException::new);
        double deltaH = maxH - minH;
        System.out.printf("\nRóżnica między największym i najmniejszym pomiarem wynosi:\n" +
                "%.4f cm", deltaH);

        double stringUncertaintyB1 = deltaH / sqrt(3);
        System.out.printf("\nNiepewność standardowa pomiaru długości nici wynosi:\n" +
                "%.4f cm", stringUncertaintyB1);

        double stringUncertaintyB2 = 0.5 * stringMeasurementError / sqrt(3);
        System.out.printf("\nNiepewność związana ze dokładnością użytego przyrządu wynosi:\n" +
                "%.4f cm", stringUncertaintyB2);

        double stringUncertaintyTotal = sqrt(stringUncertaintyB1 * stringUncertaintyB1 +
                stringUncertaintyB2 * stringUncertaintyB2);
        System.out.printf("\nCałkowita niepewność pomiaru długości nici wahadła wynosi:\n" +
                "%.4f cm", stringUncertaintyTotal);
        System.out.printf("\n\nOstateczny wynik pomiaru długości wahadła przyjmuje postać:\n" +
                "%.4f(%.4f) cm\n", stringArithmeticAverage, stringUncertaintyTotal);
        System.out.println("\n****************************************************************");

        return new double[]{stringArithmeticAverage, stringUncertaintyTotal};

    }

    // Średnica kulki
    private static double[] Ball() {
        double ballSum = ballMeasurements.stream().mapToDouble(i -> i).sum();
        double ballArithmeticAverage = ballSum / ballMeasurements.size();
        System.out.printf("\nWprowadzono pomiary średnicy kulki:\n" +
                ballMeasurements +
                "\nŚrednia arytmetyczna pomiarów wynosi:\n" +
                "%.4f mm\n", ballArithmeticAverage);

        double maxD = ballMeasurements.stream().mapToDouble(i -> i).max().orElseThrow(NoSuchElementException::new);
        double minD = ballMeasurements.stream().mapToDouble(i -> i).min().orElseThrow(NoSuchElementException::new);
        double deltaD = maxD - minD;
        System.out.printf("\nRóżnica między największym i najmniejszym pomiarem wynosi:\n" +
                "%.4f mm", deltaD);

        double ballUncertaintyB1 = 10 * deltaD / sqrt(3);
        System.out.printf("\nNiepewność standardowa pomiarów średnicy kulki wynosi:\n" +
                "%.4f mm", ballUncertaintyB1);

        double ballUncertaintyB2 = ballMeasurementError / sqrt(3);
        System.out.printf("\nNiepewność związana ze dokładnością użytego przyrządu wynosi:\n" +
                "%.4f mm", ballUncertaintyB2);

        double ballUncertaintyTotal = sqrt(ballUncertaintyB1 * ballUncertaintyB1 +
                ballUncertaintyB2 * ballUncertaintyB2);
        System.out.printf("\nCałkowita niepewność pomiaru średnicy kulki wynosi:\n" +
                "%.4f mm", ballUncertaintyTotal);
        System.out.printf("\n\nOstateczny wynik pomiaru średnicy kulki przyjmuje postać:\n" +
                "%.4f(%.4f) mm\n", ballArithmeticAverage, ballUncertaintyTotal);
        System.out.println("\n****************************************************************");

        return new double[]{ballArithmeticAverage, ballUncertaintyTotal};
    }

    // Długość całkowita
    private static double[] Length(double[] stringResults, double[] ballResults) {
        double length = stringResults[0] + (0.5 * ballResults[0] / 10);
        System.out.printf("\nDługość całkowita wynosi:\n" +
                "%.4f", length);
        double lengthUncertainty = sqrt((stringResults[1] * stringResults[1]) +
                ((0.05 * ballResults[1]) * (0.05 * ballResults[1])));
        System.out.printf("\nNiepewność długości całkowitej wynosi:\n" +
                "%.4f", lengthUncertainty);
        System.out.printf("\n\nOstatecznie wynik pomiaru długości wahadła przyjmuje postać:\n" +
                "%.4f(%.4f) cm\n", length, lengthUncertainty);
        System.out.println("\n****************************************************************");

        return new double[]{length, lengthUncertainty};
    }

    // Przyspieszenie grawitacyjne
    private static double[] Acceleration(double[] periodResults, double[] lengthResults) {
        double acceleration = 0.01 * 4 * 3.14159265359 * 3.14159265359 * lengthResults[0] /
                (periodResults[0] * periodResults[0]);
        System.out.printf("\nPrzyspieszenie grawitacyjne wynosi:\n" +
                "%.4f m/s^2", acceleration);
        double accelerationUncertainty = acceleration * sqrt(
                ((lengthResults[1] / lengthResults[0]) * (lengthResults[1] / lengthResults[0])) +
                        ((2 * periodResults[1] / periodResults[0]) * (2 * periodResults[1] / periodResults[0])));
        System.out.printf("\nNiepewność przyspieszenia grawitacyjnego wynosi:\n" +
                "%.4f m/s^2", accelerationUncertainty);
        System.out.printf("\n\nOstateczny wynik pomiaru wartości przyśpieszenia ziemskiego przyjmuje postać:\n" +
                "%.4f(%.4f) m/s^2\n", acceleration, accelerationUncertainty);

        return new double[]{acceleration, accelerationUncertainty};
    }
}
// Kacper Korab