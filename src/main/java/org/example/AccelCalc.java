package org.example;

import java.util.Scanner;

public class AccelCalc {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Przyspieszenie ziemskie 'g'" +
                "na podstawie wzoru g = (4pi^2*l)/(T^2).");
        System.out.println("Podaj długość 'l' wahadła w cm:");
        double length_l = scanner.nextDouble();
        System.out.println("Podaj okres 'T' wahadła w s:");
        double period_T = scanner.nextDouble();
        double acceleration_g = (4 * 3.14159 * 3.14159 * length_l) / (period_T * period_T);
        System.out.println("Wynik 'g' wynosi: " + String.format("%.4f", acceleration_g) + " m/s^2");

        System.out.println("Podaj niepewność długości: ");
        double lengthUncertainty = scanner.nextDouble();
        System.out.println("Podaj niepewność okresu: ");
        double periodUncertainty = scanner.nextDouble();
        double accelUncertainty = acceleration_g * Math.sqrt(
                (lengthUncertainty / length_l) * (lengthUncertainty / length_l) +
                        (2 * periodUncertainty / period_T) * (2 * periodUncertainty / period_T));
        System.out.println("Niepewność przyspieszenia wynosi: " +
                String.format("%.2f", accelUncertainty) + " m/s^2");
    }
}