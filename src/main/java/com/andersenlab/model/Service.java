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

@Entity
@Table(name = "service")
@Data
public class Service {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Version
  private Integer version;
  
  @Column(name = "service_number")
  private String serviceNumber;
  
  @Column(name = "service_name")
  private String serviceName;
  
  @Column(name = "service_price")
  private BigDecimal servicePrice;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "hotel_id")
  private Hotel hotelId;
}
