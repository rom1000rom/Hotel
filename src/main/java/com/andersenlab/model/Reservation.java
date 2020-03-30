package com.andersenlab.model;



import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

/**Класс представляет собой бронирование номера.
 @author Артемьев Р.А.
 @version 05.03.2020 */
@Entity
@Table(name = "reservation")
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"person","room"})
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private Integer version;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id")
    private Person person;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @Column(name = "date_begin")
    private LocalDate dateBegin;

    @Column(name = "date_end")
    private LocalDate dateEnd;

    public Reservation(LocalDate dateBegin, LocalDate dateEnd) {
        this.dateBegin = dateBegin;
        this.dateEnd = dateEnd;
    }

}
