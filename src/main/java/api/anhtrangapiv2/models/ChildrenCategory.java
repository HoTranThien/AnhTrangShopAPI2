package api.anhtrangapiv2.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
@Table(name = "children_category")
public class ChildrenCategory extends BaseEntity{
    @Column(unique=true)
    private String name;

    @ManyToOne
    @JoinColumn(name="paca_id")
    private ParentCategory parentCategory;

    @OneToMany(mappedBy="childrenCategory",cascade= CascadeType.ALL)
    @JsonIgnore
    private List<Product> products;
}
