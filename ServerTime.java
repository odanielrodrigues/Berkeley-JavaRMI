package util;

import java.rmi.Remote;
import java.rmi.RemoteException;

//interface para servidor de tempo 
public interface ServerTime extends Remote {

	Time getTime() throws RemoteException;

	void setTime(long epochSeconds) throws RemoteException;
}