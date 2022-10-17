package org.example;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.sqrt;

public class Formulas {

    public static void main(String[] args) {
        // Wartości podawać w jednej jednostce

        // Wydłużenie drutu (mm)
        List<Double> measurementsW1 = new ArrayList<>(List.of
                (0.28, 0.58, 0.88, 1.15, 1.46, 1.79));
        // Średnica drutu (mm)
        List<Double> measurementsW2 = new ArrayList<>(List.of
                (0.52, 0.51, 0.51, 0.53, 0.51, 0.50, 0.51, 0.51, 0.51, 0.51));
        // Masa obciążenia (kg)
        List<Double> measurementsW3 = new ArrayList<>(List.of
                (0.5, 1.0, 1.5, 2.0, 2.5, 3.0));

        // Ugięcie pręta (mm)
        List<Double> measurementsY = new ArrayList<>(List.of
                (3.0, 6.0, 10.0, 16.0, 22.0, 32.0, 53.0, 63.0, 80.0));
        // Grubość pręta (mm)
        List<Double> measurementsH = new ArrayList<>(List.of
                (2.87, 2.94, 2.95, 2.92, 2.85, 2.97, 2.87, 2.86, 2.93, 2.86));
        // Szerokość pręta (mm)
        List<Double> measurementsD = new ArrayList<>(List.of
                (15.05, 15.16, 15.13, 15.06, 15.13, 15.21, 15.08, 15.09, 15.20, 15.20));
        // Długość pręta (cm)
        List<Double> measurementsL = new ArrayList<>(List.of
                (75.7, 75.5, 75.4));
        // Długość wskazówki (cm)
        List<Double> measurementsS = new ArrayList<>(List.of
                (9.712, 9.623, 9.626));

        System.out.println("Grubość pręta H [mm]:");
        UncertaintyCalc(measurementsH, 0.03, "Grubość pręta H [mm]");
        System.out.println("Szerokość pręta D [mm]:");
        UncertaintyCalc(measurementsD, 0.03, "Szerokość pręta D [mm]");
        System.out.println("Długość pręta L [cm]:");
        UncertaintyCalc(measurementsL, 0.1, "Długość pręta L [cm]");
        System.out.println("Długość wskazówki S [cm]:");
        UncertaintyCalc(measurementsS, 0.1, "Długość wskazówki S [cm]");

    }

    public static double ArithmeticAverage(List<Double> measurements, String measurementsName) {
        double arithmeticAverage = measurements.stream().mapToDouble(i -> i).sum() / measurements.size();
        System.out.printf("""
                Wprowadzono dane: %s
                %s
                Średnia arytmetyczna wprowadzonych danych wynosi:
                %.4f
                """,measurementsName, measurements, arithmeticAverage);
        return arithmeticAverage;
    }

    public static double UncertaintyCalc(List<Double> measurements, double measurementError, String measurementsName) {
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
    // Współczynnik kierunkowy prostej
    public static double LineCoefficient(List<Double> deflectionMeasurements, List<Double> weightsUsed, String measurementsName) {
        System.out.println("\nWspółczynnik kierunkowy prostej");
        double avgDeflection = Formulas.ArithmeticAverage(deflectionMeasurements, measurementsName);
        double avgForce = Formulas.ArithmeticAverage(weightsUsed, "Masa") * 9.81105;
        double lineCoefficient = avgDeflection / avgForce;
        System.out.println("Współczynnik kierunkowy prostej a: " + lineCoefficient);
        System.out.println("********************************");
        return lineCoefficient;
    }
}
