package ch.ffhs.dameapi;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpSession;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class DameApiApplicationTests {

	@LocalServerPort
	private int port;

	private String baseURL;


	public void setMockSessionid(String mockSessionid){
		HttpSession mockSession = new MockHttpSession(null, mockSessionid);
		MockHttpServletRequest mockRequest = new MockHttpServletRequest();
		mockRequest.setSession(mockSession);
		RequestAttributes servletWebRequest = new ServletWebRequest(mockRequest);
		RequestContextHolder.setRequestAttributes(servletWebRequest);
	}

	public void setBaseURL(String endPoint){
		baseURL = "http://localhost:" + port + "/api" + endPoint;
	}

	public String getBaseURL() {
		return baseURL;
	}

	@BeforeEach
	public void contextLoads() {
		//define custom http session id
		this.setMockSessionid("JUnitTestId123456789");
	}
}
