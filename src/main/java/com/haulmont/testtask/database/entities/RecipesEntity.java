package com.haulmont.testtask.database.entities;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "RECIPES", schema = "PUBLIC", catalog = "PUBLIC")
public class RecipesEntity {
    private long uniqueId;
    private String description;
    private PatientsEntity patient;
    private DoctorsEntity doctor;
    private Timestamp createdTimestamp;
    private Timestamp validity;
    private PrioritiesEntity priority;

    public RecipesEntity() {
    }

    public RecipesEntity(String description, PatientsEntity patient, DoctorsEntity doctor, PrioritiesEntity priority, String validity) {
        this.description = description;
        this.patient = patient;
        this.doctor = doctor;
        this.priority = priority;
        createdTimestamp = new Timestamp(System.currentTimeMillis());
        Long createdTimestampMillis = System.currentTimeMillis()/1000;
        Long validityTime = Long.valueOf(validity);
        validityTime = createdTimestampMillis + validityTime*86400;
        this.validity = new Timestamp(validityTime*1000);

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
    @Column(name = "DESCRIPTION")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PATIENT", referencedColumnName = "unique_id")
    @Fetch(FetchMode.JOIN)
    public PatientsEntity getPatient() {
        return patient;
    }

    public void setPatient(PatientsEntity patient) {
        this.patient = patient;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DOCTOR", referencedColumnName = "unique_id")
    @Fetch(FetchMode.JOIN)
    public DoctorsEntity getDoctor() {
        return doctor;
    }

    public void setDoctor(DoctorsEntity doctor) {
        this.doctor = doctor;
    }

    @Basic
    @Column(name = "CREATED_TIMESTAMP")
    public Timestamp getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(Timestamp createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    @Basic
    @Column(name = "VALIDITY")
    public Timestamp getValidity() {
        return validity;
    }

    public void setValidity(Timestamp validity) {
        this.validity = validity;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRIORITY", referencedColumnName = "unique_id")
    @Fetch(FetchMode.JOIN)
    public PrioritiesEntity getPriority() {
        return priority;
    }

    public void setPriority(PrioritiesEntity priority) {
        this.priority = priority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecipesEntity that = (RecipesEntity) o;
        return uniqueId == that.uniqueId &&
                patient == that.patient &&
                doctor == that.doctor &&
                priority == that.priority &&
                Objects.equals(description, that.description) &&
                Objects.equals(createdTimestamp, that.createdTimestamp) &&
                Objects.equals(validity, that.validity);
    }

    @Override
    public int hashCode() {

        return Objects.hash(uniqueId, description, patient, doctor, createdTimestamp, validity, priority);
    }
}
