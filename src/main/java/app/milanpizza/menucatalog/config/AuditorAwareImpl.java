package app.milanpizza.menucatalog.config;

import lombok.NonNull;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    @NonNull
    public Optional<String> getCurrentAuditor() {
        // Here you can implement logic to return the current auditor's username or ID
        // For simplicity, returning a static value. In a real application, you might fetch this from the security context.
        return Optional.of("system"); // Replace with actual logic to get the current user
    }
}
