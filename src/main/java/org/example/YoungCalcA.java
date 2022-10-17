package org.example;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.sqrt;
import static org.example.Formulas.LineCoefficient;

public class YoungCalcA {
    // Wydłużenie drutu (mm)
    private static final List<Double> measurementsDelta_l = new ArrayList<>(List.of
            (0.28, 0.58, 0.88, 1.15, 1.46, 1.79));
    // Średnica drutu (mm)
    private static final List<Double> measurementsD = new ArrayList<>(List.of
            (0.52, 0.51, 0.51, 0.53, 0.51, 0.50, 0.51, 0.51, 0.51, 0.51));
    // Masa obciążenia (kg)
    private static final List<Double> measurementsM = new ArrayList<>(List.of
            (0.5, 1.0, 1.5, 2.0, 2.5, 3.0));
    // Długość drutu
    private static final double length = 2.44;

    public static void main(String[] args) {
        double lineCoefficient = 0.05986; //mm
        double youngModel = YoungModelA(length, lineCoefficient, 0.512);
        double youngModelUncertainty = YoungModelUncertaintyA(length, 0.1,
                measurementsD, 0.03, lineCoefficient, 0.0004, youngModel);
        System.out.println("Moduł Younga dla eksperymentu A wynosi: " +
                youngModel);
        System.out.println("Błąd pomiaru modułu Younga dla eksperymentu A wynosi: " +
                youngModelUncertainty);
    }

    private static double YoungModelA(double length, double lineCoefficient, double wireDiameter) {
        System.out.printf("""
                Wprowadzono dane:
                %.4f - Długość drutu l0 [m]
                %.4f - Średnica drutu D [mm]
                %.4f - Współczynnik regresji linowej a [cm]
                """, length, wireDiameter, lineCoefficient);
        return (4 * length) /
                (lineCoefficient * 3.14159 * wireDiameter * wireDiameter);
    }

    private static double YoungModelUncertaintyA(double length, double lengthMeasurementError,
                                                 List<Double> wireDiameterMeasurements, double diameterMeasurementError,
                                                 double lineCoefficient, double ua_coefficientUncertainty, double youngModel) {
        System.out.println("Niepewności pomiaru długości, średnicy i wydłużenia drutu.");
        double wireDiameter = Formulas.ArithmeticAverage(wireDiameterMeasurements, "Średnica drutu.");
        double ucl0_lengthUncertainty = lengthMeasurementError;
        double ucD_diameterUncertainty = Formulas.UncertaintyCalc(wireDiameterMeasurements, diameterMeasurementError, "Średnica drutu");
        return youngModel * sqrt(
                        Math.pow(ucl0_lengthUncertainty / length, 2) +
                        Math.pow(-2 * ucD_diameterUncertainty / wireDiameter, 2) +
                        Math.pow(-1 * ua_coefficientUncertainty / lineCoefficient, 2));
    }
}
