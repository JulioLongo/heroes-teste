package com.pointnexus.heroes.heroestest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class HeroiTeste {
    int id;
    String class_name;

    @SerializedName("class_id")
    @Expose
    private Integer classId;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("health_points")
    @Expose
    private Integer healthPoints;

    @SerializedName("defense")
    @Expose
    private Integer defense;

    @SerializedName("damage")
    @Expose
    private Integer damage;

    @SerializedName("attack_speed")
    @Expose
    private Double attackSpeed;

    @SerializedName("moviment_speed")
    @Expose
    private Integer movimentSpeed;

    @SerializedName("specialties")
    @Expose
    private ArrayList<Integer> specialties = null;

    @SerializedName("photos")
    @Expose
    private  ArrayList<Integer> photos = null;

    public HeroiTeste(Integer classId, String name, Integer healthPoints, Integer defense, Integer damage, Double attackSpeed, Integer movimentSpeed, ArrayList<Integer> specialties, ArrayList<Integer> photos) {
        this.classId = classId;
        this.name = name;
        this.healthPoints = healthPoints;
        this.defense = defense;
        this.damage = damage;
        this.attackSpeed = attackSpeed;
        this.movimentSpeed = movimentSpeed;
        this.specialties = specialties;
        this.photos = photos;
    }

    public int getId() {
        return id;
    }

    public String getClassName() {
        return class_name;
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getHealthPoints() {
        return healthPoints;
    }

    public void setHealthPoints(Integer healthPoints) {
        this.healthPoints = healthPoints;
    }

    public Integer getDefense() {
        return defense;
    }

    public void setDefense(Integer defense) {
        this.defense = defense;
    }

    public Integer getDamage() {
        return damage;
    }

    public void setDamage(Integer damage) {
        this.damage = damage;
    }

    public Double getAttackSpeed() {
        return attackSpeed;
    }

    public void setAttackSpeed(Double attackSpeed) {
        this.attackSpeed = attackSpeed;
    }

    public Integer getMovimentSpeed() {
        return movimentSpeed;
    }

    public void setMovimentSpeed(Integer movimentSpeed) {
        this.movimentSpeed = movimentSpeed;
    }

    public  ArrayList<Integer> getSpecialties() {
        return specialties;
    }

    public void setSpecialties( ArrayList<Integer> specialties) {
        this.specialties = specialties;
    }

    public  ArrayList<Integer> getPhotos() {
        return photos;
    }

    public void setPhotos(ArrayList<Integer> photos) {
        this.photos = photos;
    }

}
