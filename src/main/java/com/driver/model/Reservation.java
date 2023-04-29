package com.driver.model;

import javax.persistence.*;

@Entity
@Table(name = "reservation")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    int numberOfHours;



    @ManyToOne
    @JoinColumn
    User user;


    @ManyToOne
    @JoinColumn
    Spot spot;


    @OneToOne
    @JoinColumn
    Payment payment;

    public Reservation() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumberOfHours() {
        return numberOfHours;
    }

    public void setNumberOfHours(int numberOfHours) {
        this.numberOfHours = numberOfHours;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Spot getSpot() {
        return spot;
    }

    public void setSpot(Spot spot) {
        this.spot = spot;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }
}
