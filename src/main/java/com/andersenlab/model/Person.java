package com.andersenlab.model;

import java.util.List;
import java.util.Set;
import javax.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "person")
@Data
@EqualsAndHashCode(exclude = {"roomSet", "reservations"})
public class Person {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(name = "person_name")
  private String personName;

  @Version
  private Integer version;

  private String encrytedPassword;

  /**Находится ли пользователь в "черном" списке*/
  private Boolean blacklisted = false;

  /**Является ли пользователь администратором*/
  private Boolean admin = false;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "room_person", joinColumns = @JoinColumn(name = "person_id"),
      inverseJoinColumns = @JoinColumn(name = "room_id"))
  private Set<Room> roomSet;

  @OneToMany(mappedBy = "person")
  private List<Reservation> reservations;

  public Person() {
  }

  public Person(String personName, String encrytedPassword) {
    this.encrytedPassword = encrytedPassword;
    this.personName = personName;
  }

}
