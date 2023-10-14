package Mingeso.TopEducation.Services;

import Mingeso.TopEducation.Entities.PruebaEntity;
import Mingeso.TopEducation.Repositories.PruebaRepository;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@Service
public class PruebaService {
    @Autowired
    PruebaRepository pruebaRepository;

    private final Logger logg = LoggerFactory.getLogger(PruebaService.class);

    public ArrayList<PruebaEntity> obtenerDatos()
    {
        return (ArrayList<PruebaEntity>) pruebaRepository.findAll();
    }

    public void guardarDatos(PruebaEntity prueba){pruebaRepository.save(prueba);
    }

    public void guardarDatosBD(String RUN, String puntaje, String fechaPrueba){
        PruebaEntity newPep = new PruebaEntity();
        Integer puntos = Integer.parseInt(puntaje);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");;
        LocalDate fechaPep = LocalDate.parse(fechaPrueba, formatter);
        newPep.setFechaPrueba(fechaPep);
        newPep.setRUN(RUN);
        newPep.setPuntaje(puntos);
        guardarDatos(newPep);
    }

    @Generated
    public void leerCsv(String direccion){
        String texto = "";
        BufferedReader bf = null;
        pruebaRepository.deleteAll();
        try{
            bf = new BufferedReader(new FileReader(direccion));
            String temp = "";
            String bfRead;
            int count = 1;
            while((bfRead = bf.readLine()) != null){
                if (count == 1){
                    count = 0;
                }
                else{
                    guardarDatosBD(bfRead.split(";")[0], bfRead.split(";")[1], bfRead.split(";")[2]);
                    temp = temp + "\n" + bfRead;
                }
            }
            texto = temp;
            System.out.println("Archivo leido exitosamente");
        }catch(Exception e){
            System.err.println("No se encontro el archivo");
        }finally{
            if(bf != null){
                try{
                    bf.close();
                }catch(IOException e){
                    logg.error("ERROR", e);
                }
            }
        }
    }


    @Generated
    public String guardar(MultipartFile file){
        String filename = file.getOriginalFilename();
        if(filename != null){
            if(!file.isEmpty()){
                try{
                    byte [] bytes = file.getBytes();
                    Path path  = Paths.get(file.getOriginalFilename());
                    Files.write(path, bytes);
                    logg.info("Archivo guardado");
                }
                catch (IOException e){
                    logg.error("ERROR", e);
                }
            }
            return "Archivo guardado con exito!";
        }
        else{
            return "No se pudo guardar el archivo";
        }
    }
}
