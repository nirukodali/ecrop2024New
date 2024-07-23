package com.ecrops.validations;



public class AllocationValidations {

	
	 public  boolean isDigitPattern(String input) {
		  String pattern = "^[0-9]+$";
       return input != null && input.matches(pattern) && !input.isEmpty();
	 }
  
	 public   boolean onlyAlphaAndNumeric(String input) {
      String pattern = "^[A-za-z0-9]+$";
    return input != null && input.matches(pattern) && !input.isEmpty();
}
	 
	 public   boolean alphaNumericSymbols(String input) {
	      String pattern = "^[A-za-z0-9@]+$";
	   return input != null && input.matches(pattern) && !input.isEmpty();
	}
	 
	 public   boolean cropYear(String input) {
	      String pattern = "^[A-za-z0-9@]+$";
	  return input != null && input.matches(pattern) && !input.isEmpty();
	}
	 public  boolean oneAlpha(String input) {
	      String pattern = "[A-za-z]";
	     return input != null && input.matches(pattern) && !input.isEmpty();
	}

  
  public  boolean villageCode(String input) {
      return input != null && input.matches( "^\\d{6,7}$") && !input.isEmpty();
  }
  
  public  boolean isDigitsOnly(String input) {
      return input != null && input.matches( "^\\d$") && !input.isEmpty();
  }
  
  
  
  

  
}
