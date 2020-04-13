package com.andersenlab.model;

import java.math.BigDecimal;
import javax.persistence.*;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "facilities")
@Data
@EqualsAndHashCode(exclude = {"version"})
public class Facilities {

  @Id
  @SequenceGenerator( name = "facilitiesSeq", sequenceName = "facilities_seq",
          allocationSize = 1, initialValue = 4 )
  @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "facilitiesSeq")
  private Long id;

  @Version
  private Integer version;
  
  @Column(name = "facilities_number")
  private String facilitiesNumber;
  
  @Column(name = "facilities_name")
  private String facilitiesName;
  
  @Column(name = "facilities_price")
  private BigDecimal facilitiesPrice;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "hotel_id")
  private Hotel hotelId;
}
