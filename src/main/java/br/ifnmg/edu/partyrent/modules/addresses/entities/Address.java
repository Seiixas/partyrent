package br.ifnmg.edu.partyrent.modules.addresses.entities;

import br.ifnmg.edu.partyrent.modules.places.entities.Place;
import br.ifnmg.edu.partyrent.modules.users.entities.User;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name="addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String street;

    private String district;

    private String city;

    private String state;

    private String complement;

    private String number;

    private double latitude;

    private double longitude;

    private String cep;

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public LocalDateTime getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(LocalDateTime updated_at) {
        this.updated_at = updated_at;
    }

    @CreationTimestamp
    private LocalDateTime created_at;

    @UpdateTimestamp
    private LocalDateTime updated_at;

    @OneToOne(mappedBy = "address")
    private User user;

    @OneToOne(mappedBy = "address")
    private Place places;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Double.compare(address.latitude, latitude) == 0 && Double.compare(address.longitude, longitude) == 0 && Objects.equals(id, address.id) && Objects.equals(street, address.street) && Objects.equals(district, address.district) && Objects.equals(city, address.city) && Objects.equals(state, address.state) && Objects.equals(complement, address.complement) && Objects.equals(number, address.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, street, district, city, state, complement, number, latitude, longitude);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {

        return "%s, Nº%s%s, %s - %s/%s - %s".formatted(
                street,
                number,
                (complement != null ? ", " + complement : ""),
                district,
                city,
                state,
                cep
        );
    }
}
