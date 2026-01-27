package com.abbtech.service;

import com.abbtech.dto.ReqBrandDto;
import com.abbtech.dto.RespBrandDto;

import java.util.List;

public interface BrandService {

    List<RespBrandDto> getBrands();

    RespBrandDto getBrandById(int id);

    void addBrand(ReqBrandDto car);

    void deleteBrandById(int id);

    void updateBrand(int id, ReqBrandDto carDto);
}
