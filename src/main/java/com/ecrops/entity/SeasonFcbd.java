package com.ecrops.entity;

import javax.persistence.Entity; 
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="season")
public class SeasonFcbd {

			@Id
			private char season;
			private String seasonname;
			public char getSeason() {
				return season;
			}
			public void setSeason(char season) {
				this.season = season;
			}
			public String getSeasonname() {
				return seasonname;
			}
			public void setSeasonname(String seasonname) {
				this.seasonname = seasonname;
			}

			
}
		   