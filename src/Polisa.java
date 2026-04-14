import java.util.Objects;

public class Polisa {
    private String numerPolisy;
    private String klient;
    private double skladkaBazowa;
    private int poziomRyzyka;
    private double wartoscPojazdu;
    private boolean czyMaAlarm;
    private boolean czyBezszkodowyKlient;

    private static int liczbaUtworzonychPolis = 0;
    private static final double OPLATA_ADMINISTRACYJNA = 100.0;

    public Polisa(String numerPolisy, String klient, double skladkaBazowa,
                  int poziomRyzyka, double wartoscPojazdu,
                  boolean czyMaAlarm, boolean czyBezszkodowyKlient) {
        this.numerPolisy = numerPolisy;
        this.klient = klient;
        this.skladkaBazowa = skladkaBazowa;
        this.poziomRyzyka = poziomRyzyka;
        this.wartoscPojazdu = wartoscPojazdu;
        this.czyMaAlarm = czyMaAlarm;
        this.czyBezszkodowyKlient = czyBezszkodowyKlient;
        liczbaUtworzonychPolis++;
    }

    public String getNumerPolisy() {
        return numerPolisy;
    }

    public double obliczSkladkeKoncowa() {
        double wynik = skladkaBazowa + OPLATA_ADMINISTRACYJNA;

        wynik += poziomRyzyka * 120;

        if (wartoscPojazdu > 60000) {
            wynik += 200;
        }

        if (czyMaAlarm) {
            wynik -= 100;
        }

        if (czyBezszkodowyKlient) {
            wynik *= 0.9;
        }

        if (wynik < skladkaBazowa) {
            wynik = skladkaBazowa;
        }

        return Math.round(wynik * 100.0) / 100.0;
    }

    public double obliczSkladkeOdnowieniowa() {
        double obecna = obliczSkladkeKoncowa();
        double nowa = obecna;

        if (poziomRyzyka == 4) {
            nowa *= 1.10;
        } else if (poziomRyzyka >= 5) {
            nowa *= 1.20;
        }

        if (wartoscPojazdu > 60000) {
            nowa += 150;
        }

        if (czyBezszkodowyKlient) {
            nowa *= 0.92;
        }

        if (czyMaAlarm) {
            nowa *= 0.95;
        }

        double min = obecna * 0.9;
        double max = obecna * 1.25;

        if (nowa < min) nowa = min;
        if (nowa > max) nowa = max;

        return Math.round(nowa * 100.0) / 100.0;
    }

    public String pobierzPodsumowanieRyzyka() {
        if (poziomRyzyka <= 2) return "Niskie ryzyko";
        if (poziomRyzyka <= 4) return "Srednie ryzyko";
        return "Wysokie ryzyko";
    }

    public static int pobierzLiczbeUtworzonychPolis() {
        return liczbaUtworzonychPolis;
    }

    @Override
    public String toString() {
        return "Polisa{" +
                "numer='" + numerPolisy + '\'' +
                ", klient='" + klient + '\'' +
                ", skladkaKoncowa=" + obliczSkladkeKoncowa() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Polisa polisa = (Polisa) o;
        return Objects.equals(numerPolisy, polisa.numerPolisy);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(numerPolisy);
    }
}