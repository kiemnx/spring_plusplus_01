package vn.plusplus.spring.springbootdemo.repository.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter @NoArgsConstructor
@Table(name = "api")
public class ApiEntity {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "name")
	private String name;
	
	@Column(name = "http_method")
	private String httpMethod;

	@Column(name = "pattern")
	private String pattern;
	
	@Column(name = "is_required_access_token")
	private Boolean isRequiredAccessToken;
	
	@Column(name="should_check_permission")
	private Boolean shouldCheckPermission;
	
	public Boolean getIsRequiredAccessToken() {
		return isRequiredAccessToken;
	}

	public void setIsRequiredAccessToken(Boolean requiredAccessToken) {
		isRequiredAccessToken = requiredAccessToken;
	}
}
