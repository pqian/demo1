package com.github.pqian.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(of = { "id", "name", "price"})
@Entity
public class Product implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	private String brand;
	
	private BigDecimal price;
	
	private String image;
	
}
