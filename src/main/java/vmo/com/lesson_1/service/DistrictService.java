package vmo.com.lesson_1.service;

import org.springframework.stereotype.Service;
import vmo.com.lesson_1.dto.DistrictDTO;
import vmo.com.lesson_1.model.District;

import java.util.List;


public interface DistrictService {

    List<District> findAllDistrict();

    District findDistrictById(Long id);

    District createDistrict(Long provinceId, DistrictDTO districtDTO);

    District updateDistrict(DistrictDTO districtDTO, Long id);

    void deleteDistrict(Long id);

    List<District> findDistrictByProvinceId(Long provinceId);

    List<District> createListDistrict(Long provinceId, List<DistrictDTO> districtDTOList);

}
