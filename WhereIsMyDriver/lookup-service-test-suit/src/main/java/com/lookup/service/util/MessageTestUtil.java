package com.lookup.service.util;

import com.lookup.service.exception.ErrorMessage;

public class MessageTestUtil {
	
	public boolean checkIfErrorMessageEquals(ErrorMessage errorMsg, String latErrorMessageFromDw) {

        return errorMsg.equals(latErrorMessageFromDw);
    }

}
