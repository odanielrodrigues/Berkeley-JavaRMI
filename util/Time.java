package util;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Random;

public class Time implements Serializable {

	private static final long serialVersionUID = 1L;
	private LocalDateTime time;

	//gera hora e local baseado no instante passado como parametro de deslocamento, zona n�o pode ser nula, obt�m uma inst�ncia de LocalDateTime usando segundos da �poca de 1970-01-01T00:00:00Z
	public Time() {
		Random random = new Random();
		LocalDateTime now = LocalDateTime.now();
		time = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), random.nextInt(23),
				random.nextInt(59), 0);
	}

	// Get do tempo em segundos ZoneOffset � uma representa��o do fuso hor�rio em termos da diferen�a entre GMT/UTC e o hor�rio determinado.
	public long getEpochSeconds() {
		return time.toEpochSecond(ZoneOffset.UTC);
	}

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime ofInstant) {
		this.time = ofInstant;
	}

	@Override
	public String toString() {
		return time.toString();
	}

}
