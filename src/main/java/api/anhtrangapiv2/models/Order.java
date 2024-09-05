package api.anhtrangapiv2.models;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="_order")
public class Order extends BaseEntity{
    @Enumerated(EnumType.STRING)
    private Status status = Status.NEW_ORDER;
    private String customer_name;
    private String customer_tel;
    private String customer_address;

    @Column(columnDefinition="TEXT")
    private String customer_note;

    @Column(columnDefinition="TEXT")
    private String note;

    private long total;

    @ManyToOne
    @JoinColumn(name="delivery_id")
    private Delivery delivery;

    @OneToMany(mappedBy = "order")
    List<ProductOrder> product;
}
