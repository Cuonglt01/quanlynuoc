package ql.bn.qlbannuoc.Model;

import java.io.Serializable;

public class Waterbottle implements Serializable {
    private int id;
    private String name;
    private String address;
    private String type;
    private String brand;
    private String date;
    private byte[] image;
    private String price;
    private int id_user;



    public Waterbottle() {
    }

    public Waterbottle(int id, String name, String address, String type, String brand, String date, byte[] image, String price, int id_user) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.type = type;
        this.brand = brand;
        this.date = date;
        this.image = image;
        this.price = price;
        this.id_user = id_user;
    }

    public Waterbottle(String name, String address, String type, String brand, String date, byte[] image, String price, int id_user) {
        this.name = name;
        this.address = address;
        this.type = type;
        this.brand = brand;
        this.date = date;
        this.image = image;
        this.price = price;
        this.id_user = id_user;
    }



    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
