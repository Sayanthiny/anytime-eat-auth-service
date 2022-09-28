package it.itwtech.ateauth.utils;

import java.sql.Timestamp;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

public class Utils {

	public static String getJwt(HttpServletRequest request) {
		String authHeader = request.getHeader("Authorization");
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			return authHeader.replace("Bearer ", "");
		}
		return null;
	}

//	public static UserRoles getAuthorities(HttpServletRequest request, TokenStore tokenStore) {
//		String jwt = getJwt(request);
//		if (jwt != null) {
//			OAuth2AccessToken accessToken = tokenStore.readAccessToken(jwt);
//			OAuth2Authentication authentication = tokenStore.readAuthentication(accessToken);
//			if (!accessToken.isExpired() && authentication.isAuthenticated()) {
//				if (!authentication.getAuthorities().isEmpty()) {
//					UserRoles role = null;
//					for (GrantedAuthority authoritie : authentication.getAuthorities()) {
//						role = UserRoles.getUserRole(authoritie.getAuthority());
//					}
//					return role;
//				}
//			}
//		}
//		return null;
//	}

	public static Timestamp timeFormat() {
		Calendar time = Calendar.getInstance();
		Timestamp localTime = new Timestamp(time.getTimeInMillis());
		return localTime;
	}
}
