package demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    /**
     * Used to configure this application as an OAuth Server
     */
	@Configuration
	@EnableAuthorizationServer
	protected static class OAuth2Config extends AuthorizationServerConfigurerAdapter {
	    
	    @Autowired
	    private AuthenticationManager authenticationManager;
	    
	    @Bean
	    public JwtAccessTokenConverter accessTokenConverter() {
	        return new JwtAccessTokenConverter();
	    }

	    @Override
	    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
	        endpoints
	            .authenticationManager(this.authenticationManager)
	            .accessTokenConverter(accessTokenConverter());
	    }
		@Override
		public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
			clients.inMemory()
				.withClient("client")
				.secret("password")
				.authorities("ROLE_TRUSTED_CLIENT")
				.authorizedGrantTypes("implicit","password","authorization_code","refresh_token","client_credentials")
				.scopes("seeTheMessage","doSomethingElse")
			   //.accessTokenValiditySeconds(30)
				;
		}


		@Override
		public void configure(AuthorizationServerSecurityConfigurer oauthServer)	throws Exception {
			oauthServer
				.tokenKeyAccess("permitAll()")
				.allowFormAuthenticationForClients()
				.checkTokenAccess("isAuthenticated()");
		}

	}
}
