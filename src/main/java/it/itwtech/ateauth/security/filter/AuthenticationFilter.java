package it.itwtech.ateauth.security.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.filter.OncePerRequestFilter;

import it.itwtech.ateauth.enums.UserRoles;
import it.itwtech.ateauth.model.Role;
import it.itwtech.ateauth.repositories.RolePermissionRepository;
import it.itwtech.ateauth.repositories.RoleRepository;
import it.itwtech.ateauth.utils.Utils;


@Service
public class AuthenticationFilter extends OncePerRequestFilter {
@Autowired
private TokenStore tokenStore;

@Autowired
private RolePermissionRepository rolePermissionRepositroy;

@Autowired
private RoleRepository roleRepositroy;

	@Transactional(readOnly = true)
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			if (!(request instanceof HttpServletRequest) || !(response instanceof HttpServletResponse)) {
				throw new ServletException("OncePerRequestFilter just supports HTTP requests");
			}
			String jwt = Utils.getJwt(request);
			if (jwt != null) {
				OAuth2AccessToken accessToken = tokenStore.readAccessToken(jwt);
				OAuth2Authentication authentication = tokenStore.readAuthentication(accessToken);
				if (!accessToken.isExpired() && authentication.isAuthenticated()) {
					if (!authentication.getAuthorities().isEmpty()) {
						Set<GrantedAuthority> authority = new HashSet<GrantedAuthority>();
						List<UserRoles> roles = new ArrayList<UserRoles>();
						authentication.getAuthorities().forEach(auth -> {
							roles.add(UserRoles.getUserRole(auth.getAuthority()));
						});
						Role role = roleRepositroy.findByRoleName(roles.get(0));
						rolePermissionRepositroy.findAllByRoleId(role.getId()).forEach((rolePerm) -> {
			
							authority.add(new SimpleGrantedAuthority(rolePerm.getModule().getPrefix() + '_' + rolePerm.getPermission()));
						});
						logger.info("set user authority in security context, authoritys:" + authority + "");
						UsernamePasswordAuthenticationToken authorization = new UsernamePasswordAuthenticationToken(
								authentication.getPrincipal(), authentication.getCredentials(), authority);
						authorization.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
						SecurityContextHolder.getContext().setAuthentication(authorization);
					}
				}
			}
		} catch (Exception ex) {
			logger.error("Could not set user authentication in security context", ex);
		}
		filterChain.doFilter(request, response);

	}

}
