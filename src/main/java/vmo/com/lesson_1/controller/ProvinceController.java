package vmo.com.lesson_1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vmo.com.lesson_1.dto.ProvinceDTO;
import vmo.com.lesson_1.exception.NotFoundException;
import vmo.com.lesson_1.model.Province;
import vmo.com.lesson_1.service.DistrictService;
import vmo.com.lesson_1.service.ProvinceService;
import vmo.com.lesson_1.service.ProvinceServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProvinceController {

    @Autowired
    private ProvinceService provinceService;

    @Autowired
    private DistrictService districtService;


    @GetMapping("/provinces")
    public ResponseEntity<?> findAllProvince() {
        return ResponseEntity.ok().body(provinceService.findAllProvince());
    }



    @GetMapping("/provinces/{id}")
    public ResponseEntity<?> findProvinceById(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(provinceService.findProvinceById(id));
    }

    @PostMapping("/provinces")
    public ResponseEntity<?> createProvince(@RequestBody ProvinceDTO provinceDTO) {
        return ResponseEntity.ok().body(provinceService.createProvince(provinceDTO));
    }

    @PatchMapping("/provinces/{id}")
    public ResponseEntity<?> updateProvince(@RequestBody ProvinceDTO provinceDTO, @PathVariable Long id) {
        return ResponseEntity.ok().body(provinceService.updateProvince(provinceDTO, id));
    }

    @DeleteMapping("/provinces/{id}")
    public ResponseEntity<?> deleteProvince(@PathVariable Long id) {
        provinceService.deleteProvince(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/provinces/{id}/districts")
    public ResponseEntity<?> findDistrictByProvinceId(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(districtService.findDistrictByProvinceId(id));
    }

    @PostMapping("/province-collection")
    public ResponseEntity<?> createListProvince(@RequestBody List<ProvinceDTO> provinceDTOList) {
        return ResponseEntity.ok().body(provinceService.createListProvince(provinceDTOList));
    }
}
