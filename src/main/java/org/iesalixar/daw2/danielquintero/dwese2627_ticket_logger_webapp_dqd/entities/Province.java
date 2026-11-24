package org.iesalixar.daw2.danielquintero
        .dwese2627_ticket_logger_webapp_dqd.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "provinces")
public class
Province {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            name = "code",
            nullable = false,
            unique = true,
            length = 10
    )
    private String code;

    @Column(
            name = "name",
            nullable = false,
            length = 100
    )
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "region_id",
            nullable = false
    )
    private Region region;


    public Province(
            String code,
            String name,
            Region region) {

        this.code = code;
        this.name = name;
        this.region = region;
    }
}