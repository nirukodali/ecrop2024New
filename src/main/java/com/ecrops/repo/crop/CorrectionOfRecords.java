package com.ecrops.repo.crop;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecrops.dto.crop.response.CropIrrgMethod_Master;
import com.ecrops.dto.crop.response.CropNamesData;
import com.ecrops.dto.crop.response.CropSeed_Schema;
import com.ecrops.dto.crop.response.CropYearCrbk;
import com.ecrops.dto.crop.response.HomandalsnewView;
import com.ecrops.dto.crop.response.RejReasonData;
import com.ecrops.dto.crop.response.WaterResources;
//import com.ecrops.dto.crop.response.WbmcodeIntf;
import com.ecrops.dto.crop.response.cr_variety_master;
//import com.ecrops.entity.crop.VillageSec;
import com.ecrops.entity.VillageSec;

public interface CorrectionOfRecords extends JpaRepository<VillageSec, Integer> {

    @Query(value = "select distinct on (a.cropyear, a.season) concat(a.season, '@', cropyear) as seasonvalue, " +
            "concat(b.seasonname, ' ', cropyear) as cropyear \n" +
            "from activeseason a, season b  where a.season = b.season and a.active = 'A' and current_season = 'C' \n" +
            "order by a.cropyear, a.season", nativeQuery = true)
    List<CropYearCrbk> getCropYear();
 
    @Query(value =  "select vcode, wbvname from public.villsec_rev_v where vscode= :vscode", nativeQuery = true)
    List<VillageSec> getVillages(@Param("vscode") int vscode);
    
    @Query(value =  "select  mcode,mname from homandalsnew_v where hqcode= :hqcode order by mname", nativeQuery = true)
    List<HomandalsnewView> getMcodeDcodefromhomandalsnew_v(@Param("hqcode") int hqcode);
    
    
    @Query(value =  "select distinct wbmcode from wbvillage_mst  where mcode= :mcode and wbdcode!=88", nativeQuery = true)
    	int getwbmcode(@Param("mcode") int mcode);
       
    @Query(value="select distinct code,reason from public.vro_rej_reasons where active='A' and code in (1,2,5,6)" ,nativeQuery=true)
	public List<RejReasonData> findRejReasons();
    
	@Query(value="select varietycode,varietyname from cr_variety_master where status='A' and cropcode= :cropcode",nativeQuery=true)
	public List<cr_variety_master> findvarietycodevarietyname(@Param("cropcode") int cropcode);//VarityName

    
    @Query(value = "SELECT cropid, cropname FROM cropnames WHERE (active='A' AND cropnature IN ('A', 'H', 'S')) OR cropid=888", nativeQuery = true)
    public List<CropNamesData> findCropidAndCropName();//cropName
    
    @Query(value = "select wsrcid,wsrcdesc from waterresources  where active='A' or wsrcid=888 ",nativeQuery = true )
    public List<WaterResources> findwsrcidAndwsrcdesc(); //WaterSource

    @Query(value = "select irgcode,irgdesc from cropirrgmethod_master  where active='A'",nativeQuery = true)
    public List<CropIrrgMethod_Master> findirgcodeAndirgdesc();  

    @Query(value = "SELECT cropschtype,cropschdesc from cropseed_scheme where active='A' " ,nativeQuery = true)
    public List<CropSeed_Schema> findcropschtypeAndcropschdesc();
    
    @Query(value = "select cropid, cropname from cropnames where cropid= :cropid ", nativeQuery = true)
    CropNamesData getCropname(@Param("cropid") int cropid);
    
    @Query(value = "SELECT varietyname FROM cr_variety_master WHERE  varietycode= :varietycode ", nativeQuery = true) 
    String getVarietyname(@Param("varietycode") int varietycode);
    
    @Query(value = "select wsrcdesc from waterresources where  wsrcid= :wsrcid and active='A' ", nativeQuery = true)
    String getwsrcdesc(@Param("wsrcid") int wsrcid);
    
}



