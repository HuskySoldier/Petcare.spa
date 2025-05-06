package com.example.Cliente.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Cliente.model.Cliente;
import com.example.Cliente.repository.ClienteRepository;
@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienterespository;

    public List<Cliente> listarCliente(){
        return clienterespository.findAll();
    }

    public Cliente buscarClientePorId(Long id){
        return clienterespository.findById(id).get();
    }

    public Cliente agregarCliente(Cliente cliente){
        return clienterespository.save(cliente);
    }

    public void eliminarCliente(Long id){
        clienterespository.deleteById(id);
    }

}
