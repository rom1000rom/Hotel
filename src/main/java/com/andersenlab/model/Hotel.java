package com.andersenlab.model;

import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "hotel")
@Data
@EqualsAndHashCode(exclude = {"id", "roomSet", "facilitiesSet", "version"})
public class Hotel {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Version
  private Integer version;

  @Column(name = "hotel_name")
  private String hotelName;

  @OneToMany(mappedBy = "hotelId", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<Room> roomSet;

  @OneToMany(mappedBy = "hotelId", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<Facilities> facilitiesSet;

}
