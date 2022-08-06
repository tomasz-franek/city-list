package org.example.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.example.domain.User;
import org.example.exceptions.SessionException;
import org.example.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Date;
import java.util.Enumeration;

@Service
public class UserService implements IUserService {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

	public static final String TOKEN = "TOKEN";
	public static final long ONE_HOUR = 60L * 60L * 1000L;

	public static final Key KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

	@Autowired
	private UserRepository userRepository;

	@Override
	public User findById(Long id) {
		return userRepository.findById(id).orElse(null);
	}

	public ResponseEntity<?> createSession(String[] params) {
		User user = getUserRepository().findByNameAndPassword(params[0], params[1]).orElse(null);
		if (user != null) {
			String jws = Jwts.builder()
					.setSubject(user.getName())
					.setId(user.getUserId().toString())
					.signWith(KEY)
					.setExpiration(new Date(System.currentTimeMillis() + ONE_HOUR))
					.compact();
			MultiValueMap<String,String> headers= new HttpHeaders();
			headers.add(TOKEN,jws);
			return new ResponseEntity<>(user,headers, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>("User is not authorized", HttpStatus.UNAUTHORIZED);
		}
	}



	public User checkSession(String token) throws SessionException {
		if(StringUtils.isEmpty(token)){
			throw new SessionException("Wrong session token");
		}
		Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(KEY).build().parseClaimsJws(token);
		String id = claims.getBody().getId();
		String name = claims.getBody().getSubject();
		User user = findById(Long.parseLong(id));
		if (user == null || !user.getName().equals(name)) {
			throw new SessionException("Wrong session token");
		}
		if (claims.getBody().getExpiration().getTime() < System.currentTimeMillis()) {
			throw new SessionException("Session expired");
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(String.format("Token ok %s for user %s ", token, id));
		}
		return user;
	}

	public User wrapRequest(final HttpServletRequest request) throws SessionException {
		if (request == null ) {
			throw new SessionException("Wrong session token");
		}
		Enumeration<String> list = request.getHeaders(TOKEN);

		if (!list.hasMoreElements()) {
			LOGGER.debug("No token in request");
			throw new SessionException("No token in request");
		}
		String token = list.nextElement();
		if (list.hasMoreElements()) {
			throw new SessionException("Too many tokens in request");
		}

		return checkSession(token);
	}

	public UserRepository getUserRepository() {
		return userRepository;
	}

	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
}
