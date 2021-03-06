package com.github.thierryabalea.ticket_sales.web.json;

import com.github.thierryabalea.ticket_sales.api.command.TicketPurchase;
import net.minidev.json.JSONObject;

public class TicketPurchaseFromJson {

    public static TicketPurchase fromJson(JSONObject object) {
        Number concertId = (Number) object.get("concertId");
        Number sectionId = (Number) object.get("sectionId");
        Number numSeats = (Number) object.get("numSeats");
        Number accountId = (Number) object.get("accountId");
        Number requestId = (Number) object.get("requestId");

        return new TicketPurchase(
                concertId.longValue(),
                sectionId.longValue(),
                numSeats.intValue(),
                accountId.longValue(),
                requestId.longValue()
        );
    }
}
