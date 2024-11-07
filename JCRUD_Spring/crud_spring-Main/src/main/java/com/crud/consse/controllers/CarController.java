package com.crud.consse.controllers;
import com.crud.consse.domain.entities.CarEntity;
import com.crud.consse.services.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Optional;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


@RestController
@RequestMapping("/cars")
public class CarController {
    private CarService carService;
    @Autowired
    public CarController(CarService carService){
        this.carService = carService;
    }
    @GetMapping("/all")
    public ArrayList<CarEntity> getAllCars(){
        return carService.getAllCars();
    }
    @GetMapping("/id")
    public ArrayList<Long> getIdCars(){
        return carService.getCarsById();
    }
    @GetMapping("/plate")
    public ArrayList<String> getPlateCars(){
        return carService.getPlateCars();
    }
    @PostMapping
    public String createCar(@RequestBody CarEntity carEntity){
        return carService.createCar(carEntity);
    }
    @PutMapping("/{id}")
    public String updateCar(@RequestBody CarEntity carEntity,@PathVariable long id){
        carService.updateCar(id,carEntity);
        return "Auto Actualizado";
    }
    @DeleteMapping("/{id}")
    public String deleteCar(@PathVariable long id){
        return carService.deleteCar(id);
    }
    @GetMapping("/reporte")
    public String showReport(){
        return "Requisitos: opcion, preferencia, referencia\nopciones:\n1)Placa, 2)Modelo, 3)Marca, 4)Velocidad\npreferencias:(Placa, modelo y marca)\n 1)Empieza con... 2)Es igual a... 3)Termina con...\n(Velocidad)\n1)Menor que...2)Menor ó igual que...3)Es igual A...4)Mayor que...5)Mayor ó igual que...\nEjemplo:localhost:8080/cars/reporte/3/1/Che";
    }
    @GetMapping("/reporte/{option}/{filterOption}/{referen}")
    public ResponseEntity<Object> makeReport(@PathVariable long option,@PathVariable long filterOption,@PathVariable String referen) throws IOException{
        return carService.makeReport(option,filterOption,referen);
    }



}
