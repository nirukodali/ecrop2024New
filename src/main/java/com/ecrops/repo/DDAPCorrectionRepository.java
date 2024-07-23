package com.ecrops.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ecrops.entity.DDAPCorrection;

public interface DDAPCorrectionRepository extends JpaRepository<DDAPCorrection, String> {
	@Query(value=" select dname,farmername,crop,variety,extent,cropseq,src_irr,cr_pattern,sow_date,farming_type,deleted,\r\n"
			+ "farming_type+crop+variety+farmername+extent+src_irr+cr_pattern+sow_date+cropseq+deleted as tot_corrections,\r\n"
			+ "tot_bookings  as totbookings,maoapproved,maorejected,maoprocessed,tahapproved,tahrejected,tahprocessed\r\n"
			+ "from (select b.cr_dist_code as Dist_Code, w.wbdname as distname,d.dname as dname, count(b.bookingid) \r\n"
			+ "filter(where  cropschtype_sug is not null)  as farming_type,\r\n"
			+ "count(b.bookingid) filter(where  cr_crop_sug <>0 ) as crop, count(b.bookingid) filter(where  variety_sug <> 0) as variety,\r\n"
			+ "count(b.bookingid) filter(where  farmername_sug is not null) as farmername, count(b.bookingid) filter(where  extent_sug <> 0) as extent,\r\n"
			+ "count(b.bookingid) filter(where  cr_w_draw_sug <>0 ) as src_irr, count(b.bookingid) filter(where  cr_sow_type_sug <> 0) as cr_pattern,\r\n"
			+ "count(b.bookingid) filter(where  cr_sow_date_sug is not null) as sow_date, count(b.bookingid) filter(where cr_no_sug is not null) as cropseq,\r\n"
			+ "count(b.bookingid)  filter(where validbooking='Y' and supercheckavl is null) as tot_bookings  ,count(bookingid) FILTER (where (mao_remarks='A' \r\n"
			+ "or mao_remarks='D') and validbooking='Y' and supercheckavl is null) as maoprocessed,count(bookingid) FILTER (where mao_remarks='A' \r\n"
			+ "and validbooking='Y' and supercheckavl is null) as maoapproved,\r\n"
			+ "count(bookingid) FILTER (where mao_remarks='D'  and validbooking='Y' and supercheckavl is null) as maorejected,count(bookingid) \r\n"
			+ "FILTER (where (mro_remarks='A' or mro_remarks='D') and validbooking='Y' and supercheckavl is null) as tahprocessed,count(bookingid) \r\n"
			+ "FILTER (where mro_remarks='A' and validbooking='Y' and supercheckavl is null) as tahapproved,count(bookingid) FILTER (where mro_remarks='D' \r\n"
			+ "and validbooking='Y' and supercheckavl is null) as tahrejected\r\n"
			+ "from farmer_grievances b, (select wbdcode, wbmcode, wbvcode, wbdname from wbvillage_mst where dcode <>999 \r\n"
			+ "group by wbdcode, wbmcode, wbvcode, wbdname order by wbdcode) w, district_2011_cs d where w.wbvcode=b.cr_vcode and w.wbdcode=d.wbdcode \r\n"
			+ "group by b.cr_dist_code, w.wbdname,d.dname) r1, (select cr_dist_code,wbdname,deleted from cr_det_delete_mv) r2 \r\n"
			+ "where r1.dist_code=r2.cr_dist_code ",nativeQuery=true)
	List<ddapcorrectionview> getlist();
	
	interface ddapcorrectionview {
		String getDname();
		Long getFarmername();
		Long getCrop();
		Long getVariety();
		Long getExtent();
		Long getCropseq();
		Long getSrc_irr();
		Long getCr_pattern();
		Long getSow_date();
		Long getFarming_type();
		Long getVaadeleted();
		Long getTot_corrections();
		Long getTotbookings();
		Long getMaoapproved();
		Long getMaorejected();
		Long getMaoprocessed();
		Long getTahapproved();
		Long getTahrejected();
		Long getTahprocessed();
	}

}
