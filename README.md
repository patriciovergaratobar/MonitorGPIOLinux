# GPIO Monitor for Linux

This project aims to be an alternative to read gpios with java 8.
The project was tested on a Raspberry Pi and an imx.6.

### MonitorGpioLinux

This is the main project that is responsible for reading the GPIO.

To build the project it is necessary that you have Java 8 and Maven.

To read the gpio and execute an action when a change is made, you must only write a code like this:

```java
		
	final MonitorGPIO monitorGPIO = new MonitorGPIO();
		
	List<GpioDto> gpios = new ArrayList<>();
        
        //Define the gpio you want to read. Example 57 y 82
	GpioDto gpio57 = new GpioDto();
	gpio57.setPinNumber(GpioUtil.GPIO_57);
	gpio57.setCallBackGpio((status) -> System.out.println("=0 The new status in the gpio 57 is " + status));
	
	GpioDto gpio82 = new GpioDto();
	gpio82.setPinNumber(GpioUtil.GPIO_82);
	gpio82.setCallBackGpio((status) -> System.out.println("=0 The new status in the gpio 82 is " + status));
	
    
	gpios.add(gpio57);
	gpios.add(gpio82);
		
	monitorGPIO.startMonitor(gpios);

        
```

