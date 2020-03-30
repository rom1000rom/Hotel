package com.andersenlab.model;

import java.util.Set;
import javax.persistence.*;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "hotel")
@Data
@EqualsAndHashCode(exclude = {"id", "roomSet", "facilitiesSet", "version"})
public class Hotel {

  @Id
  @SequenceGenerator( name = "hotelSeq", sequenceName = "hotel_seq", allocationSize = 1, initialValue = 3 )
  @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "hotelSeq")
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
