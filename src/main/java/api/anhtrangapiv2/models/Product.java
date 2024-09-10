package api.anhtrangapiv2.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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

public class Product extends BaseEntity{

    @Column(
        nullable=false
    )
    private String name;
    private long cost;
    private long sale_cost;

    @Column(columnDefinition = "TEXT")
    private String description;

    private int quantity;
    private boolean isnew;

    @ManyToOne
    @JoinColumn(name="collection_id")
    private Collection collection;

    @OneToMany(mappedBy="product",cascade= CascadeType.ALL)
    private List<ImgProduct> imgProduct;

    @ManyToOne
    @JoinColumn(name="parent_category_id")
    private ParentCategory parentCategory;

    @ManyToOne
    @JoinColumn(name="children_categorygory_id")
    private ChildrenCategory childrenCategory;

    @OneToMany(mappedBy="product",cascade= CascadeType.ALL)
    @JsonIgnore
    private List<ProductSize> productSize;

    @OneToMany(mappedBy="product",cascade= CascadeType.ALL)
    @JsonIgnore
    private List<ProductColor> productColor;

    @OneToMany(mappedBy = "product",cascade= CascadeType.ALL)
    @JsonIgnore
    List<ProductOrder> productOrder;
}
