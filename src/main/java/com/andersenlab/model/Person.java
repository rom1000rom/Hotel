package com.andersenlab.model;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.persistence.*;

import lombok.Data;

@Entity
@Table(name = "person")
@Data
public class Person {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Version
  private Integer version;

  private Boolean blacklisted = false;

  @Column(name = "person_name")
  private String personName;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "room_person", joinColumns = @JoinColumn(name = "person_id"),
      inverseJoinColumns = @JoinColumn(name = "room_id"))
  private Set<Room> roomSet;

  @OneToMany(mappedBy = "person")
  private List<Reservation> reservations;

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

  public String getPersonName() {
    return personName;
  }

  public void setPersonName(String personName) {
    this.personName = personName;
  }

  public Set<Room> getRoomSet() {
    return roomSet;
  }

  public void setRoomSet(Set<Room> roomSet) {
    this.roomSet = roomSet;
  }

  public Long getId() {
    return id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Person)) return false;
    Person person = (Person) o;
    return Objects.equals(id, person.id) &&
            Objects.equals(version, person.version) &&
            Objects.equals(personName, person.personName) &&
            Objects.equals(roomSet, person.roomSet);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, version, personName, roomSet);
  }

  public Boolean getBlacklisted() {
    return blacklisted;
  }

  public void setBlacklisted(Boolean blacklisted) {
    this.blacklisted = blacklisted;
  }
}
