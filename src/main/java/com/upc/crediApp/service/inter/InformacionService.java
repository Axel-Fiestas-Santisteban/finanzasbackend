package com.upc.crediApp.service.inter;

import com.upc.crediApp.model.Informacion;

import java.util.List;

public interface InformacionService {

    List<Informacion> getAllInformaciones();

    Informacion createInformacion(Informacion informacion);

    void deleteInformacion(Long id);



}
