package vmo.com.lesson_1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vmo.com.lesson_1.dto.DistrictDTO;
import vmo.com.lesson_1.service.DistrictService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DistrictController {

    @Autowired
    private DistrictService districtService;

    @GetMapping("/districts")
    public ResponseEntity<?> getAllDistrict() {
        return ResponseEntity.ok().body(districtService.findAllDistrict());
    }

    @GetMapping("/districts/{id}")
    public ResponseEntity<?> getDistrictById(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(districtService.findDistrictById(id));
    }

    @PostMapping("/districts/{id}")
    public ResponseEntity<?> createDistrict(@PathVariable("id") Long provinceId, @RequestBody DistrictDTO districtDTO) {
        return ResponseEntity.ok().body(districtService.createDistrict(provinceId, districtDTO));
    }

    @PatchMapping("/districts/{id}")
    public ResponseEntity<?> updateDistrict(@RequestBody DistrictDTO districtDTO, @PathVariable Long id) {
        return ResponseEntity.ok().body(districtService.updateDistrict(districtDTO, id));
    }

    @DeleteMapping("/districts/{id}")
    public ResponseEntity<?> deleteDistrict(@PathVariable Long id) {
        districtService.deleteDistrict(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/district-collection/{id}")
    public ResponseEntity<?> createListDistrict(
            @PathVariable("id") Long provinceId,
            @RequestBody List<DistrictDTO> districtDTOList) {
        return ResponseEntity.ok().body(districtService.createListDistrict(provinceId, districtDTOList));
    }
}
