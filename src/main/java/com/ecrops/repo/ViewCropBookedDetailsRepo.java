package com.ecrops.repo;

import java.sql.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.ecrops.entity.ViewCropBookedDetailsEntity;
import com.ecrops.entity.VroRejectReasonsEntity;

@Repository
public class ViewCropBookedDetailsRepo {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public List<ViewCropBookedDetailsEntity> viewCropBookedDetails(Integer vscode, Integer bookingId, String partitionName) {
		
		String selectQuery = "select  distinct kh_no,cr_sno,bookingid,cr_farmeruid,tot_extent,oc_name,oc_fname,cropname,varietyname,cr_mix_unmix_ext,"
				+ " irrmethoddesc,watersource,cropnature,cr_sow_date, farmingtype,cr_crop,variety,cr_dist_code,cr_mand_code,"
				+ "cr_year,cr_season,cr_no,dname,mname,vname,cropname,varietyname,cr_w_draw as wsrcid, cr_sow_type,cropseed_scheme,  "
				+ "cr_w_draw from " + partitionName + " a where cr_vcode = :vscode and "
				+ "(a.bookingid,a.cr_crop,a.cr_no,a.variety,a.cr_sow_date,cr_vcode)  not in (select bookingid,cr_crop,cr_no,variety,"
				+ "cr_sow_date,cr_vcode from ecrop2024.farmer_grievances where cropyear=2024 and season='R' and (mao_remarks='D' or mro_remarks='D')) and a.bookingid = :bookingId"; //  AND mro_flag='R') ";

        Query query = entityManager.createNativeQuery(selectQuery, ViewCropBookedDetailsEntity.class);
        query.setParameter("vscode", vscode);
        query.setParameter("bookingId", bookingId);
        return query.getResultList();
    }

}
