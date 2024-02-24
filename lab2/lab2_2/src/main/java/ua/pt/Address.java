package ua.pt;

import java.util.Objects;

public class Address {
    private String houseNumber;
    private String city;
    private String road;
    private String zipCode;

    public Address(String road, String city, String zipCode, String houseNumber){
        this.houseNumber = houseNumber;
        this.city = city;
        this.road = road;
        this.zipCode = zipCode;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public String getCity() {
        return city;
    }

    public String getRoad() {
        return road;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setRoad(String road) {
        this.road = road;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    @Override
    public String toString() {
        return "======== Address ========\nRoad: [" + road + "]\nCity: " + city + "\nZipcode: [" + zipCode + "]\nNumber: [" + houseNumber + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Address address = (Address) o;

        if (!road.equals(address.road)) return false;
        if (!Objects.equals(city, address.city)) return false;
        if (!Objects.equals(zipCode, address.zipCode)) return false;
        return Objects.equals(houseNumber, address.houseNumber);
    }

    @Override
    public int hashCode() {
        int result = road.hashCode();
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (zipCode != null ? zipCode.hashCode() : 0);
        result = 31 * result + (houseNumber != null ? houseNumber.hashCode() : 0);
        return result;
    }


}
