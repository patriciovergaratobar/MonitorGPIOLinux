package com.pvergara.MonitorGpioLinux.examples;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.BasicConfigurator;

import com.pvergara.MonitorGpioLinux.MonitorGPIO;
import com.pvergara.MonitorGpioLinux.dto.GpioDto;
import com.pvergara.MonitorGpioLinux.utils.GpioUtil;

/**
 * This example was tested in gpio input and in an imx6 with Debian.
 *
 * @author pvergara
 *
 */
public class ExampleGPIOIn {

	public static void main(String[] args) {

		BasicConfigurator.configure();
		
		final MonitorGPIO monitorGPIO = new MonitorGPIO();
		
		List<GpioDto> gpios = new ArrayList<>();

		GpioDto gpio57 = new GpioDto();
		gpio57.setPinNumber(GpioUtil.GPIO_57);
		gpio57.setCallBackGpio((status) -> System.out.println("=0 The new change in the gpio 57 is " + status));

		GpioDto gpio82 = new GpioDto();
		gpio82.setPinNumber(GpioUtil.GPIO_82);
		gpio82.setCallBackGpio((status) -> System.out.println("=0 The new change in the gpio 82 is " + status));
		
		gpios.add(gpio57);
		gpios.add(gpio82);
		
		Thread thread = new Thread(() ->  monitorGPIO.startMonitor(gpios));
		thread.start();
		
	}

}
