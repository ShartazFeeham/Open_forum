package com.open.forum.review.shared.utility;

import java.time.ZoneId;
import java.time.ZonedDateTime;

public class ServerZonedDateTime {

    private static final ZoneId SERVER_ZONE_ID = ZoneId.of("UTC+6");

    public static ZonedDateTime now() {
        return ZonedDateTime.now(SERVER_ZONE_ID);
    }
}
