package com.ivanalimin.spring_mvc_json_view.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "order_id", updatable = false, nullable = false, columnDefinition = "uuid")
    @JsonView(Views.UserDetails.class)
    private UUID id;

    @NotNull(message = "Order amount cannot be null")
    @Column(name = "amount")
    @JsonView(Views.UserDetails.class)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Status cannot be null")
    @Column(name = "status")
    @JsonView(Views.UserDetails.class)
    private OrderStatus status;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "order_products", joinColumns = @JoinColumn(name = "order_id"))
    @Column(name = "product")
    @JsonView(Views.UserDetails.class)
    private List<String> products;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference
    private User user;
}
