package api.anhtrangapiv2.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Collection extends BaseEntity{

    @Column(
        unique=true,
        nullable=false
    )
    private String name;
    private String img = "";
    
    @OneToMany(mappedBy="collection",cascade= CascadeType.ALL)
    @JsonIgnore
    private List<Product> products;
}
