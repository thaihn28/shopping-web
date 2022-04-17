package com.example.shoppingweb.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

import javax.persistence.*;

@Entity
@Table(name =  "admin", uniqueConstraints = @UniqueConstraint(columnNames = "username"))
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Admin {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;
	@Column(name = "username")
	private String username;

	private String password;

//	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//	@JoinTable(
//			name = "admin_roles",
//			joinColumns = @JoinColumn(
//					name = "admin_id", referencedColumnName = "id"),
//			inverseJoinColumns = @JoinColumn(
//					name = "role_id", referencedColumnName = "id"))
	@OneToOne
	private Role role;
}
	

