package com.ecrops.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="cropgroups")
public class CropgroupsFCWR {
	
		@Id
	    private int cropgrpid;
	    private String grpname;
	    private String grpnameeng; 

		public CropgroupsFCWR() {
			
		}
				
		public CropgroupsFCWR(int cropgrpid, String grpname,String grpnameeng) {
			super();
			this.cropgrpid = cropgrpid;
			this.grpname = grpname;
			this.grpnameeng = grpnameeng;
		}

		public int getCropgrpid() {
			return cropgrpid;
		}
		public void setCropgrpid(int cropgrpid) {
			this.cropgrpid = cropgrpid;
		}
		
		public String getGrpname() {
			return grpname;
		}
		public void setGrpname(String grpname) {
			this.grpname = grpname;
		}

		public String getGrpnameeng() {
			return grpnameeng;
		}

		public void setGrpnameeng(String grpnameeng) {
			this.grpnameeng = grpnameeng;
		}

		@Override
		public String toString() {
			return "CropgroupsFCWR [cropgrpid=" + cropgrpid + ", grpname=" + grpname + ", grpnameeng=" + grpnameeng + "]";
		}
		
	       
	}