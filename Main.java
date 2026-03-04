import java.io.File;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

asd
public class Main {
    public static int szoSzam(String nev) {
        return nev.trim().split("\\s+").length;
    }

    public static void main(String[] args) {
        ArrayList<Asset> assetLista = new ArrayList<>();

        try (Scanner sc = new Scanner(new File("files/stolen.csv"))) {
            sc.nextLine();

            while (sc.hasNextLine()) {
                String sor = sc.nextLine();
                if (!sor.isEmpty()) {
                    assetLista.add(new Asset(sor));
                }
            }
        } catch (Exception e) {
            System.err.println("Hiba a beolvasásnál: ");
        }

        // Átalakítás fix méretű tömbbé
        Asset[] assetTomb = assetLista.toArray(new Asset[0]);

        // Teszt: Írassuk ki az ellopott tárgyakat
        System.out.println("Ellopott tárgyak listája:");
        for (Asset a : assetTomb) {
            if (a.isStolen) {
                System.out.println("- " + a.name + " (Tulaj: " + a.ownerName + ", Tolvaj: " + a.thiefName + ")");
            }
        }

        System.out.println();

        Asset maxLopas = Arrays.stream(assetTomb)
                .filter(a -> a.isStolen)
                .max(Comparator.comparingInt(a -> a.price))
                .orElse(null);

        if (maxLopas != null) {
            System.out.println("Legértékesebb lopás: " + maxLopas.name);
            System.out.println("Értéke: " + maxLopas.price + " Ft");
            System.out.println("Elkövető: " + maxLopas.thiefName);
        } else {
            System.out.println("Nem találtam ellopott tárgyat.");
        }



        System.out.println("3. Feladat: Ismert tolvajok (tulaj szülinap szerint csökkenő):");
        Arrays.stream(assetTomb)
                .filter(a -> a.isStolen && a.thiefName != null && !a.thiefName.equals("Nincs adat"))
                .sorted(Comparator.comparing((Asset a) -> a.ownerBirthday).reversed())
                .forEach(a -> System.out.println(a.ownerName + " (" + a.ownerBirthday + ") -> " + a.name));
        Set<String> mindenNev = Arrays.stream(assetTomb)
                .flatMap(a -> Stream.of(a.ownerName, a.thiefName))
                .filter(nev -> !nev.equals("Nincs adat"))
                .collect(Collectors.toSet());


        System.out.println();

        System.out.println("4. Feladat: Egyedi nevek száma: " + mindenNev.size());

        List<String> nevLista = new ArrayList<>(mindenNev);
        String randomNev = nevLista.get(new Random().nextInt(nevLista.size()));

        boolean loptakTole = Arrays.stream(assetTomb).anyMatch(a -> a.ownerName.equals(randomNev) && a.isStolen);
        boolean oLopott = Arrays.stream(assetTomb).anyMatch(a -> a.thiefName.equals(randomNev));

        System.out.println("Név: " + randomNev);
        System.out.println("Loptak-e tőle: " + (loptakTole ? "Igen" : "Nem"));
        System.out.println("Lopott-e ő: " + (oLopott ? "Igen" : "Nem"));

        System.out.println();

        Asset leghosszabbNevu = Arrays.stream(assetTomb)
                .max(Comparator.comparingInt(a -> szoSzam(a.name)))
                .orElse(null);
        System.out.println("5. Feladat: Legtöbb szóból álló terméknév: " + leghosszabbNevu.name);

        System.out.println();

        System.out.println("6. Feladat: Statisztika személyenként:");
        Arrays.stream(assetTomb)
                .filter(a -> a.isStolen && !a.thiefName.equals("Nincs adat"))
                .collect(Collectors.groupingBy(a -> a.thiefName))
                .forEach((tolvaj, targyLista) -> {
                    String evek = targyLista.stream()
                            .map(a -> a.thief_birthday) // Tegyük fel, hogy van ilyen mezőnk
                            .sorted()
                            .map(String::valueOf)
                            .collect(Collectors.joining(", ")); // Az utolsó után nem lesz vessző!

                    System.out.println(tolvaj + ": " + evek);
                });

    }

}