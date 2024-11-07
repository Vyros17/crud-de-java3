package com.crud.consse.services.impl;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.crud.consse.domain.entities.CarEntity;
import com.crud.consse.services.CarService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarServiceImpl implements CarService {
    private long id=1L;
    private ArrayList<CarEntity> carEntities;

    public CarServiceImpl() {
        this.carEntities = new ArrayList<>();
        this.carEntities.add(new CarEntity(id, "83Hf", "Auto","Chevrolet",80f));
        id++;
    }
    public ArrayList<CarEntity> sendError(){
        ArrayList<CarEntity> empty = new ArrayList<CarEntity>();
        empty.add(new CarEntity(0,"La lista de autos está vacia.","","",0));
        return empty;
    }

    @Override
    public String createCar(CarEntity carEntity) {
        carEntity.setId(id);
        id++;
        this.carEntities.add(carEntity);
        return "Auto Agregado";
    }

    @Override
    public  ArrayList<CarEntity> getAllCars() {
        if(carEntities.isEmpty()){
            return sendError();
        }
        return carEntities;
    }

    @Override
    public ArrayList<Long> getIdCars() {
        return new ArrayList<>(carEntities.stream().map(CarEntity::getId).toList());
    }

    @Override
    public ArrayList<String> getPlateCars() {
        return new ArrayList<>(carEntities.stream().map(CarEntity::getPlate).toList());
    }

    @Override
    public Optional<CarEntity> getCarById(long id){
        return carEntities.stream().filter(e -> e.getId()==id).findFirst();
    }

    @Override
    public void report(long option,long filterOption,String referen) throws IOException {
        File file = new File("Reporte.txt");
        file.createNewFile();
        FileWriter fw = new FileWriter("Reporte.txt");
        ArrayList<CarEntity> result = new ArrayList<>();
        result = switch ((int) option) {
            case 1 -> switch ((int) filterOption) {
                case 1 ->
                        new ArrayList<>(carEntities.stream().filter(e -> e.getPlate().startsWith(referen)).collect(Collectors.toList()));
                case 2 ->
                        new ArrayList<>(carEntities.stream().filter(e -> e.getPlate().equals(referen)).collect(Collectors.toList()));
                case 3 ->
                        new ArrayList<>(carEntities.stream().filter(e -> e.getPlate().endsWith(referen)).collect(Collectors.toList()));
                default -> result;
            };
            case 2 -> switch ((int) filterOption) {
                case 1 ->
                        new ArrayList<>(carEntities.stream().filter(e -> e.getModel().startsWith(referen)).collect(Collectors.toList()));
                case 2 ->
                        new ArrayList<>(carEntities.stream().filter(e -> e.getModel().equals(referen)).collect(Collectors.toList()));
                case 3 ->
                        new ArrayList<>(carEntities.stream().filter(e -> e.getModel().endsWith(referen)).collect(Collectors.toList()));
                default -> result;
            };
            case 3 -> switch ((int) filterOption) {
                case 1 ->
                        new ArrayList<>(carEntities.stream().filter(e -> e.getBrand().startsWith(referen)).collect(Collectors.toList()));
                case 2 ->
                        new ArrayList<>(carEntities.stream().filter(e -> e.getBrand().equals(referen)).collect(Collectors.toList()));
                case 3 ->
                        new ArrayList<>(carEntities.stream().filter(e -> e.getBrand().endsWith(referen)).collect(Collectors.toList()));
                default -> result;
            };
            case 4 -> {
                float speed = Float.parseFloat(referen);
                yield switch ((int) filterOption) {
                    case 1 ->
                            new ArrayList<>(carEntities.stream().filter(e -> e.getSpeed() < speed).collect(Collectors.toList()));
                    case 2 ->
                            new ArrayList<>(carEntities.stream().filter(e -> e.getSpeed() <= speed).collect(Collectors.toList()));
                    case 3 ->
                            new ArrayList<>(carEntities.stream().filter(e -> e.getSpeed() == speed).collect(Collectors.toList()));
                    case 4 ->
                            new ArrayList<>(carEntities.stream().filter(e -> e.getSpeed() > speed).collect(Collectors.toList()));
                    case 5 ->
                            new ArrayList<>(carEntities.stream().filter(e -> e.getSpeed() >= speed).collect(Collectors.toList()));
                    default -> result;
                };
            }
            default -> result;
        };
        result.forEach(e -> {
            try {
                fw.write(String.format("Placa: %s, Modelo: %s, Marca: %s, Velocidad: %.2f\n",e.getPlate(),e.getModel(),e.getBrand(),e.getSpeed()));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        fw.close();
    }

    @Override
    public ResponseEntity<Object> makeReport(long Option,long FilterOption,String referen) throws IOException {
        report(Option,FilterOption,referen);
        String filename = "Reporte.txt";
        File file = new File(filename);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition",String.format("attachment; filename=\"%s\"",file.getName()));
        headers.add("Cache-Control","no-cache, no-store, must-revalidate");
        headers.add("Pragma","no-cache");
        headers.add("Expires","0");
        ResponseEntity<Object> responseEntity = ResponseEntity.ok().headers(headers).contentLength(file.length()).contentType(MediaType.parseMediaType("application/txt")).body(resource);
        return responseEntity;
    }


    @Override
    public ArrayList<Long> getCarsById(){
        return new ArrayList<>(carEntities.stream().map(CarEntity::getId).toList());
    }

    @Override
    public String deleteCar(long id) {
        Optional<CarEntity> query = this.getCarById(id);
        if(query.isEmpty()){
            return "Auto No encontrado";
        }else{
            carEntities = new ArrayList<>(carEntities.stream().filter(e -> !(e.getId()==id)).toList());
            return "Auto Eliminado";
        }
    }

    @Override
    public Optional<CarEntity> updateCar(long id, CarEntity carEntity) {
        Optional<CarEntity> query = this.getCarById(id);
        if(query.isEmpty()){
            return query;
        }
        CarEntity car = query.get();
        car.setPlate(carEntity.getPlate());
        car.setModel(carEntity.getModel());
        car.setBrand(carEntity.getBrand());
        car.setSpeed(carEntity.getSpeed());
        return query;
    }
}
