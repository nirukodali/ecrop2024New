package com.ecrops.entity;

import java.math.BigDecimal; 
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="supercheck_status_")
public class DistwiseSupChk {
            @Id
			private BigDecimal cr_dist_code;
			private String dname;
			private Long dho_allotted;
			private Long ho_allotted;
			public BigDecimal getCr_dist_code() {
				return cr_dist_code;
			}
			public void setCr_dist_code(BigDecimal cr_dist_code) {
				this.cr_dist_code = cr_dist_code;
			}
			public String getDname() {
				return dname;
			}
			public void setDname(String dname) {
				this.dname = dname;
			}
			public Long getDho_allotted() {
				return dho_allotted;
			}
			public void setDho_allotted(Long dho_allotted) {
				this.dho_allotted = dho_allotted;
			}
			public Long getHo_allotted() {
				return ho_allotted;
			}
			public void setHo_allotted(Long ho_allotted) {
				this.ho_allotted = ho_allotted;
			}
			
}			