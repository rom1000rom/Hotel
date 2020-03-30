package com.andersenlab.model;


import java.util.List;
import javax.persistence.*;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "room")
@EqualsAndHashCode(exclude = {"id","version","reservations"})
@Data
@NoArgsConstructor
public class Room {

    @Id
    @SequenceGenerator(name = "roomSeq", sequenceName = "room_seq",
            allocationSize = 1, initialValue = 11 )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "roomSeq")
    private Long id;

    @Version
    private Integer version;

    @Column(name = "room_number", nullable = false)
    private String number;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id")
    private Hotel hotelId;

    @Fetch(FetchMode.JOIN)
    @OneToMany(mappedBy = "room")
    private List<Reservation> reservations;

    public Room(String number) {
        this.number = number;
    }

}
