package mate.academy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import mate.academy.exception.RegistrationException; // Ważne!
import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.model.User;
import mate.academy.security.AuthenticationService; // Sprawdź czy masz folder 'security'
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;
import mate.academy.service.ShoppingCartService;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) throws Exception {
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);

        AuthenticationService authenticationService =
                (AuthenticationService) injector.getInstance(AuthenticationService.class);

        ShoppingCartService shoppingCartService =
                (ShoppingCartService) injector.getInstance(ShoppingCartService.class);

        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film.");
        movieService.add(fastAndFurious);

        CinemaHall hall = new CinemaHall();
        hall.setCapacity(100);
        hall.setDescription("Main Hall");
        cinemaHallService.add(hall);

        MovieSession session = new MovieSession();
        session.setCinemaHall(hall);
        session.setMovie(fastAndFurious);
        session.setShowTime(LocalDateTime.now().plusDays(1));
        movieSessionService.add(session);

        User user = authenticationService.register("bob@gmail.com", "123456");

        shoppingCartService.addSession(session, user);

        System.out.println("Cart after adding session: " + shoppingCartService.getByUser(user));

        shoppingCartService.clear(shoppingCartService.getByUser(user));
        System.out.println("Cart after clear: " + shoppingCartService.getByUser(user));
    }
}
