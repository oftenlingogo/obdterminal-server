package com.casic.entity;

import java.util.Date;

public class PacketData
{
  private String terminalId;
  private Date time;
  private ObdTenSecondData[] tenSecondDatas;
  private BDData[] bdDatas;
  private ObdPerSecondData[] perSecondDatas;
  private String hisSt;

  public String getHisSt()
  {
    return this.hisSt;
  }

  public void setHisSt(String hisSt)
  {
    this.hisSt = hisSt;
  }
  public String getTerminalId() {
    return this.terminalId;
  }

  public void setTerminalId(String terminalId)
  {
    this.terminalId = terminalId;
  }

  public Date getTime()
  {
    return this.time;
  }

  public void setTime(Date time)
  {
    this.time = time;
  }

  public ObdTenSecondData[] getTenSecondDatas()
  {
    return this.tenSecondDatas;
  }

  public void setTenSecondDatas(ObdTenSecondData[] tenSecondDatas)
  {
    this.tenSecondDatas = tenSecondDatas;
  }

  public BDData[] getBdDatas()
  {
    return this.bdDatas;
  }

  public void setBdDatas(BDData[] bdDatas)
  {
    this.bdDatas = bdDatas;
  }

  public ObdPerSecondData[] getPerSecondDatas()
  {
    return this.perSecondDatas;
  }

  public void setPerSecondDatas(ObdPerSecondData[] perSecondDatas)
  {
    this.perSecondDatas = perSecondDatas;
  }
}