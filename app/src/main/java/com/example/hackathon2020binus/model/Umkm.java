package com.example.hackathon2020binus.model;

public class Umkm {

    private String deksripsi;
    private String gambar;
    private String id;
    private String nama;
    private Boolean openToFranchise;
    private Boolean openToPartnership;
    private String ownerName;
    private String phone;

    public String getDeksripsi() {
        return deksripsi;
    }

    public void setDeksripsi(String deksripsi) {
        this.deksripsi = deksripsi;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public Boolean getOpenToFranchise() {
        return openToFranchise;
    }

    public void setOpenToFranchise(Boolean openToFranchise) {
        this.openToFranchise = openToFranchise;
    }

    public Boolean getOpenToPartnership() {
        return openToPartnership;
    }

    public void setOpenToPartnership(Boolean openToPartnership) {
        this.openToPartnership = openToPartnership;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }



    public Umkm(String deksripsi, String gambar, String id, String nama, Boolean openToFranchise, Boolean openToPartnership, String ownerName, String phone) {
        this.deksripsi = deksripsi;
        this.gambar = gambar;
        this.id = id;
        this.nama = nama;
        this.openToFranchise = openToFranchise;
        this.openToPartnership = openToPartnership;
        this.ownerName = ownerName;
        this.phone = phone;
    }


}
