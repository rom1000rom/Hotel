package com.andersenlab.model;


import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDate;

/**Класс представляет собой бронирование номера.
 @author Артемьев Р.А.
 @version 05.03.2020 */
@Entity
@Table(name = "reservation")
@Data
@EqualsAndHashCode(exclude = {"person","room"})
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Version
    private Integer version;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id")
    private Person person;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    private LocalDate dateBegin;
    private LocalDate dateEnd;

    public Reservation() {
    }

    public Reservation(LocalDate dateBegin, LocalDate dateEnd) {
        this.dateBegin = dateBegin;
        this.dateEnd = dateEnd;
    }

}
