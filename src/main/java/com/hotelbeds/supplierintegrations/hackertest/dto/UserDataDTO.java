package com.hotelbeds.supplierintegrations.hackertest.dto;

import java.time.LocalDateTime;

/**
 * The Class UserDataDTO.
 */
public class UserDataDTO {
	
	/** The ip. */
	private String ip;
	
	/** The date. */
	private LocalDateTime date;
	
	/** The action. */
	private Action action;
	
	/** The user name. */
	private String userName;

	/**
	 * Gets the ip.
	 *
	 * @return the ip
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * Sets the ip.
	 *
	 * @param ip the new ip
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * Gets the date.
	 *
	 * @return the date
	 */
	public LocalDateTime getDate() {
		return date;
	}

	/**
	 * Sets the date.
	 *
	 * @param date the new date
	 */
	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	/**
	 * Gets the action.
	 *
	 * @return the action
	 */
	public Action getAction() {
		return action;
	}

	/**
	 * Sets the action.
	 *
	 * @param action the new action
	 */
	public void setAction(Action action) {
		this.action = action;
	}

	/**
	 * Gets the user name.
	 *
	 * @return the user name
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * Sets the user name.
	 *
	 * @param userName the new user name
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}
