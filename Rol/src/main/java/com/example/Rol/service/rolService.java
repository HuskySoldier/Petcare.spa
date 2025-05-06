package com.example.Rol.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Rol.model.Rol;
import com.example.Rol.repository.rolRepository;

@Service
public class rolService {
    @Autowired
    private rolRepository rolrespository;

    public List<Rol> listarRol(){
        return rolrespository.findAll();
    }

    public Rol buscarRolPorId(Long id){
        return rolrespository.findById(id).get();
    }

    public Rol agregarRol(Rol rol){
        return rolrespository.save(rol);
    }

    public void eliminarRol(Long id){
        rolrespository.deleteById(id);
    }
}
