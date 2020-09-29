package seol.refrection.entity;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Data @Builder
@NoArgsConstructor @AllArgsConstructor
@Entity @Table(name = "product")
@DynamicUpdate
public class Product extends BaseEntity {
	@Id @Column
	private String productId;
}
