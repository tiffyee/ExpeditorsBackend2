package ttl.larku.filter;


import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@WebFilter(urlPatterns = {"/adminrest/student", "/adminrest/student/*",
      "/adminrest/course", "/adminrest/course/*",
      "/admin/getStudents" ,"/admin/getCourses"})
//@Component
public class TimingFilter implements Filter {
   @Override
   public void init(FilterConfig filterConfig) throws ServletException {
   }

   @Override
   public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
      HttpServletRequest hsRequest = (HttpServletRequest) request;
      System.out.printf("Before TimingFilter Filter for %s%n", hsRequest.getRequestURI());

      Instant start = Instant.now();

      chain.doFilter(request, response);

      Instant end = Instant.now();

      System.out.printf("TimingFilter: Call to %s took %d ms%n",
            hsRequest.getRequestURI(), start.until(end, ChronoUnit.MILLIS));

   }

   @Override
   public void destroy() {
   }
}
