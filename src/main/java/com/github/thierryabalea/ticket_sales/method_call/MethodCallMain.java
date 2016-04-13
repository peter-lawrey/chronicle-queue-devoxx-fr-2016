package com.github.thierryabalea.ticket_sales.method_call;

import com.github.thierryabalea.ticket_sales.api.TicketPurchase;
import com.github.thierryabalea.ticket_sales.domain.ConcertServiceManager;
import com.github.thierryabalea.ticket_sales.web.RequestWebServer;
import com.github.thierryabalea.ticket_sales.web.ResponseWebServer;
import com.github.thierryabalea.ticket_sales.web.json.TicketPurchaseFromJson;
import org.rapidoid.http.fast.On;

public class MethodCallMain {

    public static void main(String[] args) throws Exception {
        On.port(7070);

        ResponseWebServer responseWebServer = new ResponseWebServer();
        responseWebServer.init();

        ConcertServiceManager concertServiceManager = new ConcertServiceManager(responseWebServer);

        RequestWebServer.JsonRequestHandler requestHandler = request -> {
            TicketPurchase ticketPurchase = TicketPurchaseFromJson.fromJson(request);
            concertServiceManager.onTicketPurchase(ticketPurchase);
        };
        RequestWebServer requestWebServer =
                new RequestWebServer(requestHandler);
        requestWebServer.init();

        SeedClient.createConcerts(concertServiceManager);
    }
}