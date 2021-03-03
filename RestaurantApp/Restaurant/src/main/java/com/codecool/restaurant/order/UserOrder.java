package com.codecool.restaurant.order;

import com.codecool.restaurant.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "USER_ORDER")
public class UserOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String status;
    private Double totalPrice;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable=false, updatable=false)
    private Date date;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    public UserOrder(String status, Double totalPrice, User user) {
        this.status = status;
        this.totalPrice = totalPrice;
        this.date = new Date();
        this.user = user;
    }
}
