package com.ivanalimin.spring_mvc_json_view.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    @JsonView(Views.UserSummary.class)
    private UUID id;

    @NotBlank(message = "Name is mandatory")
    @Column(name = "name")
    @JsonView(Views.UserSummary.class)
    private String name;

    @Email(message = "Email address is not valid")
    @NotBlank(message = "Email is mandatory")
    @Column(name = "email")
    @JsonView(Views.UserSummary.class)
    private String email;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    @JsonView(Views.UserDetails.class)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonManagedReference
    private Set<Order> orders;

}
