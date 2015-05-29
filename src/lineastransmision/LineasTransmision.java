package lineastransmision;

import static java.lang.Math.log;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import java.util.Scanner;

/*
 * dTA = diametro total aproximado -> 1/2 de r ds = Ds dec = distancias entre
 * conductores [0]=1er distancia [1]=2da distancia ...etc d = diametro de las
 * cargas del conductor w = potencia de la linea v = voltaje " " " fP = factore
 * de potencia * lL = logitud de linea frec = frecuencia
 */
public class LineasTransmision {

    static int opc;
    static double dec[] = new double[100];
    static double d, w, v, lL, frec, dTA, ds, fP, r, R;
    static int linea, noCond;
    static double pi = 3.141592;
    static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.println("\t\t______________________ ");
        System.out.println("\t\tLineas de Transmision");
        System.out.println("\t\t---------------------- ");
        System.out.print("\nNumero de conductores en la linea: ");
        noCond = scan.nextInt();
        distanciaConductores(noCond);
        System.out.print("Diametro de las cargas del conductor (m): ");
        d = scan.nextDouble();
        //imprime();
        System.out.println("\nTipo de linea?\n1. Normal\t2. Duplex\t3. Triplex\t4. Cuadruplex");
        System.out.print("-> ");
        linea = scan.nextInt();
        System.out.print("\nTipo de conductor a usar?\n");
        conductores();
        System.out.print("-> ");
        opc = scan.nextInt();
        System.out.print("\nDel conductor seleccionado:\n");
        valoresConductor(opc);
        System.out.print("\nIntroduce la potencia (W): ");
        w = scan.nextDouble();
        System.out.print("\nIntroduce el voltaje (V): ");
        v = scan.nextDouble();
        System.out.print("\nLongitud de la linea (m): ");
        lL = scan.nextDouble();
        System.out.print("\nFactor de potencia: ");
        fP = scan.nextDouble();
        System.out.print("\nFrecuencia (Hz): ");
        frec = scan.nextDouble();
        System.out.println("\nDMG= " + dmgFormula()
                + "\nDsb= " + dsbFormula()
                + "\nL= " + lFormula()
                + "\nCan= " + canFormula());
    }

    static void distanciaConductores(int d) {
        for (int i = 0; i < d; i++) {
            System.out.print("Distancia del conductor:" + (i + 1) + " (m) ");
            dec[i] = scan.nextDouble();
        }
    }

    static double dmgFormula() {
        double dmg = 0, dNumero;
        if (noCond == 2) {
            dNumero = (dec[0]) * (dec[1]);
            dmg = sqrt(dNumero);
        } else if (noCond == 3) {
            dNumero = (dec[0]) * (dec[1]) * (dec[2]);
            dmg = pow(dNumero, 1.0 / 3.0);
        } else if (noCond == 4) {
            dNumero = (dec[0]) * (dec[1]) * (dec[2]) * (dec[3]);
            dmg = pow(dNumero, 1.0 / 4.0);
        } else if (noCond == 5) {
            dNumero = (dec[0]) * (dec[1]) * (dec[2]) * (dec[3]) * (dec[4]);
            dmg = pow(dNumero, 1.0 / 5.0);
        }
        return dmg;
    }

    static double dsbFormula() {
        double dsb, opera = 0.0142 * d; //ds
        dsb = sqrt(opera);
        return dsb;
    }

    static double lFormula() {
        double l = (2.0E-7) * log((dmgFormula() / dsbFormula()));
        return l;
    }

    static double canFormula() {
        double mult, raiz, division, res = 0;
        r = (dTA / 2) / 1000;
        if (linea == 2) {
            res = 0.0242 / log(dmgFormula() / (sqrt(d * r)));
        } else if (linea == 3) {
            res = 0.0242 / log(dmgFormula() / pow(r * (d * d), 1.0 / 3.0));
        } else if (linea == 4) {
            res = 1.09 * (pow(r * (d * d * d), 1.0 / 4.0));
        }
        return res;
    }
    
    static double xlFormula() {
        return 2*Math.PI*frec*lFormula();
    }
    
    static double xcFormula() {
        return 1/Math.PI*frec*canFormula();
    }
    
    static double ycFormula() {
        return Math.PI*frec*canFormula();
    }
    
    // de la tabla R dividir entre 2 es el resultado de R -> rFormula()
    
    static double vrFormula() {
        return v/Math.sqrt(3);
    }
    
    static Imaginario irFormula() {
        double angulo = -Math.acos(fP);
        double ir = w/(Math.sqrt(3)*vrFormula()*fP);
        double real = ir*Math.cos(angulo);
        double imaginaria = ir*Math.sin(angulo);
        Imaginario irComplejo = new Imaginario(real, imaginaria);
        return irComplejo;
    }
    
    static void imprime() {
        for (int i = 0; i < 100; i++) {
            if (dec[i] != 0) {
                System.out.println(dec[i]);
            }
        }
        System.out.println(dmgFormula());
    }

    static void valoresConductor(int c) {
        switch (c) {
            case 1:
                dTA = 0;
                ds = 0.0198;
                R = 0;
                System.out.println("Diametro aproximado = " + dTA + ",\tDs = " + ds + ",\tR = " + R);
                break;
            case 2:
                dTA = 16.31;
                ds = 0.0217;
                R = 0.2569;
                System.out.println("Diametro aproximado = " + dTA + ",\tDs = " + ds + ",\tR = " + R);
                break;
            case 3:
                dTA = 17.27;
                ds = 0.0229;
                R = 0.1247;
                System.out.println("Diametro aproximado = " + dTA + ",\tDs = " + ds + ",\tR = " + R);
                break;
            case 4:
                dTA = 0;
                ds = 0.0222;
                R = 0.2057;
                System.out.println("Diametro aproximado = " + dTA + ",\tDs = " + ds + ",\tR = " + R);
                break;
            case 5:
                dTA = 18.31;
                ds = 0.0243;
                R = 0.2034;
                System.out.println("Diametro aproximado = " + dTA + ",\tDs = " + ds + ",\tR = " + R);
                break;
            case 6:
                dTA = 18.82;
                ds = 0.0255;
                R = 0.2021;
                System.out.println("Diametro aproximado = " + dTA + ",\tDs = " + ds + ",\tR = " + R);
                break;
            case 7:
                dTA = 0;
                ds = 0.0241;
                R = 0.1742;
                System.out.println("Diametro aproximado = " + dTA + ",\tDs = " + ds + ",\tR = " + R);
                break;
            case 8:
                dTA = 19.89;
                ds = 0.0264;
                R = 0.1726;
                System.out.println("Diametro aproximado = " + dTA + ",\tDs = " + ds + ",\tR = " + R);
                break;
            case 9:
                dTA = 0;
                ds = 0.0264;
                R = 0.1453;
                System.out.println("Diametro aproximado = " + dTA + ",\tDs = " + ds + ",\tR = " + R);
                break;
            case 10:
                dTA = 0;
                ds = 0.0284;
                R = 0.1444;
                System.out.println("Diametro aproximado = " + dTA + ",\tDs = " + ds + ",\tR = " + R);
                break;
            case 11:
                dTA = 21.79;
                ds = 0.0289;
                R = 0.1437;
                System.out.println("Diametro aproximado = " + dTA + ",\tDs = " + ds + ",\tR = " + R);
                break;
            case 12:
                dTA = 22.43;
                ds = 0.0304;
                R = 0.1427;
                System.out.println("Diametro aproximado = " + dTA + ",\tDs = " + ds + ",\tR = " + R);
                break;
            case 13:
                dTA = 0;
                ds = 0.0284;
                R = 0.1247;
                System.out.println("Diametro aproximado = " + dTA + ",\tDs = " + ds + ",\tR = " + R);
                break;
            case 14:
                dTA = 0;
                ds = 0.0306;
                R = 0.124;
                System.out.println("Diametro aproximado = " + dTA + ",\tDs = " + ds + ",\tR = " + R);
                break;
            case 15:
                dTA = 23.54;
                ds = 0.0314;
                R = 0.1237;
                System.out.println("Diametro aproximado = " + dTA + ",\tDs = " + ds + ",\tR = " + R);
                break;
            case 16:
                dTA = 0;
                ds = 0.0327;
                R = 0.1083;
                System.out.println("Diametro aproximado = " + dTA + ",\tDs = " + ds + ",\tR = " + R);
                break;
            case 17:
                dTA = 25.14;
                ds = 0.0335;
                R = 0.1079;
                System.out.println("Diametro aproximado = " + dTA + ",\tDs = " + ds + ",\tR = " + R);
                break;
            case 18:
                dTA = 28.14;
                ds = 0.0373;
                R = 0.0869;
                System.out.println("Diametro aproximado = " + dTA + ",\tDs = " + ds + ",\tR = " + R);
                break;
            case 19:
                dTA = 27;
                ds = 0.0352;
                R = 0.0879;
                System.out.println("Diametro aproximado = " + dTA + ",\tDs = " + ds + ",\tR = " + R);
                break;
            case 20:
                dTA = 29.59;
                ds = 0.0386;
                R = 0.0738;
                System.out.println("Diametro aproximado = " + dTA + ",\tDs = " + ds + ",\tR = " + R);
                break;
            case 21:
                dTA = 30.37;
                ds = 0.0402;
                R = 0.0732;
                System.out.println("Diametro aproximado = " + dTA + ",\tDs = " + ds + ",\tR = " + R);
                break;
            case 22:
                dTA = 30.81;
                ds = 0.0402;
                R = 0.0682;
                System.out.println("Diametro aproximado = " + dTA + ",\tDs = " + ds + ",\tR = " + R);
                break;
            case 23:
                dTA = 31.98;
                ds = 0.0415;
                R = 0.0636;
                System.out.println("Diametro aproximado = " + dTA + ",\tDs = " + ds + ",\tR = " + R);
                break;
            case 24:
                dTA = 32.84;
                ds = 0.0436;
                R = 0.0633;
                System.out.println("Diametro aproximado = " + dTA + ",\tDs = " + ds + ",\tR = " + R);
                break;
            case 25:
                dTA = 34.16;
                ds = 0.0444;
                R = 0.0561;
                System.out.println("Diametro aproximado = " + dTA + ",\tDs = " + ds + ",\tR = " + R);
                break;
            case 26:
                dTA = 35.1;
                ds = 0.0466;
                R = 0.0554;
                System.out.println("Diametro aproximado = " + dTA + ",\tDs = " + ds + ",\tR = " + R);
                break;
            case 27:
                dTA = 36.24;
                ds = 0.0470;
                R = 0.0502;
                System.out.println("Diametro aproximado = " + dTA + ",\tDs = " + ds + ",\tR = " + R);
                break;
            case 28:
                dTA = 37.21;
                ds = 0.0494;
                R = 0.0495;
                System.out.println("Diametro aproximado = " + dTA + ",\tDs = " + ds + ",\tR = " + R);
                break;
            case 29:
                dTA = 38.15;
                ds = 0.0498;
                R = 0.0456;
                System.out.println("Diametro aproximado = " + dTA + ",\tDs = " + ds + ",\tR = " + R);
                break;
            case 30:
                dTA = 39.24;
                ds = 0.0523;
                R = 0.0449;
                System.out.println("Diametro aproximado = " + dTA + ",\tDs = " + ds + ",\tR = " + R);
                break;
            case 31:
                dTA = 0;
                ds = 0.0586;
                R = 0.0344;
                System.out.println("Diametro aproximado = " + dTA + ",\tDs = " + ds + ",\tR = " + R);
                break;
            default:
                System.out.println("\n\tNo existe esa opcion!");
                break;
        }
    }

    static void conductores() {
        System.out.println("1. Waxwing\t\t11. Hawk\t\t21. Cardinal\t\t31. Bluebird");
        System.out.println("2. Partridge\t\t12. Hen\t\t\t22. Ortolan");
        System.out.println("3. Ostrich\t\t13. Osprey\t\t23. Bluejay");
        System.out.println("4. Merlin\t\t14. Parakeet\t\t24. Finch");
        System.out.println("5. Linnet\t\t15. Dove\t\t25. Bittern");
        System.out.println("6. Oriole\t\t16. Rook\t\t26. Pheasant");
        System.out.println("7. Chickadee\t\t17. Grosbeak\t\t27. Bobolink");
        System.out.println("8. Ibis\t\t\t18. Drake\t\t28. Plover");
        System.out.println("9. Pelican\t\t19. Tern\t\t29. Lapwing");
        System.out.println("10. Flicker\t\t20. Rail\t\t30. Falcon");
    }
    
    public static class Imaginario {
        
        private double i;
        private double r;
        
        public Imaginario(double r, double i) {
            this.r = r;
            this.i = i;
        }

        public double getI() {
            return i;
        }

        public void setI(double i) {
            this.i = i;
        }

        public double getR() {
            return r;
        }

        public void setR(double r) {
            this.r = r;
        }
    }
    
}
