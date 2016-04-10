package com.github.thierryabalea.ticket_sales.translate;

import com.github.thierryabalea.ticket_sales.api.ConcertCreated;
import com.github.thierryabalea.ticket_sales.api.EventType;
import com.github.thierryabalea.ticket_sales.api.Message;
import com.github.thierryabalea.ticket_sales.domain.Concert;
import com.github.thierryabalea.ticket_sales.domain.Seating;
import com.github.thierryabalea.ticket_sales.domain.Section;
import com.google.common.collect.Maps;
import org.junit.Test;

import java.util.Map;
import java.util.Map.Entry;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ConcertAvailableTranslatorTest {

    @Test
    @SuppressWarnings("rawtypes")
    public void shouldTranslate() {
        Map<Section, Seating> seating = Maps.newLinkedHashMap();
        seating.put(new Section(1234L, "Wing A", 34.50F), new Seating(20));
        seating.put(new Section(4567L, "Wing B", 55.40F), new Seating(40));

        Concert concert = new Concert(1234L, "Red Hot Chili Peppers", "Albert Hall", seating);

        Message message = new Message();
        ConcertAvailableTranslator.translateTo(message, 0, concert);

        assertThat(message.type.get(), is((Enum) EventType.CONCERT_CREATED));
        ConcertCreated concertCreated = message.event.asConcertCreated;
        assertThat(concertCreated.concertId.get(), is(concert.getId()));
        assertThat(concertCreated.name.get(), is(concert.getName()));
        assertThat(concertCreated.venue.get(), is(concert.getVenue()));
        assertThat(concertCreated.numSections.get(), is((short) seating.size()));

        int i = 0;
        for (Entry<Section, Seating> entry : seating.entrySet()) {
            assertThat(concertCreated.sections[i].sectionId.get(), is(entry.getKey().getId()));
            assertThat(concertCreated.sections[i].seats.get(), is(entry.getValue().getAvailableSeats()));
            assertThat(concertCreated.sections[i].price.get(), is(entry.getKey().getPrice()));
            i++;
        }
    }

}