package ru.philit.ufs.config.resolver;

import javax.servlet.ServletRequest;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.core.MethodParameter;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import ru.philit.ufs.model.cache.UserCache;
import ru.philit.ufs.model.entity.user.ClientInfo;

public class ClientInfoArgumentResolver implements HandlerMethodArgumentResolver {

  private final BeanFactory beanFactory;

  public ClientInfoArgumentResolver(BeanFactory beanFactory) {
    this.beanFactory = beanFactory;
  }

  @Override
  public boolean supportsParameter(MethodParameter methodParameter) {
    return methodParameter.getParameterType().equals(ClientInfo.class);
  }

  @Override
  public Object resolveArgument(
      MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer,
      NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory
  ) throws Exception {
    ClientInfo clientInfo = new ClientInfo();
    UserCache userCache = beanFactory.getBean(UserCache.class);

    clientInfo.setSessionId(getToken(nativeWebRequest));
    clientInfo.setUser(userCache.getUser(clientInfo.getSessionId()));
    clientInfo.setHost(getClientHost(nativeWebRequest));

    return clientInfo;
  }

  private String getToken(NativeWebRequest webRequest) {
    return webRequest.getHeader("etoken");
  }

  private String getClientHost(NativeWebRequest webRequest) {
    String host = webRequest.getHeader("HTTP_X_FORWARDED_FOR");

    if (StringUtils.isEmpty(host)) {
      host = webRequest.getHeader("X_FORWARDED_FOR");
    }
    if (StringUtils.isEmpty(host)) {
      host = webRequest.getHeader("iv-remote-address");
    }
    if (StringUtils.isEmpty(host) && (webRequest instanceof ServletWebRequest)) {
      ServletRequest servletRequest = ((ServletWebRequest) webRequest).getRequest();

      host = servletRequest.getRemoteAddr();
      if (StringUtils.isEmpty(host)) {
        host = servletRequest.getRemoteHost();
      }
    }
    return host;
  }
}
