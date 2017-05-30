package com.variantcapptool.common.components;

import java.util.HashMap;
import java.util.Map;

public class CodingTableConfigation
{
	private static Map<String, String> map = getMap();

	private static Map<String, String> getMap()
	{
		map = new HashMap<>();
		map.put("EX_NON_ROT_CIRCULAR_DISC_TYPE", "5");
		map.put("EX_NON_ROT_CYLINDRICAL", "6");
		map.put("EX_NON_ROT_RECTANGULAR", "7");
		map.put("EX_NON_ROT_TRIANGULAR", "8");
		map.put("EX_NON_ROT_IRREGULAR", "9");
		map.put("EX_ROT_CIRCULAR_DISC_TYPE", "0");
		map.put("EX_ROT_CYLINDRICAL", "1");
		map.put("EX_ROT_RECTANGULAR", "2");
		map.put("EX_ROT_TRIANGULAR", "3");
		map.put("EX_ROT_IRREGULAR", "4");
		map.put("INT_STEP_ONE_CIRCULAR_DISC_TYPE", "0");
		map.put("INT_STEP_ONE_CYLINDRICAL", "1");
		map.put("INT_STEP_ONE_RECTANGULAR", "2");
		map.put("INT_STEP_ONE_IRREGULAR", "3");
		map.put("INT_STEP_BOTH_CYLINDRICAL", "4");
		map.put("INT_STEP_BOTH_RECTANGULAR", "5");
		map.put("INT_STEP_BOTH_IRREGULAR", "6");
		map.put("INT_HOL_CYLINDRICAL", "7");
		map.put("INT_HOL_RECTANGULAR", "8");
		map.put("INT_HOL_IRREGULAR", "9");
		return map;
	}

	public static String getCodeByKey(String key)
	{
		return map.get(key);
	}

}
