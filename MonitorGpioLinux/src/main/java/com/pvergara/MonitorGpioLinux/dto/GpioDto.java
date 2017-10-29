package com.pvergara.MonitorGpioLinux.dto;

import java.util.function.Consumer;

/**
 * Class Model GPIO.
 *
 * @author pvergara
 *
 */
public class GpioDto {

	/**
     * Number pin gpio.
     */
    private String pinNumber;

    /**
     * Status pin gpio.
     */
    private Boolean status;

    /**
     * Function called after a change.
     */
    private Consumer<Boolean> callBackGpio;

    public GpioDto() {
    	//GPIO set status default 0. 
    	this.status = false;
    }

	public String getPinNumber() {
		return pinNumber;
	}

	public void setPinNumber(String pinNumber) {
		this.pinNumber = pinNumber;
	}

	/**
	 * Get Status GPIO
	 * GPIO output  1 = TRUE  ------ Relay  Close
	 * GPIO output  0 = FALSE ------ Relay  Open
	 * GPIO default 0 = FALSE ------ Relay  Open
	 * @return
	 */
	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Consumer<Boolean> getCallBackGpio() {
		return callBackGpio;
	}

	public void setCallBackGpio(Consumer<Boolean> callBackGpio) {
		this.callBackGpio = callBackGpio;
	}

}
