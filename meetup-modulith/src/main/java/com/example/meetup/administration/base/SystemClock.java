package com.example.meetup.administration.base;

import java.time.LocalDateTime;

public class SystemClock {

	private static LocalDateTime customDate;

    public static LocalDateTime now() {
        
    	if (customDate != null)
        	return customDate;

  		return LocalDateTime.now();
   	}

    public static void set(LocalDateTime newCustomDate) {
    	customDate = newCustomDate;
    }

    public static void reset() {
    	customDate = null;
    }
}
