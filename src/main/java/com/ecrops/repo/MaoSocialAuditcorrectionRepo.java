package com.ecrops.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecrops.entity.MaoSocialAuditcorrection;


public interface MaoSocialAuditcorrectionRepo extends JpaRepository<MaoSocialAuditcorrection, String> {
	@Query(value="select wbvname,farmername,crop,variety,extent,src_irr,cr_pattern,sow_date,farming_type,vaadeleted ,tot_corrections,totbookings,\r\n"
			+ "maoapproved,maorejected,maoprocessed,tahrejected,tahapproved,tahprocessed, \r\n"
			+ "lgdvname,coalesce(y.totsupchecks,0)as totalcheck,cropseq,x.cr_vcode from (select vaadeleted,cr_vcode, wbvname,lgdvname,farming_type,crop,variety,farmername,extent,\r\n"
			+ "src_irr,cr_pattern,sow_date,cropseq,tot_bookings  as totbookings,maoprocessed,tahprocessed,maoapproved,maorejected,tahapproved,\r\n"
			+ "tahrejected,farming_type+crop+variety+farmername+extent+src_irr+cr_pattern+sow_date+cropseq as tot_corrections \r\n"
			+ "from (select  b.cr_vcode, w.wbvname,w.lgdvname,  count(b.bookingid) filter(where  cropschtype_sug is not null ) as farming_type,  \r\n"
			+ "count(b.bookingid) filter(where  cr_crop_sug <>0 ) as crop, count(b.bookingid) filter(where  variety_sug <> 0) as variety, \r\n"
			+ "count(b.bookingid) filter(where  farmername_sug is not null) as farmername, count(b.bookingid) filter(where  extent_sug <> 0) \r\n"
			+ "as extent,  count(b.bookingid) filter(where  cr_w_draw_sug <>0 ) as src_irr, count(b.bookingid) filter(where  cr_sow_type_sug <> 0) \r\n"
			+ "as cr_pattern,  count(b.bookingid) filter(where  cr_sow_date_sug is not null) as sow_date, count(b.bookingid) filter(where cr_no_sug \r\n"
			+ "is not null) as cropseq,  count(distinct b.bookingid) as tot_bookings,count(distinct b.bookingid) FILTER (where (mao_remarks='A' \r\n"
			+ "or mao_remarks='D')) as maoprocessed,  count(distinct b.bookingid) FILTER (where mao_remarks='A') as maoapproved,\r\n"
			+ "	  count(distinct b.bookingid) FILTER (where soc_status='Y') as vaadeleted, count(distinct b.bookingid) FILTER (where mao_remarks='D') \r\n"
			+ "	  as maorejected,count(distinct b.bookingid) FILTER (where (mro_remarks='A' or mro_remarks='D')   ) as tahprocessed, \r\n"
			+ "	  count(distinct b.bookingid) FILTER (where mro_remarks='A') as tahapproved,count(distinct b.bookingid) FILTER (where mro_remarks='D'  )\r\n"
			+ "	  as tahrejected from ecrop2023.farmer_grievances b ,(select distinct wbdcode, wbmcode, wbvcode,wbvname,lgdvname from wbvillage_mst \r\n"
			+ "where dcode=:dcode and mcode=:mcode) w where w.wbvcode=b.cr_vcode and cropyear=:cropyear and season=:season group by b.cr_vcode, w.wbvname,w.lgdvname \r\n"
			+ "	  order by wbvname) r1 ) x  left join (select cr_vcode, count(distinct cast(bookingid as varchar)||cast(cr_crop as varchar)||\r\n"
			+ "			 cast(variety as varchar)||cast(cr_sow_date as varchar))\r\n"
			+ "as totsupchecks from socialaudit_rej_details b where dcode=:dcode and mcode=:mcode and cr_year=:cropyear"
			+ " and cr_season=:season group by cr_vcode)\r\n"
			+ "y on x.cr_vcode=y.cr_vcode ",nativeQuery=true)
	List< MaoSocialAuditcorrection> getSocialAudit(@Param("dcode") Integer dcode, @Param("mcode") Integer mcode ,
			@Param("cropyear") Integer cropyear,@Param("season") String season);
	

}
