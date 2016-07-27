package com.casic.entity;

import java.util.Date;

public class PacketData2
{
  private String terminalId;
  private ObdTenSecondData[] tenSecondData;
  private SecondData[] secondData;
  private Date time;

  public String getTerminalId()
  {
    return this.terminalId;
  }

  public void setTerminalId(String terminalId)
  {
    this.terminalId = terminalId;
  }

  public ObdTenSecondData[] getTenSecondData()
  {
    return this.tenSecondData;
  }

  public void setTenSecondData(ObdTenSecondData[] tenSecondData)
  {
    this.tenSecondData = tenSecondData;
  }

  public SecondData[] getSecondData()
  {
    return this.secondData;
  }

  public void setSecondData(SecondData[] secondData)
  {
    this.secondData = secondData;
  }

  public Date getTime()
  {
    return this.time;
  }

  public void setTime(Date time)
  {
    this.time = time;
  }
}