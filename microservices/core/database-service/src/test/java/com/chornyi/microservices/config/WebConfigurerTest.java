package com.chornyi.microservices.config;

import io.undertow.Undertow;
import io.undertow.Undertow.Builder;
import io.undertow.UndertowOptions;
import liquibase.util.file.FilenameUtils;
import org.h2.server.web.WebServlet;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory;
import org.springframework.mock.env.MockEnvironment;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.util.ReflectionTestUtils;
import org.xnio.OptionMap;

import javax.servlet.*;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the WebConfigurer class.
 *
 * @see WebConfigurer
 */
public class WebConfigurerTest {

    private WebConfigurer webConfigurer;

    private MockServletContext servletContext;

    private MockEnvironment env;

    private ApplicationProperties props;

    @Before
    public void setup() {
        servletContext = spy(new MockServletContext());
        doReturn(new MockFilterRegistration())
                .when(servletContext).addFilter(anyString(), any(Filter.class));
        doReturn(new MockServletRegistration())
                .when(servletContext).addServlet(anyString(), any(Servlet.class));

        env = new MockEnvironment();
        props = new ApplicationProperties();

        webConfigurer = new WebConfigurer(env, props);
    }

    @Test
    public void testStartUpServletContext() throws ServletException {
        webConfigurer.onStartup(servletContext);

        verify(servletContext).addServlet(eq("H2Console"), any(WebServlet.class));
    }

    @Test
    public void testCustomizeServletContainer() {
        UndertowServletWebServerFactory container = new UndertowServletWebServerFactory();
        webConfigurer.customize(container);
        assertThat(container.getMimeMappings().get("abs")).isEqualTo("audio/x-mpeg");
        assertThat(container.getMimeMappings().get("html")).isEqualTo("text/html;charset=utf-8");
        assertThat(container.getMimeMappings().get("json")).isEqualTo("text/html;charset=utf-8");
        if (container.getDocumentRoot() != null) {
            assertThat(container.getDocumentRoot().getPath()).isEqualTo(FilenameUtils.separatorsToSystem("target/www"));
        }

        Builder builder = Undertow.builder();
        container.getBuilderCustomizers().forEach(c -> c.customize(builder));
        OptionMap.Builder serverOptions = (OptionMap.Builder) ReflectionTestUtils.getField(builder, "serverOptions");
        assertThat(serverOptions.getMap().get(UndertowOptions.ENABLE_HTTP2)).isNull();
    }

    static class MockFilterRegistration implements FilterRegistration, FilterRegistration.Dynamic {

        @Override
        public void addMappingForServletNames(EnumSet<DispatcherType> dispatcherTypes, boolean isMatchAfter, String... servletNames) {
            
        }

        @Override
        public Collection<String> getServletNameMappings() {
            return null;
        }

        @Override
        public void addMappingForUrlPatterns(EnumSet<DispatcherType> dispatcherTypes, boolean isMatchAfter, String... urlPatterns) {

        }

        @Override
        public Collection<String> getUrlPatternMappings() {
            return null;
        }

        @Override
        public void setAsyncSupported(boolean isAsyncSupported) {

        }

        @Override
        public String getName() {
            return null;
        }

        @Override
        public String getClassName() {
            return null;
        }

        @Override
        public boolean setInitParameter(String name, String value) {
            return false;
        }

        @Override
        public String getInitParameter(String name) {
            return null;
        }

        @Override
        public Set<String> setInitParameters(Map<String, String> initParameters) {
            return null;
        }

        @Override
        public Map<String, String> getInitParameters() {
            return null;
        }
    }

    static class MockServletRegistration implements ServletRegistration, ServletRegistration.Dynamic {

        @Override
        public void setLoadOnStartup(int loadOnStartup) {
            
        }

        @Override
        public Set<String> setServletSecurity(ServletSecurityElement constraint) {
            return null;
        }

        @Override
        public void setMultipartConfig(MultipartConfigElement multipartConfig) {

        }

        @Override
        public void setRunAsRole(String roleName) {

        }

        @Override
        public void setAsyncSupported(boolean isAsyncSupported) {

        }

        @Override
        public Set<String> addMapping(String... urlPatterns) {
            return null;
        }

        @Override
        public Collection<String> getMappings() {
            return null;
        }

        @Override
        public String getRunAsRole() {
            return null;
        }

        @Override
        public String getName() {
            return null;
        }

        @Override
        public String getClassName() {
            return null;
        }

        @Override
        public boolean setInitParameter(String name, String value) {
            return false;
        }

        @Override
        public String getInitParameter(String name) {
            return null;
        }

        @Override
        public Set<String> setInitParameters(Map<String, String> initParameters) {
            return null;
        }

        @Override
        public Map<String, String> getInitParameters() {
            return null;
        }
    }

}
