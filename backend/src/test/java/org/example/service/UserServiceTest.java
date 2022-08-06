package org.example.service;

import org.example.exceptions.SessionException;
import org.example.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.servlet.http.HttpServletRequest;
import java.util.Vector;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@ExtendWith(SpringExtension.class)
class UserServiceTest {
	@MockBean
	private UserRepository userRepository;
	@MockBean
	private UserService userService;

	@Test
	void wrapRequest_requestNull_exceptionReturned() {
		SessionException thrown = assertThrows(SessionException.class, () -> {
			doCallRealMethod().when(userService).wrapRequest(any());
			userService.wrapRequest(null);
		});

		assertThat(thrown.getLocalizedMessage(), is("Wrong session token"));
	}

	@Test
	void wrapRequest_noTokensInHeader_exceptionReturned() {
		SessionException thrown = assertThrows(SessionException.class, () -> {
			HttpServletRequest request = mock(HttpServletRequest.class);
			Vector<String> vector = new Vector<>();
			doReturn(vector.elements()).when(request).getHeaders(UserService.TOKEN);
			doReturn(userRepository).when(userService).getUserRepository();
			doCallRealMethod().when(userService).wrapRequest(any());
			userService.wrapRequest(request);
		});

		assertThat(thrown.getLocalizedMessage(), is("No token in request"));
	}

}
