package com.casic.entity;

public class BDData
{
  private String bdStatus;
  private double longitude;
  private double latitude;
  private double bdspeed;
  private double bearing;

  public String getBdStatus()
  {
    return this.bdStatus;
  }

  public void setBdStatus(String bdStatus)
  {
    this.bdStatus = bdStatus;
  }

  public double getLongitude()
  {
    return this.longitude;
  }

  public void setLongitude(double longitude)
  {
    this.longitude = longitude;
  }

  public double getLatitude()
  {
    return this.latitude;
  }

  public void setLatitude(double latitude)
  {
    this.latitude = latitude;
  }

  public double getBdspeed()
  {
    return this.bdspeed;
  }

  public void setBdspeed(double bdspeed)
  {
    this.bdspeed = bdspeed;
  }

  public double getBearing()
  {
    return this.bearing;
  }

  public void setBearing(double bearing)
  {
    this.bearing = bearing;
  }
}