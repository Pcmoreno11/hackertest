package com.hotelbeds.supplierintegrations.hackertest.detector;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hotelbeds.supplierintegrations.hackertest.dto.Action;
import com.hotelbeds.supplierintegrations.hackertest.dto.UserDataDTO;
import com.hotelbeds.supplierintegrations.hackertest.utils.DateUtils;

/**
 * 
 * @author Pedro Cesar Moreno
 *
 */
@Service
public class HackerDetectorImpl implements HackerDetector{

	private Map<String, List<UserDataDTO>> mapByIp = new HashMap<>();
	
	@Override
	public String parseLine(String line) {
		
		Boolean suspiciousAction = Boolean.FALSE;
		
		//Delete old records
		this.freeMemory();
		
		//Obtain the login data from the string
		UserDataDTO userDataDTO = this.getUserData(line);
		
		//Only perform actions if there has been a failed attempt
		if(userDataDTO != null && Action.SIGNIN_FAILURE.equals(userDataDTO.getAction())) {
			//Check if we have failures registered with this IP
			if(!mapByIp.containsKey(userDataDTO.getIp())) {
				mapByIp.put(userDataDTO.getIp(), new ArrayList<>());
			}
			
			List<UserDataDTO> listFailedAction = mapByIp.get(userDataDTO.getIp());
			listFailedAction.add(userDataDTO);
			
			//Only interested in the last 5 failed attempts
			if(listFailedAction.size() >= 5) {
				suspiciousAction = this.checkSuspiciousAction(userDataDTO.getIp());
			}
		}
		
		//If there is a suspicious action, we return the IP
		return suspiciousAction ? userDataDTO.getIp() : null;
	}


	private Boolean checkSuspiciousAction(String ip) {
		
		List<UserDataDTO> listFailedAction = this.mapByIp.get(ip);
		
		Optional<UserDataDTO> opUserData1 = listFailedAction.stream()
				.min(HackerDetectorImpl::dateComparator);
		Optional<UserDataDTO> opUserData2 = listFailedAction.stream()
				.max(HackerDetectorImpl::dateComparator);
		
		//Calculate the difference in minutes from the minimum date (at most it can be 5 minutes) and maximum
		if (opUserData1.isPresent() && opUserData2.isPresent()) {
			Long minutesBetween = DateUtils
					.minutesBetweenDates(opUserData1.get().getDate(), opUserData2.get().getDate());
			
			return minutesBetween <= 5L;
		}
		
		return Boolean.FALSE;
	}


	private static int dateComparator(UserDataDTO o1, UserDataDTO o2) {
		return o1.getDate().compareTo(o2.getDate());
	}

	private UserDataDTO getUserData(String line) {
		
		UserDataDTO userDataDTO = null;
		
		String[] split = line.split(",");
		if(split.length == 4) {
			userDataDTO = new UserDataDTO();
			try {
				userDataDTO.setIp(split[0]);
				userDataDTO.setDate(DateUtils.toLocalDateTime(Long.valueOf(split[1])));
				userDataDTO.setAction(Action.valueOf(split[2]));
				userDataDTO.setUserName(split[3]);
			} catch (Exception e) {
				// Possible badly received data
				userDataDTO = null;
			}
		}
		
		return userDataDTO;
	}
	
	private void freeMemory() {
		//Eliminate records that have more than 5 minutes
		for (List<UserDataDTO> list : mapByIp.values()) {
			list.removeIf(ud -> DateUtils.minutesBetweenDates(ud.getDate(), LocalDateTime.now()) > 5L);
		}
	}

}
