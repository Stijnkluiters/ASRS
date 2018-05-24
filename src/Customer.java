public class Customer {

    private String firstName;

    private String lastName;

    private String adres;

    private String zipcode;

    private String city;

    public void setCity(String city) {
        this.city = city;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public void setLastName(String lastName) {
        this.lastName = this.ucfirst(lastName);
    }

    public void setFirstName(String firstName) {
        this.firstName = this.ucfirst(firstName);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAdres() {
        return adres;
    }

    public String getZipcode() {
        return zipcode;
    }

    public String getCity() {
        return city;
    }

    @Override
    public String toString() {
        return this.getFirstName() + " " + this.getLastName() + " woont op " + this.getAdres() + " postcode: " + this.getZipcode() + " in de stad: " + this.getCity();
    }

    private String ucfirst(String input) {
        if (input == null || input.length() <= 0) {
            return input;
        }
        char[] chars = new char[1];
        input.getChars(0, 1, chars, 0);
        if (Character.isUpperCase(chars[0])) {
            return input;
        } else {
            StringBuilder buffer = new StringBuilder(input.length());
            buffer.append(Character.toUpperCase(chars[0]));
            buffer.append(input.toCharArray(), 1, input.length() - 1);
            return buffer.toString();
        }
    }
}
