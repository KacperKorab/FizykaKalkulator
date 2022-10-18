package org.example;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class YoungCalcB {

    // Ugięcie pręta (mm)
    private static final List<Double> measurementsY = new ArrayList<>(List.of
            (3.0, 6.0, 10.0, 16.0, 22.0, 32.0, 53.0, 63.0, 80.0));
    // Grubość pręta (mm)
    private static final List<Double> measurementsH = new ArrayList<>(List.of
            (2.87, 2.94, 2.95, 2.92, 2.85, 2.97, 2.87, 2.86, 2.93, 2.86));
    // Szerokość pręta (mm)
    private static final List<Double> measurementsD = new ArrayList<>(List.of
            (15.05, 15.16, 15.13, 15.06, 15.13, 15.21, 15.08, 15.09, 15.20, 15.20));
    // Długość pręta (cm)
    private static final List<Double> measurementsL = new ArrayList<>(List.of
            (75.7, 75.5, 75.4));
    // Długość wskazówki (cm)
    private static final List<Double> measurementsS = new ArrayList<>(List.of
            (9.712, 9.623, 9.626));
    // Masa
    private static final List<Double> measurementsM = new ArrayList<>(List.of
            (0.01, 0.02, 0.03, 0.05, 0.07, 0.1, 0.5, 0.2, 0.25));

    public static void main(String[] args) {


        double rodThickness = ArithmeticAverage(measurementsH, "Grubość pręta H [mm]");
        double rodWidth = ArithmeticAverage(measurementsD, "Szerokość pręta D [mm]");
        double rodLength = ArithmeticAverage(measurementsL, "Długość pręta L [cm]");
        double tipLength = ArithmeticAverage(measurementsS, "Długość wskazówki S [cm]");
        double lineCoefficientB = 32.99;

        double youngModel = YoungModelB(rodThickness, rodWidth, rodLength, tipLength, lineCoefficientB);
        double youngModelUncertainty = YoungModelUncertaintyB(measurementsH, measurementsD, measurementsL, lineCoefficientB, 0.47, youngModel);

        System.out.println("Moduł Younga dla eksperymentu B wynosi: " +
                youngModel);
        System.out.println("Niepewność modułu Younga dla eksperymentu B wynosi: " +
                youngModelUncertainty);
    }

    private static double YoungModelB(double rodThickness, double rodWidth, double rodLength, double tipLength, double lineCoefficient) {
        System.out.printf("""
                Wprowadzono dane:
                %.4f - Grubość pręta H [mm]
                %.4f - Szerokość pręta D [mm]
                %.4f - Długość pręta H [cm]
                %.4f - Długość wskazówki S [cm]
                %.4f - Współczynnik regresji linowej a [cm]
                """, rodThickness, rodWidth, rodLength, tipLength, lineCoefficient);
        return (2 * rodLength * rodLength * ((2 * rodLength) + (3 * tipLength)) /
                (lineCoefficient * rodWidth * rodThickness * rodThickness * rodThickness));
    }

    private static double YoungModelUncertaintyB(List<Double> rodHeightMeasurements, List<Double> rodDiameterMeasurements,
                                                 List<Double> rodLengthMeasurements, double coefficient, double coefficientUncertainty, double youngModel) {
        System.out.println("Niepewności pomiaru grubości, szerokości i długości pręta oraz długości jego wskazówki.");
        double D = ArithmeticAverage(rodDiameterMeasurements, "Szerokość pręta D [mm]");
        double L = ArithmeticAverage(rodLengthMeasurements, "Długość pręta L [cm]");
        double H = ArithmeticAverage(rodHeightMeasurements, "Grubość pręta H [mm]");
        double a = coefficient;

        double ucD = UncertaintyCalc(rodDiameterMeasurements, 0.03, "Szerokość pręta D [mm]");
        double ucL = UncertaintyCalc(rodLengthMeasurements, 0.01, "Długość pręta L [cm]");
        double ucH = UncertaintyCalc(rodHeightMeasurements, 0.03, "Grubość pręta H [mm]");
        double ua = coefficientUncertainty;
        return youngModel * sqrt(pow(-1 * ucD / D, 2) + pow(3 * ucL / L, 2) + pow(-3 * ucH / H, 2) + pow(-1 * ua / a, 2));
    }

    private static double ArithmeticAverage(List<Double> measurements, String measurementsName) {
        double arithmeticAverage = measurements.stream().mapToDouble(i -> i).sum() / measurements.size();
        System.out.printf("""
                Wprowadzono dane: %s
                %s
                Średnia arytmetyczna wprowadzonych danych wynosi:
                %.4f
                """,measurementsName, measurements, arithmeticAverage);
        return arithmeticAverage;
    }

    private static double UncertaintyCalc(List<Double> measurements, double measurementError, String measurementsName) {
        System.out.println("\nNiepewność");
        double arithmeticAverage = ArithmeticAverage(measurements, measurementsName);

        // Estymator odchylenia standardowego
        List<Double> sumList = new ArrayList<>();
        // dla każdego pomiaru: pomiar minus średnia do kwadratu
        for (double measurement : measurements) {
            sumList.add((measurement - arithmeticAverage) * (measurement - arithmeticAverage));
        }
        // Niepewność A
        // pierwiastek sumy elementów wyżej, podzielonej przez ich ilość razy ilość minus jeden
        double UncertaintyA = sqrt(
                sumList.stream().mapToDouble(i -> i).sum() / sumList.size() * (sumList.size() - 1));
        System.out.printf("\nNiepewność pomiaru związana ze statystycznym rozrzutem wyników (ua):\n" +
                "%.4f", UncertaintyA);

        // Niepewność B
        double UncertaintyB = (0.5 * measurementError) / sqrt(3);
        System.out.printf("\nNiepewność pomiaru związana ze błędem prostego przyrządu mechanicznego (ub):\n" +
                "%.4f", UncertaintyB);

        double uncertaintyTotal = sqrt((UncertaintyA * UncertaintyA) +
                (UncertaintyB * UncertaintyB));
        System.out.printf("\nCałkowita niepewność pomiaru:\n" +
                "%.4f\n", uncertaintyTotal);
        System.out.println("********************************");
        return uncertaintyTotal;
    }
}
