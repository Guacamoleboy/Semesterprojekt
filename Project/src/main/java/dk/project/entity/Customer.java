package dk.project.entity;

import java.time.LocalDateTime;

public class Customer {

    // Attributes
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String street;
    private String city;
    private String zipcode;
    private String country;
    private LocalDateTime createdAt;

    // ________________________________________________________

    public Customer() {}

    public Customer(int id, String firstName, String lastName, String email, String phone,
    String street, String city, String zipcode, String country, LocalDateTime createdAt) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.street = street;
        this.city = city;
        this.zipcode = zipcode;
        this.country = country;
        this.createdAt = createdAt;
    }

    // ________________________________________________________

    public int getId() {
        return id;
    }

    // ________________________________________________________

    public void setId(int id) {
        this.id = id;
    }

    // ________________________________________________________

    public String getFirstName() {
        return firstName;
    }

    // ________________________________________________________

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    // ________________________________________________________

    public String getLastName() {
        return lastName;
    }

    // ________________________________________________________

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    // ________________________________________________________

    public String getEmail() {
        return email;
    }

    // ________________________________________________________

    public void setEmail(String email) {
        this.email = email;
    }

    // ________________________________________________________

    public String getPhone() {
        return phone;
    }

    // ________________________________________________________

    public void setPhone(String phone) {
        this.phone = phone;
    }

    // ________________________________________________________

    public String getStreet() {
        return street;
    }

    // ________________________________________________________

    public void setStreet(String street) {
        this.street = street;
    }

    // ________________________________________________________

    public String getCity() {
        return city;
    }

    // ________________________________________________________

    public void setCity(String city) {
        this.city = city;
    }

    // ________________________________________________________

    public String getZipcode() {
        return zipcode;
    }

    // ________________________________________________________

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    // ________________________________________________________

    public String getCountry() {
        return country;
    }

    // ________________________________________________________

    public void setCountry(String country) {
        this.country = country;
    }

    // ________________________________________________________

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }


    // ________________________________________________________

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

} // Customer end