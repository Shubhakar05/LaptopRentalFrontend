package com.ashokit.model;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Integer userId;
	    private String name;
	    private String phoneno;
	    private String email;
	    private String password;
	    @Column(name = "reset_token")
	    private String resetToken;
	    
	    private String address;
	    
	    
	    private LocalDateTime resetTokenExpiry;
	    

	    @Enumerated(EnumType.STRING)
	    private Roles role;
	    
	   
	 
	    @OneToMany(mappedBy = "companyId")
	    @JsonIgnore
	    private List<RentalOrder> rentalOrders;

}
