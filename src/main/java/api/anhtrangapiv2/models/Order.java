package api.anhtrangapiv2.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
    private String customerName;
    private String customerTel;
    private String customerAddress;

    @Column(columnDefinition="TEXT")
    private String customerNote;

    @Column(columnDefinition="TEXT")
    private String note;

    private long total;

    @ManyToOne
    @JoinColumn(name="delivery_id")
    @JsonIgnore
    private Delivery delivery;

    @OneToMany(mappedBy = "order")
    @JsonIgnore
    List<ProductOrder> productOrder;
}
