package com.haulmont.testtask.database.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "PRIORITIES", schema = "PUBLIC", catalog = "PUBLIC")
public class PrioritiesEntity {
    private long uniqueId;
    private String name;

    public PrioritiesEntity() {
    }

    public PrioritiesEntity(String name) {
        this.name = name;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrioritiesEntity that = (PrioritiesEntity) o;
        return uniqueId == that.uniqueId &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(uniqueId, name);
    }
}
