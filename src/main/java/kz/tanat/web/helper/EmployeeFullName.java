package kz.tanat.web.helper;

import kz.tanat.app.employee.dto.EmployeeDto;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
@Scope("request")
@AllArgsConstructor
public class EmployeeFullName {
    private final HttpServletRequest httpServletRequest;

    /**
     * You can call this method like {@code ${&#064;helper.something()}}.
     */
    public String get(EmployeeDto employee) {
//		return "Hello! " + httpServletRequest.getHeader("Host");
        return String.join(" ", employee.getName().getLast(), employee.getName().getFirst(), employee.getName().getMiddle());
    }
}
