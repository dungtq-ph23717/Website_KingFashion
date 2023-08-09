package com.example.demo.service;

import com.example.demo.entity.VaiTro;

import java.util.List;
import java.util.UUID;

public interface VaiTroService {
    List<VaiTro> getAll();

    VaiTro add(VaiTro vaiTro);

    VaiTro update(VaiTro vaiTro);

    VaiTro getOne(UUID id);



}
