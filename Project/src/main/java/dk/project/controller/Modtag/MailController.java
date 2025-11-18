package dk.project.controller.Modtag;

import dk.project.server.ThymeleafSetup;
import io.javalin.Javalin;
import io.javalin.http.Context;
import javax.mail.*;
import dk.project.server.MailSetup;

public class MailController {

    // Attributes
    private static MailController controller = new MailController();

    // _______________________________________________

    public static void registerRoutes(Javalin app) {
        app.post("/beregn/modtag", controller::contactUs);
    }

    // ___________________________________________________________

    public void contactUs(Context ctx) {

        String firstname = ctx.formParam("fornavn");
        String lastname = ctx.formParam("efternavn");
        String email = ctx.formParam("email");
        String phoneParam = ctx.formParam("telefon");

        // Validation
        String phone = (phoneParam != null && !phoneParam.isEmpty()) ? phoneParam : "Ikke angivet";

        String mailBody = String.format("""
                Tak for din forespørgsel efter et tilbud på en carport.
                Vores medarbejdere kigger på det og vender tilbage snarest muligt.
                
                Du kan se status på dit tilbud her:
                fog.guacamoleboy.dk/status

                Din oplysninger
                _______________
                
                Navn:
                %s %s

                E-mail:
                %s

                Telefonnummer:
                %s

                """, firstname, lastname, email, phone);

        boolean sent = MailSetup.sendMail(email, "Fog - Carport", mailBody);
        boolean toUs = MailSetup.sendMail("fog@travlr.dk", "Carport - Tilbud", mailBody);

        if (sent && toUs) {
            ctx.redirect("/tak");
        } else {
            ctx.redirect("/beregn/modtag?error=contactError");
        }

    }

} // MailController end