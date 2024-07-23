package com.ecrops.repo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.ecrops.entity.BookingSummaryExtent;
import com.ecrops.entity.FarmerBookingDetails;

@Repository
@Transactional
public class BookingSummaryExtPartitions {

	@PersistenceContext
	private EntityManager entityManager;

	public List<BookingSummaryExtent> bookingsum(String cropyear,int a) {
		String[] season = cropyear.split("@");
		String seasonType = season[0];
		int activeYear = a;

		Integer seasonYear = Integer.parseInt(season[1]);

		String part_key = "",tableName="";

		part_key = seasonType + seasonYear;
		if (seasonYear>=2023  && !(seasonYear == 2023 && seasonType.equalsIgnoreCase("S"))) {
        	tableName = "ecrop" + seasonYear + "." + "bookingsummary_mv_" + part_key;
        }
        else {
        	tableName = "bookingsummary_mv_" + part_key;
        }

		System.out.println("tableName---------------->" + tableName);

		String Sql = " select wbedname,cast(bookedextent as varchar),cast(vaaselectedext as  varchar),cast(mappedextent as varchar) \r\n"
				+ "from "+ tableName +" where wbdcode<>88 order by wbedname";

		Query insertQuery = (Query) entityManager.createNativeQuery(Sql);

		System.out.println("insertQuery=>" + insertQuery);
		List<Object[]> detailsEntities1 = insertQuery.getResultList();
		List<BookingSummaryExtent> detailsEntities = new ArrayList<BookingSummaryExtent>();

		for (Object[] row : detailsEntities1) {

			BookingSummaryExtent entity = new BookingSummaryExtent();
			entity.setWbedname((String) row[0]);
			entity.setBookedextent((String) row[1]);
			entity.setVaaselectedext((String) row[2]);
			entity.setMappedextent((String) row[3]);
			detailsEntities.add(entity);

		}

		return detailsEntities;

	}

}
