package com.andersenlab.model;

import java.time.LocalDate;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "room")
@EqualsAndHashCode(exclude = {"reservations"})
@Data
@NoArgsConstructor
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    /** Метод проверяет забронирован ли номер на указанный период времени
     * @param dateBegin начало периода
     * @param dateEnd   окончание периода
     * @return true - если забронирован, false - если свободен */
    public Boolean isBooked(LocalDate dateBegin, LocalDate dateEnd) {
        if (this.getReservations() == null)
            return false;

        return this.getReservations().stream().anyMatch(res -> {
            if ((res.getDateBegin().compareTo(dateBegin) >= 0) &&
                    (res.getDateBegin().compareTo(dateEnd) <= 0))
                return true;

            if ((res.getDateEnd().compareTo(dateBegin) >= 0) &&
                    (res.getDateEnd().compareTo(dateEnd) <= 0))
                return true;

            return false;
        });
    }

}
