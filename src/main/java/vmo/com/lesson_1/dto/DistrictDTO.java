package vmo.com.lesson_1.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DistrictDTO {
    private String name;
    private String type;
    //    private String slug;
//    private String nameWithType;
//    private String path;
//    private String pathWithType;
    private Long code;
    //    private Long parentCode;
    private Long provinceId;
}
