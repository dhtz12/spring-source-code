package com.song.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns="/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {

	// 保存所有的handler
	private List<Object> beanList = new ArrayList<>();
	// 保存 uri 和 handler 的映射关系
	private Map<String, HandlerMethod> uriHandlerMethodMap = new HashMap<>();

	private static final String SLASH = "/";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	@RequestMapping
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String uri = req.getRequestURI();
		String contextPath = req.getContextPath();
		// 去掉项目路径
		uri = uri.replace(contextPath, "");
		System.out.println(uri);
		if (uri == null) {
			return;
		}
		HandlerMethod handlerMethod = uriHandlerMethodMap.get(uri);
		if (handlerMethod == null) {
			resp.getWriter().write("404");
			return;
		}
		String pageName = (String)methodInvoke(handlerMethod.getBean(), handlerMethod.getMethod());
		viewResolver(pageName, req, resp);
	}

	// 视图解析器
	public void viewResolver(String pageName, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String prefix = "/";
		String suffix = ".jsp";
		req.getRequestDispatcher(prefix + pageName + suffix).forward(req, resp);
	}

	// 反射执行方法
	private Object methodInvoke(Object object, Method method) {
		try {
			return method.invoke(object);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void init() throws ServletException {
		// 获取指定包下的Class对象
		List<Class<?>> classList = ClassUtils.getAllClassByPackageName("com.javashitang.controller");
		// 找到所有标注了@Controller的类
		findAllConrollerClass(classList);
		// 初始化 uri 和 handler 的映射关系
		handlerMapping();
	}


	public void findAllConrollerClass(List<Class<?>> list) {
		list.forEach(bean -> {
			// 将被@Controller注解修饰的类放到beanList
			if (bean.isAnnotationPresent(Controller.class)) {
				try {
					beanList.add(bean.newInstance());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	// 根据url找到相应的处理类
	public void handlerMapping() {
		for (Object bean : beanList) {
			Class<? extends Object> classInfo = bean.getClass();
			// 获取类上的@RequestMapping信息
			RequestMapping beanRequestMapping = classInfo.getDeclaredAnnotation(RequestMapping.class);
			String baseUrl = beanRequestMapping != null ? String.valueOf(beanRequestMapping.value()) : "";
			Method[] methods = classInfo.getDeclaredMethods();
			for (Method method : methods) {
				System.out.println(method.getName());
				// 获取方法上的@RequestMapping信息
				RequestMapping methodRequestMapping = method.getDeclaredAnnotation(RequestMapping.class);
				if (methodRequestMapping != null) {
					String requestUrl = SLASH + baseUrl + SLASH + methodRequestMapping.value();
					// 为了处理@Controller和@RequestMapping value 中加了 / 前缀的情况
					requestUrl = requestUrl.replaceAll("/+", "/");
					HandlerMethod handlerMethod = new HandlerMethod(bean, method);
					uriHandlerMethodMap.put(requestUrl, handlerMethod);
				}
			}
		}
	}
}
