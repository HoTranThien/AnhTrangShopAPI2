package api.anhtrangapiv2.models;

//import java.util.List;

//import jakarta.persistence.CascadeType;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
//import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Color extends BaseEntity{
    @Column(unique=true)
    private String name;
    private String code;

    @OneToMany(mappedBy = "color",cascade=CascadeType.ALL)
    @JsonIgnore
    private List<ProductColor> productColor;
}
