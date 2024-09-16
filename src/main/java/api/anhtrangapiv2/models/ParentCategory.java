package api.anhtrangapiv2.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
@Table(name="parent_category")
public class ParentCategory extends BaseEntity{

    @Column(unique=true)
    private String name;

    @OneToMany(mappedBy="parentCategory",cascade= CascadeType.ALL)
    @JsonIgnore
    private List<Product> products;

    @OneToMany(mappedBy="parentCategory",cascade= CascadeType.ALL,fetch = FetchType.LAZY)
    private List<ChildrenCategory> childrenCategories;
}
