package vmo.com.lesson_1.service;

import com.github.slugify.Slugify;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import vmo.com.lesson_1.dto.ProvinceDTO;
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
public class ProvinceServiceImpl implements ProvinceService{
    @Autowired
    private ProvinceRepository provinceRepository;

    @Autowired
    private DistrictServiceImpl districtService;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private Slugify slg;

    @Override
    public List<Province> findAllProvince() {
        return provinceRepository.findAll();
    }

//    @Override
//    public List<Province> findPageProvince(Integer page, Integer size) {
//        return provinceRepository.findAll(PageRequest.of(page, size));
//    }

    @Override
    public Province findProvinceById(Long id) {
        Optional<Province> province = provinceRepository.findById(id);
        checkProvince(province);
        return province.get();
    }

    @Override
    public Province createProvince(ProvinceDTO provinceDTO) {
        Province province = mapper.map(provinceDTO, Province.class);
        setProvince(province);
        return provinceRepository.save(province);
    }

    @Override
    public Province updateProvince(ProvinceDTO provinceDTO, Long id) {
        Optional<Province> province = provinceRepository.findById(id);
        checkProvince(province);
        try {
            mapper.map(provinceDTO, province.get());
            setProvince(province.get());
            for(District district : districtService.findAllDistrict()){
                if(province.get().getProvinceId().longValue() == district.getProvince().getProvinceId().longValue()){
                    districtService.setDistrict(district, province.get());
                }
            }
            return provinceRepository.save(province.get());
        } catch (Exception e) {
            throw new InternalServerException("Database error. Can't update province");
        }
    }

    @Override
    public void deleteProvince(Long id) {
        Optional<Province> province = provinceRepository.findById(id);
        checkProvince(province);
        try {
            provinceRepository.deleteById(id);
        } catch (Exception e) {
            throw new InternalServerException("Database error. Can't delete province");
        }
    }

    @Override
    public List<Province> createListProvince(List<ProvinceDTO> provinceDTOList) {
        List<Province> provinces = new ArrayList<>();
        for(ProvinceDTO provinceDTO : provinceDTOList) {
            Province province = mapper.map(provinceDTO, Province.class);
            setProvince(province);
            provinces.add(province);
        }
        return provinceRepository.saveAll(provinces);
    }

    private void setProvince(Province province) {
        province.setSlug(slg.slugify(province.getName()));
        if(province.getType().equals("tinh")) {
            province.setNameWithType("Tỉnh " + province.getName());
        } else {
            province.setNameWithType("Thành phố " + province.getName());
        }
    }

    private void checkProvince(Optional<Province> province) {
        if(province.isEmpty()) {
            throw new NotFoundException("Not Found Province");
        }
    }
}
