package com.casic.entity;

import java.util.Date;

public class OBDDataChanged {
	private String IMEI;
	private int bdoneNo_after_mileage;
	private int bdoneNo_zero_mileage;
	private int front_oxygen_sensor;
	private int after_oxygen_sensor;
	private Date GPS_TIME;
	private String GPS_STATE;
	private double GPS_LAT;
	private double GPS_LONG;
	private double GPS_SPEED;
	private double direction_angle;
	private int instant_fuel;
	private int db_speed;
	private int speed;
	private int torque;
	private int air_condion_state;
	private int total_fuel;

	public String getIMEI() {
		return this.IMEI;
	}

	public void setIMEI(String iMEI) {
		this.IMEI = iMEI;
	}

	public Date getGPS_TIME() {
		return this.GPS_TIME;
	}

	public void setGPS_TIME(Date gPSTIME) {
		this.GPS_TIME = gPSTIME;
	}

	public int getBdoneNo_after_mileage() {
		return this.bdoneNo_after_mileage;
	}

	public void setBdoneNo_after_mileage(int bdoneNoAfterMileage) {
		this.bdoneNo_after_mileage = bdoneNoAfterMileage;
	}

	public int getBdoneNo_zero_mileage() {
		return this.bdoneNo_zero_mileage;
	}

	public void setBdoneNo_zero_mileage(int bdoneNoZeroMileage) {
		this.bdoneNo_zero_mileage = bdoneNoZeroMileage;
	}

	public int getFront_oxygen_sensor() {
		return this.front_oxygen_sensor;
	}

	public void setFront_oxygen_sensor(int frontOxygenSensor) {
		this.front_oxygen_sensor = frontOxygenSensor;
	}

	public int getAfter_oxygen_sensor() {
		return this.after_oxygen_sensor;
	}

	public void setAfter_oxygen_sensor(int afterOxygenSensor) {
		this.after_oxygen_sensor = afterOxygenSensor;
	}

	public int getAir_condion_state() {
		return this.air_condion_state;
	}

	public void setAir_condion_state(int airCondionState) {
		this.air_condion_state = airCondionState;
	}

	public int getTotal_fuel() {
		return this.total_fuel;
	}

	public void setTotal_fuel(int totalFuel) {
		this.total_fuel = totalFuel;
	}

	public String getGPS_STATE() {
		return this.GPS_STATE;
	}

	public void setGPS_STATE(String gPSSTATE) {
		this.GPS_STATE = gPSSTATE;
	}

	public double getGPS_LAT() {
		return this.GPS_LAT;
	}

	public void setGPS_LAT(double gPSLAT) {
		this.GPS_LAT = gPSLAT;
	}

	public double getGPS_LONG() {
		return this.GPS_LONG;
	}

	public void setGPS_LONG(double gPSLONG) {
		this.GPS_LONG = gPSLONG;
	}

	public double getGPS_SPEED() {
		return this.GPS_SPEED;
	}

	public void setGPS_SPEED(double gPSSPEED) {
		this.GPS_SPEED = gPSSPEED;
	}

	public double getDirection_angle() {
		return this.direction_angle;
	}

	public void setDirection_angle(double directionAngle) {
		this.direction_angle = directionAngle;
	}

	public int getInstant_fuel() {
		return this.instant_fuel;
	}

	public void setInstant_fuel(int instantFuel) {
		this.instant_fuel = instantFuel;
	}

	public int getDb_speed() {
		return this.db_speed;
	}

	public void setDb_speed(int dbSpeed) {
		this.db_speed = dbSpeed;
	}

	public int getSpeed() {
		return this.speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getTorque() {
		return this.torque;
	}

	public void setTorque(int torque) {
		this.torque = torque;
	}
}