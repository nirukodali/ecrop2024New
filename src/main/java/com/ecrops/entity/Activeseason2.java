//package com.ecrops.entity;
//
//import javax.persistence.CascadeType;
//import javax.persistence.Entity;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.OneToOne;
//import javax.persistence.Table;
//
//@Entity
//@Table(name = "activeseason")
//public class Activeseason2 {
//
//			@Id
//			private String cropyear;
//			private String seasonvalue;
//			@OneToOne(cascade = CascadeType.ALL)
//		    @JoinColumn(name = "season")
//			private Season season;
//			
//			public Activeseason() {
//				
//			}
//
//			public Activeseason(String cropyear, String seasonvalue, Season season) {
//				super();
//				this.cropyear = cropyear;
//				this.seasonvalue = seasonvalue;
//				this.season = season;
//			}
//
//			public String getCropyear() {
//				return cropyear;
//			}
//			public void setCropyear(String cropyear) {
//				this.cropyear = cropyear;
//			}
//
//			public String getSeasonvalue() {
//				return seasonvalue;
//			}
//			public void setSeasonvalue(String seasonvalue) {
//				this.seasonvalue = seasonvalue;
//			}
//
//			public Season getSeason() {
//				return season;
//			}
//			public void setSeason(Season season) {
//				this.season = season;
//			}
//
//			@Override
//			public String toString() {
//				return "Activeseason [cropyear=" + cropyear + ", seasonvalue=" + seasonvalue + ", season=" + season
//						+ "]";
//			}
//
//}			
//			
//			
//			
//		    
