package mate.academy;

import java.time.LocalDateTime;
import mate.academy.lib.Injector;
import mate.academy.model.CinemaHall;
import mate.academy.model.Movie;
import mate.academy.model.MovieSession;
import mate.academy.model.User;
import mate.academy.security.AuthenticationService;
import mate.academy.service.CinemaHallService;
import mate.academy.service.MovieService;
import mate.academy.service.MovieSessionService;
import mate.academy.service.ShoppingCartService;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) throws Exception {
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        Movie fastAndFurious = new Movie("Fast and Furious");
        fastAndFurious.setDescription("An action film.");
        movieService.add(fastAndFurious);

        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        CinemaHall hall = new CinemaHall();
        hall.setCapacity(100);
        hall.setDescription("Main Hall");
        cinemaHallService.add(hall);

        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        MovieSession session = new MovieSession();
        session.setCinemaHall(hall);
        session.setMovie(fastAndFurious);
        session.setShowTime(LocalDateTime.now().plusDays(1));
        movieSessionService.add(session);

        AuthenticationService authenticationService =
                (AuthenticationService) injector.getInstance(AuthenticationService.class);
        User user = authenticationService.register("bob@gmail.com", "123456");

        ShoppingCartService shoppingCartService =
                (ShoppingCartService) injector.getInstance(ShoppingCartService.class);
        shoppingCartService.addSession(session, user);

        System.out.println("Cart after adding session: " + shoppingCartService.getByUser(user));

        shoppingCartService.clear(shoppingCartService.getByUser(user));
        System.out.println("Cart after clear: " + shoppingCartService.getByUser(user));
    }
}
