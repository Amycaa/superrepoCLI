public class Asset {
    public int id;
    public String name;
    public Integer price; // Integer-t használunk, mert lehet üres
    public boolean isStolen;
    public String ownerName;
    public String thiefName;
    public String ownerBirthday;
    public Object thief_birthday;

    public Asset(String csvSor) {
        // A -1 limit azért kell, hogy az üres oszlopokat is kezelje a végén
        String[] s = csvSor.split(",", -1);

        this.id = Integer.parseInt(s[0]);
        this.name = s[1];

        // Ár kezelése (ha üres a mező, legyen 0 vagy null)
        this.price = (s[2].isEmpty()) ? 0 : Integer.parseInt(s[2]);

        this.isStolen = s[3].equals("1");

        // Tulajdonos teljes neve (Keresztnév + Vezetéknév)
        this.ownerName = s[5] + " " + s[6];
        this.ownerBirthday = s[8];

        // Tolvaj neve (csak ha van megadva id)
        if (!s[8].isEmpty()) {
            this.thiefName = s[9] + " " + s[10];
        } else {
            this.thiefName = "Nincs adat";
        }
        this.thief_birthday = s[11];
    }
}