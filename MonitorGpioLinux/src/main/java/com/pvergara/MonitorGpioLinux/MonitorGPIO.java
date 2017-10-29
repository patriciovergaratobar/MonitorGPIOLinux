package com.pvergara.MonitorGpioLinux;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.log4j.Logger;

import com.pvergara.MonitorGpioLinux.dto.GpioDto;

/**
 * Class Monitor GPIO at Linux.
 *
 * @author pvergara
 *
 */
public class MonitorGPIO {

	static Logger log = Logger.getLogger(MonitorGPIO.class);
 
	/**
	 * Map with the last status of each GPIO.
	 */
	final Map<String, GpioDto> lastStates = new ConcurrentHashMap<>();

	/**
	 * Path System GPIO.
	 */
	private final static String PATH_GPIO = "/sys/class/gpio/";

	/**
	 * Method that monitors the gpios.
	 * @param gpios
	 * @throws IOException
	 */
	public void startMonitor(final List<GpioDto> gpios) {
		while (true) {
			gpios.stream().forEach((gpio) -> checkingChange(gpio));			
			try {
				Thread.sleep(10L);
			} catch (InterruptedException e) {
				log.error("Error time sleep. ", e);
			}
		}

	}

	/**
	 * Method that checks if there are changes of the gpio.
	 * @param gpio
	 */
	private void checkingChange(final GpioDto gpio) {
		try {
			String valorNuevo = currentStatus(Files.lines(pathSystemGpio(gpio)));
			Boolean newStatus;
			if (valorNuevo.contains("1")) {
				newStatus = Boolean.TRUE;
			} else {
				newStatus = Boolean.FALSE;
			}
			GpioDto ultimoValor = lastStates.get(gpio.getPinNumber());
			if (lastStates.containsKey(gpio.getPinNumber()) == Boolean.FALSE || ultimoValor.getStatus().equals(newStatus) == Boolean.FALSE) {				
				gpio.setStatus(newStatus);
				lastStates.put(gpio.getPinNumber(), gpio);				
				final Thread hiloCallback = new Thread(() -> runCallback(gpio));
				hiloCallback.start();
			}
		} catch (IOException e) {
			log.error("Error read gpio status. ", e);
		}
	}

	/**
	 * Method that obtains the path of the value of a gpio.
	 * 
	 * @param gpio
	 * @return
	 */
	private Path pathSystemGpio(final GpioDto gpio) {
		final Path path = Paths.get(PATH_GPIO.concat("gpio").concat(gpio.getPinNumber()).concat("/value"));
		return path;
	}

	/**
	 * Method that gets the value of the gpio.
	 * 
	 * @param contenido
	 * @return
	 */
	private String currentStatus(final Stream<String> contenido) {
		final String valor = contenido.map(Object::toString).collect(Collectors.joining("")).trim();
		contenido.close();
		return valor;
	}

	/**
	 * Method that handles the callbacks of the gpio changes.
	 * @param gpio
	 */
	private void runCallback(final GpioDto gpio) {
		log.info("GPIO -> "+gpio.getPinNumber());
		log.info("Status -> "+gpio.getStatus());
		log.info("Start Call Back");
		gpio.getCallBackGpio().accept(gpio.getStatus());
	}

}
