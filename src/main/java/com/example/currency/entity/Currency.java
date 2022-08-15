package com.example.currency.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Currency implements Serializable {

    @Column
    private String countryName;

    @Id
    private String countryCode;

}

