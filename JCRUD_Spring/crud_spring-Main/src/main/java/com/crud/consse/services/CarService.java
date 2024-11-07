package com.crud.consse.services;
import com.crud.consse.domain.entities.CarEntity;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;


public interface CarService {
    public String createCar(CarEntity carEntity);
    public ArrayList<CarEntity> getAllCars();
    public ArrayList<Long> getIdCars();
    public ArrayList<String> getPlateCars();
    public ArrayList<Long>  getCarsById();
    public String deleteCar(long id);
    public Optional<CarEntity> updateCar(long id,CarEntity carEntity);
    public Optional<CarEntity> getCarById(long id);
    public void report(long Option,long FilterOption,String referen) throws IOException;
    public ResponseEntity<Object> makeReport(long Option,long FilterOption,String referen) throws IOException;
}
