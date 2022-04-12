package vmo.com.lesson_1.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class District {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long districtId;

    @Nationalized
    private String name;

    @Nationalized
    private String type;

    private String slug;

    @Nationalized
    private String nameWithType;

    @Nationalized
    private String path;

    @Nationalized
    private String pathWithType;

    private Long code;

    private Long parentCode;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "province_id")
    private Province province;
}
