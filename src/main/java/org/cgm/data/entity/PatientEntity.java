package org.cgm.data.entity;

import java.util.UUID;
import org.hibernate.annotations.UuidGenerator;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "patients")
public class PatientEntity {

    @Id
    @UuidGenerator
    private UUID uuid;

    @Column(nullable = false)
    private String name;

}
