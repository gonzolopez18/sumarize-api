package com.example.tenpo.infrastructure.logging;

import com.example.tenpo.infrastructure.logging.ApiInterceptor;
import com.example.tenpo.infrastructure.logging.RequestLoggingService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.core.task.TaskExecutor;
import org.springframework.web.servlet.ModelAndView;

import static org.mockito.Mockito.*;

@RunWith(JUnit4.class)
public class ApiInterceptorTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private Object handler;

    @Mock
    private ModelAndView modelAndView;

    @Mock
    private RequestLoggingService loggingService;

    @Mock
    private TaskExecutor taskExecutor;

    private ApiInterceptor apiInterceptor;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        apiInterceptor = new ApiInterceptor(loggingService, taskExecutor);
    }

    @Test
    public void testPostHandle() throws Exception {
        apiInterceptor.postHandle(request, response, handler, modelAndView);

        ArgumentCaptor<Runnable> taskCaptor = ArgumentCaptor.forClass(Runnable.class);
        verify(taskExecutor, times(1)).execute(taskCaptor.capture());
        Runnable capturedTask = taskCaptor.getValue();

        capturedTask.run();
        verify(loggingService, times(1)).save(request, response);
    }
}
