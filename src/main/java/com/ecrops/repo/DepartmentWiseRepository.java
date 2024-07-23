package com.ecrops.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ecrops.entity.DepartmentWise;

public interface DepartmentWiseRepository extends JpaRepository<DepartmentWise, String> {
	@Query(value = "select distinct dist.dname, a.dcode, a.cr_year, a.cr_season, a.normalarea as agri_normalarea, a.cultivable_land as agri_cultivable_land, \r\n"
			+ "h.hnormalarea as hnormalarea, h.cultivable_land as hcultivable_land, s.cultivable_land as seri_cultivable_land, sf.normalarea as \r\n"
			+ "soc_forestry_normalarea, sf.cultivable_land as soc_forestry_cultivable_land, f.cultivable_land as fodder_cultivable_land, \r\n"
			+ "dt.cr_dist_code, a_vaaauthextent, a_vroauthextent, a_ekycbookedext, h_vaaauthextent, h_vroauthextent, h_ekycbookedext,\r\n"
			+ "s_vaaauthextent, s_vroauthextent, s_ekycbookedext, r_vaaauthextent, r_vroauthextent, r_ekycbookedext \r\n"
			+ "from district_2011_cs dist, ecrop2023.cr_booking_dist_agri_v a, ecrop2023.cr_booking_dist_hort_v h, ecrop2023.cr_booking_dist_seri_v s,\r\n"
			+ "ecrop2023.cr_booking_dist_soc_forestry_v sf, ecrop2023.cr_booking_dist_fodder_v f, ecrop2023.cr_authdetails_dist_v_k2023, \r\n"
			+ "(SELECT cr_dist_code, dcode,\r\n"
			+ "sum(coalesce(a_vaaauthextent,0)) as a_vaaauthextent, sum(coalesce(a_vroauthextent,0)) as a_vroauthextent, sum(coalesce(a_ekycbookedext,0)) \r\n"
			+ "as a_ekycbookedext, sum(coalesce(h_vaaauthextent,0)) as h_vaaauthextent, sum(coalesce(h_vroauthextent,0)) as h_vroauthextent, \r\n"
			+ "sum(coalesce(h_ekycbookedext,0)) as h_ekycbookedext, sum(coalesce(s_vaaauthextent,0)) as s_vaaauthextent, sum(coalesce(s_vroauthextent,0)) \r\n"
			+ "as s_vroauthextent, sum(coalesce(s_ekycbookedext,0)) as s_ekycbookedext, sum(coalesce(r_vaaauthextent,0)) as r_vaaauthextent, \r\n"
			+ "sum(coalesce(r_vroauthextent,0)) as r_vroauthextent, sum(coalesce(r_ekycbookedext,0)) as r_ekycbookedext FROM \r\n"
			+ "ecrop2023.cr_authdetails_vill_dept_mv_k2023 group by cr_dist_code,dcode) dt where a.dcode = dist.dcode and a.dcode = h.dcode and a.dcode=s.dcode \r\n"
			+ "and a.dcode=sf.dcode and a.dcode=f.dcode and a.dcode=dt.dcode and a.cr_year=2023 and a.cr_season='K' and h.cr_year=2023 and h.cr_season='K' \r\n"
			+ "and s.cr_year=2023 and s.cr_season='K' and sf.cr_year=2023 and sf.cr_season='K' and dist.dcode!=999 and  f.cr_year=2023 and f.cr_season='K' \r\n"
			+ "and dt.cr_dist_code=dist.wbdcode", nativeQuery = true)
	List<DepartmentWiseProjections> getList();

}
