package com.example.demo.service.impl;

import com.example.demo.entity.VaiTro;
import com.example.demo.repository.VaiTroRepository;
import com.example.demo.service.VaiTroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
@Service
public class VaiTroServiceImpl implements VaiTroService {

    @Autowired
    private VaiTroRepository vaiTroRepository;

    @Override
    public List<VaiTro> getAll() {
        return vaiTroRepository.findAll();
    }

    @Override
    public VaiTro add(VaiTro vaiTro) {
        return vaiTroRepository.save(vaiTro);
    }

    @Override
    public VaiTro update(VaiTro vaiTro) {
        return vaiTroRepository.save(vaiTro);
    }

    @Override
    public VaiTro getOne(UUID id) {
        return vaiTroRepository.findById(id).get();
    }


}
