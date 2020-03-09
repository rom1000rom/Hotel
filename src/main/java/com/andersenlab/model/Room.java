package com.andersenlab.model;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;
import lombok.Data;

@Entity
@Table(name = "room")
@Data
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

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "room_person", joinColumns = @JoinColumn(name = "room_id"),
      inverseJoinColumns = @JoinColumn(name = "person_id"))
  private Set<Person> personSet;

  @OneToMany(mappedBy = "room")
  private List<Reservation> reservations;

  public Room(String number) {
    this.number = number;
  }

  public Room() {
  }

  public List<Reservation> getReservations() {
    return reservations;
  }

  public void setReservations(List<Reservation> reservations) {
    this.reservations = reservations;
  }

  public Integer getVersion() {
    return version;
  }

  public void setVersion(Integer version) {
    this.version = version;
  }

  public String getNumber() {
    return number;
  }

  public void setNumber(String number) {
    this.number = number;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Hotel getHotelId() {
    return hotelId;
  }

  public void setHotelId(Hotel hotelId) {
    this.hotelId = hotelId;
  }

  public Set<Person> getPersonSet() {
    return personSet;
  }

  public void setPersonSet(Set<Person> personSet) {
    this.personSet = personSet;
  }

  public Long getId() {
    return id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Room)) return false;
    Room room = (Room) o;
    return Objects.equals(id, room.id) &&
            Objects.equals(version, room.version) &&
            Objects.equals(number, room.number) &&
            Objects.equals(hotelId, room.hotelId) &&
            Objects.equals(personSet, room.personSet);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, version, number, hotelId, personSet);
  }
}
