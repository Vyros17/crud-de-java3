package com.crud.consse.domain.entities;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarEntity {
    private long id;
    private String plate;
    private String model;
    private String brand;
    private float speed;
}
