package com.ecrops.partition;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ecrops.entity.MandalCorrectionSuperCheckEntity;
import com.ecrops.repo.SeasonCropBookedExtentRepo;
import com.ecrops.repo.SeasonCropBookedExtentRepo.DeletedCount;

@Repository
@Transactional
public class MandalCorrectionSuperCheckRepo {

	@Autowired
	private SeasonCropBookedExtentRepo cropBookedExtentRepo;
	
	@PersistenceContext
	private EntityManager entityManager;

	public List<MandalCorrectionSuperCheckEntity> getmandasuperchckredressed(String wbdcode, String cropyear) {

		String[] season = cropyear.split("@");
		String seasonType = season[0];
		Integer seasonYear = Integer.parseInt(season[1]);

		String tab1 = "supercheck_upd";
		String tab2 = "supercheck_rej_v";
		
		 if (seasonYear>=2023) {
	            tab1 = "ecrop" + seasonYear + "." + tab1;
	            tab2 = "ecrop" + seasonYear + "." + tab2;
	        }
		System.out.println("tab1---------------->" + tab1);
		System.out.println("tab2---------------->" + tab2);


		String Sql = "select coalesce(dist_code,0) as dist_code,coalesce(mand_code, 0) as mand_code,wbmname,y.totsupchecks,farmername,crop,variety,extent,cropseq,src_irr,cr_pattern,sow_date,farming_type,\r\n"
				+ "directly_forwarded,totbookings,maoapproved,maorejected,maoprocessed,tahapproved,tahrejected,\r\n"
				+ "tahprocessed,tot_corrections from  \r\n"
				+ "(select directly_forwarded,dist_code,distname,dname,mand_code,mandname,farming_type,crop,variety,farmername,extent,src_irr,cr_pattern,sow_date,cropseq,tot_bookings  \r\n"
				+ "as totbookings,maoprocessed,tahprocessed,maoapproved,maorejected,tahapproved,tahrejected,\r\n"
				+ "farming_type+crop+variety+farmername+extent+src_irr+cr_pattern+sow_date+cropseq as tot_corrections   from\r\n"
				+ "(select  b.cropyear,b.season,b.cr_dist_code as Dist_Code,b.cr_mand_code as mand_code, w.wbdname as distname,w.wbmname as mandname,d.dname as dname,  \r\n"
				+ "count(b.bookingid) filter(where  cropschtype_sug is not null and del_status is null  ) as farming_type, count(b.bookingid) filter (where  cr_crop_sug <>0 and del_status is null  ) \r\n"
				+ "as crop, count(b.bookingid) filter(where  variety_sug <> 0 and del_status is null ) as variety, count(b.bookingid) filter(where  farmername_sug is not null and del_status is null ) \r\n"
				+ "as farmername, count(b.bookingid) filter(where  extent_sug <> 0 and del_status is null ) as extent, count(b.bookingid) filter(where  cr_w_draw_sug <>0 and del_status is null ) \r\n"
				+ "as src_irr, count(b.bookingid) filter(where  cr_sow_type_sug <> 0 and del_status is null ) as cr_pattern, count(b.bookingid)  \r\n"
				+ "filter(where  cr_sow_date_sug is not null and del_status is null ) as sow_date, count(b.bookingid) filter(where cr_no_sug is not null and del_status is null ) as cropseq,  \r\n"
				+ "count( b.bookingid)   as tot_bookings  ,count( b.bookingid) FILTER (where (mao_remarks='A' or mao_remarks='D')  )  \r\n"
				+ "as maoprocessed,count( b.bookingid) FILTER (where mao_remarks='A'   ) as maoapproved,count( b.bookingid) FILTER \r\n"
				+ "(where (mao_remarks='D' and del_status is null) or (mao_remarks is null  and mro_remarks='D'))  as maorejected,count( b.bookingid) FILTER (where (mro_remarks='A' or mro_remarks='D')   ) \r\n"
				+ "as tahprocessed, count( b.bookingid) FILTER (where mro_remarks='A' ) as tahapproved,count( b.bookingid)  \r\n"
				+ "FILTER (where mro_remarks='D'  and del_status is null ) as tahrejected,count( bookingid)  FILTER ( where  cropschtype_sug is null and cr_crop_sug=0 and variety_sug=0  \r\n"
				+ "and farmername_sug is null and fathername_sug is null and uid_sug='0' and extent_sug=0 and cr_w_draw_sug=0 and cr_sow_type_sug=0 and cr_no_sug is null  and del_status is null )\r\n"
				+ "as directly_forwarded  from "+tab1+" b , (select wbdcode, wbmcode, wbvcode, wbdname,wbmname from  \r\n"
				+ "wbvillage_mst where dcode <>999  group by wbdcode, wbmcode, wbvcode, wbdname,wbmname order by wbdcode) w, district_2011_cs d \r\n"
				+ "where w.wbvcode=b.cr_vcode and w.wbdcode=d.wbdcode  and cropyear="+seasonYear+" and season='"+seasonType+"' and b.cr_dist_code="+wbdcode+"\r\n"
				+ "group by b.cr_dist_code,b.cr_mand_code, w.wbdname,d.dname,mandname, \r\n"
				+ "b.cropyear,b.season order by distname,mandname) r1 ) x right join (select wbdcode,wbmcode,wbmname,wbdname, SUM(totsupchecks) as totsupchecks from "+tab2+"\r\n"
				+ "where cr_year="+seasonYear+" and cR_season='"+seasonType+"' and wbdcode="+wbdcode+"  group by wbdcode,wbmcode,wbmname,wbdname) y on x.dist_code=y.wbdcode and x.mand_code=y.wbmcode ORDER BY wbmname\r\n"
				+ "";

		Query insertQuery = (Query) entityManager.createNativeQuery(Sql);

		List<Object[]> detailsEntities1 = insertQuery.getResultList();

		System.out.println("detailsEntities1=>" + detailsEntities1.size());
		System.out.println("detailsEntities1=>" + detailsEntities1.toString());

		List<MandalCorrectionSuperCheckEntity> detailsEntities = new ArrayList<MandalCorrectionSuperCheckEntity>();

		try {
			for (Object[] row : detailsEntities1) {
				MandalCorrectionSuperCheckEntity entity = new MandalCorrectionSuperCheckEntity();

				entity.setDist_code((Integer)row[0]);
				entity.setMand_code((Integer)row[1]);
				entity.setWbmname((String) row[2]);
				entity.setTotsupchecks((BigDecimal) row[3]);
				entity.setFarmername((BigInteger) row[4]);
				entity.setCrop((BigInteger) row[5]);
				entity.setVariety((BigInteger) row[6]);
				entity.setExtent((BigInteger) row[7]);
				entity.setCropseq((BigInteger) row[8]);
				entity.setSrc_irr((BigInteger) row[9]);
				entity.setCr_pattern((BigInteger) row[10]);
				entity.setSow_date((BigInteger) row[11]);
				entity.setFarming_type((BigInteger) row[12]);
				entity.setDirectly_forwarded((BigInteger) row[13]);
				entity.setTotbookings((BigInteger) row[14]);
				entity.setMaoapproved((BigInteger) row[15]);
				entity.setMaorejected((BigInteger) row[16]);
				entity.setMaoprocessed((BigInteger) row[17]);
				entity.setTahapproved((BigInteger) row[18]);
				entity.setTahrejected((BigInteger) row[19]);
				entity.setTahprocessed((BigInteger) row[20]);
				entity.setTot_corrections((BigInteger )row[21]);

				detailsEntities.add(entity);
			}
			System.out.println("detailsEntities===========>" + detailsEntities.size());
			
			for(MandalCorrectionSuperCheckEntity bean:detailsEntities) {
				
				System.out.println(bean.getDist_code()+"---------"+bean.getMand_code());
				DeletedCount count = cropBookedExtentRepo.getDeleteCount(bean.getDist_code(),bean.getMand_code());
				System.out.println(bean.getMand_code()+"count--------->"+count.getDeletedCnt());
				bean.setDeletedCount(count.getDeletedCnt());
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("erroe==> " + e);
		}
		return detailsEntities;
	}
}