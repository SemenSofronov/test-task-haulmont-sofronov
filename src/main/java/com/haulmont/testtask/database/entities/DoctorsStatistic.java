package com.haulmont.testtask.database.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "DOCTORSSTATISTIC", schema = "PUBLIC", catalog = "PUBLIC")
public class DoctorsStatistic {

    private long uniqueId;
    private String surname;
    private long value;


    @Id
    @Column(name = "UNIQUE_ID")
    public long getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(long uniqueId) {
        this.uniqueId = uniqueId;
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
    @Column(name = "VALUE")
    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }


}
