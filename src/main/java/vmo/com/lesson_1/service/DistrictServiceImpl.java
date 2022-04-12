package vmo.com.lesson_1.service;

import com.github.slugify.Slugify;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import vmo.com.lesson_1.dto.DistrictDTO;
import vmo.com.lesson_1.exception.InternalServerException;
import vmo.com.lesson_1.exception.NotFoundException;
import vmo.com.lesson_1.model.District;
import vmo.com.lesson_1.model.Province;
import vmo.com.lesson_1.repository.DistrictRepository;
import vmo.com.lesson_1.repository.ProvinceRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DistrictServiceImpl implements DistrictService{

    @Autowired
    private DistrictRepository districtRepository;

    @Autowired
    private ProvinceRepository provinceRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private Slugify slg;

    @Override
    public List<District> findAllDistrict() {
        return districtRepository.findAll();
    }

    @Override
    public District findDistrictById(Long id) {
        Optional<District> district = districtRepository.findById(id);
        checkDistrict(district);
        return district.get();
    }

    @Override
    public District createDistrict(Long provinceId, DistrictDTO districtDTO) {
        District district = mapper.map(districtDTO, District.class);

        Optional<Province> province = provinceRepository.findById(provinceId);
        if(province.isEmpty()) {
            throw new InternalServerException("Province does not exist. Can't create district");
        }
        setDistrict(district, province.get());

        return districtRepository.save(district);
    }

    @Override
    public District updateDistrict(DistrictDTO districtDTO, Long id) {
        Optional<District> district = districtRepository.findById(id);
        checkDistrict(district);
        Optional<Province> province = provinceRepository.findById(districtDTO.getProvinceId());
        try {
            mapper.map(districtDTO, district.get());
            setDistrict(district.get(), province.get());
            return districtRepository.save(district.get());
        } catch (Exception e) {
            throw new InternalServerException("Database error. Can't update district");
        }
    }

    @Override
    public void deleteDistrict(Long id) {
        Optional<District> district = districtRepository.findById(id);
        checkDistrict(district);
        try {
            districtRepository.deleteById(id);
        } catch (Exception e) {
            throw new InternalServerException("Database error. Can't delete district");
        }
    }

    @Override
    public List<District> findDistrictByProvinceId(Long provinceId) {
        List<District> districts = new ArrayList<>();
        for (District district : findAllDistrict()) {
            if(district.getProvince().getProvinceId().longValue() == provinceId.longValue()) {
                districts.add(district);
            }
        }
        return districts;
    }

    @Override
    public List<District> createListDistrict(Long provinceId, List<DistrictDTO> districtDTOList) {
        List<District> districts = new ArrayList<>();

        Optional<Province> province = provinceRepository.findById(provinceId);
        if(province.isEmpty()) {
            throw new InternalServerException("Province does not exist. Can't create district");
        }
        for(DistrictDTO districtDTO : districtDTOList) {
            District district = mapper.map(districtDTO, District.class);
            setDistrict(district, province.get());
            district.setProvince(province.get());
            districts.add(district);
        }
        return districtRepository.saveAll(districts);
    }

    protected void setDistrict(District district, Province province) {
        district.setSlug(slg.slugify(district.getName()));
        switch (district.getType()) {
            case "huyen":
                district.setNameWithType("Huyện " + district.getName());
                break;
            case "quan":
                district.setNameWithType("Quận " + district.getName());
                break;
            case "thi-xa":
                district.setNameWithType("Thị xã " + district.getName());
                break;
            default:
                district.setNameWithType("Thành phố " + district.getName());
                break;
        }
        district.setPath(district.getName() + ", " + province.getName());
        district.setPathWithType(district.getNameWithType() + ", " + province.getNameWithType());
        district.setParentCode(province.getCode());
        district.setProvince(province);
    }

    private void checkDistrict(Optional<District> province) {
        if(province.isEmpty()) {
            throw new NotFoundException("Not Found District");
        }
    }
}
