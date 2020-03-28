package com.andersenlab.model;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "facilities")
@Data
@EqualsAndHashCode(exclude = {"version"})
public class Facilities {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
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
