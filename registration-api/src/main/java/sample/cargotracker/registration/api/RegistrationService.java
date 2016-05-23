package sample.cargotracker.registration.api;

import akka.Done;
import akka.NotUsed;
import akka.stream.javadsl.Source;
import com.lightbend.lagom.javadsl.api.Descriptor;
import com.lightbend.lagom.javadsl.api.Service;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.api.transport.Method;
import org.pcollections.PSequence;

import static com.lightbend.lagom.javadsl.api.Service.*;

/**
 * The registration service interface.
 * <p/>
 * This describes everything that Lagom needs to know about how to serve and consume the RegistrationService.
 */
public interface RegistrationService extends Service {

    /**
     * Example: curl -H "Content-Type: application/json" -X POST -d
     * '{
     * "cargo": {
     * "id": 1,
     * "name": "laptop",
     * "description": "macbook",
     * "owner": "Clark Kent",
     * "destination": "Metropolis"
     * }
     * }' http://localhost:9000/api/registration
     */
    ServiceCall<NotUsed, sample.cargotracker.registration.api.Cargo, Done> register();

    ServiceCall<NotUsed, NotUsed, Source<sample.cargotracker.registration.api.Cargo, ?>> getLiveRegistrations();

    ServiceCall<NotUsed, NotUsed, PSequence<sample.cargotracker.registration.api.Cargo>> getAllRegistrations();

    ServiceCall<String, NotUsed, sample.cargotracker.registration.api.Cargo> getRegistration();

    @Override
    default Descriptor descriptor() {
        // @formatter:off
        return named("registrationService").with(
                restCall(Method.POST, "/api/registration", register()),
                pathCall("/api/registration/live", getLiveRegistrations()),
                restCall(Method.GET, "/api/registration/all", getAllRegistrations()),
                restCall(Method.GET, "/api/registration/:id", getRegistration())
        ).withAutoAcl(true);
        // @formatter:on
    }
}
