package vmo.com.lesson_1.service;

import vmo.com.lesson_1.dto.ProvinceDTO;
import vmo.com.lesson_1.model.Province;

import java.util.List;
import java.util.Optional;

public interface ProvinceService {

    List<Province> findAllProvince();

    Province findProvinceById(Long id);

    Province createProvince(ProvinceDTO provinceDTO);

    Province updateProvince(ProvinceDTO provinceDTO, Long id);

    void deleteProvince(Long id);

    List<Province> createListProvince(List<ProvinceDTO> provinceDTOList);

}
