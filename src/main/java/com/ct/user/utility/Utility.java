package com.ct.user.utility;

import org.apache.commons.lang.RandomStringUtils;

public class Utility {

	public static String generateOtp() {
		return RandomStringUtils.randomAlphanumeric(6).concat("@C1c");
	}

}
