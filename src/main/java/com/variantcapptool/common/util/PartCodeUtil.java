package com.variantcapptool.common.util;

import com.variantcapptool.common.components.CodingTableConfigation;
import com.variantcapptool.service.dto.PartDTO;

public class PartCodeUtil
{
	public static String getPartCode(PartDTO partDTO)
	{
		StringBuffer partCode = new StringBuffer();
		partCode.append(getFirstDigit(partDTO.getExternalShape()));
		partCode.append(getSecondDigit(partDTO.getInternalShape()));
		partCode.append(
				getThirdDigit(partDTO.getDimensionalCharacteristicsType(), partDTO.getDimensionalCharacteristic()));
		partCode.append(getFourthDigit(partDTO.getNoOfHoles()));
		partCode.append(getFifthDigit(partDTO.getMass()));
		return partCode.toString();
	}

	private static String getFifthDigit(Double mass)
	{
		if (mass <= 5)
		{
			return "0";
		}
		if (mass > 5 && mass <= 10)
		{
			return "1";
		}
		if (mass > 10 && mass <= 15)
		{
			return "2";
		}
		if (mass > 15 && mass <= 20)
		{
			return "3";
		}
		if (mass > 20 && mass <= 25)
		{
			return "4";
		}
		if (mass > 25 && mass <= 30)
		{
			return "5";
		}
		if (mass > 30 && mass <= 35)
		{
			return "6";
		}
		if (mass > 35 && mass <= 40)
		{
			return "7";
		}
		if (mass > 40 && mass <= 50)
		{
			return "8";
		}
		if (mass > 50)
		{
			return "9";
		}
		return null;
	}

	private static String getFourthDigit(Double noOfHoles)
	{
		if (noOfHoles <= 5)
		{
			return "0";
		}
		if (noOfHoles > 5 && noOfHoles <= 10)
		{
			return "1";
		}
		if (noOfHoles > 10 && noOfHoles <= 15)
		{
			return "2";
		}
		if (noOfHoles > 15 && noOfHoles <= 20)
		{
			return "3";
		}
		if (noOfHoles > 20 && noOfHoles <= 25)
		{
			return "4";
		}
		if (noOfHoles > 25 && noOfHoles <= 30)
		{
			return "5";
		}
		if (noOfHoles > 30 && noOfHoles <= 35)
		{
			return "6";
		}
		if (noOfHoles > 35 && noOfHoles <= 40)
		{
			return "7";
		}
		if (noOfHoles > 40 && noOfHoles <= 50)
		{
			return "8";
		}
		if (noOfHoles > 50)
		{
			return "9";
		}
		return null;
	}

	private static String getThirdDigit(String dimensionalCharacteristicsType, Double dimensionalCharacteristic)
	{
		String code = null;
		switch (dimensionalCharacteristicsType)
		{
			case "DIM_ROTAOTIONAL_PARTS":
				code = getForRotationalParts(dimensionalCharacteristic);
				break;
			case "DIM_STEPPED_ON_BOTH_END":
				code = getForSteppedOnBothEnd(dimensionalCharacteristic);
				break;
			default:
				System.out.println("Wrong Choice");
				break;
		}
		return code;
	}

	private static String getForSteppedOnBothEnd(Double dimensionalCharacteristic)
	{
		if (dimensionalCharacteristic <= 2)
		{
			return "5";
		}
		if (dimensionalCharacteristic > 2 && dimensionalCharacteristic <= 5)
		{
			return "6";
		}
		if (dimensionalCharacteristic > 5 && dimensionalCharacteristic <= 7)
		{
			return "7";
		}
		if (dimensionalCharacteristic > 7 && dimensionalCharacteristic <= 10)
		{
			return "8";
		}
		if (dimensionalCharacteristic > 10)
		{
			return "9";
		}
		return null;
	}

	private static String getForRotationalParts(Double dimensionalCharacteristic)
	{
		if (dimensionalCharacteristic < 0.5)
		{
			return "0";
		}
		if (dimensionalCharacteristic >= 0.5 && dimensionalCharacteristic < 1)
		{
			return "1";
		}
		if (dimensionalCharacteristic >= 1 && dimensionalCharacteristic < 2)
		{
			return "2";
		}
		if (dimensionalCharacteristic >= 2 && dimensionalCharacteristic < 3)
		{
			return "3";
		}
		if (dimensionalCharacteristic >= 3)
		{
			return "4";
		}
		return null;

	}

	private static String getSecondDigit(String internalShape)
	{
		return CodingTableConfigation.getCodeByKey(internalShape);
	}

	private static String getFirstDigit(String externalShape)
	{
		return CodingTableConfigation.getCodeByKey(externalShape);
	}
}
