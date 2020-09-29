package seol.refrection.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity @Table(name = "user")
public class User {
	@Id
	private String userId;

	private String userName;

	private Integer age;

	private String etc;
}
