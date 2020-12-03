package com.example.hackathon2020binus.model;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

import com.google.firebase.firestore.Exclude;

public class Umkm implements Parcelable {

    private String deksripsi;
    private String gambar;
    private String id;
    private String nama;
    private Boolean openToFranchise;
    private Boolean openToPartnership;
    private String ownerName;
    private String phone;
    private Double latitude;
    private Double longitude;

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public  Umkm(){

    }
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


    public Umkm(String deksripsi, String gambar, String id, String nama, Boolean openToFranchise, Boolean openToPartnership, String ownerName, String phone, Double latitude, Double longitude) {
        this.deksripsi = deksripsi;
        this.gambar = gambar;
        this.id = id;
        this.nama = nama;
        this.openToFranchise = openToFranchise;
        this.openToPartnership = openToPartnership;
        this.ownerName = ownerName;
        this.phone = phone;
        this.latitude = latitude;
        this.longitude = longitude;
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


    @Override
    public int describeContents() {
        return 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(deksripsi);
        dest.writeString(gambar);
        dest.writeString(id);
        dest.writeString(nama);
        //dest.writeString(String.valueOf(openToFranchise));
        dest.writeByte((byte) (openToFranchise ? 1 : 0));
        dest.writeByte((byte) (openToPartnership ? 1 : 0));
        //dest.writeString(String.valueOf(openToPartnership));
        dest.writeString(ownerName);
        dest.writeString(phone);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    protected Umkm(Parcel in){
        this.deksripsi = in.readString();
        this.gambar = in.readString();
        this.id = in.readString();
        this.nama = in.readString();
        this.openToFranchise = in.readByte() != 0;
        this.openToPartnership = in.readByte() != 0;
        this.ownerName = in.readString();
        this.phone = in.readString();
        this.latitude = in.readDouble();
        this.longitude = in.readDouble();
    }

    public static final Parcelable.Creator<Umkm> CREATOR = new Parcelable.Creator<Umkm>(){

        @RequiresApi(api = Build.VERSION_CODES.Q)
        @Override
        public Umkm createFromParcel(Parcel source) {
            return new Umkm(source);
        }

        @Override
        public Umkm[] newArray(int size) {
            return new Umkm[size];
        }
    };

}
