package com.casic.entity;

public class ObdPerSecondData
{
  private int fuelValue;
  private int obdspeed;
  private int rspeed;
  private int torque;

  public int getFuelValue()
  {
    return this.fuelValue;
  }

  public void setFuelValue(int fuelValue)
  {
    this.fuelValue = fuelValue;
  }

  public int getObdspeed()
  {
    return this.obdspeed;
  }

  public void setObdspeed(int obdspeed)
  {
    this.obdspeed = obdspeed;
  }

  public int getRspeed()
  {
    return this.rspeed;
  }

  public void setRspeed(int rspeed)
  {
    this.rspeed = rspeed;
  }

  public int getTorque()
  {
    return this.torque;
  }

  public void setTorque(int torque)
  {
    this.torque = torque;
  }
}