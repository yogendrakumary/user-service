package com.ct.user.utility;

import java.util.Random;
import java.util.stream.Collectors;

public class RandomPasswordGenerator {
	public static String generatePassword() {
	     String upperChar = new Random().ints(1, 65, 90).mapToObj(i -> String.valueOf((char)i)).collect(Collectors.joining());
	      String lowerChar = new Random().ints(4, 97, 122).mapToObj(i -> String.valueOf((char)i)).collect(Collectors.joining());
	      String specialChar = new Random().ints(1, 35, 38).mapToObj(i -> String.valueOf((char)i)).collect(Collectors.joining());
	      String numbericChar = new Random().ints(4, 48, 57).mapToObj(i -> String.valueOf((char)i)).collect(Collectors.joining());
	     String password = upperChar+lowerChar+specialChar+numbericChar;
			return password;
	   }
}
