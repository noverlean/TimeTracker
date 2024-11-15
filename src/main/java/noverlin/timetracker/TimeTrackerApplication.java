package noverlin.timetracker;

import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.context.SecurityContextHolder;

@SpringBootApplication
public class TimeTrackerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TimeTrackerApplication.class, args);
    }

}
