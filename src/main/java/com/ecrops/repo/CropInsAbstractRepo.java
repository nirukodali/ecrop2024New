package com.ecrops.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecrops.entity.CropInsAbstract;

public interface CropInsAbstractRepo  extends JpaRepository<CropInsAbstract, String>{
	@Query(value="select district,mandal,rbkname,coalesce(yb_farmers,0) as yb_farmers,yb_extent,yb_claimamt,\r\n"
			+ "coalesce(wb_farmers,0) as wb_farmers,wb_extent,wb_claimamt,\r\n"
			+ "dcode,mcode,vcode from(select dcode,dname as district,mcode,mname \r\n"
			+ "as mandal,vcode,vname as rbkname,wb_farmers,wb_extent,wb_claimamt,yb_farmers,yb_extent,yb_claimamt from \r\n"
			+ "(select distinct dcode,mcode,vcode,dname,mname,vname from villsec_det_v) a left join (select district,mandal,rbkid,rbkname,\r\n"
			+ "sum(farmers) as wb_farmers,round(sum(extent),2) as wb_extent,round(sum(claimamt))as wb_claimamt from pmfby_wb_k2022_mv\r\n"
			+ "group by district,mandal,rbkid,rbkname) b on vcode=cast(substr(rbkid,5) as integer)  left join  (select \"Crop District Name\",\r\n"
			+ "\"level4Name\",rbkuserid,rbkname,sum(farmer_count) as yb_farmers,round(sum(area_insured*2.5),2) \r\n"
			+ "as yb_extent,round(sum(claim_amt)) as yb_claimamt from pmfby_yb_k2022_mv group by \"Crop District Name\",\"level4Name\",\r\n"
			+ "rbkuserid,rbkname) c on vcode= cast(substr(rbkuserid,5) as integer)) e where dcode=:dcode and  mandal not ilike 'kurnool%' and\r\n"
			+ "(wb_claimamt is not null or yb_claimamt is not null) order by district,mandal,rbkname  ",nativeQuery=true)
  List<CropInsAbstract>getCropIns(@Param("dcode") Integer dcode);

}
