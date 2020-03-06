package com.andersenlab.model;

import java.util.List;
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

  private String encrytedPassword;

  /**Находится ли пользователь в "черном" списке*/
  private Boolean blacklisted = false;

  /**Является ли пользователь администратором*/
  private Boolean admin = false;

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

  public Boolean getAdmin() {
    return admin;
  }

  public void setAdmin(Boolean admin) {
    this.admin = admin;
  }

  public Boolean getBlacklisted() {
    return blacklisted;
  }

  public void setBlacklisted(Boolean blacklisted) {
    this.blacklisted = blacklisted;
  }

  public String getEncrytedPassword() {
    return encrytedPassword;
  }

  public void setEncrytedPassword(String encrytedPassword) {
    this.encrytedPassword = encrytedPassword;
  }
}
