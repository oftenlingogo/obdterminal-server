package com.casic.entity;

public class ObdTenSecondData
{
  private int mileageAfterBreak;
  private int mileageBeforeBreak;
  private int frontOxgenSensorVal;
  private int backOxgenSensorVal;
  private int airConditionerStatus;
  private int oilVal;

  public int getMileageAfterBreak()
  {
    return this.mileageAfterBreak;
  }

  public void setMileageAfterBreak(int mileageAfterBreak)
  {
    this.mileageAfterBreak = mileageAfterBreak;
  }

  public int getMileageBeforeBreak()
  {
    return this.mileageBeforeBreak;
  }

  public void setMileageBeforeBreak(int mileageBeforeBreak)
  {
    this.mileageBeforeBreak = mileageBeforeBreak;
  }

  public int getFrontOxgenSensorVal()
  {
    return this.frontOxgenSensorVal;
  }

  public void setFrontOxgenSensorVal(int frontOxgenSensorVal)
  {
    this.frontOxgenSensorVal = frontOxgenSensorVal;
  }

  public int getBackOxgenSensorVal()
  {
    return this.backOxgenSensorVal;
  }

  public void setBackOxgenSensorVal(int backOxgenSensorVal)
  {
    this.backOxgenSensorVal = backOxgenSensorVal;
  }

  public int getAirConditionerStatus()
  {
    return this.airConditionerStatus;
  }

  public void setAirConditionerStatus(int airConditionerStatus)
  {
    this.airConditionerStatus = airConditionerStatus;
  }

  public int getOilVal()
  {
    return this.oilVal;
  }

  public void setOilVal(int oilVal)
  {
    this.oilVal = oilVal;
  }
}