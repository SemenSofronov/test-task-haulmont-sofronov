package com.haulmont.testtask.database.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "DOCTORS", schema = "PUBLIC", catalog = "PUBLIC")
public class DoctorsEntity {
    private long uniqueId;
    private String name;
    private String surname;
    private String middleName;
    private String specialization;

    public DoctorsEntity() {
    }

    public DoctorsEntity(String name, String surname, String middleName, String specialization) {
        this.name = name;
        this.surname = surname;
        this.middleName = middleName;
        this.specialization = specialization;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UNIQUE_ID")
    public long getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(long uniqueId) {
        this.uniqueId = uniqueId;
    }

    @Basic
    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "SURNAME")
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Basic
    @Column(name = "MIDDLE_NAME")
    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    @Basic
    @Column(name = "SPECIALIZATION")
    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DoctorsEntity that = (DoctorsEntity) o;
        return uniqueId == that.uniqueId &&
                Objects.equals(name, that.name) &&
                Objects.equals(surname, that.surname) &&
                Objects.equals(middleName, that.middleName) &&
                Objects.equals(specialization, that.specialization);
    }

    @Override
    public int hashCode() {

        return Objects.hash(uniqueId, name, surname, middleName, specialization);
    }
}
