package com.variantcapptool.service.dto;

public class PartDTO
{
	private Double	dimensionalCharacteristic;
	private String	dimensionalCharacteristicsType;
	private String	externalShape;
	private String	externalShapeType;
	private String	internalShape;
	private String	internalShapeType;
	private Double	mass;
	private Double	noOfHoles;

	public Double getDimensionalCharacteristic()
	{
		return dimensionalCharacteristic;
	}

	public String getDimensionalCharacteristicsType()
	{
		return dimensionalCharacteristicsType;
	}

	public String getExternalShape()
	{
		return externalShape;
	}

	public String getExternalShapeType()
	{
		return externalShapeType;
	}

	public String getInternalShape()
	{
		return internalShape;
	}

	public String getInternalShapeType()
	{
		return internalShapeType;
	}

	public Double getMass()
	{
		return mass;
	}

	public Double getNoOfHoles()
	{
		return noOfHoles;
	}

	public void setDimensionalCharacteristic(Double dimensionalCharacteristic)
	{
		this.dimensionalCharacteristic = dimensionalCharacteristic;
	}

	public void setDimensionalCharacteristicsType(String dimensionalCharacteristicsType)
	{
		this.dimensionalCharacteristicsType = dimensionalCharacteristicsType;
	}

	public void setExternalShape(String externalShape)
	{
		this.externalShape = externalShape;
	}

	public void setExternalShapeType(String externalShapeType)
	{
		this.externalShapeType = externalShapeType;
	}

	public void setInternalShape(String internalShape)
	{
		this.internalShape = internalShape;
	}

	public void setInternalShapeType(String internalShapeType)
	{
		this.internalShapeType = internalShapeType;
	}

	public void setMass(Double mass)
	{
		this.mass = mass;
	}

	public void setNoOfHoles(Double noOfHoles)
	{
		this.noOfHoles = noOfHoles;
	}

}
