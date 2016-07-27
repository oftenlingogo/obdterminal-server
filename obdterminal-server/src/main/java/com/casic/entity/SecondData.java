package com.casic.entity;

public class SecondData
{
  private String bdStatus;
  private double longitude;
  private double latitude;
  private double bdspeed;
  private float bearing;
  private int fuelValue;
  private int obdspeed;
  private int rspeed;
  private int torque;

  public String getBdStatus()
  {
    return this.bdStatus;
  }
  public void setBdStatus(String bdStatus) {
    this.bdStatus = bdStatus;
  }
  public double getLongitude() {
    return this.longitude;
  }
  public void setLongitude(double longitude) {
    this.longitude = longitude;
  }
  public double getLatitude() {
    return this.latitude;
  }
  public void setLatitude(double latitude) {
    this.latitude = latitude;
  }
  public double getBdspeed() {
    return this.bdspeed;
  }
  public void setBdspeed(double bdspeed) {
    this.bdspeed = bdspeed;
  }
  public float getBearing() {
    return this.bearing;
  }
  public void setBearing(float bearing) {
    this.bearing = bearing;
  }
  public int getFuelValue() {
    return this.fuelValue;
  }
  public void setFuelValue(int fuelValue) {
    this.fuelValue = fuelValue;
  }
  public int getObdspeed() {
    return this.obdspeed;
  }
  public void setObdspeed(int obdspeed) {
    this.obdspeed = obdspeed;
  }
  public int getRspeed() {
    return this.rspeed;
  }
  public void setRspeed(int rspeed) {
    this.rspeed = rspeed;
  }
  public int getTorque() {
    return this.torque;
  }
  public void setTorque(int torque) {
    this.torque = torque;
  }
}