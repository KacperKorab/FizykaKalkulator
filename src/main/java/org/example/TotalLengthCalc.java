package org.example;

import java.util.Scanner;

import static java.lang.Math.sqrt;

public class TotalLengthCalc {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj długość nici w cm:");
        double stringLength = scanner.nextDouble();
        System.out.println("Podaj średnicę kulki w cm:");
        double sphereLength = scanner.nextDouble();
        double totalLength = stringLength + 0.5 * sphereLength;
        System.out.println("Długość całkowita to: " + String.format("%.3f", totalLength));

        System.out.println("Podaj niepewność długości nici w cm:");
        double stringUncertainty = scanner.nextDouble();
        System.out.println("Podaj niepewność średnicy kulki w cm:");
        double sphereUncertainty = scanner.nextDouble();
        double totalLengthUncertainty = sqrt(stringUncertainty * stringUncertainty
                + sphereUncertainty * sphereUncertainty);
        System.out.println("Niepewność całkowita pomiaru to: " +
                String.format("%.4f", totalLengthUncertainty));
    }
}
