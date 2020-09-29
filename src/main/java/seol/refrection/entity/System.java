package seol.refrection.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Data @Builder
@NoArgsConstructor @AllArgsConstructor
@Entity @Table(name = "system")
@DynamicUpdate
public class System {
	@Id @Column
	private String systemId;
}
