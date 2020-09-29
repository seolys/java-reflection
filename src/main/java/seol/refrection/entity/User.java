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
@Entity @Table(name = "user")
@DynamicUpdate
public class User {
	@Id @Column
	private String userId;
	@Column
	private String userName;
	@Column
	private Integer age;
	@Column
	private String etc;
	@Column @Enumerated(EnumType.STRING)
	private UserStatus userStatus;
	@Column
	private BigDecimal haveMoney;
}
