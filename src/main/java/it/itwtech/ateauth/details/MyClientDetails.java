package it.itwtech.ateauth.details;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

import it.itwtech.ateauth.model.OauthClientDetails;
import it.itwtech.ateauth.utils.Constants;

public class MyClientDetails implements ClientDetails {

	private static final long serialVersionUID = -5294562011836131914L;

	private OauthClientDetails oauthClientDetails;

	public MyClientDetails(OauthClientDetails oauthClientDetails) {
		this.oauthClientDetails = oauthClientDetails;
	}

	@Override
	public Integer getAccessTokenValiditySeconds() {
		return this.oauthClientDetails.getAccessTokenValidity();
	}

	@Override
	public Map<String, Object> getAdditionalInformation() {
		return null;
	}

	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
		grantedAuthorities.add(new SimpleGrantedAuthority(Constants.ROLE_CLIENT));
		return grantedAuthorities;
	}

	@Override
	public Set<String> getAuthorizedGrantTypes() {
		String[] getGrantedTypes = this.oauthClientDetails.getAuthorizedGrantTypes().split(",");
		Set<String> getGrantedTypesSet = new HashSet<String>();
		for (String string : getGrantedTypes)
			getGrantedTypesSet.add(string);
		return getGrantedTypesSet;
	}

	@Override
	public String getClientId() {
		return this.oauthClientDetails.getClientId();
	}

	@Override
	public String getClientSecret() {
		return this.oauthClientDetails.getClientSecret();
	}

	@Override
	public Integer getRefreshTokenValiditySeconds() {
		return this.oauthClientDetails.getRefreshTokenValidity();
	}

	@Override
	public Set<String> getRegisteredRedirectUri() {
		String[] registeredRedirectUri = this.oauthClientDetails.getWebServerRedirectUri().split(",");
		Set<String> registeredRedirectUriSet = new HashSet<String>();
		for (String string : registeredRedirectUri)
			registeredRedirectUriSet.add(string);
		return registeredRedirectUriSet;
	}

	@Override
	public Set<String> getResourceIds() {
		String[] resourceId = this.oauthClientDetails.getResourceId().split(",");
		Set<String> resourceIdSet = new HashSet<String>();
		for (String string : resourceId)
			resourceIdSet.add(string);
		return resourceIdSet;
	}

	@Override
	public Set<String> getScope() {
		String[] scope = this.oauthClientDetails.getScope().split(",");
		Set<String> scopeSet = new HashSet<String>();
		for (String string : scope)
			scopeSet.add(string);
		return scopeSet;
	}

	@Override
	public boolean isAutoApprove(String arg0) {
		return false;
	}

	@Override
	public boolean isScoped() {
		return true;
	}

	@Override
	public boolean isSecretRequired() {
		return true;
	}
}
