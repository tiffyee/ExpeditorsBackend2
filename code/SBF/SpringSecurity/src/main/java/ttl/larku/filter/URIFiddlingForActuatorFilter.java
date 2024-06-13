package ttl.larku.filter;


import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.*;

/**
 * IMPORTANT - We are NOT using this right now.  We are using the much
 * simpler method in actuator.AcutatorRestForwardingController.
 * A caveat is that forwarding will not rewrite the urls on the way
 * back out, as the Filter below will do.
 * *******************************************************************
 * We are using this filter because we want to allow actuator use through
 * a browser and through a REST api.  Problem comes with security.  If
 * we set it up in SecurityFilterRest, then web apps don't work, and if
 * we set it up in SecurityFilterWeb, then REST apps don't work.
 *
 * Because Spring Boot only allows a single root url for actuator.
 *
 * So, what we do in the is filter is make the REST client use
 * '/actuatorrest' rather than '/actuator'.  In this filter
 * we replace all urls of the form '/actuatorrest' to '/actuator' on the
 * way in, and then change them back to '/actuator' on the way out.
 * Actually, we only do a simple String substitution for now.
 *
 * Since this filter is applied *after* the Spring Security Chain, each
 * type of client can get authorized via a different URL (and thus a
 * different Security Filter Chain) and then go the common '/actuator'
 * endpoint on the app.
 */
//@WebFilter(urlPatterns = "/actuatorrest/*")
//@Component
@Order(SecurityProperties.DEFAULT_FILTER_ORDER + 5)
public class URIFiddlingForActuatorFilter implements Filter {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    private String targetString = "/actuatorrest";
    private String replaceString = "/actuator";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String uri = httpRequest.getRequestURI();
        String servletPath = httpRequest.getServletPath();
        String params = httpRequest.getQueryString();

        //Create the wrappers for the request and the response.
        //The request wrapper takes care of the translation on the
        //way in.  The response wrapper is needed to save the request
        //so we can massage it on the way out later in this Filter.
        HttpServletRequestWrapper wrappedRequest = new URIFiddlingForActuatorRequestWrapper(httpRequest, targetString, replaceString);
        URIFiddlingForActuatorResponseWrapper wrappedResponse = new URIFiddlingForActuatorResponseWrapper(httpResponse);

        logger.info("Changing URL from {} to {} ", targetString, replaceString);

        chain.doFilter(wrappedRequest, wrappedResponse);

//        var servletResponse = wrappedResponse.toString();
        var bos = wrappedResponse.osWriter;
        var finalResponse = bos.toByteArray();
        if (servletPath.contains(targetString)) {
            var servletResponse = bos.toString();

            var newResponse = servletResponse.replace(replaceString, targetString);

            finalResponse = newResponse.getBytes();

            logger.info("Reverted URL from {} to {} ", replaceString, targetString);
        }

        var outputStream = response.getOutputStream();
        outputStream.write(finalResponse);
    }

    @Override
    public void destroy() {
    }
}

/**
 * We need this so we can change '/actuatorrest' urls on the way in
 * to '/actuator' urls.
 */
class URIFiddlingForActuatorRequestWrapper extends HttpServletRequestWrapper {
    private StringBuffer newRequestURL;
    private String newRequestURI;
    private String newServletPath;
    private String newParams;

    private String targetString;
    private String replaceString;

    public URIFiddlingForActuatorRequestWrapper(HttpServletRequest source, String targetString, String replaceString) {
        super(source);
        this.targetString = targetString;
        this.replaceString = replaceString;

        newRequestURL = getNewRequestURL();
        newRequestURI = getNewRequestURI();
        newServletPath = getNewServletPath();
        newParams = source.getQueryString();
    }

    @Override
    public StringBuffer getRequestURL() {
        return newRequestURL;
    }

    @Override
    public String getRequestURI() {
        return newRequestURI;
    }

    @Override
    public String getServletPath() {
        return newServletPath;
    }

    @Override
    public java.lang.String getQueryString() {
        return newParams;
    }

    public StringBuffer getNewRequestURL() {
        StringBuffer origURL = ((HttpServletRequest) getRequest()).getRequestURL();
        String uri = origURL.toString();
        uri = uri.replace(targetString, replaceString);
        System.out.println("replaced Uri: " + uri);
        System.out.println("UriFiddler RequestURL replaced: " + origURL + " with: " + uri);

        return new StringBuffer(uri);
    }

    private String getNewRequestURI() {
        String origUri = ((HttpServletRequest) getRequest()).getRequestURI();
        String uri = origUri.replace(targetString, replaceString);
        System.out.println("UriFiddler RequestURI replaced: " + origUri + " with: " + uri);
        return uri;
    }

    private String getNewServletPath() {
        String origUri = ((HttpServletRequest) getRequest()).getRequestURI();
        String uri = origUri.replace(targetString, replaceString);
        System.out.println("UriFiddler ServletPath replaced: " + origUri + " with: " + uri);
        return uri;
    }

};

/**
 * Wrap the response.  We need to save the response so we can
 * potentially change the /actuator urls to /actuatorrest urls.
 * (But only if we have to, see the filter code above).
 * The trick is to have the response get written into a
 * ByteArrayOutputStream.  The only wrinkle there is we also have to
 * create our own ServletOutputStream because that is what the
 * 'getOutputStream' has to return.
 */
class URIFiddlingForActuatorResponseWrapper extends HttpServletResponseWrapper {
    public CharArrayWriter writer;
    public ByteArrayOutputStream osWriter;
    public MyServletResponseOutputStream servletOutputStream;

    public URIFiddlingForActuatorResponseWrapper(HttpServletResponse response) {
        super(response);
        writer = new CharArrayWriter();
        osWriter = new ByteArrayOutputStream();
        try {
            ServletOutputStream sos = response.getOutputStream();
            servletOutputStream = new MyServletResponseOutputStream(sos, osWriter);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public PrintWriter getWriter() {
        return new PrintWriter(writer);
    }

    //The return type here is why we have to create our own
    //ServletOutputStream below.
    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return servletOutputStream;
    }

    @Override
    public String toString() {
        return writer.toString();
    }
}

class MyServletResponseOutputStream extends ServletOutputStream {
    private ServletOutputStream origStream;
    private ByteArrayOutputStream baos;

    public MyServletResponseOutputStream(ServletOutputStream origStream, ByteArrayOutputStream baos) {
        this.origStream = origStream;
        this.baos = baos;
    }

    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public void setWriteListener(WriteListener listener) {

    }

    /**
     * Write into the ByteArrayOutputStream.
     * This is where we are saving the output
     * to later be able to access it on the way out
     * and do post-processing on it.
     * @param b   the {@code byte}.
     * @throws IOException
     */
    @Override
    public void write(int b) throws IOException {
        baos.write(b);
    }
}
